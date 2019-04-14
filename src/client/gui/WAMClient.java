package client.gui;

import java.io.IOException;
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
    private boolean go;
    private WAMBoard board;
    private String[] tokens;

    private synchronized boolean isGo() {return this.go;}

    private synchronized void stop() {this.go = false;}



    public WAMClient(String host, int port, WAMBoard board)
    {
        try {
            this.clientSocket = new Socket(host, port);
            this.networkIn = new Scanner(clientSocket.getInputStream());
            this.networkOut = new PrintStream(clientSocket.getOutputStream());
            this.board = board;
            this.go = true;
            //Block waiting for the WELCOME message from the server
            String request = this.networkIn.next();
            tokens = request.split(" ");
            if (!tokens[0].equals(WAMProtocol.WELCOME)){
                throw new Exception("Expected WELCOME message from the server");
            }
            System.out.println("Connected to server " + this.clientSocket );

        } catch (Exception e){
            e.printStackTrace();
        }



    }

    public void startListener() throws Exception{
        new Thread(() -> this.run()).start();
    }

    public int getRow(){
        int row = Integer.parseInt(tokens[1]);
        return row;
    }

    public int getColumn(){
        int col = Integer.parseInt(tokens[2]);
        return col;
    }


    public void close(){
        try{
            this.clientSocket.close();
        } catch (IOException ioe){

        }
        this.board.close();
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


    private void run() {
        while (this.isGo()) {
            try {
                String request = this.networkIn.next();
                String arguments = this.networkIn.nextLine().trim();
                System.out.println("Network in message = \"" + request + '"');

                switch (request) {
                    case WAMProtocol.WELCOME:
                        //TODO
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
            } catch (Exception e){
                this.stop();
            }
        }
        this.close();
    }


}
