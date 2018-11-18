package deadgiveaway;

/**
 * The SuspectCard class provides implementation methods to simulate the
 * properties of a suspect card in the game.
 * @author Austin Sparks
 * @version 2.0
 */
public class SuspectCard extends Card 
{
    /**
     * Enumerator designating the name of the suspect card
     */
    public enum Name 
    {
        /** Name 1*/
        NAME1,
        /** Name 2*/
        NAME2,
        /** Name 3*/
        NAME3,
        /** Name 4*/
        NAME4,
        /** Name 5*/
        NAME5,
        /** Name 6*/
        NAME6;
        
        /** list of names for Greek theme */
        private static final String[] kNames = {
            "Aphrodite", "Ares", "Artemis", "Dionysius", "Hera", "Hermes"
        };
        
        /**
         * Represents the name of the suspect
         * @return name of the suspect
         */
        @Override
        public String toString() 
        {
            //Return a name corresponding to this card's suspect
            return kNames[this.ordinal()];
        }
    }
    
    /**
     * Enumerator designating the gender of each suspect
     */
    public enum Gender 
    {
        /** Gender 1*/
        GENDER1,
        /** Gender 2*/
        GENDER2;
        
        /**
         * Gets a string representation of the suspect's gender
         * @return Suspect's gender
         */
        @Override
        public String toString() 
        {
            // GET the gender - male then female
            String gender = "male";
            
            // ASSIGN gender as female if Gender 2
            if (this == GENDER2)
            {
                gender = "female";
            }
            
            return gender;
        }
        
        /** Array containing the genders of the suspects in order */
        private static final Gender[] kFromName = {GENDER2, GENDER1, GENDER2,
                                                   GENDER1, GENDER2, GENDER1};
    }
    
    /** name of this suspect */
    private final Name kName;
    /** gender of this suspect */
    private final Gender kGender;
    
    /**
     * Constructs a new SuspectCard
     * @param name the name of this suspect
     */
    public SuspectCard(Name name) 
    {
        //INITIALIZE the suspect card using the input name enum
        this.kName = name;
        this.kGender = Gender.kFromName[name.ordinal()];
        super.setTitle(name.toString());
    }
    
    /**
     * Returns whether this card is the same as the other suspect card
     * @param other the suspect card to check for equivalence to
     * @return whether this card is equal to the other suspect card
     */
    @Override
    public boolean equals(Object other) 
    {
        //IF the other card is a SuspectCard and has the same name
        return (other instanceof SuspectCard &&
                 ((SuspectCard)other).kName == this.kName);
    }
    
    /**
     * Gets the name of this suspect
     * @return name of this suspect
     */
    public Name getName() 
    {
        return kName;
    }
   
    /**
     * Return the gender of this suspect
     * @return the gender of this suspect
     */
    public Gender getGender() 
    {
        return kGender;
    }
   
    /**
     * Deep copies the suspect card
     * @return a copy of the suspect card
     * @throws CloneNotSupportedException if the card cannot be cloned
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return new SuspectCard(kName);
    }
    
    /**
     * Gets the name of the suspect as a string
     * @return The suspect's name.
     */
    @Override
    public String toString() 
    {
        return getName().toString();
    }
}
