package deadgiveaway.client;
import deadgiveaway.fake.FakeClueClientJon;

/**
 * ClientApp is the main class intended to be run to initialize a client.
 * @author Steven Gerick
 * @author Alex Saalberg
 * @version 1.0
 * 
 * @author Jon Kuzmich
 * @version 2.0
 */
public class ClientApp 
{
    //kDefaultPort - default port of the server. (5557)
    private static final int kDefaultPort = 5557;
    
    //Hostname of our virtual machine is "cslvm48.csc.calpoly.edu"
    //Hostname of local machine is "localhost"
    //kDefaultHostName - default host name of the server. 
    //private static final String kDefaultHostName = "cslvm48.csc.calpoly.edu";
    private static final String kDefaultHostName = "localhost";
    /**
     * Initializes the client and GUI, using the hostname and port. Starts the
     * client and then waits for the child thread to finish before terminating.
     * @param args Arguments given to the program : 
     *      '-p [natural number]' = port number
     *      '-h [host name string]' = host name
     *      '-c' to set the program in console mode
     */
    public static void main(String[] args) 
    {
        //CREATE a new variable for the port number
        int port = kDefaultPort;
        //CREATE a new variable for the host name
        String host = kDefaultHostName;
        //CREATE a new variable for the user interface mode
        boolean consoleInterface = false;
        
        try
        {
            //FOR all the commandline arguments
            for (int index = 0; index < args.length; index++)
            {
                //CHECK for the '-p' flag
                //IF there is a '-p' flag
                if (args[index].equals("-p"))
                {
                    //SET the port number to the number that follows
                    port = Integer.parseInt(args[++index]);
                }
                //ENDIF

                //CHECK for the '-h' flag
                //IF there is a '-h' flag
                if (args[index].equals("-h"))
                {
                    //SET the host name to the string that follows if it is not
                    //flag
                    host = args[++index];
                }
                //ENDIF

                //CHECK for the '-c' flag
                //IF there is a '-c' flag
                if (args[index].equals("-c"))
                {
                    //SET the user interface to be a console interface
                    consoleInterface = true;
                }
                //ENDIF
            }
        }
        catch (java.lang.NumberFormatException ex)
        {
            //PRINT out the corect usage for commandline arguments
            System.err.println("Usage : " +
                    System.getProperty("sun.java.command")
                    + " [ -p portNumber ] [ -h hostName ] [ -c ]");
            System.err.println("Options:");
            System.err.println("    -p    ---" +
                    "    Specify a port number to connect to.");
            System.err.println("    -h    ---" +
                    "    Specify the name of the host machine.");
            System.err.println("    -c    ---    Use a console interface.");
            
        }
        
        //IF the user interface is set to be a console interface
        if (consoleInterface)
        {
            //INITIALIZE a new ClueClient with the port number, host name, 
            //  and console interface
            FakeClueClientJon client = new FakeClueClientJon(host,
                port, null, ConsoleUI.class);
        }
        else
        {
            //INITIALIZE a new ClueClient with the port number, host name, 
            //  and GUI
            FakeClueClientJon client = new FakeClueClientJon(host,
                port, null, GUI.class);
        }
    }
}
