package server;

public class WAMGame implements Runnable{
    private WAMPlayer[] players;

    private int time;
    private WAM game;

    public WAMGame(WAMPlayer[] players, int cols, int rows, int time){
        this.game = new WAM(rows, cols);
        this.players = players;
        this.time = time;
    }

    @Override
    public void run(){

    }


}
