package client.gui;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import common.WAMProtocol;

/**
 * The client side network interface to a Whack-A-Mole game server.
 *
 * @author Jared Sullivan (jms8376@rit.edu)
 * @author Bao Nguyen (btn6364@rit.edu)
 */
public class WAMClient {
    /**
     * Client socket to communicate with server
     */
    private Socket clientSocket;
    /**
     * Used to read requests from the server
     */
    private Scanner networkIn;
    /**
     * Used to write responses to the server.
     */
    private PrintStream networkOut;
    /**
     * Sentinel loop used to control the main loop
     */
    private boolean go;
    /**
     * The model which keeps track of the game
     */
    private WAMBoard board;
    /**
     * The tokens to store the value from the WELCOME message
     */
    private String[] tokens;

    /**
     * The row that can be obtained from the welcome message
     */
    private int rowMsg;

    /**
     * The column that can be obtained from the welcome message
     */
    private int colMsg;

    /**
     * The number of players
     */
    private int numberOfPlayers;

    /**
     * The player index
     */
    private int playerNumber;

    /**
     * The score of the player
     */
    private int myScore;

    /**
     * The game result type
     */
    private int gameResult;

    protected static final int RESULT_ONGOING = 0;
    /**
     * The variable used for winning result.
     */
    protected static final int RESULT_WON = 1;

    /**
     * The variable used for losing result.
     */
    protected static final int RESULT_LOST = 2;

    /**
     * The variable used for tied result.
     */
    protected static final int RESULT_TIED = 3;

    /**
     * The variable used for error message.
     */
    protected static final int RESULT_ERROR = 4;
    /**
     * Accessor that takes multithreaded access into account
     *
     * @return whether it ok to continue or not
     */
    private synchronized boolean isGo() {
        return this.go;
    }

    /**
     * Multithread-safe mutator
     */
    private synchronized void stop() {
        this.go = false;
    }

    /**
     * Hook up with a Whack-A-Mole game server already running and waiting for
     * the player to connect.
     *
     * @param host the name of the host running the server program
     * @param port the port of the server socket on which the server is listening
     * @throws Exception If there is a problem opening the connection
     */
    public WAMClient(String host, int port) {
        try {
            this.clientSocket = new Socket(host, port);
            this.networkIn = new Scanner(clientSocket.getInputStream());
            this.networkOut = new PrintStream(clientSocket.getOutputStream());
            this.go = true;
            this.myScore = 0;
            //Block waiting for the WELCOME message from the server
            String request = this.networkIn.nextLine();
            this.tokens = request.split("\\s");
            if (!tokens[0].equals(WAMProtocol.WELCOME)) {
                throw new Exception("Expected WELCOME message from the server");
            }
            this.rowMsg = Integer.parseInt(tokens[1]);
            this.colMsg = Integer.parseInt(tokens[2]);
            this.board = new WAMBoard(rowMsg, colMsg);
            this.numberOfPlayers = Integer.parseInt(tokens[3]);
            this.playerNumber = Integer.parseInt(tokens[4]);
            System.out.println("Connected to server " + this.clientSocket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the board
     * @return the board
     */
    public WAMBoard getBoard() {
        return board;
    }

    /**
     * Get the game result
     * @return the game result
     */
    public int getGameResult(){
        return gameResult;
    }

    /**
     * Called from the GUI when it is ready to start receiving messages
     * from the server.
     */
    public void startListener() {
        new Thread(() -> this.run()).start();
    }

    /**
     * Get the row number from the welcome message
     *
     * @return the row number from the welcome message
     */
    public int getRow() {
        return rowMsg;
    }

    /**
     * Get the column number from the welcome message
     *
     * @return the column number from the welcome message
     */
    public int getColumn() {
        return colMsg;
    }

    /**
     * This method should be called at the end of the game to
     * close the client connection.
     */
    public void close() {
        try {
            this.clientSocket.close();
        } catch (IOException ioe) {

        }
        this.board.close();
    }

    /**
     * A MoleThread is going up
     *
     * @param arguments the MoleThread number in String type
     */
    public void moleUp(String arguments) {
        int moleNumber = Integer.parseInt(arguments);
        int row = moleNumber / colMsg;
        int col = moleNumber % colMsg;
        this.board.setContents(col, row, 1);
        this.board.alertObservers();
    }

    /**
     * A MoleThread is going down
     *
     * @param arguments the MoleThread number in String type
     */
    public void moleDown(String arguments) {
        int moleNumber = Integer.parseInt(arguments);
        int row = moleNumber / colMsg;
        int col = moleNumber % colMsg;
        this.board.setContents(col, row, 0);
        this.board.alertObservers();
    }

    /**
     * Send a WHACK message from the client to the server
     * @param moleNumber the mole number to send
     */
    public void whack(int moleNumber) {
        networkOut.println(WAMProtocol.WHACK + " " + moleNumber + " " + playerNumber);
    }

    /**
     * Update the score of the player.
     * @param arguments
     */
    public void updateScore(String arguments){
        String[] tokens = arguments.split("\\s+");
        this.myScore = Integer.parseInt(tokens[this.playerNumber-1]);
        this.board.alertObservers();
    }

    /**
     * Get the score of the player.
     * @return the score of the player.
     */
    public int getMyScore(){
        return myScore;
    }

    /**
     * Update the result of the game.
     * @param result the result of the game.
     */
    public void updateResult(int result) {
        this.gameResult = result;
        this.board.alertObservers();
    }

    /**
     * Run the main client loop. Intended to be started as a separate
     * thread internally. This method is made private so that no one
     * outside will call it or try to start a thread on it.
     */
    private void run() {
        while (this.isGo()) {
            try {
                String request = this.networkIn.next();
                String arguments = this.networkIn.nextLine().trim();
                System.out.println("Network in message = \"" + request + '"');

                switch (request) {
                    case WAMProtocol.MOLE_UP:
                        moleUp(arguments);
                        break;
                    case WAMProtocol.MOLE_DOWN:
                        moleDown(arguments);
                        break;
                    case WAMProtocol.SCORE:
                        updateScore(arguments);
                        break;
                    case WAMProtocol.GAME_WON:
                        updateResult(RESULT_WON);
                        this.stop();
                        break;
                    case WAMProtocol.GAME_LOST:
                        updateResult(RESULT_LOST);
                        this.stop();
                        break;
                    case WAMProtocol.GAME_TIED:
                        updateResult(RESULT_TIED);
                        this.stop();
                        break;
                    case WAMProtocol.ERROR:
                        updateResult(RESULT_ERROR);
                        this.stop();
                        break;
                }
            } catch (Exception e) {
                this.stop();
            }
        }
        this.close();
    }

}
