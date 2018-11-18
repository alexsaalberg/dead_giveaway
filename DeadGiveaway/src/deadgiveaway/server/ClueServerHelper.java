package deadgiveaway.server;

import deadgiveaway.ActionCard;
import deadgiveaway.Card;
import deadgiveaway.LocationCard;
import deadgiveaway.SuspectCard;
import deadgiveaway.VehicleCard;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Contains helper constants for ClueServer
 * @author Alex Saalberg
 * @version 2.0
 */
public class ClueServerHelper
{
    /**
     * kMaxPlayers - maximum number of allowed players
     */
    protected static final int kMaxPlayers = 5;
    
    /**
     * printTimeStamps - True if the server will print time stamps
     */
    private final boolean printTimeStamps;
    /**
     * printConsole - True if the server will print to console
     */
    private final boolean printConsole;
    /**
     * printDebug - True if the server will print to debug
     */
    private final boolean printDebug;
    /**
     * shuffle - Whether to shuffle or not
     */
    protected final boolean shuffle;
    
    /**
     * A helper class for ClueServer. Contains constants and print methods
     * 
     * @author Alex Saalberg
     * @param shuffle Whether to shuffle or not
     * @param printTimeStamps Whether to print timestamps with messages
     * @param printConsole Whether to print regular messages
     * @param printDebug Whether to print debug messages
     */
    public ClueServerHelper(boolean shuffle, boolean printTimeStamps,
            boolean printConsole, boolean printDebug)
    {
        //shuffle - Whether to shuffle or not.
        this.shuffle = shuffle;
        //printTimeStamps - Whether to print time stamps or not.
        this.printTimeStamps = printTimeStamps;
        //printConsole - Whether to print console messages or not.
        this.printConsole = printConsole;
        //printDebug - Whether to print debug messages or not.
        this.printDebug = printDebug;
    }
    
    /**
     * A helper class for ClueServer. Contains constants and print methods
     * 
     * @author Alex Saalberg
     * @param printTimeStamps Whether to print timestamps with messages
     * @param printConsole Whether to print regular messages
     * @param printDebug Whether to print debug messages
     */
    public ClueServerHelper(boolean printTimeStamps,
            boolean printConsole, boolean printDebug)
    {
        //Call the full constructor
        this(true, printTimeStamps, printConsole, printDebug);
    }
    
    /*
     * Prints a str if printConsole is true
     * 
     * @author Alex Saalberg
     */
    protected void printToConsole(String str)
    {
        //IF printTimeStamps is TRUE
        if (printTimeStamps)
        {
            //INITIALIZE a new DATE object called dateObj
            Date date = new Date();
            //CONCATENATE dateObj.toString() to the front of str
            str = date.toString() + " " + str;
        } //ENDIF

        //IF printConsole is TRUE
        if (printConsole)
        {
            //PRINT using System.out.println(str);
            System.out.println(str);
        } //ENDIF
    }

    /*
     * Prints a str if printDebug is true
     * 
     * @author Alex Saalberg
     */
    protected void printToDebug(String str)
    {
        //IF printTimeStamps is TRUE
        if (printTimeStamps)
        {
            //INITIALIZE a new DATE object called date
            Date date = new Date();
            //CONCATENATE dateObj.toString() to the front of str
            str = date.toString() + " " + str;
        } //ENDIF

        //IF printDebug is TRUE
        if (printDebug)
        {
            //PRINT using System.out.println(str);
            System.out.println(str);
        } //ENDiF
    }
    
    /**
     * Debugging method to print out each Player in players
     * @param players list of players in the game
     * @author Alex Saalberg
     */
    protected void printPlayers(ArrayList<Player> players)
    {
        //FOR each Player player in players
        for (Player player : players)
        {
            //PRINT using printToDebug
            printToDebug("PLIST: " + player);
        } //ENDFOR
    }
    /**
     * Helper method to sort players
     * @param players list of players in the game
     * @return ArrayList sorted list of players in the game
     */
    protected static ArrayList<Player> sortPlayers(ArrayList<Player> players)
    {
        //FOR idx from 0 to players.size-2
        for (int idx = 0; idx < players.size() - 1; idx++)
        {
            //IF players.get(idx).id > players.get(idx+1).id
            if (players.get(idx).getID() > players.get(idx + 1).getID())
            {
                //players.set(idx+1, players.set(idx, players.get(idx+1)))
                players.set(idx + 1, players.set(idx, players.get(idx + 1)));
                //IF idx > 0
                if (idx > 0)
                {
                    //DECREMENT idx
                    idx--;
                }
            }
        }
        
        return players;
    } 
    
    /**
     * Moves the player to the new location and swaps with another player
     * if necessary. Doesn't involve the location marker list.
     * @return ArrayList new list of players in the game
     * @param players list of players in the game
     * @param player the player to move
     * @param loc The location to move to
     */
    protected static ArrayList<Player> movePlayer(ArrayList<Player> players,
            Player player, LocationCard loc)
    {
        //MOVE the player to the location
        Player moved = null;
        //The list of players. Null if location stays the same
        Player[] playerList = null;
        //CHECK if there are players at that location
        for (Player player1 : players)
        {
            //IF the player is at that location
            if (player1.getLocation().equals(loc))
            {
                moved = player1;
            }
        }
        //IF there was a player at that location
        if (moved != null)
        {
            //SET the other player's location to his own
            moved.setLocation(player.getLocation());
        }
        //SET the location
        player.setLocation(loc);
        
        return players;
    }
    
    protected static ArrayList<Player> populatePlayerInternalLists(
            ArrayList<Player> players)
    {
        //ADD every Player to every other Player using addPlayer()
        //FOR every player LOOP
        for (int idx = 0; idx < kMaxPlayers; idx++)
        {
            //FOR every player LOOP
            for (int jdx = 0; jdx < kMaxPlayers; jdx++)
            {
                players.get(idx).addPlayer(players.get(jdx));
            }//ENDFOR
        } //ENDFOR
        return players;
    }
    
    protected static ArrayList<Player> replacePlayerWithRobot(
            ArrayList<Player> players, Player dcer, Random random)
    {
        Card[] dcerClueCards;
        Card[] dcerActionCards;
        RobotPlayer robotPlayer;

        //INITIALIZE robot to a new RobotPlayer with dcer's location and ID
        robotPlayer = new RobotPlayer(dcer.getID(), "bot"
                + (dcer.getID() + 1), random, dcer.getLocation());

        //SET dcerClueCards equal to the dcer's clue cards
        dcerClueCards = dcer.getClueCards();
        //SET dcerActionCards equal to the dcer's action cards
        dcerActionCards = dcer.getActionCards();

        //Fix defect #470: Using getNumCards from dcer instead of length
        //ADD the clueCards from dcerClueCards to robot
        for (int idx = 0; idx < dcer.getNumCards(); idx++)
        {
            robotPlayer.addCard(dcerClueCards[idx]);
        }
        //Fix defect #470: Using getNumCards from dcer instead of length
        //ADD the actionCards from dcerActionCards to robot
        for (int idx = 0; idx < dcer.getNumActions(); idx++)
        {
            robotPlayer.addCard(dcerActionCards[idx]);
        }
        //REMOVE the disconnected player from the player list
        players.remove(dcer);
        //ADD the RobotPlayer to the playerlist in the spot of the human
        players.add(robotPlayer.getID(), robotPlayer);

        //FOR each Player player in players
        for (Player player : players)
        {
            //ADD player to robotPlayer
            robotPlayer.addPlayer(player);
        }
        
        return players;
    }
    
    /**
     * 
     * @param players Player list to copy
     * @return Copied Player list.
     */
    protected static ArrayList<Player> deepCopyPlayers(ArrayList<Player> players)
    {
        //newList - the new list of players
        ArrayList<Player> newList = new ArrayList<Player>();
        
        //FOR idx from 0 to players.size()-1
        for (int idx = 0; idx < players.size(); idx++)
        {
            //IF the player at players.idx is null.
            if(players.get(idx) == null)
            {
                System.out.println("Null at " + idx + " in players.");
            }
            //ELSE
            else
            {
                //ADD a deepcopy of the player in players at idx to newList
                newList.add(players.get(idx).deepCopy());
            }
        }

        //SET players to newList
        return newList;
    }

    /**
     * Fills the empty spots in players with robotPlayers.
     * @param players Players list to add to.
     * @param random To pass into each RobotPlayer
     * @return A modified players.
     */
    protected static ArrayList<Player> initRobotPlayers(
            ArrayList<Player> players, Random random)
    {
        //FOR each remaining player slot (players size to kMaxPlayers)
        for (int id = players.size(); id < kMaxPlayers; id++)
        {
            //INIT a new RobotPlayer, robotPlayer
            RobotPlayer robotPlayer = new RobotPlayer(id, "bot" + (id + 1),
                    random, null);
            //ADD robotPlayer to players
            players.add(robotPlayer);
            
            //defect #431 removed.
        } //ENDFOR
        return players;
    }
    /**
     * Determines if player can respond to the card. Returns -1 if the player
     * cannot respond.
     * @param player to determine if they can respond
     * @param card the action card the player is responding to
     * @return The index of applicable clueCard.
     * @author Alex Saalberg
     */
    
    protected static int canRespondToSuperSleuth(Player player, Card card)
    {
        //IF card is not an action card, that's no bueno
        if (!(card instanceof ActionCard))
        {
            //THROW IllegalArgumentException
            throw new IllegalArgumentException(
                    "Card " + card + " is not an actionCard");
        } //ENDIF

        //IF it's not a super sleuth, also not good
        if (!("Super Sleuth".equals(card.toString())))
        {
            //TROW IllegalARgumentException
            throw new IllegalArgumentException(
                    "Card " + card + " is not a superSleuth");
        } //ENDIF

        //actionCard - The super sleuth card that the player could 
        //possibly respond to.
        ActionCard actionCard = (ActionCard) card;
        
        //playerClueCards - local copy of the player's clue cards
        Card[] playerClueCards = player.getClueCards();
        
        //FOR each card in the player's hand
        for (int idx = 0; idx < player.getNumCards(); idx++)
        {
            //IF the card from appList and player's hand match
            if (actionCard.matchesCard(playerClueCards[idx], null))
            {
                //RETURN the index of the card in the players hand.
                return idx;
            } //ENDIF
        } //ENDFOR

        //RETURN -1. (This means the player cannot respond to the superSleuth
        return -1;
    }

    /**
     * Determines if a player can disprove a suggestion
     *
     * @author Alex Saalberg
     * @param player Player we want to know about
     * @param suggest Suggestion that was given
     * @return boolean for player can/can't disprove
     */
    protected static boolean canDisproveSuggestion(Player player, Card[] suggest)
    {
        //canDisprove - whether the player can disprove the suggestion
        boolean canDisprove = false;

        //GO through all cards in the suggestion, except for the action card
        //used (thus the -1).
        for (int idx = 0; idx < suggest.length - 1; idx++)
        {
            //SET canDisprove to the OR of itself and the player containing it
            canDisprove |= player.hasCard(suggest[idx]);
        }

        //RETURN canDisprove
        return canDisprove;
    }
    
    /**
     * Finds a card in hand that can disprove the cards in suggestion.
     * @param hand the array of cards to search
     * @param suggestion the three cards suggested
     * @return Card that disproves the suggestion
     * @author Alex Saalberg
     */
    protected static Card findCardThatDisprovesSuggestion(Card[] hand,
            Card[] suggestion)
    {
        Card card = null;

        //GO through each player's clue cards
        for (Card playerCard : hand)
        {
            //GO through each suggestion
            for (Card suggest : suggestion)
            {
                //IF this is the player's card //Fixes defect #449
                if (playerCard != null && playerCard.equals(suggest))
                {
                    card = playerCard;
                }
            }
        }

        return card;
    }

    
    /**
     * Calculates the number of non-RobotPlayers in players
     * @param players list of players in the game
     * @author Alex Saalberg
     * @return Number of Player objects in players
     */
    protected static int getHumanPlayerCount(ArrayList<Player> players)
    {
        //count - number of human players
        int count = 0;

        //FOR each Player player in players
        for (Player player : players)
        {
            //IF player is not an instanceof RobotPlayer
            if (!(player instanceof RobotPlayer))
            {
                //INCREMENT count
                count++;
            }
        }

        //RETURN count
        return count;
    }
    
    /**
     * Picks a random card from a player's hand.
     * @param player the user to take a card from
     * @param random a random object to choose the card with
     * @return Card[] a random card from a player
     * @author Alex Saalberg
     */
    protected static Card[] getRandomClueCard(Player player, Random random)
    {
        //int randomCardIndex - Index of chosen random card in players hand.
        int randomCardIndex;

        //Card[] returnCardArray - Array to be returned. Will contain the
        //random card at index 0 and be of size 1.
        Card[] returnCardArray = new Card[1];

        //SET randomCardIndex equal to a random int from 0 to the size of
        //the player's hand.
        randomCardIndex = random.nextInt(player.getNumCards());

        //SET index 0 of returnCardArray equal the card at randomCardIndex in
        //the player's hand.
        returnCardArray[0] = player.getClueCards()[randomCardIndex];

        //RETURN returnCardArray.
        return returnCardArray;
    }   
}
    
    