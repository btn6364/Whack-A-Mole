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
    private boolean canRandomizeUp;
    private boolean canRandomizeDown;


    public WAM(int rows, int cols, int timeInSeconds) {
        this.rows = rows;
        this.cols = cols;
        this.timeInSeconds = timeInSeconds;
        this.canRandomizeUp = true;
        this.canRandomizeDown = true;
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

    public int randomizeUp() {
        if (!canRandomizeUp) {
            System.out.println();
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
                canRandomizeUp = true;
            }
        }, delayInSec * 1000);
        canRandomizeUp = false;

        return moleNumber;
    }

    public int randomizeDown(){
        if (!canRandomizeDown){
            System.out.println();
            return -1;
        }

        Random random = new Random();
        int moleNumber = random.nextInt(rows * cols);
        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                board[row][col] = UP;
            }
        }
        setDown(moleNumber);

        int delayInSec = random.nextInt(9) + 2; // from 3 to 5
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                canRandomizeDown = true;
            }
        }, delayInSec * 1000);
        canRandomizeDown = false;

        return moleNumber;

    }



}
