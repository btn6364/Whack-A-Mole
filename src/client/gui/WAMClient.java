package client.gui;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
    private boolean go;
    private WAMBoard board;
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


        tokens = this.networkIn.nextLine().split(" ");
        if(!tokens[0].equals(WAMProtocol.WELCOME))
        {
            throw new Exception("Expected WELCOME from server");
        }
        System.out.println("Connected to server " + this.clientSocket);
        String arguments = this.networkIn.nextLine();
    }

    public void startListener() throws Exception{
        new Thread(() -> this.run()).start();
    }

    public String welcome(String arguments){
        return arguments;
    }

    public void moleUp(String arguments){
        int moleNumber = Integer.parseInt(arguments);
        int row = moleNumber / WAMBoard.COLS;
        int col = moleNumber % WAMBoard.ROWS;
        this.board.setContents(col, row, 1 );
        this.board.alertObservers();
    }

    public void moleDown(String arguments){
        int moleNumber = Integer.parseInt(arguments);
        int row = moleNumber / WAMBoard.COLS;
        int col = moleNumber % WAMBoard.ROWS;
        this.board.setContents(col, row, 0);
        this.board.alertObservers();
    }


    private void run()
    {
        while (this.isGo())
        {
            String request = this.networkIn.next();
            String arguments = this.networkIn.nextLine().trim();
            String[] argumentArray = arguments.split(" ");
            System.out.println("Network in message = \"" + request + '"');
            switch (request)
            {
                case WAMProtocol.WELCOME:
                    welcome(arguments);
                    break;
                case WAMProtocol.MOLE_UP:
                    moleUp(arguments);
                    break;
                case WAMProtocol.MOLE_DOWN:
                    moleDown(arguments);
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

    public static void main(String[] args) throws Exception
    {
//        WAMClient client = new WAMClient(args[0], Integer.parseInt(args[1]));
//        client.startListener();
    }


}
