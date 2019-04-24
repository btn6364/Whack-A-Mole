package server;

/**
 * @author Jared Sullivan (jms8376@rit.edu)
 */
public class MoleThread implements Runnable
{
    int id;

    boolean up;

    public void run(){

    }

    public boolean isUp(){
        return up;
    }

    public void setDown(){
        this.up = false;
    }

    public void setUp(){
        this.up = false;
    }
}
