package server;

import java.util.ArrayList;
import java.util.Random;

public class WAMGame implements Runnable {
    private WAMPlayer[] players;
    private WAM wam;
    private ArrayList<MoleThread> moleThreads = new ArrayList<>();
    private int time;
//    private Thread[] threads; not necessary

    public WAMGame(WAMPlayer[] players, int cols, int rows, int timeInSeconds) {
        this.wam = new WAM(rows, cols, timeInSeconds);
        this.players = players;
        this.time = timeInSeconds;
//        this.threads = new Thread[players.length]; not necessary
    }

    @Override
    public synchronized void run() {
//        for (int i = 0; i < threads.length; i++) {
//            threads[i] = new Thread(players[i]);
//        } not necessary since created the object without running it.
        Long startingTime = System.currentTimeMillis();
        int size = wam.getCols() * wam.getRows();
        for(int i = 0; i < size; i++)
        {
            MoleThread moleThread = new MoleThread(i);
            moleThreads.add(moleThread);
        }
        for(MoleThread m:moleThreads)
        {
            m.run();
        }
        while (System.currentTimeMillis() - startingTime < time * 1000) {
            int randomMoleNumber = wam.randomize();

            // check game.hasWon().....
//
//        for (WAMPlayer player : players) {
//            player.close();
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

    public int randomUp(){
        Random r = new Random();
        int low = 2;
        int high = 5;
        return r.nextInt(high-low) + low;
    }

    public int randomDown() {
        Random r = new Random();
        int low = 5;
        int high = 10;
        return r.nextInt(high-low) + low;
    }

    public class MoleThread implements Runnable
    {
        private int moleNum;

        public MoleThread(int moleNum)
        {
            this.moleNum = moleNum;
        }

        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    setUp(moleNum);

                    // this.wait(randomUp() * 1000);
                    setDown(moleNum);
                    // this.wait(randomDown() * 1000);

                } catch (Exception ie)
                {
                    System.err.println();
                }
            }
        }
    }

}
