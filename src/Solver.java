import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private State finalState;
    private final Board initialboard;


    private static class State implements Comparable<State> {
        final State previous;
        final int numOfMoves;
        final Board board;
        final int manhattan;

        State(Board board, int numOfMoves, State previous) {
            this.previous = previous;
            this.numOfMoves = numOfMoves;
            this.board = board;
            manhattan = this.board.manhattan();
        }

        public int compareTo(State that) {
            if ((this.manhattan + this.numOfMoves) == (that.manhattan + that.numOfMoves))
                return 0;
            else if ((this.manhattan + this.numOfMoves) < (that.manhattan + that.numOfMoves))
                return -1;
            return 1;
        }
    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        initialboard = initial;
        if (!isSolvable())
            return;
        MinPQ<State> states = new MinPQ<>();
        State dequeued = new State(initial, 0, null);
        states.insert(dequeued);

        while (!dequeued.board.isGoal()) {
            dequeued = states.delMin();
            for (Board itr : dequeued.board.neighbors()) {
                State prev = dequeued.previous;
                boolean seen = false;
                while (prev != null) {
                    if (itr.equals(prev.board)) {
                        seen = true;
                        break;
                    }
                    prev = prev.previous;
                }
                if (!seen)
                    states.insert(new State(itr, dequeued.numOfMoves + 1, dequeued));
            }
        }
        finalState = dequeued;
    }

    private int[] getInvCount() {
        String[] arrOfStr = initialboard.toString().split("\n", 0);
        int[][] arr = new int[arrOfStr.length - 1][arrOfStr.length - 1];
        for (int i = 1; i < arrOfStr.length; i++) {
            String[] tmp = arrOfStr[i].split(" ", 0);
            for (int j = 0; j < arr.length; j++) {
                arr[i - 1][j] = Integer.parseInt(tmp[j]);
            }
        }
        int count = 0;
        int max = 0;
        int blankrow = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i][j] != 0) {
                    if (max > arr[i][j]) {
                        count++;
                    }
                    max = arr[i][j];
                } else blankrow = i;
            }
        }
        int[] out = new int[2];
        out[0] = count;
        out[1] = blankrow;
        return out;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        if (initialboard.dimension() % 2 == 0) {//for even board
            return (getInvCount()[0] + getInvCount()[1]) % 2 != 0;
        } else {//for odd board
            return (getInvCount()[0] % 2 == 0);
        }
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
        int[][] arr = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board initial = new Board(arr);
        Solver answer = new Solver(initial);
        StdOut.print(answer.moves());
        StdOut.print(answer.isSolvable());
        for (Board board : answer.solution()) {
            StdOut.print(board.toString());
        }
    }

}
