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
    private boolean canRandomize;


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

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }

    public int randomize() {
        if (!canRandomize) {
            System.out.println("CANNOT randomize");
            return -1;
        }

        Random random = new Random();
        int moleNumber = random.nextInt(rows*cols);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] = DOWN;
            }
        }
        setUp(moleNumber);

        Random r = new Random();
        int low = 2;
        int high = 5;
        int delayInSec = r.nextInt(high-low) + low;

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
