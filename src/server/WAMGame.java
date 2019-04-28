package server;

public class WAMGame implements Runnable {
    private WAMPlayer[] players;
    private WAM wam;

    public WAMGame(WAMPlayer[] players, int cols, int rows, int timeInSeconds) {
        this.wam = new WAM(rows, cols, timeInSeconds);
        this.players = players;
    }

    @Override
    public void run() {
        boolean go = true;
        while (go) {
            int randomMoleNumber = wam.randomizeUp();
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
