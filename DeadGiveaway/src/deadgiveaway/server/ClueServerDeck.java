/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deadgiveaway.server;

import deadgiveaway.ActionCard;
import deadgiveaway.Card;
import deadgiveaway.LocationCard;
import deadgiveaway.SuspectCard;
import deadgiveaway.VehicleCard;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Contains deck information for a ClueServer.
 * @author alexsaalberg
 * @version 2.0
 */
public class ClueServerDeck
{
    protected static final int kSuspects = SuspectCard.Name.values().length;
    /**
     * kVehicles - number of unique Vehicle cards.
     */
    protected static final int kVehicles = VehicleCard.Type.values().length;
    /**
     * kLocations - number of unique Location cards.
     */
    protected static final int kLocations = LocationCard.Title.values().length;
    /**
     * kNumSUGGESTALL - number of suggest all cards in the actioncard deck.
     */
    protected static final int kNumSUGGESTALL = 10;
    /**
     * kNumSUGGESTCUR - number of suggest current cards in the actioncard deck.
     */
    protected static final int kNumSUGGESTCUR = 9;
    /**
     * kNumSNOOP - number of snoop cards in the actioncard deck.
     */
    protected static final int kNumSNOOP = 4;
    /**
     * kNumALLSNOOP - number of all snoop cards in the actioncard deck.
     */
    protected static final int kNumALLSNOOP = 2;
    /**
     * kSuspects - number of unique Suspect cards.
     */
    
    /**
     * list of LocationCards used to assign locations to the players.
     */
    private final ArrayList<LocationCard> locations;
    /**
     * solution - arraylist of the solution cluecards.
     */
    protected ArrayList<Card> solution;
    /**
     * deck of all action cards not in discard or a player's hand.
     */
    private final ArrayList<ActionCard> actionCards;
    /**
     * actionDiscards - deck of all discarded action cards.
     */
    private final ArrayList<ActionCard> actionDiscards;
    /**
     * clueCards - deck of all clue cards not dealt or in solution.
     */
    private final ArrayList<Card> clueCards;
    /**
     * random - a Random used for any RNG. (Shuffling)
     */
    private Random random;
    /**
     * clueServerHelper - User for printing.
     */
    private final ClueServerHelper clueServerHelper;
    
    /**
     * Only constructor for ClueServerDeck. 
     * Should only be called by a ClueServer
     * @param random Random for RNG
     * @param clueServerHelper ClueServerHelper for printing
     * @author Alex Saalberg
     */
    public ClueServerDeck(Random random, ClueServerHelper clueServerHelper)
    {
        this.random = random;
        locations = new ArrayList<LocationCard>();
        //solution - The solution to the clues.
        solution = new ArrayList<Card>();
        //actionCards - Deck of actionCards. This is the main deck.
        actionCards = new ArrayList<ActionCard>();
        //actionDiscards - Deck of used cards. Used to reshuffle deck.
        actionDiscards = new ArrayList<ActionCard>();
        //clueCards - Deck of cluecards. Used to setup game
        clueCards = new ArrayList<Card>();

        //CALL initLocationMarkets to initiliaze the location markets.
        initLocationMarkers();
        //CALL initActionCards to initialize the action cards
        initActionCards();
        //CALL initClueCards to initialize the clue cards
        initClueCards();
        
        this.clueServerHelper = clueServerHelper;
    }

    protected Player givePlayerLocationFromDeck(Player player, LocationCard card)
    {
        //IF locations contains messageCard
        if (locations.contains(card))
        {
            //ADD messagePlayer's location to locations
            locations.add(player.getLocation());
            //REMOVED messageCard from locations
            locations.remove(player);
            //SET messagePlayer's location to messageCard
            player.setLocation(card);
        }
        return player;
    }
    
    /**
     * Shuffles the location markers.
     * @author Alex Saalberg
     */
    protected void shuffleLocationMarkets()
    {
        //SHUFFLE clueCards with random
        Collections.shuffle(locations, random);
    }
    
    /**
     * Shuffles the Clue Card deck.
     * @author Alex Saalberg
     */
    protected void shuffleClueCards()
    {
        //SHUFFLE clueCards with random
        Collections.shuffle(clueCards, random);
    }
    
    /**
     * Reshuffles the action deck.
     * @author Alex Saalberg
     */
    protected void reshuffleActionDeck()
    {
        //PRINT using printToConsole; "Server: Deck Reshuffled"
        clueServerHelper.printToConsole("Server: Deck Reshuffled");
        
        //Add all cards from actionDiscards to actionCards
        actionCards.addAll(actionDiscards);
        //Shuffle actionCards
        Collections.shuffle(actionCards, random);
        //Clear actionDiscards
        actionDiscards.clear();
    }
    
    /**
     * Prints all the cards from clueCards to all the Players in players
     * "Clockwise," one at a time.
     * @param players list of all players in game
     * @return ArrayList list of players in the game
     * @author Alex Saalberg
     * @author Austin Sparks
     */
    protected ArrayList<Player> dealAllClueCards(ArrayList<Player> players)
    {
        //curCard - card being dealt.
        Card curCard;

        //idx - index of current player
        int idx = 0;

        //WHILE clueCards list is not empty LOOP
        while (!clueCards.isEmpty())
        {
            // REMOVE a clue card from list
            curCard = clueCards.remove(0);
            // ADD card to next player
            players.get(idx++).addCard(curCard);

            //IF idx  at the end of player list
            if (idx == ClueServerHelper.kMaxPlayers)
            {
                //SET idx to 0
                idx = 0;
            } // ENDIF
        } //ENDWHILE
        
        return players;
    }
    
    protected ArrayList<Player> dealActionCards(ArrayList<Player> players)
    {
        //FOR each Player player in players LOOP
        for (Player player : players)
        {
            //DRAW a card, add it to player's hand
            player.addCard(drawActionCard()[0]);
        } //ENDFOR   
        return players;
    }

    /**
     * For each player, removes a location from the location deck and gives it
     * to the player.
     * @param players list of players in the game
     * @return ArrayList list of players in the game
     * @author Alex Saalberg
     */
    protected ArrayList<Player> initPlayerLocations(ArrayList<Player> players)
    {
        //FOR each Player player in players
        for(Player player : players)
        {
            //REMOVE a location from locations, give it to player.
            player.setLocation(locations.remove(0));
        }
        
        return players;
    }
    
    /**
     * Initializes all 9 location markers.
     * @author Alex Saalberg
     */
    protected void initLocationMarkers()
    {
        
        //FOR each LocationCard.Title
        for (LocationCard.Title loc : LocationCard.Title.values())
        {
            //ADD the location to locations
            locations.add(new LocationCard(loc));
        } //ENDFOR
    }
    
    /**
     * Initializes the solution
     * @author Alex Saalberg
     */
    protected void initSolution()
    {
        int idx = 0;
        //SET idx to a random int from 0 to kLocations
        idx = random.nextInt(kLocations);
        //REMOVE from clueCards at location idx and add it to a solution
        solution.add(clueCards.remove(idx));

        //SET idx to a random int from 0 to kSuspects
        idx = random.nextInt(kSuspects);
        //REMOVE from clueCards at location idx + kLocations - 1
        //and add it to solution
        solution.add(clueCards.remove(idx + kLocations - 1));

        //SET idx to a random int from 0 to kVehicles
        idx = random.nextInt(kVehicles);
        //REMOVE from clueCards at location idx + kLocations + kSuspects - 2
        //and add it to solution
        solution.add(clueCards.remove(idx + kLocations + kSuspects - 2));
           
        //PRINT using printToDebug; "Server: The solution is <X>"
        clueServerHelper.printToDebug("The solution is "
                    + ((LocationCard) solution.get(0)).getTitle() + ", "
                    + ((SuspectCard) solution.get(1)).getName() + ", "
                    + ((VehicleCard) solution.get(2)).getModel());
    }

    /**
     * Private helper method to fill the deck
     *
     * @author Austin Sparks
     * @author Alex Saalberg
     */
    protected void initClueCards()
    {
        //FOR EACH Title title IN Title.values()
        for (LocationCard.Title title : LocationCard.Title.values())
        {
            //ADD a locationCard of title type to clueCards
            clueCards.add(new LocationCard(title));
        } //ENDFOR

        //FOR EACH Name name IN Name.values()
        for (SuspectCard.Name name : SuspectCard.Name.values())
        {
            //ADD a suspectCard of name type to clueCards
            clueCards.add(new SuspectCard(name));
        } //ENDFOR
        
        //FOR EACH Model model IN Model.values()
        for (VehicleCard.Model model : VehicleCard.Model.values())
        {
            //ADD a vehicleCard of model type to vehicleCards
            clueCards.add(new VehicleCard(model));
        } //ENDFOR
    }
    
    /**
     * Initializes the action card deck.
     * @author Alex Saalberg
     */
    protected void initActionCards()
    {
        // ADD kNumSUGGESTALL (10) suggest from any destination
        for (int idx = 0; idx < kNumSUGGESTALL; idx++)
        {
            actionCards.add(new ActionCard(ActionCard.Type.SUGGESTALL));
        }

        // ADD kNumSUGGESTCUR (9) suggest from current destination
        for (int idx = 0; idx < kNumSUGGESTCUR; idx++)
        {
            actionCards.add(new ActionCard(ActionCard.Type.SUGGESTCUR));
        }

        // ADD kNumSNOOP (4) snoop
        for (int idx = 0; idx < kNumSNOOP; idx++)
        {
            actionCards.add(new ActionCard(ActionCard.Type.SNOOP));
        }

        // ADD kNumALLSNOOP (2) all snoop left and right
        for (int idx = 0; idx < kNumALLSNOOP; idx++)
        {
            actionCards.add(new ActionCard(ActionCard.Type.ALLSNOOPLEFT));
            actionCards.add(new ActionCard(ActionCard.Type.ALLSNOOPRIGHT));
        }

        //FOR EACH Type type IN Type.values()
        for (ActionCard.Type type : ActionCard.Type.values())
        {
            ActionCard tempCard = new ActionCard(type);

            // IF type.title() is equal to "Super Sleuth"
            if (tempCard.title().equals("Super Sleuth"))
            {
                //ADD a new actionCard of Type type to actionCards
                actionCards.add(tempCard);
            }
            // ELSEIF type.title() is equal to "Private Tip"
            else if (tempCard.title().equals("Private Tip"))
            {
                //ADD a new actionCard of Type type to actionCards
                actionCards.add(tempCard);
            } //ENDIF
        } //ENDFOR
    }
    
    /**
     * Adds card to discards.
     * @param card Card to be discarded
     * @author Alex Saalberg
     */
    protected void discard(Card card)
    {
        //IF card is a ActionCard
        if(card instanceof ActionCard)
        {
            //DISCARD
            actionDiscards.add((ActionCard) card);
        } //ENDIF
    }
    
    /**
     * Draws the next card from the top of action card deck. This means removing
     * it from the arrayList and returning it. If the deck is empty return null
     * it will reshuffle it and then draw. If the deck is empty and the discard
     * pile is empty, returns null.
     * @return Card[] array of action cards drawn
     * @author Alex Saalberg
     */
    protected Card[] drawActionCard()
    {
        //Card[] returnCardArray - Array to be returned. Will contain the
        //drawn card at index 0 and be of size 1.
        Card[] returnCardArray = new Card[1];

        //IF actionCards is empty
        if (actionCards.isEmpty())
        {
            //RESHUFFLE actionCards using reshuffleDeck()
            reshuffleActionDeck();
        } //ENDIF

        //DRAW the top card from actionCards
        returnCardArray[0] = actionCards.remove(0);

        //RETURN the drawn card
        return returnCardArray;
    }
    
    /**
     * Returns solution
     * @return The solution
     */
    protected ArrayList<Card> getSolution()
    {
        //returnList - list that will be returned
        ArrayList<Card> returnList = new ArrayList<Card>();

        //FOR each Card card in solution
        for (Card card : solution)
        {
            //TRY
            try
            {
                returnList.add((Card) card.clone());
            }
            //CATCH CloneNotSupportedException e
            catch (CloneNotSupportedException ex)
            {
                System.out.println(ex);
                ex.printStackTrace();
            }
        } //ENDFOR

        //RETURN a deep copy of players.
        return returnList;
    }
    
    /**
     * Returns the action deck.
     * @return Deep copy of the action deck.
     */
    protected ArrayList<ActionCard> getActionDeck()
    {
        //returnList - list that will be returned
        ArrayList<ActionCard> returnList = new ArrayList<ActionCard>();

        //FOR each Card card in actionCards
        for (Card card : actionCards)
        {
            //TRY
            try
            {
                returnList.add((ActionCard) card.clone());
            }
            //CATCH CloneNotSupportedException e 
            catch (CloneNotSupportedException e)
            {
                System.out.println(e);
            }
        } //ENDFOR

        //RETURN a deep copy of players.
        return returnList;
    }
}
