package server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static common.WAMProtocol.*;

/**
 * A class that manages the requests and responses to a single client.
 * @author Bao Nguyen (btn6364@rit.edu)
 * @author Jared Sullivan (jms8376@rit.edu)
 */
public class WAMPlayer implements Closeable, Runnable {
    /**
     * The socket used to communicate with the client.
     */
    private Socket sock;

    /**
     * An instance of the Whack-A-Mole game.
     */
    private WAMGame game;

    /**
     * The scanner used to read responses from the client.
     */
    private Scanner scanner;

    /**
     * The printer used to send requests to the client.
     */
    private PrintWriter printer;

    /**
     * The id of the player.
     */
    private int id;

    /**
     * The score of the player.
     */
    private int scorePoint;

    /**
     * Constructor of a WAMPlayer.
     * @param sock the socket.
     * @param id the id of the player.
     * @throws Exception IO Exception.
     */
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

    /**
     * Get the scanner.
     * @return the scanner.
     */
    public Scanner getScanner() {
        return this.scanner;
    }

    /**
     * Send a welcome message from the server to the client.
     * @param rows the number of rows.
     * @param col the number of columns.
     * @param numOfPlayers the number of players.
     * @param playerIndex the player index.
     */
    public void welcome(int rows, int col, int numOfPlayers, int playerIndex) {
        printer.println(WELCOME + " " + rows + " " + col + " " + numOfPlayers + " " + playerIndex);
    }

    /**
     * Notify a client that a mole is up.
     * @param moleNumber the mole number.
     */
    public void moleUp(int moleNumber) {
        printer.println(MOLE_UP + " " + moleNumber);
    }

    /**
     * Notify a client that a mole is down.
     * @param moleNumber the mole number.
     */
    public void moleDown(int moleNumber) {
        printer.println(MOLE_DOWN + " " + moleNumber);
    }

    /**
     * Notify a client if he won the game.
     */
    public void gameWon() {
        printer.println(GAME_WON);
    }

    /**
     * Notify a client if he lost the game.
     */
    public void gameLost() {
        printer.println(GAME_LOST);
    }

    /**
     * Notify a client if the game was a tie.
     */
    public void gameTied() {
        printer.println(GAME_TIED);
    }

    /**
     * Notify a client if an error occured.
     */
    public void error(String message) {
        printer.println(ERROR + " " + message);
    }

    /**
     * Update score if the player whack an up mole.
     * @param moleNumber the mole number.
     * @param playerNumber the player that whacked the mole.
     */
    public void whack(int moleNumber, int playerNumber) {
        if (game.isUp(moleNumber)) {
            game.setDown(moleNumber);
            this.scorePoint += 2;
        } else {
            this.scorePoint--;
        }
    }

    /**
     * Send the score to all players.
     * @param players
     */
    public void sendScore(WAMPlayer[] players) {
        String message = SCORE;
        for (WAMPlayer player : players) {
            message += " " + player.scorePoint;
        }
        for (WAMPlayer player : players) {
            printer.println(message);
        }
    }

    /**
     * Get score points.
     * @return
     */
    public int getScorePoint() {
        return scorePoint;
    }

    /**
     * Close the connection.
     */
    @Override
    public void close() {
        try {
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the game
     * @param game the game to set.
     */
    public void setGame(WAMGame game) {
        this.game = game;
    }

    /**
     * The client sends a WHACK message to the server.
     * The server will check and do the score update.
     */
    @Override
    public void run() {
        while (!game.isGameEnded()) {
            try {
                String request = this.scanner.next();
                String[] arguments = this.scanner.nextLine().trim().split("\\s+");
                if (request.equals(WHACK)) {
                    int moleNumber = Integer.parseInt(arguments[0]);
                    int playerNumber = Integer.parseInt(arguments[1]);
                    whack(moleNumber, playerNumber);
                    sendScore(game.getPlayers());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}