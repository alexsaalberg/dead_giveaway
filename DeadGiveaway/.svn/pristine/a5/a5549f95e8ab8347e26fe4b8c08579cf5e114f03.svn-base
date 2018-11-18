package deadgiveaway.client;

import deadgiveaway.*;
import deadgiveaway.server.*;
import static deadgiveaway.LocationCard.Title;
import static deadgiveaway.SuspectCard.Name;
import static deadgiveaway.VehicleCard.Model;
import static deadgiveaway.Message.Move;
import org.jdesktop.layout.LayoutStyle;
import org.jdesktop.layout.GroupLayout;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.util.concurrent.Semaphore;
import java.util.Observable;
/**
 * This class contains definitions for a GUI used by a human player.
 * @author Christopher Siu
 * @author Steven Gerick
 * @author Brad Johnson
 * @version 1.0
 */
public class GUI extends javax.swing.JFrame implements KeyEventPostProcessor,
        UserInterface
{
    /** Non-shared portion of the filepath of the action cards */
    public static final String[] kActionCardNames = {
        "SuggestionAny", "SuggestionCurrent", "Snoop", "AllSnoopLeft",
        "AllSnoopRight", "SuperSleuthFemale", "SuperSleuthMale",
        "SuperSleuthAir", "SuperSleuthBlue", "SuperSleuthSouth",
        "SuperSleuthWest", "PrivateTipSuspect", "PrivateTipVehicle",
        "PrivateTipDestination", "PrivateTipFemale", "PrivateTipRed",
        "PrivateTipNorth"
    };
    /** Names of actions */
    public static final String[] kActionNames = {
        "Suggestion-Any", "Suggestion-Current", "Snoop", "All Snoop Left",
        "All Snoop Right", "Super Sleuth Female", "Super Sleuth Male",
        "Super Sleuth Flying", "Super Sleuth Blue", "Super Sleuth South",
        "Super Sleuth West", "Private Tip Suspect", "Private Tip Vehicle",
        "Private Tip Destination", "Private Tip Female", "Private Tip Red",
        "Private Tip North"
    };

    /** Non-shared portion of the filepath of the location cards*/
    public static final String[][] kLocationFiles =
    {
        {
            "AthenasForest", "Athens", "AtlasSky", "DionysusVineyard",
            "HadesUnderworld", "HephaestusVolcano", "MountOlympus",
            "PhoebesMoon", "PoseidonsOcean"
        },
        {
            "Gibraltar", "Havana", "Tortuga", "Santiago", "Guadeloupe",
            "PortRoyale", "Veracruz", "Barbados", "SanJuan"

        },
        {
            "Shenandoah", "Smoky_Mtns", "Olympic", "Yellowstone", "Yosemite",
            "Grand_Cyn", "Joshua_Tree", "Dry_Tortugas", "Everglades"
        }
    };

    /** Non-shared portion of the filepath of the suspect cards*/
    public static final String[][] kSuspectFiles =
    {
        {
            "Aphrodite", "Ares", "Artemis", "Dionysus", "Hera", "Hermes"
        },
        {
            "AnneBonny", "BlackBeard", "Granuaile", "CalicoJack",
            "SadieTheGoat", "WilliamKidd"
        },
        {
            "Condi", "Cheney", "Coulter", "Dubya", "Laura", "Karl"
        }
    };
    /** Non-shared portion of the filepath of the vehicle cards*/
    public static final String[][] kVehicleFiles =
    {
        {
            "ApollosChariot", "AthenaHorse", "ErosWings",
            "HadesDogs", "HermesWingSandals", "PoseidonShip"
        },
        {
            "Galleon", "Sloop", "Tartrane", "Barque", "Brig", "Merchantman"
        },
        {
            "AirForceOne", "Motorcade", "Blimp", "Humvee", "Chopper",
            "Motorcycle"
        }
    };
    /** an array of numbers 0-5 as strings */
    public static final String[] kNumberNames = {
        "zero", "one", "two", "three", "four", "five"
    };
    private static final Color kActiveBorderColor = new Color(0x804040FF, true);
    /** an offset one spot to the left */
    private static final int kLeft1 = 1;
    /** an offset two spots to the left */
    private static final int kLeft2 = 2;
    /** an offset three spots to the left */
    private static final int kLeft3 = 3;
    /** an offset four spots to the left */
    private static final int kLeft4 = 4;
    /** number of supported action cards */
    private static final int kSupportedActionCards = 17;
    /** Number of unique location cards */
    private static final int kNumLocations = 9;
    /** Number of unique suspect cards*/
    private static final int kNumSuspects = 6;
    /** Number of unique vehicle cards*/
    private static final int kNumVehicles = 6;
    /** constant used to represent the greek theme */
    private static final int kGreekTheme = 0;
    /** constant used to represent the pirate theme */
    private static final int kPirateTheme = 1;
    /** constant used to represent the White House theme */
    private static final int kWhiteHouseTheme = 2;
    private static final javax.swing.border.MatteBorder kDefaultBorder = 
            BorderFactory.createMatteBorder(2, 2, 2, 2, kActiveBorderColor);
    /** HashMap used to map a card name to the filepath of its image */
    private static final HashMap<String, String[]> kCardImages;
    /** HashMap used to hold padded versions of the card images */
    private static final HashMap<String, String[]> kPaddedImages;
    /** Hashmap used to map keyCodes to buttons */
    private HashMap<Integer, JButton> kButtonKeys;
    /** an array of suspect names, grouped by theme */
    private static final String[][] kSuspectNames =
    {
        kSuspectFiles[0],
        {
            "Anne Bonny", "Black Beard", "Granuaile", "Calico Jack",
            "Sadie The Goat", "William Kidd"
        },
        kSuspectFiles[2]
    };
    /** an array of location names, grouped by theme */
    private static final String[][] kLocationNames =
    {
        {
            "Athena's Forest", "Athens", "Atlas's Sky",
            "Dionysius's Vineyard", "Hades's Underworld",
            "Hephaestus's Volcano", "Mount Olympus", "Phoebe's Moon",
            "Poseidon's Ocean"
        },
        {
            "Gibraltar", "Havana", "Tortuga", "Santiago", "Guadeloupe",
            "Port Royale", "Veracruz", "Barbados", "San Juan"
        },
        {
            "Shenandoah", "Smoky Mountains", "Olympic", "Yellowstone",
            "Yosemite", "Grand Canyon", "Joshua Tree", "Dry Tortugas",
            "Everglades"
        }
    };
    /** an array of vehicle names, grouped by theme */
    private static final String[][] kVehicleNames =
    {
        {
            "Apollo's Chariot", "Athena's Horse", "Eros's Wings",
            "Hades's Dogs", "Hermes's Winged Sandals", "Poseidon's Ship"
        },
        {
            "Galleon", "Sloop", "Tartrane", "Barque", "Brig", "Merchantman"
        },
        {
            "Air Force One", "Motorcade", "Blimp", "Humvee", "Chopper",
            "Motorcycle"
        }
    };
    /** array of references to buttons for easy loop access */
    private JButton[] clueCardButtons;
    /** array of references to buttons for easy loop access */
    private JButton[] actionCardButtons;
    /** array of labels used to display player names in notesheed*/
    private JLabel[] playerNameLabels;
    /** double array of checkboxes for easy loop access */
    private JCheckBox[][] suspectCheckBoxes;
    /** double array of checkboxes for easy loop access */
    private JCheckBox[][] locationCheckBoxes;
    /** double array of checkboxes for easy loop access */
    private JCheckBox[][] vehicleCheckBoxes;
    /** array of locations for easy loop access, user first */
    private JLabel[] locationMarkers;
    /** array of player names for easy loop access */
    private JLabel[] otherNames;
    /** Array of labels for suspects*/
    private JLabel[] noteSheetSuspects;
    /** Array of labels for vehicles*/
    private JLabel[] noteSheetVehicles;
    /** Array of labels for locations*/
    private JLabel[] noteSheetLocations;

    /** int used to represent the current theme */
    private int curTheme;

    /** int used to represent current lobby status */
    private int lobbyMode;
    /** RobotPlayer used to make moves for the human on timeout */
    private RobotPlayer robotUser;
    /** the list of players */
    private ArrayList<Player> players;
    /** index of this GUI's user in the arraylist of players */
    private Player thisPlayer;
    /** a String holding a record of actions */
    private String logText;
    /** a ClueClient holding the connection to the server */
    private final ClueClient client;
    /** a boolean indicating whether or not the game has started */
    private boolean isStarted;
    /** the next card that this user can draw, as passed by the server */
    private Card nextCard;
    /** a message containing the action the client is responding to */
    private Message curMessage;
    /** a reference to the keyboard focus manager */
    private final KeyboardFocusManager keyManager;
    /** the string containing the game rules accessed in game by users */
    private static String kRules = "";
    /** a mutex lock to prevent handling multiple KeyEvents */
    private final Semaphore keyMutex;
    /** a boolean indicating whether or not the game has started */
    private volatile boolean gameStarted;
    /** an action to send to the server */
    private ActionCard selectedAction;
    /** whether or not this player is in the game */
    private boolean inGame;
    /** whether this player is the host */
    private boolean isHost;
    /** int that tracks number of invalid inputs, for testing purposes */
    private int invalidNumber;

    static {
        kCardImages = new HashMap<String, String[]>();
        kPaddedImages = new HashMap<String, String[]>();
                //FOR every action card supported by the game
        for (int index = 0; index < kSupportedActionCards; index++)
        {
            kCardImages.put(kActionCardNames[index], new String[]
            {
                "/deadgiveaway/resources/Action-" + kActionCardNames[index]
                        + ".jpg"
            });
            kPaddedImages.put(kActionCardNames[index], new String[]
            {
                "/deadgiveaway/resources/rAction-" + kActionCardNames[index]
                        + ".gif"
            });
        }
        //FOR every location card supported by the game
        for (int index = 0; index < kNumLocations ; index++)
        {
            kCardImages.put("locationName" +  index, new String[]
            {
                "/deadgiveaway/resources/Location-" +
                        kLocationFiles[0][index] + ".gif",
                "/deadgiveaway/resources/Location-" +
                        kLocationFiles[1][index] + ".gif",
                "/deadgiveaway/resources/Location-" +
                        kLocationFiles[2][index] + ".gif"
            });
            kPaddedImages.put("locationName" + index, new String[]
            {
                "/deadgiveaway/resources/rLocation-" +
                        kLocationFiles[0][index] + ".gif",
                "/deadgiveaway/resources/rLocation-" +
                        kLocationFiles[1][index] + ".gif",
                "/deadgiveaway/resources/rLocation-" +
                        kLocationFiles[2][index] + ".gif"
            });
        }
        //FOR every suspect card supported by the game
        for (int index = 0; index < kNumSuspects ; index++)
        {
            kCardImages.put("suspectName" +  index, new String[]
            {
                "/deadgiveaway/resources/Suspects-" +
                        kSuspectFiles[0][index] + ".gif",
                "/deadgiveaway/resources/Suspects-" +
                        kSuspectFiles[1][index] + ".gif",
                "/deadgiveaway/resources/Suspects-" +
                        kSuspectFiles[2][index] + ".gif"
            });
            kPaddedImages.put("suspectName" + index, new String[]
            {
                "/deadgiveaway/resources/rSuspects-" +
                        kSuspectFiles[0][index] + ".gif",
                "/deadgiveaway/resources/rSuspects-" +
                        kSuspectFiles[1][index] + ".gif",
                "/deadgiveaway/resources/rSuspects-" +
                        kSuspectFiles[2][index] + ".gif"
            });
        }
        //FOR every vehicle card supported by the game
        for (int index = 0; index < kNumVehicles ; index++)
        {
            kCardImages.put("vehicleModel" +  index, new String[]
            {
                "/deadgiveaway/resources/Transportation-"
                        + kVehicleFiles[0][index] + ".gif",
                "/deadgiveaway/resources/Transportation-"
                        + kVehicleFiles[1][index] + ".gif",
                "/deadgiveaway/resources/Transportation-"
                        + kVehicleFiles[2][index] + ".gif"
            });
            kPaddedImages.put("vehicleModel" + index, new String[]
            {
                "/deadgiveaway/resources/rTransportation-"
                        + kVehicleFiles[0][index] + ".gif",
                "/deadgiveaway/resources/rTransportation-"
                        + kVehicleFiles[1][index] + ".gif",
                "/deadgiveaway/resources/rTransportation-"
                        + kVehicleFiles[2][index] + ".gif"
            });
        }
    }
   /**
    * Constructs a GUI
    * @param client The ClueClient that is communicating with the server.
    */
    public GUI(ClueClient client)
    {
        logText = "";
        invalidNumber = 0;
        players = new ArrayList<Player>(5);
        thisPlayer = new Player("", -1, null);
        lobbyMode = 0;
        inGame = true;
        //DISPLAY the lobby until a game is initialized.
        gameStarted = false;
        this.client = client;
        //SET the keyboard focus manager and dispatcher
        keyMutex = new Semaphore(1);
        keyManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        keyManager.addKeyEventPostProcessor(this);
        curTheme = kGreekTheme;
        isHost = false;
        initCreateComponents();
        this.setVisible(true);
    }
    /**
     * Helper method to get icons from resource packages
     * @param path the path of the icon in the source folder
     * @return a new icon containing the image at that path
     */
    private ImageIcon getIcon(String path)
    {
        return new ImageIcon(getClass().getResource(path));
    }
    /**
     * Private helper method - redraws card stack images
     */
    private void redrawCardStacks()
    {
        //DRAW the player hands:
        int cardNum = players.get((thisPlayer.getID() + kLeft1) % players.size()
        ).getNumCards();
        //left player
        if ((cardNum - kLeft3) % 2 == 1)
        {
            handPlus1.setIcon(getIcon("/deadgiveaway/resources/"
                            + kNumberNames[cardNum]+ "CardsLeft.gif"));
        }
        cardNum = players.get((thisPlayer.getID() + kLeft2) % players.size()
        ).getNumCards();
        //top player
        if ((cardNum - kLeft3) % 2 == 1)
        {
            handPlus2.setIcon(getIcon("/deadgiveaway/resources/"
                            + kNumberNames[cardNum] + "CardsTop.gif"));
        }
        cardNum = players.get((thisPlayer.getID() + kLeft3) % players.size()
        ).getNumCards();
        // bottom player
        if ((cardNum - kLeft3) % 2 == 1)
        {
            handPlus3.setIcon(getIcon("/deadgiveaway/resources/"
                            + kNumberNames[cardNum] + "CardsTop.gif"));
        }
        cardNum = players.get((thisPlayer.getID() + kLeft4) % players.size()
        ).getNumCards();
        // bottom player
        if ((cardNum - kLeft3) % 2 == 1)
        {
            handPlus4.setIcon(getIcon("/deadgiveaway/resources/"
                            + kNumberNames[cardNum] + "CardsRight.gif"));
        }
    }

    /**
     * Private helper method - redraws the action cards
     */
    private void redrawActionCards()
    {
        Card[] cards; // An array of the cards currently being drawn
        int cardIdx;  // The index of the card being drawn

        cards = players.get(thisPlayer.getID()).getActionCards();
        //FOR every action card:
        for (cardIdx = 0; cardIdx < players.get(thisPlayer.getID())
                .getNumActions();
             cardIdx++)
        {
            int index = ((ActionCard)cards[cardIdx]).getType().ordinal();
            //use the index to update the icon
            actionCardButtons[cardIdx].setIcon(getIcon(
                    kPaddedImages.get(kActionCardNames[index])[0]));
            actionCardButtons[cardIdx].setRolloverIcon(getIcon(
                    kCardImages.get(kActionCardNames[index])[0]));
            actionCardButtons[cardIdx].setDisabledIcon(getIcon(
                    kPaddedImages.get(kActionCardNames[index])[0]));
            actionCardButtons[cardIdx].setBorderPainted(
                    actionCardButtons[cardIdx].isEnabled());
            actionCardButtons[cardIdx].repaint();
        }

        //FOR every remaining action card button:
        while (cardIdx < actionCardButtons.length)
        {
            //MAKE the button invisible.
            actionCardButtons[cardIdx].setIcon(null);
            actionCardButtons[cardIdx].setRolloverIcon(null);
            actionCardButtons[cardIdx].repaint();
            //DISABLE the button.
            actionCardButtons[cardIdx].setEnabled(false);
            actionCardButtons[cardIdx].setBorderPainted(false);
            cardIdx++;
        }
        //END LOOP        
    }
    
    /**
     * Private helper method : redraws locations
     */
    private void redrawLocations()
    {
        int id = thisPlayer.getID();
        //FOR every player in the game, starting with this user:
        for (int idx = 0; idx < players.size(); idx++)
        {
            int index = players.get((idx + id) % players.size())
                .getLocation().getTitle().ordinal();
            //use the index to update that player's location marker.
            locationMarkers[idx].setIcon(getIcon(
                kCardImages.get("locationName" + index)[curTheme]));
            locationMarkers[idx].repaint();
            //END IF
        }
        int nameIdx = 0;
        //set all names of the other players
        for (int idx = id + 1; nameIdx < 4; idx++)
        {
            idx = idx % players.size();
            //SHOW the player's name.
            otherNames[nameIdx++].setText(players.get(idx).getName());
        }
    }
    /**
     * Redraws all the cards and locations.
     */
    private void reDrawCards()
    {
        redrawActionCards();
        int cardIdx;  // The index of the card being drawn
        // An array of the cards currently being drawn
        Card[] cards = players.get(thisPlayer.getID()).getClueCards();
        
        //GO through every clue card:
        for (cardIdx = 0; cardIdx < players.get(thisPlayer.getID())
                .getNumCards(); cardIdx++)
        {
            Card curCard = cards[cardIdx];
            String fileName;
            //IF the card is a location card:
            if (curCard instanceof LocationCard)
            {
                //use the index to update the icon
                int index = ((LocationCard)curCard).getTitle().ordinal();
                fileName = "locationName" + index;                
            }
            // ELSE IF the card is a Suspect
            else if (curCard instanceof SuspectCard)
            {
                //use the index to update the icon
                int index = ((SuspectCard)curCard).getName().ordinal();
                fileName = "suspectName" + index;
            }
            //ELSE IF card is a vehicle
            else
            {
                //use the index to update the icon
                int index = ((VehicleCard)curCard).getModel().ordinal();
                fileName = "vehicleModel" + index;
            }
            clueCardButtons[cardIdx].setIcon(getIcon(
                kPaddedImages.get(fileName)[curTheme]));
            clueCardButtons[cardIdx].setRolloverIcon(getIcon(
                kCardImages.get(fileName)[curTheme]));
            clueCardButtons[cardIdx].setDisabledIcon(getIcon(
                kPaddedImages.get(fileName)[curTheme]));
            clueCardButtons[cardIdx].setBorderPainted(clueCardButtons[cardIdx]
                .isEnabled());
            clueCardButtons[cardIdx].repaint();
            //CHECK off the card in the notesheet.
            checkNotesheet(curCard, thisPlayer.getID());
        }
        //END LOOP
        //FOR every remaining clue card button:
        while (cardIdx < clueCardButtons.length)
        {
            //MAKE the button invisible.
            clueCardButtons[cardIdx].setIcon(null);
            clueCardButtons[cardIdx].setRolloverIcon(null);
            clueCardButtons[cardIdx].repaint();
            //DISABLE the button.
            clueCardButtons[cardIdx].setEnabled(false);
            clueCardButtons[cardIdx].setBorderPainted(false);
            cardIdx++;
        }
        //END LOOP
        buttonDrawCard.setBorderPainted(buttonDrawCard.isEnabled());
        redrawLocations();
        redrawCardStacks();
    }

    /**
     * 
     * Checks off a card in the notesheet.
     * @param card The card to check off
     * @param playerIdx The index of the player who had this card
     */
    private void checkNotesheet(Card card, int playerIdx)
    {
        //CHECK OFF the card using the checkbox arrays
        if (card instanceof LocationCard)
        {
            //use the index to set the card as seen in the check boxes
            int index = ((LocationCard)card).getTitle().ordinal();
            locationCheckBoxes[index][playerIdx].setSelected(true);
        }
        // ELSE IF suspect card
        else if (card instanceof SuspectCard)
        {
            //use the index to set the card as seen in the check boxes
            int index = ((SuspectCard)card).getName().ordinal();
            suspectCheckBoxes[index][playerIdx].setSelected(true);
        }
        //ELSE IF vehicle card
        else if (card instanceof VehicleCard)
        {
            //use the index to set the card as seen in the check boxes
            int index = ((VehicleCard)card).getModel().ordinal();
            vehicleCheckBoxes[index][playerIdx].setSelected(true);
        }
    }

    /**
     * Returns the name of a Clue card for the current theme.
     * @param card The card to get the name of
     * @return The name of the card, null if the card type is unknown.
     */
    private String getCardName(Card card)
    {
        String cardName = null; // The name to return

        //RETRIEVE the name from the array of themed names
        if (card instanceof SuspectCard)
        {
            cardName = kSuspectNames[curTheme]
                    [((SuspectCard)card).getName().ordinal()];
        }
        //ELSE IF card is location card
        else if (card instanceof LocationCard)
        {
            cardName = kLocationNames[curTheme]
                    [((LocationCard)card).getTitle().ordinal()];
        }
        // ELSE IF card is vehicle card
        else if (card instanceof VehicleCard)
        {
            cardName = kVehicleNames[curTheme]
                    [((VehicleCard)card).getModel().ordinal()];
        }

        return cardName;
    }

    /**
     * Updates the player list to track changes to players.
     * @param playerList an updated list of players
     */
    public void updatePlayers(ArrayList<Player> playerList)
    {
        players.clear();
        int idx = 0;
        // ADD all the players
        for (Player player : playerList)
        {
            players.add(player);
            //if the other player's id is this player's id, update this player
            if (player.getID() == thisPlayer.getID() ||
                    player.getName().equals(thisPlayer.getName()))
            {
                thisPlayer = player;
            }
            //IF the game is started, set the player labels
            if (gameStarted)
            {
                playerNameLabels[idx].setText(
                    "Player " + (idx + 1) + ": " + player.getName());                
            }
            idx++;
        }
        //if we are in the lobby, update the player list
        if (lobbyMode == 1)
        {
            String names = "";
            //FOR every player in the message
            for (Player player : playerList)
            {
                //UPDATE the name list
                names += player.getName() + "\n";
            }
            lobbyTextArea1.setText(names);
        }
        //IF the game is already in progress, redraw the cards
        if (gameStarted)
        {
            //REDRAW the players and their cards
            reDrawCards();
        }
    }

    /**
     * Enables the appropriate clue cards for responding to Super Sleuths.
     * @param action The card that was played
     */
    private void handleAction(Player player, ActionCard action)
    {
        int idx = 0; // The index of the current card
        String cardType = "Super Sleuth";
        //if the card is a private type, set the string to "Private Tip"
        if (action.getType().ordinal() >= ActionCard.Type.PTSUSPECT.ordinal())
        {
            cardType = "Private Tip";
        }
        //SET the turn indicator
        labelTurnIndicator.setText("Select a card to show.");
        //ALERT the user
        showPopup("You must respond to " + player.getName() +
            "'s " + cardType + ".");
        //ENABLE the appropriate clue card buttons
        reDrawCards();

        //FOR each clue card:
        for (Card crd : players.get(thisPlayer.getID()).getClueCards())
        {
            //DETERMINE whether or not it's a valid response
            if (action.matchesCard(crd, null))
            {
                clueCardButtons[idx].setEnabled(true);
                clueCardButtons[idx].setBorderPainted(true);
            }
            idx++;
        }//END LOOP
    }

    /**
     * Shows a message in a dialog box.
     * @param msg The message to show.
     */
    private void showPopup(String msg)
    {
        JOptionPane.showMessageDialog(null, msg, null,
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Adds a player to the ArrayList of players
     * @param player The player to be added
     */
    public void addPlayer(Player player)
    {
        players.add(player);
    }

    /**
     * Writes a message to the GUI's action log
     * @param msg The message to be written
     */
    private void writeToLog(String msg)
    {
        //System.out.println("LOG IS BEING WRITTEN TO WITH ------- " + msg);
        logText += "\n\n> " + msg;
        actionLog.setText(logText);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents()
    {
        logPane = new javax.swing.JScrollPane();
        actionLog = new javax.swing.JTextArea();
        bottomBar = new javax.swing.JPanel();
        buttonEndTurn = new javax.swing.JButton();
        buttonAccuse = new javax.swing.JButton();
        buttonRules = new javax.swing.JButton();
        labelTurnTimer = new javax.swing.JLabel();
        labelTurnTimer.setName("Turn Timer");
        labelTurnIndicator = new javax.swing.JLabel();
        buttonAction2 = new javax.swing.JButton();
        buttonAction2.setName("Action Card 2");
        buttonAction1 = new javax.swing.JButton();
        buttonAction1.setName("Action Card 1");
        buttonClueCard4 = new javax.swing.JButton();
        buttonClueCard4.setName("Clue Card 4");
        buttonClueCard3 = new javax.swing.JButton();
        buttonClueCard3.setName("Clue Card 4");
        buttonClueCard2 = new javax.swing.JButton();
        buttonClueCard2.setName("Clue Card 4");
        buttonClueCard1 = new javax.swing.JButton();
        buttonClueCard1.setName("Clue Card 4");
        jScrollPane1 = new javax.swing.JScrollPane();
        notesTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        noteSheetPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        suspect1P4 = new javax.swing.JCheckBox();
        suspect1P0 = new javax.swing.JCheckBox();
        suspect1P3 = new javax.swing.JCheckBox();
        suspect1P1 = new javax.swing.JCheckBox();
        suspect1P2 = new javax.swing.JCheckBox();
        suspect2P0 = new javax.swing.JCheckBox();
        suspect2P1 = new javax.swing.JCheckBox();
        suspect2P2 = new javax.swing.JCheckBox();
        suspect2P3 = new javax.swing.JCheckBox();
        suspect2P4 = new javax.swing.JCheckBox();
        suspect3P4 = new javax.swing.JCheckBox();
        suspect3P3 = new javax.swing.JCheckBox();
        suspect3P2 = new javax.swing.JCheckBox();
        suspect3P1 = new javax.swing.JCheckBox();
        suspect3P0 = new javax.swing.JCheckBox();
        suspect4P4 = new javax.swing.JCheckBox();
        suspect4P3 = new javax.swing.JCheckBox();
        suspect4P2 = new javax.swing.JCheckBox();
        suspect4P1 = new javax.swing.JCheckBox();
        suspect4P0 = new javax.swing.JCheckBox();
        suspect5P4 = new javax.swing.JCheckBox();
        suspect5P3 = new javax.swing.JCheckBox();
        suspect5P2 = new javax.swing.JCheckBox();
        suspect5P1 = new javax.swing.JCheckBox();
        suspect5P0 = new javax.swing.JCheckBox();
        suspect6P4 = new javax.swing.JCheckBox();
        suspect6P3 = new javax.swing.JCheckBox();
        suspect6P2 = new javax.swing.JCheckBox();
        suspect6P1 = new javax.swing.JCheckBox();
        suspect6P0 = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        vehicle1P3 = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        vehicle1P4 = new javax.swing.JCheckBox();
        vehicle1P2 = new javax.swing.JCheckBox();
        vehicle1P1 = new javax.swing.JCheckBox();
        vehicle1P0 = new javax.swing.JCheckBox();
        vehicle2P4 = new javax.swing.JCheckBox();
        vehicle2P3 = new javax.swing.JCheckBox();
        vehicle2P2 = new javax.swing.JCheckBox();
        vehicle2P1 = new javax.swing.JCheckBox();
        vehicle2P0 = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        vehicle3P4 = new javax.swing.JCheckBox();
        vehicle3P3 = new javax.swing.JCheckBox();
        vehicle3P2 = new javax.swing.JCheckBox();
        vehicle3P1 = new javax.swing.JCheckBox();
        vehicle3P0 = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        vehicle4P4 = new javax.swing.JCheckBox();
        vehicle4P3 = new javax.swing.JCheckBox();
        vehicle4P2 = new javax.swing.JCheckBox();
        vehicle4P1 = new javax.swing.JCheckBox();
        vehicle4P0 = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        vehicle5P4 = new javax.swing.JCheckBox();
        vehicle5P3 = new javax.swing.JCheckBox();
        vehicle5P2 = new javax.swing.JCheckBox();
        vehicle5P1 = new javax.swing.JCheckBox();
        vehicle5P0 = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        vehicle6P4 = new javax.swing.JCheckBox();
        vehicle6P3 = new javax.swing.JCheckBox();
        vehicle6P2 = new javax.swing.JCheckBox();
        vehicle6P1 = new javax.swing.JCheckBox();
        vehicle6P0 = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        location1P4 = new javax.swing.JCheckBox();
        location1P3 = new javax.swing.JCheckBox();
        location1P2 = new javax.swing.JCheckBox();
        location1P1 = new javax.swing.JCheckBox();
        location1P0 = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        location2P4 = new javax.swing.JCheckBox();
        location2P3 = new javax.swing.JCheckBox();
        location2P2 = new javax.swing.JCheckBox();
        location2P1 = new javax.swing.JCheckBox();
        location2P0 = new javax.swing.JCheckBox();
        jLabel17 = new javax.swing.JLabel();
        location3P4 = new javax.swing.JCheckBox();
        location3P3 = new javax.swing.JCheckBox();
        location3P2 = new javax.swing.JCheckBox();
        location3P1 = new javax.swing.JCheckBox();
        location3P0 = new javax.swing.JCheckBox();
        location9P4 = new javax.swing.JCheckBox();
        jLabel18 = new javax.swing.JLabel();
        location4P4 = new javax.swing.JCheckBox();
        location4P3 = new javax.swing.JCheckBox();
        location4P2 = new javax.swing.JCheckBox();
        location4P1 = new javax.swing.JCheckBox();
        location4P0 = new javax.swing.JCheckBox();
        jLabel19 = new javax.swing.JLabel();
        location5P4 = new javax.swing.JCheckBox();
        location5P3 = new javax.swing.JCheckBox();
        location5P2 = new javax.swing.JCheckBox();
        location5P1 = new javax.swing.JCheckBox();
        location5P0 = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        location6P4 = new javax.swing.JCheckBox();
        location6P3 = new javax.swing.JCheckBox();
        location6P2 = new javax.swing.JCheckBox();
        location6P1 = new javax.swing.JCheckBox();
        location6P0 = new javax.swing.JCheckBox();
        jLabel21 = new javax.swing.JLabel();
        location7P4 = new javax.swing.JCheckBox();
        location7P3 = new javax.swing.JCheckBox();
        location7P2 = new javax.swing.JCheckBox();
        location7P1 = new javax.swing.JCheckBox();
        location7P0 = new javax.swing.JCheckBox();
        jLabel22 = new javax.swing.JLabel();
        location8P4 = new javax.swing.JCheckBox();
        location8P3 = new javax.swing.JCheckBox();
        location8P2 = new javax.swing.JCheckBox();
        location8P1 = new javax.swing.JCheckBox();
        location8P0 = new javax.swing.JCheckBox();
        jLabel23 = new javax.swing.JLabel();
        location9P3 = new javax.swing.JCheckBox();
        location9P2 = new javax.swing.JCheckBox();
        location9P1 = new javax.swing.JCheckBox();
        location9P0 = new javax.swing.JCheckBox();
        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        gameTablePanel = new javax.swing.JPanel();
        locationPlus2 = new javax.swing.JLabel();
        buttonDrawCard = new javax.swing.JButton(getIcon(
            "/deadgiveaway/resources/placeholder.gif"));
        buttonDrawCard.setName("Deck");
        playerPlus2Name = new javax.swing.JLabel();
        locationPlus1 = new javax.swing.JLabel();
        playerPlus1Name = new javax.swing.JLabel();
        locationPlus3 = new javax.swing.JLabel();
        playerPlus3Name = new javax.swing.JLabel();
        locationPlus4 = new javax.swing.JLabel();
        playerPlus4Name = new javax.swing.JLabel();
        handPlus1 = new javax.swing.JLabel();
        handPlus2 = new javax.swing.JLabel();
        handPlus3 = new javax.swing.JLabel();
        handPlus4 = new javax.swing.JLabel();
        playerLocation = new javax.swing.JLabel();
        kButtonKeys = new HashMap<Integer, JButton>();
        kButtonKeys.put(KeyEvent.VK_1, buttonClueCard4);
        kButtonKeys.put(KeyEvent.VK_2, buttonClueCard3);
        kButtonKeys.put(KeyEvent.VK_3, buttonClueCard2);
        kButtonKeys.put(KeyEvent.VK_4, buttonClueCard1);
        kButtonKeys.put(KeyEvent.VK_5, buttonAction1);
        kButtonKeys.put(KeyEvent.VK_6, buttonAction2);
        kButtonKeys.put(KeyEvent.VK_R, buttonRules);
        kButtonKeys.put(KeyEvent.VK_A, buttonAccuse);
        kButtonKeys.put(KeyEvent.VK_D, buttonDrawCard);
        kButtonKeys.put(KeyEvent.VK_E, buttonEndTurn);
                
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 255));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setForeground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        actionLog.setEditable(false);
        actionLog.setColumns(20);
        actionLog.setLineWrap(true);
        actionLog.setRows(5);
        actionLog.setWrapStyleWord(true);
        logPane.setViewportView(actionLog);

        bottomBar.setBorder(javax.swing.BorderFactory.createLineBorder(
                new java.awt.Color(0, 0, 0), 2));
        bottomBar.setPreferredSize(new java.awt.Dimension(732, 40));

        
        buttonEndTurn.setText("End Turn");
        buttonEndTurn.setName("End Turn");
        buttonEndTurn.setMargin(new java.awt.Insets(0, 0, 0, 0));
        buttonEndTurn.setMnemonic(KeyEvent.VK_BACK_SPACE);
        buttonEndTurn.setActionCommand("End Turn");
        buttonEndTurn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonEndTurnActionPerformed(evt);
            }
        });
        buttonEndTurn.addActionListener(client);

        buttonAccuse.setText("Accuse");
        buttonAccuse.setName("Accuse");
        buttonAccuse.setMargin(new java.awt.Insets(0, 0, 0, 0));
        buttonAccuse.setMnemonic(KeyEvent.VK_A);
        buttonAccuse.setActionCommand("Accuse");
        buttonAccuse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonAccuseActionPerformed(evt);
            }
        });
        buttonAccuse.addActionListener(client);

        buttonRules.setText("Rules");
        buttonRules.setMargin(new java.awt.Insets(0, 0, 0, 0));
        buttonRules.setMnemonic(KeyEvent.VK_R);
        buttonRules.setActionCommand("Rules");
        buttonRules.addActionListener(client);
        buttonRules.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonRulesActionPerformed(evt);
            }
        });

        labelTurnTimer.setFont(new java.awt.Font("Courier New", 1, 22)); // NOI18N
        labelTurnTimer.setText("3:00");
        labelTurnTimer.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        labelTurnIndicator.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        labelTurnIndicator.setText("Waiting for <.player.>...");

        GroupLayout bottomBarLayout =
                new GroupLayout(bottomBar);
        bottomBarLayout.setHorizontalGroup(
            bottomBarLayout.createParallelGroup(
                    GroupLayout.LEADING)
                    .add(GroupLayout.TRAILING,
                    bottomBarLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(labelTurnIndicator)
                        .add(30, 165, 165)
                        .add(labelTurnTimer)
                        .add(30, 165, 165)
                        .add(buttonRules)
                        .add(5, 5, 5)
                        .add(buttonAccuse)
                        .add(5, 5, 5)
                        .add(buttonEndTurn)
                        .addContainerGap())
        );
        bottomBarLayout.setVerticalGroup(
                bottomBarLayout.createSequentialGroup()
                    .add(4, 4, 4)
                    .add(bottomBarLayout.createParallelGroup(
                            GroupLayout.BASELINE)
                        .add(buttonEndTurn)
                        .add(buttonAccuse)
                        .add(buttonRules)
                        .add(labelTurnTimer)
                        .add(labelTurnIndicator))
        );
        bottomBar.setLayout(bottomBarLayout);
        buttonAction2.setName("Action Card 2");
        buttonAction2.setIcon(getIcon(
                "/deadgiveaway/resources/placeholder.png")); // NOI18N
        buttonAction2.setBorderPainted(false);
        buttonAction2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        buttonAction2.setPreferredSize(new java.awt.Dimension(70, 100));
        buttonAction2.setMaximumSize(new java.awt.Dimension(70, 100));
        buttonAction2.setMnemonic(KeyEvent.VK_6);
        buttonAction2.setActionCommand("Action 1");
        buttonAction2.addActionListener(client);

        buttonAction1.setName("Action Card 1");
        buttonAction1.setIcon(getIcon(
                "/deadgiveaway/resources/placeholder.png")); // NOI18N
        buttonAction1.setBorderPainted(false);
        buttonAction1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        buttonAction1.setPreferredSize(new java.awt.Dimension(70, 100));
        buttonAction1.setMaximumSize(new java.awt.Dimension(70, 100));
        buttonAction1.setMnemonic(KeyEvent.VK_5);
        buttonAction1.setActionCommand("Action 0");
        buttonAction1.addActionListener(client);

        buttonClueCard4.setName("Clue Card 4");
        buttonClueCard4.setIcon(getIcon(
                "/deadgiveaway/resources/placeholder.png")); // NOI18N
        buttonClueCard4.setBorderPainted(false);
        buttonClueCard4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        buttonClueCard4.setPreferredSize(new java.awt.Dimension(70, 100));
        buttonClueCard4.setMnemonic(KeyEvent.VK_4);
        buttonClueCard4.setActionCommand("Clue Card 3");
        buttonClueCard4.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonClueCardActionPerformed(evt);
            }
        });
        buttonClueCard4.addActionListener(client);
        
        buttonClueCard3.setName("Clue Card 3");
        buttonClueCard3.setIcon(getIcon(
                "/deadgiveaway/resources/placeholder.png")); // NOI18N
        buttonClueCard3.setBorderPainted(false);
        buttonClueCard3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        buttonClueCard3.setPreferredSize(new java.awt.Dimension(70, 100));
        buttonClueCard3.setMnemonic(KeyEvent.VK_3);
        buttonClueCard3.setActionCommand("Clue Card 2");
        buttonClueCard3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonClueCardActionPerformed(evt);
            }
        });
        buttonClueCard3.addActionListener(client);

        buttonClueCard2.setName("Clue Card 2");
        buttonClueCard2.setIcon(getIcon(
                "/deadgiveaway/resources/placeholder.png")); // NOI18N
        buttonClueCard2.setBorderPainted(false);
        buttonClueCard2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        buttonClueCard2.setPreferredSize(new java.awt.Dimension(70, 125));
        buttonClueCard2.setMnemonic(KeyEvent.VK_2);
        buttonClueCard2.setActionCommand("Clue Card 1");
        buttonClueCard2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonClueCardActionPerformed(evt);
            }
        });
        buttonClueCard2.addActionListener(client);

        buttonClueCard1.setName("Clue Card 1");
        buttonClueCard1.setIcon(getIcon(
            "/deadgiveaway/resources/placeholder.png")); // NOI18N
        buttonClueCard1.setBorderPainted(false);
        buttonClueCard1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        buttonClueCard1.setPreferredSize(new java.awt.Dimension(70, 100));
        buttonClueCard1.setMnemonic(KeyEvent.VK_1);
        buttonClueCard1.setActionCommand("Clue Card 0");
        buttonClueCard1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonClueCardActionPerformed(evt);
            }
        });
        buttonClueCard1.addActionListener(client);

        notesTextArea.setColumns(20);
        notesTextArea.setLineWrap(true);
        notesTextArea.setRows(5);
        notesTextArea.setText("(Your notes)");
        notesTextArea.setWrapStyleWord(true);
        
        notesTextArea.addKeyListener(new java.awt.event.KeyListener()
        {
            public void keyPressed(KeyEvent e)
            {
                //if key is tab, shift focus to main window
                if (e.getKeyCode() == KeyEvent.VK_TAB)
                {
                    GUI.this.requestFocusInWindow();
                }  
            }
            public void keyReleased(KeyEvent e)
            {
                
            }
            public void keyTyped(KeyEvent e)
            {
                
            }
        }      
        
        ); 
        jScrollPane1.setViewportView(notesTextArea);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(
                new java.awt.Color(0, 0, 0), 2));

        noteSheetPanel.setBackground(new java.awt.Color(255, 255, 255));
        // <editor-fold defaultstate="collapsed" desc="Generated Code"> 
        jLabel1.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel1.setText("Suspects:");

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel2.setText(kSuspectNames[curTheme][0]);

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel3.setText(kSuspectNames[curTheme][1]);

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel4.setText(kSuspectNames[curTheme][2]);

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel5.setText(kSuspectNames[curTheme][3]);

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel6.setText(kSuspectNames[curTheme][4]);

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel7.setText(kSuspectNames[curTheme][5]);

        suspect1P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect1P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect1P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect1P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect1P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect2P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect2P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect2P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect2P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect2P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect3P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect3P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect3P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect3P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect3P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect4P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect4P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect4P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect4P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect4P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect5P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect5P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect5P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect5P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect5P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect6P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect6P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect6P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect6P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        suspect6P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel8.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel8.setText("Vehicles:");

        vehicle1P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel9.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel9.setText(kVehicleNames[curTheme][0]);

        vehicle1P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle1P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle1P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle1P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle2P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle2P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle2P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle2P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle2P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel10.setText(kVehicleNames[curTheme][1]);

        vehicle3P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle3P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle3P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle3P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle3P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel11.setText(kVehicleNames[curTheme][2]);

        vehicle4P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle4P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle4P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle4P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle4P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel12.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel12.setText(kVehicleNames[curTheme][3]);

        vehicle5P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle5P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle5P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle5P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle5P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel13.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel13.setText(kVehicleNames[curTheme][4]);

        vehicle6P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle6P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle6P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle6P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        vehicle6P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel14.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel14.setText(kVehicleNames[curTheme][5]);

        jLabel15.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel15.setText("Locations:");

        location1P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location1P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location1P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location1P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location1P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel16.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel16.setText(kLocationNames[curTheme][0]);

        location2P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location2P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location2P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location2P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location2P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel17.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel17.setText(kLocationNames[curTheme][1]);

        location3P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location3P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location3P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location3P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location3P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location9P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel18.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel18.setText(kLocationNames[curTheme][2]);

        location4P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location4P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location4P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location4P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location4P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel19.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel19.setText(kLocationNames[curTheme][3]);

        location5P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location5P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location5P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location5P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location5P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel20.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel20.setText(kLocationNames[curTheme][4]);

        location6P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location6P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location6P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location6P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location6P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel21.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel21.setText(kLocationNames[curTheme][5]);

        location7P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location7P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location7P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location7P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location7P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel22.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel22.setText(kLocationNames[curTheme][6]);

        location8P4.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location8P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location8P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location8P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location8P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel23.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel23.setText(kLocationNames[curTheme][7]);

        location9P3.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location9P2.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location9P1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        location9P0.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel24.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel24.setText(kLocationNames[curTheme][8]);

        jLabel27.setFont(new java.awt.Font("Courier New", 0, 15)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("1 2 3 4 5");
        jLabel27.setName(""); // NOI18N

        jLabel28.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        jLabel28.setText("Players:");

        jLabel29.setText("Player 1:");

        jLabel30.setText("Player 2:");

        jLabel31.setText("Player 3:");

        jLabel32.setText("Player 4:");

        jLabel33.setText("Player 5:");

        GroupLayout notesLayout = new GroupLayout(noteSheetPanel);
        notesLayout.setHorizontalGroup(
                notesLayout.createParallelGroup(GroupLayout.LEADING)
                .add(notesLayout.createSequentialGroup()
                        .add(jLabel14)
                        .addPreferredGap(LayoutStyle.RELATED,
                                GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .add(vehicle6P0)
                        .add(vehicle6P1)
                        .add(vehicle6P2)
                        .add(vehicle6P3)
                        .add(vehicle6P4))
                .add(notesLayout.createSequentialGroup()
                        .add(jLabel13)
                        .addPreferredGap(LayoutStyle.RELATED,
                                GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .add(vehicle5P0)
                        .add(vehicle5P1)
                        .add(vehicle5P2)
                        .add(vehicle5P3)
                        .add(vehicle5P4))
                .add(notesLayout.createSequentialGroup()
                        .add(jLabel12)
                        .addPreferredGap(LayoutStyle.RELATED,
                                GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .add(vehicle4P0)
                        .add(vehicle4P1)
                        .add(vehicle4P2)
                        .add(vehicle4P3)
                        .add(vehicle4P4))
                .add(notesLayout.createSequentialGroup()
                        .add(jLabel11)
                        .addPreferredGap(LayoutStyle.RELATED,
                                GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .add(vehicle3P0)
                        .add(vehicle3P1)
                        .add(vehicle3P2)
                        .add(vehicle3P3)
                        .add(vehicle3P4))
                .add(notesLayout.createSequentialGroup()
                        .add(jLabel10)
                        .addPreferredGap(LayoutStyle.RELATED,
                                GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .add(vehicle2P0)
                        .add(vehicle2P1)
                        .add(vehicle2P2)
                        .add(vehicle2P3)
                        .add(vehicle2P4))
                .add(notesLayout.createSequentialGroup()
                        .add(jLabel9)
                        .addPreferredGap(LayoutStyle.RELATED,
                                GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .add(vehicle1P0)
                        .add(vehicle1P1)
                        .add(vehicle1P2)
                        .add(vehicle1P3)
                        .add(vehicle1P4))
                .add(notesLayout.createParallelGroup(
                                GroupLayout.LEADING)
                        .add(jLabel3)
                        .add(jLabel2)
                        .add(jLabel4)
                        .add(jLabel5)
                        .add(jLabel6)
                        .add(jLabel7))
                .add(GroupLayout.TRAILING,
                        notesLayout.createSequentialGroup()
                        .add(notesLayout.createParallelGroup(
                                        GroupLayout.LEADING)
                                .add(jLabel8)
                                .add(jLabel1)
                                .add(jLabel15)
                                .add(notesLayout.createParallelGroup(
                                                GroupLayout.LEADING)
                                        .add(jLabel17)
                                        .add(jLabel16)
                                        .add(jLabel18)
                                        .add(jLabel19)
                                        .add(jLabel20)
                                        .add(jLabel21)
                                        .add(jLabel22)
                                        .add(jLabel23)
                                        .add(jLabel24)
                                        .add(jLabel29)
                                        .add(jLabel31)
                                        .add(jLabel30)
                                        .add(jLabel32)
                                        .add(jLabel33)))
                        .addPreferredGap(LayoutStyle.RELATED,
                                GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .add(jLabel28)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(notesLayout.createParallelGroup(
                                        GroupLayout.LEADING)
                                .add(GroupLayout.TRAILING,
                                        notesLayout.createSequentialGroup()
                                        .add(suspect1P0)
                                        .add(suspect1P1)
                                        .add(suspect1P2)
                                        .add(suspect1P3)
                                        .add(suspect1P4))
                                .add(GroupLayout.TRAILING,
                                        notesLayout.createSequentialGroup()
                                        .add(suspect2P0)
                                        .add(suspect2P1)
                                        .add(suspect2P2)
                                        .add(suspect2P3)
                                        .add(suspect2P4))
                                .add(GroupLayout.TRAILING,
                                        notesLayout.createSequentialGroup()
                                        .add(suspect3P0)
                                        .add(suspect3P1)
                                        .add(suspect3P2)
                                        .add(suspect3P3)
                                        .add(suspect3P4))
                                .add(GroupLayout.TRAILING,
                                        notesLayout.createSequentialGroup()
                                        .add(suspect4P0)
                                        .add(suspect4P1)
                                        .add(suspect4P2)
                                        .add(suspect4P3)
                                        .add(suspect4P4))
                                .add(GroupLayout.TRAILING,
                                        notesLayout.createSequentialGroup()
                                        .add(suspect5P0)
                                        .add(suspect5P1)
                                        .add(suspect5P2)
                                        .add(suspect5P3)
                                        .add(suspect5P4))
                                .add(GroupLayout.TRAILING,
                                        notesLayout.createSequentialGroup()
                                        .add(suspect6P0)
                                        .add(suspect6P1)
                                        .add(suspect6P2)
                                        .add(suspect6P3)
                                        .add(suspect6P4))
                                .add(GroupLayout.TRAILING,
                                        notesLayout.createSequentialGroup()
                                        .add(location1P0)
                                        .add(location1P1)
                                        .add(location1P2)
                                        .add(location1P3)
                                        .add(location1P4))
                                .add(GroupLayout.TRAILING,
                                        notesLayout.createSequentialGroup()
                                        .add(location2P0)
                                        .add(location2P1)
                                        .add(location2P2)
                                        .add(location2P3)
                                        .add(location2P4))
                                .add(GroupLayout.TRAILING,
                                        notesLayout.createSequentialGroup()
                                        .add(location3P0)
                                        .add(location3P1)
                                        .add(location3P2)
                                        .add(location3P3)
                                        .add(location3P4))
                                .add(GroupLayout.TRAILING,
                                        notesLayout.createSequentialGroup()
                                        .add(location4P0)
                                        .add(location4P1)
                                        .add(location4P2)
                                        .add(location4P3)
                                        .add(location4P4))
                                .add(GroupLayout.TRAILING,
                                        notesLayout.createSequentialGroup()
                                        .add(location5P0)
                                        .add(location5P1)
                                        .add(location5P2)
                                        .add(location5P3)
                                        .add(location5P4))
                                .add(GroupLayout.TRAILING,
                                        notesLayout.createSequentialGroup()
                                        .add(location6P0)
                                        .add(location6P1)
                                        .add(location6P2)
                                        .add(location6P3)
                                        .add(location6P4))
                                .add(GroupLayout.TRAILING,
                                        notesLayout.createSequentialGroup()
                                        .add(location7P0)
                                        .add(location7P1)
                                        .add(location7P2)
                                        .add(location7P3)
                                        .add(location7P4))
                                .add(GroupLayout.TRAILING,
                                        notesLayout.createSequentialGroup()
                                        .add(location8P0)
                                        .add(location8P1)
                                        .add(location8P2)
                                        .add(location8P3)
                                        .add(location8P4))
                                .add(GroupLayout.TRAILING,
                                        notesLayout.createSequentialGroup()
                                        .add(location9P0)
                                        .add(location9P1)
                                        .add(location9P2)
                                        .add(location9P3)
                                        .add(location9P4))
                                .add(jLabel27)))
        );
        notesLayout.setVerticalGroup(notesLayout.createSequentialGroup()
                .addContainerGap()
                .add(notesLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(jLabel1)
                        .add(GroupLayout.TRAILING,
                                notesLayout.createParallelGroup(
                                        GroupLayout.LEADING)
                                .add(jLabel27)
                                .add(jLabel28)))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(suspect1P4)
                        .add(suspect1P3)
                        .add(suspect1P2)
                        .add(suspect1P1)
                        .add(suspect1P0)
                        .add(jLabel2))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(suspect2P0)
                        .add(suspect2P1)
                        .add(suspect2P2)
                        .add(suspect2P3)
                        .add(suspect2P4)
                        .add(jLabel3))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(
                                GroupLayout.TRAILING)
                        .add(suspect3P4)
                        .add(suspect3P3)
                        .add(suspect3P2)
                        .add(suspect3P1)
                        .add(suspect3P0)
                        .add(jLabel4))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(suspect4P0)
                        .add(suspect4P1)
                        .add(suspect4P2)
                        .add(suspect4P3)
                        .add(suspect4P4)
                        .add(jLabel5))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(suspect5P4)
                        .add(suspect5P3)
                        .add(suspect5P2)
                        .add(suspect5P1)
                        .add(suspect5P0)
                        .add(jLabel6))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(suspect6P4)
                        .add(suspect6P3)
                        .add(suspect6P2)
                        .add(suspect6P1)
                        .add(suspect6P0)
                        .add(jLabel7))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(jLabel8)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(
                                GroupLayout.TRAILING)
                        .add(vehicle1P4)
                        .add(vehicle1P3)
                        .add(vehicle1P2)
                        .add(vehicle1P1)
                        .add(vehicle1P0)
                        .add(jLabel9))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(vehicle2P4)
                        .add(vehicle2P3)
                        .add(vehicle2P2)
                        .add(vehicle2P1)
                        .add(vehicle2P0)
                        .add(GroupLayout.TRAILING, jLabel10))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(vehicle3P4)
                        .add(vehicle3P3)
                        .add(vehicle3P2)
                        .add(vehicle3P1)
                        .add(vehicle3P0)
                        .add(jLabel11))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(vehicle4P4)
                        .add(vehicle4P3)
                        .add(vehicle4P2)
                        .add(vehicle4P1)
                        .add(vehicle4P0)
                        .add(jLabel12))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(vehicle5P4)
                        .add(vehicle5P3)
                        .add(vehicle5P2)
                        .add(vehicle5P1)
                        .add(vehicle5P0)
                        .add(jLabel13))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(vehicle6P4)
                        .add(vehicle6P3)
                        .add(vehicle6P2)
                        .add(vehicle6P1)
                        .add(vehicle6P0)
                        .add(jLabel14))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(jLabel15)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(notesLayout.createParallelGroup(
                                        GroupLayout.LEADING)
                                .add(location1P4)
                                .add(location1P3)
                                .add(location1P2)
                                .add(location1P1)
                                .add(location1P0)
                                .add(jLabel16)))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(location2P4)
                        .add(location2P3)
                        .add(location2P2)
                        .add(location2P1)
                        .add(location2P0)
                        .add(jLabel17))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(
                                GroupLayout.TRAILING)
                        .add(notesLayout.createParallelGroup(
                                        GroupLayout.LEADING)
                                .add(location3P2)
                                .add(location3P4)
                                .add(location3P3)
                                .add(location3P1)
                                .add(location3P0))
                        .add(jLabel18))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(
                                GroupLayout.TRAILING)
                        .add(notesLayout.createParallelGroup(
                                        GroupLayout.LEADING)
                                .add(location4P4)
                                .add(location4P3)
                                .add(location4P2)
                                .add(location4P1)
                                .add(location4P0))
                        .add(jLabel19))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(
                                GroupLayout.TRAILING)
                        .add(notesLayout.createParallelGroup(
                                        GroupLayout.LEADING)
                                .add(location5P4)
                                .add(location5P3)
                                .add(location5P2)
                                .add(location5P1)
                                .add(location5P0))
                        .add(jLabel20))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(
                                GroupLayout.TRAILING)
                        .add(notesLayout.createParallelGroup(
                                        GroupLayout.LEADING)
                                .add(location6P4)
                                .add(location6P3)
                                .add(location6P2)
                                .add(location6P1)
                                .add(location6P0))
                        .add(jLabel21))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(
                                GroupLayout.TRAILING)
                        .add(notesLayout.createParallelGroup(
                                        GroupLayout.LEADING)
                                .add(location7P4)
                                .add(location7P3)
                                .add(location7P2)
                                .add(location7P1)
                                .add(location7P0))
                        .add(jLabel22))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(
                                GroupLayout.TRAILING)
                        .add(notesLayout.createParallelGroup(
                                        GroupLayout.LEADING)
                                .add(location8P4)
                                .add(location8P3)
                                .add(location8P2)
                                .add(location8P1)
                                .add(location8P0))
                        .add(jLabel23))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(notesLayout.createParallelGroup(
                                GroupLayout.TRAILING)
                        .add(notesLayout.createParallelGroup(
                                        GroupLayout.LEADING)
                                .add(location9P4)
                                .add(location9P3)
                                .add(location9P2)
                                .add(location9P1)
                                .add(location9P0))
                        .add(jLabel24))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(jLabel29)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(jLabel30)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(jLabel31)
                .addPreferredGap(LayoutStyle.RELATED, 8, Short.MAX_VALUE)
                .add(jLabel32)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(jLabel33)
                .add(5, 5, 5)
        );
        noteSheetPanel.setLayout(notesLayout); //</editor-fold>
        
        jScrollPane2.setViewportView(noteSheetPanel);

        locationPlus2.setIcon(getIcon(
            "/deadgiveaway/resources/Location-PhoebesMoon.gif")); // NOI18N

        buttonDrawCard.setText("Deck");
        buttonDrawCard.setPreferredSize(new java.awt.Dimension(70 , 100));
        buttonDrawCard.setMnemonic(KeyEvent.VK_D);
        buttonDrawCard.setActionCommand("Draw Card");
        buttonDrawCard.setHorizontalTextPosition(JButton.CENTER);
        buttonDrawCard.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonDrawCardActionPerformed(evt);
            }
        });
        
        buttonDrawCard.setBorder(kDefaultBorder);
        //Partially fixes defect 349
        buttonDrawCard.addActionListener(client);

        playerPlus2Name.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        playerPlus2Name.setText("<player+2>");

        locationPlus1.setIcon(getIcon(
            "/deadgiveaway/resources/Location-PoseidonsOcean.gif")); // NOI18N

        playerPlus1Name.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        playerPlus1Name.setText("<player+1>");

        locationPlus3.setIcon(getIcon(
            "/deadgiveaway/resources/Location-MountOlympus.gif")); // NOI18N

        playerPlus3Name.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        playerPlus3Name.setText("<player+3>");

        locationPlus4.setIcon(getIcon(
            "/deadgiveaway/resources/Location-AtlasSky.gif")); // NOI18N

        playerPlus4Name.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        playerPlus4Name.setText("<player+4>");

        handPlus1.setIcon(getIcon(
            "/deadgiveaway/resources/threeCardsLeft.gif")); // NOI18N

        handPlus2.setIcon(getIcon(
            "/deadgiveaway/resources/threeCardsTop.gif")); // NOI18N

        handPlus3.setIcon(getIcon(
            "/deadgiveaway/resources/threeCardsTop.gif")); // NOI18N

        handPlus4.setIcon(getIcon(
            "/deadgiveaway/resources/threeCardsRight.gif")); // NOI18N

        playerLocation.setIcon(getIcon(
            "/deadgiveaway/resources/placeholder.png")); // NOI18N

        // <editor-fold defaultstate="collapsed" desc="Generated Code"> 
        GroupLayout gameLayout = new GroupLayout(gameTablePanel);
        gameLayout.setHorizontalGroup(gameLayout.createSequentialGroup()
            .add(gameLayout.createParallelGroup(GroupLayout.LEADING)
                .add(gameLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(handPlus1)
                        .add(gameLayout.createParallelGroup(
                                        GroupLayout.LEADING)
                                .add(GroupLayout.TRAILING,
                                        gameLayout.createSequentialGroup()
                                        .addPreferredGap(
                                                LayoutStyle.RELATED,
                                                GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .add(locationPlus2)
                                        .addPreferredGap(
                                                LayoutStyle.RELATED)
                                        .add(playerPlus2Name)
                                        .add(14, 14, 14))
                                .add(gameLayout.createSequentialGroup()
                                        .addPreferredGap(
                                                LayoutStyle.RELATED)
                                        .add(gameLayout.createParallelGroup(
                                                        GroupLayout.LEADING)
                                                .add(playerPlus1Name)
                                                .add(locationPlus1))
                                        .addPreferredGap(
                                                LayoutStyle.RELATED,
                                                GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .add(buttonDrawCard,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))))
                .add(gameLayout.createSequentialGroup()
                        .add(79, 79, 79)
                        .add(handPlus2)))
            .add(18, 18, 18)
            .add(gameLayout.createParallelGroup(GroupLayout.TRAILING)
                .add(gameLayout.createSequentialGroup()
                        .add(handPlus3)
                        .add(95, 95, 95))
                .add(gameLayout.createSequentialGroup()
                        .add(gameLayout.createParallelGroup(
                                        GroupLayout.TRAILING)
                                .add(gameLayout.createSequentialGroup()
                                        .add(locationPlus3)
                                        .addPreferredGap(
                                                LayoutStyle.RELATED))
                                .add(gameLayout.createSequentialGroup()
                                        .add(playerLocation)
                                        .add(115, 115, 115)))
                        .add(gameLayout.createParallelGroup(
                                        GroupLayout.LEADING)
                                .add(gameLayout.createSequentialGroup()
                                        .add(gameLayout.createParallelGroup(
                                                        GroupLayout.TRAILING)
                                                .add(locationPlus4)
                                                .add(playerPlus4Name))
                                        .addPreferredGap(
                                                LayoutStyle.RELATED)
                                        .add(handPlus4))
                                .add(playerPlus3Name))))
        );
        gameLayout.setVerticalGroup(gameLayout.createParallelGroup(
                GroupLayout.TRAILING)
                    .add(gameLayout.createSequentialGroup()
                        .add(handPlus2)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(gameLayout.createParallelGroup(GroupLayout.LEADING)
                            .add(handPlus1)
                            .add(GroupLayout.TRAILING,
                                    gameLayout.createSequentialGroup()
                                .add(gameLayout.createParallelGroup(
                                            GroupLayout.LEADING)
                                    .add(playerPlus2Name)
                                    .add(gameLayout.createSequentialGroup()
                                        .add(locationPlus2)
                                        .addPreferredGap(
                                                LayoutStyle.RELATED,
                                                GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .add(locationPlus1)))
                                .addPreferredGap(
                                        LayoutStyle.RELATED)
                                .add(playerPlus1Name)
                                .add(10, 10, 10))))
                    .add(gameLayout.createSequentialGroup()
                        .add(handPlus3)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(gameLayout.createParallelGroup(GroupLayout.LEADING)
                            .add(gameLayout.createSequentialGroup()
                                .add(playerPlus3Name)
                                .addPreferredGap(
                                        LayoutStyle.RELATED,
                                        GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .add(gameLayout.createParallelGroup(
                                            GroupLayout.LEADING)
                                    .add(GroupLayout.TRAILING,
                                            gameLayout.createSequentialGroup()
                                            .add(locationPlus4)
                                            .addPreferredGap(
                                                    LayoutStyle.RELATED)
                                            .add(playerPlus4Name)
                                            .add(18,
                                                    18,
                                                    18))
                                    .add(GroupLayout.TRAILING,
                                            gameLayout.createSequentialGroup()
                                            .add(handPlus4)
                                            .addContainerGap())))
                            .add(gameLayout.createSequentialGroup()
                                .add(gameLayout.createParallelGroup(
                                                GroupLayout.TRAILING)
                                        .add(buttonDrawCard,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .add(gameLayout.createSequentialGroup()
                                                .add(locationPlus3)
                                                .add(18,
                                                        18,
                                                        18)
                                                .add(playerLocation)))
                                .addContainerGap())))
        );
        gameTablePanel.setLayout(gameLayout);
        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                    .add(layout.createParallelGroup(GroupLayout.LEADING)
                        .add(layout.createSequentialGroup()
                                .add(bottomBar,
                                        GroupLayout.PREFERRED_SIZE, 697,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.RELATED,
                                        GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE))
                        .add(layout.createSequentialGroup()
                                .add(gameTablePanel,
                                        GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.RELATED))
                        .add(layout.createSequentialGroup()
                            .add(logPane, GroupLayout.PREFERRED_SIZE,
                                    240,
                                    GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(
                                    org.jdesktop.layout.LayoutStyle.UNRELATED)
                            .add(buttonClueCard4,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.RELATED,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE)
                            .add(buttonClueCard3,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.RELATED)
                            .add(buttonClueCard2,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.RELATED)
                            .add(buttonClueCard1,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.RELATED)
                            .add(buttonAction1,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.RELATED)
                            .add(buttonAction2,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE)
                            .add(18, 18, 18)))
                    .add(layout.createParallelGroup(GroupLayout.LEADING,
                                    false)
                            .add(jScrollPane2, GroupLayout.PREFERRED_SIZE,
                                    333,
                                    GroupLayout.PREFERRED_SIZE)
                            .add(jScrollPane1)))
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
            .add(layout.createParallelGroup(GroupLayout.TRAILING)
                    .add(layout.createSequentialGroup()
                            .add(jScrollPane2,
                                    GroupLayout.PREFERRED_SIZE, 0,
                                    Short.MAX_VALUE)
                            .addPreferredGap(
                                    org.jdesktop.layout.LayoutStyle.UNRELATED)
                            .add(jScrollPane1,
                                    GroupLayout.PREFERRED_SIZE, 146,
                                    GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                            .add(gameTablePanel,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.RELATED)
                            .add(layout.createParallelGroup(
                                            GroupLayout.TRAILING)
                                    .add(layout.createParallelGroup(
                                                    GroupLayout.LEADING)
                                            .add(buttonAction1,
                                                    GroupLayout.PREFERRED_SIZE,
                                                    125,
                                                    GroupLayout.PREFERRED_SIZE)
                                            .add(buttonAction2,
                                                    GroupLayout.PREFERRED_SIZE,
                                                    125,
                                                    GroupLayout.PREFERRED_SIZE)
                                            .add(buttonClueCard1,
                                                    GroupLayout.PREFERRED_SIZE,
                                                    125,
                                                    GroupLayout.PREFERRED_SIZE)
                                            .add(buttonClueCard2,
                                                    GroupLayout.PREFERRED_SIZE,
                                                    GroupLayout.DEFAULT_SIZE,
                                                    GroupLayout.PREFERRED_SIZE)
                                            .add(buttonClueCard3,
                                                    GroupLayout.PREFERRED_SIZE,
                                                    125,
                                                    GroupLayout.PREFERRED_SIZE)
                                            .add(buttonClueCard4,
                                                    GroupLayout.PREFERRED_SIZE,
                                                    125,
                                                    GroupLayout.PREFERRED_SIZE))
                                    .add(logPane, GroupLayout.PREFERRED_SIZE,
                                            175, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(
                                    org.jdesktop.layout.LayoutStyle.UNRELATED)
                            .add(bottomBar,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE)))
        ); //</editor-fold>
        getContentPane().setLayout(layout);
        pack();
    }

    /**
     * Responds to the end turn button being pressed
     * @param evt The button's event
     */
    private void buttonEndTurnActionPerformed(java.awt.event.ActionEvent evt)
    {
        //DISABLE the end turn button and accuse button
        buttonEndTurn.setEnabled(false);
        buttonAccuse.setEnabled(false);
    }
    
    /**
     * Private helper method to disable buttons
     * @param cluecards whether to disable all clue cards
     * @param actioncards whether to disable all action cards
     * @param other whether to disable the end turn, accuse, and draw cards
     */
    private void disableButtons(boolean cluecards, boolean actioncards,
            boolean other)
    {
        //IF disabling clue cards
        if (cluecards)
        {
            //disable all clue card buttons
            for (JButton button : clueCardButtons)
            {
                button.setEnabled(false);
                button.setBorderPainted(false);
            }
        }
        //IF disabling action cards
        if (actioncards)
        {
            //disable all action card buttons
            for (JButton button : actionCardButtons)
            {
                button.setEnabled(false);
                button.setBorderPainted(false);
            }
        }
        //IF disabling other cards
        if (other)
        {
            buttonDrawCard.setEnabled(false);
            buttonAccuse.setEnabled(false);
            buttonEndTurn.setEnabled(false);
        }
    }
    /**
     * Called when an action has been input to the client
     */
    public void actionSelected()
    {
        reDrawCards();
        //DISABLE all buttons.
        disableButtons(true, true, false);
        buttonAccuse.setEnabled(false);
        buttonEndTurn.setEnabled(false);
    }
    
    /**
     * Returns a player's suggestion from any location.
     * @param suggestDialog The suggestion dialog jpanel
     * @param locationsBox The locations jcombobox
     * @param suspectsBox The suspects jcombobox
     * @param vehiclesBox The vehicles jcombobox
     * @param suggestCard the suggestion card used
     * @return A list of cards containing the selected location, suspect,
     * vehicle, and the suggest any card, in that order.
     */
    public Card[] getAnySuggestion(JPanel suggestDialog, JComboBox locationsBox,
            JComboBox suspectsBox, JComboBox vehiclesBox,
            ActionCard suggestCard)
    {
        //if the user didn't cancel the suggest any dialog
        if (JOptionPane.showConfirmDialog(null, suggestDialog, "",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null) == 0)
        {
            //CREATE the message containing the suggestion.
            // The suggestion represented as cards:
            Card[] suggestion =
            {
                new LocationCard(Title.values()
                    [locationsBox.getSelectedIndex()]),
                new SuspectCard(Name.values()[suspectsBox.getSelectedIndex()]),
                new VehicleCard(Model.values()[vehiclesBox.getSelectedIndex()]),
                suggestCard
            };
            return suggestion;
        }
        //ELSE:
        else
        {
            //RETURN null.
            return null;
        }
        //END IF
    }
    /**
     * Method called to prompt the user to move locations
     * @param moveDialog The dialog used to move the player
     * @param locationsBox the locations jcombobox with options for moving
     * @return null if the player decides not to move, else an array of cards
     * containing a locationcard with the player's selected location
     */
    public Card[] getMoveChange(JPanel moveDialog, JComboBox locationsBox)
    {
        //IF the user responds to the move dialog
        if (JOptionPane.showConfirmDialog(null, moveDialog, "",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null) == 0)
        {
            return new Card[] {new LocationCard(Title.values()
                [locationsBox.getSelectedIndex()])};
        }
        //ELSE:
        else
        {
            //RETURN null.
            return null;
        }
    }
    
    /**
     * Gets a suggestion via dialog box.
     * @param location The location to force. Pass null to let the user choose.
     * @return A message containing the suggestion
     */
    public Card[] getSuggestion(Integer location)
    {
        //CREATE a dialog box containing choices for suspect and vehicle.
        JPanel suggestDialog = new JPanel(); // The dialog box
        suggestDialog.setLayout(new BoxLayout(suggestDialog,
            BoxLayout.Y_AXIS));
        suggestDialog.add(new JLabel("Make a suggestion:"));
        // A dropdown containing the suspects:
        JComboBox suspectsBox = new JComboBox(kSuspectNames[curTheme]);
        suggestDialog.add(suspectsBox);
        // A dropdown containing the vehicles:
        JComboBox vehiclesBox = new JComboBox(kVehicleNames[curTheme]);
        suggestDialog.add(vehiclesBox);
        // A dropdown containing the locations:
        JComboBox locationsBox = new JComboBox(kLocationNames[curTheme]);

        //IF the location is null (Suggest All):
        if (location == null)
        {
            //ADD a dropdown menu for locations to the main dialog box.
            suggestDialog.add(locationsBox);
        }
        //ELSE (Suggest Current or Move):
        else
        {
            //GET the chosen action from a dialog box.
            JPanel moveDialog = new JPanel(); // The dialog box
            moveDialog.setLayout(new BoxLayout(moveDialog, BoxLayout.Y_AXIS));
            moveDialog.add(new JLabel("Select an action:"));
            JComboBox actionsBox = new JComboBox(
                    new String[] {"Make a suggestion", "Switch locations"});
            moveDialog.add(actionsBox);

            //IF the player clicked 'cancel':
            if (JOptionPane.showConfirmDialog(null, moveDialog, "",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null) != 0)
            {
                //RETURN null.
                return null;
            }

            //IF the chosen action was to move:
            if (actionsBox.getSelectedIndex() == 1)
            {
                //GET the chosen destination from a dialog box.
                moveDialog = new JPanel();
                moveDialog.setLayout(new BoxLayout(moveDialog,
                    BoxLayout.Y_AXIS));
                moveDialog.add(new JLabel("Select a destination:"));
                moveDialog.add(locationsBox);

                //IF the player didn't click 'cancel':
                return getMoveChange(moveDialog, locationsBox);
                //END IF
            //END IF
            }

            //(else -- the action is suggest current)
            //SET the selected suggestion location as the current location.
            locationsBox.setSelectedIndex(location);
            //ADD the preset location to the main dialog box.
            suggestDialog.add(new JLabel(kLocationNames[curTheme][location]));
        }
        //END IF

        //GET the suggestion from the main dialog box.
        //IF the player didn't click 'cancel':
        return getAnySuggestion(suggestDialog, locationsBox, suspectsBox,
                vehiclesBox, new ActionCard(ActionCard.Type.SUGGESTALL));
    }

    /**
     * Gets a Snoop via dialog box.
     * @param target The player to snoop on. Null if the player can choose.
     * @return The player chosen as the target. Return null if the user cancels.
     */
    @Override
    public Player getSnoop(Player target)
    {
        JPanel snoopDialog = new JPanel(); // A dialog box to get the snoop from
        ArrayList<String> options; // A list of snoop options as strings
        final ArrayList<JRadioButton> buttons = new ArrayList<JRadioButton>();

        //GET the selected card from a dialog box.
        snoopDialog = new JPanel();
        snoopDialog.setLayout(new BoxLayout(snoopDialog, BoxLayout.X_AXIS));
        ButtonGroup group = new ButtonGroup();
        snoopDialog = new JPanel();
        snoopDialog.add(new JLabel("Select one of " + target.getName() +
                "'s cards to view:   "));
        //ADD options to the dropdown based on the target's number of cards
        for (int idx = 0; idx < target.getNumCards(); idx++)
        {
            final JRadioButton button = new JRadioButton("Card " + (idx + 1)
                    , getIcon("/deadgiveaway/resources/placeholder.jpg"));
            button.setForeground(new Color(0x000000));
            //Fixes defect #464
            button.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,
                    new Color(0x804040FF, true)));
            button.addMouseListener(new java.awt.event.MouseAdapter()
            {
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    //iterate through the buttons and update their borders
                    for (JRadioButton button : buttons)
                    {
                        button.setBorderPainted(button.isSelected());
                    }
                }
            });
            button.setPreferredSize(new java.awt.Dimension(70, 100));
            button.setMaximumSize(new java.awt.Dimension(70, 100));
            button.setMargin(new java.awt.Insets(5, 5, 5, 5));
            button.setHorizontalTextPosition(JButton.CENTER);
            button.setFocusPainted(false);
            group.add(button);
            snoopDialog.add(button);
            buttons.add(button);
        }
        labelTurnIndicator.setText("Select a card to view.");
        //IF the player didn't click 'cancel':
        if (JOptionPane.showConfirmDialog(null, snoopDialog, "",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null) == 0)
        {
            return target;
        }
        //ELSE:
        else
        {
            //RETURN null.
            return null;
        }
        //END IF
    }
    
    /**
     * Gets a target for a snoop, super sleuth, or private tip
     * @return the Player the user has targeted
     */
    public Player selectTarget()
    {
        JPanel snoopDialog = new JPanel(); // A dialog box to get the snoop from
        ArrayList<String> options; // A list of snoop options as strings
        Player target = null;

        final ArrayList<JRadioButton> buttons = new ArrayList<JRadioButton>();
        snoopDialog.setLayout(new BoxLayout(snoopDialog, BoxLayout.X_AXIS));
        ButtonGroup group = new ButtonGroup();
        snoopDialog.add(new JLabel("Select a player to target:"));
        //ASK for a target, NOT INCLUDING SELF, in a dialog box.
        for (int idx = 1; idx < players.size(); idx++)
        {
            String buttonName = players.get((idx + thisPlayer.getID()) %
                    players.size()).getName();
            //Split the name on the button if it's longer than 8 characters
            if (buttonName.length() > 8)
            {
                buttonName = buttonName.substring(0, 8) + "\n" +
                        buttonName.substring(8, buttonName.length());
            }
            final JRadioButton button = new JRadioButton(buttonName, getIcon(
                    "/deadgiveaway/resources/user.jpg"));
            button.setForeground(new Color(0x804040FF));
            //Fixes defect #464
            button.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,
                    new Color(0x804040FF, true)));
            button.addMouseListener(new java.awt.event.MouseAdapter()
            {
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    //update all borders of the buttons
                    for (JRadioButton button : buttons)
                    {
                        button.setBorderPainted(button.isSelected());
                    }
                }
            });
            button.setFocusPainted(false);
            button.setPreferredSize(new java.awt.Dimension(70, 100));
            button.setMargin(new java.awt.Insets(5, 5, 5, 5));
            button.setHorizontalTextPosition(JButton.CENTER);
            group.add(button);
            snoopDialog.add(button);
            buttons.add(button);
        }

        //IF the player didn't click 'cancel':
        if (JOptionPane.showConfirmDialog(null, snoopDialog, "",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null) == 0)
        {
            int selected = 0;
            //FOR each button
            for (int idx = 0; idx < buttons.size() ; idx++)
            {
                //IF the button is selected
                if (buttons.get(idx).isSelected())
                {
                    //SET selected to the index of this button
                    selected = idx;
                }
            }
            //GET the target from the dialog box.
            return players.get((selected +
                                 thisPlayer.getID() + 1) % players.size());
        }
        //ELSE:
        else
        {
            //RETURN null.
            return null;
        }
        //END IF
    }

    /**
     * Handles a clue card being pressed by disabling all clue card buttons
     * @param evt The button's event
     */
    private void buttonClueCardActionPerformed(java.awt.event.ActionEvent evt)
    {
        //DISABLE the clue cards
        disableButtons(true, true, false);
    }

    /**
     * Handles the accusation button being pressed
     * @param evt The button's event
     */
    @SuppressWarnings("unchecked")
    private void buttonAccuseActionPerformed(java.awt.event.ActionEvent evt)
    {
        //GET accusation from a dialog box
        JPanel accuseDialog = new JPanel(); // The dialog box
        accuseDialog.setLayout(new BoxLayout(accuseDialog, BoxLayout.Y_AXIS));
        accuseDialog.add(new JLabel("Make an accusation:"));
        // A dropdown containing suspect options:
        JComboBox suspectsBox = new JComboBox(kSuspectNames[curTheme]);
        accuseDialog.add(suspectsBox);
        // A dropdown containing location options:
        JComboBox locationsBox = new JComboBox(kLocationNames[curTheme]);
        accuseDialog.add(locationsBox);
        // A dropdown containing vehicle options:
        JComboBox vehiclesBox = new JComboBox(kVehicleNames[curTheme]);
        accuseDialog.add(vehiclesBox);

        //IF the user didn't click 'cancel':
        if (JOptionPane.showConfirmDialog(null, accuseDialog, "",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null) == 0)
        {
            buttonEndTurn.setEnabled(false);
            buttonAccuse.setEnabled(false);
            //CREATE the message containing the accusation.
            ArrayList<Card> accusation = new ArrayList<Card>();
            accusation.add(new LocationCard(Title.values()
                           [locationsBox.getSelectedIndex()]));
            accusation.add(new SuspectCard(Name.values()
                           [suspectsBox.getSelectedIndex()]));
            accusation.add(new VehicleCard(Model.values()
                           [vehiclesBox.getSelectedIndex()]));
            //SEND the message containing the accusation to the server
            client.handleUserInput(new Message(players.get(thisPlayer.getID()),
                    null, players.toArray(new Player[5]), Move.ACCUSATION,
                    null, accusation.toArray(new Card[3])));
        }
        //END IF
    }

    /**
     * Handles the deck button being pressed
     * @param evt The button's event
     */
    private void buttonDrawCardActionPerformed(java.awt.event.ActionEvent evt)
    {
        //DISABLE the deck
        buttonDrawCard.setEnabled(false);
        //ENABLE the action cards
        buttonAction1.setEnabled(true);
        buttonAction2.setEnabled(true);
        //UPDATE the action card buttons
        labelTurnIndicator.setText("Select an action.");
        reDrawCards();
        //disable all the clue card buttons
        for(JButton curButton : clueCardButtons)
        {
            curButton.setEnabled(false);
        }
    }

    /**
     * Handles the rules button being pressed
     * @param evt The button's event
     */
    private void buttonRulesActionPerformed(java.awt.event.ActionEvent evt)
    {
        new RulesGUI().setVisible(true);
    }
 
    /**
     * Sets the turntimer countdown
     * @param time the time in minutes and seconds, as a string
     */
    @Override
    public void setTurnCountdown(String time)
    {
        labelTurnTimer.setText(String.format(time));
    }
    /**
     * Handles the player's turn timer expiring
     */
    public void timeExpired()
    {
        //UDPATE the action log
        writeToLog("Your time has run out");

        //DISABLE all buttons
        buttonDrawCard.setEnabled(false);
        buttonAccuse.setEnabled(false);
        buttonEndTurn.setEnabled(false);

        disableButtons(true, true, false);
        //REDRAW the cards:
        reDrawCards();
    }

    /**
     * Handles a key being pressed
     * @param e The KeyEvent of the key that was pressed
     * @return A boolean indicating whether or not this event is still active
     */
    public boolean postProcessKeyEvent(KeyEvent e)
    {
        //IF the user isn't typing in the free notes area AND IF there are no
        //other keyEvents being processed:
        if (gameStarted && !notesTextArea.hasFocus() && keyMutex.tryAcquire())
        {
            JButton button = kButtonKeys.get(e.getKeyCode());
            //if the key corresponds to a valid enabled button
            if (button != null && button.isEnabled())
            {
                //TRIGGER that button
                button.doClick();
            }
            //ALLOW other actions to be triggered from the keyboard
            keyMutex.release();
            //PREVENT any other dispatchers from using the key
            return true;
        }
        //END IF
        //ALLOW any other dispatchers to use the key
        return false;
    }

    /**
     * Returns the publicly available information from the Message, formatting
     * in a format easily read by a player.
     * @param msg the message to get info from
     * @return The message, formatted
     */
    //Fixes defect #465
    public String getInfo(Message msg)
    {
        //(Player name) (action) (target name)
        String returnString = msg.getPlayer().getName();
        
        //if the player has mad an action
        if (msg.getType() == Message.Type.SUGGESTION)
        {
            
            LocationCard loc = (LocationCard)msg.getCards()[0];
            SuspectCard sus = (SuspectCard)msg.getCards()[1];
            VehicleCard veh = (VehicleCard)msg.getCards()[2];

            returnString += " has made a suggestion consisting of "
                + getCardName(loc) + ", "
                + getCardName(sus) + ", and "
                + getCardName(veh);
        }
        //if the message is a disprovesuggestion
        else if (msg.getMove() == Move.DISPROVESUGGESTION)
        {
            LocationCard loc = (LocationCard)msg.getCards()[0];
            SuspectCard sus = (SuspectCard)msg.getCards()[1];
            VehicleCard veh = (VehicleCard)msg.getCards()[2];
        
            returnString = "Disprove " + returnString + "'s suggestion of "
                + getCardName(loc) + ", "
                + getCardName(sus) + ", and "
                + getCardName(veh);
        }
        //if the message is a player moved message
        else if (msg.getMove() == Message.Move.PLAYERMOVED)
        {
            LocationCard loc = (LocationCard)msg.getCards()[0];
            returnString = msg.getPlayer().getName() + " moved to " + 
                    getCardName(loc);
        }
        //else
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
     * Notifies this GUI of a Message being sent.
     * @param o The observable notifying the GUI
     * @param arg The Message being sent
     */
    @Override
    public void update(Observable o, Object arg)
    {
        //CAST the object to a Message
        Message msg = (Message) arg;
        //SWITCH on the message type
        switch (msg.getMove())
        {
            case UPDATELOG:
                //CONSTRUCT a string from the message
                //CALL WriteToLog()
                writeToLog(getInfo(msg));                
                break;
            case YOURTURN:
                //CALL turnstarted(this player, null, false)
                turnStarted(msg.getPlayer(),
                        (ActionCard)msg.getCards()[0], false);
                break;
            case RESUMETURN:
                //CALL turnstarted(this player, no card, true)
                turnStarted(msg.getPlayer(), null, true);
                break;
            case SHOWNCARDS:
                //call shownCard(target, cards, message move type)
                shownCard(msg.getPlayer(), msg.getTarget(),
                    msg.getCards(), msg.getType());
                break;
            case GAMESTARTED:
                //call gameStarted() and redraw the cards
                gameStarted();
                reDrawCards();
                break;
            case CONNECTIONESTABLISHED:
                //call connectionEstablished(player id)
                connectionEstablished(msg.getPlayer().getID(), msg.getType());
                break;
            case CONNECTIONREFUSED:
                dispose();
                break;
            case LOBBYSTARTED:
                startLobby(msg.getType() != Message.Type.LOBBYEXISTS);
                break;
            case DISPROVESUGGESTION:
                //call disprovesuggestion(target, cards)
                disproveSuggestion(msg.getPlayer(), msg.getCards());
                break;
            case ACCUSATION:
                //call accusationMade(subject, cards, correctness)
                accusationMade(msg.getPlayer(), msg.getCards(), msg.getType()
                    == Message.Type.CORRECTACCUSATION);
                break;
            case YOURACCUSATION:
                accusationMade(msg.getPlayer(), msg.getCards(), msg.getType()
                    == Message.Type.CORRECTACCUSATION);
                break;
            case ACTION:
                //call handleAction()
                handleAction(msg.getTarget(),
                    (ActionCard)msg.getCards()[msg.getCards().length - 1]);
                break;
            case TIMEOUT:
                timeExpired();
                break;
            case PLAYERMOVED:
                //CONSTRUCT a string from the message
                //CALL WriteToLog()
                writeToLog(getInfo(msg));                
                break;
            default:
                break;
        }
    }
    /**
     * Called when invalid input is passed to the GUI
     */
    @Override
    public void invalidInput()
    {
        invalidNumber++;
        writeToLog("You have entered some form of invalid input. Total "
                + invalidNumber + " times.");
        reDrawCards();
    }
    
    /**
     * Returns the number of invalid inputs. Intended for testing purposes.
     * @return The number of times this GUI has received a notification that
     * it has entered invalid input.
     */
    public int getInvalidNumber()
    {
        return invalidNumber;
    }

    /**
     * Tell the GUI to resume their turn.
     */
    public void allowEndTurnOrAccuse()
    {
        //ENABLE the end turn and accusation buttons
        buttonEndTurn.setEnabled(true);
        buttonAccuse.setEnabled(true);
        //SET the turn indicator
        labelTurnIndicator.setText("Accuse or end your turn.");
    }
    /**
     * Converts a card into its corresponding image
     * @param card the card to convert
     * @return An ImageIcon for that card
     */
    private ImageIcon getIconFromCard(Card card)
    {
        //IF the card is a Location
        if (card instanceof LocationCard)
        {
            //use the index to update the icon
            return getIcon(kPaddedImages.get("locationName" +
                    ((LocationCard)card).getTitle().ordinal())[curTheme]);
        }
        // ELSE IF the card is a Suspect
        else if (card instanceof SuspectCard)
        {
            //use the index to update the icon
            return getIcon(kPaddedImages.get("suspectName" +
                    ((SuspectCard)card).getName().ordinal())[curTheme]);
        }
        //ELSE IF card is a vehicle
        else if (card instanceof VehicleCard)
        {
            //use the index to update the icon
            return getIcon(kPaddedImages.get("vehicleModel" +
                    ((VehicleCard)card).getModel().ordinal())[curTheme]);
        }
        else
        {
            return null;
        }
    }
    /**
     * Displays a card that was shown to the user.
     * @param player The player who showed the card.
     * @param cards The cards that were shown.
     * @param action The action that was played to show the card
     * @param target The player shown the card
     */
    public void shownCard(Player player, Player target,
            Card[] cards, Message.Type action)
    {
        String messageText;
        String popupMessageText;
        ImageIcon icon = null;
        //if there is at least one card
        if (cards != null && cards.length != 0)
        {
            //The text to write to the log
            messageText = target.getName() + " showed you";
            popupMessageText = target.getName() + " showed you";
            //IF this is the player that played the card:
            //UPDATE the note sheet and get the card names
            for (int idx = 0; idx < cards.length; idx++)
            {
                //if this is actually a card
                if (cards[idx] != null)
                {
                    popupMessageText = target.getName() + " showed you " + 
                            getCardName(cards[idx]) + ".";
                    messageText += " " + getCardName(cards[idx]);
                    //IF within bounds
                    if (idx < cards.length - 1)
                    {
                        messageText += ",";
                    }
                    checkNotesheet(cards[idx], target.getID());
                    Card curCard = cards[idx];
                    icon = getIconFromCard(curCard);
                }
                //else this card is null
                else
                {
                    popupMessageText = target.getName() +
                            " showed you nothing.";
                    messageText += " nothing.";
                }
                JOptionPane.showMessageDialog(this, popupMessageText,
                    "Shown card", JOptionPane.INFORMATION_MESSAGE, icon);
            }
        }
        //else if the list of cards is null
        else
        {
            popupMessageText = "You were shown nothing.";
            messageText = "You were shown nothing.";
            JOptionPane.showMessageDialog(this, popupMessageText,
                    "Shown card", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        //UPDATE the action log
        writeToLog(messageText);
    }
    
    /**
     * Initializes the checkboxes when the game is started
     */
    public void initializeCheckBoxes()
    {
        //LOAD the checkboxes into arrays
        suspectCheckBoxes = new JCheckBox[][]
        {
            { suspect1P0, suspect1P1, suspect1P2, suspect1P3, suspect1P4},
            {suspect2P0, suspect2P1, suspect2P2, suspect2P3, suspect2P4},
            {suspect3P0, suspect3P1, suspect3P2, suspect3P3, suspect3P4},
            {suspect4P0, suspect4P1, suspect4P2, suspect4P3, suspect4P4},
            {suspect5P0, suspect5P1, suspect5P2, suspect5P3, suspect5P4},
            {suspect6P0, suspect6P1, suspect6P2, suspect6P3, suspect6P4}
        };

        locationCheckBoxes = new JCheckBox[][]
        {
            {location1P0, location1P1, location1P2, location1P3, location1P4},
            {location2P0, location2P1, location2P2, location2P3, location2P4},
            {location3P0, location3P1, location3P2, location3P3, location3P4},
            {location4P0, location4P1, location4P2, location4P3, location4P4},
            {location5P0, location5P1, location5P2, location5P3, location5P4},
            {location6P0, location6P1, location6P2, location6P3, location6P4},
            {location7P0, location7P1, location7P2, location7P3, location7P4},
            {location8P0, location8P1, location8P2, location8P3, location8P4},
            {location9P0, location9P1, location9P2, location9P3, location9P4}
        };

        vehicleCheckBoxes = new JCheckBox[][]
        {
            {vehicle1P0, vehicle1P1, vehicle1P2, vehicle1P3, vehicle1P4},
            {vehicle2P0, vehicle2P1, vehicle2P2, vehicle2P3, vehicle2P4},
            {vehicle3P0, vehicle3P1, vehicle3P2, vehicle3P3, vehicle3P4},
            {vehicle4P0, vehicle4P1, vehicle4P2, vehicle4P3, vehicle4P4},
            {vehicle5P0, vehicle5P1, vehicle5P2, vehicle5P3, vehicle5P4},
            {vehicle6P0, vehicle6P1, vehicle6P2, vehicle6P3, vehicle6P4}
        };
    }
    /**
     * Private helper method that initializes the notesheet
     */
    public void initNotesheetAndLabels()
    {
        noteSheetSuspects = new JLabel[]
        {
            jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7
        };
        noteSheetVehicles = new JLabel[]
        {
            jLabel9, jLabel10, jLabel11, jLabel12, jLabel13, jLabel14
        };
        noteSheetLocations = new JLabel[]
        {
            jLabel16, jLabel17, jLabel18, jLabel19, jLabel20,
            jLabel21, jLabel22, jLabel23, jLabel24
        };
        //SET all suspect labels in notesheet
        for (int idx = 0; idx < kNumSuspects; idx++)
        {
            noteSheetSuspects[idx].setText(kSuspectNames[curTheme][idx]);
        }
        //SET all vehicle labels in notesheet
        for (int idx = 0; idx < kNumVehicles; idx++)
        {
            noteSheetVehicles[idx].setText(kVehicleNames[curTheme][idx]);
        }
        //SET all location labels in notesheet
        for (int idx = 0; idx < kNumLocations; idx++)
        {
            noteSheetLocations[idx].setText(kLocationNames[curTheme][idx]);
        }
        playerNameLabels = new JLabel[]{
            jLabel29, jLabel30, jLabel31, jLabel32, jLabel33
        };
        //LOAD the location markers and player names into arrays.
        locationMarkers = new JLabel[]
        {
            playerLocation, locationPlus1, locationPlus2,
            locationPlus3, locationPlus4
        };
        otherNames = new JLabel[]
        {
            playerPlus1Name, playerPlus2Name,  playerPlus3Name,
            playerPlus4Name
        };
    }
    /**
     * Helper method to initialize cluecard and actioncard buttons
     */
    private void initButtons()
    {
        //LOAD the buttons into arrays
        clueCardButtons = new JButton[]
        {
            buttonClueCard1, buttonClueCard2, buttonClueCard3,
            buttonClueCard4
        };
        actionCardButtons = new JButton[] { buttonAction1, buttonAction2 };
        
        //DISABLE the initial hand.
        for (JButton curButton : clueCardButtons)
        {
            curButton.setIcon(null);
            curButton.setBorder(kDefaultBorder);
        }
        //DISABLE the action cards
        for (JButton curButton : actionCardButtons)
        {
            curButton.setIcon(null);
            curButton.setBorder(kDefaultBorder);
        }
        
        disableButtons(true, true, true);
    }
    /**
     * Removes the player from the lobby and begins displaying the game.
     */
    public void gameStarted()
    {
        gameStarted = true;
        //IF button 3 is selected
        if (jRadiolobbyButton3.isSelected())
        {
            curTheme = 0;
        }
        //IF button 2 is selected
        else if (jRadiolobbyButton2.isSelected())
        {
            curTheme = 2;
        }
        else
        {
            curTheme = 1;
        }
        //INITIALIZE Swing components generated by NetBeans GUI Builder
        getContentPane().removeAll();

        initComponents();
        initNotesheetAndLabels();
        
        javax.swing.text.DefaultCaret caret = (
                javax.swing.text.DefaultCaret)actionLog.getCaret();
        caret.setUpdatePolicy(
                javax.swing.text.DefaultCaret.ALWAYS_UPDATE);
        setVisible(true);

        initializeCheckBoxes();
        initButtons();

        //INITIALIZE the turn indicator to "Initializing game..."
        labelTurnIndicator.setText("Initializing game...     ");

        //INITIALIZE the action log with a welcome message.
        writeToLog(players.get(thisPlayer.getID()).getName()
                   + ", welcome to Dead Giveaway!");

        //DRAW all the cards
        reDrawCards();
        repaint();
    }

    /**
     * Displays that the connection to the server has been established.
     * @param playerPos The position of the player in the list of players
     * @param type whether a lobby already exists
     */
    public void connectionEstablished(int playerPos, Message.Type type)
    {
        //remove everything in the create game window
        thisPlayer.setID(playerPos);
        //initialize the lobby, with a boolean of whether this
        //player is the host
        if (type == Message.Type.LOBBYEXISTS)
        {
            startLobby(false);
        }
    }
    
    /**
     * Starts the lobby
     * @param host whether this player is the host
     */
    public void startLobby(boolean host)
    {
        isHost = host;
        String name = getUsername();
        getContentPane().removeAll();
        initLobbyComponents(name);
    }
    
    /**
     * Displays that a player's turn has started or can be resumed from a
     * previous action.
     * @param player The player whose turn it is.
     * @param drawn The card that was been drawn if it is the user's turn.
     * @param resume Whether or not the player is resuming their turn.
     */
    public void turnStarted(Player player, ActionCard drawn, boolean resume)
    {
        //IF it is the user's turn:
        if (thisPlayer.getID() == player.getID())
        {
            //IF resuming a turn
            if (resume)
            {
                //ENABLE the end turn button
                buttonEndTurn.setEnabled(true);
                labelTurnIndicator.setText("Accuse or end your turn.");
            }
            //ELSE
            else
            {
                writeToLog("It's your turn!");
                //SET the turn indicator
                labelTurnIndicator.setText("Draw a card.");
                //IF resuming a turn, enable 'End Turn' button:
                buttonEndTurn.setEnabled(false);
                //SET the next card to be drawn
                nextCard = drawn;
                //ENABLE the deck
                buttonDrawCard.setEnabled(true);
                buttonDrawCard.setBorderPainted(true);
            }
            //ENDIF
            //ENABLE the accusation button
            buttonAccuse.setEnabled(true);
        }
        //ELSE
        else
        {
            //SET the turn indicator
            labelTurnIndicator.setText(
                    "Waiting for " + player.getName() + "...");
        }
        //END IF
    }

    /**
     * Displays a suggestion that was made.
     * @param player The player making the suggestion.
     * @param suggestion The cards in the suggestion.
     */
    public void disproveSuggestion(Player player, Card[] suggestion)
    {
        //UPDATE the action log.
        writeToLog("Disprove " + player.getName() + "'s suggestion.");
        //SET the turn indicator
        labelTurnIndicator.setText("Select a card to show.");
        //GET the actionCard included with the suggestion
        ActionCard action = (ActionCard)suggestion[3];
        //ENABLE the valid cards through the action card
        int idx = 0;
        //iterate all the cards and enable the ones that are valid
        for (Card card : thisPlayer.getClueCards())
        {
            // IF it's the card we want
            if (action.matchesCard(card, suggestion))
            {
                clueCardButtons[idx].setEnabled(true);
                //Fixes defect #463
                clueCardButtons[idx].setBorderPainted(true);
            }
            idx++;
        }
        //ALERT the user.
        showPopup("You must disprove " + player.getName() + "'s suggestion:\n\""
                + getCardName(suggestion[0]) + ", " + getCardName(suggestion[1])
                + ", " + getCardName(suggestion[2]) + "\".");
    }

    /**
     * Displays an accusation that was made.
     * @param player The player making the accusation.
     * @param accusation The cards in the accusation.
     * @param right Whether the accusation was right or not
     */
    public void accusationMade(Player player, Card[] accusation, boolean right)
    {
        //IF the user made the accusation:
        if (player.getID() == thisPlayer.getID())
        {
            //DISABLE the accusation button.
            buttonAccuse.setEnabled(false);
            //DISABLE the end turn button.
            buttonEndTurn.setEnabled(false);
            //IF the user won:
            if (right)
            {
                writeToLog("Your accusation was correct; you win the game!");
                showPopup("Your accusation was correct; you win the game!");
                labelTurnIndicator.setText("You won!");
            }
            //ELSE:
            else
            {
                players.get(thisPlayer.getID()).setOut();
                inGame = false;
                writeToLog("Your accusation was incorrect.");
                showPopup("Your accusation was incorrect!\n\nYou cannot win " +
                    "the game, but you must continue\nto disprove other " +
                    "players' suggestions.");
                labelTurnIndicator.setText("You lost!");
                disableButtons(true, true, true);
            }
            //END IF
        }
        //ELSE (someone else made the accusation)
        else
        {
            //WRITE the accusation to the log.
            writeToLog(player.getName() + " accused \"" +
                getCardName(accusation[1]) + " at " + getCardName(accusation[0]) +
                " with " + getCardName(accusation[2]) + "\".");

            //IF that player won:
            if (right)
            {
                writeToLog(player.getName() + "'s accusation was correct. " +
                           player.getName() + " wins the game!");
                showPopup(player.getName() + "'s accusation was correct. " +
                          player.getName() + " wins the game!");
                labelTurnIndicator.setText(player.getName() + " wins!");
            }
            //ELSE:
            else
            {
                writeToLog(player.getName() + "'s accusation was incorrect.");
            }
            //END IF
        }
        //END IF
    }

    private void initCreateComponents()
    {
        lobbyButtonGroup = new javax.swing.ButtonGroup();
        createLabel1 = new java.awt.Label();
        createLabel2 = new java.awt.Label();
        createTextField1 = new java.awt.TextField();
        createLabel3 = new java.awt.Label();
        aiEasyButton = new javax.swing.JRadioButton();
        aiEasyButton.setSelected(true);
        aiMediumButton = new javax.swing.JRadioButton();
        aiHardButton = new javax.swing.JRadioButton();
        createRulesButton = new javax.swing.JButton("Rules");
        createQuitButton = new javax.swing.JButton("Quit");
        createGameButton = new javax.swing.JButton("Create Game");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        createLabel1.setAlignment(java.awt.Label.CENTER);
        createLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        createLabel1.setText("Welcome to Dead Giveaway!");

        createLabel2.setAlignment(java.awt.Label.CENTER);
        createLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        createLabel2.setText("Select a username (10 characters max)");

        createTextField1.setCursor(new java.awt.Cursor(
                java.awt.Cursor.TEXT_CURSOR));
        createTextField1.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                createTextField1FocusLost(evt);
            }
        });
        createTextField1.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyTyped(java.awt.event.KeyEvent evt)
            {
                createTextField1KeyTyped(evt);
            }
        });

        createLabel3.setAlignment(java.awt.Label.CENTER);
        createLabel3.setText("Select AI Difficulty");

        lobbyButtonGroup.add(aiEasyButton);
        aiEasyButton.setText("Easy - Makes dumb snoops and dumb suggestions");

        lobbyButtonGroup.add(aiMediumButton);
        aiMediumButton.setText(
                "Medium - Makes smart snoops and dumb suggestions");

        lobbyButtonGroup.add(aiHardButton);
        aiHardButton.setText("Hard - Makes smart snoops and smart suggestions");

        createRulesButton.setMnemonic(KeyEvent.VK_R);
        createRulesButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {

                createRulesButtonMouseClicked(evt);
            }
        });

        createQuitButton.setActionCommand("Quit");
        createQuitButton.setMnemonic(KeyEvent.VK_Q);
        createQuitButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {

                createQuitButtonMouseClicked(evt);
            }
        });
        createQuitButton.addActionListener(client);

        createGameButton.setActionCommand("Create Game");
        createGameButton.setMnemonic(KeyEvent.VK_C);
        createGameButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {

                createGameButtonMouseClicked(evt);
            }
        });
        createGameButton.addActionListener(client);

        setResizable(false);
        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
                .add(createLabel1)
                .add(createLabel2)
                .add(createLabel3)
                .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                    .add(layout.createParallelGroup(GroupLayout.LEADING)
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                            .add(aiHardButton)
                            .add(aiMediumButton)
                            .add(aiEasyButton))
                        .add(GroupLayout.TRAILING,
                                layout.createSequentialGroup()
                                .add(createGameButton,
                                    GroupLayout.PREFERRED_SIZE,
                                    120, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.RELATED)
                                .add(createRulesButton,
                                    GroupLayout.PREFERRED_SIZE,
                                    120, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.RELATED)
                                .add(createQuitButton,
                                    GroupLayout.PREFERRED_SIZE,
                                    120, GroupLayout.PREFERRED_SIZE))))
                .add(layout.createSequentialGroup()
                    .add(149, 149, 149)
                    .add(createTextField1, GroupLayout.PREFERRED_SIZE, 100,
                        GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .add(createLabel1)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(createLabel2)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(createTextField1, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(layout.createSequentialGroup()
                        .add(createLabel3)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(aiEasyButton)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(aiMediumButton)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(aiHardButton)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                            .add(createRulesButton, GroupLayout.PREFERRED_SIZE,
                                    45, GroupLayout.PREFERRED_SIZE)
                            .add(createQuitButton, GroupLayout.PREFERRED_SIZE,
                                    45, GroupLayout.PREFERRED_SIZE)
                            .add(createGameButton, GroupLayout.PREFERRED_SIZE,
                                    45, GroupLayout.PREFERRED_SIZE)))
        );
        getContentPane().setLayout(layout);
        pack();
    }

    private void createTextField1KeyTyped(java.awt.event.KeyEvent evt)
    {
        //IF the text field contains more than 10 characters, trim it
        if (createTextField1.getText().length() > 10)
        {
            createTextField1.setText(createTextField1.getText().substring(
                    0, 10));
        }
        createTextField1.setCaretPosition(9);
    }

    private void createTextField1FocusLost(java.awt.event.FocusEvent evt)
    {
        //IF the text field contains more than 10 characters, trim it
        if (createTextField1.getText().length() > 10)
        {
            createTextField1.setText(createTextField1.getText().substring(
                    0, 10));
        }
    }

    private void createRulesButtonMouseClicked(java.awt.event.MouseEvent evt)
    {
        //IF the button was a left click
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1)
        {
            (new RulesGUI()).setVisible(true);
        }
    }

    private void createQuitButtonMouseClicked(java.awt.event.MouseEvent evt)
    {
        //IF the button was a left click
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1)
        {
            this.dispose();
        }
    }

    private void createGameButtonMouseClicked(java.awt.event.MouseEvent evt)
    {
        //IF the button was a left click
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1)
        {
            thisPlayer.setName(createTextField1.getText());
        }
    }
    
    /**
     * Returns the username of this player
     * @return the username of this player
     */
    public String getUsername()
    {
        String ret = "";
        //if we are in the create game screen
        if (lobbyMode == 0)
        {
            ret = createTextField1.getText();
        }
        //else if we are in the lobby
        else if (lobbyMode == 1)
        {
            ret = lobbyTextField1.getText();
        }
        //else (we are in the main game)
        else
        {
            return thisPlayer.getName();
        }
        return ret;
    }
    
    /**
     * Returns the AI difficulty selected by the user in the create game menu
     * @return the AI difficulty selected by the user
     */
    public int getAIDifficulty()
    {
        int difficulty = -1;
        
        //IF the user selected easy AI
        if (aiEasyButton.isSelected())
        {
            difficulty = 0;
        }
        //IF the user selected medium AI
        else if (aiMediumButton.isSelected())
        {
            difficulty = 1;
        }
        //IF the user selected hard AI
        else if (aiHardButton.isSelected())
        {
            difficulty = 2;
        }
        
        return difficulty;
    }
    
    private javax.swing.JButton createRulesButton;
    private javax.swing.JButton createQuitButton;
    private javax.swing.JButton createGameButton;
    private javax.swing.ButtonGroup lobbyButtonGroup;
    private javax.swing.JRadioButton aiEasyButton;
    private javax.swing.JRadioButton aiMediumButton;
    private javax.swing.JRadioButton aiHardButton;
    private java.awt.Label createLabel1;
    private java.awt.Label createLabel2;
    private java.awt.Label createLabel3;
    private java.awt.TextField createTextField1;

    /**
     * Initializes the components in the lobby
     * @param name the name of the user when they were in the previous screen
     */
    private void initLobbyComponents(String name)
    {
        lobbyMode = 1;
        lobbyButtonGroup2 = new javax.swing.ButtonGroup();
        lobbyLabel1 = new java.awt.Label();
        lobbyLabel2 = new java.awt.Label();
        lobbyTextField1 = new java.awt.TextField();
        lobbyButton1 = new javax.swing.JButton("Rules");
        lobbyButton2 = new javax.swing.JButton("Quit");
        lobbyButton3 = new javax.swing.JButton("Start Game");
        lobbyButton3.setName("Start Game");
        lobbyLabel4 = new java.awt.Label();
        jRadiolobbyButton1 = new javax.swing.JRadioButton();
        jRadiolobbyButton2 = new javax.swing.JRadioButton();
        jRadiolobbyButton3 = new javax.swing.JRadioButton();
        lobbyJlobbyLabel1 = new javax.swing.JLabel();
        lobbyScrollPane1 = new javax.swing.JScrollPane();
        lobbyTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(400, 300));
        setResizable(false);

        lobbyLabel1.setAlignment(java.awt.Label.CENTER);
        lobbyLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lobbyLabel1.setText("Welcome to Dead Giveaway!");

        lobbyLabel2.setAlignment(java.awt.Label.CENTER);
        lobbyLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lobbyLabel2.setText("Select a username (10 characters max)");
        lobbyTextField1.setCursor(new java.awt.Cursor(
                java.awt.Cursor.TEXT_CURSOR));
        lobbyTextField1.setMaximumSize(new java.awt.Dimension(8, 20));
        lobbyTextField1.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                lobbyTextField1FocusLost(evt);
            }
        });
        lobbyTextArea1.setEnabled(false);

        String lobbyPlayers = "";
        //FOR every player in the game
        for (Player player : players)
        {
            lobbyPlayers += player.getName() + "\n";
        }
        lobbyTextArea1.setText(lobbyPlayers);
        
        //if the previous name isn't null, update the new username field with it
        if (name != null)
        {
            lobbyTextField1.setText(name);
        }
        //IF this player is the host
        if (isHost)
        {
            lobbyLabel2.setText("Your username:");
            lobbyTextField1.setEnabled(false);
        }
        lobbyTextField1.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyTyped(java.awt.event.KeyEvent evt)
            {

                lobbyTextField1KeyTyped(evt);
            }
        });

        lobbyButton1.setMaximumSize(new java.awt.Dimension(38, 24));
        lobbyButton1.setMinimumSize(new java.awt.Dimension(38, 24));
        lobbyButton1.setMnemonic(KeyEvent.VK_R);
        lobbyButton1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {

                lobbyButton1MouseClicked(evt);
            }
        });

        lobbyButton2.setActionCommand("Quit");
        lobbyButton2.setMnemonic(KeyEvent.VK_Q);
        lobbyButton2.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {

                lobbyButton2MouseClicked(evt);
            }
        });
        lobbyButton2.addActionListener(client);

        lobbyButton3.setMaximumSize(new java.awt.Dimension(38, 24));
        lobbyButton3.setMinimumSize(new java.awt.Dimension(38, 24));
        lobbyButton3.setPreferredSize(new java.awt.Dimension(38, 24));
        lobbyButton3.setActionCommand("Start Game");
        lobbyButton3.setMnemonic(KeyEvent.VK_S);
        lobbyButton3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {

                lobbyButton3ActionPerformed(evt);
            }
        });
        lobbyButton3.addActionListener(client);

        //IF this player isn't the host
        if (!isHost)
        {
            lobbyButton3.setLabel("Join Lobby");
            lobbyButton3.setActionCommand("Join Lobby");
        }

        lobbyLabel4.setAlignment(java.awt.Label.CENTER);
        lobbyLabel4.setMaximumSize(new java.awt.Dimension(141, 20));
        lobbyLabel4.setText("Players in Lobby: (5 max)");

        lobbyButtonGroup2.add(jRadiolobbyButton1);
        jRadiolobbyButton1.setText("Pirate");

        lobbyButtonGroup2.add(jRadiolobbyButton2);
        jRadiolobbyButton2.setText("WhiteHouse");

        lobbyButtonGroup2.add(jRadiolobbyButton3);
        jRadiolobbyButton3.setText("Greek");
        jRadiolobbyButton3.setSelected(true);

        lobbyJlobbyLabel1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lobbyJlobbyLabel1.setText("Choose Display Theme");

        lobbyTextArea1.setColumns(20);
        lobbyTextArea1.setRows(5);
        lobbyScrollPane1.setViewportView(lobbyTextArea1);

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                .add(lobbyLabel2, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(lobbyLabel1, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(layout.createSequentialGroup()
                        .add(160, 160, 160)
                        .add(lobbyTextField1, GroupLayout.PREFERRED_SIZE, 100,
                                GroupLayout.PREFERRED_SIZE))
                .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                                .add(layout.createSequentialGroup()
                                        .add(lobbyButton3,
                                                GroupLayout.PREFERRED_SIZE, 120,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.RELATED)
                                        .add(lobbyButton1,
                                                GroupLayout.PREFERRED_SIZE, 120,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.RELATED)
                                        .add(lobbyButton2,
                                                GroupLayout.PREFERRED_SIZE, 120,
                                                GroupLayout.PREFERRED_SIZE))
                                .add(layout.createSequentialGroup()
                                        .add(layout.createParallelGroup(
                                                        GroupLayout.LEADING)
                                                .add(lobbyLabel4)
                                                .add(layout
                                                        .createSequentialGroup()
                                                        .add(10, 10, 10)
                                                        .add(lobbyScrollPane1)))
                                        .addPreferredGap(LayoutStyle.RELATED)
                                        .add(layout.createParallelGroup(
                                                        GroupLayout.LEADING)
                                                .add(jRadiolobbyButton1)
                                                .add(jRadiolobbyButton2)
                                                .add(jRadiolobbyButton3)
                                                .add(lobbyJlobbyLabel1)))))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(lobbyLabel1)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(lobbyLabel2)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(lobbyTextField1, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(layout.createParallelGroup(GroupLayout.LEADING, false)
                    .add(lobbyLabel4)
                    .add(lobbyJlobbyLabel1))
                .add(layout.createParallelGroup(GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(28, 28, 28)
                        .add(jRadiolobbyButton3)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(jRadiolobbyButton1)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(jRadiolobbyButton2)
                        .add(23, 23, 23))
                    .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(lobbyScrollPane1, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)))
                .add(layout.createParallelGroup(GroupLayout.LEADING)
                    .add(lobbyButton1)
                    .add(lobbyButton2)
                    .add(lobbyButton3)))
        );
        getContentPane().setLayout(layout);

        pack();
    }

    private void lobbyTextField1KeyTyped(java.awt.event.KeyEvent evt)
    {
        //IF the text field contains more than 10 characters, trim it
        if (lobbyTextField1.getText().length() > 10)
        {
            lobbyTextField1.setText(lobbyTextField1.getText().substring(0, 10));
        }
        lobbyTextField1.setCaretPosition(10);
    }

    private void lobbyTextField1FocusLost(java.awt.event.FocusEvent evt)
    {
        //IF the text field contains more than 10 characters, trim it
        if (lobbyTextField1.getText().length() > 10)
        {
            lobbyTextField1.setText(lobbyTextField1.getText().substring(0, 10));
        }
    }

    private void lobbyButton1MouseClicked(java.awt.event.MouseEvent evt)
    {
        //IF the button was a left click
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) 
        {
            (new RulesGUI()).setVisible(true);
        }
    }

    private void lobbyButton2MouseClicked(java.awt.event.MouseEvent evt)
    {
        //IF the button was a left click
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1)
        {
            dispose();
        }
    }

    private void lobbyButton3ActionPerformed(java.awt.event.ActionEvent evt)
    {
        lobbyButton3.setEnabled(false);
        //IF this player is the host
        if (!isHost)
        {
            lobbyLabel2.setText("Your username:");
            lobbyTextField1.setEnabled(false);
            thisPlayer.setName(lobbyTextField1.getText());
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JButton lobbyButton1;
    private javax.swing.JButton lobbyButton2;
    private javax.swing.JButton lobbyButton3;
    private javax.swing.ButtonGroup lobbyButtonGroup2;
    private javax.swing.JLabel lobbyJlobbyLabel1;
    private javax.swing.JRadioButton jRadiolobbyButton1;
    private javax.swing.JRadioButton jRadiolobbyButton2;
    private javax.swing.JRadioButton jRadiolobbyButton3;
    private javax.swing.JScrollPane lobbyScrollPane1;
    private javax.swing.JTextArea lobbyTextArea1;
    private java.awt.Label lobbyLabel1;
    private java.awt.Label lobbyLabel2;
    private java.awt.Label lobbyLabel4;
    private java.awt.TextField lobbyTextField1;
    // End of variables declaration
    // Variables declaration - do not modify
    private javax.swing.JTextArea actionLog;
    private javax.swing.JPanel bottomBar;
    private JButton buttonAccuse, buttonDrawCard, buttonEndTurn;
    private JButton buttonClueCard1, buttonClueCard2, buttonClueCard3,
    buttonClueCard4, buttonAction1, buttonAction2;

    private javax.swing.JButton buttonRules;
    private javax.swing.JPanel gameTablePanel;
    private JLabel handPlus1, handPlus2, handPlus3, handPlus4;
    private JLabel locationPlus1, locationPlus2, locationPlus3, locationPlus4;
    private JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6,
    jLabel7, jLabel8, jLabel9, jLabel10, jLabel11, jLabel12, jLabel13, jLabel14,
    jLabel15, jLabel16, jLabel17, jLabel18, jLabel19, jLabel20, jLabel21,
    jLabel22, jLabel23, jLabel24, jLabel25, jLabel26, jLabel27, jLabel28,
    jLabel29, jLabel30, jLabel31, jLabel32, jLabel33;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelTurnIndicator;
    private javax.swing.JLabel labelTurnTimer;
    private JCheckBox location1P0, location1P1, location1P2, location1P3,
    location1P4, location2P0, location2P1, location2P2, location2P3,
    location2P4, location3P0, location3P1, location3P2, location3P3,
    location3P4, location4P0, location4P1, location4P2, location4P3,
    location4P4, location5P0, location5P1, location5P2, location5P3,
    location5P4, location6P0, location6P1, location6P2, location6P3,
    location6P4, location7P0, location7P1, location7P2, location7P3, 
    location7P4, location8P0, location8P1, location8P2, location8P3, 
    location8P4, location9P0, location9P1, location9P2, location9P3, 
    location9P4;
    private javax.swing.JScrollPane logPane;
    private javax.swing.JPanel noteSheetPanel;
    private javax.swing.JTextArea notesTextArea;
    private javax.swing.JPanel panelLobby;
    private javax.swing.JLabel playerLocation;
    private JLabel playerPlus1Name, playerPlus2Name, playerPlus3Name,
    playerPlus4Name;
    private JCheckBox suspect1P0, suspect1P1, suspect1P2,
    suspect1P3, suspect1P4, suspect2P0, suspect2P1, suspect2P2, suspect2P3,
    suspect2P4, suspect3P0, suspect3P1, suspect3P2, suspect3P3, suspect3P4,
    suspect4P0, suspect4P1, suspect4P2, suspect4P3, suspect4P4, suspect5P0,
    suspect5P1, suspect5P2, suspect5P3, suspect5P4, suspect6P0, suspect6P1,
    suspect6P2, suspect6P3, suspect6P4;
    private JCheckBox vehicle1P0, vehicle1P1, vehicle1P2,
    vehicle1P3, vehicle1P4, vehicle2P0, vehicle2P1, vehicle2P2, vehicle2P3, 
    vehicle2P4, vehicle3P0, vehicle3P1, vehicle3P2, vehicle3P3, vehicle3P4, 
    vehicle4P0, vehicle4P1, vehicle4P2, vehicle4P3, vehicle4P4, vehicle5P0, 
    vehicle5P1, vehicle5P2, vehicle5P3, vehicle5P4, vehicle6P0, vehicle6P1, 
    vehicle6P2, vehicle6P3, vehicle6P4;
    // End of variables declaration

    /**
     * The RulesGUI class is a JFrame used to display the rules to a player
     * @author Steven
     */
    private class RulesGUI extends javax.swing.JFrame
    {

        /**
         * Creates new form RulesGUI
         */
        public RulesGUI()
        {
            initComponents();
        }

        /**
         * Initializes the RulesGUI
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents()
        {

            textArea1 = new java.awt.TextArea();

            setDefaultCloseOperation(
                    javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

            textArea1.setText("PLAY\n\nTurns pass to the left. On your turn, you: \n1. Take the top Action card from the Draw Pile (by clicking it or pressing \"d\") and add it to your hand. \n2. Play one of your two Action cards (see \\Playing Action Cards\\ below). \n3. Conclude your turn by checking off the identity of any Clue cards you were shown during your turn.\n   a) Feel free to keep notes on your notesheet, such as what you see happening during other players' turns. \n4. Select one of the following:\n   a) Make an Accusation (with \"r\" or by clicking on the button) (see \\Making an Accusation\\)\n   b) End your turn, and the card you played is placed on the discard pile.\n\nPlaying Action Cards \nThere are five different types of Action cards.\nYou can only play one action card per turn.\nPlay an action card by clicking on it or pressing \"5\" (for the left card) or \"6\" (for the right card).\n \n* MAKE A SUGGESTION from any destination *\nDecide which Destination will be part of your Suggestion (see \\Making a Suggestion\\). If the destination is\ndifferent than the one you are in, your current Destination Marker will be exchanged for the desired\ndestination. If another player has this Destination Marker, that player takes yours in return.\n \n* MAKE A SUGGESTION from current destination (or move) *\nWhen you play this card, you may either swap out your destination marker for another, OR\nYou may make a suggestion with the restriction that the location you suggest is your current location.\n(see \\Making a Suggestion\\). \n \n* SNOOP *\nSelect an opponent to whom you wish to snoop on. Pick any one card and look at it. \n \n* ALL SNOOP *\nALL SNOOP cards are either \"All Snoop Left\", in which every player selects a card to view from the player on\ntheir left, or \"All Snoop Right\", in which every player selects a card to view from the player on their right.\n \n* SUPER SLEUTH *\nEach player must show you one card with the specified feature, if possible.\n \n* PRIVATE TIP *\nA selected player shows one card of the type requested (or all cards held of this type, if instructed by the\nPrivate Tip card). If the opponent has no cards of the type requested, they show nothing. \n\n\nENDING YOUR TURN \nAfter you have played an Action card and any relevant players have responded, you may either\nmake an accusation (by clicking the button or pressing \"R\") (see \\Making an Accusation\\) or end\nyour turn by clicking the button or pressing backspace)\n\nMAKING A SUGGESTION\nTo make a Suggestion, name a Suspect, Vehicle and a Destination from the selection lists.\nExample: \"I suggest that Aphrodite is traveling in Apollo's Chariot, to Hades Underworld.\"\n\nYou may only select a destination if you are playing a \"suggestion from any destination\" action card.\n\n*TIP: Making Suggestions throughout the game will help you to determine - by process of elimination -\nwhich three cards comprise the SOLUTION*\n\n*TIP: If you want to ensure that you receive a disproval of a certain type (for example, a suspect), you can\nmake your suggestion contain cards you own, leaving fewer fields for others to disprove.*\n\n*TIP: If nobody is able to disprove your suggestion, the elements of the suggestion are either in\nyour hand or in the suggestion*\n \nPROVING A SUGGESTION TRUE OR FALSE\nAs soon as you make a Suggestion, your opponents, in the order turns move, try to prove it false. As soon as\na player disproves your suggestion, the turn returns to you for you to end your turn or make an accusation.\n\nA player can disprove your suggestion, if they have any card that matches any item of your suggestion.\nIf that player can disprove your suggestion, they are required to select ONE such card to show you.\nIf that opponent has none of the cards that you named, then the next player is called to disprove instead.\n \n*TIP: If a player shows you one of the cards you named, it is proof that this card is not part of the solution.*\n \nMAKING AN ACCUSATION \nWhen you think you have figured out which three cards comprise the Solution, you should make an Accusation\non your turn. You may do so before or after playing an Action card on your turn.\nThen select the three elements you believe are in the Solution and submit it.\n \n*TIP: In an Accusation, you may name any destination.*\n\n*TIP: You may make only one Accusation per game; regardless of whether your accusation is correct you wil\nbe unable to continue playing cards or making accusations after an accusation.*\n \nWINNING\nIf your Accusation is correct (that is, the three elements you named are in the Solution), the game ends. Great\ndetective work! You win!\n \nIf your Accusation is incorrect... \n \n    - You remain in the game but make no further plays, so you cannot win. \n    - You continue to try to prove your opponents' Suggestions false. \n    - Opponents may still exchange Destination markers with you as usual.");

            GroupLayout layout = new GroupLayout(getContentPane());
            layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                    .add(textArea1,
                            GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                    .add(textArea1,
                            GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
            );
            getContentPane().setLayout(layout);
            pack();
        }// </editor-fold>                        
        // Variables declaration - do not modify                     
        private java.awt.TextArea textArea1;
        // End of variables declaration                   
    }
}