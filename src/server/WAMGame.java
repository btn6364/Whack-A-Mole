package server;

public class WAMGame implements Runnable{
    private WAMPlayer[] players;
    private int TIME;
    private WAM game;

    public WAMGame(WAMPlayer[] players, int cols, int rows, int time){
        this.game = new WAM(rows, cols);
        this.players = players;
        this.TIME = time;
    }

    @Override
    public void run(){
        boolean go = true;
        while(go)
        {
            if(!timeNotUp(TIME))
            {
                go = false;
            }
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
