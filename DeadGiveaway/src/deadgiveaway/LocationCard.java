package deadgiveaway;

/**
 * The LocationCard class provides implementation methods to simulate the
 * properties of a location card in the game.
 * @author Steven Gerick
 * @version 1.0
 */
public class LocationCard extends Card 
{
    /**
     * Enumeration that represents directions of locations
     */
    public enum Direction 
    {
        /**
         * DIRECTION1 - NorthEast
         */
        DIRECTION1,
        /**
         * DIRECTION2 - NorthWest
         */
        DIRECTION2,
        /**
         * DIRECTION3 - SouthWest
         */
        DIRECTION3,
        /**
         * DIRECTION4 - SouthEast
         */
        DIRECTION4;
        
        /**
         * Returns the four directions available as a string.
         * @return The name of the direction being called.
         */
        @Override
        public String toString() 
        {
            String dir = "Invalid direction";

            //If diection1 set to NE
            if(this == DIRECTION1)
            {
                dir = "Northeast";
            }
            //if diection2 set to NW
            else if(this == DIRECTION2)
            {
                dir = "Northwest";
            }
            //if direction3 set to SW
            else if(this == DIRECTION3)
            {
                dir = "Southwest";
            }
            //if direction4 set to SE
            else if(this == DIRECTION4)
            {
                dir = "Southeast";
            }
            return dir;
        }
        
        private static final Direction[] kFromName = {DIRECTION1, DIRECTION1,
            DIRECTION2, DIRECTION2, DIRECTION3, DIRECTION3, DIRECTION3,
            DIRECTION4, DIRECTION4};
    }
    
    /**
     * ToString not supported on names because these vary during runtime
     * depending on the theme selected.
     */
    public enum Title 
    {
        /**
         * Enum for name of location 1
         */
        TITLE1,
        /**
         * Enum for name of location 2
         */
        TITLE2,
        /**
         * Enum for name of location 3
         */
        TITLE3,
        /**
         * Enum for name of location 4
         */
        TITLE4,
        /**
         * Enum for name of location 5
         */
        TITLE5,
        /**
         * Enum for name of location 6
         */
        TITLE6,
        /**
         * Enum for name of location 7
         */
        TITLE7,
        /**
         * Enum for name of location 8
         */
        TITLE8,
        /**
         * Enum for name of location 9
         */
        TITLE9;

        /**
         * Return a string representing this location's title
         * @return a string representing this location's title
         */
        @Override
        public String toString() 
        {
            //Set a string return value
            String title = "";
            //if it is title1
            if(this == TITLE1)
            {
                title = "Athena's Forest";
            }
            //if it is title2
            else if(this == TITLE2)
            {
                title = "Athens";
            }
            //if it is title3
            else if(this == TITLE3)
            {
                title = "Atlas's Sky";
            }
            //if it is title4
            else if(this == TITLE4)
            {
                title = "Dionysius's Vineyard";
            }
            //if it is title5
            else if(this == TITLE5)
            {
                title = "Hade's Underworld";
            }
            //if it is title6
            else if(this == TITLE6)
            {
                title = "Hephaestus's Volcano";
            }
            //if it is title7
            else if(this == TITLE7)
            {
                title = "Mount Olympus";
            }
            //if it is title8
            else if(this == TITLE8)
            {
                title = "Phoebe's Moon";
            }
            //if it is title9
            else
            {
                title = "Poseidon's Ocean";
            }
            return title;
        }
    }
    
    //Direction - Direction enum indicating one of four directions for the card
    private final Direction kDirection;
    //Name - Name enum that indicates the name of the card
    private final Title kTitle;
    
    /**
     * Constructs a new LocationCard
     * @param titl the name this card will hold
     */
    public LocationCard(Title titl) 
    {
        //INITIALIZE the card's fields using the given direction and name enums
        kTitle = titl;
        //INITIALIZE direction using the static array of ordinals to directions
        kDirection = Direction.kFromName[titl.ordinal()];
    }
    
    /**
     * Returns whether this card is the same as the other location card
     * @param other the location card to check for equivalence to
     * @return whether this card is equal to the other location card
     */
    @Override
    public boolean equals(Object other) 
    {
        //IF the other card is a LocationCard and has the same name
        return other instanceof LocationCard &&
               ((LocationCard)other).kTitle == this.kTitle;
    }
    
    /**
     * Return the name of this destination
     * @return the name of this destination
     */
    public Title getTitle() 
    {
        return kTitle;
    }
   
    /**
     * Return the direction of this destination
     * @return the direction of this destination
     */
    public Direction getDirection() 
    {
        return kDirection;
    }
   
    /**
     * Copy the current card
     * @return Copy of the card
     * @throws CloneNotSupportedException if this object cannot be cloned
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return new LocationCard(kTitle);
    }
    
    /**
     * Gets the string representation of the location card
     * @return Name of location as string
     */
    @Override
    public String toString() 
    {
        return getTitle().toString();
    }
}
