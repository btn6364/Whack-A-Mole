package server;

public class WAMGame implements Runnable{

    private WAMPlayer playerOne;
    private WAMPlayer playerTwo;
    private WAMPlayer playerThree;
    private WAM game; //TODO

    public WAMGame(WAMPlayer playerOne, WAMPlayer playerTwo,
                   WAMPlayer playerThree)
    {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.playerThree = playerThree;

    }

    @Override
    public void run()
    {

    }
}
