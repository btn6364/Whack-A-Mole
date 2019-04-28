package server;

import java.util.ArrayList;
import java.util.Random;

/**
 * The class that represents a Whack-A-Mole game.
 * @author Bao Nguyen (btn6364@rit.edu)
 * @author Jared Sullivan (jms8376@rit.edu)
 */
public class WAMGame implements Runnable {
    /**
     * The array that contains all players.
     */
    private WAMPlayer[] players;

    /**
     * The Whack-A-Mole board.
     */
    private WAM wam;

    /**
     * An array that contains all Mole Thread
     */
    private ArrayList<MoleThread> moleThreads = new ArrayList<>();

    /**
     * The time of the game.
     */
    private int time;

    /**
     * The number of rows.
     */
    private int rows;

    /**
     * The number of columns.
     */
    private int cols;

    /**
     * A checker to check if the game has ended or not.
     */
    private volatile boolean gameEnded = false;

    /**
     * A constructor for the WAMPlayer.
     * @param players the array of all players.
     * @param cols the number of columns.
     * @param rows the number of rows.
     * @param timeInSeconds the time of the game.
     */
    public WAMGame(WAMPlayer[] players, int cols, int rows, int timeInSeconds) {
        this.wam = new WAM(rows, cols);
        this.players = players;
        this.time = timeInSeconds;
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Get the array of all players
     * @return the array of all players
     */
    public WAMPlayer[] getPlayers() {
        return players;
    }

    /**
     * Check if the game has ended.
     * @return true if the game has ended and false otherwise.
     */
    public boolean isGameEnded() {
        return gameEnded;
    }

    /**
     * Run the game.
     * When the time is up. Check who is the winner and sends result.
     */
    @Override
    public void run() {
        for (WAMPlayer player : players) {
            Thread thread = new Thread(player);
            thread.start();
        }
        int size = rows * cols;
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


    /**
     * Check if a mole is up.
     * @param moleNumber the mole number
     * @return
     */
    public boolean isUp(int moleNumber) {
        return wam.isUp(moleNumber);
    }

    /**
     * Set a mole up
     * @param moleNumber the mole number
     */
    public void setUp(int moleNumber) {
        wam.setUp(moleNumber);
        for (WAMPlayer player : players) {
            player.moleUp(moleNumber);
        }
    }

    /**
     * Set a mole down.
     * @param moleNumber the mole number.
     */
    public void setDown(int moleNumber) {
        wam.setDown(moleNumber);
        for (WAMPlayer player : players) {
            player.moleDown(moleNumber);
        }
    }

    /**
     * Randomize the time for a mole to be up
     * @return the time for a mole to be up. (3-5 seconds).
     */
    public int randomUp() {
        Random r = new Random();
        int low = 3;
        int high = 5;
        return r.nextInt(high - low) + low;
    }

    /**
     * Randomize the time for a mole to be down.
     * @return the time for a mole to be down. (2-10 seconds).
     */
    public int randomDown() {
        Random r = new Random();
        int low = 2;
        int high = 10;
        return r.nextInt(high - low) + low;
    }

    /**
     * A class that represents each mole as a thread.
     */
    public class MoleThread implements Runnable {
        private int moleNum;

        public MoleThread(int moleNum) {
            this.moleNum = moleNum;
        }

        public void close() {
            this.close();
        }

        /**
         * A run function for each mole.
         */
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
