package deadgiveaway;

import deadgiveaway.server.*;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit Test class for Message
 * @author Jon Kuzmich
 */
public class MessageTest 
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
        Player mockedPlayer = mock(Player.class);
        Player mockedTarget = mock(Player.class);
        Card mockedCard = mock(Card.class);
        Player[] playerList = new Player[] {};
        Card[] cardList = new Card[] {mockedCard};
        
        Message message = new Message(mockedPlayer, mockedTarget, playerList,
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardList);
        
        assertEquals(mockedPlayer, message.getPlayer());
        assertEquals(mockedTarget, message.getTarget());
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
        Player mockedPlayer = mock(Player.class);
        Player mockedTarget = mock(Player.class);
        Card mockedCard = mock(Card.class);
        Player[] playerList = new Player[] {};
        Card[] cardList = new Card[] {mockedCard};
        
        Message message1 = new Message(mockedPlayer, mockedTarget, playerList,
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardList);
        
        Message message2 = new Message(mockedPlayer, mockedTarget, playerList,
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardList);
        
        Message message3 = new Message(mockedTarget, mockedPlayer, playerList,
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardList);
        
        Message message4 = new Message(mockedTarget, mockedPlayer, playerList,
                Message.Move.ACCUSATION, Message.Type.SNOOP, cardList);
        
        Message empty1 = new Message(null, null, null, null, null, null);
        Message empty2 = new Message(null, null, null, null, null, null);
        
        assertEquals(message1, message1);
        assertEquals(message1, message2);
        assertNotEquals(message1, message3);
        assertNotEquals(message3, message4);
        assertNotEquals(message3, empty1);
        assertEquals(empty1, empty2);
        
        message2 = new Message(mockedPlayer, mockedPlayer, playerList,
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardList);
        assertNotEquals(message1, message2);
        
        message2 = new Message(mockedPlayer, mockedTarget, new Player[] {mockedPlayer},
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardList);
        assertNotEquals(message1, message2);
        
        message2 = new Message(mockedPlayer, mockedTarget, playerList,
                Message.Move.ACTION, Message.Type.SNOOP, cardList);
        assertNotEquals(message1, message2);
        
        message2 = new Message(mockedPlayer, mockedTarget, playerList,
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
        Player mockedPlayer = mock(Player.class);
        Player mockedTarget = mock(Player.class);
        Card mockedCard = mock(Card.class);
        Player[] playerList = new Player[] {mockedPlayer};
        Card[] cardList = new Card[] {mockedCard};
        
        Message message = new Message(mockedPlayer, mockedTarget, playerList,
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardList);
        
        when(mockedPlayer.clone()).thenReturn(mockedPlayer);
        when(mockedTarget.clone()).thenReturn(mockedTarget);
        when(mockedCard.clone()).thenReturn(mockedCard);
        
        Message clone = (Message)message.clone();
        
        when(mockedCard.equals(mockedCard)).thenReturn(true);
        
        assertEquals(message, clone);
    }

    /**
     * Test of toString method, of class Message.
     */
    @Test
    public void testToString() 
    {
        Player mockedPlayer = mock(Player.class);
        Player mockedTarget = mock(Player.class);
        Card mockedCard = mock(Card.class);
        Player[] playerList = new Player[] {};
        Card[] cardList = new Card[] {mockedCard};
        
        Message message = new Message(null, null, null, null, null, null);
        assertEquals("Msg(NULL; NULL; NULL; NULL; NULL; NULL)",
                message.toString());
        
        message = new Message(mockedPlayer, mockedTarget, playerList,
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, cardList);
        
        when(mockedPlayer.getID()).thenReturn(1);
        when(mockedPlayer.getName()).thenReturn("Bot1");
        when(mockedTarget.getID()).thenReturn(2);
        when(mockedTarget.getName()).thenReturn("Bot2");
        when(mockedCard.toString()).thenReturn("Card1");
        
        assertEquals("Msg(1:Bot1; 2:Bot2; LIST; Action Card; All Snoop Left;"
                + " 1 card(s): Card1)", message.toString());
        
        message = new Message(mockedPlayer, mockedTarget, playerList,
                Message.Move.ACTION, Message.Type.ALLSNOOPLEFT, new Card[] {});
        
        assertEquals("Msg(1:Bot1; 2:Bot2; LIST; Action Card; All Snoop Left;"
                + " 0 card(s): )", message.toString());
    }
    
}
