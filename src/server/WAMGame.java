package server;

import common.WAMProtocol;

public class WAMGame implements Runnable{
    private WAMPlayer[] players;
    private int time;
    private WAM wam;
    private int cols;
    private int rows;
    private Thread[] threads;

    public WAMGame(WAMPlayer[] players, int cols, int rows, int time){
        this.wam = new WAM(rows, cols);
        this.players = players;
        this.threads = new Thread[players.length];
        this.time = time;
        this.cols = cols;
        this.rows = rows;
    }

    @Override
    public void run(){
        boolean go = true;
        for(int i = 0; i < threads.length; i++)
        {
            threads[i] = new Thread(players[i]);
        }
        while(go)
        {

        }
        for(WAMPlayer player:players)
        {
            player.close();
        }
    }

    public boolean timeNotUp(int time)
    {
        //TODO
        return false;
    }

    public boolean isUp(int moleNumber)
    {
        return wam.isUp(moleNumber);
    }

    public void setUp(int moleNumber)
    {
        wam.setUp(moleNumber);
        for(WAMPlayer player:players)
        {
            player.moleUp(moleNumber);
        }
    }

    public void setDown(int moleNumber)
    {
        wam.setDown(moleNumber);
        for(WAMPlayer player:players)
        {
            player.moleDown(moleNumber);
        }
    }
}
