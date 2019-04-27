package server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import static common.WAMProtocol.*;

/**
 * A class that manages the requests and responses to a single client.
 */
public class WAMPlayer implements Closeable, Runnable {

    private Socket sock;

    private Scanner scanner;
    private PrintWriter printer;

    public WAMPlayer(Socket sock) throws Exception{
        this.sock = sock;
        try {
            scanner = new Scanner(sock.getInputStream());
            printer = new PrintWriter(sock.getOutputStream(),true);
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void welcome(int rows, int col, int numOfPlayers, int playerIndex){
        printer.println(WELCOME + " " + rows + " " + col + " " + numOfPlayers + " " + playerIndex);
    }


    public void moleUp(int moleNumber){
        printer.println(MOLE_UP + " " + moleNumber);
    }

    public void moleDown(int moleNumber){
        printer.println(MOLE_DOWN + " " + moleNumber);
    }

    public void gameWon(){
        printer.println(GAME_WON);
    }

    public void gameLost(){
        printer.println(GAME_LOST);
    }

    public void gameTied(){
        printer.println(GAME_TIED);
    }

    public void error(String message){
        printer.println(ERROR + " " + message);
    }

    /**
     *
     * @param moleNumber
     * @param playerNumber
     */
    public void whack(int moleNumber, int playerNumber) {

    }
    @Override
    public void close(){
        try {
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        try
        {
            this.scanner = new Scanner(sock.getInputStream());
            this.printer = new PrintWriter(sock.getOutputStream(), true);
            String request = this.scanner.nextLine();

        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
