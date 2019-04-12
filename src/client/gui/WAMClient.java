package client.gui;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import common.WAMProtocol;

/**
 * @author Jared Sullivan (jms8376@rit.edu)
 * @author Bao Nguyen (btn6364@rit.edu)
 */
public class WAMClient
{
    private Socket clientSocket;
    private Scanner networkIn;
    private PrintStream networkOut;
    private WAMBoard board;
    private boolean go;
    private String[] tokens;

    private synchronized boolean isGo() {return this.go;}

    private synchronized void stop() {this.go = false;}

    public WAMClient(String host, int port, WAMBoard board) throws Exception
    {
        this.clientSocket = new Socket(host, port);
        this.networkIn = new Scanner(clientSocket.getInputStream());
        this.networkOut = new PrintStream(clientSocket.getOutputStream());
        this.board = board;
        this.go = true;

        //
        tokens = this.networkIn.nextLine().split(" ");

    }

    public void startListener() throws Exception{
        new Thread(() -> this.run()).start();
    }

    public void welcome(String arguments)
    {

    }

    private void run()
    {
        while (this.isGo())
        {
            String request = this.networkIn.next();
            String arguments = this.networkIn.nextLine().trim();
            System.out.println("Network in message = \"" + request + '"');

            switch (request)
            {
                case WAMProtocol.WELCOME:
                    //TODO
                    break;
                case WAMProtocol.MOLE_UP:
                    //TODO
                    break;
                case WAMProtocol.MOLE_DOWN:
                    //TODO
                    break;
                case WAMProtocol.SCORE:
                    //TODO
                    break;
                case WAMProtocol.GAME_WON:
                    //TODO
                    break;
                case WAMProtocol.GAME_LOST:
                    //TODO
                    break;
                case WAMProtocol.GAME_TIED:
                    //TODO
                    break;
                case WAMProtocol.ERROR:
                    //TODO
                    break;
            }
        }
    }



}
