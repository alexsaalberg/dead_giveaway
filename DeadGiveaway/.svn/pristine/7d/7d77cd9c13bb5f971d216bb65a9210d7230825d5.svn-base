package deadgiveaway.server;

import deadgiveaway.ActionCard;
import deadgiveaway.Card;
import deadgiveaway.LocationCard;
import deadgiveaway.Message;
import deadgiveaway.SuspectCard;
import deadgiveaway.VehicleCard;
import deadgiveaway.fake.FakeClueServerCameron;
import deadgiveaway.fake.FakePlayerCameron;
import java.io.IOException;
import java.util.ArrayList;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;
import java.util.Random;
import static junit.framework.TestCase.assertEquals;

/**
 * The RobotPlayerTest class contains methods to test the functionality
 * of the methods within the RobotPlayer class.
 * 
 * @author Cameron Geehr
 */
public class RobotPlayerTest extends TestCase
{
    FakeClueServerCameron server;
    ActionCard allsnoopleft, allsnoopright, ptdestination, ptfemale, 
            ptnorthdest, ptredvehicle, ptsuspect, ptvehicle, snoop, ssblue, 
            ssfemale, ssflying, ssmale, sssouth, sswest, suggestall, suggestcur;
    LocationCard loc1, loc2, loc3, loc4, loc5, loc6, loc7, loc8, loc9;
    SuspectCard sus1, sus2, sus3, sus4, sus5, sus6;
    VehicleCard veh1, veh2, veh3, veh4, veh5, veh6;
     
    public RobotPlayerTest(String testName) 
    {
        super(testName);
        
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
    
    public void setUp()
    {
        server = new FakeClueServerCameron(5557, 0, new ClueServerHelper(false, false, false));
    }
    
    public void tearDown() throws IOException
    {
        server.close();
    }
    
    //Test the constructor
    public void testRobotPlayer()
    {
        System.out.println("Testing Robot Constructor");
        Random rand = new Random();
        RobotPlayer robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        
        assertEquals(robot1.getID(), 0);
        assertEquals(robot1.getName(), "Cam");
        assertEquals(robot1.getLocation(), loc1);
        
        RobotPlayer robot2 = new RobotPlayer(0, "CameronGeehr", rand, loc2);
        
        assertEquals(robot2.getID(), 0);
        assertEquals(robot2.getName(), "CameronGee");
        assertEquals(robot2.getLocation(), loc2);
    }
    
    public void testAddCard() 
    {
        System.out.println("Testing add card");
        Random rand = new Random();
        RobotPlayer robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        RobotPlayer robot2 = new RobotPlayer(1, "Austin", rand, loc1);
        //Test adding a clue card
        assertEquals(robot1.getNumCards(), 0);
        assertFalse(robot1.hasCard(loc1));
        assertTrue(robot1.getUnseenCards().contains(loc1));
        robot1.addCard(loc1);
        assertEquals(robot1.getNumCards(), 1);
        assertFalse(robot1.getUnseenCards().contains(loc1));
        assertTrue(robot1.hasCard(loc1));
        assertFalse(robot1.getUnseenCards().contains(loc1));
        robot1.addCard(loc2);
        robot1.addCard(loc3);
        robot1.addCard(loc4);
        robot1.addCard(loc5);
        assertFalse(robot1.hasCard(loc5));
        //Test adding an action card
        assertEquals(robot2.getNumActions(), 0);
        assertFalse(robot2.hasCard(allsnoopleft));
        robot2.addCard(allsnoopleft);
        assertEquals(robot2.getNumActions(), 1);
        assertTrue(robot2.hasCard(allsnoopleft));
        robot2.addCard(allsnoopright);
        robot2.addCard(snoop);
        assertFalse(robot2.hasCard(snoop));
    }
    
    public void testSuggestion()
    {
        System.out.println("Testing suggestion");
        Card[] card = new Card[1];
        Message action = new Message(null, null, null, Message.Move.YOURTURN,
                null, card);
        RobotPlayer robot1, robot2;
        Card[] suggestion = new Card[4];
        Card[] cards = new Card[3];
        FakePlayerCameron[] playerList;
        suggestion[0] = suggestion[1] = suggestion[2] = suggestion[3] = null;
        Message msg;
        Random rand;
        
        //TESTING EASY AND MEDIUM
        System.out.println("1Test EASY AND SUGGESTALL, location should change and cards should be the correct type");
        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(0).thenReturn(10).thenReturn(16).thenReturn(1);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(0);
        robot2 = mock(RobotPlayer.class);
        suggestion[0] = loc2;
        suggestion[1] = sus2;
        suggestion[2] = veh2;
        suggestion[3] = suggestall;
        playerList = new FakePlayerCameron[5];
 //       playerList[0] = robot1;
 //       playerList[1] = robot2;
        msg = new Message(robot1, null, null, Message.Move.ACTION,
                Message.Type.SUGGESTION, suggestion);
        when(robot2.getLocation()).thenReturn(loc2);
        when(robot2.getID()).thenReturn(1);
        doNothing().when(robot2).setLocation(any(LocationCard.class));
        
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        card[0] = suggestall;
        robot1.selectAction(action, server);
        assertEquals(msg, server.getLastMessage());
        assertEquals(robot1.getLocation(), loc1);
        
        System.out.println("2Test EASY AND SUGGESTALL, location should stay same and cards should be the correct type");
        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(10).thenReturn(16).thenReturn(0);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(0);
        robot2 = mock(RobotPlayer.class);
        suggestion[0] = loc1;
        suggestion[1] = sus2;
        suggestion[2] = veh2;
        suggestion[3] = suggestall;
        msg = new Message(robot1, null, null, Message.Move.ACTION,
                Message.Type.SUGGESTION, suggestion);
        when(robot2.getLocation()).thenReturn(loc2);
        
        robot1.addPlayer(robot2);

        robot1.suggest(suggestall, server);
        assertEquals(msg, server.getLastMessage());
        assertEquals(robot1.getLocation(), loc1);
        
        System.out.println("3Test MEDIUM and SUGGESTCUR, location should stay same and cards should be correct types");
        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(10).thenReturn(16);
        robot1 = new RobotPlayer(0, "Cam", rand, loc2);
        robot1.setDifficulty(1);
        robot2 = mock(RobotPlayer.class);
        suggestion[0] = loc2;
        suggestion[1] = sus2;
        suggestion[2] = veh2;
        suggestion[3] = suggestcur;
        msg = new Message(robot1, null, null, Message.Move.ACTION,
                Message.Type.SUGGESTION, suggestion);
        
        robot1.addPlayer(robot2);

        robot1.suggest(suggestcur, server);
        assertEquals(msg, server.getLastMessage());
        assertEquals(robot1.getLocation(), loc2);
        
        //TESTING HARD
        System.out.println("4Test SUGGESTALL and no players with unknown cards, should just be random");
        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(9).thenReturn(14).thenReturn(1);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(2);
        robot2 = mock(RobotPlayer.class);
        cards[0] = sus1;
        cards[1] = loc1;
        cards[2] = veh1;
        when(robot2.getClueCards()).thenReturn(cards);
        when(robot2.getLocation()).thenReturn(loc2);
        suggestion[0] = loc3;
        suggestion[1] = sus3;
        suggestion[2] = veh3;
        suggestion[3] = suggestall;
        playerList = new FakePlayerCameron[5];
//       playerList[0] = robot1;
//       playerList[1] = robot2;
        msg = new Message(robot1, null, null, Message.Move.ACTION,
                Message.Type.SUGGESTION, suggestion);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.showCard(loc1);
        robot1.showCard(sus1);
        robot1.showCard(veh1);
        robot1.suggest(suggestall, server);
        assertEquals(msg, server.getLastMessage());
        assertEquals(robot1.getLocation(), loc1);
        
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
        playerList = new FakePlayerCameron[5];
//        playerList[0] = robot1;
//        playerList[1] = robot2;
        msg = new Message(robot1, null, null, Message.Move.ACTION,
                Message.Type.SUGGESTION, suggestion);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.showCard(sus1);
        robot1.showCard(veh3);
        robot1.suggest(suggestall, server);
        assertEquals(msg, server.getLastMessage());
        assertEquals(robot1.getLocation(), loc1);
        
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
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.showCard(loc1);
        robot1.showCard(veh3);
        robot1.suggest(suggestall, server);
        assertEquals(msg, server.getLastMessage());
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
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.showCard(loc1);
        robot1.showCard(sus2);
        robot1.suggest(suggestall, server);
        assertEquals(msg, server.getLastMessage());
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
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        
        robot1.showCard(loc1);
        robot1.showCard(veh3);
        robot1.suggest(suggestall, server);
        assertEquals(msg, server.getLastMessage());
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
                null, suggestion);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        robot1.showCard(loc1);
        card[0] = suggestcur;
        robot1.selectAction(action, server);
        assertEquals(msg, server.getLastMessage());
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
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.showCard(loc2);
        robot1.showCard(veh3);
        robot1.showCard(loc1);
        robot1.suggest(suggestcur, server);
        assertEquals(msg, server.getLastMessage());
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
        assertEquals(msg, server.getLastMessage());
        assertEquals(robot1.getLocation(), loc1);
    }
    
    public void testAccuse()
    {
        System.out.println("Testing accuse");
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
        assertEquals(result, server.getLastMessage());
        
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        cardList = null;
        cards = new ArrayList();
        robot1.setUnseenCards(cards);
        result = new Message(robot1, null, null, 
                Message.Move.ENDTURN, null, cardList);
        robot1.selectAction(action, server);
        assertEquals(result, server.getLastMessage());
    }
    
    public void testPrivateTip()
    {
        System.out.println("Testing private tip");
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
        assertEquals(result, server.getLastMessage());
        
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
        assertEquals(result, server.getLastMessage());
        
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
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        result =  new Message(robot1, robot2, null, Message.Move.ACTION,
                Message.Type.PRIVATETIP, action);
        robot1.selectAction(msg, server);
        assertEquals(result, server.getLastMessage());
        
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
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        result = new Message(robot1, robot2, null, Message.Move.ACTION,
                Message.Type.PRIVATETIP, action);
        robot1.selectAction(msg, server);
        assertEquals(result, server.getLastMessage());
        
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
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        result = new Message(robot1, robot2, null, Message.Move.ACTION,
                Message.Type.PRIVATETIP, action);
        robot1.selectAction(msg, server);
        assertEquals(result, server.getLastMessage());
        
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
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        result = new Message(robot1, robot2, null, Message.Move.ACTION,
                Message.Type.PRIVATETIP, action);
        robot1.selectAction(msg, server);
        assertEquals(result, server.getLastMessage());
        
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
        robot1.addPlayer(robot3);
        robot1.addPlayer(robot3);
        robot1.showCard(loc1);
        result =  new Message(robot1, robot2, null, Message.Move.ACTION,
                Message.Type.PRIVATETIP, action);
        robot1.selectAction(msg, server);
        assertEquals(result, server.getLastMessage());
    }
    
    public void testSnoop()
    {
        System.out.println("Testing snoop");
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
        assertEquals(result, server.getLastMessage());
        
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
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        result = new Message(robot1, robot2, null, 
                Message.Move.ACTION, Message.Type.SNOOP, action);
        robot1.selectAction(msg, server);
        assertEquals(result, server.getLastMessage());
        
        //Test HARD
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot1.setDifficulty(2);
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
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        robot1.addPlayer(robot2);
        result = new Message(robot1, robot2, null, 
                Message.Move.ACTION, Message.Type.SNOOP, action);
        robot1.selectAction(msg, server);
        assertEquals(result, server.getLastMessage());
    }
    
    public void testAllSnoop()
    {
        System.out.println("Testing all snoop");
        RobotPlayer robot1, robot2;
        Card[] action = new Card[1];
        Random rand;
        Message msg, result;
        
        //Test left
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot2 = mock(RobotPlayer.class);
        action[0] = allsnoopleft;
        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result = new Message(robot1, robot2, null, 
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, action);
        robot1.selectAction(msg, server);
        assertEquals(result, server.getLastMessage());
        
        //Test right
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot2 = mock(RobotPlayer.class);
        action[0] = allsnoopright;
//        FakePlayerCameron[] cams = {robot1, robot2};
//        msg = new Message(null, null, cams, Message.Move.YOURTURN,
//                null, action);
        result = new Message(robot1, null, null, 
                Message.Move.ACTION, Message.Type.ALLSNOOPRIGHT, action);
        robot1.selectAction(msg, server);
        assertEquals(result, server.getLastMessage());
    }
    
    public void testSuperSleuth()
    {
        System.out.println("Testing super sleuth");
        RobotPlayer robot1, robot2;
        Card[] action = new Card[1];
        Random rand;
        Message msg, result;
        
        //Test ssblue
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot2 = mock(RobotPlayer.class);

        action[0] = ssblue;

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result = new Message(robot1, null, null, 
                Message.Move.ACTION, Message.Type.SUPERSLEUTH, action);
        robot1.selectAction(msg, server);
        assertEquals(result, server.getLastMessage());
        
        //Test ssfemale
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot2 = mock(RobotPlayer.class);

        action[0] = ssfemale;

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result = new Message(robot1, null, null, 
                Message.Move.ACTION, Message.Type.SUPERSLEUTH, action);
        robot1.selectAction(msg, server);
        assertEquals(result, server.getLastMessage());
        
        //Test ssflying
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot2 = mock(RobotPlayer.class);

        action[0] = ssflying;

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result = new Message(robot1, null, null, 
                Message.Move.ACTION, Message.Type.SUPERSLEUTH, action);
        robot1.selectAction(msg, server);
        assertEquals(result, server.getLastMessage());
        
        //Test ssmale
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot2 = mock(RobotPlayer.class);

        action[0] = ssmale;

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result = new Message(robot1, null, null, 
                Message.Move.ACTION, Message.Type.SUPERSLEUTH, action);
        robot1.selectAction(msg, server);
        assertEquals(result, server.getLastMessage());
        
        //Test sssouth
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot2 = mock(RobotPlayer.class);

        action[0] = sssouth;

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result = new Message(robot1, null, null, 
                Message.Move.ACTION, Message.Type.SUPERSLEUTH, action);
        robot1.selectAction(msg, server);
        assertEquals(result, server.getLastMessage());
        
        //Test sswest
        rand = mock(Random.class);
        robot1 = new RobotPlayer(0, "Cam", rand, loc1);
        robot2 = mock(RobotPlayer.class);

        action[0] = sswest;

        msg = new Message(null, null, null, Message.Move.YOURTURN,
                null, action);
        
        robot1.addPlayer(robot1);
        robot1.addPlayer(robot2);
        result = new Message(robot1, null, null, 
                Message.Move.ACTION, Message.Type.SUPERSLEUTH, action);
        robot1.selectAction(msg, server);
        assertEquals(result, server.getLastMessage());
    }
    
    public void testShowCard()
    {
        System.out.println("Testing show card");
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
    
    public void testGetUnseenCards()
    {
        testShowCard();
    }
    
    public void testSetUnseenCards()
    {
        testShowCard();
    }
    
    public void testClone()
    {
        System.out.println("Testing clone");
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