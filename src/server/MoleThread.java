package server;

import java.util.Random;

/**
 * @author Jared Sullivan (jms8376@rit.edu)
 */
public class MoleThread implements Runnable
{
    int id;
    boolean up;
    WAMGame game;

    public MoleThread()
    {
        //todo make id and stuff
    }

    public int randomDown() {
        Random r = new Random();
        int low = 5;
        int high = 10;
        return r.nextInt(high-low) + low;
    }

    public int randomUp(){
        Random r = new Random();
        int low = 2;
        int high = 5;
        return r.nextInt(high-low) + low;
    }


    public void run(){
        while(true)
        {
            int randUp = randomUp();
            int randDown = randomDown();

        }
    }

    public boolean isUp(){
        return up;
    }

    public void setDown(){
        //TODO need to actually set mole to down in the game/board
        this.up = false;
        game.setUp(this.id);
    }

    public void setUp(){
        //TODO need to actually set the mole to up in the game/board
        this.up = true;
    }
}
