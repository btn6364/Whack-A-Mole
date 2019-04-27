package server;

import common.WAMProtocol;

public class WAMGame implements Runnable {
    private WAMPlayer[] players;
    private WAM wam;
//    private Thread[] threads; not necessary

    public WAMGame(WAMPlayer[] players, int cols, int rows, int timeInSeconds) {
        this.wam = new WAM(rows, cols, timeInSeconds);
        this.players = players;
//        this.threads = new Thread[players.length]; not necessary
    }

    @Override
    public void run() {
        boolean go = true;
//        for (int i = 0; i < threads.length; i++) {
//            threads[i] = new Thread(players[i]);
//        } not necessary since created the object without running it.
        while (go) {
            int randomMoleNumber = wam.randomize();
            if (randomMoleNumber >= 0) {
                for (WAMPlayer player : players) {
                    player.moleUp(randomMoleNumber);
                }
            }
            // check game.hasWon().....
        }
        for (WAMPlayer player : players) {
            player.close();
        }
    }

    public boolean timeNotUp(int time) {
        //TODO
        return false;
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
}
