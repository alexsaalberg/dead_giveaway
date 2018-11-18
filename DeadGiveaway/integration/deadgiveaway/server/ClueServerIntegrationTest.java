/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deadgiveaway.server;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import deadgiveaway.ActionCard;
import deadgiveaway.Card;
import deadgiveaway.LocationCard;
import deadgiveaway.LocationCard.Title;
import deadgiveaway.Message;
import deadgiveaway.Message.Move;
import deadgiveaway.Message.Type;
import deadgiveaway.SuspectCard;
import deadgiveaway.SuspectCard.Name;
import deadgiveaway.VehicleCard;
import deadgiveaway.VehicleCard.Model;
import deadgiveaway.server.ClueServer;
import deadgiveaway.server.Player;
import deadgiveaway.server.RobotPlayer;
import java.io.IOException;
import java.util.ArrayList;
import ocsf.server.ConnectionToClient;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Clue Server integration tests.
 * @author Jon Kuzmich
 */
public class ClueServerIntegrationTest {
    
    private ClueServer clueServer;
    
    private ByteArrayOutputStream out;
    
    private PrintStream originalOut;
        
    public ClueServerIntegrationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @Before
    public void setUp() {
        clueServer = new ClueServer(5557, 0, new ClueServerHelper(false, true, true));
        
        setUpMockClients();
        
        setUpMockPlayers();
        
        setUpPlayerAdded();
        
        setUpMockMessage();
    }
    
    public void tearDown() {
        try
        {
            clueServer.close();
        }
        catch (IOException ex)
        {
            System.out.println("tearDown: "+ex);
        }
        
        clueServer = null;
    }
    
    public void setUpStream(){ 
        originalOut = System.out;
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }
    
    public void tearDownStream(){
        System.setOut(originalOut);
    }
    
    private ConnectionToClient mockClient;
    private ConnectionToClient mockClient2;
    
    private Player mockPlayer;
    private Player mockPlayer2;
    
    private RobotPlayer mockRobotPlayer;
    
    private Message mockMessage;
    
    private ActionCard[] actionCardArray;
    private Card[] clueCardArray;
    
    public void setUpMockClients()
    {
        //Initialize the mockClients
        mockClient = mock(ConnectionToClient.class);
        mockClient2 = mock(ConnectionToClient.class);
        
        when(mockClient.toString()).thenReturn("MockIP1");
        when(mockClient2.toString()).thenReturn("MockIP2");
        
        //Connect the clients
        clueServer.clientConnected(mockClient);
        clueServer.clientConnected(mockClient2);
    }

    public void setUpMockPlayers()
    {
        //initialize the mockPlayers
        mockPlayer = new Player("_HumanP1_", 0, new LocationCard(Title.TITLE1));
        mockPlayer2 = new Player("_HumanP2_", 0, new LocationCard(Title.TITLE2));
        
        //initialize card arrays
        clueCardArray = new Card[3];
        actionCardArray = new ActionCard[1];
        
        //stub mockPlayer
        clueCardArray[0] = new LocationCard(Title.TITLE1);
        clueCardArray[1] = new SuspectCard(Name.NAME1);
        clueCardArray[2] = new VehicleCard(Model.MODEL1);
        mockPlayer.addCard(new LocationCard(Title.TITLE1));
        mockPlayer.addCard(new SuspectCard(Name.NAME1));
        mockPlayer.addCard(new VehicleCard(Model.MODEL1));
        
        actionCardArray[0] = new ActionCard(ActionCard.Type.SNOOP);
        mockPlayer.addCard(new ActionCard(ActionCard.Type.SNOOP));
        
        //stub mockPlayer2
        clueCardArray[0] = new LocationCard(Title.TITLE2);
        clueCardArray[1] = new SuspectCard(Name.NAME2);
        clueCardArray[2] = new VehicleCard(Model.MODEL2);
        mockPlayer2.addCard(new LocationCard(Title.TITLE2));
        mockPlayer2.addCard(new SuspectCard(Name.NAME2));
        mockPlayer2.addCard(new VehicleCard(Model.MODEL2));
        
        actionCardArray[0] = new ActionCard(ActionCard.Type.SNOOP);
        mockPlayer.addCard(new ActionCard(ActionCard.Type.SNOOP));
        
        //stub mockRobotPlayer
        mockRobotPlayer = new RobotPlayer(0, "_MRobot_", new java.util.Random(), new LocationCard(Title.TITLE2));
        
    }
    
    public void setUpPlayerAdded()
    {
        mockMessage = new Message(mockPlayer, null, null, Message.Move.PLAYERADDED, null, null);
        
        //send playerAdded message for mockPlayer
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        mockMessage = new Message(mockPlayer2, null, null, Message.Move.PLAYERADDED, null, null);
        
        //send for mockPlayer2
        clueServer.handleMessageFromClient(mockMessage, mockClient2);
    }
        
    public void setUpMockMessage()
    {
        Card[] cardArray = {new ActionCard(ActionCard.Type.ALLSNOOPLEFT)};
        mockMessage = new Message(mockPlayer, null, null, Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardArray);
        
        //when(mockMessage.getPlayer()).thenReturn(mockPlayer);
    }
    
    @Test
    public void testNothing(){
        
    }
    
    @Test
    public void testHandleMessageFromClientNonMessage() {
        try
        {
            //send server a message that doesn't contain a Message object.
            clueServer.handleMessageFromClient(null, null);
            fail();
        }
        catch(IllegalArgumentException e)
        {
            //Correct
        }
    }
    
    @Test
    public void testPlayerAdded() {
        //connect mockClient to clueServer
        clueServer.clientConnected(mockClient);
        clueServer.start(false);
        
        //ArrayList<Player> playerList - list to store getPlayers
        ArrayList<Player> playerList = clueServer.getPlayers();
        
        mockMessage = new Message(mockPlayer, null, null, Message.Move.PLAYERADDED, null, null);
        
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        try {
            verify(mockClient, times(6)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    @Test
    public void testHandleAllSnoopLeft() {
        //Card[] cardArray - The all snoop left card being sent to server.
        Card[] cardArray = {new ActionCard(ActionCard.Type.ALLSNOOPLEFT)};
        Card[] clueCardArray = {new LocationCard(LocationCard.Title.TITLE1)};
        
        clueServer.start(false);
        
        //mockMessage = mock(Message.class);
        mockMessage = new Message(mockPlayer, null, null, Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardArray);
        //call handleMessageFromClient
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        try {
            verify(mockClient, times(4)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    @Test
    public void testHandleAllSnoopRight() {
        //Card[] cardArray - The all snoop left card being sent to server.
        Card[] cardArray = {new ActionCard(ActionCard.Type.ALLSNOOPRIGHT)};
        Card[] clueCardArray = {new LocationCard(LocationCard.Title.TITLE1)};
        
        clueServer.start(false);
        
        //mockMessage = mock(Message.class);
        mockMessage = new Message(mockPlayer, null, null, Message.Move.ACTION, Message.Type.ALLSNOOPRIGHT, cardArray);
        
        //call handleMessageFromClient
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        try {
            verify(mockClient, times(4)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
   
    public void testHandleAllSnoopLeftRobot() {
        Card[] cardArray = {new ActionCard(ActionCard.Type.ALLSNOOPLEFT)};
        Card[] clueCardArray = {new LocationCard(LocationCard.Title.TITLE1)};
        
        //end mockPlayer's turn
        mockMessage = new Message(mockPlayer, null, null, Message.Move.ENDTURN, null, null);
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        //end mockPlayer2's turn
        mockMessage = new Message(mockPlayer2, null, null, Message.Move.ENDTURN, null, null);
        clueServer.handleMessageFromClient(mockMessage, mockClient2);
        
        //start the clueServer without shuffling
        clueServer.start(false);
        
        mockMessage = new Message(mockRobotPlayer, null, null, Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardArray);
        
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        try {
            verify(mockClient).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
   
    public void testHandleAllSnoopRightRobot() {
        Card[] cardArray = {new ActionCard(ActionCard.Type.ALLSNOOPRIGHT)};
        Card[] clueCardArray = {new LocationCard(LocationCard.Title.TITLE1)};
        
        //end mockPlayer's turn
        mockMessage = mock(Message.class);
        mockMessage = new Message(mockPlayer, null, null, Message.Move.ENDTURN, null, null);
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        //end mockPlayer2's turn
        mockMessage = mock(Message.class);
        mockMessage = new Message(mockPlayer2, null, null, Message.Move.ENDTURN, null, null);
        clueServer.handleMessageFromClient(mockMessage, mockClient2);
        
        //start the clueServer without shuffling
        clueServer.start(false);
        
        mockMessage = new Message(mockRobotPlayer, null, null, Message.Move.ACTION, Message.Type.ALLSNOOPRIGHT, cardArray);
        
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        try {
            verify(mockClient).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    @Test
    public void testHandleAllSnoopLeftPlayerOut() {
        //Card[] cardArray - The all snoop left card being sent to server.
        Card[] cardArray = {new ActionCard(ActionCard.Type.ALLSNOOPLEFT)};
        Card[] clueCardArray = {new LocationCard(LocationCard.Title.TITLE1)};
        
        mockPlayer = new Player("_HumanP1_", 0, new LocationCard(LocationCard.Title.TITLE1));
        mockPlayer.addCard(new LocationCard(LocationCard.Title.TITLE1));
        mockPlayer.setID(0);
        mockPlayer.setOut();
        
        mockMessage = new Message(mockPlayer, null, null, Message.Move.PLAYERADDED, null, cardArray);
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        clueServer.start(false);
        
        mockMessage = new Message(mockPlayer, null, null, Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardArray);
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        try {
            verify(mockClient, times(10)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    @Test
    public void testHandleAllSnoopRightPlayerOut() {
        //Card[] cardArray - The all snoop left card being sent to server.
        Card[] cardArray = {new ActionCard(ActionCard.Type.ALLSNOOPRIGHT)};
        Card[] clueCardArray = {new LocationCard(LocationCard.Title.TITLE1)};
        
        mockPlayer = new Player("_HumanP1_", 0, new LocationCard(LocationCard.Title.TITLE1));
        mockPlayer.addCard(new LocationCard(LocationCard.Title.TITLE1));
        mockPlayer.setID(0);
        mockPlayer.setOut();
        
        mockMessage = new Message(mockPlayer, null, null, Message.Move.PLAYERADDED, null, cardArray);
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        clueServer.start(false);
        
        mockMessage = new Message(mockPlayer, null, null, Message.Move.ACTION, Message.Type.ALLSNOOPRIGHT, cardArray);
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        try {
            verify(mockClient, times(10)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    
    @Test
    public void testHandleSnoop() {
        Card[] cardArray = {new ActionCard(ActionCard.Type.SNOOP)};
        Card[] cardArray2 = {new LocationCard(LocationCard.Title.TITLE1)};
        
        clueServer.start(false);
        
        mockMessage = new Message(mockPlayer, mockPlayer2, null, Message.Move.ACTION, Message.Type.SNOOP, cardArray);
        
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        try {
            verify(mockClient, times(4)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    public void testHandleSnoopRobot() {
        Card[] cardArray = {new ActionCard(ActionCard.Type.SNOOP)};
        Card[] cardArray2 = {new LocationCard(LocationCard.Title.TITLE1)};
        
        clueServer.start(false);
        
        mockMessage = new Message(mockRobotPlayer, mockPlayer2, null, Message.Move.ACTION, Message.Type.SNOOP, cardArray);
        
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        try {
            verify(mockClient).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    public void testHandleSuperSleuth() {
        //Card[] cardArray - The all snoop left card being sent to server.
        Card[] cardArray = {new ActionCard(ActionCard.Type.SSBLUE)};
        
        clueServer.start(false);
        
        mockMessage = new Message(mockPlayer, null, null, Message.Move.ACTION, Message.Type.SUPERSLEUTH, cardArray);
        
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        try {
            verify(mockClient).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    @Test
    public void testHandleSuggestion() {
        Card[] cardArray = {new LocationCard(LocationCard.Title.TITLE1), 
            new SuspectCard(SuspectCard.Name.NAME1), 
            new VehicleCard(VehicleCard.Model.MODEL1),
            new ActionCard(ActionCard.Type.SUGGESTALL)};
        
        clueServer.start(false);
        mockMessage = new Message(mockPlayer, null, null, Message.Move.ACTION, Message.Type.SUGGESTION, cardArray);
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        
        try {
            verify(mockClient, times(4)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    public void testPrivateTip() {
        
        //Card[] cardArray - The all snoop left card being sent to server.
        Card[] cardArray = {new ActionCard(ActionCard.Type.PTDESTINATION)};
        
        mockMessage = new Message(mockPlayer, null, null, Message.Move.ACTION, Message.Type.PRIVATETIP, cardArray);
        
        //connect mockClient to clueServer
        clueServer.clientConnected(mockClient);
        
        //start the clueServer without shuffling
        clueServer.start(false);
        
        //call handleMessageFromClient
        clueServer.handleMessageFromClient(mockMessage, mockClient);
    }
    
    public void testHandleAccusation() {
        
        //Card[] cardArray - The all snoop left card being sent to server.
        Card[] cardArray = {new SuspectCard(SuspectCard.Name.NAME1),
                new LocationCard(LocationCard.Title.TITLE1),
                new VehicleCard(VehicleCard.Model.MODEL1)};
        
        when(mockMessage.getCards()).thenReturn(cardArray);
        //Tell mockmessage to return ACCUSATION when getMove
        when(mockMessage.getMove()).thenReturn(Move.ACCUSATION);
        //Tell mockmessage to return NULL when getType
        when(mockMessage.getType()).thenReturn(null);
        
        mockMessage = new Message(mockPlayer, null, null, Message.Move.ACCUSATION, null, cardArray);
        
        //connect mockClient to clueServer
        clueServer.clientConnected(mockClient);
        
        //start the clueServer without shuffling
        clueServer.start(false);
        
        //call handleMessageFromClient
        clueServer.handleMessageFromClient(mockMessage, mockClient);
    }
    
    @Test
    public void testReplacePlayersList() {
        
        //Card[] cardArray - The all snoop left card being sent to server.
        ArrayList<Player> players = new ArrayList<Player>();
        Player player = new Player("TEST",0,new LocationCard(LocationCard.Title.TITLE1));
        player.addCard(new ActionCard(ActionCard.Type.SNOOP));
        player.addCard(new ActionCard(ActionCard.Type.SNOOP));
        players.add(player);
        
        mockMessage = new Message(player, null, players.toArray(new Player[0]), Message.Move.UPDATELOG, null, null);
        //connect mockClient to clueServer
        clueServer.clientConnected(mockClient);
        
        //start the clueServer without shuffling
        clueServer.start(false);
        
        //call handleMessageFromClient
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        assertNotSame(players,clueServer.getPlayers());
        assertEquals(players.toString(), clueServer.getPlayers().toString());
    }
    
    @Test
    public void testEndTurn()
    {
        mockMessage = new Message(mockPlayer, null, null, Move.ENDTURN, null, null);
        
        clueServer.start(false);
      
        assertEquals(0, clueServer.getTurn());
        
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        assertEquals(1, clueServer.getTurn());
    } 
    
    public void testMistimedResponse() throws IOException
    {
        mockMessage = new Message(mockPlayer2, null, null, Move.ENDTURN, null, null);
        
        clueServer.start(false);
        
        try
        {
            clueServer.handleMessageFromClient(mockMessage, mockClient2);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            
        }
    }
    
}