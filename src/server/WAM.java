package server;

/**
 * A class that represents the board of the Whack-A-Mole game.
 * @author Bao Nguyen (btn6364@rit.edu)
 * @author Jared Sullivan (jms8376@rit.edu)
 */
public class WAM {
    /**
     * The number of rows of the board
     */
    private int rows;

    /**
     * The number of columns of the board
     */
    private int cols;

    /**
     * The board
     */
    private int[][] board;

    /**
     * The value represents a mole is up
     */
    private static final int UP = 1;

    /**
     * The value represents a mole is down
     */
    private static final int DOWN = 0;


    /**
     * Create the board for the game
     * @param rows the number of rows
     * @param cols the number of cols
     */
    public WAM(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new int[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] = DOWN;
            }
        }
    }


    /**
     * Set a mole in the board to be up.
     * @param moleNumber the mole number.
     */
    public void setUp(int moleNumber) {
        board[moleNumber / cols][moleNumber % cols] = UP;
    }

    /**
     * Set a mole in the board to be down.
     * @param moleNumber the mole number.
     */
    public void setDown(int moleNumber) {
        board[moleNumber / cols][moleNumber % cols] = DOWN;
    }

    /**
     * Check if the mole is up or not.
     * @param moleNumber the mole number.
     * @return true if it is up and false otherwise.
     */
    public boolean isUp(int moleNumber) {
        return (board[moleNumber / cols][moleNumber % cols] == UP);
    }

}
