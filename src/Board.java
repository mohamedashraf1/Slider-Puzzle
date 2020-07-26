import edu.princeton.cs.algs4.Stack;

public class Board {
    private final int[][] myBoard;


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        myBoard = new int[tiles.length][tiles.length];
        for (int i = 0; i < myBoard.length; i++) {
            System.arraycopy(tiles[i], 0, myBoard[i], 0, myBoard.length);
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder board = new StringBuilder(myBoard.length + "\n");
        for (int[] ints : myBoard) {
            for (int j = 0; j < myBoard.length; j++) {
                board.append(ints[j]).append(" ");
            }
            board.append("\n");
        }
        return board.toString();
    }

    // board dimension n
    public int dimension() {
        return myBoard.length;
    }

    // number of tiles out of place
    public int hamming() {
        int numOfWrong = 0;
        for (int i = 0; i < myBoard.length; i++) {
            for (int j = 0; j < myBoard.length; j++) {
                if (myBoard[i][j] != 0 && myBoard[i][j] != (i * myBoard.length + j + 1))
                    numOfWrong++;
            }
        }
        return numOfWrong;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < myBoard.length; i++) {
            for (int j = 0; j < myBoard.length; j++) {
                if (myBoard[i][j] != 0) {
                    int x, y;
                    x = (myBoard[i][j] - 1) / myBoard.length;
                    y = (myBoard[i][j] - 1) % myBoard.length;
                    sum += Math.abs(i - x) + Math.abs(j - y);
                }
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (this == y)//check if it's the same board
            return true;
        if (y == null)
            return false;
        if (this.getClass() != y.getClass())
            return false;
        Board that = (Board) y;

        if (this.dimension() != that.dimension())
            return false;

        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                if (this.myBoard[i][j] != that.myBoard[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        int tmp;
        int[][] neighbor = new int[myBoard.length][myBoard.length];

        for (int i = 0; i < myBoard.length; i++) {
            System.arraycopy(myBoard[i], 0, neighbor[i], 0, myBoard.length);
        }

        for (int i = 0; i < neighbor.length; i++) {
            for (int j = 0; j < neighbor.length; j++) {
                if (neighbor[i][j] == 0) {
                    if (i > 0) {//swap with the upper
                        tmp = neighbor[i][j];//swap to get the neighbor
                        neighbor[i][j] = neighbor[i - 1][j];
                        neighbor[i - 1][j] = tmp;

                        neighbors.push(new Board(neighbor));

                        tmp = neighbor[i][j];//swap back
                        neighbor[i][j] = neighbor[i - 1][j];
                        neighbor[i - 1][j] = tmp;

                    }
                    if (i < neighbor.length - 1) {// swap with the bottom
                        tmp = neighbor[i][j];//swap to get the neighbor
                        neighbor[i][j] = neighbor[i + 1][j];
                        neighbor[i + 1][j] = tmp;

                        neighbors.push(new Board(neighbor));

                        tmp = neighbor[i][j];//swap back
                        neighbor[i][j] = neighbor[i + 1][j];
                        neighbor[i + 1][j] = tmp;
                    }
                    if (j < neighbor.length - 1) {// swap with the right
                        tmp = neighbor[i][j];//swap to get the neighbor
                        neighbor[i][j] = neighbor[i][j + 1];
                        neighbor[i][j + 1] = tmp;

                        neighbors.push(new Board(neighbor));

                        tmp = neighbor[i][j];//swap back
                        neighbor[i][j] = neighbor[i][j + 1];
                        neighbor[i][j + 1] = tmp;
                    }
                    if (j > 0) {// swap with the lift
                        tmp = neighbor[i][j];//swap to get the neighbor
                        neighbor[i][j] = neighbor[i][j - 1];
                        neighbor[i][j - 1] = tmp;

                        neighbors.push(new Board(neighbor));

                        tmp = neighbor[i][j];//swap back
                        neighbor[i][j] = neighbor[i][j - 1];
                        neighbor[i][j - 1] = tmp;
                    }
                    i = j = neighbor.length;
                }
            }
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinArr = new int[myBoard.length][myBoard.length];

        for (int i = 0; i < myBoard.length; i++) {
            System.arraycopy(myBoard[i], 0, twinArr[i], 0, myBoard.length);
        }


        for (int i = 0; i < twinArr.length; i++) {
            for (int j = 0; j < twinArr.length - 1; j++) {
                if (twinArr[i][j] != 0 && twinArr[i][j + 1] != 0) {
                    int tmp = twinArr[i][j];
                    twinArr[i][j] = twinArr[i][j + 1];
                    twinArr[i][j + 1] = tmp;
                    i = j = twinArr.length;
                }
            }
        }

        return new Board(twinArr);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] arr = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        int[][] arr2 = {{1, 0}, {2, 3}};
        Board test = new Board(arr);
        Board test2 = new Board(arr2);
        System.out.println(test.toString());
        System.out.println(test.hamming());
        System.out.println(test.manhattan());
        for (Board itr : test.neighbors()) {
            System.out.println(itr.toString());
        }

        System.out.println(test.twin());

        System.out.println(test.isGoal());

        System.out.println(test.dimension());

        System.out.println(test.equals(test2));


    }

}
