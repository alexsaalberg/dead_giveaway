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
import deadgiveaway.Message.Move;
import deadgiveaway.Message.Type;
import deadgiveaway.SuspectCard;
import deadgiveaway.SuspectCard.Name;
import deadgiveaway.VehicleCard;
import deadgiveaway.VehicleCard.Model;
import java.io.IOException;
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
public class ClueServerHandleMessageFromClientTest {
    
    private ClueServer clueServer;
    
    private ConnectionToClient mockClient;
    private ConnectionToClient mockClient2;
    
    private Player mockPlayer;
    private Player mockPlayer2;
    
    private RobotPlayer mockRobotPlayer;
    
    private Message mockMessage;
    
    private ActionCard[] actionCardArray;
    private Card[] clueCardArray;
    
    @Before
    public void setUp() {
        clueServer = new ClueServer(5557, 0,  new ClueServerHelper(false, true, true));
        
        setUpMockClients();
        
        setUpMockPlayers();
        
        setUpPlayerAdded();
        
        setUpMockMessage();
    }
    
    @After
    public void tearDown()
    {
        try
        {
            clueServer.close();
        }
        catch (IOException ex)
        {
            System.out.println("tearDown: "+ex);
        }
    }
    
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
        mockPlayer = mock(Player.class);
        mockPlayer2 = mock(Player.class);
        
        //initialize card arrays
        clueCardArray = new Card[3];
        actionCardArray = new ActionCard[1];
        
        //stub mockPlayer
        clueCardArray[0] = new LocationCard(Title.TITLE1);
        clueCardArray[1] = new SuspectCard(Name.NAME1);
        clueCardArray[2] = new VehicleCard(Model.MODEL1);
        when(mockPlayer.getNumCards()).thenReturn(3);
        when(mockPlayer.getClueCards()).thenReturn(clueCardArray.clone());
        
        actionCardArray[0] = new ActionCard(ActionCard.Type.SNOOP);
        when(mockPlayer.getNumActions()).thenReturn(1);
        when(mockPlayer.getActionCards()).thenReturn(actionCardArray);
        
        when(mockPlayer.getID()).thenReturn(0);
        when(mockPlayer.getName()).thenReturn("_HumanP1_");
        when(mockPlayer.getOut()).thenReturn(false);
        when(mockPlayer.getLocation()).thenReturn(new LocationCard(Title.TITLE1));
        when(mockPlayer.toString()).thenReturn("_P1STRING_");
        when(mockPlayer.deepCopy()).thenReturn(mockPlayer);
        
        //stub mockPlayer2
        clueCardArray[0] = new LocationCard(Title.TITLE2);
        clueCardArray[1] = new SuspectCard(Name.NAME2);
        clueCardArray[2] = new VehicleCard(Model.MODEL2);
        when(mockPlayer2.getNumCards()).thenReturn(3);
        when(mockPlayer2.getClueCards()).thenReturn(clueCardArray.clone());
        
        actionCardArray[0] = new ActionCard(ActionCard.Type.SNOOP);
        when(mockPlayer2.getNumActions()).thenReturn(1);
        when(mockPlayer2.getActionCards()).thenReturn(actionCardArray);
        
        when(mockPlayer2.getID()).thenReturn(1);
        when(mockPlayer2.getName()).thenReturn("_HumanP2_");
        when(mockPlayer2.getOut()).thenReturn(false);
        when(mockPlayer2.getLocation()).thenReturn(new LocationCard(Title.TITLE2));
        when(mockPlayer2.toString()).thenReturn("_P2STRING_");
        when(mockPlayer2.deepCopy()).thenReturn(mockPlayer2);
        
        //stub mockRobotPlayer
        mockRobotPlayer = mock(RobotPlayer.class);
        when(mockRobotPlayer.getID()).thenReturn(0);
        when(mockRobotPlayer.getNumCards()).thenReturn(3);
        when(mockRobotPlayer.getClueCards()).thenReturn(clueCardArray);
        when(mockRobotPlayer.getName()).thenReturn("_MRobot_");
    }
    
    public void setUpPlayerAdded()
    {
        mockMessage = mock(Message.class);
        
        //send playerAdded message for mockPlayer
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        when(mockMessage.getMove()).thenReturn(Move.PLAYERADDED);
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        //send for mockPlayer2
        mockMessage = mock(Message.class);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer2);
        when(mockMessage.getMove()).thenReturn(Move.PLAYERADDED);
        clueServer.handleMessageFromClient(mockMessage, mockClient2);
    }
        
    public void setUpMockMessage()
    {
        mockMessage = mock(Message.class);
        Card[] cardArray = {new ActionCard(ActionCard.Type.ALLSNOOPLEFT)};
        
        when(mockMessage.getPlayer()).thenReturn(mockPlayer); 
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
        //Tell mockMessage to return a move, ACTION
        when(mockMessage.getMove()).thenReturn(Move.PLAYERADDED);
        
        //connect mockClient to clueServer
        clueServer.clientConnected(mockClient);
        clueServer.start(false);
        
        //ArrayList<Player> playerList - list to store getPlayers
        ArrayList<Player> playerList = clueServer.getPlayers();
        
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
    }
    
    @Test
    public void testHandleAllSnoopLeft() {
        //Card[] cardArray - The all snoop left card being sent to server.
        Card[] cardArray = {new ActionCard(ActionCard.Type.ALLSNOOPLEFT)};
        Card[] clueCardArray = {new LocationCard(LocationCard.Title.TITLE1)};
        
        clueServer.start(false);
        
        //mockMessage = mock(Message.class);
        
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getMove()).thenReturn(Move.ACTION);
        when(mockMessage.getType()).thenReturn(Message.Type.ALLSNOOPLEFT);
        
        //call handleMessageFromClient
        clueServer.handleMessageFromClient(mockMessage, mockClient);
      
        try {
            verify(mockClient, times(9)).sendToClient(any(Message.class));
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
        
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getMove()).thenReturn(Move.ACTION);
        when(mockMessage.getType()).thenReturn(Message.Type.ALLSNOOPRIGHT);
        
        //call handleMessageFromClient
        clueServer.handleMessageFromClient(mockMessage, mockClient);
      
        try {
            verify(mockClient, times(9)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
   
    @Test
    public void testHandleAllSnoopLeftRobot() {
        Card[] cardArray = {new ActionCard(ActionCard.Type.ALLSNOOPLEFT)};
        Card[] clueCardArray = {new LocationCard(LocationCard.Title.TITLE1)};
        
        //end mockPlayer's turn
        mockMessage = mock(Message.class);
        when(mockMessage.getMove()).thenReturn(Move.ENDTURN);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        //end mockPlayer2's turn
        mockMessage = mock(Message.class);
        when(mockMessage.getMove()).thenReturn(Move.ENDTURN);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer2);
        clueServer.handleMessageFromClient(mockMessage, mockClient2);
        
        //start the clueServer without shuffling
        clueServer.start(false);
        
        mockMessage = mock(Message.class);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getMove()).thenReturn(Move.ACTION);
        when(mockMessage.getType()).thenReturn(Message.Type.ALLSNOOPLEFT);
        when(mockMessage.getPlayer()).thenReturn(mockRobotPlayer);
        
        clueServer.handleMessageFromClient(mockMessage, mockClient);
      
        try {
            verify(mockClient, times(8)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
   
    @Test
    public void testHandleAllSnoopRightRobot() {
        Card[] cardArray = {new ActionCard(ActionCard.Type.ALLSNOOPRIGHT)};
        Card[] clueCardArray = {new LocationCard(LocationCard.Title.TITLE1)};
        
        //end mockPlayer's turn
        mockMessage = mock(Message.class);
        when(mockMessage.getMove()).thenReturn(Move.ENDTURN);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        //end mockPlayer2's turn
        mockMessage = mock(Message.class);
        when(mockMessage.getMove()).thenReturn(Move.ENDTURN);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer2);
        clueServer.handleMessageFromClient(mockMessage, mockClient2);
        
        //start the clueServer without shuffling
        clueServer.start(false);
        
        mockMessage = mock(Message.class);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getMove()).thenReturn(Move.ACTION);
        when(mockMessage.getType()).thenReturn(Message.Type.ALLSNOOPRIGHT);
        when(mockMessage.getPlayer()).thenReturn(mockRobotPlayer);
        
        clueServer.handleMessageFromClient(mockMessage, mockClient);
      
        try {
            verify(mockClient, times(8)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    @Test
    public void testHandleAllSnoopLeftPlayerOut() {
        //Card[] cardArray - The all snoop left card being sent to server.
        Card[] cardArray = {new ActionCard(ActionCard.Type.ALLSNOOPLEFT)};
        Card[] clueCardArray = {new LocationCard(LocationCard.Title.TITLE1)};
        
        mockPlayer = mock(Player.class);
        when(mockPlayer.getID()).thenReturn(0);
        when(mockPlayer.getOut()).thenReturn(true);
        when(mockPlayer.getNumCards()).thenReturn(1);
        when(mockPlayer.getClueCards()).thenReturn(clueCardArray);
        
        mockMessage = mock(Message.class);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getMove()).thenReturn(Move.PLAYERADDED);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        try
        {
            clueServer.start(false);
        }
        catch (NullPointerException e)
        {
            
        }
        
        mockMessage = mock(Message.class);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getMove()).thenReturn(Move.ACTION);
        when(mockMessage.getType()).thenReturn(Message.Type.ALLSNOOPLEFT);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        try
        {
            clueServer.handleMessageFromClient(mockMessage, mockClient);
        }
        catch (IllegalArgumentException e)
        {
            
        }
      
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
        
        mockPlayer = mock(Player.class);
        when(mockPlayer.getID()).thenReturn(0);
        when(mockPlayer.getOut()).thenReturn(true);
        when(mockPlayer.getNumCards()).thenReturn(1);
        when(mockPlayer.getClueCards()).thenReturn(clueCardArray);
        
        mockMessage = mock(Message.class);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getMove()).thenReturn(Move.PLAYERADDED);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        try
        {
            clueServer.start(false);
        }
        catch (NullPointerException e)
        {
            
        }
        
        mockMessage = mock(Message.class);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getMove()).thenReturn(Move.ACTION);
        when(mockMessage.getType()).thenReturn(Message.Type.ALLSNOOPRIGHT);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        try
        {
            clueServer.handleMessageFromClient(mockMessage, mockClient);
        }
        catch (IllegalArgumentException e)
        {
            
        }
      
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
        
        mockMessage = mock(Message.class);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getMove()).thenReturn(Move.ACTION);
        when(mockMessage.getType()).thenReturn(Message.Type.SNOOP);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        when(mockMessage.getTarget()).thenReturn(mockPlayer2);
        
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        try {
            verify(mockClient, times(9)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    @Test
    public void testHandleSnoopRobot() {
        Card[] cardArray = {new ActionCard(ActionCard.Type.SNOOP)};
        Card[] cardArray2 = {new LocationCard(LocationCard.Title.TITLE1)};
        
        clueServer.start(false);
        
        mockMessage = mock(Message.class);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getMove()).thenReturn(Move.ACTION);
        when(mockMessage.getType()).thenReturn(Message.Type.SNOOP);
        when(mockMessage.getPlayer()).thenReturn(mockRobotPlayer);
        when(mockMessage.getTarget()).thenReturn(mockPlayer2);
        
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        try {
            verify(mockClient, times(7)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }

    }
    
    @Test
    public void testHandleSuperSleuth() {
        //Card[] cardArray - The all snoop left card being sent to server.
        Card[] cardArray = {new ActionCard(ActionCard.Type.SSMALE)};
        
        clueServer.start(false);
        
        mockMessage = mock(Message.class);
        when(mockMessage.getMove()).thenReturn(Move.ACTION);
        when(mockMessage.getType()).thenReturn(Type.SUPERSLEUTH);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        try {
            verify(mockClient, times(7)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
     
    @Test
    public void testHandleSuperSleuthDisconnect() throws IOException {
        //Card[] cardArray - The all snoop left card being sent to server.
        Card[] cardArray = {new ActionCard(ActionCard.Type.SSMALE)};
        
        clueServer.start(false);
        clueServer.listen();
        
        mockMessage = mock(Message.class);
        when(mockMessage.getMove()).thenReturn(Move.ACTION);
        when(mockMessage.getType()).thenReturn(Type.SUPERSLEUTH);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        clueServer.clientDisconnected(mockClient2);
        
        try {
            verify(mockClient, times(10)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    public void testHandleSuperSleuthNoResponse() {
        //Card[] cardArray - The all snoop left card being sent to server.
        Card[] cardArray = {new ActionCard(ActionCard.Type.SSBLUE)};
        
        clueServer.start(false);
        
        mockMessage = mock(Message.class);
        when(mockMessage.getMove()).thenReturn(Move.ACTION);
        when(mockMessage.getType()).thenReturn(Type.SUPERSLEUTH);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        try {
            verify(mockClient, times(3)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    } 
    
    public void testHandleSuperSleuthRobotResponse() {
        Card[] cardArray = {new ActionCard(ActionCard.Type.SSMALE)};
        
        clueCardArray = new Card[3];
        
        clueCardArray[0] = new LocationCard(Title.TITLE2);
        clueCardArray[1] = new SuspectCard(Name.NAME2);
        clueCardArray[2] = new VehicleCard(Model.MODEL2);
        
        mockRobotPlayer = mock(RobotPlayer.class);
        when(mockRobotPlayer.getID()).thenReturn(1);
        when(mockRobotPlayer.getNumCards()).thenReturn(3);
        when(mockRobotPlayer.getClueCards()).thenReturn(clueCardArray);
        when(mockRobotPlayer.getName()).thenReturn("_MRobot_");
        
        mockMessage = mock(Message.class);
        when(mockMessage.getMove()).thenReturn(Move.PLAYERADDED);
        when(mockMessage.getPlayer()).thenReturn(mockRobotPlayer);
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        clueServer.start(false);
        
        mockMessage = mock(Message.class);
        when(mockMessage.getMove()).thenReturn(Move.ACTION);
        when(mockMessage.getType()).thenReturn(Type.SUPERSLEUTH);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        try {
            verify(mockClient, times(5)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    @Test
    public void testHandleSuggestion() {
        Card[] cardArray = {new LocationCard(LocationCard.Title.TITLE2), 
            new SuspectCard(SuspectCard.Name.NAME1), 
            new VehicleCard(VehicleCard.Model.MODEL1),
            new ActionCard(ActionCard.Type.SUGGESTALL)};
        
        clueServer.start(false);
        
        mockMessage = mock(Message.class);
        when(mockMessage.getMove()).thenReturn(Move.ACTION);
        when(mockMessage.getType()).thenReturn(Type.SUGGESTION);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        try
        {
            clueServer.handleMessageFromClient(mockMessage, mockClient);
        }
        catch (IllegalArgumentException e)
        {
            
        }
        
        try {
            verify(mockClient, times(9)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    @Test
    public void testHandleSuggestionNoResponse() {
        Card[] cardArray = {new LocationCard(LocationCard.Title.TITLE1), 
            new SuspectCard(SuspectCard.Name.NAME1), 
            new VehicleCard(VehicleCard.Model.MODEL1),
            new ActionCard(ActionCard.Type.SUGGESTALL)};
        
        clueServer.start(false);
        
        mockMessage = mock(Message.class);
        when(mockMessage.getMove()).thenReturn(Move.ACTION);
        when(mockMessage.getType()).thenReturn(Type.SUGGESTION);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getID()).thenReturn(0);
        try
        {
            clueServer.handleMessageFromClient(mockMessage, mockClient);
        }
        catch (IllegalArgumentException e)
        {
            
        }
        
        try {
            verify(mockClient, times(9)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    @Test
    public void testHandleSuggestionRobotReponse() {
        Card[] cardArray = {new LocationCard(LocationCard.Title.TITLE2), 
            new SuspectCard(SuspectCard.Name.NAME1), 
            new VehicleCard(VehicleCard.Model.MODEL1),
            new ActionCard(ActionCard.Type.SUGGESTALL)};
        
        clueCardArray = new Card[3];
        
        clueCardArray[0] = new LocationCard(Title.TITLE2);
        clueCardArray[1] = new SuspectCard(Name.NAME2);
        clueCardArray[2] = new VehicleCard(Model.MODEL2);
        
        mockRobotPlayer = mock(RobotPlayer.class);
        when(mockRobotPlayer.getID()).thenReturn(1);
        when(mockRobotPlayer.getNumCards()).thenReturn(3);
        when(mockRobotPlayer.getClueCards()).thenReturn(clueCardArray);
        when(mockRobotPlayer.getName()).thenReturn("_MRobot_");
        
        when(mockRobotPlayer.hasCard(clueCardArray[0])).thenReturn(true);
        
        mockMessage = mock(Message.class);
        when(mockMessage.getMove()).thenReturn(Move.PLAYERADDED);
        when(mockMessage.getPlayer()).thenReturn(mockRobotPlayer);
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        clueServer.start(false);

        mockMessage = mock(Message.class);
        when(mockMessage.getMove()).thenReturn(Move.ACTION);
        when(mockMessage.getType()).thenReturn(Type.SUGGESTION);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);

        clueServer.handleMessageFromClient(mockMessage, mockClient);

        try {
            verify(mockClient, times(13)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    @Test
    public void testPrivateTip() {
        
        //Card[] cardArray - The all snoop left card being sent to server.
        Card[] cardArray = {new ActionCard(ActionCard.Type.PTDESTINATION)};
        
        //Tell mockmessage to return ACTION when getMove
        when(mockMessage.getMove()).thenReturn(Move.ACTION);
        //Tell mockmessage to return SUPERSLEUTH when getType
        when(mockMessage.getType()).thenReturn(Type.PRIVATETIP);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        when(mockMessage.getTarget()).thenReturn(mockPlayer2);
        
        //connect mockClient to clueServer
        clueServer.clientConnected(mockClient);
        
        //start the clueServer without shuffling
        clueServer.start(false);
        
        //call handleMessageFromClient
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
       
        try {
            verify(mockClient, times(10)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    @Test
    public void testHandleAccusation() {
        
        //Card[] cardArray - The all snoop left card being sent to server.
        Card[] cardArray = {new SuspectCard(SuspectCard.Name.NAME1),
                new LocationCard(LocationCard.Title.TITLE1),
                new VehicleCard(VehicleCard.Model.MODEL1)};
        
        clueServer.start(false);
        
        mockMessage = mock(Message.class);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getMove()).thenReturn(Move.ACCUSATION);
        when(mockMessage.getType()).thenReturn(null);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        try {
            verify(mockClient, times(8)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    @Test
    public void testHandleAccusationGameOver() {
        
        //Card[] cardArray - The all snoop left card being sent to server.
        Card[] cardArray = {new SuspectCard(SuspectCard.Name.NAME1),
                new LocationCard(LocationCard.Title.TITLE1),
                new VehicleCard(VehicleCard.Model.MODEL1)};
        
        clueServer.clientConnected(mockClient);
        clueServer.clientConnected(mockClient);
        clueServer.clientConnected(mockClient);
        
        clueServer.start(false);
        
        mockMessage = mock(Message.class);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getMove()).thenReturn(Move.ACCUSATION);
        when(mockMessage.getType()).thenReturn(null);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer2);
        
        try
        {
            clueServer.handleMessageFromClient(mockMessage, mockClient);
        }
        catch (IllegalArgumentException e)
        {
            
        }
        when(mockPlayer2.getID()).thenReturn(1);
        try
        {
            clueServer.handleMessageFromClient(mockMessage, mockClient);
        }
        catch (IllegalArgumentException e)
        {
            
        }
        when(mockPlayer2.getID()).thenReturn(2);
        try
        {
            clueServer.handleMessageFromClient(mockMessage, mockClient);
        }
        catch (IllegalArgumentException e)
        {
            
        }
        when(mockPlayer2.getID()).thenReturn(3);
        try
        {
            clueServer.handleMessageFromClient(mockMessage, mockClient);
        }
        catch (IllegalArgumentException e)
        {
            
        }
        when(mockPlayer2.getID()).thenReturn(4);
        try
        {
            clueServer.handleMessageFromClient(mockMessage, mockClient);
        }
        catch (IllegalArgumentException e)
        {
            
        }
        
        
        
        try {
            verify(mockClient, times(9)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    @Test
    public void testReplacePlayersList() {
        
        //Card[] cardArray - The all snoop left card being sent to server.
        ArrayList<Player> players = new ArrayList<Player>();
        Player player = new Player("TEST",0,new LocationCard(LocationCard.Title.TITLE1));
        player.addCard(new ActionCard(ActionCard.Type.SNOOP));
        player.addCard(new ActionCard(ActionCard.Type.SNOOP));
        players.add(player);
        
        when(mockMessage.getPlayers()).thenReturn(players.toArray(new Player[0]));
        
        //Tell mockmessage to return NULL when getType
        when(mockMessage.getMove()).thenReturn(Move.UPDATELOG);
        //Tell mockmessage to return NULL when getType
        when(mockMessage.getType()).thenReturn(null);
        
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
        when(mockMessage.getMove()).thenReturn(Move.ENDTURN);
        
        clueServer.start(false);
      
        assertEquals(0, clueServer.getTurn());
        clueServer.clientConnected(mockClient);
        try
        {
            clueServer.handleMessageFromClient(mockMessage, mockClient);
        }
        catch (IllegalArgumentException e)
        {
            
        }
        
        assertEquals(1, clueServer.getTurn());
    } 
    
    public void testMistimedResponse() throws IOException
    {
        mockMessage = mock(Message.class);
        when(mockMessage.getMove()).thenReturn(Move.ENDTURN);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer2);
        
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
    
    @Test
    public void testPlayerMoved()
    {
        Card[] cardArray =
        {
            new LocationCard(LocationCard.Title.TITLE1)
        };

        clueServer.start(false);

        mockMessage = mock(Message.class);
        when(mockMessage.getCards()).thenReturn(cardArray);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        when(mockMessage.getMove()).thenReturn(Move.PLAYERMOVED);
        
        clueServer.handleMessageFromClient(mockMessage, mockClient);

        try
        {
            verify(mockClient, times(8)).sendToClient(any(Message.class));
        }
        catch (IOException ioException)
        {
            fail();
        }
    }
    
    @Test
    public void testGameStartedDefect407()
    {
        //assertEquals(-1, clueServer.getTurn());
        
        clueServer.clientConnected(mockClient);
        
        //assertEquals(-1, clueServer.getTurn());
        
        try {
            verify(mockClient, times(5)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
        
        mockMessage = mock(Message.class);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer);
        when(mockMessage.getMove()).thenReturn(Move.PLAYERADDED);
        
        clueServer.handleMessageFromClient(mockMessage, mockClient);
        
        assertEquals(-1, clueServer.getTurn());
        
        
        try {
            verify(mockClient, times(6)).sendToClient(any(Message.class));
        } catch (IOException ioException) {
            fail();
        }
    }
    
    public void testLobbyCreationDefect427()
    {
        
    }
    
    public void testPlayerMovedDefect459()
    {
        testPlayerMoved();
    }

    @Test
    public void testTwoClientDisconnectedGameStartedDifferentTurn() throws IOException
    {
        clueServer.start(false);
        clueServer.listen();
        
        //call clientDisconnected
        clueServer.clientDisconnected(mockClient2);
    }
    
    @Test
    public void testClientDisconnected()
    {
        clueServer.clientDisconnected(mockClient);
    }
  
    @Test
    public void testWrongMessageID()
    {
        clueServer.start(false);
        
        mockMessage = mock(Message.class);
        when(mockMessage.getPlayer()).thenReturn(mockPlayer2);
        try
        {
            clueServer.handleMessageFromClient(mockMessage, mockClient2);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
        }
    }
}