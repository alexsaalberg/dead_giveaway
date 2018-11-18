package deadgiveaway.fake;

import deadgiveaway.*;
import deadgiveaway.server.ClueServer;
import deadgiveaway.server.ClueServerHelper;
import ocsf.server.ConnectionToClient;

/**
 * Fake Clue Server for testing
 * @author camgeehr
 */
public class FakeClueServerCameron extends ClueServer
{
    /* Empty message */
    private Message message;
    
    /**
     * Constructor
     * @param port port number
     * @param seed random seed
     * @param clueServerHelper helper for the server
     */
    public FakeClueServerCameron(int port, int seed, 
            ClueServerHelper clueServerHelper)
    {
        super(port, seed, clueServerHelper);
    }
    
    /**
     * Handles a message from the client
     * @param msg Message coming in
     * @param con Connection
     */
    public void handleMessageFromClient(Object msg, ConnectionToClient con)
    {
        message = (Message)msg;
    }
    
    /**
     * Gets the last message received
     * @return the last message received
     */
    public Message getLastMessage()
    {
        return message;
    }
    
}
