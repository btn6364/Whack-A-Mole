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

    public Scanner getScanner(){
        return this.scanner;
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
     * @param moleNumber
     * @param playerNumber
     */
    public void whack(int moleNumber, int playerNumber) {
        if (game.isUp(moleNumber)) {
            game.setDown(moleNumber);
            this.scorePoint += 2;
        } else {
            this.scorePoint--;
        }
        System.out.println("Current score:" + this.scorePoint);
    }

    public void sendScore(WAMPlayer[] players) {
        //TODO
        String message = SCORE;
        for (WAMPlayer player : players) {
            message += " " + player.scorePoint;
        }
        for (WAMPlayer player : players){
            printer.println(message);
        }
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
    public void run() {
        boolean check = true;
        while (check) {
            String request = this.scanner.next();
            String[] arguments = this.scanner.nextLine().trim().split("\\s+");
            if (request.equals(WHACK)) {
                int moleNumber = Integer.parseInt(arguments[0]);
                int playerNumber = Integer.parseInt(arguments[1]);
                whack(moleNumber, playerNumber);
                sendScore(game.getPlayers());
            }
        }
    }
}