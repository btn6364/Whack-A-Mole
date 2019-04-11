package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

/**
 * @author Jared Sullivan (jms8376@rit.edu)
 */
public class WAMServer implements Runnable
{
    //Used to wait for incoming connections
    private ServerSocket server;

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
            System.out.println("Usage: game-port#  #rows  #columns  #players  game-duration-seconds");
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

    }
}
