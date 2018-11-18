package deadgiveaway;

import deadgiveaway.server.Player;
import java.util.Arrays;

/**
 * The Message class defines the objects that are passed between the clients
 * and the server. The class provides data regarding who made a move,
 * what the move was, who was targeted by the move, if any,
 * and what cards were involved.
 * 
 * @version 1.0
 * @author Stephen Gerick
 * 
 * @version 2.0
 * @author Jon Kuzmich
 */
public class Message implements java.io.Serializable 
{
    /**
     * Enumerator designating the type of move
     */
    public static enum Move 
    {
        /** Enum value for a player playing an Action card */
        ACTION,
        /** Enum value for a player making a suggestion */
        SUGGESTION,
        /** Enum value for a player making an accusation */
        ACCUSATION,
        /** Enum value for a player to take their turn */
        YOURTURN,
        /** Enum value for a player running out of time on their turn */
        TIMEOUT,
        /** Enum value for a player to make an accusation or end their turn */
        YOURACCUSATION,
        /** Enum value for a player showing a card */
        SHOWNCARDS,
        /** Enum value for a player needing to update their turn log */
        UPDATELOG,
        /** Enum value for a player needing to disprove a suggestion */
        DISPROVESUGGESTION,
        /** Enum value for the game starting */
        GAMESTARTED,
        /** Enum value for a player connecting to the server */
        CONNECTIONESTABLISHED,
        /** Enum value for a player unable to connect to the server */
        CONNECTIONREFUSED,
        /** Enum value for a player joining the game */
        PLAYERADDED,
        /** Enum value for interfaces needing to update displayed cards */
        UPDATECARDS,
        /** Enum value for a player being able to resume their turn */
        RESUMETURN,
        /** Enum value for a player ending their turn */
        ENDTURN,
        /** Enum value for a player moving to a new location */
        PLAYERMOVED,
        /** Enum value for a lobby being created */
        LOBBYSTARTED;
        
        private static final String[] kString =
        {   "Action Card",
            "Suggestion",
            "Accusation",
            "Your turn",
            "Time is out",
            "Your accusation",
            "You were shown card(s)",
            "Update your log",
            "Disprove the suggestion",
            "The game has started",
            "Connection established",
            "Connection refused",
            "Player added",
            "Update displayed cards",
            "Resume your turn",
            "End turn",
            "Player moved to a different location",
            "The lobby has been created"
        };
        
        /**
         * Returns this action as a string
         * @return A description of what the message's move is.
         */
        @Override
        public String toString() 
        {
            return kString[this.ordinal()];
        }
    }
    
    /**
     * Enumerator designating the type of ACTION or ACCUSATION the move is.
     */
    public static enum Type 
    {
        /** Enum value for an All Snoop Left Action card */
        ALLSNOOPLEFT,
        /** Enum value for an All Snoop Right Action card */
        ALLSNOOPRIGHT,
        /** Enum value for a Super Sleuth Action card */
        SUPERSLEUTH,
        /** Enum value for a Private Tip Action card */
        PRIVATETIP,
        /** Enum value for a Suggestion (SuggestAll or SuggestCur) */
        SUGGESTION, 
        /** Enum value for a player making a correct accusation */
        CORRECTACCUSATION,
        /** Enum value for a player making an incorrect accusation */
        INCORRECTACCUSATION,
        /** Enum value for a Snoop Action card */
        SNOOP,
        /** Enum value for a game lobby existing when the game has not started */
        LOBBYEXISTS;
        
        private static final String[] kString =
        {   "All Snoop Left",
            "All Snoop Right",
            "Super Sleuth",
            "Private Tip",
            "Suggestion",
            "Correct Accusation",
            "Incorrect Accusation",
            "Snoop",
            "Lobby Exists"
        };
        
        /**
         * Returns this move type as a string.
         * @return A description of what the message's type is.
         */
        @Override
        public String toString() 
        {
            return kString[this.ordinal()];
        }
    }
   
    //player - Player variable designating the player who made a move.
    private Player player;
    //target - Player variable designating the player who is affected by the move.
    private Player target;
    //playerList - for communicating the list of all players
    private Player[] playerList;
    //cards - Arraylist of Card type designating the cards involved in this move.
    private Card[] cards;
    //move - Move variable designating the type of move this is.
    private Move move;
    //msg - a String primitive to hold miscellanious information
    private Type type;

    /**
     * Constructs a new message.
     * @param player The player sending the message
     * @param target The player targeted by the move
     * @param players the list of players in the game
     * @param move The type of move the player is making
     * @param type The subtype of move the player is making
     * @param cards The cards involved in the move
     * card.
     */
    public Message(Player player, Player target, Player[] players,
            Move move, Type type, Card[] cards) 
    {
        //INITIALIZE player
        this.player = player;
        //INITIALIZE target
        this.target = target;
        //INITIALIZE players
        playerList = players;
        //INITIALIZE move
        this.move = move;
        //INITIALIZE type
        this.type = type;
        //INITIALIZE cards
        this.cards = cards;
    }
   
    /**
     * Checks whether two message are equal.
     * @param object The message to check for equivalence in
     * @return whether the messages are equivalent
     */
    @Override
    public boolean equals(Object object) 
    {
        //RETURN true if player, target, players, move, type, and cards are same
        //RETURN false otherwise
        return object instanceof Message &&
            ((Message)object).player == this.player &&
            ((Message)object).target == this.target && 
            ((Message)object).move == this.move && 
            ((Message)object).type == this.type &&
            Arrays.equals(this.playerList, ((Message)object).playerList) &&
            Arrays.equals(this.cards, ((Message)object).cards);
    }
   
    /**
     * Returns the list of cards associated with this message
     * @return the cards contained by this message
     */
    public Card[] getCards() 
    {
        return cards;
    }
   
    /**
     * Return the player that made this move
     * @return the player that made this move
     */
    public Player getPlayer() 
    {
        return player;
    }
   
    /**
     * Return the player targeted by this move, if any
     * @return the player targeted by this move, if any
     */
    public Player getTarget() 
    {
        return target;
    }
   
    /**
     * Return the list of players associated with this message.
     * @return the list of players in this message
     */
    public Player[] getPlayers() 
    {
        return playerList;
    }
   
    /**
     * Return the type of move this message represents
     * @return the type of move this message represents
     */
    public Move getMove() 
    {
        return move;
    }
    
    /**
     * Return the subtype of move this message represents
     * @return the subtype of move this message represents
     */
    public Type getType() 
    {
        return type;
    }
   
    /**
     * Makes a deep copy of the message
     * @return Copy of the message
     * @throws CloneNotSupportedException if the Message cannot be cloned
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        //clone - the cloned message we will return
        Message clone = new Message(null, null, null, null, null, null);
        
        // TRY to clone the player and target
        clone.player = (Player)player.clone();
        clone.target = (Player)target.clone();
        clone.playerList = new Player[playerList.length];
        
        // PUT a copy of each player in the new list
        for (int idx = 0; idx < playerList.length; idx++) 
        {
            // TRY to clone the player
            clone.playerList[idx] = (Player)playerList[idx].clone();
        }
       
        clone.cards = new Card[cards.length];
       
        // PUT a copy of each card in the new list
        for (int idx = 0; idx < cards.length; idx++) 
        {
            // TRY to clone the card
            clone.cards[idx] = (Card)cards[idx].clone();
        }
       
        clone.move = move;
        clone.type = type;
       
        return clone;
    }
   
    /**
     * Converts the message to a string
     * @return String representation of the message
     * @author Alex Saalberg
     * @author Jon Kuzmich
     */
    @Override
    public String toString() 
    {
        //returnString - the string we will return
        String returnString = "Msg(";

        //APPEND player information
        if (player == null) 
        {
            returnString += "NULL; ";
        } 
        else 
        {
            returnString += player.getID() + ":" + player.getName() + "; ";
        }
       
        //APPEND target information
        if (target == null) 
        {
            returnString += "NULL; ";
        }
        else 
        {
            returnString += target.getID() + ":" + target.getName() + "; ";
        }
       
        //APPEND playerList information
        if (playerList == null) 
        {
            returnString += "NULL; ";
        } 
        else 
        {
            returnString += "LIST; ";
        }
       
        //APPEND move information
        returnString += objectUtilToString(move) + "; ";
       
        //APPEND type information
        returnString += objectUtilToString(type) + "; ";
       
        //APPEND cards information
        if (cards == null)
        {
            returnString += "NULL";
        }
        else 
        {
            returnString += ("" + cards.length + " card(s): ");
            
            // IF there is a card, add it to the string
            if (cards.length > 0) 
            {
                returnString += ("" + cards[0]);
            }
        }
        
        return returnString + ")";
    }
    
    private String objectUtilToString(Object object)
    {
        String returnString;
        
        //IF the object is null
        if (object == null)
        {
            //SET the returned string to null
            returnString = "NULL";
        }
        //ELSE
        else
        {
            //SET the return string to the object's string representation
            returnString = object.toString();
        }
        
        return returnString;
    }
}