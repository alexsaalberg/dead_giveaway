package deadgiveaway.client;

import deadgiveaway.*;
import deadgiveaway.server.*;
import java.util.ArrayList;

/**
 * The UserInterface interface ensures that all user interfaces are observers 
 * and have methods which a ClueClient can call upon.
 * 
 * @author Jon Kuzmich
 * @version 2.0
 */
public interface UserInterface extends java.util.Observer 
{
    
    /**
     * Notifies the user that invalid input was entered.
     */
    public void invalidInput();
    
    /**
     * Responds to an action that another player has made.
     * @param o The ClueClient that received the message.
     * @param arg The message that represents another player's action.
     */
    @Override
    public void update(java.util.Observable o, Object arg);
    
    /**
     * Sets the countdown on the turn timer. 
     * @param time A string representing the time remaining for the user's turn.
     */
    public void setTurnCountdown(String time);
    
    /**
     * Gets a Snoop. Lets a player select a target and then 
     * one of the target's cards.
     * @param target The player to snoop on.
     * @return The player chosen as the target. Return null if the user cancels.
     */
    public Player getSnoop(Player target);
    
    /**
     * Gets a suggestion. Lets a player pick 
     * @param location The location to force. Pass null to let the user choose
     * a location (only for SUGGESTALL)
     * @return The suggestion in an array of cards, including the action card as
     * the last entry. Return null if the user cancels the suggestion.
     */
    public Card[] getSuggestion(Integer location);
    
    /**
     * Sets the list of players to the passed ArrayList. 
     * @param playerList The updated list of players. 
     */
    public void updatePlayers(ArrayList<Player> playerList);
    
    /**
     * Prevents the user from selecting performing another action. 
     */
    public void actionSelected();
    
    /**
     * Prompts the user to select a target for the desired action.
     * @return The player that is silected by the user as the target.
     */
    public Player selectTarget();
    
    /**
     * Getter for the AI difficulty.
     * @return The difficulty selected by the user.
     */
    public int getAIDifficulty();
    
    /**
     * Getter for the user's username.
     * @return The username of the user.
     */
    public String getUsername();
    
    /**
     * Allow the user to end their turn or accuse.
     * (Enable buttons for a GUI and display a prompt for a Console)
     */
    public void allowEndTurnOrAccuse();
}
