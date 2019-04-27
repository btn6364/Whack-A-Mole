package server;

import common.WAMProtocol;

public class WAMGame implements Runnable{
    private WAMPlayer[] players;
    private int TIME;
    private WAM wam;
    private int COLS;
    private int ROWS;
    private Thread[] threads;

    public WAMGame(WAMPlayer[] players, int cols, int rows, int time){
        this.wam = new WAM(rows, cols);
        this.players = players;
        this.threads = new Thread[players.length];
        this.TIME = time;
        this.COLS = cols;
        this.ROWS = rows;
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
