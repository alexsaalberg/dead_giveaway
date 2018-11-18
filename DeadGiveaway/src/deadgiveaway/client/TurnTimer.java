package deadgiveaway.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
* The TurnTimer class reacts to signals sent by the timer. The timer will
* send a signal every second, and TurnTimer will update the time left.
*
* @author Christopher Siu
* @version 0.2
* 
* @author Jon Kuzmich
* @version 2.0
*/
class TurnTimer implements ActionListener
{
    /** the number of milliseconds in a second */
    public static final int kMSinSec = 1000;
    /** the number of seconds in a minute */
    public static final int kSecsInMin = 60;
    /** number of seconds in default time limit*/
    public static final int kMaxTurnTime = 180;
    /** the lowest possible maximum time remaining */
    public static final int kLowestMax = 180;
    /** the amount of maximum time lost if time runs out */
    public static final int kPenalty = 0;
    /** time limit for responses to other actions */
    public static final int kResponseTime = 30;
    /** the GUI this timer belongs to */
    private ClueClient client;
    /** the amount of time remaining */
    private int timeRemaining;
    /** the maximum timer value */
    private int maxTime;

    /**
     * Constructs a turn timer.
     * @param initTime The initial value of the timer in seconds
     * @param client The ClueClient to which this timer belongs
     */
    public TurnTimer(int initTime, ClueClient client)
    {
        //SET the initial time.
        timeRemaining = initTime;
        maxTime = kMaxTurnTime;
        //INITIALIZE this timer's reference to its GUI.
        this.client = client;
    }  

    /**
     * Resets the timer to its maximum value
     */
    protected void reset()
    {
        timeRemaining = maxTime;
    }  

    /**
     * Resets the timer to the provided value
     * @param newTime The new value of the timer in seconds
     */
    protected void reset(int newTime)
    {
        timeRemaining = newTime;
    }

    /**
     * Resets the timer's maximum value to 90 seconds
     */
    protected void resetMax()
    {
        maxTime = kMaxTurnTime;
    }

    /**
     * Gets the maximum time.
     * @return The maximum time.
     */
    protected int getMax()
    {
        return maxTime;
    }

    /**
     * Sets the timer's maximum value to the provided value in seconds, as
     * long as that value is above the lowest possible maximum
     * @param newMax
     */
    protected void setMax(int newMax)
    {
        //IF the desired maximum is less than the limit:
        if (newMax < kLowestMax)
        {
            //SET the maximum to the limit.
            maxTime = kLowestMax;
        }
        //ELSE:
        else
        {
            //SET the new maximum.
            maxTime = newMax;
        }
        //END IF
    }

    /**
     * Decrements the timer when triggered. If the timer has reached 0,
     * lets its GUI know and decreases the maximum time.
     * @param e The action (typically a Timer that goes off every second)
     * to respond to.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //IF there is time remaining:
        if (timeRemaining > 0)
        {
            //DECREMENT the time remaining.
            timeRemaining--;
            //UPDATE the turn timer.
            client.setTimeRemaining(timeRemaining);
        }
        //ELSE:
        else
        {
            //CALL the GUI's time expired method.
            client.timeExpired();
        }
        //END IF
    }
}