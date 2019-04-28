package server;

public class WAM {
    private int rows;
    private int cols;
    private int timeInSeconds;
    private int[][] board;
    private static final int UP = 1;
    private static final int DOWN = 0;
    private volatile boolean canRandomize;


    public WAM(int rows, int cols, int timeInSeconds) {
        this.rows = rows;
        this.cols = cols;
        this.timeInSeconds = timeInSeconds;
        this.canRandomize = true;
        board = new int[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] = DOWN;
            }
        }
    }



    public void setUp(int moleNumber) {
        board[moleNumber / cols][moleNumber % cols] = UP;
    }


    public void setDown(int moleNumber) {
        board[moleNumber / cols][moleNumber % cols] = DOWN;
    }

    public boolean isUp(int moleNumber) {
        return (board[moleNumber / cols][moleNumber % cols] == UP);
    }

    public boolean hasWonGame(){
        //TODO


        return false;
    }

    public boolean hasTiedGame(){
        //TODO


        return false;
    }

}
