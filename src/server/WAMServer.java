package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Jared Sullivan (jms8376@rit.edu)
 * @author Bao Nguyen (btn6364@rit.edu)
 */
public class WAMServer implements Runnable
{
    //Used to wait for incoming connections
    private ServerSocket server;

    private int rows;

    private int columns;

    private int numPlayers;

    private String[] args;

    private int time;

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
        this.time = Integer.parseInt(args[4]);
    }


    public static void main(String[] args) throws Exception {
        if (args.length != 5)
        {
            System.out.println("Usage: port#  #rows  #columns  #players  game-duration-seconds");
            System.exit(-1);
        }
        WAMServer wamServer = new WAMServer(args);
        wamServer.run();
    }



    @Override
    public void run() {
        int numPlayersInGame = 1;
        WAMPlayer[] players = new WAMPlayer[numPlayers];

        while(numPlayersInGame <= numPlayers) {
            try {
                System.out.println("Waiting for player "+numPlayersInGame+"...");
                Socket playerSocket = server.accept();
                WAMPlayer player = new WAMPlayer(playerSocket, numPlayersInGame-1);
                players[numPlayersInGame-1] = player;
                player.welcome(rows, columns, numPlayers, numPlayersInGame);
                System.out.println("Player "+numPlayersInGame+" connected");
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
        WAMGame game = new WAMGame(players, columns, rows, time);
        for (WAMPlayer player:players) {
            player.setGame(game);
        }
        new Thread(game).run();
    }
}
