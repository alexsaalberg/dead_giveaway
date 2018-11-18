package deadgiveaway.server;

import deadgiveaway.*;
import java.util.List;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Player abstract class declares methods to be used by any player in a
 * clue game, whether said player is a robot or a human.
 * @author Steven Gerick
 * @author Christopher Siu
 * @author Cameron Geehr
 * @version 2.0
 */
public class Player implements java.io.Serializable
{
    //a constant representing the max length of a player's name
    protected static final int kNameCap = 10;
    //a constant represening the max amount of action cards
    protected static final int kMaxActionCards = 2;
    //a constant representing the max amount of clue cards
    protected static final int kMaxClueCards = 4;
    //a constant representing the max amount of players
    protected static final int kMaxPlayers = 5;
    //actionCards - list of action cards this player owns
    protected ActionCard[] actionCards;
    //numActions - the number of action cards this player owns
    protected int numActions;
    //clueCards - list of clue cards this player owns
    protected Card[] clueCards;
    //numCards - the number of clue cards this player owns
    protected int numCards;
    //location - destination marker this player has
    protected LocationCard location;
    //players - list of all players
    protected Player[] players;
    //ID - int representing the ID of this player
    protected int id;
    //name - String representing a name for the player
    protected String name;
    //isOut - boolean representing if the player is eliminated
    protected Boolean isOut;
    //difficulty - int representing difficulty of the player
    protected int difficulty;
   
   /**
    * This constructor is not intended to be called publicly. It exists
    * to encapsulate shared construction functionality between HumanPlayer
    * and RobotPlayer and is public to initialize the list for GUI testing.
    * @param name the name of this player
    * @param id the ID of this player
    * @param location the location to start the player at
    */
    public Player(String name, int id, LocationCard location) 
    {
        //IF the length of the input name is greater than 10 characters THEN
        if (name.length() > kNameCap)
        {
            //TRIM the name to 10 characters
            this.name = name.substring(0, kNameCap);
        }
        //ELSE use the input name
        else
        {
            this.name = name;
        }
       
        this.location = location;
        this.id = id;
        isOut = false;
        actionCards = new ActionCard[kMaxActionCards];
        players = new Player[kMaxPlayers];
        clueCards = new Card[kMaxClueCards];
        numCards = 0;
        numActions = 0;
        difficulty = 0;
    }
   
    /**
     * add a card to this user's hand.
     * @param card the card to add
     */
    public void addCard(Card card) 
    {
        //IF the card is an action card 
        if (card instanceof ActionCard) 
        {
            // GO through all the cards
            for (int idx = 0; idx < actionCards.length; idx++)
            {
                // IF there is no card, add it and quit
                if (actionCards[idx] == null) 
                {
                    actionCards[idx] = (ActionCard)card;
                    numActions++;
                    break;
                }
            }
        }
        else 
        {
            //set the proper card
            if (numCards < clueCards.length) 
            {
                clueCards[numCards++] = card;
            }
        }
    }
   
    /**
     * Remove a card from this user's hand.
     * @param card the card to remove
     */
    public void removeActionCard(ActionCard card) 
    {
        //IF the card is in the first slot, remove that and move the first to 0
        if (card.equals(actionCards[0])) 
        {
            actionCards[0] = actionCards[1];
            actionCards[1] = null;
            numActions--;
        }
        //ELSE remove it from the second slot
        else if (card.equals(actionCards[1]))
        {
            actionCards[1] = null;
            numActions--;
        }
    }
   
    /**
     * Remove a card from this user's hand.
     * @param idx the index to remove
     */
    public void removeActionCard(int idx) 
    {
        //IF the action card is at index 0, remove it and move 1 to 0
        if (idx == 0)
        {
            actionCards[0] = actionCards[1];
            actionCards[1] = null;
        }
        //ELSE if the card is index 1, remove it
        else if (idx == 1) 
        {
            actionCards[1] = null;
        }
        numActions--;
    }
   
    /**
     * Remove a clue card from this player's hand.
     * @param card the card to remove from the clueCards list
     */
    public void removeClueCard(Card card) 
    {
        //REMOVE the card from the clue card list
        for (int idx = 0; idx < clueCards.length; idx++) 
        {
            //IF the card is found, remove it
            if (card.equals(clueCards[idx])) 
            {
                clueCards[idx] = null;
                numCards--;
                break;
            }
        }
    }
   
    /**
     * Check whether the player has the given card in their hand.
     * @param card the card to check for
     * @return whether the player has the given card in their hand
     */
    public  boolean hasCard(Card card) 
    {
        //IF the card is an ActionCard THEN
        if (card instanceof ActionCard) 
        {
            // LOOP through all cards
            for (Card crd : actionCards) 
            {
                // IF the card is found, we are done
                if (card.equals(crd))
                {
                    return true;
                }
            }
            //return actionCards.contains((ActionCard)card);
        }
        else 
        {
            // GO through every card
            for (Card crd : clueCards) 
            {
                // IF card is found, we are done
                if (card.equals(crd))
                {
                    return true;
                }
            }
           //return clueCards.contains(card);
        }
       
        return false;
    }
   
    /**
     * Returns the set of cards this player has.
     * @return The set of cards belonging to this player.
     */
    public  Card[] getClueCards() 
    {
         //RETURN a reference to the list of cards
        return clueCards;
    }
   
    /**
     * Return the list of action cards belonging to this player.
     * @return the list of ActionCards this player has
     */
    public  ActionCard[] getActionCards() 
    {
        return actionCards;
    }
   
    /**
     * Returns the ID of this player.
     * @return the ID of this player
     */
    public  int getID() 
    {
        //RETURN the ID field directly
        return id;
    }
   
    /**
     * Set the ID of this player.
     * @param newId the ID to set this player to.
     */
    public  void setID(int newId) 
    {
        //SET the ID of this player
        this.id = newId;
    }
   
    /**
     * Adds a player to this player's list of players.
     * @param player the player to add to the list
     */
    public  void addPlayer(Player player) 
    {
        //ADD a player to the list of players in the first non-null position
        for (int idx = 0; idx < players.length; idx++) 
        {
            // IF there is no player, add it
            if (players[idx] == null) 
            {
                players[idx] = player;
                break;
            }
        }
    }
   
    /**
     * Get the location of this player.
     * @return the location of this player
     */
    public  LocationCard getLocation() 
    {
        return location;
    }
   
    /**
     * Move this player to a different location.
     * @param location The location to move this player to
     */
    public  void setLocation(LocationCard location) 
    {
        this.location = location;
    }
   
    /**
     * Return the name of this player.
     * @return the name of this player
     */
    public  String getName() 
    {
        return name;
    }
   
    /**
     * Rename this player.
     * @param name The name to set for this player
     */
    public  void setName(String name) 
    {
        //IF the name is greater than 10 characters trim it to 10 and use it
        if (name.length() > kNameCap)
        {
            this.name = name.substring(0, kNameCap);
        }
        //ELSE use the input name
        else
        {
            this.name = name;
        }
    }
   
    /**
     * Sets this player as out.
     */
    public  void setOut() 
    {
        isOut = true;
    }
   
    /**
     * Returns whether this player is out.
     * @return whether this player is out
     */
    public  Boolean getOut() 
    {
        return isOut;
    }
    
    /**
     * Returns the number of action cards this player has.
     * @return the number of action cards in this player's hand
     */
    public  int getNumActions() 
    {
        return numActions;
    }
    
    /**
     * Return the number of clue cards this player has.
     * @return the number of clue cards this player has
     */
    public  int getNumCards() 
    {
        return numCards;
    }
    
    /**
     * Returns all of the players in the game.
     * @return the list of players
     */
    public Player[] getPlayers()
    {
        return players;
    }
    
    /**
     * Converts the player to a string.
     * Player(Id, name, isOut, location, clueCards, actionCards)
     * @return String representation of the message
     * @author Alex Saalberg
     */
    @Override
    public String toString() 
    {
        String returnString = "player("; //The string to be returned by this method
        
        returnString += getID();
        returnString += "; " + name;
        returnString += "; IsOut:" + isOut;
        returnString += "; " + location;
        
        returnString += "; ";
        
        //GO through all clue cards
        for(int idx = 0; idx < numCards; idx++)
        {
            returnString += idx + ":"  + clueCards[idx] + " ";
        }
        returnString += "; ";
        
        //GO through all action cards
        for(int idx = 0; idx < numActions; idx++)
        {
            returnString += idx+":"+actionCards[idx]+" "; 
        }
        returnString += ")";
        
        return returnString;
        
        
    }
    
    /**
     * Clone this player.
     * @return A new player with the same fields
     * @throws CloneNotSupportedException if the object cannot be cloned
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return deepCopy();
    }
    
    /**
     * Returns a deep copy of the state of this Player.
     * @return A deep copy of this player.
     */
    public Player deepCopy()
    {
        //Fixes defect 438
        Player clone = new Player(name, id, location); 

        clone.difficulty = difficulty;
        //Checks every action card
        for (int idx = 0; idx < numActions; idx++)
        {
            clone.actionCards[idx] = new ActionCard(actionCards[idx].getType());
        }
        clone.numActions = numActions;
        
        //COPY all clue cards into the new player
        for (int idx = 0; idx < numCards; idx++) 
        {
            // IF suspect card, clone it
            if (clueCards[idx] instanceof SuspectCard) 
            {
                SuspectCard.Name nm = ((SuspectCard)clueCards[idx]).getName();
                clone.clueCards[idx] = new SuspectCard(nm);
            }
            // ELSE IF vehicle card, clone it
            else if (clueCards[idx] instanceof VehicleCard) 
            {
                VehicleCard.Model md;
                md = ((VehicleCard)clueCards[idx]).getModel();
                clone.clueCards[idx] = new VehicleCard(md);
            }
            // ELSE it is a location card
            else 
            {
                LocationCard.Title tl = ((LocationCard)clueCards[idx]).getTitle();
                clone.clueCards[idx] = new LocationCard(tl);
            }
        }
        
        clone.numCards = numCards;
        
        //COPY all players in the list to the new players list
        clone.players = players;
        
        clone.name = name;
        clone.isOut = isOut;
        
        //IF location is not null
        if (location != null)
        {
            // CLONE location
            clone.location = new LocationCard(location.getTitle());
        }
        clone.id = id;
        
        return clone;
    }
    
    /**
     * Returns the difficulty setting of the player.
     * @return The difficulty setting of the player.
     */
    public int getDifficulty()
    {
        return difficulty;
    }

    /**
     * Set the difficulty of this player.
     * @param difficulty the difficulty to set this player to.
     */
    public void setDifficulty(int difficulty)
    {
        this.difficulty = difficulty;
    }
}
