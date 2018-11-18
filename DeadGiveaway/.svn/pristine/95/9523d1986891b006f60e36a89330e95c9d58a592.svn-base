package deadgiveaway;

/**
 * The VehicleCard class provides implementation methods to simulate the
 * properties of a vehicle card in the game.
 * @author Austin Sparks
 * @version 2.0
 */
public class VehicleCard extends Card 
{
    /**
     * Enumerator designating the color of the vehicleCard, Red or Blue
     */
    public enum Color 
    {
        /** COLOR1 - Red */
        COLOR1, 
        /** COLOR2 - Blue */
        COLOR2;
        
        /**
         * Returns the color of this Color as a string. Set to 1=Red, 2=Blue in
         * this version.
         * @return the color this Color represents.
         */
        @Override
        public String toString() 
        {
            //GET the color - red then blue
            String color = "";
            
            //SWITCH on this
            switch (this)
            {
                case COLOR1:
                    color = "Red";
                    break;
                case COLOR2:
                    color = "Blue";
                    break;
                default :
                    color = "INVALIDCOLOR";
                    break;
            }
            
            return color;
        }
        
        /** Array to get the vehicle color based on the model */
        public static final Color[] kFromModel = {COLOR2, COLOR2, COLOR2, COLOR1,
            COLOR1, COLOR1};
    }
    
    /**
     * Enumerator designating the type of the Card
     */
    public enum Type 
    {
        /** TYPE1 - Flying */
        TYPE1,
        /** TYPE2 - Ground */
        TYPE2;
        
        /**
         * Returns the type of this card. Valid types in this version are
         * flying and ground.
         * @return The type of this card.
         */
        @Override
        public String toString() 
        {
            String type = "";
            
            //CASE a switch on the type enum OF
            switch (this)
            {
                // condition TYPE1 : "Flying"
                case TYPE1:
                    type = "Flying";
                    break;
                // condition TYPE2 : "Ground"
                case TYPE2:
                    type = "Ground";
                    break;
                default :
                    type = "INVALIDTYPE";
                    break;
            } //ENDCASE
            
            return type;
        }
        
        /** Array to get the type based on the model */
        public static final Type[] kFromModel = {TYPE1, TYPE2, TYPE1, TYPE2,
                                                 TYPE1, TYPE2};
    }
    
    /**
     * Enumerator for vehicle model
     */
    public enum Model 
    {
        
        /** MODEL1 - Apollo's Chariot */
        MODEL1,
        /** MODEL2 - Athena's Horse */
        MODEL2, 
        /** MODEL3 - Ero's Wings */
        MODEL3, 
        /** MODEL4 - Hades' Dogs */
        MODEL4, 
        /** MODEL5 - Hermes' Winged Sandals */
        MODEL5, 
        /** MODEL6 - Poseidon's Ship */
        MODEL6;
        
        private static final String[] kNames = {
            "Apollo's Chariot", "Athena's Horse", "Ero's Wings",
            "Hades' Dogs", "Hermes' Winged Sandals", "Poseidon's Ship"
        };
        
        /**
         * Gets a string representation of the vehicle
         * @return Vehicle Model name as a string
         */
        @Override
        public String toString()
        {
            // GET names in alphabetical order
            return kNames[this.ordinal()];
        }
    }
    
    //color - enum representing the color of this vehicle
    private final Color kColor;
    //type - enum representing type of this vehicle
    private final Type kType;
    //name - enum representing name of this vehicle
    private final Model kName;
    
    /**
     * Constructs a new vehicle card
     * @param name The Model this card will hold
     */
    public VehicleCard(Model name) 
    {
        //Initialize the card to have the given color, type, and name
        this.kName = name;
        this.kColor = Color.kFromModel[name.ordinal()];
        this.kType = Type.kFromModel[name.ordinal()];
    }
    
    /**
     * Returns whether this card is the same as the other vehicle card
     * @param other the vehicle card to check for equivalence to
     * @return whether this card is equal to the other vehicle card
     */
    @Override
    public boolean equals(Object other) 
    {
        //IF the other card is a vehiclecard and has the same name THEN
        return (other instanceof VehicleCard &&
                ((VehicleCard)other).kName == this.kName);
    }
    
    /**
     * Return the color of this vehicle
     * @return the enum color of this vehicle
     */
    public Color getColor() 
    {
        return kColor;
    }
    
    /**
     * Return the model of this vehicle
     * @return the model of this vehicle
     */
    public Model getModel() 
    {
        return kName;
    }
    
    /**
     * Return the type of this vehicle
     * @return the type of this vehicle
     */
    public Type getType() 
    {
        return kType;
    }
    
    /**
     * Deep copies the vehicle card
     * @return the copy of this vehicle card
     * @throws CloneNotSupportedException if this card cannot be cloned
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return new VehicleCard(kName);
    }
    
    /**
     * Gets a string representation of the vehicle
     * @return Vehicle model name as a string
     */
    @Override
    public String toString() 
    {
        return getModel().toString();
    }
}
