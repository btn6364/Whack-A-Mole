package server;

public class WAMGame implements Runnable{
    private WAMPlayer playerOne;

    private WAM game;

    public WAMGame(WAMPlayer playerOne){
        this.playerOne = playerOne;

        this.game = new WAM();
    }

    @Override
    public void run(){

    }
}
