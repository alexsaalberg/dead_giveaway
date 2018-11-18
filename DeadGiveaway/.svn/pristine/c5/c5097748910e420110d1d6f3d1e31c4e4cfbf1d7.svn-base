package deadgiveaway.client;

import java.awt.event.*;
import ocsf.client.ObservableClient;
import java.util.*;

import deadgiveaway.*;
import static deadgiveaway.ActionCard.Type.ALLSNOOPLEFT;
import static deadgiveaway.ActionCard.Type.ALLSNOOPRIGHT;
import static deadgiveaway.ActionCard.Type.SNOOP;
import static deadgiveaway.ActionCard.Type.SUGGESTALL;
import static deadgiveaway.ActionCard.Type.SUGGESTCUR;
import static deadgiveaway.LocationCard.Title.TITLE1;
import static deadgiveaway.SuspectCard.Name.NAME1;
import static deadgiveaway.VehicleCard.Model.MODEL1;
import deadgiveaway.server.*;
import java.io.IOException;
import java.util.Random;
import javax.swing.Timer;

/**
  * The ClueClient class provides methods and fields required for communication
  * between a user and the server running the clue game. This client interacts 
  * with a UserInterface and a ClueServer.
  * 
  * @author Jon Kuzmich
  * @author Brad Johnson
  * @version 2.0
  */
public class ClueClient extends ObservableClient implements ActionListener
{
    //Boolean indicating whether the game has started or not
    private boolean gameStarted;
    //The current list of players in the game
    private Player[] players;
    //The index of user in the list of players
    private int thisPlayer;
    //The player reference representing the user
    private Player thisPlayerRef;
    //Whether the user has not made an incorrect accusation
    private boolean inGame;
    //Whether the user is the host of the game or not
    private boolean isHost;
    //The last message recieved from the server
    protected Message curMessage;
    //The next card that this user can draw, as passed by the server
    private Card nextCard;
    //The user interface the user is using
    private UserInterface userInterface;
    //Timer used to time this user's turn
    private final Timer secondsTimer;
    //Timer with a listener used to display this user's turn time
    private final TurnTimer turnTimer;
    //The time left for the user's turn
    private int timeRemaining;
    //
    private boolean lobbyStarted;
    
    /**
     * Constructs a new instance of a ClueClient.
     * @param hostname The name of the host to connect to.
     * @param port The port to connect to on the host machine.
     * @param interfaceChoice The choice of what UI to use.
     * @param interfaceType The type of interface the user interface must be.
     * card.
     */
    public ClueClient(String hostname, int port, UserInterface interfaceChoice,
            Class<? extends UserInterface> interfaceType)
    {
        //CREATE a new ObservableClient by calling super connecting to the
        //server
        super(hostname, port);
     
        thisPlayerRef = new Player("", 0, null);
        //CREATE a new UserInterface from based on the interfaceChoice
        //IF interfaceChoice is null
        if (interfaceChoice == null)
        {
            //SET userInterface to the desired interface type
            try
            {
                interfaceChoice = (UserInterface)interfaceType
                        .getConstructor(ClueClient.class)
                        .newInstance(new Object[] {this});
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        //ENDIF
        
        userInterface = interfaceChoice;
        
        //ADD the user interface as an observer
        addObserver(userInterface);
        
        
        //SET UP a Timer to trigger a TurnTimer every second.
        turnTimer = new TurnTimer(TurnTimer.kSecsInMin, this);
        secondsTimer = new Timer(TurnTimer.kMSinSec, turnTimer);
        
        inGame = false;
        gameStarted = false;
        
        //OPEN the connection to the server
        try
        {
            openConnection();
        }
        catch (java.io.IOException ex)
        {
            System.out.println(ex);
        }
    }
    
    /**
     * Handles a button or option being clicked in the user interface and
     * generates the proper response on behalf of the user.
     * @param evt The event defining which option the user selected.
     */
    @Override
    public void actionPerformed(ActionEvent evt)
    {
        //RESET the turn timer max -- the user has responded
        turnTimer.resetMax();
        
        String command = evt.getActionCommand();

        //IF an action cards was clicked
        if (command.contains("Action"))
        {
            actionPerformedActionCard(Integer.parseInt(command.split(" ")[1]));
        }
        //ELSE IF a clue card was clicked
        else if (command.contains("Clue Card"))
        {
            actionPerformedClueCard(Integer.parseInt(command.split(" ")[2]));
        }
        //ELSE IF the end turn button was clicked
        else if (command.equals("End Turn"))
        {
            actionPerformedEndTurn();
        }
        //ELSE IF the draw button was clicked
        else if (command.equals("Draw Card"))
        {
            //Partially fixes defect 349
            players[thisPlayer].addCard(nextCard);
            updatePlayers(players);
        }
        //ELSE IF the quit button was clicked
        else if (command.equals("Quit"))
        {
            System.exit(0);
        }
        //ELSE IF the create game button was clicked
        else if (command.equals("Create Game"))
        {
            actionPerformedCreateGame();
        }
        //ELSE IF the start game button was clicked
        else if (command.equals("Start Game"))
        {
            handleUserInput(new Message(thisPlayerRef, null, null,
                Message.Move.GAMESTARTED, null, null));
        }
        //ELSE IF the join lobby button was clicked
        else if (command.equals("Join Lobby"))
        {
            actionPerformedJoinLobby();
        }
    }
    
    private void actionPerformedJoinLobby()
    {
        String name = userInterface.getUsername();
        //IF the user didn't enter a name
        if (name.length() == 0)
        {
            //Set the user's name to a random number
            name = "player_" + new java.util.Random().nextInt(999);
        }
        //ELSEIF the user entered a name longer than ten characters
        else if (name.length() > 10)
        {
            //Take only the first ten characters of the user's name
            name = name.substring(0, 10);
        }
        thisPlayerRef.setName(name);
        relayToServer(new Message(thisPlayerRef, null, null,
            Message.Move.PLAYERADDED, null, null));
    }
    
    private void actionPerformedCreateGame()
    {
        String name = userInterface.getUsername();
        int difficulty = userInterface.getAIDifficulty();
        //IF the user didn't enter a name
        if (name.length() == 0)
        {
            //Set the user's name to a random number
            name = "player_" + new java.util.Random().nextInt(999);
        }
        //ELSEIF the user entered a name longer than ten characters
        else if (name.length() > 10)
        {
            //Take only the first ten characters of the user's name
            name = name.substring(0, 10);
        }
        //IF the user didn't pick a difficulty
        if (difficulty < 0)
        {
            difficulty = 0;
        }
        thisPlayerRef.setName(name);
        thisPlayerRef.setDifficulty(difficulty);
        relayToServer(new Message(thisPlayerRef, null, players,
            Message.Move.PLAYERADDED, null, null));
    }
    
    private void actionPerformedActionCard(int index)
    {
        Message msg = null;          // The message to send to the server
        //GET the selected action
        Card[] action = new Card[1]; // The card(s) to send to the server

        //SAVE the selected action
        ActionCard selectedAction = players[thisPlayer].getActionCards()[index];
        action[0] = new ActionCard(selectedAction.getType());
        //SWITCH on action card type
        switch (selectedAction.getType())
        {
            //IF the action is a Suggest All:
            case SUGGESTALL:
                //GET the message containing the suggestion
                msg = actionSuggestAll(action);
                break;
            //ELSE IF the action is a Suggest Current:
            case SUGGESTCUR:
                //GET the message containing the suggestion
                msg = actionSuggestCur(action);
                break;
            //ELSE IF the action is a Snoop:
            case SNOOP:
                msg = actionSnoop(action);
                break;
            //ELSE IF the action is an All Snoop
            case ALLSNOOPLEFT:
                //CREATE the message containing the action.
                msg = actionAllSnoop(action, Message.Type.ALLSNOOPLEFT);
                break;
            case ALLSNOOPRIGHT:
                //CREATE the message containing the action.
                msg = actionAllSnoop(action, Message.Type.ALLSNOOPRIGHT);
                break;
            //ELSE IF the action is a super sleuth or private tip
            default:
                msg = actionSuperSleuthOrPrivateTip(action);
                break;
            //END IF
        }
        //IF the user didn't hit 'cancel':
        if (msg != null)
        {
            //REMOVE the card that was played from the hand.
            players[thisPlayer].removeActionCard(index);
            //DISABLE all buttons.
            userInterface.actionSelected();
            //SEND the message to the client.
            relayToServer(msg);
        }
        //END IF
    }
    
    private Message actionSuperSleuthOrPrivateTip(Card[] action)
    {
        ActionCard selectedAction = (ActionCard)action[0];
        String actionTitle = selectedAction.title();
        Message msg = null;
        
        //IF the action is a super sleuth
        if (actionTitle.equals("Super Sleuth"))
        {
            //Fixes defect #453
            msg = new Message(players[thisPlayer], null, players,
                Message.Move.ACTION, Message.Type.SUPERSLEUTH, action);
        }
        //ELSE IF the action is a private tip
        else
        {
            Player target = userInterface.selectTarget();
            //IF a target is chosen 
            if (target != null)
            {
                //MAKE a new message from the target
                msg = new Message(players[thisPlayer], target, players,
                    Message.Move.ACTION, Message.Type.PRIVATETIP, action);
            }
        }
        
        return msg;
    }
    
    private Message actionSuggestCur(Card[] action)
    {
        Message msg = null;
        
        //GET the message containing the suggestion from a dialog box.
        Card[] suggestCur = userInterface.getSuggestion(players[thisPlayer]
                .getLocation().getTitle().ordinal());
        
        //IF the user cancelled their suggestion
        if (suggestCur == null)
        {
            msg = null;
        }
        //ELSE IF the user moved locations
        else if (suggestCur.length == 1)
        {
            msg = new Message(players[thisPlayer], null, players,
                Message.Move.PLAYERMOVED, null, suggestCur);
        }
        //ELSE the user made a suggestion
        else
        {
            msg = new Message(players[thisPlayer], null, players,
                Message.Move.ACTION, Message.Type.SUGGESTION,
                suggestCur);
        }
        
        return msg;
    }
    
    private Message actionSuggestAll(Card[] action)
    {
        Message msg = null;
        
        //GET the message containing the action from a dialog box.
        Card[] suggestAll = userInterface.getSuggestion(null);

        //IF the user did not cancel their suggestion
        if (suggestAll != null)
        {
            Player target = swapLocations((LocationCard)suggestAll[0]);
            msg = new Message(players[thisPlayer], null, players,
                Message.Move.ACTION, Message.Type.SUGGESTION,
                suggestAll);
        }
        
        return msg;
    }
    
    private Message actionSnoop(Card[] action)
    {
        Message msg = null;
        
        //GET the message containing the snoop from the dialog box.
        Player target = userInterface.selectTarget();
        
        //IF the user picked a target
        if (target != null)
        {
            target = userInterface.getSnoop(target);
        }
        //IF the user did not cancel their snoop
        if (target != null)
        {
            msg = new Message(players[thisPlayer], target, players, 
                Message.Move.ACTION, Message.Type.SNOOP, action);
        }
        
        return msg;
    }
    private Message actionAllSnoop(Card[] action, Message.Type type)
    {
        int player;
        
        //IF the user played an ALLSNOOPLEFT
        if (type == Message.Type.ALLSNOOPLEFT)
        {
            player = (thisPlayer + 1) % players.length;
        }
        //ELSE the user played an ALLSNOOPRIGHT
        else
        {
            player = (thisPlayer + players.length - 1) % players.length;
        }
        
        return new Message(players[thisPlayer], players[player], null,
            Message.Move.ACTION, type, action);
    }
    
    private void actionPerformedClueCard(int index)
    {
        //SEND the selected card to the server
        Card[] clueCard = new Card[1];
        clueCard[0] = players[thisPlayer].getClueCards()[index];
        relayToServer(new Message(players[thisPlayer], null,
                players, Message.Move.SHOWNCARDS,
                null, clueCard));
    }
    
    private void actionPerformedEndTurn()
    {
        relayToServer(new Message(players[thisPlayer], null, null,
                    Message.Move.ENDTURN, null, null));
    }
    
    /**
     * Handles input from the user sent from the user interface.
     * @param input input from the user
     */
    public void handleUserInput(Message input)
    {
        //CHECK if the input is valid message
        //IF the input is not a valid message
        if (input == null)
        {
            //CALL invalidInput in the UI
            userInterface.invalidInput();
            //CALL notifyObservers with curMessage
            setChanged();
            notifyObservers(curMessage);
            
            return;
        }
        //ENDIF

        //SWITCH on the message type
        switch(input.getMove())
        {
            //CASE ACTION
            case ACTION:
                handleUserAction(input);
                break;
            //CASE SUGGESTION
            case SUGGESTION:
                handleUserSuggestion(input);
                break;
            //CASE ACCUSATION
            case ACCUSATION:
                handleUserSuggestion(input);
                //ENDIF
                break;
            //CASE SHOWNCARDS
            case SHOWNCARDS:
                handleUserShowingCards(input);
                break;
            //CASE ENDTURN
            case ENDTURN:
                handleUserEndTurn(input);
                break;
            //DEFAULT
            default:
                //RELAY the message to the server via sendToServer
                relayToServer(input);
        //ENDCASE
        }
    }
    /**
     * Handles a Message object sent from the server by relaying it to the GUI.
     * @param msg message sent from the server
     */
    @Override
    protected void handleMessageFromServer(Object msg)
    {
        Message message = (Message)msg;
        //SET curMessage to the Message received from the server
        curMessage = message;
        
        //IF the server sent an invalid message
        if (message == null)
        {
            return;
        }
        
        //IF the server sent a new list of players
        if (message.getPlayers() != null)
        {
            //UPDATE the player list
            updatePlayers(message.getPlayers());
        }
        
        //IF the server sent a connection established message
        if (message.getMove() == Message.Move.CONNECTIONESTABLISHED)
        {
            thisPlayer = message.getPlayer().getID();
            thisPlayerRef = new Player("", thisPlayer, null);
        }
        //ELSEIF the server sent a connection refused message
        else if (message.getMove() == Message.Move.CONNECTIONREFUSED)
        {
            notifyObservers(message);
            try
            {
                closeConnection();
            }
            catch (IOException e)
            {
                System.out.println(e);
                System.exit(3);
            }
        }
        //ELSE IF the server sent a your turn message
        else if (message.getMove() == Message.Move.YOURTURN)
        {
            messageYourTurn(message);
        }
        //ELSE IF the server sent a shown cards message
        else if (message.getMove() == Message.Move.SHOWNCARDS)
        {
            messageShownCards(message);
        }
        //ELSE IF the server sent a action message
        else if (message.getMove() == Message.Move.ACTION)
        {
            //START the turn timer at 20 seconds
            turnTimer.reset(TurnTimer.kResponseTime);
            secondsTimer.start();
        }
        else 
        {
            continueHandleMessage(message);
        }
        //CALL notifyObservers with the Message
        setChanged();
        notifyObservers(curMessage);
    }
    
    private void continueHandleMessage(Message message)
    {
        //IF the server sent a disprovesuggestion message
        if (message.getMove() == Message.Move.DISPROVESUGGESTION)
        {
            //START the turn timer at 20 seconds
            turnTimer.reset(TurnTimer.kResponseTime);
            secondsTimer.start();
        }
        //ELSE IF the server sent a game started message
        else if (message.getMove() == Message.Move.GAMESTARTED)
        {
            players = message.getPlayers();
            thisPlayerRef = message.getPlayer();
            thisPlayer = thisPlayerRef.getID();
            inGame = true;
            gameStarted = true;
        }
        //ELSE IF the server sent a your accusation message
        else if (message.getMove() == Message.Move.YOURACCUSATION)
        {
            inGame = message.getType() == Message.Type.CORRECTACCUSATION;
        }
        //ELSE IF the server sent a player added message
        else if (message.getMove() == Message.Move.PLAYERADDED)
        {
            updatePlayers(message.getPlayers());
        }
        //Else IF the server sent a lobby started message
        else if (message.getMove() == Message.Move.LOBBYSTARTED)
        {
            isHost = (message.getType() != Message.Type.LOBBYEXISTS);
            lobbyStarted = true;
        }
        //ELSE IF the server sent a resume turn message
        else if (message.getMove() == Message.Move.RESUMETURN)
        {
            turnTimer.reset();
            secondsTimer.start();
        }
    }
    
    private void messageYourTurn(Message message)
    {
        //IF it is the user's turn
        if (message.getPlayer().getID() == thisPlayer)
        {
            //IF the player is eliminated:
            if (!inGame)
            {
                //END the turn
                relayToServer(new Message(players[thisPlayer],
                        null, null, Message.Move.ENDTURN,
                        null, null));
            }
            //ELSE begin a new turn:
            else
            {
                //SET the next card to be drawn
                nextCard = message.getCards()[0];
                //If the user interface is a console UI, the card needs to
                // be added instantly.
                if (userInterface instanceof ConsoleUI)
                {
                    //Fixed defect #429
                    players[thisPlayer].addCard(nextCard);
                    updatePlayers(players);
                }
                //RESET the turn timer
                turnTimer.reset();
            }
            secondsTimer.start();
            //END IF
        }
    }
    
    private void messageShownCards(Message message)
    {
        Message.Type action = message.getType();
        //IF this is the result of an All Snoop:
        if (action == Message.Type.ALLSNOOPRIGHT ||
                action == Message.Type.ALLSNOOPLEFT)
        {
            //SHOW a (meaningless) popup to let the user 'pick' a card
            userInterface.getSnoop(message.getTarget());
        }
        //END IF
    }
    
    protected void updatePlayers(Player[] newPlayers)
    {
        players = newPlayers;
        //IF either the game or the lobby has started
        if (gameStarted || lobbyStarted)
        {
            //FOR all players in the new list of players
            for (Player player : newPlayers)
            {
                //IF the player in the list has the same name as the user player
                if (player.getName().equals(thisPlayerRef.getName()))
                {
                    thisPlayer = player.getID();
                }
                //IF the player's ID matches the user's player ID
                if (player.getID() == thisPlayerRef.getID())
                {
                    thisPlayerRef = player;
                }
            }
        }
        userInterface.updatePlayers(new ArrayList<Player>(Arrays.asList(players)));
    }
    
    protected void setTimeRemaining(int timeRemaining)
    {
        this.timeRemaining = timeRemaining;
        userInterface.setTurnCountdown(String.format("%d:%02d",
               timeRemaining / TurnTimer.kSecsInMin,
               timeRemaining % TurnTimer.kSecsInMin));
    }
    
    protected void timeExpired()
    {
        //STOP the turn timer
        secondsTimer.stop();
        //DECREMENT the allotted maximum time
        turnTimer.setMax(turnTimer.getMax() - turnTimer.kPenalty);
        
        setTimeRemaining(0);
        
        //Make a message with TIMEOUT
        Message timeOut = new Message(null, null, null, Message.Move.TIMEOUT,
                null, null);
        
        //NOTFIY the user interface tha time has expired
        setChanged();
        notifyObservers(timeOut);
        
        //IF the player can end their turn:
        if (curMessage.getMove() == Message.Move.RESUMETURN)
        {
            //END the turn
            actionPerformed(new ActionEvent(this, 0, "End Turn"));
        }
        //ELSE IF it's the player's turn:
        else if(curMessage.getMove() == Message.Move.YOURTURN)
        {
            automateTurn();
        }
        //ELSE (We're responding with a clue card.):
        else
        {
            automateRespond();
        }
    }

    /**
     * Private helper method - makes move for player if they time out
     * on their turn
     */
    private void automateTurn()
    {
        //Random rand = new Random();
        //IF the player only has one action card (they never drew a card):
        if (players[thisPlayer].getNumActions() == 1)
        {
            //DRAW a second card:
            players[thisPlayer].addCard(nextCard);
        }

        //SELECT an action card to perform for the user
        Card[] actionCard = new Card[1]; // An array containing the card
        int randIdx = 0; //rand.nextInt(1);   // The index of the card
        Message msg;

        //IF the card is a Suggest Any, build a suggestion:
        if (players[thisPlayer].getActionCards()[randIdx]
                .getType() == SUGGESTALL)
        {
            //REMOVE the selected card from the hand
            players[thisPlayer].removeActionCard(randIdx);
            //SEND the selected action to the server
            msg = new Message(players[thisPlayer], null,
                    players, Message.Move.ACTION,
                    Message.Type.SUGGESTION, new Card[]
                    {
                        new LocationCard(TITLE1), new SuspectCard(NAME1),
                        new VehicleCard(MODEL1), new ActionCard(SUGGESTALL)
                    });
        }
        //ELSE IF the card is a Suggest Current, build a suggestion:
        else if (players[thisPlayer].getActionCards()[randIdx]
                .getType() == SUGGESTCUR)
        {
            //REMOVE the selected card from the hand
            players[thisPlayer].removeActionCard(randIdx);
            //SEND the selected action to the server
            msg = new Message(players[thisPlayer], null,
                    players, Message.Move.ACTION,
                    Message.Type.SUGGESTION, new Card[]
                    {
                        new LocationCard(
                            players[thisPlayer].getLocation().getTitle()),
                        new SuspectCard(NAME1),
                        new VehicleCard(MODEL1),
                        new ActionCard(SUGGESTCUR)
                    });
        }
        //ELSE we'll send the server the action card:
        else
        {
            actionCard[0] = (players[thisPlayer].getActionCards()
                             [randIdx]);
            //REMOVE the selected card from the hand
            players[thisPlayer].removeActionCard(randIdx);
            msg = new Message(players[thisPlayer], players[(thisPlayer + 1) %
                    players.length], players, Message.Move.ACTION, null,
                    actionCard);
        }
        
        handleUserInput(msg);
        //END IF
    }
    
    /**
     * private helper method - automates response to request for clue card
     * if the player's time runs out
     */
    private void automateRespond()
    {
        //SELECT a valid clue card
        Card[] clueCard = new Card[1]; // An array to contain the response
        Message msg = null;
        ActionCard lastAction = null;
        Card[] possibleSuggest = null;
        
        //IF the user is disproving a suggestion
        if (curMessage.getMove() == Message.Move.DISPROVESUGGESTION)
        {
            //SET the last played action card
            lastAction = (ActionCard)curMessage.getCards()[3];
            //SET the suggestion
            possibleSuggest = curMessage.getCards();
        }
        //ELSEIF the user is responding to an action card
        else if (curMessage.getMove() == Message.Move.ACTION)
        {
            //SET the last played action card
            lastAction = (ActionCard)curMessage.getCards()[0];
        }
        
        //CHECK for clue card that can be shown
        //IF clue card 1 is a match
        if(lastAction.matchesCard(players[thisPlayer].getClueCards()[0],
                possibleSuggest))
        {
            //SEND the selected card to the server
            clueCard[0] = (players[thisPlayer].getClueCards()[0]);
            msg = new Message(players[thisPlayer], null, players,
                    Message.Move.SHOWNCARDS, null, clueCard);
        }
        //CHECK for second clue card is a match
        else if (lastAction.matchesCard(players[thisPlayer].getClueCards()[1],
                possibleSuggest))
        {
            //SEND the selected card to the server
            clueCard[0] = (players[thisPlayer].getClueCards()[1]);
            msg = new Message(players[thisPlayer], null, players,
                    Message.Move.SHOWNCARDS, null, clueCard);
        }
        //CHECK for third clue card is a match
        else if (lastAction.matchesCard(players[thisPlayer].getClueCards()[2],
                possibleSuggest))
        {
            //SEND the selected card to the server
            clueCard[0] = (players[thisPlayer].getClueCards()[2]);
            msg = new Message(players[thisPlayer], null, players,
                    Message.Move.SHOWNCARDS, null, clueCard);
        }
        // CHECK for fourth clue card is a match
        else if (players[thisPlayer].getNumCards() > 3 &&
                lastAction.matchesCard(players[thisPlayer].getClueCards()[3],
                possibleSuggest))
        {
            //SEND the selected card to the server
            clueCard[0] = (players[thisPlayer].getClueCards()[3]);
            msg = new Message(players[thisPlayer], null, players,
                    Message.Move.SHOWNCARDS, null, clueCard);
        }
        //ELSE: (No valid responses)
        else
        {
            msg = new Message(players[thisPlayer], null,
                    null, Message.Move.SHOWNCARDS, null, null);
        }
        
        relayToServer(msg);     
    }
    
    /**
     * Moves to a new location.
     * @param dest The location to move to.
     * @return The player with whom locations were swapped, null if there was
     * none
     */
    private Player swapLocations(LocationCard dest)
    {
        int playerIdx = 0; // The index of the current player being checked
        Player other = players[playerIdx++]; // One of the other players

        //SEARCH all the other players.
        while (other != null && !other.getLocation().equals(dest))
        {
            //IF idx within bounds
            if (playerIdx < players.length)
            {
                other = players[playerIdx++];
            }
            else
            {
                other = null;
            }
        }

        //IF another player occupies the location:
        if (other != null)
        {
            //SWAP locations with that player.
            LocationCard temp = players[thisPlayer].getLocation();
            players[thisPlayer].setLocation(other.getLocation());
            other.setLocation(temp);
        }
        //ELSE:
        else
        {
            //SET the new location.
            players[thisPlayer].setLocation(dest);
        }
        //ENDIF

        return other;
    }
    
    /**
     * Accessor method for the user interface.
     * @return The user interface being used by the client.
     */
    public UserInterface getInterface() 
    {
        return userInterface;
    }
    
    
    private void relayToServer(Object input)
    {
        //STOP the turn timer.
        secondsTimer.stop();
        
        try 
        {
            sendToServer(input);
        }
        catch (java.io.IOException e)
        {
            System.out.println(e);
        }
    }
    
        
    private void handleUserAction(Message input)
    {
        //CHECK if the user can play an action
        //IF curMessage has Move enum of RESUMETURN
        if (curMessage.getMove() == Message.Move.RESUMETURN)
        {
            //CALL invalidInput in the UI
            userInterface.invalidInput();
            //CALL notifyObservers with curMessage
            setChanged();
            notifyObservers(curMessage);
        }
        //ELSE
        else
        {
            //RELAY the message to the server
            relayToServer(input);
        }
        //ENDIF
    }
    
    private void handleUserSuggestion(Message input)
    {
        //CHECK if the suggestion is valid
        //IF the suggestion is not valid
        if (!isValid(input))
        {
            //CALL invalidInput in the UI
            userInterface.invalidInput();
            //CALL notifyObservers with a RESUMETURN message
            setChanged();
            notifyObservers(new Message(null, null, null,
                    Message.Move.RESUMETURN, null, null));
        }
        //ELSE
        else
        {
            //RELAY the message to the server with sendToServer
            relayToServer(input);
        }
        //ENDIF
    }
    
    private void handleUserShowingCards(Message input)
    {
        //CHECK if the shown card is valid for the last played action
        //IF the card is not valid
        if(!isValid(input) || !isCorrect(input))
        {    
            //CALL invalidInput in the UI
            userInterface.invalidInput();
            //CALL notifyObservers with curMessage
            setChanged();
            notifyObservers(curMessage);
        }
        //ELSE
        else
        {
            //RELAY the message to the server with sendToServer
            relayToServer(input);
        }
        //ENDIF
    }
    
    private void handleUserEndTurn(Message input)
    {
        //CHECK if the user can end their turn
        //IF curMessage does not have a Move enum of RESUMETURN
        if(curMessage.getMove() != Message.Move.RESUMETURN
                && curMessage.getMove() != Message.Move.SHOWNCARDS)
        {    
            //CALL invalidInput of the UI
            userInterface.invalidInput();
            //CALL notifyObservers with curMessage
            setChanged();
            notifyObservers(curMessage);
        //ELSE
        }
        else
        {
            //RELAY the message to the server
            relayToServer(input);
        }
        //ENDIF
    }
    
    private boolean isValid(Message msg)
    {
        boolean valid = true;
        
        //IF the user is showing cards
        if(msg.getMove() == Message.Move.SHOWNCARDS)
        {
            //SET the card's validity to false if it's an action
            valid = !(msg.getCards()[0] instanceof ActionCard);
        }
        //ELSEIF the user is making a suggestion
        else if (msg.getMove() == Message.Move.SUGGESTION)
        {
            //CHECK if the suggestion if formatted properly
            Card[] cards = msg.getCards();
            valid = isValidAccusation(cards) && cards[3] instanceof ActionCard;
        }
        //ELSEIF the user is making an accusation
        else
        {
            //CHECK if the accusation is formatted properly
            valid = isValidAccusation(msg.getCards());
        }
        
        return valid;
    }
    
    private boolean isCorrect(Message input)
    {
        ActionCard lastAction = null;
        Card shownCard = input.getCards()[0];
        Card[] possibleSuggest = null;
        
        //IF the user is disproving a suggestion
        if (curMessage.getMove() == Message.Move.DISPROVESUGGESTION)
        {
            //SET the last played action card
            lastAction = (ActionCard)curMessage.getCards()[3];
            //SET the suggestion
            possibleSuggest = curMessage.getCards();
        }
        //ELSEIF the user is responding to an action card
        else if (curMessage.getMove() == Message.Move.ACTION)
        {
            //SET the last played action card
            lastAction = (ActionCard)curMessage.getCards()[0];
        }
        
        //RETURN whether the shown card is a correct response
        return lastAction.matchesCard(shownCard, possibleSuggest);
    }
    
    private boolean isValidAccusation(Card[] cards)
    {
        return cards[0] instanceof LocationCard &&
               cards[1] instanceof SuspectCard &&
               cards[2] instanceof VehicleCard;
    }
    
    @Override
    protected void connectionClosed()
    {
        
    }

    @Override
    protected void connectionException(Exception exception)
    {

    }

    @Override
    protected void connectionEstablished()
    {

    }
}
