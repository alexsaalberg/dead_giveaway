package deadgiveaway.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ServerApp is the main class intended to be run to initialize a server for the
 * clue game.
 *
 * @author Alex Saalberg
 * @author Steven Gerick
 * @version 1.0
 */
public class ServerApp
{
    //MAX_PLAYERS - max players allowed in a game

    private static final int kMaxPlayers = 5;
    private static final int kSeedArg = 1;
    private static final int kTimeArg = 2;
    private static final int kMsInSecond = 1000;
    private static final int kDefaultWait = 60000;
    private static final int kDefaultPort = 5557;
    private static final int kIOExceptionExit = 3;

    /**
     * Initializes and runs the server. Creates and runs a child server, then
     * waits for the server to finish running before terminating.
     *
     * @param args The input arguments. Supported flags are -p (port), -s
     * (seed), -pt, -pc, pd. The last three are boolean flags for printing
     * timestamps, printConsole messages, and/or printDebug messages.
     */
    public static void main(String[] args)
    {
        //port - the port the server is hosted on
        int port = kDefaultPort;
        //seed - the seed to run the game with
        Integer seed = null;
        //clueServerHelper - The ClueServerHelper
        ClueServerHelper clueServerHelper;
        
        //printTimeStamps - Will be passed into clueServerHelper
        boolean printTimeStamps = false;
        //printConsole - Will be passed into clueServerHelper
        boolean printConsole = false;
        //printDebug - Will be passed into clueServerHelper
        boolean printDebug = false;
        //shuffle - Whether to shuffle or not
        boolean shuffle = true;
        

        //FOR all the commandline arguments
        for (int index = 0; index < args.length; index++)
        {
            //IF there is a '-p' flag (port)
            if (args[index].equals("-p"))
            {
                //SET the port number to the number that follows
                port = Integer.parseInt(args[++index]);
            } //ENDIF
            
            //IF there is a '-s' flag (seed)
            if (args[index].equals("-s"))
            {
                //SET seed to the next argument.
                seed = Integer.parseInt(args[++index]);
            } //ENDIF

            //Fixed defect #428
            //IF there is a '-pd' flag
            if (args[index].equals("-noshuffle"))
            {
                //SET printDebug to true
                shuffle = false;
            } //ENDIF
            
            //IF there is a '-pt' flag
            if (args[index].equals("-pt"))
            {
                //SET printTimeStamps to true
                printTimeStamps = true;
            } //ENDIF
            
            //IF there is a '-pc' flag
            if (args[index].equals("-pc"))
            {
                //SET printConsole to true
                printConsole = true;
            } //ENDIF
            
            //IF there is a '-pd' flag
            if (args[index].equals("-pd"))
            {
                //SET printDebug to true
                printDebug = true;
            } //ENDIF      
        }

        clueServerHelper = new ClueServerHelper(shuffle, printTimeStamps,
                printConsole, printDebug);

        startServer(port, seed, clueServerHelper);
    }

    /**
     * 
     * Starts a server with the arguments port, seed, and clueServerHelper
     * Does not check for validity of variables, which should be done in main.
     * 
     * @param port Port number. Default is 5557
     * @param seed Seed. Default is 0
     * @param clueServerHelper Helper class for ClueServer.
     * @author Alex Saalberg
     */
    public static void startServer(int port, Integer seed,
            ClueServerHelper clueServerHelper)
    {
        //server - the server this app is initializing and running
        ClueServer server = new ClueServer(port, seed, clueServerHelper);
        
        //TRY
        try
        {
            //BEGIN listening
            server.listen();
            System.out.println("Server now listening...");

            //fixed defect 406 - removed serverapp logic for starting the game.
            Thread.sleep(1000);
        }
        
        //CATCH
        catch (IOException ex)
        {
            Logger.getLogger(ServerApp.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(ServerApp.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }
}