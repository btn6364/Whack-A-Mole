package server;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
    public int randomize() {
        if (!canRandomize) {
            return -1;
        }

        Random random = new Random();
        int moleNumber = random.nextInt(rows * cols);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] = DOWN;
            }
        }
        setUp(moleNumber);

        int delayInSec = random.nextInt(3) + 3; // from 3 to 5

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                canRandomize = true;

            }
        }, delayInSec * 1000);
        canRandomize = false;

        return moleNumber;
    }
}
