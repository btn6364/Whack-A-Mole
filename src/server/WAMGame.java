package server;

public class WAMGame implements Runnable{
    private WAMPlayer[] players;
    private int TIME;
    private WAM game;
    private int COLS;
    private int ROWS;

    public WAMGame(WAMPlayer[] players, int cols, int rows, int time){
        this.game = new WAM(rows, cols);
        this.players = players;
        this.TIME = time;
        this.COLS = cols;
        this.ROWS = rows;
    }

    @Override
    public void run(){
        boolean go = true;
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


}
