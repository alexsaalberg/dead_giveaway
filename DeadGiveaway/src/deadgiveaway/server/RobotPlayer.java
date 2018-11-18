package deadgiveaway.server;

import deadgiveaway.ActionCard;
import deadgiveaway.Card;
import deadgiveaway.LocationCard;
import deadgiveaway.Message;
import deadgiveaway.SuspectCard;
import deadgiveaway.VehicleCard;
import java.util.ArrayList;
import java.util.Random;
import deadgiveaway.fake.*;
import static deadgiveaway.server.Player.kMaxActionCards;
import java.util.Arrays;

/**
 * The RobotPlayer class implements the methods declared in the Player
 * abstract class for use by an AI/robot player.
 *
 * @author Steven
 * @author Jon Kuzmich
 * @author Cameron Geehr
 * @version 2.0
 */
public class RobotPlayer extends Player
{
    //kSuggestionSize - the amount of cards in a suggestion or accusation
    private final int kSuggestionSize = 4;
    //kNotesSize - the amount of players to keep notes on
    private final int kNotesSize = 4;
    //kMinUnseen - the minimum amount of unseen cards
    private final int kMinUnseen = 3;
    //kAccuseSize - the size of an accusation
    private final int kAccuseSize = 3;
    //unseenCards - the list of cards this Robot has not seen
    private ArrayList<Card> unseenCards;
    //random - a random number generator to make random moves and choices
    private Random random;
    
    /**
     * Difficulty - the levels of difficulty 
     */
    private enum Difficulty 
    {
        //The easiest difficulty
        EASY,
        //The medium difficulty
        MEDIUM,
        //The hardest difficulty
        HARD 
    }
    
    /**
     * CardType - the different types of cards
     */
    private enum CardType
    {
        //The representation of location
        LOCATION,
        //The representation of suspect
        SUSPECT,
        //The representation of vehicle
        VEHICLE,
        //The representation of action
        ACTION
    }

    /**
     * Constructs a new RobotPlayer.
     * @param id the ID this player will have
     * @param name the name this player will have
     * @param rand random object
     * @param location the initial location of the robot
     */
    public RobotPlayer(int id, String name, Random rand,
            LocationCard location)
    {
        //INITIALIZE the name and ID and location
        super(name, id, location);
        //INITIALIZE the kSeed for the random number generator
        this.random = rand;
        //INITIALIZE the list of unseen Clue cards
        initClueCards();
    }

    /**
    * Add a card to this user's hand and mark it as seen.
    * @param card the card to add
    */
    @Override
    public void addCard(Card card)
    {
        //IF the card is an action card
        if (card instanceof ActionCard)
        {
            super.addCard(card);
        }
        //IF the card is a clue card
        else
        {
            super.addCard(card);
            //ITERATE through all the cards in unseen cards
            for (int idx = 0; idx < unseenCards.size(); idx++)
            {
                //IF the card is equal to unseen card, remove it from unseen
                if (card.equals(unseenCards.get(idx)))
                {
                    unseenCards.remove(idx);
                }
            }
        }
    }

    /**
     * Determines what the robot does to select a move for the turn.
     * @param msg the message telling the robot what to do
     * @param server the server to communicate to during the selection
     */
    public void selectAction(Message msg, ClueServer server)
    {
        //IF there are any players in the message, update players
        if (msg.getPlayers() != null)
        {
            players = msg.getPlayers();
        }
        //IF called for an action 
        if (msg.getMove() == Message.Move.YOURTURN)
        {
            //ADD the action card to the hand
            ActionCard card;
            addCard(msg.getCards()[0]);

            //PICK a random action card
            card = actionCards[random.nextInt(getNumActions())];
            this.removeActionCard(card);
            
            //CASE on the action type enum
            pickSuggest(card, server);
            pickSnoop(card, server);
            pickPrivateTip(card, server);
            pickSuperSleuth(card, server);
        }
        //ELSE IF called for an accusation
        else if (msg.getMove() == Message.Move.YOURACCUSATION)
        {
            //call accuse(action, server);
            accuse(null, server);
        }
        //ENDIF
    }
    
    private void pickSuggest(ActionCard action, ClueServer server)
    {
        //PICK the coorresponding action
        switch (action.getType())
        {
            //condition SUGGESTALL: call suggest(action);
            case SUGGESTALL: 
                suggest(action, server);
                break;
            //condition SUGGESTCUR: call suggest(action);
            case SUGGESTCUR: 
                suggest(action, server);
                break;
            default:
        }
    }
    
    private void pickSnoop(ActionCard action, ClueServer server)
    {
        //PICK the coorresponding action
        switch (action.getType())
        {
            //condition SNOOP: return call allSnoop(action);
            case SNOOP: 
                snoop(action, server);
                break;
            //condition ALLSNOOPLEFT: call allSnoop(action);
            case ALLSNOOPLEFT: 
                allSnoop(action, server);
                break;
            //condition ALLSNOOPRIGHT: call allSnoop(action);
            case ALLSNOOPRIGHT: 
                allSnoop(action, server);
                break;
            default:
        }
    }
    
    private void pickSuperSleuth(ActionCard action, ClueServer server)
    {
        //PICK the coorresponding action
        switch (action.getType())
        {
            //condition SSFEMALE: call superSleuth(action);
            case SSFEMALE: 
                superSleuth(action, server);
                break;
            //condition SSMALE: call superSleuth(action);
            case SSMALE: 
                superSleuth(action, server);
                break;
            //condition SSFLYING: call superSleuth(action);
            case SSFLYING: 
                superSleuth(action, server);
                break;
            //condition SSBLUE: call superSleuth(action);
            case SSBLUE: 
                superSleuth(action, server);
                break;
            //condition SSSOUTH: call superSleuth(action);
            case SSSOUTH: 
                superSleuth(action, server);
                break;
            //condition SSWEST: call superSleuth(action);
            case SSWEST: 
                superSleuth(action, server);
                break;
            default:
        }

    }
    
    private void pickPrivateTip(ActionCard action, ClueServer server)
    {
        //PICK the coorresponding action
        switch (action.getType())
        {
            //condition PTSUSPECT: call privateTip(action);
            case PTSUSPECT: 
                privateTip(action, server);
                break;
            //condition PTVEHICLE: call privateTip(action);
            case PTVEHICLE: 
                privateTip(action, server);
                break;
            //condition PTDESTINATION: call privateTip(action);
            case PTDESTINATION: 
                privateTip(action, server);
                break;
            //condition PTFEMALE: call privateTip(action);
            case PTFEMALE: 
                privateTip(action, server);
                break;
            //condition PTREDVEHICLE: call privateTip(action);
            case PTREDVEHICLE: 
                privateTip(action, server);
                break;
            //condition PTNORTHDEST: call privateTip(action);
            case PTNORTHDEST: 
                privateTip(action, server);
                break;
            default:
        }
    }

    /**
     * Make an accusation.
     * @param action This will be null
     * @param server The server to send the results to
     */
    public void accuse(ActionCard action, ClueServer server)
    {
        Message.Move move = Message.Move.ACCUSATION;
        //CREATE a new card arraylist
        Card[] cardList = new Card[kAccuseSize];

        //IF there are only three cards that haven't been seen.
        if (unseenCards.size() == kAccuseSize)
        {
            //SET the cardList to unseenCards
            cardList[CardType.LOCATION.ordinal()] =
                    unseenCards.get(CardType.LOCATION.ordinal());
            cardList[CardType.SUSPECT.ordinal()] =
                    unseenCards.get(CardType.SUSPECT.ordinal());
            cardList[CardType.VEHICLE.ordinal()] =
                    unseenCards.get(CardType.VEHICLE.ordinal());
        }
        //ELSE
        else
        {
            //CREATE an empty list of cards
            cardList = null;
            move = Message.Move.ENDTURN;
        }
        //ENDIF
        //SEND a message to the server with the arraylist of cards
        server.handleMessageFromClient(new Message(this, null, null, 
                move, null, cardList), null);
    }

    /**
     * Determines what the robot does in order to make a suggestion.
     * Message used is {robot, null, players, SUGGESTION, "suggestion",
     * {locationcard, suspectcard, vehiclecard, actioncard}}
     * @param action the ActionCard used for the suggestion
     * @param server the server to send the response to
     */
    public void suggest(ActionCard action, ClueServer server)
    {
        //An array of cards for the suggestion
        Card[] suggestion = new Card[kSuggestionSize];
        //A value representing whether a player has been found
        boolean playerFound = false;
        //A value representing whether an unknown card has been found
        boolean hasUnknown = false;
        //The type of move to send
        Message.Move move = Message.Move.ACTION;
        //The type of type to send
        Message.Type type = Message.Type.SUGGESTION;
        //The message to send
        Message message;
        //INITIALIZE each index in suggestions[] as null
        for (int idx = 0; idx < kSuggestionSize ; idx++)
        {
            suggestion[idx] = null;
        }
        //SET last card in suggestion to action
        suggestion[CardType.ACTION.ordinal()] = action;
        //IF difficulty is EASY or MEDIUM
        if (difficulty == Difficulty.EASY.ordinal() || 
                difficulty == Difficulty.MEDIUM.ordinal())
        {
            //IF the action type is SUGGESTCUR
            if (action.getType() == ActionCard.Type.SUGGESTCUR)
            {
                //SET suggestion[LOCATION] to current LocationCard
                suggestion[CardType.LOCATION.ordinal()] = location;
            }
            //FILL suggestion randomly
            randomSuggest(suggestion);
        }
        //ELSE
        else
        {
            //IF the action type is SUGGESTALL
            if (action.getType() == ActionCard.Type.SUGGESTALL)
            {
                hardSuggestAll(suggestion);
            }
            //ELSE
            else
            {
                move = hardSuggestCur(suggestion);
                //IF the player is being moved
                if (move == Message.Move.PLAYERMOVED)
                {
                    //SET the type of the message to null
                    type = null;
                }
            }
            //ENDIF
        }
        //ENDIF
        //SEND a message to the server with this player, null, a list of
        //players, the SUGGESTION or PLAYERMOVED type, string,
        //and the card list.
        message = new Message(this, null, null, move,
                type, suggestion);
        server.handleMessageFromClient(message, null);
    }
    
    private void hardSuggestAll(Card[] suggestion)
    {
        //A value representing whether a player has been found
        boolean playerFound = false;
        //A value representing whether an unknown card has been found
        boolean hasUnknown = false;
        //FOR each player going clockwise and while playerFound == false
        for (int idx = (id + 1) % players.length; idx % players.length
                != id && !playerFound; idx++)
        {
            //IF the player has an unknown card
            if (findCards(players[idx], suggestion, hasUnknown))
            {
                //SET playerFound to true
                playerFound = true;
            }
        }
        //IF (playerFound == false)
        if (!playerFound)
        {
            //MAKE a random suggestion
            randomSuggest(suggestion);
        }
    }
    
    private Message.Move hardSuggestCur(Card[] suggestion)
    {
        //A value representing whether a player has been found
        boolean playerFound = false;
        //A value representing whether an unknown card has been found
        boolean hasUnknown = false;
        //The type of action to take
        Message.Move move = Message.Move.ACTION;
        //IF location is in next clockwise player's hand
        if (players[(id + 1) % players.length].hasCard(location)
                && !unseenCards.contains(location))
        {
            //MOVE random unknown location
            move = Message.Move.PLAYERMOVED;
            setRandomLocation(suggestion);               
        }
        //ELSE
        else
        {
            //IF location is unknown
            if (unseenCards.contains(location))
            {
                //SET hasUnknown to true
                hasUnknown = true;
            }
            //SET suggestion[LOCATION] to current LocationCard
            suggestion[CardType.LOCATION.ordinal()] = location;
            //FOR each player going clockwise and while
            //playerFound == false
            for (int idx = (id + 1) % players.length; idx % players.length 
                    != id && !playerFound; idx++)
            {
                //IF findCards(player, suggestion)
                if (findCards(players[idx], suggestion, hasUnknown))
                {
                    //SET playerFound to true
                    playerFound = true;
                }
            }
            //MAKE a random suggestion
            randomSuggest(suggestion);
        }
        //ENDIF
        return move;
    }

    /**
     * A helper method that finds random cards of all types not yet found for
     * the suggestion routine.
     * @param suggestion a list of cards of different types to be returned to
     *                   the caller. The types at each index are
     *                   [LOCATION]: LocationCard
     *                   [SUSPECT]: SuspectCard
     *                   [VEHICLE]: VehicleCard
     * @pre There is at least one card of each type in unseenCards
     */
    private void randomSuggest(Card[] suggestion)
    {
        //FIND an unseen suspect
        while (!(suggestion[CardType.SUSPECT.ordinal()] instanceof SuspectCard))
        {
            suggestion[CardType.SUSPECT.ordinal()] = 
                    unseenCards.get(random.nextInt(unseenCards.size()));
        }
        //FIND an unseen vehicle
        while (!(suggestion[CardType.VEHICLE.ordinal()] instanceof VehicleCard))
        {
            suggestion[CardType.VEHICLE.ordinal()] = 
                    unseenCards.get(random.nextInt(unseenCards.size()));
        }
        //FIND an unseen location
        while (!(suggestion[CardType.LOCATION.ordinal()] instanceof 
                LocationCard))
        {
            suggestion[CardType.LOCATION.ordinal()] = 
                    unseenCards.get(random.nextInt(unseenCards.size()));
        }
    }
    
    /**
     * A helper method that finds a random location.
     * @param suggestion a list of cards of different types to be returned to
     *                   the caller. The types at each index are
     *                   [LOCATION]: LocationCard
     *                   [SUSPECT]: SuspectCard
     *                   [VEHICLE]: VehicleCard
     */
    private void setRandomLocation(Card[] suggestion)
    {
        //FIND an unseen location
        while (!(suggestion[CardType.LOCATION.ordinal()] instanceof 
                LocationCard))
        {
            suggestion[CardType.LOCATION.ordinal()] = 
                    unseenCards.get(random.nextInt(unseenCards.size()));
        }
    }

    /**
     * A helper method that finds if a player has any unknown cards, and also
     * finds known cards to fill the suggestion.
     * @param player     The player to check if has any unknown cards
     * @param suggestion A list of cards of different types to be returned to
     *                   the caller. The types at each index are
     *                   [LOCATION]: LocationCard
     *                   [SUSPECT]: SuspectCard
     *                   [VEHICLE]: VehicleCard
     * @param hasUnknown A value indicating whether or not an unknown card
     *                   has been found
     * @return a value indicating whether the player had an unknown card
     */
    private boolean findCards(Player player, Card[] suggestion,
            boolean hasUnknown)
    {
        //An unknown card the player has
        Card unknownCard;
        //FIND unknown card
        unknownCard = findUnknown(player, suggestion, hasUnknown);
        //IF hasUnknown
        if (hasUnknown || unknownCard != null)
        {
            //IF unknownCard instanceof LocationCard
            if (unknownCard instanceof LocationCard)
            {
                //SET suggestion[LOCATION] to unknownCard
                suggestion[CardType.LOCATION.ordinal()] = unknownCard;
            }
            //ELSEIF unknownCard instanceof SuspectCard
            else if (unknownCard instanceof SuspectCard)
            {
                //SET suggestion[SUSPECT] to unknownCard
                suggestion[CardType.SUSPECT.ordinal()] = unknownCard;
            }
            //ELSE unknownCard instanceof VehicleCard
            else if (unknownCard instanceof VehicleCard)
            {
                //SET suggestion[VEHICLE] to unknownCard
                suggestion[CardType.VEHICLE.ordinal()] = unknownCard;
            }
            //ENDIF
            //IF (suggestion[LOCATION] == null)
            if (suggestion[CardType.LOCATION.ordinal()] == null)
            {
                //SET suggestion[LOCATION] to findCardOfType(LOCATION)
                suggestion[CardType.LOCATION.ordinal()] = 
                        findCardOfType(CardType.LOCATION);
            }
            //IF (suggestion[SUSPECT] == null)
            if (suggestion[CardType.SUSPECT.ordinal()] == null)
            {
                //SET suggestion[SUSPECT] to findCardOfType(SUSPECT)
                suggestion[CardType.SUSPECT.ordinal()] = 
                        findCardOfType(CardType.SUSPECT);
            }
            //IF (suggestion[VEHICLE] == null)
            if (suggestion[CardType.VEHICLE.ordinal()] == null)
            {
                //SET suggestion[VEHICLE] to findCardOfType(VEHICLE)
                suggestion[CardType.VEHICLE.ordinal()] = 
                        findCardOfType(CardType.VEHICLE);
            }
        }
        //ENDIF
        //RETURN hasUnknown
        return hasUnknown;
    }
    
    private Card findUnknown(Player player, Card[] suggestion, 
            boolean hasUnknown)
    {
        //The unknown card
        Card unknownCard = null;
        //The current card
        Card curCard;
        //FOR each card in player's hand and while hasUnknown == false
        for (int idx = 0; idx < player.getNumCards() && !hasUnknown; idx++)
        {
            curCard = player.getClueCards()[idx];  
            //IF that card is in unseenCards
            if (unseenCards.contains(curCard))
            {
                //IF (suggestion[LOCATION] == null) or that card is not 
                //instanceof LocationCard
                if (suggestion[CardType.LOCATION.ordinal()] == null || 
                        !(curCard instanceof LocationCard))
                {
                    //SET card to that card
                    unknownCard = curCard;
                }
                //ENDIF
            }
        }
        //ENDIF
        return unknownCard;
    }
    
    /**
     * A helper method that finds a known card of a specific type.
     * @param type The number that corresponds to a card type
     * @return The card that was found
     */
    private Card findCardOfType(CardType type)
    {
        //The card of the given type to return
        Card card = null;
        //The current card
        Card curCard;
        //The current player
        Player curPlayer;
        //Whether the robot player has checked itself
        boolean visitedSelf = false;
        //FOR each player starting with this RobotPlayer going clockwise and
        //while card == null
        for (int pIdx = id % players.length; (pIdx % players.length != id || 
                !visitedSelf) && card == null; pIdx++)
        {
            visitedSelf = true;
            curPlayer = players[pIdx];
            //FOR each card in their hand and while card == null
            for (int cIdx = 0; cIdx < curPlayer.getClueCards().length && 
                    card == null; cIdx++)
            {
                card = helpFindCardOfType(curPlayer, type, cIdx);
            }
        }
        //RETURN card
        return card;
    }
    
    private Card helpFindCardOfType(Player player, CardType type,
            int index)
    {
        Card curCard = player.getClueCards()[index];
        Card card = null;
        //IF (type == LOCATION)
        if (type == CardType.LOCATION)
        {
            //IF that card is known and instanceof LocationCard
            if (!unseenCards.contains(curCard) &&
                    curCard instanceof LocationCard)
            {
                //SET card to that card
                card = curCard;
            }
        }
        //IF (type == SUSPECT)
        else if (type == CardType.SUSPECT)
        {
            //IF that card is known and instanceof SuspectCard
            if (!unseenCards.contains(curCard) &&
                    curCard instanceof SuspectCard)
            {
                //SET card to that card
                card = curCard;
            }
        }
        //IF (type == VEHICLE)
        else
        {
            //IF that card is known and instanceof VehicleCard
            if (!unseenCards.contains(curCard) &&
                    curCard instanceof VehicleCard)
            {
                //SET card to that card
                card = curCard;
            }
        }
        return card;
    }

    /**
     * Determines what the robot does in order to snoop.
     * Message used is {robot, target, players, ACTION, "snoop", {snoopcard}}
     * @param action the ActionCard used for the snoop
     * @param server the server to send the response to
     */
    public void snoop(ActionCard action, ClueServer server)
    {
        //The target of the snoop
        Player player;
        //The action card
        Card[] card = { action };
        //IF difficulty is EASY
        if (difficulty == Difficulty.EASY.ordinal())
        {
            //PICK a random player as the target
            player = findRandomPlayer();
        }
        //ELSE
        else
        {
            //FIND player with highest number of unknown cards
            player = findMostUnknownCards(players);
        }
        //SEND a message to the server with this player, the target, a list of
        //players, the ACTION type, "snoop" string, and the list of cards.
        server.handleMessageFromClient(new Message(this, player, null, 
                Message.Move.ACTION, Message.Type.SNOOP, card), null);
    }

    /**
     * Finds the player with the most unknown cards.
     * @param players The list of players in the game
     * @return The player with the most unknown cards
     */
    private Player findMostUnknownCards(Player[] players)
    {
        //The player with the most unknown cards
        Player mostUnknown = (Player) players[(id + 1)
                % players.length];
        //The highest number of unknown cards
        int numUnknown = numUnknownCards(mostUnknown);
        //GO through each player
        for (int idx = (mostUnknown.getID() + 1) % players.length;
                idx % players.length != id; idx++)
        {
            //IF the player has more unknowns that the previous most
            if (numUnknownCards((Player) players[idx]) >
                    numUnknown)
            {
                //THAT player becaomes the new most
                mostUnknown = (Player) players[idx];
                numUnknown =
                        numUnknownCards((Player) players[idx]);
            }
                
        }
        return mostUnknown;
    }
    
    /**
     * Finds the number of unknown cards the player has.
     * @param player The player whose cards to search
     * @return the number of unknown cards the player has
     */
    private int numUnknownCards(Player player)
    {
        //The number of unknown cards in the player's hand
        int numUnknown = 0;
        //GO through each card
        for (int idx = 0; idx < player.getNumCards(); idx++)
        {
            //IF the card isn't seen increment the count
            if (unseenCards.contains(player.getClueCards()[idx]))
            {
                numUnknown++;
            }
        }
        return numUnknown;
    }
    
    /**
     * Determines what the robot does in order to make an "all snoop".
     * Message used {robot, target, players, ACTION, "all snoop", {snoopcard}}
     * @param action the ActionCard used for the all snoop
     * @param server the server to send the response to
     */
    public void allSnoop(ActionCard action, ClueServer server)
    {
        //The player to target
        Player target;
        //The card to pass
        Card[] card = { action };
        
        Message.Type type = Message.Type.ALLSNOOPLEFT;
        //IF the action type is ALLSNOOPLEFT
        if (action.getType() == ActionCard.Type.ALLSNOOPLEFT)
        {
            //PICK the left player as a target
            target = players[(id + 1) % players.length];
        }
        //ELSE it's ALLSNOOPRIGHT
        else
        {
            type = Message.Type.ALLSNOOPRIGHT;
            //PICK the right player as a target
            target = players[(id + players.length - 1) % players.length];
        }
        //SEND a message to the server with this player, the target, a list of
        //players, the ACTION type, "allsnoop" string, and the list of cards.
        server.handleMessageFromClient(new Message(this, target, null, 
                Message.Move.ACTION, type, card), null);
    }

    /**
     * Determines what the robot does in order to make a super sleuth.
     * Message used is {robot, null, players, ACTION, "super sleuth",
     * {supersleuthcard}}
     * @param action the ActionCard used for the super sleuth
     * @param server the server to communicate the response to
     */
    public void superSleuth(ActionCard action, ClueServer server)
    {
        //The action to pass
        Card[] card = { action };
        //SEND a message to the server with this player, null, a list of
        //players, ACTION type, "supersleuth" string, and the list of cards.
        server.handleMessageFromClient(new Message(this, null, null, 
                Message.Move.ACTION, Message.Type.SUPERSLEUTH, card), null);
    }

    /**
     * Determines what the robot does in order to make a private tip. Relays
     * its choice upwards.
     * Message used is {robot, target, players, ACTION, "private tip",
     * {privatetipcard}}
     * @param action the ActionCard used for the private tip
     * @param server the server to communicate the response to
     */
    public void privateTip(ActionCard action, ClueServer server)
    {
        //The action card
        Card[] card = { action };
        //The player to be targeted by the PrivateTip
        Player target = null;
        //A list of how many unknown cards each player has
        int[] unknownCards = new int[5];
        //The index with the most unknown cards
        int maxIndex = -1;
        //IF difficulty is EASY
        if (difficulty == Difficulty.EASY.ordinal())
        {
            //SET target to findRandomPlayer()
            target = findRandomPlayer();
        }
        //ELSE
        else
        {
            //FIND the matching amount of cards of everyone
            for (int idx = 0; idx < players.length; idx++)
            {
                unknownCards[idx] = numUnknownCards(players[idx]);
            }
            //CHECK each player
            for (int idx = 0; idx < players.length; idx++)
            {
                maxIndex = findMaxIndex(unknownCards);
                target = players[maxIndex];
                //CHECK each card
                for (int cIdx = 0; cIdx < target.getClueCards().length; cIdx++)
                {
                    //IF the card matches the tip
                    if (action.matchesCard(target.getClueCards()[cIdx], null))
                    {
                        //SET the unknown amount to 0
                        unknownCards[maxIndex] = 0;
                    }
                }
            }
            //IF there are no players without a private tip match
            if (unknownCards[maxIndex] == 0)
            {
                //FIND player with highest number of unknown cards
                target = findMostUnknownCards(players);
            }
        }
        //SEND a message to the server with this player, target, a list of
        //players, ACTION type, "private tip" string, and the list of cards.
        server.handleMessageFromClient(new Message(this, target, null, 
                Message.Move.ACTION, Message.Type.PRIVATETIP, card), null);
        
    }
    
    private int findMaxIndex(int[] arr)
    {
        int maxInd = id + 1;
        //GO through each element
        for (int ind = (id + 1) % arr.length; ind % arr.length != id; ind++)
        {
            //IF the element is greater than the max
            if (arr[maxInd] < arr[ind])
            {
                //IT becomes the new max
                maxInd = ind;
            }
        }
        return maxInd;
    }
    
    private Player findRandomPlayer()
    {
        //The index of the random player
        int randomIndex; 
        //DO WHILE the randomIndex is equal to id
        do
        {
            //SET randomIndex equal to random*kMaxPlayers
            randomIndex = random.nextInt(players.length);
        }
        while(randomIndex == id);
        //RETURN the player of that index
        return players[randomIndex];
        
    }

    /**
     * Marks the shown card as seen by removing it from the unseenCards list.
     * @param card the card to show the robot
     */
    public void showCard(Card card)
    {
        unseenCards.remove(card);
    }

    /**
     * Private helper method to fill unseenCards.
     */
    private void initClueCards()
    {
        unseenCards = new ArrayList();
        // ADD each destination by title
        unseenCards.add(new LocationCard(LocationCard.Title.TITLE1));
        unseenCards.add(new LocationCard(LocationCard.Title.TITLE2));
        unseenCards.add(new LocationCard(LocationCard.Title.TITLE3));
        unseenCards.add(new LocationCard(LocationCard.Title.TITLE4));
        unseenCards.add(new LocationCard(LocationCard.Title.TITLE5));
        unseenCards.add(new LocationCard(LocationCard.Title.TITLE6));
        unseenCards.add(new LocationCard(LocationCard.Title.TITLE7));
        unseenCards.add(new LocationCard(LocationCard.Title.TITLE8));
        unseenCards.add(new LocationCard(LocationCard.Title.TITLE9));
        // ADD each suspect by name
        unseenCards.add(new SuspectCard(SuspectCard.Name.NAME1));
        unseenCards.add(new SuspectCard(SuspectCard.Name.NAME2));
        unseenCards.add(new SuspectCard(SuspectCard.Name.NAME3));
        unseenCards.add(new SuspectCard(SuspectCard.Name.NAME4));
        unseenCards.add(new SuspectCard(SuspectCard.Name.NAME5));
        unseenCards.add(new SuspectCard(SuspectCard.Name.NAME6));
        // ADD each vehicle by model
        unseenCards.add(new VehicleCard(VehicleCard.Model.MODEL1));
        unseenCards.add(new VehicleCard(VehicleCard.Model.MODEL2));
        unseenCards.add(new VehicleCard(VehicleCard.Model.MODEL3));
        unseenCards.add(new VehicleCard(VehicleCard.Model.MODEL4));
        unseenCards.add(new VehicleCard(VehicleCard.Model.MODEL5));
        unseenCards.add(new VehicleCard(VehicleCard.Model.MODEL6));    
    }

    /**
     * Returns a clone of this robot.
     * @return a clone of this robot
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
    @Override
    public RobotPlayer deepCopy()
    {
        //Fixes defect #445
        RobotPlayer clone = new RobotPlayer(id, name, random, location); 

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
        for (int idx = 0; idx < players.length; idx++) 
        {
            // IF human player, CLONE human
            if (players[idx] != null && !(players[idx] instanceof RobotPlayer))
            {
                clone.players[idx] = new Player(players[idx].getName(),
                        players[idx].getID(), players[idx].getLocation());
            }
            // ELSE CLONE robot
            else if (players[idx] != null)
            {
                clone.players[idx] = new RobotPlayer(players[idx].getID(), 
                        players[idx].getName(),
                        ((RobotPlayer)players[idx]).getRandom(), 
                        players[idx].getLocation());
            }
        }
       
        
        clone.name = name;
        clone.isOut = isOut;
        clone.location = new LocationCard(location.getTitle());
        clone.id = id;
        clone.unseenCards = unseenCards;
        return clone;
    }
    
    /**
     * Returns a copy of the unseen cards ArrayList.
     * @return a new ArrayList containing the same items as the unseenCards
     * ArrayList.
     */
    public ArrayList<Card> getUnseenCards()
    {
        //RETURN unseenCards
        return unseenCards;
    }

    /**
     * Sets the unseen cards list of this robot.
     * @param cards The list to set as this robot's unseen card list
     */
    public void setUnseenCards(ArrayList<Card> cards)
    {
        unseenCards = new ArrayList();
        //GO through every element
        for (int idx = 0; idx < cards.size(); idx++)
        {
            unseenCards.add(cards.get(idx));
        }
    }
    
    /**
     * Returns the random object for the player.
     * @return the random object for the player
     */
    public Random getRandom()
    {
        return random;
    }
    
    /**
     * Sets the random object for the player.
     * @param random The random object for the player
     */
    public void setRandom(Random random)
    {
        this.random = random;
    }
    
}
