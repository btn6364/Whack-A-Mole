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

    private int ROWS;

    private int COLUMNS;

    private int NUM_PLAYERS;

    private String[] args;

    private int TIME;

    /**
     * Creates a new WAMServer that listens for incoming connections on a
     * specified port
     *
     * @param port The port on which the server listens for a connection
     * @throws Exception If there is an error creating the ServerSocket
     */
    public WAMServer(String[] args) throws Exception
    {
        this.server = new ServerSocket(Integer.parseInt(args[0]));
        String argsString = System.getProperties().toString();
        this.args = argsString.split(" ");
        this.COLUMNS = Integer.parseInt(args[2]);
        this.ROWS = Integer.parseInt(args[1]);
        this.NUM_PLAYERS = Integer.parseInt(args[3]);
        this.TIME = Integer.parseInt(args[4]);
    }


    public static void main(String[] args) throws Exception
    {
        if (args.length != 5)
        {
            System.out.println("Usage: port#  #rows  #columns  #players  game-duration-seconds");
            System.exit(-1);
        }
        WAMServer wamServer = new WAMServer(args);
        wamServer.run();
    }

    public int[] welcomeMessage()
    {
    //TODO for sending info to the client and GUI
        int[] ints = new int[2];
        ints[0] = COLUMNS;
        ints[1] = ROWS;
        return ints;

    }


    @Override
    public void run()
    {
        int numPlayersInGame = 1;
        WAMPlayer[] players = new WAMPlayer[NUM_PLAYERS];
        while(numPlayersInGame <= NUM_PLAYERS)
        {
            try
            {
                System.out.println("Waiting for player "+numPlayersInGame+"...");
                Socket playerSocket = server.accept();
                WAMPlayer player = new WAMPlayer(playerSocket);
                players[numPlayersInGame-1] = player;
                player.welcome(ROWS, COLUMNS, NUM_PLAYERS, numPlayersInGame);
                System.out.println("Player "+numPlayersInGame+" connected");
            } catch (IOException ie)
            {
                System.err.println("Something bad has happened!!!");
                ie.printStackTrace();
            } catch (Exception e)
            {
                System.err.println("Failed to create the player!");
                e.printStackTrace();
            }
            numPlayersInGame++;
        }
        System.out.println("Starting game !");
        //TODO: Create a WAMGame and start to run in a new thread
        WAMGame game = new WAMGame(players, COLUMNS, ROWS, TIME);
        new Thread(game).run();
    }
}
