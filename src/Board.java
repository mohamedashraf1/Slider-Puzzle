import java.util.Iterator;

public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
    }

    // string representation of this board
    public String toString() {
        return "";
    }

    // board dimension n
    public int dimension() {
        return 0;
    }

    // number of tiles out of place
    public int hamming() {
        return 0;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            public Iterator<Board> iterator() {
                return null;
            }
        };
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return new Board(new int[5][5]);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
    }

}
