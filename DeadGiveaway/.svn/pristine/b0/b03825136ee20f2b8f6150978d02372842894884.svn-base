package deadgiveaway;

/**
 * The card class provides abstract declarations of the fields and
 * methods common to card types.
 * 
 * @author Steven Gerick
 * @version 1.0
 */
public abstract class Card implements java.io.Serializable 
{
    //title - String variable that represents the name of the Card
    private String title;
    
    /**
     * Returns whether the given card is equivalent to another card.
     * @param other The card to check against for equality
     * @return whether the other card is equivalent to this card
     */
    public abstract boolean equals(Object other);
   
    /**
     * Deep copies this card.
     * @return A deep copy for this card
     * @throws CloneNotSupportedException if the object cannot be cloned
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        //clone - the clone we will be returning
        Card clone = (Card)super.clone();
        clone.title = title;
        return clone;
    }
   
    /**
     * Gives a string representation of the title of the card.
     * @return Card's title
     */
    @Override
    public String toString() 
    {
        return title;
    }
    
    /**
     * Sets the title of the card.
     * @param titl New title of the card
     */
    public void setTitle(String titl)
    {
        this.title = titl;
    }
}
