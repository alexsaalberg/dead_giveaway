/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deadgiveaway.server;


import deadgiveaway.ActionCard;
import deadgiveaway.Card;
import deadgiveaway.LocationCard;
import deadgiveaway.LocationCard.Title;
import deadgiveaway.Message;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ocsf.server.ConnectionToClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author alexsaalberg
 */
public class ClueServerTest 
{
    
    private ClueServer clueServer;
    private ByteArrayOutputStream out;
    private PrintStream originalOut;
    
    @Before
    public void setUp() 
    {
        clueServer = new ClueServer(5557, 0, new ClueServerHelper(false, true, true));
    }
    
    @After
    public void tearDown() throws IOException 
    {
        clueServer.close();
    }
    
    public void setUpStream()
    { 
        originalOut = System.out;
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }
    
    public void tearDownStream()
    {
        System.setOut(originalOut);
    }
    
    @Test
    public void testConstructor() 
    {
        
        //assert that clueServer's players list is initialized and empty.
        assertEquals(new ArrayList<Player>(), clueServer.getPlayers());
        
        //assert that clueServer's deck (action card list) is initialized and not empty.
        assertNotEquals(new ArrayList<ActionCard>(), clueServer.getDeck());
        
        //assert that clueServer's solution is initialized and empty.
        assertEquals(new ArrayList<Card>(), clueServer.getSolution());
        
        //assert that the server has not started
        assertFalse(clueServer.isStarted());
        
        //assert that the server is closed
        assertTrue(clueServer.isClosed());
        
        //assert that the server is not listening
        assertFalse(clueServer.isListening());
      
        try
        {
            clueServer.listen();
        }
        catch (IOException e)
        {
            System.out.println(e);
            fail();
        }

        //assert that the server is closed
        assertFalse(clueServer.isClosed());
        
        //assert that the server is not listening
        assertTrue(clueServer.isListening());
        
    }
    
    @Test
    public void testConstructorNoSeed()
    {
        try
        {
            clueServer.listen();
        }
        catch (IOException e)
        {
            System.out.println(e);
            fail();
        }
        
        //assert that the server is closed
        assertFalse(clueServer.isClosed());
        
        //assert that the server is not listening
        assertTrue(clueServer.isListening());
        
    }
    
    /**
     * Test client connected
     */
    public void testClientConnectedGameNotStarted() 
    {
        //mockClient - mock ConnectionToClient
        ConnectionToClient mockClient = mock(ConnectionToClient.class);
        
        when(mockClient.toString()).thenReturn("LOCALHOST");
        
        //setup out as the stdout print stream
        setUpStream();
        
        //CALL clientConnected, pass it mockClient
        clueServer.clientConnected(mockClient);
        
        //reset stdout to originalOut
        tearDownStream();
        
        //assert the output to the console was correct
        assertEquals(String.format("Server: LOCALHOST connected%nServer: null added.%n"), out.toString());
    }
    
    @Test
    public void testClientConnectedGameStarted()
    {
        //mockClient - mock ConnectionToClient
        ConnectionToClient mockClient = mock(ConnectionToClient.class);
         
        when(mockClient.toString()).thenReturn("LOCALHOST");
        
        //call start on clueServer with no shuffling
        clueServer.start(false);
        
        //setup out as the stdout print stream
        setUpStream();
        
        //CALL clientConnected, pass it mockClient
        clueServer.clientConnected(mockClient);
        
        //reset stdout to originalOut
        tearDownStream();
        
        //assert the output to the console was correct
        assertEquals(String.format("Server: LOCALHOST connected%nServer: Player 0 added.%n"), out.toString());
    }
    
    @Test
    public void testClientConnectedGameStartedRefusalException() throws IOException{
        //mockClient - mock ConnectionToClient
        ConnectionToClient mockClient = mock(ConnectionToClient.class);
        ConnectionToClient mockClient2 = mock(ConnectionToClient.class);
         
        when(mockClient.toString()).thenReturn("C1");
        when(mockClient2.toString()).thenReturn("C2");
        doThrow(new IOException("Alex")).when(mockClient2).sendToClient(any(Message.class));
        
        //call start on clueServer with no shuffling
        
        //setup out as the stdout print stream
        setUpStream();
        
        //CALL clientConnected, pass it mockClient
        clueServer.clientConnected(mockClient);
        
        assertEquals(String.format("Server: C1 connected%nServer: Player 0 added.%n"), out.toString());
        out.reset();
        
        clueServer.start(false);
        
        assertEquals(String.format("Server: Now starting with 0 clients%n"), out.toString());
        out.reset();
        
        clueServer.clientConnected(mockClient2);
        
        //reset stdout to originalOut
        tearDownStream();
        /*
        //assert the output to the console was correct
        assertEquals(String.format("Server: C2 connected%n"
                + "IOException: Can't send connection established message.%n"
                + "IOException: Game started and couldn't send message to refuse client.%n"
                + "java.io.IOException: Alex%n"), out.toString());
        */
    }
    
    @Test
    public void testClientConnectedGameStartedRefusal() throws IOException{
        //mockClient - mock ConnectionToClient
        ConnectionToClient mockClient = mock(ConnectionToClient.class);
        ConnectionToClient mockClient2 = mock(ConnectionToClient.class);
         
        when(mockClient.toString()).thenReturn("C1");
        when(mockClient2.toString()).thenReturn("C2");
        
        //call start on clueServer with no shuffling
        
        //setup out as the stdout print stream
        setUpStream();
        
        //CALL clientConnected, pass it mockClient
        clueServer.clientConnected(mockClient);
        
        assertEquals(String.format("Server: C1 connected%nServer: Player 0 added.%n"), out.toString());
        out.reset();
        
        clueServer.start(false);
        
        assertEquals(String.format("Server: Now starting with 0 clients%n"), out.toString());
        out.reset();
        
        clueServer.clientConnected(mockClient2);
        
        //reset stdout to originalOut
        tearDownStream();
        
        //assert the output to the console was correct
        assertEquals(String.format("Server: C2 connected%nServer: Player 1 added.%n"), out.toString());
    }
    
    public void testClientConnectedGameStartedException2() throws IOException{
        //mockClient - mock ConnectionToClient
        ConnectionToClient mockClient = mock(ConnectionToClient.class);
         
        when(mockClient.toString()).thenReturn("LOCALHOST");
        doThrow(new IOException("Alex")).when(mockClient).close();
        
        //call start on clueServer with no shuffling
        clueServer.start(false);
        
        //setup out as the stdout print stream
        setUpStream();
        
        //CALL clientConnected, pass it mockClient
        clueServer.clientConnected(mockClient);
        
        //reset stdout to originalOut
        tearDownStream();
        
        System.out.println(out.toString());
        //assert the output to the console was correct
        assertEquals(String.format("Server: LOCALHOST connected%nIOException: "
                + "Game started and couldn't close client connection.%n" 
                + "java.io.IOException: Alex%n"), out.toString());
    }
    
    @Test
    public void testClientDisconnectedGameNotStarted(){
        //mockClient - mock ConnectionToClient
        ConnectionToClient mockClient = mock(ConnectionToClient.class);
        
        when(mockClient.toString()).thenReturn("LOCALHOST");
        
        //call clientConnected, pass it mockClient
        clueServer.clientConnected(mockClient);
        
        //setup out as the stdout print stream
        setUpStream();
        
        //call clientDisconnected
        clueServer.clientDisconnected(mockClient);
        
        //tear down stream
        tearDownStream();
        
        //assert the output was as expected
        //assertEquals(String.format("Server: LOCALHOST disconnected%n"), out.toString());
    }
    
    @Test
    public void testClientDisconnectedGameStarted(){
        //mockClient - mock ConnectionToClient
        ConnectionToClient mockClient = mock(ConnectionToClient.class);
        
        when(mockClient.toString()).thenReturn("LOCALHOST");
        
        //call clientConnected, pass it mockClient
        clueServer.clientConnected(mockClient);
        
        clueServer.start(false);
        
        //setup out as the stdout print stream
        setUpStream();
        
        //call clientDisconnected
        clueServer.clientDisconnected(mockClient);

        //assert the output was as expected
//        assertEquals(String.format("Server: LOCALHOST disconnected%n"), out.toString());
        assertTrue(clueServer.isClosed());        
        //tear down stream
        tearDownStream();
    }
    
    @Test
    public void testTwoClientDisconnectedGameStarted() throws IOException{
        ConnectionToClient mockClient = mock(ConnectionToClient.class);
        ConnectionToClient mockClient2 = mock(ConnectionToClient.class);
        
        when(mockClient.toString()).thenReturn("LOCALHOST");
        when(mockClient2.toString()).thenReturn("LOCALHOST2");
        
        //call clientConnected, pass it mockClient
        clueServer.clientConnected(mockClient);
        clueServer.clientConnected(mockClient2);
        
        clueServer.start(false);
        clueServer.listen();
        
        //setup out as the stdout print stream
        setUpStream();
        
        //call clientDisconnected
        clueServer.clientDisconnected(mockClient);
        
        //tear down stream
        tearDownStream();
        
        //assert the output was as expected
        //assertEquals(String.format("Server: LOCALHOST disconnected%n"), out.toString());
    }
    
    @Test
    public void testTwoClientDisconnectedGameStartedDifferentTurn() throws IOException{
        
        ConnectionToClient mockClient = mock(ConnectionToClient.class);
        ConnectionToClient mockClient2 = mock(ConnectionToClient.class);
        
        when(mockClient.toString()).thenReturn("LOCALHOST");
        when(mockClient2.toString()).thenReturn("LOCALHOST2");
        
        //call clientConnected, pass it mockClient
        clueServer.clientConnected(mockClient);
        clueServer.clientConnected(mockClient2);
        
        clueServer.start(false);
        clueServer.listen();
        
        //setup out as the stdout print stream
        setUpStream();
        
        //call clientDisconnected
        clueServer.clientDisconnected(mockClient2);
        
        //tear down stream
        tearDownStream();
        
        //assert the output was as expected
        //assertEquals(String.format("Server: LOCALHOST disconnected%n"), out.toString());
    }
    
    @Test
    public void testTwoClientDisconnectedGameStartedServerClosed() throws IOException{
        
        ConnectionToClient mockClient = mock(ConnectionToClient.class);
        ConnectionToClient mockClient2 = mock(ConnectionToClient.class);
        
        when(mockClient.toString()).thenReturn("LOCALHOST");
        when(mockClient2.toString()).thenReturn("LOCALHOST2");
        
        //call clientConnected, pass it mockClient
        clueServer.clientConnected(mockClient);
        clueServer.clientConnected(mockClient2);
        
        clueServer.start(false);
        clueServer.listen();
        clueServer.close();
        
        //setup out as the stdout print stream
        setUpStream();
        
        //call clientDisconnected
        clueServer.clientDisconnected(mockClient2);
        
        //tear down stream
        tearDownStream();
        
        //assert the output was as expected
        //assertEquals(String.format("Server: LOCALHOST disconnected%n"), out.toString());
    }
    
    @Test
    public void testClientDisconnectedSingle(){
        //mockClient - mock ConnectionToClient
        ConnectionToClient mockClient = mock(ConnectionToClient.class);
        
        when(mockClient.toString()).thenReturn("LOCALHOST");
        
        //call clientConnected, pass it mockClient
        clueServer.clientConnected(mockClient);
        
        //call start with no shuffling
        clueServer.start(false);
        
        //setup out as the stdout print stream
        setUpStream();
        
        //call clientDisconnected
        clueServer.clientDisconnected(mockClient);
        
        tearDownStream();
        
        //assert the output was as expected
        //assertEquals(String.format("Server: LOCALHOST disconnected%n"), out.toString());
        
        //assert true that the server is closed
        assertTrue(clueServer.isClosed());
    }        
        

public void testClientDisconnectDual(){
    
        //mockClient - mock ConnectionToClient
        ConnectionToClient mockClient = mock(ConnectionToClient.class);
        
        //mockClient - mock ConnectionToClient
        ConnectionToClient mockClient2 = mock(ConnectionToClient.class);
        
        //playerList - list of players
        ArrayList<Player> playerList;
        
        //call clientConnected on clueserver, pass it mockClient
        clueServer.clientConnected(mockClient);
        
        //call clientConnected on clueserver, pass it mockClient2
        clueServer.clientConnected(mockClient2);
        
        //call start on clueServer, with no shuffling
        clueServer.start(false);
        
        //change stdout to out, save old stdout as originalOut
        setUpStream();
        
        //call clientDisconnected on clueserver, pass it mockClient
        clueServer.clientDisconnected(mockClient);
        
        tearDownStream();
        
        //assert that the 1st player did change
        assertNotEquals(mockClient, clueServer.getPlayers().get(0));
    }
    
    @Test 
    public void testStartNoPlayers() {
        //actionCardList - Used to "remember" the deck before it gets shuffled.
        ArrayList<ActionCard> actionCardList =  clueServer.getDeck();
        
        ConnectionToClient mockClient = mock(ConnectionToClient.class);
        clueServer.clientConnected(mockClient);
        
        //call start on clueserver with shuffle true.
        clueServer.start(true);
        
        //assert the current deck is not the same as actionCardList;
        assertNotSame(actionCardList, clueServer.getDeck());
    } 
    
    @Test 
    public void testStartPlayers() {
        //actionCardList - Used to "remember" the deck before it gets shuffled.
        ArrayList<ActionCard> actionCardList =  clueServer.getDeck();
        
        //mockClient - mock ConnectionToClient
        ConnectionToClient mockClient = mock(ConnectionToClient.class);
        
        
        //call clientConnected, pass it mockClient
        clueServer.clientConnected(mockClient);
        
        //call start on clueserver with shuffle false.
        clueServer.start(false);
        
        assertEquals(-1, clueServer.getTurn());
        
        //assert the current deck is not the same (reference) as actionCardList;
        assertNotSame(actionCardList, clueServer.getDeck());
        
        //assert the current deck is not equal to actionCardList;
        //assertNotEquals(actionCardList, clueServer.getDeck());
        
        //remove 5 cards (1 for each player) like start will.
        for(int i = 0; i < 5; i++)
        {
            actionCardList.remove(0);
        }
        
        //assert the current deck is not equal to actionCardList;
        assertNotEquals(actionCardList, clueServer.getDeck());
    }
    
}