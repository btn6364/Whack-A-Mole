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
    private volatile boolean gameEnded = false;

    public WAMGame(WAMPlayer[] players, int cols, int rows, int timeInSeconds) {
        this.wam = new WAM(rows, cols, timeInSeconds);
        this.players = players;
        this.time = timeInSeconds;
        this.row = rows;
        this.col = cols;
    }

    public WAMPlayer[] getPlayers() {
        return players;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    @Override
    public void run() {
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                gameEnded = true;
                // end game
                if (players.length == 1){
                    players[0].gameWon();
                    return;
                }
                ArrayList<Integer> scoreStorage = new ArrayList<Integer>();
                for (WAMPlayer player : players) {
                    int playerScore = player.getScorePoint();
                    scoreStorage.add(playerScore);
                }
                int max = 0;
                int min = 100000000;
                for (int idx = 0; idx < scoreStorage.size(); idx++) {
                    if (scoreStorage.get(idx) > max) {
                        max = scoreStorage.get(idx);
                    }
                    if (scoreStorage.get(idx) < min) {
                        min = scoreStorage.get(idx);
                    }
                }

                if (max == min) {
                    for (WAMPlayer player : players) {
                        player.gameTied();
                    }
                } else { //max > min
                    for (int i = 0; i < scoreStorage.size(); i++) {
                        if (scoreStorage.get(i) == max) {
                            players[i].gameWon();
                        }
                        if (scoreStorage.get(i) == min) {
                            players[i].gameLost();
                        }
                    }
                }
            }
        }).start();
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
            long starting = System.currentTimeMillis() * 1000;
            while (System.currentTimeMillis() - starting < time * 1000) {
                if (gameEnded) {
                    return;
                }
                try {
                    setDown(moleNum);
                    this.wait(randomDown() * 1000);
                    setUp(moleNum);
                    this.wait(randomUp() * 1000);
                } catch (InterruptedException ie) {
                    for (WAMPlayer player: players){
                        player.error(ie.getMessage());
                    }
                }
            }

        }
    }

}
