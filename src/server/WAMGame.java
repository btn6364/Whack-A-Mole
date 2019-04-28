package server;

import java.util.ArrayList;
import java.util.Random;

public class WAMGame implements Runnable {
    private WAMPlayer[] players;
    private WAM wam;
    private ArrayList<MoleThread> moleThreads = new ArrayList<>();
    private int time;
    private int row;
    private int col;

    public WAMGame(WAMPlayer[] players, int cols, int rows, int timeInSeconds) {
        this.wam = new WAM(rows, cols, timeInSeconds);
        this.players = players;
        this.time = timeInSeconds;
        this.row = rows;
        this.col = cols;
    }

    public WAMPlayer[] getPlayers(){
        return players;
    }

    @Override
    public void run() {
        System.out.println("start game");
        for (WAMPlayer player : players) {
            Thread thread = new Thread(player);
            thread.start();
        }
        int size = row * col;
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            MoleThread moleThread = new MoleThread(i);
            moleThreads.add(moleThread);
            Thread thread = new Thread(moleThread);
            thread.start();
        }
    }


    public boolean isUp(int moleNumber) {
        return wam.isUp(moleNumber);
    }

    public void setUp(int moleNumber) {
        wam.setUp(moleNumber);
        for (WAMPlayer player : players) {
            player.moleUp(moleNumber);
        }
    }

    public void setDown(int moleNumber) {
        wam.setDown(moleNumber);
        for (WAMPlayer player : players) {
            player.moleDown(moleNumber);
        }
    }

    public int randomUp() {
        Random r = new Random();
        int low = 2;
        int high = 5;
        return r.nextInt(high - low) + low;
    }

    public int randomDown() {
        Random r = new Random();
        int low = 5;
        int high = 10;
        return r.nextInt(high - low) + low;
    }

    public class MoleThread implements Runnable {
        private int moleNum;

        public MoleThread(int moleNum) {
            this.moleNum = moleNum;
        }

        public void close() {
            this.close();
        }

        @Override
        public synchronized void run() {
            Long starting = System.currentTimeMillis() * 1000;
            while (System.currentTimeMillis() - starting < time * 1000) {
                try {
                    setDown(moleNum);
                    this.wait(randomDown() * 1000);
                    setUp(moleNum);
                    this.wait(randomUp() * 1000);
                } catch (InterruptedException ie) {
                    System.err.println("Error");
                }
            }
        }
    }

}
