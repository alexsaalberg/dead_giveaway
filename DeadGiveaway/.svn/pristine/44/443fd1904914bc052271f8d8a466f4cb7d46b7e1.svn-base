package deadgiveaway.server;

import deadgiveaway.Message;
import deadgiveaway.Message.*;
import deadgiveaway.Card;
import deadgiveaway.ActionCard;
import deadgiveaway.LocationCard;
import deadgiveaway.SuspectCard;
import deadgiveaway.VehicleCard;
import static deadgiveaway.server.ClueServerHelper.*;
import ocsf.server.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Arrays;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import deadgiveaway.fake.*;
/**
 * The ClueServer class provides methods and fields required to encapsulate a
 * server running the clue game. This server interacts with at least two
 * ClueClient s to carry out a game of the card game Clue.
 *
 * @author Alex Saalberg
 * @version 2.0
 */
public class ClueServer extends AbstractServer
{
    /**
     * clients - list of clients
     */
    private volatile ConnectionToClient[] connectionIndices;
    /**
     * clients - list of clients
     */
    private volatile ConnectionToClient[] clients = {null, null, 
        null, null, null};
    /**
     * players - arraylist of all players in the game
     */
    protected ArrayList<Player> players;
    /**
     * waitingOn - list of players we are waiting for
     */
    protected ArrayList<Player> waitingOn;
    /**
     * suggestion - suggestion currently being waited on
     */
    protected Card[] suggestion;
    /**
     * random - A random number generator to be used when RNG is needed
     */
    private Random random;
    /**
     * turn - index of the player whose turn it is
     */
    private int turn;
    /**
     * mutex - number of messages the server is waiting on
     */
    private volatile int mutex = 0;
    /**
     * playedCard - used for super sleuth and private tip cards to hold which
     * type of card was played while waiting for a response
     */
    private Card playedCard;
    /**
     * gameStarted - whether the game has started
     */
    private volatile boolean gameStarted;
    /**
     * lobbyStarted - whether the lobby has been created
     */
    private boolean lobbyStarted;
    /**
     * numAccusations - Variable to track the number of accusations received
     */
    private volatile int numAccusations = 0;
    /**
     * semaphore - Semaphore for limiting response handling
     */
    private final Semaphore semaphore;
    /**
     * clueServerHelper - A helper object used for printing
     */
    private ClueServerHelper clueServerHelper;
    
    private ArrayList<Player> connected;
    
    private ClueServerDeck clueServerDeck;
    

    /**
     * Constructs a new ClueServer using a given port. Calls to the the
     * superclass constructor, sets the turn to 0, and initializes a randomized
     * list of locationCards, cluecards, actionCards, cluecards, and a blank
     * list of players, waitingOn, solution, and locations.
     *
     * @param port The port this server will listen on.
     * @param seed Seed for random object
     * @param clueServerHelper ClueServerHelper for printing
     *
     * @author Alex Saalberg
     * @version 2.0
     */
    public ClueServer(int port, Integer seed, ClueServerHelper clueServerHelper)
    {
        super(port);
        
        connectionIndices = new ConnectionToClient[] { null, null, null,
            null, null};
        //players - List of players
        players = new ArrayList<Player>();
        //connected - list of connected players.
        connected = new ArrayList<Player>();
        //waitingOn - List of players we are waiting for responses from.
        waitingOn = new ArrayList<Player>();
        //playedCard - Card that we are waiting for responses to.
        playedCard = null;
        //gameStarted - Whether the game is started or not.
        gameStarted = false;
        //lobbyStarted - Whether the lobby is started or not.
        lobbyStarted = false;
        //turn - The idx of the current player. Used as idx of host player until
        //the game starts.
        semaphore = new Semaphore(1);
        //clueServerHelper - A helper object for printing
        this.clueServerHelper = clueServerHelper;
        
        //SET turn to -1
        turn = -1;

        
        //IF seed parameter is not null
        if (seed != null)
        {
            //INITIALIZE randomw with seed
            //random - Random object used for RNG
            random = new Random(seed);
        }
        //ELSE
        else
        {
            //INITIALIZE random with no seed
            //random - Random object used for RNG
            random = new Random();
        } //ENDIF
        
        clueServerDeck = new ClueServerDeck(random, clueServerHelper);
    }

    /**
     * Have the server start running turns.
     *
     * @author Alex Saalberg
     * @param shuffle Whether to shuffle or not
     */
    public void start(boolean shuffle)
    {
        //PRINT using printToConsole 
        //"Server: now starting with "+getNumberOfClients()+" clients"
        clueServerHelper.printToConsole("Server: Now starting with "
                + getNumberOfClients() + " clients");

        //IF gameStarted or there are no clients, do nothing
        if (!gameStarted && ClueServerHelper.getHumanPlayerCount(players) > 0)
        {
            //SET gameStarted to TRUE
            gameStarted = true;
            players = ClueServerHelper.sortPlayers(players);
            
            //CALL initSolution
            clueServerDeck.initSolution();

            //IF shuffle
            if (shuffle)
            {
                //SHUFFLE clueCards
                clueServerDeck.shuffleClueCards();
                clueServerDeck.shuffleLocationMarkets();

                //CALL reshuffleDeck
                reshuffleDeck();
            } //ENDIF
            
            
            //CALL initRobotPlayers on clueServerHelper
            players = ClueServerHelper.initRobotPlayers(players, random);
            
            //REPLACE players with return from initPlayerLocations(players)
            players = clueServerDeck.initPlayerLocations(players);

            //REPLACE players with return from dealAllCluerCards(players)
            players = clueServerDeck.dealAllClueCards(players);

            //REPLACE players with return from dealActionCards(players)
            players = clueServerDeck.dealActionCards(players);

            //REPLACE players with return from populateLists(players)
            players = ClueServerHelper.populatePlayerInternalLists(players);

            //CALL startSendMessages
            startSendMessages();
        } //ENDIF
    }

    /**
     * Establishes a new human player when a client connects.
     *
     * @param connection The connection to the client
     * @author Alex Saalberg
     */
    @Override
    public synchronized void clientConnected(ConnectionToClient connection)
    {
        //Fixed defect #421
        //int idx - idx of connected player
        int idx = 0;
        //WHILE clients[idx] is not null and idx < 5
        while (clients[idx] != null && idx < 5)
        {
            //increment idx
            idx++;
        }
        //IF idx is 5 (clients has no nulls)
        if (idx == 5)
        {
            //TRY
            try
            {
                //SEND a CONNECTIONREFUSED to connection
                connection.sendToClient(new Message(null, null, null,
                    Move.CONNECTIONREFUSED, null, null));
            } 
            catch (IOException ex)
            {
                System.out.println(ex);
            }
            return;
        }
        
        //CALL clientConnectedAddPlayer with connection and idx
        clientConnectedAddPlayer(connection, idx);
    }

    /**
     * Replaces a human player with a robot player when the former disconnects
     *
     * @param connection The connection that was lost
     *
     * @author Alex Saalberg
     */
    @Override
    public void clientDisconnected(ConnectionToClient connection)
    {
        //robotPlayer - RobotPlayer to replace disconnected Player with
        RobotPlayer robotPlayer;

        //CALL getPlayerFromConnection with connection, set dcer to it
        Player dcer = getPlayerFromConnection(connection);

        //dcerClueCards - Temporary storage for transferring clueCards
        Card[] dcerClueCards;

        //dcerActionCards - Temporary storage for transferring actionCards
        Card[] dcerActionCards;

        //CALL printToConsole with parameter 
        //"Server: <IP>" disconnected."
        //clueServerHelper.printToConsole("Server: " + connection + " disconnected");
        
        //IF the lobby OR game have started AND this is the last client.
        if ((lobbyStarted || gameStarted) && this.getNumberOfClients() == 1)
        {
            //CALL closeServer
            closeServer();
        }
        //IF game started
        else if (gameStarted)
        {
            clientDisconnectedGameStarted(dcer);
        }
        //ELSE IF lobby is started and game is not started and dcer is not null
        else if (lobbyStarted && !gameStarted && dcer != null)
        {
            clientDisconnectedLobbyStarted(connection, dcer);
        } //ENDIF
        
        //FOR each Player player in connected
        for (Player player : connected)
        {
            //IF current player is the disconnector
            if (clients[player.getID()] == connection)
            {
                //SET returnPlayer to current player 
                dcer = player;
                clients[player.getID()] = null;
            } //ENDIF
        }
        //REMOVE dcer from connection
        connected.remove(dcer);
        
        //SEND a PLAYERADDED to every client.
        deepCopyPlayers();
        sendToAllClients(new Message(dcer, null, players.toArray(new Player[0]),
                Message.Move.PLAYERADDED, null, null));
    }

    /**
     * Handles incoming data packets from clients. Processes the data and sends
     * the results to the players.
     *
     * @param msg the incoming data packet
     * @param client The connection to the client the handled message if from
     * @author Alex Saalberg
     */
    @Override
    protected void handleMessageFromClient(Object msg,
            ConnectionToClient client)
    {
        //messageObj - Message object. Used to store msg
        Message messageObj;

        //IF msg is not instanceof Message
        if (!(msg instanceof Message))
        {
            //THROW IllegalArgumentException
            throw new IllegalArgumentException("msg is not a Message");
        } //ENDIF

        //SET messageObj to msg
        messageObj = (Message) msg;
        //IF message is from a robotPlayer (connection is null)
        if (client == null)
        {
            //PRINT using printToDebug; "Server: Message from Robot: messageObj"
            clueServerHelper.printToDebug("Server: Message from Robot: " + messageObj);
        } 
        //ELSE
        else
        {
            //PRINT using printToDebug; "Server: Message from <IP>: messageObj"
            clueServerHelper.printToDebug(
                    "Server: Message from " + client + ": " + messageObj);
        } //ENDIF

        //IF messageObj.getPlayers() is not NULL
        if (messageObj.getPlayers() != null)
        {
            //SET players to messageObj's players list
            players = new ArrayList<Player>(
                    Arrays.asList(messageObj.getPlayers()));

            //PRINT using printToDebug; "Server: Player list overwritten"
            clueServerHelper.printToDebug("Server: Player list overwritten");
        } //ENDIF

        //IF game has started and not messageObj.player's turn (Response)
        if (gameStarted && messageObj.getPlayer().getID() != turn)
        {
            //IF playedCard is null
            if (playedCard == null)
            {
                //THROW IllegalArgumentException
                throw new IllegalArgumentException("playedCard is null. "
                        + "Turn is " + turn + ", but player's id is "
                        + messageObj.getPlayer().getID() + ".");
            }
            //ELSEIF playedCard is super sleuth
            else if ("Super Sleuth".equals(playedCard.toString()))
            {
                //CALL handleSuperSleuthResponse with messageObj
                handleSuperSleuthResponse(messageObj);
            }
            //ELSEIF playedCard is suggestion
            else if ("Suggestion".equals(playedCard.toString()))
            {
                //CALL handleSuggestionResponse with messageObj
                handleSuggestionResponse(messageObj);
            } //ENDIF
        }
        //ELSE
        else
        {
            handleMessageMoveSwitch(messageObj, client);
        } //ENDIF
    }

    /**
     * Accessor method for players list
     *
     * @return the player list the server is using
     * @author Alex Saalberg
     */
    public ArrayList<Player> getPlayers()
    {
        //returnList - list that will be returned
        ArrayList<Player> returnList = players;

        deepCopyPlayers();
        
        //RETURN a deep copy of players.
        return returnList;
    }

    /**
     * Accessor method for the solution
     *
     * @return the solution chosen by the server
     * @author Alex Saalberg
     */
    public ArrayList<Card> getSolution()
    {
        return clueServerDeck.getSolution();
    }

    /**
     * Accessor method for the deck
     *
     * @return the deck of actionCards used by the server
     * @author Alex Saalberg
     */
    public ArrayList<ActionCard> getDeck()
    {
        return clueServerDeck.getActionDeck();
    }

    /**
     * Accessor method for turn
     *
     * @return Current turn idx
     * @author Alex Saalberg
     */
    public int getTurn()
    {
        return turn;
    }

    /**
     * Accessor method for if the game is started
     *
     * @return true if the game has started, false otherwise
     * @author Alex Saalberg
     */
    public boolean isStarted()
    {
        return gameStarted;
    }
    
    /**
     * Send msg to all clients in connectionIndices.
     * 
     * @param msg  Message to send to all clients.
     */
    @Override
    public void sendToAllClients(Object msg)
    {
        try
        {
            Thread[] threadArray = this.getClientConnections();

            //FOR each ConnectionToClient client in connectionIndices
            for (ConnectionToClient client : connectionIndices)
            {
                //TRY
                try
                {
                    //IF client is not null.
                    if (client != null)
                    {
                        //SEND client msg
                        client.sendToClient(msg);
                    }
                }
                //CATCH IOException ex
                catch (IOException ex)
                {
                    //PRINT using println
                    System.out.println(ex);
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            ex.printStackTrace();
        }
    }

    /**
     * Sends msg to player. If player is a robot, do nothing.
     *
     * @param player The player to send a message to.
     * @param msg The message to send.
     * @author Alex Saalberg
     */
    protected void sendToPlayer(Player player, Message msg)
    {
        //IF player is a humanPlayer
        if (!(player instanceof RobotPlayer))
        {
            //Send message to client
            try
            {
                ConnectionToClient cc = connectionIndices[player.getID()];
                // IF the connection exists
                if (cc != null)
                {
                    cc.sendToClient(msg);
                }
            }
            catch (IOException ex)
            {
                System.out.println(ex);
            }
        }
    }

    /**
     * Sends to every Player except player
     * 
     * @param player The player who does not get a message.
     * @param msg The message to send.
     * @author Alex Saalberg
     */
    private void sendToAllExceptPlayer(Player player, Message msg)
    {
        //FOR each Player loopPlayer in players
        for (Player loopPlayer : players)
        {
            //IF loopPlayer is not player
            if (loopPlayer.getID() != player.getID())
            {
                //SEND msg to loopPlayer
                sendToPlayer(loopPlayer, msg);
            } //ENDIF
        } //ENDFOR
    }

    /**
     * Calls close() with exception handling
     *
     * @author Alex Saalberg
     */
    private void closeServer()
    {
        //TRY
        try
        {
            //CALL close to close the server
            Thread.sleep(5000);
            close();
        } //CATCH IOException
        catch (IOException ex)
        {
            System.out.println(ex);
        }
        catch (InterruptedException ex)
        {
            System.out.println(ex);
        }
    }

    /**
     * Ends the game.
     *
     * @author Alex Saalberg
     */
    private void gameOver()
    {
        //SEND to everyone: Message(Player who made accusation, null, 
        //Move.ACCUSATION, ‚ÄúGame Over‚Äù, null)
        deepCopyPlayers();
        Message gameOverMessage = new Message(null, null,
                players.toArray(new Player[players.size()]),
                Move.ACCUSATION, Type.INCORRECTACCUSATION, null);
        sendToAllClients(gameOverMessage);

        //CLOSE the server
        closeServer();
    }

    //Fixes defect 438
    /**
     * Creates a deep copy of all players in the player list.
     * To be called before sending a message with an updated player list.
     */
    private void deepCopyPlayers()
    {
        //REPLACE players with deepCopyPlayers(players) on clueServerHelper.
        players = ClueServerHelper.deepCopyPlayers(players);
    }

    /**
     * Reshuffles the deck when all used up
     *
     * @author Alex Saalberg
     */
    private void reshuffleDeck()
    {
        clueServerDeck.reshuffleActionDeck();
    }
    
    private void clientDisconnectedGameStarted(Player dcer)
    {
        Card[] dcerClueCards;
        Card[] dcerActionCards;
        RobotPlayer robotPlayer;

        //IF the server is not closed THEN
        if (!isClosed())
        {
            players = replacePlayerWithRobot(players, dcer, random);
            
            //IF the player at dcer.id is not a RobotPlayer
            if (!(players.get(dcer.getID()) instanceof RobotPlayer))
            {
                clueServerHelper.printToConsole(
                        "Player wasn't replaced correctly");
            }
            robotPlayer = (RobotPlayer) players.get(dcer.getID());

            //SEND a message to all clients that the player has disconnected

            //IF the player disconnected on their own turn
            if (turn == dcer.getID())
            {
                //CALL nextTurn
                nextTurn();
            } //ENDIF

            removePlayerFromWaitingOn(dcer, robotPlayer);
        }
    }

    private void clientDisconnectedLobbyStarted(ConnectionToClient connection, 
            Player dcer)
    {
        //REMOVE the disconnected player from the player list
        for (int idx = players.size() - 1; idx >= 0; idx--)
        {
            //IF players.idx.idx is dcer.idx
            if (players.get(idx).getID() == dcer.getID())
            {
                //REMOVE dcer from players
                players.remove(dcer);
            }
            //ELSE IF dcer's idx is less than players.idx.idx
            else if (dcer.getID() < players.get(idx).getID())
            {
                //SWAP positions in players with id-1
                players.get(idx).setID(players.get(idx).getID() - 1);
            }
        }
        //removed - if a connecton has been removed.
        boolean removed = false;
        //for each idx from 0 to connectionIndices.length
        for (int idx = 0; idx < connectionIndices.length; idx++)
        {
            //IF connectionIndices[idx] == connection
            if (connectionIndices[idx] == connection)
            {
                //SET connectionIndices to null
                connectionIndices[idx] = null;
                //SET removed to TRUE
                removed = true;
            }
            //ELSE IF removed is TRUE
            else if (removed)
            {
                //SET connectionIndices[idx-1] to connectionIndices[idx]
                connectionIndices[idx - 1] = connectionIndices[idx];
            }
        }
        //SET removed to FALSE
        removed = false;
        //FOR idx from 0 to clients.length
        for (int idx = 0; idx < clients.length; idx++)
        {
            //IF clients[idx] is connection
            if (clients[idx] == connection)
            {
                //SET clients[idx] to null
                clients[idx] = null;
                //SET removed to TRUE
                removed = true;
            }
            //ELSE IF removed is TRUE
            else if (removed)
            {
                //SET clients[idx-1] to clients[idx]
                clients[idx - 1] = clients[idx];
            } //ENDIF
        } //ENDFOR
    }

    /**
     * Adds a player from an id. Called exclusively from clientConnected
     * 
     * @param connection connection that connected
     * @param idx id chosen for player
     * @author Alex Saalberg
     */
    private void clientConnectedAddPlayer(ConnectionToClient connection, int idx)
    {
        
        //Player player - used for player who connected
        Player player = new Player("Player " + idx, idx, null);
        connected.add(player);

        //PRINT using printToServer; "Server: <X> connected." <X> = ip
        clueServerHelper.printToConsole("Server: " + connection + " connected");
        
        //ADD player to players
        //players.add(player);
        //ADD connection to clients[idx], the ConnectionToClient array
        clients[idx] = connection;
        //CALL setInfo on connection with parameters "ID", idx
        connection.setInfo("ID", idx);
        //TRY
        try
        {
            //IF lobbyStarted
            if (lobbyStarted)
            {
                //SEND a CONNECTIONESTABLISHED message
                connection.sendToClient(new Message(player, null,
                        players.toArray(new Player[0]),
                        Move.CONNECTIONESTABLISHED, Message.Type.LOBBYEXISTS, null));
            }
            //ELSE
            else
            {
                //SEND a CONNECTIONESTABLISHED message
                connection.sendToClient(new Message(player, null,
                        null, Move.CONNECTIONESTABLISHED, null, null));
            }
        }
        //CATCH IOException
        catch (IOException ex)
        {
            System.out.println(ex);
        }

        //IF gameStarted
        if (gameStarted)
        {
            //TRY
            try
            {
                //SEND a message to connection with every field null except
                //for Move, which should be CONNECTIONREFUSED
                connection.sendToClient(new Message(null, null, null,
                        Move.CONNECTIONREFUSED, null, null));
            }
            //CATCH IOException
            catch (IOException ex)
            {
                System.out.println(ex);
            }

            //TRY
            try
            {
                //CLOSE the connection using close
                connection.close();
            }
            //CATCH IOException
            catch (IOException ex)
            {
                System.out.println(ex);
            }
        }
        //ELSE
        else
        {
            //PRINT using printToServer; "Server: <X> connected to server.
            clueServerHelper.printToConsole("Server: " + player.getName() + " added.");
        } //ENDIF
    }
    
    private void startSendMessages()
    {
        //playerIndex - index of Player player in players
        int playerIndex = 0;
        //FOR each Player player in players
        for (Player player : players)
        {
            //IF player is human
            if (!(player instanceof RobotPlayer))
            {
                //SEND them a gameStarted message, include new players list
                deepCopyPlayers();
                //IF clients[playersIndex] is != null
                if (clients[playerIndex] != null)
                {
                    try
                    {
                        clients[playerIndex].sendToClient(
                                new Message(player, null,
                                players.toArray(new Player[0]),
                                Move.GAMESTARTED, null, null));
                    }
                    catch (IOException ex)
                    {
                        System.out.println(ex);
                    }
                }
            } //ENDIF
            playerIndex++;
        } //ENDFOR

        //SET turn to 0
        turn = 0;
        deepCopyPlayers();
        //SEND a YourTurn message to everyone, player field's idx is turn
        Message yourTurnMessage = new Message(players.get(turn), null,
                players.toArray(new Player[players.size()]), Move.YOURTURN,
                null, clueServerDeck.drawActionCard());
        sendToAllClients(yourTurnMessage);
    }
    
    /**
     * If dcer is in waitingOn, removes them and handles their response with
     * robotPlayer Only called from ClientDisconnected
     *
     * @author Alex Saalberg
     * @param dcer Player who disconnected
     * @param robotPlayer RobotPlayer who replaced dcer
     */
    private void removePlayerFromWaitingOn(Player dcer, RobotPlayer robotPlayer)
    {
        //card - Card used for suggestion
        Card card = null;

        //FOR all players that the server is waiting to respond
        for (int idx = 0; idx < waitingOn.size(); idx++)
        {
            //IF dcer is player
            if (dcer.getID() == waitingOn.get(idx).getID())
            {
                //IF playedCard is null
                if (playedCard == null)
                {
                    
                }
                //ELSEIF playedCard is Super Sleuth
                else if ("Super Sleuth".equals(playedCard.toString()))
                {
                    //Fix defect #470: Super sleuth goes through proper method
                    //FIND a card that can disprove the suggestion
                    for (Card playerCard : robotPlayer.getClueCards())
                    {
                        //IF playersCard.matchesCard(playerCard, null)
                        if (((ActionCard)playedCard).matchesCard(playerCard, null))
                        {
                            //SET card equal to playerCard
                            card = playerCard;
                        } //ENDIF
                    } //ENDFOR
                    Card[] shown = new Card[1];
                    shown[0] = card;
                    //CREATE a super sleuth response from the new robot
                    Message newMsg = new Message(robotPlayer, players.get(turn),
                            null, Message.Move.SHOWNCARDS, null, shown);
                    handleSuperSleuthResponse(newMsg);
                } //ENDIF
                //ELSEIF playedCard is Suggestion
                else if ("Suggestion".equals(playedCard.toString()))
                {
                    //FIND a card that can disprove the suggestion
                    for (Card playerCard : dcer.getClueCards())
                    {
                        //IF playedCard.matchesCard(playerCard, suggestion)
                        if (((ActionCard)playedCard).matchesCard(playerCard,
                                suggestion))
                        {
                            //SET card equal to playerCard
                            card = playerCard;
                        } //ENDIF
                    } //ENDFOR
                    //shown - array of cards containing the disproving card
                    Card[] shown = new Card[1];
                    shown[0] = card;
                    //CREATE a suggestion response from the new robot
                    Message newMsg = new Message(robotPlayer, players.get(turn),
                            null, Message.Move.SUGGESTION, null, shown);
                    handleSuggestionResponse(newMsg);
                } //ENDIF
            } //ENDIF
        } //ENDFOR
    }

    /**
     * Finds a non-RobotPlayer associated with connection.
     *
     * @param connection
     * @return Player associated with connection, or null if none.
     * @author Alex Saalberg
     */
    private Player getPlayerFromConnection(ConnectionToClient connection)
    {
        //returnPlayer - player associated with this connection.
        Player returnPlayer = null;

        //FOR all players
        for (Player player : players)
        {
            //IF current player is a Human player and is the disconnector
            if (!(player instanceof RobotPlayer)
                    && (clients[player.getID()] == connection))
            {
                //SET returnPlayer to current player 
                returnPlayer = player;
            } //ENDIF
        } //ENDFOR
        
        //return returnPLayer
        return returnPlayer;
    }

    /**
     * Called when all responses are in. Resets playerCard to null and sends
     * resume message to the player whose turn it is.
     * 
     * @author Alex Saalberg
     */
    private void doneWaitingOnResponses()
    {
        //IF waitingOn isn't empty
        if (!waitingOn.isEmpty())
        {
            //PRINT using printToDebug; "Clearing waitingOn.."
            clueServerHelper.printToDebug("Server: Clearing waitingOn unexpectedly");
            //CALL clear ON waitingOn
            waitingOn.clear();
        } //ENDIF

        //SET playedCard to null
        playedCard = null;
        sendResumeTurnMessage();
    }
    
    private void sendResumeTurnMessage()
    {
        //Fixes defect#468: Players weren't being updated after moving locations
        //IF the player whose turn it is is a RobotPlayer
        if (players.get(turn) instanceof RobotPlayer)
        {
            //SEND a YOURACCUSATION message to RobotPlayer
            ((RobotPlayer) players.get(turn)).selectAction(
                    new Message(players.get(turn), null,
                    players.toArray(new Player[0]),
                    Message.Move.YOURACCUSATION, null, null), this);
        }
        //ELSE (player whose turn it is is a human)
        else
        {
            //SEND a RESUMETURN message to the player using sendToPlayer.
            sendToPlayer(players.get(turn),
                    new Message(players.get(turn), null,
                    players.toArray(new Player[0]),
                    Message.Move.RESUMETURN, null, null));
        } //ENDIF
    }

    /**
     * Finds the next non-out player and tells everyone it is their turn If
     * every player is out, end the game.
     *
     * @author Alex Saalberg
     */
    private void nextTurn()
    {
        //idx - int used to find idx of who should have the next turn.
        int idx;

        //player - player who will take a turn after this method
        Player player;

        //SET idx to turn
        idx = turn;

        //DO
        do
        {
            //SET idx to the next CW idx
            idx = (idx + 1) % players.size();
        }
        //WHILE idx is not turn and the player
        while (idx != turn && players.get(idx).getOut());

        //SET turn to idx
        turn = idx;

        //SET player to current playing player
        player = players.get(turn);

        //SEND to everyone: Message(player who‚Äôs turn it is, null, players, 
        //Move.YOURTURN, ‚ÄúYour Turn‚Äù, next card to be drawn)
        deepCopyPlayers();
        Message yourTurnMessage = new Message(player, null, players.toArray(
                new Player[players.size()]), Move.YOURTURN, null,
                clueServerDeck.drawActionCard());
        sendToAllClients(yourTurnMessage);

        //PRINT using printToConsole
        //"-Turn Starting: <X> is starting their turn.-"
        clueServerHelper.printToConsole("-Turn Starting: "
                + player.getName() + " is starting their turn.-");

        //PRINT playerlist using printPlayers, which will use printToDebug
        clueServerHelper.printPlayers(players);
        
        //IF the current player (turn) is a robotPlayer
        if (player instanceof RobotPlayer)
        {
            //SELECT an action for the robot player
            RobotPlayer robotPlayer = (RobotPlayer) player;
            robotPlayer.selectAction(yourTurnMessage, this);
        } //ENDIF
    }

    /**
     * Sends a YOURACCUSATION method to the passed in robotPlayer
     * 
     * @param robotPlayer 
     * @author Alex Saalberg
     */
    private void tellRobotToEndTurn(RobotPlayer robotPlayer)
    {
        robotPlayer.selectAction(new Message(players.get(turn), null, null,
                Message.Move.YOURACCUSATION, null, null), this);
    }


    

    

    /**
     * Handles the Move switch formerly within handleMessageFromClient. Should
     * only be called by handleMessageFromClient
     *
     * @author Alex Saalberg
     * @param msg Message from client
     * @param client the client that sent the message
     */
    private void handleMessageMoveSwitch(Message msg, ConnectionToClient client)
    {
        //move - Move from msg
        Move move = msg.getMove();
        
        //PRINT playerlist using printPlayers, which will use printToDebug
        clueServerHelper.printPlayers(players);
        
        //CASE move OF
        switch (move)
        {
            //GAMESTARTED: CALL handleGameStarted
            case GAMESTARTED:
                handleGameStarted(msg);
                break;
            
            //PLAYERADDED: CALL handlePlayerAdded
            case PLAYERADDED:
                handlePlayerAdded(msg, client);
                break;
            //ENDTURN: CALL nextTurn
            case ENDTURN:
                //IF it is player's turn
                if (msg.getPlayer().getID() == turn)
                {
                    //CALL nextTurn
                    nextTurn();
                } //ENDIF
                break;
            //PLAYERMOVED: CALL handlePlayerMoved with msg
            case PLAYERMOVED:
                handlePlayerMoved(msg);
                break;
            //ACCUSATION: CALL handleAccusation with msg
            case ACCUSATION:
                handleAccusation(msg);
                break;
            //SUGGESTION: CALL handleSuggestion with msg
            case SUGGESTION:
                
                break;
            //ACTION: CALL handleMessageTypeSwitch with msg
            case ACTION:
                //CASE type OF
                handleMessageTypeSwitch(msg);
                break;
            //default: PRINT the message using printToDebug
            default:
                clueServerHelper.printToDebug("Server: Unexpected message: " + msg);
                break;
        }
    }

    /**
     * Handles the Type switch from handleMessageMoveSwitch. Should only be
     * called by handleMessageMoveSwitch
     *
     * @author Alex Saalberg
     * @param msg Message from client
     */
    private void handleMessageTypeSwitch(Message msg)
    {
        Type type = msg.getType();
        
        Card[] msgCards = msg.getCards();
        Card firstMsgCard = msgCards[0];
        
        //CASE type OF
        switch (type)
        {
            //ALLSNOOPLEFT: CALL handleAllSnoopLeft with msg
            case ALLSNOOPLEFT:
                clueServerDeck.discard(firstMsgCard);
                handleAllSnoopLeft(msg);
                break;
            //ALLSNOOPRIGHT: CALL handleAllSnoopRight with msg
            case ALLSNOOPRIGHT:
                clueServerDeck.discard(firstMsgCard);
                handleAllSnoopRight(msg);
                break;
            //SUPERSLEUTH: CALL handleAllSnoopRight with msg
            case SUPERSLEUTH:
                clueServerDeck.discard(firstMsgCard);
                handleSuperSleuth(msg);
                break;
            //PRIVATETIP: CALL handlePrivateTip with msg
            case PRIVATETIP:
                clueServerDeck.discard(firstMsgCard);
                handlePrivateTip(msg);
                break;
            //SNOOP: CALL handleSnoop with msg
            case SNOOP:
                clueServerDeck.discard(firstMsgCard);
                handleSnoop(msg);
                break;
            //SNOOP: CALL handleSnoop with msg
            case SUGGESTION:
                clueServerDeck.discard(msgCards[3]);
                handleSuggestion(msg);
                break;
            //default: PRINT the message using printToDebug
            default:
                clueServerHelper.printToDebug(
                        "Server: Unexpected ACTION message: " + msg);
                break;
        }
    }

    /**
     * Calls start
     * 
     * @param msg The message from handleMessageMoveSwitch 
     * @author Alex Saalberg
     */
    private void handleGameStarted(Message msg)
    {
        try
        {
            start(clueServerHelper.shuffle);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    /**
     * Handles a player added message.
     * 
     * @param msg The message from handleMessageMoveSwitch
     * @param client the client that sent the message
     * @author Alex Saalberg
     */
    private void handlePlayerAdded(Message msg, ConnectionToClient client)
    {
        //Fixed defect 407
        //IF lobbyStarted
        if (lobbyStarted)
        {
            //SET player to Player at ID index in players
            if (msg.getPlayer().getID() < players.size())
            {
                players.add(msg.getPlayer().getID(), msg.getPlayer());
            }
            else
            {
                players.add(msg.getPlayer());
            }
            
            connectionIndices[msg.getPlayer().getID()] = client;

            //SEND an update message with players
            Message updateMessage = new Message(msg.getPlayer(), null, players
                    .toArray(new Player[0]), Move.PLAYERADDED, null, null);
            sendToAllClients(updateMessage);
        }
        //ELSE
        else
        {
            lobbyStarted = true;
            
            connectionIndices[msg.getPlayer().getID()] = client;
            //SET player to Player at ID index in players
            players.add(msg.getPlayer());
            //Fixed defect 427
            Message lobbyMessage = new Message(msg.getPlayer(), null, null,
                    Move.LOBBYSTARTED,
                    Type.LOBBYEXISTS, null);

            Message hostLobbyMessage = new Message(msg.getPlayer(), null,
                    null, Move.LOBBYSTARTED,
                    null, null);
            try
            {
                //SEND hostLobbyMessage to client
                client.sendToClient(hostLobbyMessage);
                //FOR ConnectionToClient clnt in clients
                for (ConnectionToClient clnt : clients)
                {
                    //IF clnt is not null AND clnt is not equal to client
                    if (clnt != null && !clnt.equals(client))
                    {
                        //SEND lobbyMessage to clnt
                        clnt.sendToClient(lobbyMessage);
                    }
                }
            }
            catch (IOException ex)
            {
                System.out.println(ex);
                ex.printStackTrace();
            }
            //SEND an update message with players
            Message updateMessage = new Message(msg.getPlayer(), null, players
                    .toArray(new Player[0]), Move.PLAYERADDED, null, null);
            sendToAllClients(updateMessage);

        }
    }
    
    /**
     * Handles a player changing locations
     *
     * @msg Message from handleMessageMoveSwitch
     * @author Alex Saalberg
     */
    private void handlePlayerMoved(Message msg)
    {
        //messagePlayer - subject of the message
        Player messagePlayer = msg.getPlayer();

        LocationCard messageCard = (LocationCard) msg.getCards()[0];

        //IF location is not the current one
        if (!(messagePlayer.getLocation().equals(messageCard)))
        {
            //Fixes defect #459
            players = ClueServerHelper.movePlayer(players, messagePlayer,
                    messageCard);
        }

        sendToAllClients(new Message(messagePlayer, null,
                players.toArray(new Player[0]), Message.Move.PLAYERMOVED, null,
                new Card[]{messageCard}));
        sendResumeTurnMessage();

        return;
    }

    /**
     * Handles accusation messages from client.
     *
     * @msg Message from handleMessageMoveSwitch
     * @author Alex Saalberg
     */
    private void handleAccusation(Message msg)
    {
        //EXPECTED: From Client - Message(player making accusation, null, 
        //players, Move.ACCUSATION, null, cards)
        
        //solution - solution from ClueServerDeck.
        ArrayList<Card> solution = clueServerDeck.getSolution();
            
        //correct - whether the accusation is correct
        boolean correct = solution.get(0).equals(msg.getCards()[0])
                && solution.get(1).equals(msg.getCards()[1])
                && solution.get(2).equals(msg.getCards()[2]);

        //PRINT using printToConsole; "Accusation: [X] made an accusation of [Y]"
        clueServerHelper.printToConsole("Accusation: X made an accusation");

        numAccusations++;

        //IF msg.getCards() is null THEN
        if (msg.getCards() == null)
        {
            throw new IllegalArgumentException(
                    "Accusation message has no cards");
        }
        //IF the accusation is correct
        else if (correct)
        {
            handleCorrectAccusation(msg);
        }
        else //ELSE (if the accusation is incorrect)
        {
            //SET the accuser as "out" using player.setOut()
            players.get(msg.getPlayer().getID()).setOut();

            deepCopyPlayers();
            //SEND to accuser: Message(accuser, null, players, 
            //Move.YOURACCUSATION, ‚ÄúLost‚Äù, null)
            Message yourLoseMessage = new Message(msg.getPlayer(), null,
                    players.toArray(new Player[players.size()]),
                    Message.Move.YOURACCUSATION, Type.INCORRECTACCUSATION,
                    msg.getCards());

            //SEND to everyone but accuser: Message(player who made accusation, 
            //null, Move.ACCUSATION, ‚ÄúLost‚Äù, null)
            Message loseMessage = new Message(msg.getPlayer(), null,
                    players.toArray(new Player[players.size()]),
                    Message.Move.ACCUSATION, Type.INCORRECTACCUSATION,
                    msg.getCards());


            //SEND accuser yourLoseMessage
            sendToPlayer(msg.getPlayer(), yourLoseMessage);

            //SEND everyone else loseMessage
            sendToAllExceptPlayer(msg.getPlayer(), loseMessage);

            //IF numAccusations is equal to the size of players
            if (numAccusations == players.size())
            {
                gameOver();
                return;
            }
        } //ENDIF

        //CALL nextTurn
        nextTurn();
    }

    /**
     * Used to shorten handleAccusation because it was 52 lines long (50 is
     * max).
     *
     * @msg Message from handleMessageMoveSwitch
     * @author Alex Saalberg
     */
    private void handleCorrectAccusation(Message msg)
    {
        deepCopyPlayers();
        //SEND to the accuser: Message(player who made accusation, null,
        //players, Move.YOURACCUSATION, ‚ÄúWon‚Äù, null)
        Message yourWinMessage = new Message(msg.getPlayer(), null, players
                .toArray(new Player[0]), Move.YOURACCUSATION,
                Type.CORRECTACCUSATION, null);

        //SEND to everyone else: Message(player who made accusation, null, 
        //players, Move.ACCUSATION, ‚ÄúWon‚Äù, null)
        Message winMessage = new Message(msg.getPlayer(), null, players
                .toArray(new Player[0]), Move.ACCUSATION,
                Type.CORRECTACCUSATION, msg.getCards());

        //SEND accuser yourWinMessage
        sendToPlayer(msg.getPlayer(), yourWinMessage);

        //SEND everyone else winMessage
        sendToAllExceptPlayer(msg.getPlayer(), winMessage);

        //CLOSE the server
        try
        {
            close();
            return;
        }
        catch (IOException ex)
        {
            System.out.println(ex);
            ex.printStackTrace();
        }
    }

    /**
     * Handles all snoop left messages from client.
     *
     * @msg Message from handleMessageMoveSwitch
     * @author Alex Saalberg
     */
    private void handleAllSnoopLeft(Message msg)
    {
        //EXPECTED: From Client - new Message(players.get(thisPlayer), 
        //players.get((thisPlayer + 1) % players.size()), players, 
        //Message.Move.ACTION, "All Snoop Left", action);

        //SEND to everyone: Message(player who used card, null, players, 
        //Move.UPDATELOG, Type.ALLSNOOPRIGHT, null)
        deepCopyPlayers();
        Message updateMessage = new Message(msg.getPlayer(), null, players
                .toArray(new Player[0]), Move.UPDATELOG, Type.ALLSNOOPLEFT,
                msg.getCards());
        sendToAllClients(updateMessage);

        //PRINT using printToConsole; "[Action Card: X] used an All Snoop Left."
        clueServerHelper.printToConsole("Action Card: <PLAYER> used an all snoop");

        //FOR each player in players
        for (int idx = 0; idx < players.size(); idx++)
        {
            //player - the player at this index
            Player player = players.get(idx);
            //GET a random card from the next clockwise player
            //nextPlayer - next player in the list
            Player nextPlayer = players.get((idx + 1) % players.size());

            //IF player is not out of the game
            if (!player.getOut())
            {
                //IF the player is robot
                if (player instanceof RobotPlayer)
                {
                    //robot - the player, cast to a robot
                    RobotPlayer robot = (RobotPlayer) player;
                    //SHOW robot a random clue card from nextPlayer
                    robot.showCard(clueServerHelper
                            .getRandomClueCard(nextPlayer, random)[0]);
                }
                //ELSE (the player is a human)
                else
                {
                    //SEND to player: Message(card user, null, players, 
                    //Move.SHOWNCARDS, null, random card)
                    deepCopyPlayers();
                    Message showCardMessage = new Message(msg.getPlayer(),
                            nextPlayer, 
                            players.toArray(new Player[players.size()]),
                            Move.SHOWNCARDS, Type.ALLSNOOPLEFT,
                            getRandomClueCard(nextPlayer, random));
                    sendToPlayer(player, showCardMessage);
                } //ENDIF
            } //ENDIF
        } //ENDFOR

        //IF player a robot
        if (msg.getPlayer() instanceof RobotPlayer)
        {
            tellRobotToEndTurn((RobotPlayer) msg.getPlayer());
        } //ENDIF
        else 
        {
            //Fixes defect #457: sends a resume turn to players after a snoop
            sendToPlayer(msg.getPlayer(), new Message(msg.getPlayer(), null,
                    null, Message.Move.RESUMETURN, null, null));
        }
    }

    /**
     * Handles all snoop right messages from client.
     *
     * @msg Message from handleMessageMoveSwitch
     * @author Alex Saalberg
     */
    private void handleAllSnoopRight(Message msg)
    {
        //EXPECTED: Message(players.get(thisPlayer), 
        //players.get((thisPlayer - 1 + players.size()) % players.size()), 
        //players, Message.Move.ACTION, "All Snoop Right", action);

        //SEND to everyone: Message(player who used card, null, players, 
        //Move.UPDATELOG, Type.ALLSNOOPRIGHT, null)
        deepCopyPlayers();
        Message updateMessage = new Message(msg.getPlayer(), null, players
                .toArray(new Player[0]), Move.UPDATELOG, Type.ALLSNOOPRIGHT,
                msg.getCards());
        sendToAllClients(updateMessage);

        //PRINT using printToConsole; "Action Card: [X] used an All Snoop Right."
        clueServerHelper.printToConsole("Action Card: <PLAYER> used an all right snoop");

        //FOR each player in players
        for (int idx = 0; idx < players.size(); idx++)
        {
            //player - the player at this index
            Player player = players.get(idx);
            //GET a random card from the next clockwise player
            //nextPlayer - next player in the list
            Player nextPlayer = players.get((idx - 1 + players.size()) % players
                    .size());

            //IF player is not out of the game
            if (!player.getOut())
            {
                //IF the player is robot
                if (player instanceof RobotPlayer)
                {
                    //robot - the player, cast to a robot
                    RobotPlayer robot = (RobotPlayer) player;
                    //SHOW robot a random clue card from nextPlayer
                    robot.showCard(getRandomClueCard(nextPlayer, random)[0]);
                }
                //ELSE (the player is a human
                else
                {
                    //SEND to player: Message(card user, null, players, 
                    //Move.SHOWNCARDS, null, random card)
                    deepCopyPlayers();
                    Message showCardMessage = new Message(msg.getPlayer(),
                            nextPlayer, 
                            players.toArray(new Player[players.size()]),
                            Move.SHOWNCARDS, Type.ALLSNOOPRIGHT,
                            getRandomClueCard(nextPlayer, random));
                    sendToPlayer(player, showCardMessage);
                } //ENDIF
            } //ENDIF
        } //ENDFOR

        //IF player a robot
        if (msg.getPlayer() instanceof RobotPlayer)
        {
            //CALL tellRobotToEndTurn WITH the robot
            tellRobotToEndTurn((RobotPlayer) msg.getPlayer());
        } //ENDIF
        else 
        {
            //Fixes defect #457: sends a resume turn to players after a snoop
            sendToPlayer(msg.getPlayer(), new Message(msg.getPlayer(), null,
                    null, Message.Move.RESUMETURN, null, null));
        }
    }

    /**
     * Handles snoop message from client.
     *
     * @msg Message from handleMessageMoveSwitch
     * @author Alex Saalberg
     */
    private void handleSnoop(Message msg)
    {
        //EXPECTED: Message(players.get(thisPlayer), target, players, 
        //Message.Move.ACTION, "Snoop", null);

        //Fixes defect #456: Added the target to the updatelog message.
        //SEND to everyone: Message(player who used card, target, players, 
        //Move.UPDATELOG, Type.SNOOP, null)
        deepCopyPlayers();
        Message updateMessage = new Message(msg.getPlayer(), msg.getTarget(),
                players.toArray(new Player[0]), Move.UPDATELOG, Type.SNOOP,
                msg.getCards());
        sendToAllClients(updateMessage);

        //player - subject of the message
        Player player = msg.getPlayer();
        //target - target of the message
        Player target = msg.getTarget();
        playedCard = msg.getCards()[0];
        //IF the player is a RobotPlayer
        if (player instanceof RobotPlayer)
        {
            ((RobotPlayer) player)
                    .showCard(getRandomClueCard(target, random)[0]);
        }
        //ELSE (the player is a HumanPlayer)
        else
        {
            //SEND to player who used card: Message(null, msg.target(), players, 
            //Message.Move.SHOWNCARDS, null, random card from msg.target()
            deepCopyPlayers();
            Message showCardMessage = new Message(player, target, players
                    .toArray(new Player[players.size()]), Move.SHOWNCARDS,
                    Type.SNOOP, getRandomClueCard(target, random));
            sendToPlayer(player, showCardMessage);
        } //ENDIF


        //IF player a robot
        if (player instanceof RobotPlayer)
        {
            //CALL tellRobotToEndTurn WITH the robot
            tellRobotToEndTurn((RobotPlayer) player);
        } 
        //ELSE tell the player to resume their turn.
        else 
        {
            //Fixes defect #457: sends a resume turn to players after a snoop
            sendToPlayer(player, new Message(player, null, null,
                    Message.Move.RESUMETURN, null, null));
        }
    }

    /**
     * Handles Super Sleuth messages.
     *
     * @msg Message from handleMessageMoveSwitch
     * @author Alex Saalberg
     */
    private void handleSuperSleuth(Message msg)
    {
        //EXPECTED: From Client - Message(players.get(thisPlayer), null, 
        //players, Message.Move.ACTION, Messave.Type.SUPERSLEUTH, card)

        //SEND to everyone: Message(player who used card, null, players, 
        //Move.UPDATELOG, Type.SUPERSLEUTH, card)
        deepCopyPlayers();
        Message updateMessage = new Message(msg.getPlayer(), null, players
                .toArray(new Player[0]), Move.UPDATELOG, Type.SUPERSLEUTH,
                msg.getCards());
        sendToAllClients(updateMessage);

        //player - subject of this message
        Player messagePlayer = msg.getPlayer();
        //card - the super sleuth card
        Card card = msg.getCards()[0];
        //message - message used to notify players of super sleuth
        Message message;
        //cardList - array containing the super sleuth card
        Card[] cardList = new Card[1];
        //response - array containing a selected card from the other player
        Card[] response = new Card[1];
        boolean shown = false;
        
        //SET cardList[0] to card
        cardList[0] = card;
        //SET playedCard to card
        playedCard = card;

        //player - player at a given index
        Player player;

        //Fixes defect #447
        //FOR idx 0 to players.size()-1
        for (int idx = 0; idx < players.size(); idx++)
        {
            //SET player to players.get(idx)
            player = players.get(idx);
            //IF player.id is not messagePlayer.id 
                    //AND player can response to super sleuth
            if (player.getID() != messagePlayer.getID()
                    && ClueServerHelper.canRespondToSuperSleuth(player, card) >= 0)
            {
                mutex++;
                shown = true;
            }
        }
        handleSuperSleuthSendMessages(messagePlayer, card, cardList);
        //Fixes defect #448
        //IF mutex is 0
        if (mutex == 0 && !shown)
        {
            //Fixes defect #452
            sendToPlayer(messagePlayer, new Message(messagePlayer, null,
                            null, Move.SHOWNCARDS, null, null));
            //CALL doneWaitingOnResponses
            doneWaitingOnResponses();
        } //ENDIF
    }

    /**
     * Handles players responding to superSleuths.
     *
     * @msg Message from handleMessageMoveSwitch
     * @author Alex Saalberg
     */
    private void handleSuperSleuthSendMessages(Player messagePlayer, Card card,
            Card[] cardList)
    {
        Player player;
        Message message;
        Card[] response = new Card[1];
        
        //GO through all players
        for (int idx = 0; idx < players.size(); idx++)
        {
            player = players.get(idx);

            //IF not the current player and can respond
            if (player.getID() != messagePlayer.getID()
                    && ClueServerHelper.canRespondToSuperSleuth(player, card) >= 0)
            {

                message = new Message(player, messagePlayer, null,
                        Message.Move.ACTION, null,
                        cardList);
                //IF player is a robot
                if (player instanceof RobotPlayer)
                {
                    response[0] = player.getClueCards()[clueServerHelper
                            .canRespondToSuperSleuth(player, card)];
                    message = new Message(player, messagePlayer,
                            null, Message.Move.ACTION,
                            null, response);
                    handleMessageFromClient(message, null);
                }
                //ELSE (player is ahuman)
                else
                {
                    sendToPlayer(player, message);
                    waitingOn.add(player);
                } //ENDIF
            } //ENDFOR
        } //ENDFOR
    }
    
    private void handleSuperSleuthResponse(Message msg)
    {
        //Expected: Message(player who responded to super sleuth, 
        //player who played supersleuth, Move.SHOWNCARDS, null, card chosen)

        //player - subject of the message
        Player player = msg.getPlayer();
        //target - player whose turn it is
        Player target = players.get(turn);

        //PRINT using printToConsole; "[Player] responded [Player]'s 
        //Super Sleuth with [Card]"
        clueServerHelper.printToConsole(
                "Response: " + target.getName() + " reponsed to " + player
                .getName() + " with " + msg.getCards()[0]);

        //shownCardList - list of cards containing the shown card
        ArrayList<Card> shownCardList = new ArrayList<Card>();

        //the shown card from the msg
        shownCardList.add(msg.getCards()[0]);

        //IF the target is robot
        if (players.get(turn) instanceof RobotPlayer)
        {
            //SHOW the robot their card
            ((RobotPlayer) players.get(turn)).showCard(msg.getCards()[0]);
        }
        //ELSE (the target is human)
        else
        {
            //SEND a SHOWNCARDS message to that player with the card.
            sendToPlayer(players.get(turn), new Message(target, player,
                    null, Move.SHOWNCARDS, null,
                    shownCardList.toArray(new Card[1])));
        } //ENDIF

        //DECREMENT mutex
        mutex--;

        //Fixes defect #454: Don't use for each loops when modifying things.
        //FOR each Player player in waitingOn
        for (int idx = waitingOn.size() - 1; idx >= 0; idx--)
        {
            //IF this message is from that player, REMOVE that player
            if (msg.getPlayer().getID() == waitingOn.get(idx).getID())
            {
                //REMOVE player from waitingOn
                waitingOn.remove(waitingOn.get(idx));
            } //ENDIF
        } //ENDFOR

        //IF mutex is 0
        if (mutex == 0)
        {
            //CALL doneWaitingOnResponses
            doneWaitingOnResponses();
        } //ENDIF
    }
    
    /**
     * Handles players playing a private tip
     *
     * @param msg the Message used to initiate this handler
     * @author Alex Saalberg
     */
    private void handlePrivateTip(Message msg)
    {
        //EXPECTED: Message(players.get(thisPlayer), target, players, 
        //Message.Move.ACTION, Message.Type.PRIVATETIP, action card, null);


        //PRINT using printToConsole; "Action Card: [X] used a Private Tip."
        clueServerHelper.printToConsole("Action Card: private tip");

        Card[] msgCards = msg.getCards();
        Card foundCard = null;
        ArrayList<Card> foundCards = new ArrayList<Card>();
        ActionCard privateTipCard = (ActionCard)msgCards[0];
        
        Player player = msg.getPlayer();
        Player target = msg.getTarget();
        
        //SEND to everyone: Message(player who used card, target, players,
        //Move.UPDATELOG, null, null, "<X> targeted <Y> with a private tip")
        Message privateTipMessage = new Message(player, target, null,
                Move.UPDATELOG, Type.PRIVATETIP, msg.getCards());
        sendToAllClients(privateTipMessage);


        //FOR all cards in target's clue cards
        for(Card card : target.getClueCards())
        {
            
            //IF PrivateTipCard.matchesCard(card, private tip card)
            if(privateTipCard.matchesCard(card, null))
            {
                //ADD the card to the list
                foundCard = card;
                foundCards.add(card);
            }
        }
        
        //Fixes defect #467
        if (privateTipCard.getType() == ActionCard.Type.PTDESTINATION ||
                privateTipCard.getType() == ActionCard.Type.PTSUSPECT ||
                privateTipCard.getType() == ActionCard.Type.PTVEHICLE)
        {
            //IF the player is a robot
            if (player instanceof RobotPlayer)
            {
                //CAST the player to a robot
                RobotPlayer robotPlayer = (RobotPlayer) player;

                //SHOW the robot the card
                robotPlayer.showCard(foundCard);
            }
            else
            {
                sendToPlayer(player, new Message(player, target, null, 
                        Message.Move.SHOWNCARDS, null,
                        foundCards.toArray(new Card[0])));
            }
        }
        else 
        {
            //IF the player is a robot
            if (player instanceof RobotPlayer)
            {
                //CAST the player to a robot
                RobotPlayer robotPlayer = (RobotPlayer) player;

                //SHOW the robot the card
                robotPlayer.showCard(foundCard);
            }
            else
            {
                sendToPlayer(player, new Message(player, target, null, 
                        Message.Move.SHOWNCARDS, null, new Card[] {foundCard}));
            }
        }
        
        //Send end turn messages
        doneWaitingOnResponses();
    }

    
    /**
     * Handles players making suggestions.
     *
     * @author Alex Saalberg
     * @param msg Message from client
     */
    private void handleSuggestion(Message msg)
    {
        //EXPECTED: Message(players.get(thisPlayer), target if card is swapped 
        //or null otherwise, players, Message.Move.ACTION, 
        //Message.Type.SUGGESTION, suggestion cards)  
        
        //SEND to everyone: Message(player who used card, null, players, 
        //Move.UPDATELOG, Type.SUGGESTION, null)
        //SET player equal to msg.getPlayer();
        Player player = players.get(msg.getPlayer().getID());
        
        //IF player is a Robotplayer
        if (player instanceof RobotPlayer)
        {
            //MOVE player to msg.getCards()[0]
            players = clueServerHelper.movePlayer(players, player,
                    (LocationCard) msg.getCards()[0]);
        }

        deepCopyPlayers();
        Message updateMessage = new Message(msg.getPlayer(), null, 
                players.toArray(new Player[0]), Move.UPDATELOG, 
                Type.SUGGESTION, msg.getCards());
        sendToAllClients(updateMessage);

        handleSuggestionBody(msg, player);
        
    }
    
    private void handleSuggestionBody(Message msg, Player player)
    {
        //SET nextPlayer equal to players.get((player.getID()+1)%players.size())
        Player nextPlayer = players.get((player.getID() + 1) % players.size());
        //SET suggestion equal to msg.getCards()
        this.suggestion = msg.getCards();
        playedCard = msg.getCards()[3];
        //disproved - whether the suggestion has been disproved
        boolean disproved = false;

        //WHILE nextPlayer is not equal to player
        while (nextPlayer.getID() != player.getID() && !disproved)
        {
            //IF the nextplayer can disprove the suggestion
            if (ClueServerHelper.canDisproveSuggestion(nextPlayer, suggestion))
            {
                disproved = true;
                //IF nextPlayer is a RobotPlayer
                if (nextPlayer instanceof RobotPlayer)
                {
                    //SelectAction for the robotPlayer
                    Message newMsg;
                    //crd - card the player can use to disprove, if any
                    Card crd = null;

                    crd = findCardThatDisprovesSuggestion(nextPlayer
                            .getClueCards(), suggestion);
                    //shown - the card used to disprove the suggestion
                    Card[] shown = new Card[1];
                    shown[0] = crd;
                    deepCopyPlayers();
                    newMsg = new Message(nextPlayer, player, players.toArray(
                            new Player[players.size()]),
                            Message.Move.SHOWNCARDS, null, shown);
                    handleSuggestionResponse(newMsg);
                }
                //ELSE (Player is a human)
                else
                {
                    //ADD the player to the waitingOn list
                    waitingOn.add(nextPlayer);
                    //SEND nextplayer: Message {client, target, players,
                    //DISPROVESUGGESTION, "", msg.getCards()}
                    deepCopyPlayers();
                    sendToPlayer(nextPlayer, new Message(player, nextPlayer,
                            players.toArray(new Player[players.size()]),
                            Move.DISPROVESUGGESTION, null, suggestion));
                } //ENDIF
            } //ENDIF
            //Increment nextPlayer
            nextPlayer = players.get((nextPlayer.getID() + 1) % players.size());
        } //ENDWHILE

        //IF not disproved and player is a robot
        if (!disproved)
        {
            //Fixes defect #452
            sendToPlayer(player, new Message(player, null,
                            null, Move.SHOWNCARDS, null, null));
            //CALL doneWaitingOnResponses
            doneWaitingOnResponses();
        }
    }

    /**
     * Handles players responding to suggestions.
     *
     * @author Alex Saalberg
     */
    private void handleSuggestionResponse(Message msg)
    {
        //Expected: Message(players.get(thisPlayer), player making suggestion, 
        //players, Message.Move.SHOWNCARDS, null, clueCard

        //player - subject of the message
        Player player = msg.getPlayer();
        //suggester - the player making the suggestion
        Player suggester = players.get(turn);

        //PRINT using printToConsole; "[Player] responded to [Player]'s 
        //Suggestion of [CARDS] with [Card]"
        /*clueServerHelper.printToConsole(
                "Response: " + player.getName() + " responded to " + suggester
                .getName() + "'s suggestion with " + msg.getCards()[0]);*/

        //REMOVE player from waitingOn
        //Fixes defect #448
        waitingOn.clear();
        
        //IF suggester is a human
        if (!(suggester instanceof RobotPlayer))
        {
            //SEND to suggester: Message(null, player showing the card, players, 
            //Move.SHOWNCARDS, null, shown card);
            sendToPlayer(suggester, new Message(suggester, player, null,
                    Move.SHOWNCARDS, null, msg.getCards()));
        }
        else
        {
            //SHOW the card to the robot
            ((RobotPlayer) suggester).showCard(msg.getCards()[0]);
        }

        //CALL doneWaitingOnResponses
        doneWaitingOnResponses();
    }
}
