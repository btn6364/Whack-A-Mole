package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The {@link WAMServer} waits for incoming client connections and
 * pairs them off to play {@link WAMGame games}.
 *
 * @author Jared Sullivan (jms8376@rit.edu)
 * @author Bao Nguyen (btn6364@rit.edu)
 */
public class WAMServer implements Runnable {
    /**
     * Used to wait for incoming message.
     */
    private ServerSocket server;

    /**
     * The number of rows.
     */
    private int rows;

    /**
     * The number of columns.
     */
    private int columns;

    /**
     * The number of players.
     */
    private int numPlayers;

    private String[] args;

    /**
     * The time game.
     */
    private int gameTime;

    /**
     * Creates a new WAMServer that listens for incoming connections on a
     * specified port
     *
     * @throws Exception If there is an error creating the ServerSocket
     */
    public WAMServer(String[] args) throws Exception {
        this.server = new ServerSocket(Integer.parseInt(args[0]));
        this.rows = Integer.parseInt(args[1]);
        this.columns = Integer.parseInt(args[2]);
        this.numPlayers = Integer.parseInt(args[3]);
        this.gameTime = Integer.parseInt(args[4]);
    }

    /**
     * Waits for two clients to connect. Creates a {@link WAMPlayer}
     * for each and then pairs them off in a {@link WAMGame}.<P>
     */

    @Override
    public void run() {
        int numPlayersInGame = 1;
        WAMPlayer[] players = new WAMPlayer[numPlayers];
        while (numPlayersInGame <= numPlayers) {
            try {
                System.out.println("Waiting for player " + numPlayersInGame + "...");
                Socket playerSocket = server.accept();
                WAMPlayer player = new WAMPlayer(playerSocket, numPlayersInGame - 1);
                players[numPlayersInGame - 1] = player;
                player.welcome(rows, columns, numPlayers, numPlayersInGame);
                System.out.println("Player " + numPlayersInGame + " connected");
            } catch (IOException ie) {
                System.err.println("Something bad has happened!!!");
                ie.printStackTrace();
            } catch (Exception e) {
                System.err.println("Failed to create the player!");
                e.printStackTrace();
            }
            numPlayersInGame++;
        }
        System.out.println("Starting game!");
        WAMGame game = new WAMGame(players, columns, rows, gameTime);
        for (WAMPlayer player : players) {
            player.setGame(game);
        }
        new Thread(game).run();

    }

    /**
     * The main function to run the server.
     * @param args the arguments that contains all the information for the welcom message
     * @throws Exception if there is a problem with the server.
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 5)
        {
            System.out.println("Usage: port#  #rows  #columns  #players  game-duration-seconds");
            System.exit(-1);
        }
        WAMServer wamServer = new WAMServer(args);
        wamServer.run();
    }

}
