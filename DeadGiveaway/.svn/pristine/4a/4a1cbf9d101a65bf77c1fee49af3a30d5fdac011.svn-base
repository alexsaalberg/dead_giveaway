package deadgiveaway.server;

import deadgiveaway.ActionCard;
import deadgiveaway.Card;
import deadgiveaway.LocationCard;
import deadgiveaway.Message;
import deadgiveaway.Message.Move;
import deadgiveaway.SuspectCard;
import deadgiveaway.VehicleCard;
import deadgiveaway.fake.FakeClueServerCameron;
import deadgiveaway.fake.FakePlayerCameron;
import deadgiveaway.server.ClueServer;
import deadgiveaway.server.Player;
import deadgiveaway.server.RobotPlayer;
import java.io.IOException;
import java.util.ArrayList;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;
import java.util.Random;
import static junit.framework.TestCase.assertEquals;
import ocsf.server.ConnectionToClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The RobotPlayerTest class contains methods to test the functionality
 * of the methods within the RobotPlayer class.
 * 
 * @author Cameron Geehr
 */
public class RobotPlayerIntegrationTest
{
    ClueServer server;
    ActionCard allsnoopleft, allsnoopright, ptdestination, ptfemale, 
            ptnorthdest, ptredvehicle, ptsuspect, ptvehicle, snoop, ssblue, 
            ssfemale, ssflying, ssmale, sssouth, sswest, suggestall, suggestcur;
    LocationCard loc1, loc2, loc3, loc4, loc5, loc6, loc7, loc8, loc9;
    SuspectCard sus1, sus2, sus3, sus4, sus5, sus6;
    VehicleCard veh1, veh2, veh3, veh4, veh5, veh6;
    
    Random random;
    RobotPlayer robotPlayer, robotPlayer2;
    Card[] suggestionCardArray, singleCardArray, cardArray3Long;
    Message message;
    Player player;
    ConnectionToClient mockClient;
     
    public RobotPlayerIntegrationTest() 
    {
        initializeCards();
    }
    
    @Before
    public void setUp()
    {
        server = spy(new ClueServer(5557, 0, new ClueServerHelper(false, false, false)));
        
        random = mock(Random.class);
        
        suggestionCardArray = new Card[4];
        singleCardArray = new Card[1];
        cardArray3Long = new Card[3];
                
        robotPlayer = new RobotPlayer(0, "Cameron", random, loc1);
        robotPlayer2 = new RobotPlayer(1, "Alex", random, loc2);
        
        player = new Player("player", 0, loc3);
        
        mockClient = mock(ConnectionToClient.class);
        
        server.clientConnected(mockClient);
        
        message = new Message(robotPlayer, null, null, Move.PLAYERADDED, null, null);
        
        server.handleMessageFromClient(message, mockClient);
        
        server.start(false);
        
    }
    
    @After
    public void tearDown() throws IOException
    {
        server.close();
    }
    
    public void initializeCards()
    {
        
        allsnoopleft = new ActionCard(ActionCard.Type.ALLSNOOPLEFT);
        allsnoopright = new ActionCard(ActionCard.Type.ALLSNOOPRIGHT);
        ptdestination = new ActionCard(ActionCard.Type.PTDESTINATION);
        ptfemale = new ActionCard(ActionCard.Type.PTFEMALE);
        ptnorthdest = new ActionCard(ActionCard.Type.PTNORTHDEST);
        ptredvehicle = new ActionCard(ActionCard.Type.PTREDVEHICLE);
        ptsuspect = new ActionCard(ActionCard.Type.PTSUSPECT);
        ptvehicle = new ActionCard(ActionCard.Type.PTVEHICLE);
        snoop = new ActionCard(ActionCard.Type.SNOOP);
        ssblue = new ActionCard(ActionCard.Type.SSBLUE);
        ssfemale = new ActionCard(ActionCard.Type.SSFEMALE);
        ssflying = new ActionCard(ActionCard.Type.SSFLYING);
        ssmale = new ActionCard(ActionCard.Type.SSMALE);
        sssouth = new ActionCard(ActionCard.Type.SSSOUTH);
        sswest = new ActionCard(ActionCard.Type.SSWEST);
        suggestall = new ActionCard(ActionCard.Type.SUGGESTALL);
        suggestcur = new ActionCard(ActionCard.Type.SUGGESTCUR);
        
        loc1 = new LocationCard(LocationCard.Title.TITLE1);
        loc2 = new LocationCard(LocationCard.Title.TITLE2);
        loc3 = new LocationCard(LocationCard.Title.TITLE3);
        loc4 = new LocationCard(LocationCard.Title.TITLE4);
        loc5 = new LocationCard(LocationCard.Title.TITLE5);
        loc6 = new LocationCard(LocationCard.Title.TITLE6);
        loc7 = new LocationCard(LocationCard.Title.TITLE7);
        loc8 = new LocationCard(LocationCard.Title.TITLE8);
        loc9 = new LocationCard(LocationCard.Title.TITLE9);
        
        sus1 = new SuspectCard(SuspectCard.Name.NAME1);
        sus2 = new SuspectCard(SuspectCard.Name.NAME2);
        sus3 = new SuspectCard(SuspectCard.Name.NAME3);
        sus4 = new SuspectCard(SuspectCard.Name.NAME4);
        sus5 = new SuspectCard(SuspectCard.Name.NAME5);
        sus6 = new SuspectCard(SuspectCard.Name.NAME6);
        
        veh1 = new VehicleCard(VehicleCard.Model.MODEL1);
        veh2 = new VehicleCard(VehicleCard.Model.MODEL2);
        veh3 = new VehicleCard(VehicleCard.Model.MODEL3);
        veh4 = new VehicleCard(VehicleCard.Model.MODEL4);
        veh5 = new VehicleCard(VehicleCard.Model.MODEL5);
        veh6 = new VehicleCard(VehicleCard.Model.MODEL6);
    }
    
    @Test
    public void testRobotPlayer()
    {
        assertEquals(robotPlayer.getID(), 0);
        assertEquals(robotPlayer.getName(), "Cameron");
        assertEquals(robotPlayer.getLocation(), loc1);
        
        assertEquals(robotPlayer2.getID(), 1);
        assertEquals(robotPlayer2.getName(), "Alex");
        assertEquals(robotPlayer2.getLocation(), loc2);
    }
    
    @Test
    public void testAddCard() 
    {
        //Test adding a clue card
        assertEquals(robotPlayer.getNumCards(), 0);
        assertFalse(robotPlayer.hasCard(loc1));
        assertTrue(robotPlayer.getUnseenCards().contains(loc1));
        
        robotPlayer.addCard(loc1);
        assertEquals(robotPlayer.getNumCards(), 1);
        assertFalse(robotPlayer.getUnseenCards().contains(loc1));
        assertTrue(robotPlayer.hasCard(loc1));
        assertFalse(robotPlayer.getUnseenCards().contains(loc1));
        
        robotPlayer.addCard(loc2);
        robotPlayer.addCard(loc3);
        robotPlayer.addCard(loc4);
        robotPlayer.addCard(loc5);
        assertFalse(robotPlayer.hasCard(loc5));
        
        //Test adding an action card
        assertEquals(robotPlayer.getNumActions(), 0);
        assertFalse(robotPlayer.hasCard(allsnoopleft));
        
        robotPlayer.addCard(allsnoopleft);
        assertEquals(robotPlayer.getNumActions(), 1);
        assertTrue(robotPlayer.hasCard(allsnoopleft));
        
        //Test adding over 2 action cards
        robotPlayer.addCard(allsnoopright);
        robotPlayer.addCard(snoop);
        assertTrue(robotPlayer.hasCard(allsnoopright));
        assertFalse(robotPlayer.hasCard(snoop));
    }
    
    public void testSuggestAllEasy()
    {        
        when(random.nextInt(anyInt())).thenReturn(0).thenReturn(10).thenReturn(16).thenReturn(1);
        robotPlayer.setDifficulty(0);
        robotPlayer2 = mock(RobotPlayer.class);
        suggestionCardArray[0] = loc2;
        suggestionCardArray[1] = sus2;
        suggestionCardArray[2] = veh2;
        suggestionCardArray[3] = suggestall;
        message = new Message(robotPlayer, null, null, Message.Move.ACTION,
                Message.Type.SUGGESTION, suggestionCardArray);
        
        
        message = new Message(null, null, null, Message.Move.YOURTURN,
                null, singleCardArray);
        
        robotPlayer.addPlayer(robotPlayer2);
        singleCardArray[0] = suggestall;
        robotPlayer.selectAction(message, server);
        verify(server).handleMessageFromClient(eq(message), any(ConnectionToClient.class));
        assertEquals(robotPlayer.getLocation(), loc2);
        verify(robotPlayer2).setLocation(loc1);
    }
    
    public void testSuggestAllEasy2()
    {
        random = mock(Random.class);
        when(random.nextInt(anyInt())).thenReturn(10).thenReturn(16).thenReturn(0);
        
        robotPlayer.setDifficulty(0);
        robotPlayer2 = mock(RobotPlayer.class);
        suggestionCardArray[0] = loc1;
        suggestionCardArray[1] = sus2;
        suggestionCardArray[2] = veh2;
        suggestionCardArray[3] = suggestall;
        message = new Message(robotPlayer, null, null, Message.Move.ACTION,
                Message.Type.SUGGESTION, suggestionCardArray);
        
        robotPlayer.addPlayer(robotPlayer2);

        robotPlayer.suggest(suggestall, server);
        verify(server).handleMessageFromClient(eq(message), any(ConnectionToClient.class));
        assertEquals(robotPlayer.getLocation(), loc1);
    }
    
    public void testSuggestCurMedium()
    {
        random = mock(Random.class);
        when(random.nextInt(anyInt())).thenReturn(10).thenReturn(16);
        robotPlayer.setDifficulty(1);
        suggestionCardArray[0] = loc2;
        suggestionCardArray[1] = sus2;
        suggestionCardArray[2] = veh2;
        suggestionCardArray[3] = suggestcur;
        message = new Message(robotPlayer, null, null, Message.Move.ACTION,
                Message.Type.SUGGESTION, suggestionCardArray);
        
        robotPlayer.addPlayer(robotPlayer2);

        robotPlayer.suggest(suggestcur, server);
        verify(server).handleMessageFromClient(eq(message), any(ConnectionToClient.class));
        assertEquals(robotPlayer.getLocation(), loc2);
        
    }
    
    public void testSuggestAllNoUnknownCards()
    {
        random = mock(Random.class);
        when(random.nextInt(anyInt())).thenReturn(9).thenReturn(14).thenReturn(1);
        robotPlayer = new RobotPlayer(0, "Cam", random, loc1);
        robotPlayer.setDifficulty(2);
        robotPlayer2 = mock(RobotPlayer.class);
        cardArray3Long[0] = sus1;
        cardArray3Long[1] = loc1;
        cardArray3Long[2] = veh1;
        when(robotPlayer2.getClueCards()).thenReturn(cardArray3Long);
        when(robotPlayer2.getLocation()).thenReturn(loc2);
        suggestionCardArray[0] = loc3;
        suggestionCardArray[1] = sus3;
        suggestionCardArray[2] = veh3;
        suggestionCardArray[3] = suggestall;
        Player[] playerArray = new FakePlayerCameron[5];
        playerArray[0] = robotPlayer;
        playerArray[1] = robotPlayer2;
        message = new Message(robotPlayer, null, playerArray, Message.Move.ACTION,
                Message.Type.SUGGESTION, suggestionCardArray);
        robotPlayer.addPlayer(robotPlayer);
        robotPlayer.addPlayer(robotPlayer2);
        robotPlayer.showCard(loc1);
        robotPlayer.showCard(sus1);
        robotPlayer.showCard(veh1);
        robotPlayer.suggest(suggestall, server);
        verify(server).handleMessageFromClient(eq(message), any(ConnectionToClient.class));
        assertEquals(robotPlayer.getLocation(), loc3);
    }
    
    @Test
    public void testSuggestion()
    {
        Card[] card = new Card[1];
        Message action = new Message(null, null, null, Message.Move.YOURTURN,
                null, card);
        Card[] suggestion = new Card[4];
        Card[] cards = new Card[3];
        suggestion[0] = suggestion[1] = suggestion[2] = suggestion[3] = null;
        Message msg;
        
        
        
        
        
       
        
        /*
        System.out.println("5Test SUGGESTALL and RobotPlayer has two different card types from unknown's location");
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(2);
        robot2 = mock(RobotPlayer.class);
        cards[0] = sus1;
        cards[1] = loc2;
        cards[2] = veh3;
        when(robot2.getClueCards()).thenReturn(cards);
        when(robot2.getNumCards()).thenReturn(3);
        when(robot2.getLocation()).thenReturn(loc5);
        suggestion[0] = loc2;
        suggestion[1] = sus1;
        suggestion[2] = veh3;
        suggestion[3] = suggestall;
        msg = new Message(robot1, null, null, Message.Move.ACTION,
                Message.Type.SUGGESTION, suggestion);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        robot1.showCard(sus1);
        robot1.showCard(veh3);
        robot1.suggest(suggestall, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        assertEquals(robot1.getLocation(), loc2);
        
        System.out.println("6Test SUGGESTALL and RobotPlayer has two different card types from unknown's suspect");
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(2);
        robot2 = mock(RobotPlayer.class);
        cards[0] = sus2;
        cards[1] = loc1;
        cards[2] = veh3;
        when(robot2.getClueCards()).thenReturn(cards);
        when(robot2.getNumCards()).thenReturn(3);
        when(robot2.getLocation()).thenReturn(loc5);
        suggestion[0] = loc1;
        suggestion[1] = sus2;
        suggestion[2] = veh3;
        suggestion[3] = suggestall;
        msg = new Message(robot1, null, null, Message.Move.ACTION,
                Message.Type.SUGGESTION, suggestion);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        robot1.showCard(loc1);
        robot1.showCard(veh3);
        robot1.suggest(suggestall, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        assertEquals(robot1.getLocation(), loc1);
        
        System.out.println("7Test SUGGESTALL and RobotPlayer has two different card types from unknown's vehicle");
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(2);
        robot2 = mock(RobotPlayer.class);
        cards[0] = sus2;
        cards[1] = loc1;
        cards[2] = veh4;
        when(robot2.getClueCards()).thenReturn(cards);
        when(robot2.getNumCards()).thenReturn(3);
        when(robot2.getLocation()).thenReturn(loc5);
        suggestion[0] = loc1;
        suggestion[1] = sus2;
        suggestion[2] = veh4;
        suggestion[3] = suggestall;
        msg = new Message(robot1, null, null, Message.Move.ACTION,
                Message.Type.SUGGESTION, suggestion);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        robot1.showCard(loc1);
        robot1.showCard(sus2);
        robot1.suggest(suggestall, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        assertEquals(robot1.getLocation(), loc1);
        
        System.out.println("8Test SUGGESTALL and RobotPlayer has two different card types from unknown's suspect");
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(2);
        robot2 = mock(RobotPlayer.class);
        cards[0] = sus2;
        cards[1] = loc1;
        cards[2] = veh3;
        when(robot2.getClueCards()).thenReturn(cards);
        when(robot2.getNumCards()).thenReturn(3);
        when(robot2.getLocation()).thenReturn(loc5);
        suggestion[0] = loc1;
        suggestion[1] = sus2;
        suggestion[2] = veh3;
        suggestion[3] = suggestall;
        msg = new Message(robot1, null, null, Message.Move.ACTION,
                Message.Type.SUGGESTION, suggestion);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        robot1.showCard(loc1);
        robot1.showCard(veh3);
        robot1.suggest(suggestall, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        assertEquals(robot1.getLocation(), loc1);
        
        System.out.println("9Test SUGGESTCUR and current location is known and held by next clockwise player");
        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(0).thenReturn(3); //initially at index 4 but loc1 is known so at index 3
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(2);
        robot2 = mock(RobotPlayer.class);
        cards[0] = sus2;
        cards[1] = loc1;
        cards[2] = veh3;
        when(robot2.getClueCards()).thenReturn(cards);
        when(robot2.getNumCards()).thenReturn(3);
        when(robot2.getLocation()).thenReturn(loc4);
        when(robot2.hasCard(loc1)).thenReturn(true);
        suggestion[0] = loc5;
        suggestion[1] = null;
        suggestion[2] = null;
        suggestion[3] = suggestcur;
        msg = new Message(robot1, null, null, Message.Move.PLAYERMOVED,
                Message.Type.SUGGESTION, suggestion);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        robot1.showCard(loc1);
        card[0] = suggestcur;
        robot1.selectAction(action, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        //no location check because server handles this instance
        
        System.out.println("10Test SUGGESTCUR and there is an unknown card that isn't a location");
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(2);
        robot2 = mock(RobotPlayer.class);
        cards[0] = sus4;
        cards[1] = loc2;
        cards[2] = veh3;
        when(robot2.getClueCards()).thenReturn(cards);
        when(robot2.getNumCards()).thenReturn(3);
        when(robot2.getLocation()).thenReturn(loc4);
        when(robot2.hasCard(loc2)).thenReturn(true);
        suggestion[0] = loc1;
        suggestion[1] = sus4;
        suggestion[2] = veh3;
        suggestion[3] = suggestcur;
        msg = new Message(robot1, null, null, Message.Move.ACTION,
                Message.Type.SUGGESTION, suggestion);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        robot1.showCard(loc2);
        robot1.showCard(veh3);
        robot1.showCard(loc1);
        robot1.suggest(suggestcur, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        assertEquals(robot1.getLocation(), loc1);
        
        System.out.println("11Test SUGGESTCUR and the location is unknown");
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(2);
        robot2 = mock(RobotPlayer.class);
        cards[0] = sus5;
        cards[1] = loc2;
        cards[2] = veh3;
        when(robot2.getClueCards()).thenReturn(cards);
        when(robot2.getNumCards()).thenReturn(3);
        when(robot2.getLocation()).thenReturn(loc4);
        when(robot2.hasCard(loc2)).thenReturn(true);
        suggestion[0] = loc1;
        suggestion[1] = sus5;
        suggestion[2] = veh3;
        suggestion[3] = suggestcur;
        msg = new Message(robot1, null, null, Message.Move.ACTION,
                Message.Type.SUGGESTION, suggestion);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        robot1.showCard(sus5);
        robot1.showCard(veh3);
        robot1.suggest(suggestcur, server);
        
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        assertEquals(robot1.getLocation(), loc1);
        */
    }
    
    public void testAccuse()
    {
        Random rand = mock(Random.class);
        RobotPlayer robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        Message action = new Message(robot1, null, null, 
                Message.Move.YOURACCUSATION, null, null);
        Card[] cardList = { loc1, sus1, veh1 };
        ArrayList<Card> cards = new ArrayList();
        cards.add(loc1);
        cards.add(sus1);
        cards.add(veh1);
        robot1.setUnseenCards(cards);
        Message result = new Message(robot1, null, null, 
                Message.Move.ACCUSATION, null, cardList);
        robot1.selectAction(action, server);
                verify(server).handleMessageFromClient(eq(result), any(ConnectionToClient.class));
        
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        cardList = null;
        cards = new ArrayList();
        robot1.setUnseenCards(cards);
        result = new Message(robot1, null, null, 
                Message.Move.ENDTURN, null, cardList);
        robot1.selectAction(action, server);
                verify(server).handleMessageFromClient(eq(result), any(ConnectionToClient.class));
    }
    
    public void testPrivateTip()
    {
        RobotPlayer robot1, robot2, robot3;
        Card[] action = new Card[1];
        Card[] cards = new Card[3];
        Card[] cards1 = new Card[3];
        Message msg, result;
        Random rand;
        
        //Test EASY and ptdestination
        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(0).thenReturn(1);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(0);
        robot2 = mock(RobotPlayer.class);
        cards[0] = sus4;
        cards[1] = loc2;
        cards[2] = veh3;
        action[0] = ptdestination;
        when(robot2.getClueCards()).thenReturn(cards);

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result =  new Message(robot1, robot2, null, Message.Move.ACTION,
                Message.Type.PRIVATETIP, action);
        robot1.selectAction(msg, server);
        verify(server, times(2)).handleMessageFromClient(any(Message.class), any(ConnectionToClient.class));
        //verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        
        //Test EASY and ptfemale
        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(0).thenReturn(1);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(0);
        robot2 = mock(RobotPlayer.class);
        cards[0] = sus3;
        cards[1] = loc1;
        cards[2] = veh5;
        action[0] = ptfemale;
        when(robot2.getClueCards()).thenReturn(cards);

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result =  new Message(robot1, robot2, null, Message.Move.ACTION,
                Message.Type.PRIVATETIP, action);
        robot1.selectAction(msg, server);
        verify(server, times(2)).handleMessageFromClient(any(Message.class), any(ConnectionToClient.class));
        //verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        
        //Test MEDIUM and ptnorthdest
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(1);
        robot2 = mock(RobotPlayer.class);
        cards[0] = sus4;
        cards[1] = loc1;
        cards[2] = veh6;
        action[0] = ptnorthdest;
        when(robot2.getClueCards()).thenReturn(cards);

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result =  new Message(robot1, robot2, null, Message.Move.ACTION,
                Message.Type.PRIVATETIP, action);
        robot1.selectAction(msg, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        
        //Test MEDIUM and ptredvehicle
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(1);
        robot2 = mock(RobotPlayer.class);
        cards[0] = sus5;
        cards[1] = loc2;
        cards[2] = veh3;
        action[0] = ptredvehicle;
        when(robot2.getClueCards()).thenReturn(cards);

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result = new Message(robot1, robot2, null, Message.Move.ACTION,
                Message.Type.PRIVATETIP, action);
        robot1.selectAction(msg, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        
        //Test HARD and ptsuspect
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(2);
        robot2 = mock(RobotPlayer.class);
        cards[0] = sus3;
        cards[1] = loc5;
        cards[2] = veh5;
        action[0] = ptsuspect;
        when(robot2.getClueCards()).thenReturn(cards);

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result = new Message(robot1, robot2, null, Message.Move.ACTION,
                Message.Type.PRIVATETIP, action);
        robot1.selectAction(msg, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        
        //Test HARD and ptvehicle
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(2);
        robot2 = mock(RobotPlayer.class);
        cards[0] = sus2;
        cards[1] = loc1;
        cards[2] = veh2;
        action[0] = ptvehicle;
        when(robot2.getClueCards()).thenReturn(cards);

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result = new Message(robot1, robot2, null, Message.Move.ACTION,
                Message.Type.PRIVATETIP, action);
        robot1.selectAction(msg, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        
        //Test HARD with multiple players and ptvehicle
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(2);
        robot2 = mock(RobotPlayer.class);
        robot3 = mock(RobotPlayer.class);
        cards[0] = sus2;
        cards[1] = loc1;
        cards[2] = veh2;
        cards1[0] = sus3;
        cards1[1] = loc3;
        cards1[2] = veh3;
        action[0] = ptvehicle;
        when(robot2.getClueCards()).thenReturn(cards);
        when(robot3.getClueCards()).thenReturn(cards1);

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot3);
        robot1.showCard(loc1);
        result =  new Message(robot1, robot3, null, Message.Move.ACTION,
                Message.Type.PRIVATETIP, action);
        robot1.selectAction(msg, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
    }
    
    public void testSnoop()
    {
        RobotPlayer robot1, robot2;
        Card[] action = new Card[1];
        Card[] cards = new Card[3];
        Random rand;
        Message msg;
        Message result; 
        
        //Test EASY
        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(0).thenReturn(1);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(0);
        robot2 = mock(RobotPlayer.class);
        cards[0] = sus2;
        cards[1] = loc1;
        cards[2] = veh2;
        action[0] = snoop;
        when(robot2.getClueCards()).thenReturn(cards);

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result = new Message(robot1, robot2, null, 
                Message.Move.ACTION, Message.Type.SNOOP, action);
        robot1.selectAction(msg, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        
        //Test MEDIUM
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(1);
        robot2 = mock(RobotPlayer.class);
        
        cards[0] = sus3;
        cards[1] = loc2;
        cards[2] = veh3;
        action[0] = snoop;
        when(robot2.getClueCards()).thenReturn(cards);

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result = new Message(robot1, robot2, null, 
                Message.Move.ACTION, Message.Type.SNOOP, action);
        robot1.selectAction(msg, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        
        //Test HARD
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(2);
        robot2 = new RobotPlayer(1, "Alex", rand, loc2);
        robot2.addCard(sus2);
        robot2.addCard(loc1);
        robot2.addCard(veh2);
        action[0] = snoop;
        when(robot2.getClueCards()).thenReturn(cards);

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result = new Message(robot1, robot2, null, 
                Message.Move.ACTION, Message.Type.SNOOP, action);
        robot1.selectAction(msg, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
    }
    
    public void testAllSnoop()
    {
        Message msg, result;
        
        //Test left
        singleCardArray[0] = allsnoopleft;
        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, singleCardArray);
        robotPlayer.addPlayer(robotPlayer);
        robotPlayer2.addPlayer(robotPlayer2);
        result = new Message(robotPlayer, robotPlayer2, null, 
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, singleCardArray);
        robotPlayer.selectAction(msg, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        
        //Test right
        singleCardArray[0] = allsnoopright;
        Player[] cams = {robotPlayer, robotPlayer2};
        msg = new Message(null, null, cams, Message.Move.YOURTURN,
                null, singleCardArray);
        result = new Message(robotPlayer, robotPlayer2, null, 
                Message.Move.ACTION, Message.Type.ALLSNOOPRIGHT, singleCardArray);
        robotPlayer.selectAction(msg, server);
        verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
    }
    
    public void testSuperSleuth()
    {
        RobotPlayer robot1, robot2;
        Card[] action = new Card[1];
        Random rand;
        Message msg, result;
        
        //Test ssblue
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot2 = new RobotPlayer(1, "Alex", rand, loc2);

        action[0] = ssblue;

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result = new Message(robot1, null, null, 
                Message.Move.ACTION, Message.Type.SUPERSLEUTH, action);
        robot1.selectAction(msg, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        
        //Test ssfemale
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot2 = new RobotPlayer(1, "Alex", rand, loc2);

        action[0] = ssfemale;

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result = new Message(robot1, null, null, 
                Message.Move.ACTION, Message.Type.SUPERSLEUTH, action);
        robot1.selectAction(msg, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        
        //Test ssflying
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot2 = new RobotPlayer(1, "Alex", rand, loc2);

        action[0] = ssflying;

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result = new Message(robot1, null, null, 
                Message.Move.ACTION, Message.Type.SUPERSLEUTH, action);
        robot1.selectAction(msg, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        
        //Test ssmale
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot2 = new RobotPlayer(1, "Alex", rand, loc2);

        action[0] = ssmale;

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result = new Message(robot1, null, null, 
                Message.Move.ACTION, Message.Type.SUPERSLEUTH, action);
        robot1.selectAction(msg, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        
        //Test sssouth
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot2 = new RobotPlayer(1, "Alex", rand, loc2);

        action[0] = sssouth;

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result = new Message(robot1, null, null, 
                Message.Move.ACTION, Message.Type.SUPERSLEUTH, action);
        robot1.selectAction(msg, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
        
        }
    
    public void testSSWest()
    {
        //Test sswest
        Random rand = mock(Random.class);
        RobotPlayer robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        RobotPlayer robot2 = new RobotPlayer(1, "Alex", rand, loc2);
        ActionCard[] action = new ActionCard[1];

        action[0] = sswest;
        Message msg = new Message(null, null, null, Message.Move.YOURTURN,
                        null, action);
        
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        Message result = new Message(robot1, null, null, 
                        Message.Move.ACTION, Message.Type.SUPERSLEUTH, action);
        robot1.selectAction(msg, server);
                verify(server).handleMessageFromClient(eq(msg), any(ConnectionToClient.class));
    }
    
    @Test
    public void testShowCard()
    {
        Random rand = new Random();
        RobotPlayer robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        
        ArrayList<Card> unseen = new ArrayList<>();
        unseen.add(loc1);
        unseen.add(sus1);
        unseen.add(veh1);
        robot1.setUnseenCards(unseen);
        
        unseen = robot1.getUnseenCards();
        assertTrue(unseen.contains(loc1));
        assertTrue(unseen.contains(sus1));
        assertTrue(unseen.contains(veh1));
        
        robot1.showCard(loc1);
        robot1.showCard(veh1);
        unseen = robot1.getUnseenCards();
        assertFalse(unseen.contains(loc1));
        assertTrue(unseen.contains(sus1));
        assertFalse(unseen.contains(veh1));
    }
    
    @Test
    public void testGetUnseenCards()
    {
        testShowCard();
    }
    
    @Test
    public void testSetUnseenCards()
    {
        testShowCard();
    }
    
    @Test
    public void testClone()
    {
        RobotPlayer robot1 = new RobotPlayer(0, "Cam", new Random(0), loc1);
        RobotPlayer robot2 = new RobotPlayer(1, "Austin", new Random(0), loc2);
        RobotPlayer player3 = mock(RobotPlayer.class);
        when(player3.getID()).thenReturn(2);
        when(player3.getName()).thenReturn("Steven");
        when(player3.getLocation()).thenReturn(loc3);
        RobotPlayer player4 = null;
        
        robot1.addCard(loc1);
        robot1.addCard(sus1);
        robot1.addCard(veh1);
        robot1.addCard(loc4);
        robot1.addCard(suggestall);
        robot1.addCard(suggestcur);
        robot1.addPlayer(robot2);
        robot1.addPlayer(player3);
        
        try
        {
            player4 = (RobotPlayer) robot1.clone();
        }
        catch(CloneNotSupportedException e)
        {
        }
        
        ActionCard[] actions1 = robot1.getActionCards();
        Card[] clues1 = robot1.getClueCards();
        ActionCard[] actions2 = player4.getActionCards();
        Card[] clues2 = player4.getClueCards();
        Player[] players1 = robot1.getPlayers();
        Player[] players2 = player4.getPlayers();
        
        for (int i = 0; i < robot1.getNumCards(); i++)
        {
            assertEquals(clues1[i], clues2[i]);
        }
        assertEquals(actions1[0], actions2[0]);
        assertEquals(actions1[1], actions2[1]);
        assertEquals(robot1.getNumActions(), player4.getNumActions());
        assertEquals(robot1.getNumCards(), player4.getNumCards());
        
        assertEquals(robot1.getName(), player4.getName());
        assertEquals(robot1.getOut(), player4.getOut());
        assertEquals(robot1.getLocation(), player4.getLocation());
        assertEquals(robot1.getID(), player4.getID());
        
        assertEquals(players1[0].getID(), players2[0].getID());
        assertEquals(players1[1].getID(), players2[1].getID());
        
        robot1 = new RobotPlayer(0, "Cam", new Random(0), loc1);
        robot2 = new RobotPlayer(1, "Austin", new Random(0), loc2);
        player3 = mock(RobotPlayer.class);
        when(player3.getID()).thenReturn(2);
        when(player3.getName()).thenReturn("Steven");
        when(player3.getLocation()).thenReturn(loc3);
        player4 = null;
        
        robot1.setOut();
        robot1.addCard(loc1);
        robot1.addCard(sus1);
        robot1.addCard(veh1);
        robot1.addCard(loc4);
        robot1.addCard(suggestall);
        robot1.addCard(suggestcur);
        robot1.addPlayer(robot2);
        robot1.addPlayer(player3);
        
        try
        {
            player4 = (RobotPlayer) robot1.clone();
        }
        catch(CloneNotSupportedException e)
        {
        }
        
        actions1 = robot1.getActionCards();
        clues1 = robot1.getClueCards();
        actions2 = player4.getActionCards();
        clues2 = player4.getClueCards();
        players1 = robot1.getPlayers();
        players2 = player4.getPlayers();
        
        for (int i = 0; i < robot1.getNumCards(); i++)
        {
            assertEquals(clues1[i], clues2[i]);
        }
        assertEquals(actions1[0], actions2[0]);
        assertEquals(actions1[1], actions2[1]);
        assertEquals(robot1.getNumActions(), player4.getNumActions());
        assertEquals(robot1.getNumCards(), player4.getNumCards());
        
        assertEquals(robot1.getName(), player4.getName());
        assertEquals(robot1.getOut(), player4.getOut());
        assertEquals(robot1.getLocation(), player4.getLocation());
        assertEquals(robot1.getID(), player4.getID());
        
        assertEquals(players1[0].getID(), players2[0].getID());
        assertEquals(players1[1].getID(), players2[1].getID());
    }
}