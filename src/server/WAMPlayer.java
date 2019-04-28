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
    private WAMGame game;
    private Scanner scanner;
    private PrintWriter printer;
    private int id;
    private int scorePoint;

    public WAMPlayer(Socket sock, int id) throws Exception {
        this.sock = sock;
        this.id = id;
        this.scorePoint = 0;
        try {
            scanner = new Scanner(sock.getInputStream());
            printer = new PrintWriter(sock.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void welcome(int rows, int col, int numOfPlayers, int playerIndex) {
        printer.println(WELCOME + " " + rows + " " + col + " " + numOfPlayers + " " + playerIndex);
    }


    public void moleUp(int moleNumber) {
        printer.println(MOLE_UP + " " + moleNumber);
    }

    public void moleDown(int moleNumber) {
        printer.println(MOLE_DOWN + " " + moleNumber);
    }

    public void gameWon() {
        printer.println(GAME_WON);
    }

    public void gameLost() {
        printer.println(GAME_LOST);
    }

    public void gameTied() {
        printer.println(GAME_TIED);
    }

    public void error(String message) {
        printer.println(ERROR + " " + message);
    }

    /**
     * @param moleNum
     * @param playerNum
     */
    public void whack(int moleNum, int playerNum) throws Exception {
//        if (game.isUp(moleNumber)) {
//            game.setDown(moleNumber);
//            //TODO add 2 to score
//        } else {
//            //TODO decrease score by 1
//        }
        String response = scanner.nextLine();
        if (response.startsWith(WHACK)){
            String[] tokens = response.split(" ");
            if (tokens.length == 3){
                //TODO
                int moleNumber = Integer.parseInt(tokens[1]);
                int playerNumber = Integer.parseInt(tokens[2]);
                if (game.isUp(moleNumber)){
                    game.setDown(moleNumber);
                    this.scorePoint += 2;
                } else {
                    System.out.println("Miss!");
                    this.scorePoint --;
                }

            } else {
                throw new Exception("Invalid player response: " + response);
            }
        } else {
            throw new Exception("Invalid player response: " + response);
        }
    }

    public void score(WAMPlayer[] players){
        //TODO

    }
    @Override
    public void close() {
        try {
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGame(WAMGame game) {
        this.game = game;
    }


    @Override
    public void run() {//?
        try {
            this.scanner = new Scanner(sock.getInputStream());
            this.printer = new PrintWriter(sock.getOutputStream(), true);
            String request = this.scanner.next();
            String[] argument = this.scanner.nextLine().trim().split(" ");
            if (request.equals(WHACK)) {
                //whack(Integer.parseInt(argument[0]), Integer.parseInt(argument[1]));
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
