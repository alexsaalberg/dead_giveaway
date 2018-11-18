package deadgiveaway.fake;

import deadgiveaway.*;
import deadgiveaway.server.*;
import deadgiveaway.server.Player;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Fake player for testing.
 * @author Cameron Geehr
 */
public class FakePlayerCameron extends Player
{
    /** Number of cards */
    protected int numCards;
    /** Number of actions */
    protected int numActions;
    /** Player name */
    protected String name;
    /** Player id */
    protected int id;
    /** Robot difficulty */
    protected int difficulty;
    /** List of other players */
    protected List<FakePlayerCameron> players;
    
    /**
     * Constructor.
     * @param name Player name
     * @param id Player id
     * @param location Player's location
     */
    public FakePlayerCameron(String name, int id, LocationCard location)
    {
        super(name, id, location);
        this.name = name;
        this.id = id;
        this.location = location;
        numCards = 0;
        numActions = 0;
        players = new ArrayList();
    }
    
    /**
     * Get the name of the player
     * @return name of the player
     */
    public String getName()
    {
        // IF name is what i want
        if (name.equals("CameronGeehr"))
        {
            return "CameronGeh";
        }
        // ELSE 
        else
        {
            return name;
        }
    }
    
    /**
     * Get player id
     * @return player id
     */
    public int getID()
    {
        return id;
    }
    
    /*public LocationCard getLocation()
    {
        return new LocationCard(location.getTitle());
    }
    
    public void setLocation(LocationCard locaton)
    {
        this.location = new LocationCard(location.getTitle());
    }*/
    
    /**
     * Get the number of cards
     * @return number of cards
     */
    public int getNumCards()
    {
        return numCards;
    }
    
    /**
     * Get the number of actions
     * @return number of actions
     */
    public int getNumActions()
    {
        return numActions;
    }
    
    /**
     * Determines if player has given card
     * @param card card to test
     * @return true if has card
     */
    public boolean hasCard(Card card)
    {
        // IF card is action card
        if (card instanceof ActionCard)
        {
            return numActions > 0 && numActions < 2;
        }
        // ELSE
        else
        {
            return numCards > 0 && numCards < 4;
        }
    }
    
    /**
     * Add a player to the list
     * @param player new player
     */
    public void addPlayer(FakePlayerCameron player)
    {
        players.add(player);
    }
    
    /**
     * Get the players in the list
     * @return players
     */
    public FakePlayerCameron[] getPlayers()
    {
        return players.toArray(new FakePlayerCameron[players.size()]);
    }
    
    /**
     * Set the difficulty
     * @param difficulty new diff
     */
    public void setDifficulty(int difficulty)
    {
        this.difficulty = difficulty;
    }
    
    /**
     * Get the diff
     * @return diff
     */
    public int getDifficulty()
    {
        return difficulty;
    }
}
