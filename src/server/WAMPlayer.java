package server;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import static common.WAMProtocol.*;

public class WAMPlayer implements Closeable {

    private Socket sock;

    private Scanner scanner;

    private PrintWriter printer;

    public WAMPlayer(Socket sock) throws Exception{
        this.sock = sock;
        try {
            scanner = new Scanner(sock.getInputStream());
            printer = new PrintWriter(sock.getOutputStream());
        } catch (IOException e){
            System.err.println(e);
        }
    }

    public void moleUp(int moleNumber){
        printer.println(moleNumber);
        printer.flush();
    }

    public void moleDown(int moleNumber){
        printer.println(moleNumber);
        printer.flush();
    }

    public void gameWon(){
        printer.println(GAME_WON);
        printer.flush();
    }

    public void gameLost(){
        printer.println(GAME_LOST);
        printer.flush();
    }

    public void gameTied(){
        printer.println(GAME_TIED);
        printer.flush();
    }

    public void errorDisplay(String message){
        printer.println(ERROR + " " + message);
        printer.flush();
    }

    @Override
    public void close(){
        try {
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
