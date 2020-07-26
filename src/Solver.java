import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final State finalState;
    private boolean solvable;


    private static class State implements Comparable<State> {
        final State previous;
        final int numOfMoves;
        final Board board;
        final int priority;

        State(Board board, int numOfMoves, State previous) {
            this.previous = previous;
            this.numOfMoves = numOfMoves;
            this.board = board;
            priority = this.board.manhattan() + numOfMoves;
        }

        public int compareTo(State that) {
            if (this.priority < that.priority)
                return -1;
            else if (this.priority > that.priority)
                return 1;
            return 0;
        }
    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        solvable = false;

        MinPQ<State> states = new MinPQ<>();
        MinPQ<State> twinStates = new MinPQ<>();
        State dequeued = new State(initial, 0, null);
        states.insert(dequeued);
        State twinDequeued = new State(initial.twin(), 0, null);
        twinStates.insert(twinDequeued);

        while (true) {
            dequeued = states.delMin();
            if (dequeued.board.isGoal()) {
                solvable = true;
                break;
            }
            twinDequeued = twinStates.delMin();
            if (twinDequeued.board.isGoal()) {
                break;
            }

            for (Board itr : dequeued.board.neighbors()) {
                State prev = dequeued.previous;
                boolean seen = false;
                if (prev != null) {
                    if (itr.equals(prev.board)) {
                        seen = true;
                    }
                }
                if (!seen)
                    states.insert(new State(itr, dequeued.numOfMoves + 1, dequeued));
            }
            for (Board itr : twinDequeued.board.neighbors()) {
                State prev = twinDequeued.previous;
                boolean seen = false;
                if (prev != null) {
                    if (itr.equals(prev.board)) {
                        seen = true;
                    }
                }

                if (!seen)
                    twinStates.insert(new State(itr, twinDequeued.numOfMoves + 1, twinDequeued));
            }
        }
        if (solvable)
            finalState = dequeued;
        else finalState = null;
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())
            return -1;
        else return finalState.numOfMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;

        Stack<Board> solution = new Stack<>();
        solution.push(finalState.board);
        State pre = finalState.previous;
        while (pre != null) {
            solution.push(pre.board);
            pre = pre.previous;
        }
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();


        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
