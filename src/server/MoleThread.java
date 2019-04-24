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
            int randomUp = randomUp();
        }
    }

    public boolean isUp(){
        return up;
    }

    public void setDown(){
        this.up = false;

    }

    public void setUp(){
        this.up = true;
    }
}
