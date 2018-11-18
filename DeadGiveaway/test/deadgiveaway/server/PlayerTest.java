package deadgiveaway.server;

import deadgiveaway.ActionCard;
import deadgiveaway.Card;
import deadgiveaway.LocationCard;
import deadgiveaway.SuspectCard;
import deadgiveaway.VehicleCard;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * The Player class contains methods to test the functionality
 * of the methods within the RobotPlayer class.
 * 
 * @author Cameron Geehr
 */
public class PlayerTest extends TestCase
{
    ActionCard allsnoopleft, allsnoopright, ptdestination, ptfemale, 
            ptnorthdest, ptredvehicle, ptsuspect, ptvehicle, snoop, ssblue, 
            ssfemale, ssflying, ssmale, sssouth, sswest, suggestall, suggestcur;
    LocationCard loc1, loc2, loc3, loc4, loc5, loc6, loc7, loc8, loc9;
    SuspectCard sus1, sus2, sus3, sus4, sus5, sus6;
    VehicleCard veh1, veh2, veh3, veh4, veh5, veh6;
     
    public PlayerTest(String testName) 
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
    }
    
    public void testPlayer()
    {
        Player player1 = new Player("Cam", 0, loc1);
        
        assertEquals(player1.getID(), 0);
        assertEquals(player1.getName(), "Cam");
        assertEquals(player1.getLocation(), loc1);
        assertFalse(player1.getOut());
        
        Player player2 = new Player("CameronGeehr", 1, loc2);
        
        assertEquals(player2.getID(), 1);
        assertEquals(player2.getName(), "CameronGee");
        assertEquals(player2.getLocation(), loc2);
        assertFalse(player2.getOut());

    }
    
    public void testAddCard()
    {
        Player player1 = new Player("Cam", 0, loc1);
        
        player1.addCard(sus1);
        assertTrue(player1.hasCard(sus1));
        
        player1.addCard(sus2);
        assertTrue(player1.hasCard(sus2));
        
        player1.addCard(loc1);
        assertTrue(player1.hasCard(loc1));
        
        player1.addCard(veh1);
        assertTrue(player1.hasCard(veh1));
        
        player1.addCard(sus3);
        assertFalse(player1.hasCard(sus3));
        
        player1.addCard(suggestall);
        assertTrue(player1.hasCard(suggestall));
        
        player1.addCard(suggestcur);
        assertTrue(player1.hasCard(suggestall));
        
        player1.addCard(snoop);
        assertFalse(player1.hasCard(snoop));
    }
    
    public void testRemoveActionCard()
    {
        Player player1 = new Player("Cam", 0, loc1);
        
        player1.addCard(suggestall);
        player1.addCard(suggestcur);
        player1.removeActionCard(suggestcur);
        assertFalse(player1.hasCard(suggestcur));
        
        player1.addCard(suggestcur);
        player1.removeActionCard(suggestall);
        assertFalse(player1.hasCard(suggestall));
        
        Player player2 = new Player("Austin", 1, loc2);
        
        player2.addCard(suggestall);
        player2.addCard(suggestcur);
        player2.removeActionCard(1);
        assertFalse(player2.hasCard(suggestcur));
        
        player2.addCard(suggestcur);
        player2.removeActionCard(0);
        assertFalse(player2.hasCard(suggestall));
        player2.removeActionCard(2);
        assertEquals(player2.getNumActions(), 0);
    }

    public void testRemoveClueCard()
    {
        Player player1 = new Player("Cam", 0, loc1);

        player1.addCard(sus1);
        player1.addCard(sus2);
        player1.removeClueCard(sus2);
        assertFalse(player1.hasCard(sus2));

        player1.addCard(sus2);
        player1.removeClueCard(sus1);
        assertFalse(player1.hasCard(sus1));
        player1.removeClueCard(sus3);
        assertEquals(player1.getNumCards(), 1);
    }

    public void testHasCard()
    {
        Player player1 = new Player("Cam", 0, loc1);

        assertFalse(player1.hasCard(sus1));
        player1.addCard(sus1);
        assertTrue(player1.hasCard(sus1));
        player1.addCard(sus2);
        assertTrue(player1.hasCard(sus2));
        player1.removeClueCard(sus2);
        assertFalse(player1.hasCard(sus2));

        player1.addCard(sus2);
        assertTrue(player1.hasCard(sus2));
        player1.removeClueCard(sus1);
        assertFalse(player1.hasCard(sus1));

        assertFalse(player1.hasCard(suggestall));
        player1.addCard(suggestall);
        assertTrue(player1.hasCard(suggestall));
        player1.addCard(suggestcur);
        assertTrue(player1.hasCard(suggestcur));
        player1.removeActionCard(suggestcur);
        assertFalse(player1.hasCard(suggestcur));

        player1.addCard(loc1);
        assertTrue(player1.hasCard(loc1));
        player1.removeClueCard(loc1);
        assertFalse(player1.hasCard(loc1));
    }

    public void testGetClueCards()
    {
        Player player1 = new Player("Cam", 0, loc1);
        
        player1.addCard(sus1);
        player1.addCard(sus2);
        player1.addCard(loc1);
        player1.addCard(veh1);
        
        Card[] cards = player1.getClueCards();
        
        assertEquals(sus1, cards[0]);
        assertEquals(sus2, cards[1]);
        assertEquals(loc1, cards[2]);
        assertEquals(veh1, cards[3]);
    }
    
    public void testGetActionCards()
    {
        Player player1 = new Player("Cam", 0, loc1);
        
        player1.addCard(suggestall);
        player1.addCard(suggestcur);
        
        ActionCard[] cards = player1.getActionCards();
        
        assertEquals(suggestall, cards[0]);
        assertEquals(suggestcur, cards[1]);
    }
    
    public void testGetDifficulty()
    {
        Player player1 = new Player("Cam", 0, loc1);
        
        player1.setDifficulty(1);
        assertEquals(player1.getDifficulty(), 1);
        player1.setDifficulty(2);
        assertEquals(player1.getDifficulty(), 2);
        player1.setDifficulty(3);
        assertEquals(player1.getDifficulty(), 3);
    }
    
    public void testSetDifficulty()
    {
        testGetDifficulty();
    }
    
    public void testGetID()
    {
        Player player1 = new Player("Cam", 0, loc1);
        
        player1.setID(1);
        assertEquals(player1.getID(), 1);
        player1.setID(3);
        assertEquals(player1.getID(), 3);
    }
    
    public void testSetID()
    {
        testGetID();
    }
    
    public void testAddPlayer()
    {
        Player player1 = new Player("Cam", 0, loc1);
        Player player2 = new Player("Austin", 1, loc2);
        Player player3 = new Player("Steven", 2, loc3);
        Player player4 = new Player("Quang", 3, loc4);
        Player player5 = new Player("Jon", 4, loc5);
        Player player6 = new Player("Brad", 5, loc6);
        
        player1.addPlayer(player1);
        player1.addPlayer(player2);
        player1.addPlayer(player3);
        player1.addPlayer(player4);
        player1.addPlayer(player5);
        player1.addPlayer(player6);
        
        Player[] players = player1.getPlayers();
        
        assertEquals(players.length, 5);
        assertEquals(player1, players[0]);
        assertEquals(player2, players[1]);
        assertEquals(player3, players[2]);
        assertEquals(player4, players[3]);
        assertEquals(player5, players[4]);
    }
    
    public void testGetPlayers()
    {
        testAddPlayer();
    }
    
    public void testGetLocation()
    {
        Player player1 = new Player("Cam", 0, loc1);
        
        assertEquals(player1.getLocation(), loc1);
        player1.setLocation(loc2);
        assertEquals(player1.getLocation(), loc2);
    }
    
    public void testSetLocation()
    {
        testGetLocation();
    }
    
    public void testGetName()
    {
        Player player1 = new Player("Cam", 0, loc1);
        
        assertEquals(player1.getName(), "Cam");
        player1.setName("Austin");
        assertEquals(player1.getName(), "Austin");
    }
    
    public void testSetName()
    {
        Player player1 = new Player("Cam", 0, loc1);
        
        assertEquals(player1.getName(), "Cam");
        player1.setName("AustinSparks");
        assertEquals(player1.getName(), "AustinSpar");
    }
    
    public void testSetOut()
    {
        Player player1 = new Player("Cam", 0, loc1);
        
        assertFalse(player1.getOut());
        player1.setOut();
        assertTrue(player1.getOut());
        player1.setOut();
        assertTrue(player1.getOut());
    }
    
    public void testGetOut()
    {
        testSetOut();
    }
    
    public void testGetNumActions()
    {
        Player player1 = new Player("Cam", 0, loc1);
        
        assertEquals(player1.getNumActions(), 0);
        player1.addCard(suggestall);
        assertEquals(player1.getNumActions(), 1);
        player1.addCard(suggestcur);
        assertEquals(player1.getNumActions(), 2);
        player1.addCard(allsnoopleft);
        assertEquals(player1.getNumActions(), 2);
        player1.removeActionCard(suggestcur);
        assertEquals(player1.getNumActions(), 1);
    }
    
    public void testGetNumCards()
    {
        Player player1 = new Player("Cam", 0, loc1);
        
        assertEquals(player1.getNumCards(), 0);
        player1.addCard(loc1);
        assertEquals(player1.getNumCards(), 1);
        player1.addCard(loc2);
        assertEquals(player1.getNumCards(), 2);
        player1.addCard(sus1);
        assertEquals(player1.getNumCards(), 3);
        player1.addCard(veh1);
        assertEquals(player1.getNumCards(), 4);
        player1.addCard(veh2);
        assertEquals(player1.getNumCards(), 4);
        player1.removeClueCard(veh1);
        assertEquals(player1.getNumCards(), 3);
    }
    
    public void testToString()
    {
        Player player1 = new Player("Cam", 0, loc1);
        
        player1.addCard(loc1);
        player1.addCard(suggestall);
        assertEquals(player1.toString(), "player(0; Cam; IsOut:false; Athena's Forest; 0:Athena's Forest ; 0:Suggestion )");
    }
    
    public void testClone()
    {
        Player player1 = new Player("Cam", 0, loc1);
        Player player2 = new Player("Austin", 1, loc2);
        
        Player player4 = null;
        
        player1.addCard(loc1);
        player1.addCard(sus1);
        player1.addCard(veh1);
        player1.addCard(loc4);
        player1.addCard(suggestall);
        player1.addCard(suggestcur);
        player1.addPlayer(player2);
        
        try
        {
            player4 = (Player) player1.clone();
        }
        catch(CloneNotSupportedException e)
        {
            
        }
        
        ActionCard[] actions1 = player1.getActionCards();
        Card[] clues1 = player1.getClueCards();
        ActionCard[] actions2 = player4.getActionCards();
        Card[] clues2 = player4.getClueCards();
        Player[] players1 = player1.getPlayers();
        Player[] players2 = player4.getPlayers();
        
        for (int i = 0; i < player1.getNumCards(); i++)
        {
            assertEquals(clues1[i], clues2[i]);
        }
        assertEquals(actions1[0], actions2[0]);
        assertEquals(actions1[1], actions2[1]);
        assertEquals(player1.getNumActions(), player4.getNumActions());
        assertEquals(player1.getNumCards(), player4.getNumCards());
        
        assertEquals(player1.getName(), player4.getName());
        assertEquals(player1.getOut(), player4.getOut());
        assertEquals(player1.getLocation(), player4.getLocation());
        assertEquals(player1.getID(), player4.getID());
        
        assertEquals(players1[0].getID(), players2[0].getID());
        
        player1 = new Player("Cam", 0, loc1);
        player2 = new Player("Austin", 1, loc2);
        player4 = null;
        
        player1.setOut();
        player1.addCard(loc1);
        player1.addCard(sus1);
        player1.addCard(veh1);
        player1.addCard(loc4);
        player1.addCard(suggestall);
        player1.addCard(suggestcur);
        player1.addPlayer(player2);
        
        try
        {
            player4 = (Player) player1.clone();
        }
        catch(CloneNotSupportedException e)
        {
        }
        
        actions1 = player1.getActionCards();
        clues1 = player1.getClueCards();
        actions2 = player4.getActionCards();
        clues2 = player4.getClueCards();
        players1 = player1.getPlayers();
        players2 = player4.getPlayers();
        
        for (int i = 0; i < player1.getNumCards(); i++)
        {
            assertEquals(clues1[i], clues2[i]);
        }
        assertEquals(actions1[0], actions2[0]);
        assertEquals(actions1[1], actions2[1]);
        assertEquals(player1.getNumActions(), player4.getNumActions());
        assertEquals(player1.getNumCards(), player4.getNumCards());
        
        assertEquals(player1.getName(), player4.getName());
        assertEquals(player1.getOut(), player4.getOut());
        assertEquals(player1.getLocation(), player4.getLocation());
        assertEquals(player1.getID(), player4.getID());
        
        assertEquals(players1[0].getID(), players2[0].getID());
    }
    
    public void testDeepCopy()
    {
        Player player1 = new Player("Cam", 0, loc1);
        Player player2 = new Player("Austin", 1, loc2);
        
        Player player4 = null;
        
        player1.addCard(loc1);
        player1.addCard(sus1);
        player1.addCard(veh1);
        player1.addCard(loc4);
        player1.addCard(suggestall);
        player1.addCard(suggestcur);
        player1.addPlayer(player2);
        
        player4 = (Player) player1.deepCopy();
        
        ActionCard[] actions1 = player1.getActionCards();
        Card[] clues1 = player1.getClueCards();
        ActionCard[] actions2 = player4.getActionCards();
        Card[] clues2 = player4.getClueCards();
        Player[] players1 = player1.getPlayers();
        Player[] players2 = player4.getPlayers();
        
        for (int i = 0; i < player1.getNumCards(); i++)
        {
            assertEquals(clues1[i], clues2[i]);
        }
        assertEquals(actions1[0], actions2[0]);
        assertEquals(actions1[1], actions2[1]);
        assertEquals(player1.getNumActions(), player4.getNumActions());
        assertEquals(player1.getNumCards(), player4.getNumCards());
        
        assertEquals(player1.getName(), player4.getName());
        assertEquals(player1.getOut(), player4.getOut());
        assertEquals(player1.getLocation(), player4.getLocation());
        assertEquals(player1.getID(), player4.getID());
        
        assertEquals(players1[0].getID(), players2[0].getID());
        
        player1 = new Player("Cam", 0, loc1);
        player2 = new Player("Austin", 1, loc2);
        player4 = null;
        
        player1.setOut();
        player1.addCard(loc1);
        player1.addCard(sus1);
        player1.addCard(veh1);
        player1.addCard(loc4);
        player1.addCard(suggestall);
        player1.addCard(suggestcur);
        player1.addPlayer(player2);
        
        player4 = (Player) player1.deepCopy();
        
        actions1 = player1.getActionCards();
        clues1 = player1.getClueCards();
        actions2 = player4.getActionCards();
        clues2 = player4.getClueCards();
        players1 = player1.getPlayers();
        players2 = player4.getPlayers();
        
        for (int i = 0; i < player1.getNumCards(); i++)
        {
            assertEquals(clues1[i], clues2[i]);
        }
        assertEquals(actions1[0], actions2[0]);
        assertEquals(actions1[1], actions2[1]);
        assertEquals(player1.getNumActions(), player4.getNumActions());
        assertEquals(player1.getNumCards(), player4.getNumCards());
        
        assertEquals(player1.getName(), player4.getName());
        assertEquals(player1.getOut(), player4.getOut());
        assertEquals(player1.getLocation(), player4.getLocation());
        assertEquals(player1.getID(), player4.getID());
        
        assertEquals(players1[0].getID(), players2[0].getID());
    }
}