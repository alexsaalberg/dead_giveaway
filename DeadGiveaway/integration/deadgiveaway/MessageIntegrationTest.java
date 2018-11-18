package deadgiveaway;

import deadgiveaway.ActionCard;
import deadgiveaway.Card;
import deadgiveaway.LocationCard;
import deadgiveaway.Message;
import deadgiveaway.SuspectCard;
import deadgiveaway.server.*;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit Test class for Message
 * @author Jon Kuzmich
 */
public class MessageIntegrationTest 
{
    @Before
    public void setUp()
    {
        
    }
    /**
     * Test of constructor, of class Message.
     */
    @Test
    public void testConstructor() 
    {
        Player player = new Player("Bill", 0, 
                new LocationCard(LocationCard.Title.TITLE1));
        Player target = new Player("Jill", 1, 
                new LocationCard(LocationCard.Title.TITLE2));
        Card card = new ActionCard(ActionCard.Type.ALLSNOOPLEFT);
        Player[] playerList = new Player[] {};
        Card[] cardList = new Card[] {card};
        
        Message message = new Message(player, target, playerList,
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardList);
        
        assertEquals(player, message.getPlayer());
        assertEquals(target, message.getTarget());
        assertEquals(playerList, message.getPlayers());
        assertEquals(Message.Move.ACTION, message.getMove());
        assertEquals(Message.Type.ALLSNOOPLEFT, message.getType());
        assertEquals(cardList, message.getCards());
    }
    /**
     * Test of equals method, of class Message.
     */
    @Test
    public void testEquals() 
    {
        Player player = new Player("Bill", 0, 
                new LocationCard(LocationCard.Title.TITLE1));
        Player target = new Player("Jill", 1, 
                new LocationCard(LocationCard.Title.TITLE2));
        Card card = new SuspectCard(SuspectCard.Name.NAME1);
        Player[] playerList = new Player[] {};
        Card[] cardList = new Card[] {card};
        
        Message message1 = new Message(player, target, playerList,
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardList);
        
        Message message2 = new Message(player, target, playerList,
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardList);
        
        Message message3 = new Message(target, player, playerList,
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardList);
        
        Message message4 = new Message(target, player, playerList,
                Message.Move.ACCUSATION, Message.Type.SNOOP, cardList);
        
        Message empty1 = new Message(null, null, null, null, null, null);
        Message empty2 = new Message(null, null, null, null, null, null);
        
        assertEquals(message1, message1);
        assertEquals(message1, message2);
        assertNotEquals(message1, message3);
        assertNotEquals(message3, message4);
        assertNotEquals(message3, empty1);
        assertEquals(empty1, empty2);
        
        message2 = new Message(player, player, playerList,
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardList);
        assertNotEquals(message1, message2);
        
        message2 = new Message(player, target, new Player[] {player},
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardList);
        assertNotEquals(message1, message2);
        
        message2 = new Message(player, target, playerList,
                Message.Move.ACTION, Message.Type.SNOOP, cardList);
        assertNotEquals(message1, message2);
        
        message2 = new Message(player, target, playerList,
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, new Card[] {});
        assertNotEquals(message1, message2);
        
        assertNotEquals(message1, "hello");
    }

    /**
     * Test of clone method, of class Message.
     */
    @Test
    public void testClone() throws Exception 
    {
        Player player = new Player("Bill", 1, 
                new LocationCard(LocationCard.Title.TITLE1));
        Player target = new Player("Jill", 2, 
                new LocationCard(LocationCard.Title.TITLE2));
        Card card = new SuspectCard(SuspectCard.Name.NAME1);
        for(int pindx = 0; pindx < 5; pindx++)
        {
            player.addPlayer(player);
        }
        
        for(int tindx = 0; tindx < 5; tindx++)
        {
            target.addPlayer(player);
        }
 
        Player[] playerList = new Player[] {player, target};
        Card[] cardList = new Card[] {card};
        
        Message message = new Message(player, target, playerList,
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardList);
         
        Message clone = (Message)message.clone();
        
   }

    /**
     * Test of toString method, of class Message.
     */
    @Test
    public void testToString() 
    {
        Player player = new Player("Bot1", 1, 
                new LocationCard(LocationCard.Title.TITLE1));
        Player target = new Player("Bot2", 2, 
                new LocationCard(LocationCard.Title.TITLE2));
        Card card = new SuspectCard(SuspectCard.Name.NAME1);
        Player[] playerList = new Player[] {};
        Card[] cardList = new Card[] {card};
        
        Message message = new Message(null, null, null, null, null, null);
        assertEquals("Msg(NULL; NULL; NULL; NULL; NULL; NULL)",
                message.toString());
        
        message = new Message(player, target, playerList,
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardList);
        
        
        assertEquals("Msg(1:Bot1; 2:Bot2; LIST; Action Card; All Snoop Left;"
                + " 1 card(s): Aphrodite)", message.toString());
        
        message = new Message(player, target, playerList,
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, new Card[] {});
        
        assertEquals("Msg(1:Bot1; 2:Bot2; LIST; Action Card; All Snoop Left;"
                + " 0 card(s): )", message.toString());
    }
    
}
