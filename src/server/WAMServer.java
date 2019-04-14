package server;

import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

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

    private int numPlayers;

    /**
     * Creates a new WAMServer that listens for incoming connections on a
     * specified port
     *
     * @param port The port on which the server listens for a connection
     * @throws Exception If there is an error creating the ServerSocket
     */
    public WAMServer(int port) throws Exception
    {
        this.server = new ServerSocket(port);
    }


    public static void main(String[] args) throws Exception
    {
        if (args.length != 5)
        {
            System.out.println("Usage: port#  #rows  #columns  #players  game-duration-seconds");
            System.exit(-1);
        }

        int port = Integer.parseInt(args[0]);
        WAMServer server = new WAMServer(port);
        server.run();
    }

    public int[] welcomeMessage()
    {
        //TODO for sending info to the client and GUI


        return null;
    }


    @Override
    public void run()
    {
        //TODO after WAMPlayer is created
//        try {
//            System.out.println("Waiting for player one...");
//            Socket playerOneSocket = server.accept();
//            WAMPlayer playerOne = new WAMPlayer(playerOneSocket);
//            //playerOne.welcome();need to fix this
//            System.out.println("Player one connected");
//            System.out.println("Starting game !");
//            //TODO: Create a WAMGame and start to run in a new thread
//
//
//        } catch (IOException ie){
//            System.err.println("Something bad has happened!!!");
//            ie.printStackTrace();
//        } catch (Exception e){
//            System.err.println("Failed to create the player!");
//            e.printStackTrace();
//        }

    }
}
