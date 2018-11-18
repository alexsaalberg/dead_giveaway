package deadgiveaway.client;

import deadgiveaway.*;
import java.util.*;
import java.util.concurrent.Semaphore;
import deadgiveaway.server.*;
import java.io.PrintStream;
import java.io.InputStream;
import java.awt.event.ActionEvent;

/**
 * This class contains definitions for a console interface used by for
 * testing.
 * 
 * @author Austin Sparks
 * @version 2.0
 */
public class ConsoleUI implements UserInterface 
{
    /** constant used to represent the Greek theme */
    private final int kGreekNames = 0;
    /** constant used to represent the pirate theme */
    private final int kPirateNames = 1;
    /** constant used to represent the WhiteHouse theme */
    private final int kWhiteHouseNames = 2;
    /** max length of a username */
    private final int kMaxName = 10;
    /** int used to represent the current theme */
    private int curTheme;    
    /** the list of players */
    private ArrayList<Player> players;
    /** index of this user in the ArrayList of players */
    private int thisPlayer;
    /** a mutex lock to prevent race conditions */
    private final Semaphore keyMutex;
    /** A boolean indicating whether or not the game has started */
    private boolean gameStarted;
    /** A ClueClient holding the connection to the server */
    private ClueClient client;
    /** A boolean for indicating whether the player is still in game */
    private boolean inGame;
    /** Optional alternate output stream */
    private PrintStream out;
    /** Optional alternate input stream */
    private InputStream in;
    /** Scanner to read from the input stream */
    private Scanner scanner;
    /** String to save last message printed to console */
    private String lastMessage;
    /** Boolean to tel is the current user is the host. */
    private boolean isHost;
    /** List of action cards specific names */
    public static final String[] kActionNames = {
        "Suggestion-Any", "Suggestion-Current", "Snoop", "All Snoop Left",
        "All Snoop Right", "Super Sleuth Female", "Super Sleuth Male",
        "Super Sleuth Flying", "Super Sleuth Blue", "Super Sleuth South",
        "Super Sleuth West", "Private Tip Suspect", "Private Tip Vehicle",
        "Private Tip Destination", "Private Tip Female", "Private Tip Red",
        "Private Tip North"
    };
    
    
    /**
    * Constructs a ConsoleUI for a console user interface.
    * @author Austin Sparks
    * @param clnt The ClueClient that is communicating with the server.
    */
    public ConsoleUI(ClueClient clnt)
    {
        keyMutex = null;
        // SET clueClient
        this.client = clnt;
        // SET out to System.out
        this.out = System.out;
        // SET in to System.in
        this.in = System.in;
        // SET scanner to read from System.in
        this.scanner = new Scanner(this.in);
        // INIT players list
        players = new ArrayList<Player>();
    }
    
    /**
     * Constructs a ConsoleUI with specified input and output
     * @author Austin Sparks
     * @param clnt The ClueClient that is communicating with the server
     * @param nout Alternate output stream for testing
     * @param nin Alternate input stream for testing
     */
    public ConsoleUI(ClueClient clnt, PrintStream nout, InputStream nin)
    {
        keyMutex = null;
        // SET clueClient
        this.client = clnt;
        // SET this.out to nout
        this.out = nout;
        // SET this.in to nin
        this.in = nin;
        // SET scanner to read from nin
        this.scanner = new Scanner(nin);
        // INIT players list
        players = new ArrayList<Player>();
    }
    
    
    /**
     * Sets the countdown on the turn timer. Not implemented in console.
     * @param time A string representing the time remaining for the user's turn.
     */
    @Override
    public void setTurnCountdown(String time)
    {
        // DO nothing as there is no timer for the console
    }
    
    /**
     * Gets a Snoop. Lets a player select a target and then 
     * one of the target's cards.
     * @param target The player to snoop on.
     * @return The player chosen as the target. Return null if the user cancels.
     */
    public Player getSnoop(Player target)
    {
        // PRINT out message asking for card to select
        lastMessage = "Please select a card to snoop from 1 - " + 
                target.getNumCards();
        out.println(lastMessage);
        
        // IGNORE card because server picks one at random
        String num = scan();
        
        lastMessage += "\nYou chose card " + num;
        out.println(lastMessage);
        //SEND message to client with card chosen
        //Defect #458 NullPointer Exception (FIXED)
        //Message msg = new Message(target, players.get(thisPlayer), null,
        //Message.Move.ACTION, Message.Type.SNOOP, target.getClueCards());
        //client.handleUserInput(msg);
        
        // RETURN the target of the snoop
        return target;
    }
    
    /**
     * Gets a suggestion. Lets a player pick 
     * @param location The location to force. Pass null to let the user choose
     * a location (only for SUGGESTALL)
     * @return The suggestion in an array of cards, including the action card as
     * the last entry. Return null if the user cancels the suggestion.
     */
    public Card[] getSuggestion(Integer location)
    {
        // IF location is null
        if (location == null)
        {
            return suggestAll();
        }
        // ELSE IF location is not null
        else
        {
            return suggestCur();
        }
    }
    
    private Card[] suggestCur()
    {
        LocationCard loc;
        SuspectCard sus;
        VehicleCard veh;
        Card[] cards;
        
        // PRINT message
        out.println("Change location or suggest from current?");
        out.println("\t1 - Change Location");
        out.println("\t2 - Suggest from current location");
        // GET input
        int input = getInput(2);
        
        System.err.println("Input: " + input);
        
        // IF change location
        if (input == 1)
        {
            cards = new Card[1];
            // GET the new location
            cards[0] = getLocation();
            
        }// ELSE make suggestion
        else
        {
            cards = new Card[4];
            cards[0] = players.get(thisPlayer).getLocation();
            cards[1] = getSuspect();
            cards[2] = getVehicle();
            cards[3] = new ActionCard(ActionCard.Type.SUGGESTCUR);
        }
        
        return cards;
    }
    
    private Card[] suggestAll()
    {
        Card[] cards = new Card[4];
        // GET each card
        cards[0] = getLocation();
        cards[1] = getSuspect();
        cards[2] = getVehicle();
        cards[3] = new ActionCard(ActionCard.Type.SUGGESTALL);
        
        // RETURN the suggestion
        return cards;
    }
    
    /**
     * Prevents the user from selecting performing another action. 
     */
    public void actionSelected()
    {
        // DO nothing as there is nothing to display
    }
    
    /**
     * Prompts the user to select a target for the desired action.
     * @return The player that is selected by the user as the target.
     */
    public Player selectTarget()
    {
        int plyct = 1;
        lastMessage = "Please select an opponent";
        out.println(lastMessage);
        ArrayList<Player> pls = new ArrayList<Player>();
        
        // GO through each player
        // Defect #460: Loop never executed (FIXED)
        for (int idx = 0; idx < players.size(); idx++)
        {
            // IF idx is not thisPlayer
            if (idx != thisPlayer)
            {
                // ADD to the list of possible players
                pls.add(players.get(idx));
                // PRINT out the player as an option
                lastMessage = "\t" + plyct + " - " + players.get(idx).getName();
                out.println(lastMessage);
                plyct++;
            }
        }
        
        // GET input from user
        int input = getInput(pls.size()) - 1;
        // RETURN the selected player
        return pls.get(input);
    }
    
    /**
     * Getter for the AI difficulty.
     * @return The difficulty selected by the user.
     */
    public int getAIDifficulty()
    {
        // ASK user to enter difficulty
        lastMessage = "Please select a difficulty\n1 - Easy"
                        + "\n2 - Medium\n3 - Hard\n";
        out.print(lastMessage);
        // GET input from the user
        int input = scanner.nextInt();
        
        // RETURN input - 1
        return input - 1;
    }
    
    /**
     * Getter for the user's username.
     * @return The username of the user.
     */
    public String getUsername()
    {
        lastMessage = "Please enter your desired username";
        // GET username from user
        out.println(lastMessage);
        // SCAN for user name
        String name = scan();
        // RETURN username
        return name;
    }
    
    /**
     * Allow the user to end their turn or accuse.
     * (Enable buttons for a GUI and display a prompt for a Console)
     */
    public void allowEndTurnOrAccuse()
    {
        String input = "";
        boolean done = false;
        lastMessage = "End your turn (end) or make an accusation (accuse).";
        // PRINT out options
        out.println(lastMessage);

        // LOOP until user input is valid
        while (!done)
        {
            input = scan();
            
            // IF input is end or accuse
            if (input.equals("end") || input.equals("accuse"))
            {
                // END the loop
                done = true;
            }// ELSE
            else
            {
                // ASK again
                lastMessage = "Invalid input. Please enter \"end\" or \"accuse\"";
                out.println(lastMessage);
            }// ENDIF
        }// ENDLOOP
        
        // IF end turn
        if (input.equals("end"))
        {
            // CONSTRUCT ActionEvent with "End Turn"
            ActionEvent evt = new ActionEvent(this, 0, "End Turn");
            client.actionPerformed(evt);
        }// ELSE
        else
        {
            // ACCUSE
            accuse();
        }// ENDIF
    }
    
    /**
     * Starts your turn
     * @param player Player whose turn it is
     * @param drawn Card that was drawn from the deck
     */
    private void turnStarted(Player player, ActionCard drawn)
    {
        boolean done = false;
        String input = "";
        // ADD msg.getCards()[0] to player hand
        players.get(thisPlayer).addCard(drawn);
        // PRINT out player cards
        out.println("Your clue cards are");
        Card[] clueCards = players.get(thisPlayer).getClueCards();
        ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(clueCards));
        printValidCards(cards);
        // PRINT out turn indicator and cards
        lastMessage = "It is your turn. Please select an action card or accuse.";
        out.println(lastMessage);
        //Fixes defect #430
        printActionCards();
        // LOOP until input is correct
        while (!done)
        {
            // PRINT message to select card
            out.println("Please enter 1, 2, or accuse");
            
            // SCAN the next line for input
            input = scan();
            
            // IF input is 1, 2, or accuse
            if (input.equals("1") || input.equals("2") || input.equals("accuse"))
            {
                // SET done true
                done = true;
            }
        } // ENDLOOP

        // IF 1
        if (input.equals("1"))
        {
            // CONSTRUCT ActionEvent with Action 0
            ActionEvent evt = new ActionEvent(this, 0, "Action 0");
            client.actionPerformed(evt);
        }
        // ELSE IF 2
        else if(input.equals("2"))
        {
            // CONSTRUCT ActionEvent with Action 1
            ActionEvent evt = new ActionEvent(this, 0, "Action 1");
            client.actionPerformed(evt);
        }
        // ELSE it's an accuse
        else
        {
            // CALL accuse
            accuse();
        }// ENDIF
    }
    
    /**
     * Updates the player list to track changes to players.
     * @author Austin Sparks
     * @param plyrs And updated list of players
     */
    public void updatePlayers(Player[] plyrs)
    {
        // CLEAR the list of players
        players = new ArrayList<Player>();
        // COPY players into this.players
        for (Player pl : plyrs)
        {
            players.add(pl);
        }
    }
    
    /**
     * Updates the player list to track changes to players.
     * @author Austin Sparks
     * @param plyrs An ArrayList of the players
     */
    @Override
    public void updatePlayers(ArrayList<Player> plyrs)
    {
        // COPY the players over
        players = plyrs;
    }
    
    /**
     * Removes the player from the lobby and begins displaying the game.
     * @author Austin Sparks
     */
    public void gameStarted()
    {
        // PRINT out game start message
        lastMessage = "Dead Giveaway has started.";
        out.println(lastMessage);
        // SET inGame to true
        inGame = true;
        // SET gameStarted to true
        gameStarted = true;
    }
    
    /**
     * Displays that the connection to the server has been established.
     * @author Austin Sparks
     * @param playerPos The position of the player in the list of players
     * @param player The player connecting to the server
     * @return The username of the player.
     */
    public String connectionEstablished(int playerPos, Player player)
    {
        thisPlayer = playerPos;
        out.println("Connection Established");
        client.actionPerformed(new ActionEvent(this, 0, "Create Game"));
        
        // RETURN the name of the player
        return player.getName();
    }
    
    /**
     * Print out the action cards the player has
     * @author Austin Sparks
     */
    private void printActionCards()
    {
        int number = 1;
        // Get each card from the player
        for (ActionCard card : players.get(thisPlayer).getActionCards())
        {
            lastMessage = "\t" + number + " - ";
            lastMessage += kActionNames[card.getType().ordinal()];
            out.println(lastMessage);
            number++;
        }
    }
    
    /**
     * Prints out all valid cards in the play
     * @author Austin Sparks
     * @param validCards
     */
    private int printValidCards(ArrayList<Card> validCards)
    {
        int idx = 1;
        
        // GO through each card
        for (Card card : validCards)
        {
            //Defect #425; Fixed by incrementing idx
            // PRINT the card out
            lastMessage = idx++ + " - " + card.toString();
            out.println(lastMessage);
        }
        
        // RETURN the index of the last card
        return idx;
    }
    
    /**
     * Displays a card that was shown to the user.
     * @author Austin Sparks
     * @param player The player who showed the card.
     * @param cards The cards that were shown.
     * @param action The action that was played to show the card
     */
    public void shownCard(Player player, Card[] cards, Message.Type action)
    {
        // GET card name
        String card = cards[0].toString();
        // GET player name
        String name = player.getName();
        // PRINT message saying what card by which player
        lastMessage = name + " showed you " + card + "\n";
        out.print(lastMessage);
    }
    
    /**
     * Displays a suggestion that was made.
     * @author Austin Sparks
     * @param player The player making the suggestion.
     * @param suggestion The cards in the suggestion.
     */
    public void disproveSuggestion(Player player, Card[] suggestion)
    {
        lastMessage = player.getName() + " made a suggestion with ";
        lastMessage += suggestion[0].toString() + " " + suggestion[1].toString();
        lastMessage += " " + suggestion[2].toString();
        out.println(lastMessage);
        lastMessage = "You must disprove this suggestion. Please select a card.";
        out.println(lastMessage);
        ArrayList<Card> validCards = new ArrayList();
        
        // GET player name and cards
        SuspectCard sus = (SuspectCard)suggestion[1];
        VehicleCard veh = (VehicleCard)suggestion[2];
        LocationCard loc = (LocationCard)suggestion[0];
        
        // GO through all of player's cards and get valid ones
        for (Card card : players.get(thisPlayer).getClueCards())
        {
            // IF equals one of the suggested
            if (sus.equals(card) || veh.equals(card) || loc.equals(card))
            {
                // SAVE card
                validCards.add(card);
            }
        }
       
        // PRINT out options to show
        int max = printValidCards(validCards);
        // GET input from user
        int num = getInput(max);
        // SEND message with card
        Card[] cards = {validCards.get(num - 1)};
        Message msg;
        msg = new Message(players.get(thisPlayer), player, null, 
               Message.Move.SHOWNCARDS, Message.Type.SUGGESTION, cards);
        client.handleUserInput(msg);
        lastMessage = "\nYou have disproved " + player.getName();
        lastMessage += "'s suggestion with " + cards[0].toString();
        out.println(lastMessage);
    }
    
    /**
     * Displays an accusation that was made.
     * @author Austin Sparks
     * @param player The player making the accusation.
     * @param accusation The cards in the accusation.
     * @param right Whether the accusation was right or not
     */
    public void accusationMade(Player player, Card[] accusation, boolean right)
    {
        String name = player.getName();
        
        // IF right
        if (right)
        {
            // PRINT accusation was correct
            lastMessage = "The accusation was correct. " + name;
            lastMessage += " won the game!";
            out.println(lastMessage);
            // CALL gameOver method
            gameOver();
        }
        // ELSE
        else
        {
            // PRINT accusation was incorrect
            lastMessage = "The accusation was incorrect. " + name;
            lastMessage += " is out of the game.";
            out.println(lastMessage);
        }// ENDIF
    }
    
    /**
     * Respond to a super sleuth that was played
     * @author Austin Sparks
     * @param player Player that played the super sleuth
     * @param card Super sleuth card
     */
    private void respondToSS(Player player, Card card)
    {
        // PRINT you must select a card to show
        lastMessage = player.getName() + " played a super sleuth. ";
        lastMessage += "You must respond. Please select a card.";
        out.println(lastMessage);
        ArrayList<Card> validCards = new ArrayList<Card>();
        
        // GO through each card thisPlayer has
        for (Card cd : players.get(thisPlayer).getClueCards())
        {
            // IF the clue card matches
            if (((ActionCard)card).matchesCard(cd, null))
            {
                // SAWE the clue card
                validCards.add(cd);
            }
        }
        
        // PRINT out the valid cards to select
        int max = printValidCards(validCards);
        int num = getInput(max);
        Card[] cards = {validCards.get(num - 1)};
        // SEND message to server with card
        Message msg;
        msg = new Message(players.get(thisPlayer), player, null, 
                Message.Move.SHOWNCARDS, Message.Type.SUPERSLEUTH, cards);
        client.handleUserInput(msg);
    }
    
    /**
     * Respond to a private tip
     * @author Austin Sparks
     * @param player Player that played the private tip
     * @param card Private tip card
     */
    private void respondToPT(Player player, Card card)
    {
        // PRINT you must select a card to show
        lastMessage = player.toString() + " says " + card.toString();
        out.println(lastMessage);
        ArrayList<Card> validCards = new ArrayList<Card>();
        
        // GO through each card thisPlayer has
        for (Card cd : players.get(thisPlayer).getClueCards())
        {
            // IF the card matches the action
            if (((ActionCard)card).matchesCard(cd, null))
            {
                validCards.add(cd);
            }
        }
        
        // PRINT out the valid cards to select
        int max = printValidCards(validCards);
        // GET a card
        int num = getInput(max);
        Card[] cards = {validCards.get(num - 1)};
        
        // SEND response to server
        Message msg;
        msg = new Message(players.get(thisPlayer), player, null, 
                Message.Move.SHOWNCARDS, Message.Type.PRIVATETIP, cards);
        client.handleUserInput(msg);
    }
    
    /**
     * Notifies the user that invalid input was entered.
     * @author Austin Sparks
     */
    @Override
    public void invalidInput()
    {
        // PRINT message invalid input
        lastMessage = "Invalid input";
        out.println(lastMessage);
    }
    
    /**
     * Displays all actions based on the game state represented by the message.
     * @author Austin Sparks
     * @param o The ClueClient that received the message.
     * @param arg The message that represents another player's action.
     */
    @Override
    public void update(java.util.Observable o, Object arg)
    {
        String endMsg = "End your turn (end) or make an accusation (accuse).";
        // SAVE msg by casting arg to a msg
        Message msg = (Message)arg;
        
        // IF UPDATELOG
        if(msg.getMove() == Message.Move.UPDATELOG)
        {
            // PRINT out the information
            out.println(getInfo(msg));
        }
        // IF gamestarted is FALSE
        if (!gameStarted)
        {
            // IF Message.Move.GAMESTARTED
            if (msg.getMove() == Message.Move.GAMESTARTED)
            {
                // CALL gamestarted()
                gameStarted();
            }
            // ELSE IF Message.Move.CONNECTIONESTABLISHED
            else if (msg.getMove() == Message.Move.CONNECTIONESTABLISHED)
            {
                //Fixed defect #422
                //CALL connectionEstablished()
                connectionEstablished(msg.getPlayer().getID(), msg.getPlayer());
                isHost = (msg.getType() != Message.Type.LOBBYEXISTS);
            }
            // ELSE IF Message.Move.LOBBYSTARTED
            else if (msg.getMove() == Message.Move.LOBBYSTARTED)
            {
                // IF I am the host
                if (isHost)
                {
                    // CALL startGame()
                    startGame();
                }
            }
        }
        
        // IF Message.Move.CONNECTIONREFUSED
        if (msg.getMove() == Message.Move.CONNECTIONREFUSED)
        {
            // PRINT message game full
            lastMessage = "A game is currently in session. ";
            lastMessage += "Please try again later.";
            out.println(lastMessage);
            // RETURN
            gameOver();
        }// ELSE IF RESUMETURN
        else if (msg.getMove() == Message.Move.RESUMETURN &&
                !lastMessage.equals(endMsg))
        {
            allowEndTurnOrAccuse();
        }// ELSE
        else
        {
            // CHECK the rest of the message
            updateCont(msg);
        }
    }
    
    //Fixed defect #424
    private void startGame()
    {
        String choice = "";
        out.println("Enter 's' to start the game.");
        
        // LOOP until user enters 's'
        do
        {
            choice = scanner.nextLine();
        } while(!choice.equals("s"));

        // CONSTRUCT ActionEvent with Start Game
        ActionEvent evt = new ActionEvent(this, 0, "Start Game");
        client.actionPerformed(evt);
    }
    
    /**
     * Continues the update call in the event that thisPlayer is the target
     * @author Austin Sparks
     * @param msg The message that was given
     */
    private void updateCont(Message msg)
    {
        Message mes;
        
        // IF Message.Move.YOURTURN
        if (msg.getMove() == Message.Move.YOURTURN)
        {
            updateYourTurn(msg);
        }
        // ELSE IF Message.Move.SHOWNCARDS
        else if (msg.getMove() == Message.Move.SHOWNCARDS)
        {            
            // CALL shownCard()
            shownCard(msg.getTarget(), msg.getCards(), msg.getType());
        }
        // ELSE IF Message.Move.DISPROVESUGGESTION
        else if (msg.getMove() == Message.Move.DISPROVESUGGESTION)
        {
            // CALL disproveSuggestion()
            disproveSuggestion(msg.getPlayer(), msg.getCards());
        }
        // ELSE IF Message.Move.YOURACCUSATION
        else if (msg.getMove() == Message.Move.YOURACCUSATION)
        {
            updateYourAccusation(msg);
        }
        // ELSE IF Message.Move.ACCUSATION
        else if (msg.getMove() == Message.Move.ACCUSATION)
        {
            updateAccusation(msg);
        }
        // ELSE IF Message.Move.ACTION
        else if (msg.getMove() == Message.Move.ACTION)
        {
            updateAction(msg);
        }
        // ELSE IF Message.Move.PLAYERADDED
        else if (msg.getMove() == Message.Move.PLAYERADDED)
        {
            updatePlayerAdded(msg);
        }
        // ELSE IF Message.Move.PLAYERMOVED
        else if (msg.getMove() == Message.Move.PLAYERMOVED)
        {
            updatePlayerMoved(msg);
        }
    }
    
    /**
     * Handles a yourturn message. Skips turn if not in game.
     * @author Austin Sparks
     * @param msg The message containing the action
     */
    private void updateYourTurn(Message msg)
    {
        Message mes;

        // IF not inGame
        if (!inGame)
        {
            // SEND to server end turn
            mes = new Message(players.get(thisPlayer), null, null,
                    Message.Move.ENDTURN, null, null);
            //client.handleUserInput(mes);
        }
        // ELSE IF im the target
        else if (msg.getPlayer().getID() == players.get(thisPlayer).getID())
        {
            turnStarted(msg.getTarget(), (ActionCard)msg.getCards()[0]);
        }
        // ELSE
        else
        {
            out.println("Waiting for " + msg.getPlayer().getName() + ".");
        }
    }
    
    /**
     * Handles response to accusation the player made.
     * @author Austin Sparks
     * @param msg Message with the accusation information
     */
    private void updateYourAccusation(Message msg)
    {
        // IF msg is that player won
        if (msg.getType() == Message.Type.CORRECTACCUSATION)
        {
            // PRINT you win
            lastMessage = "Your accusation was correct! You win!";
            out.println(lastMessage);
            // CALL gameOver()
            gameOver();
        }
        // ELSE
        else
        {
            // SET inGame to false
            inGame = false;
            // PRINT your accusation was incorrect
            lastMessage = "Your accusation was incorrect.";
            out.println(lastMessage);
            // PRINT notice
            lastMessage = "You can no longer make a move but you must ";
            lastMessage += " respond to other players' moves.\n";
            lastMessage += "Enter anything to contiue";
            out.println(lastMessage);
            scan();
        }
    }
    
    /**
     * Handles message containing someone else's accusation
     * @param msg Message with the accusation information
     */
    private void updateAccusation(Message msg)
    {
        // IF type is correct
        if (msg.getType() == Message.Type.CORRECTACCUSATION)
        {
            // CALL accusation made with right
            accusationMade(msg.getPlayer(), msg.getCards(), true);
            // CALL gameOver()
            gameOver();
        }
        // ELSE IF type is incorrect
        else if (msg.getType() == Message.Type.INCORRECTACCUSATION)
        {
            // Call accusationMade with not right
            accusationMade(msg.getPlayer(), msg.getCards(), false);
        }
        // ELSE type is null
        else
        {
            // PRINT out the accusation
            lastMessage = msg.getPlayer().getName() + " accused " +
                    msg.getCards()[1] + " going to " + msg.getCards()[0] + 
                    " with " + msg.getCards()[2];
            out.println(lastMessage);
        }
    }
    
    /**
     * Handles an action message
     * @author Austin Sparks
     * @param msg Message containing action
     */
    private void updateAction(Message msg)
    {
        // IF Type is SUPERSLEUTH
        if ("Super Sleuth".equals(msg.getCards()[0].toString()))
        {
            // CALL respondToSS()
            respondToSS(msg.getTarget(), msg.getCards()[0]);
        }
        // ELSE IF Type is PRIVATETIP
        else if ("Private Tip".equals(msg.getCards()[0].toString()))
        {
            // CALL respondToPT()
            respondToPT(msg.getTarget(), msg.getCards()[0]);
        }
    }
    
    /**
     * Handles message when a player is added
     * @param msg Message with the new player information
     */
    private void updatePlayerAdded(Message msg)
    {
        // PRINT that the player joined
        lastMessage = msg.getPlayer().getName() + " has joined the game.";
        out.println(lastMessage);
    }
    
    /**
     * Handles message when a player moves
     * @param msg Message with the player information
     */
    private void updatePlayerMoved(Message msg)
    {
        // PRINT that the player moved
        lastMessage = msg.getPlayer().getName() + " moved to " + 
                msg.getCards()[0].toString();
        out.println(lastMessage);
    }
    
    /**
     * End the game.
     * @author Austin Sparks
     */
    private void gameOver()
    {
        // EXIT
        //System.exit(0);
    }
    
    /**
     * Get a suspect card from the user
     * @return The suspect card they select
     */
    private SuspectCard getSuspect()
    {
        ArrayList<Card> validCards = new ArrayList<Card>();
        
        // GO through each suspect
        for (SuspectCard.Name name : SuspectCard.Name.values())
        {
            validCards.add(new SuspectCard(name));
        }
        
        // PRINT message and cards
        lastMessage = "Please select a suspect.";
        out.println(lastMessage);
        printValidCards(validCards);
        
        return (SuspectCard)validCards.get(getInput(validCards.size()) - 1);
    }
    
    /**
     * Get a vehicle card from the user
     * @return The vehicle card they select
     */
    private VehicleCard getVehicle()
    {
        ArrayList<Card> validCards = new ArrayList<Card>();
        
        // GO through each vehicle
        for (VehicleCard.Model model : VehicleCard.Model.values())
        {
            validCards.add(new VehicleCard(model));
        }
        
        // PRINT message and cards
        lastMessage = "Please select a vehicle.";
        out.println(lastMessage);
        printValidCards(validCards);
        
        return (VehicleCard)validCards.get(getInput(validCards.size()) - 1);
    }
    
    /**
     * Get a location card from the user
     * @return The location card they select
     */
    private LocationCard getLocation()
    {
        ArrayList<Card> validCards = new ArrayList<Card>();
        
        // GO through each location
        for (LocationCard.Title title : LocationCard.Title.values())
        {
            validCards.add(new LocationCard(title));
        }
        
        // PRINT message and cards
        lastMessage = "Please select a destination.";
        out.println(lastMessage);
        printValidCards(validCards);
        
        return (LocationCard)validCards.get(getInput(validCards.size()) - 1);
    }
    
    /**
     * Perform an accusation
     * @author Austin Sparks
     */
    private void accuse()
    {
        Card[] cards = new Card[3];
        // GET a location
        cards[0] = getLocation();
        // GET a suspect
        cards[1] = getSuspect();
        // GET a vehicle
        cards[2] = getVehicle();
        
        // SEND message to server making accusation
        Message msg;
        msg = new Message(players.get(thisPlayer), null, 
                players.toArray(new Player[5]), 
                Message.Move.ACCUSATION, null, cards);
        client.handleUserInput(msg);
    }
    
    /**
     * Set the inputStream
     * @author Austin Sparks
     * @param inStream New input stream
     */
    public void setInputStream(InputStream inStream)
    {
        // SET input to new one
        in = inStream;
        scanner = new Scanner(in);
    }
    
    /**
     * Set the output stream
     * @author Austin Sparks
     * @param outStream New output stream
     */
    public void setOutputStream(PrintStream outStream)
    {
        // SET ouput to new one
        out = outStream;
    }
    
    /**
     * Get the list of players the UI has for testing
     * @author Austin Sparks
     * @return current list of players
     */
    public ArrayList<Player> getPlayers()
    {
        // RETURN the list of players involved
        return this.players;
    }
    
    /**
     * Gets input from the user with error checking
     * @author Austin Sparks
     * @param max Maximum value the input can be
     * @return The input number given
     */
    private int getInput(int max)
    {
        boolean done = false;
        int num = -1;
        
        // WHILE not done
        while (!done)
        {
            // GET the number input
            String input = scan();
            num = Integer.parseInt(input);  
            // IF valid input
            if (num > 0 && num <= max)
            {
                // DONE
                done = true;
            }
            // ELSE ask again
            else
            {
                lastMessage += "Invalid input. Please select a number 1 - ";
                lastMessage += max + ".";
                out.println(lastMessage);
            }
        }// ENDWHILE
        
        // RETURN the number
        return num;
    }
    
    /**
     * Returns the publicly available information from the Message, formatting
     * in a format easily read by a player.
     * @return The message, formatted
     */
    //Fixes defect #465
    private String getInfo(Message msg)
    {
        //(Player name) (action) (target name)
        String returnString = msg.getPlayer().getName();
        
        // IF the player has made an action of type suggestion
        if (msg.getType() == Message.Type.SUGGESTION)
        {
            
            LocationCard loc = (LocationCard)msg.getCards()[0];
            SuspectCard sus = (SuspectCard)msg.getCards()[1];
            VehicleCard veh = (VehicleCard)msg.getCards()[2];

            returnString += " has made a suggestion consisting of "
                + loc.toString() + ", "
                + sus.toString() + ", and "
                + veh.toString() + ".";
        }
        // ELSE IF the player is disproving a suggestion
        else if (msg.getMove() == Message.Move.DISPROVESUGGESTION)
        {
            LocationCard loc = (LocationCard)msg.getCards()[0];
            SuspectCard sus = (SuspectCard)msg.getCards()[1];
            VehicleCard veh = (VehicleCard)msg.getCards()[2];
        
            returnString = "Disprove " + returnString + "'s suggestion of "
                + loc + ", "
                + sus + ", and "
                + veh + ".";
        }
        else
        {
            ActionCard action = (ActionCard)msg.getCards()[0];
            returnString += " has played a " +
                    kActionNames[action.getType().ordinal()];
            //if the action has a target
            if(msg.getType() != Message.Type.ALLSNOOPLEFT &&
                    msg.getType() != Message.Type.ALLSNOOPRIGHT &&
                    msg.getType() != Message.Type.SUPERSLEUTH)
            {
                returnString += " on " + msg.getTarget().getName();
            }
        }
        return returnString + ".";
    }
    
    
    /**
     * Gets the last message printed to the user
     * @return last message sent
     */
    @Override
    public String toString()
    {
        return lastMessage;
    }
    
    private String scan()
    {
        String input = scanner.nextLine();

        // IF input is quit at any point
        if (input.equals("quit"))
        {
            // QUIT
            System.exit(0);
        }

        
        // RETURN input
        return input;
    }
    
}
