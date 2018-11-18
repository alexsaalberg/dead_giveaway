package deadgiveaway;
import java.util.EnumMap;
/**
 * The ActionCard class provides implementation methods to simulate the
 * properties of an action card in the game.
 * @author Quang Ngo
 * @version 2.0
 */
public class ActionCard extends Card 
{
    /**
     * Enumerator for the type of action card.
     */
    public enum Type
    {
        /** Suggest from any location. */
        SUGGESTALL,
        /** Suggest from current location. */
        SUGGESTCUR,
        /** Regular snoop. */
        SNOOP,
        /** All snoop left. */
        ALLSNOOPLEFT,
        /** All snoop right. */
        ALLSNOOPRIGHT,
        /** Super sleuth female. */
        SSFEMALE,
        /** Super sleuth male. */
        SSMALE,
        /** Super sleuth flying. */
        SSFLYING,
        /** Super sleuth blue. */
        SSBLUE,
        /** Super sleuth south. */
        SSSOUTH,
        /** Super sleuth west. */
        SSWEST,
        /** Private tip suspect. */
        PTSUSPECT,
        /** Private tip vehicle. */
        PTVEHICLE,
        /** Private tip destination. */
        PTDESTINATION,
        /** Private tip female. */
        PTFEMALE,
        /** Private tip vehicle. */
        PTREDVEHICLE,
        /** Private tip north location. */
        PTNORTHDEST
    }
    
    /** Map for enums containing the title of the cards. */
    private final EnumMap<Type, String> enumTitle = new EnumMap(Type.class);
    /** Map for enums containing the description of the cards. */
    private EnumMap<Type, String> enumDesc = new EnumMap(Type.class);
    /** type - Type enum representing the action subtype */
    private final Type kType;
    
    /**
     * Constructs a new ActionCard. Sets the title of this card accordingly.
     * @param type The type of action card this card is
     */
    public ActionCard(Type type)
    {
       //Initalize the card's field with the given type
        mapSetupT();
        this.kType = type;
        super.setTitle(enumTitle.get(kType));
    }

    private void mapSetupT()
    {
        enumTitle.put(Type.ALLSNOOPLEFT, "All Snoop");
        enumTitle.put(Type.ALLSNOOPRIGHT, "All Snoop");
        enumTitle.put(Type.PTDESTINATION, "Private Tip");
        enumTitle.put(Type.PTFEMALE, "Private Tip");
        enumTitle.put(Type.PTNORTHDEST, "Private Tip");
        enumTitle.put(Type.PTREDVEHICLE, "Private Tip");
        enumTitle.put(Type.PTSUSPECT, "Private Tip");
        enumTitle.put(Type.PTVEHICLE, "Private Tip");
        enumTitle.put(Type.SNOOP, "Snoop");
        enumTitle.put(Type.SSBLUE, "Super Sleuth");
        enumTitle.put(Type.SSFEMALE, "Super Sleuth");
        enumTitle.put(Type.SSFLYING, "Super Sleuth");
        enumTitle.put(Type.SSMALE, "Super Sleuth");
        enumTitle.put(Type.SSSOUTH, "Super Sleuth");
        enumTitle.put(Type.SSWEST, "Super Sleuth");
        enumTitle.put(Type.SUGGESTALL, "Suggestion");
        enumTitle.put(Type.SUGGESTCUR, "Suggestion");
        mapSetupD();
    }
    
    private void mapSetupD()
    {
        enumDesc.put(Type.ALLSNOOPLEFT, "All players snoop on the hand "
                + "to their left.");
        enumDesc.put(Type.ALLSNOOPRIGHT, "All players snoop on the hand "
                + "to their left.");
        enumDesc.put(Type.PTDESTINATION, "Show me all your destination cards.");
        enumDesc.put(Type.PTFEMALE, "Show me one femalse suspect card.");
        enumDesc.put(Type.PTNORTHDEST, "Show me a location in the north.");
        enumDesc.put(Type.PTREDVEHICLE, "Show me a red vehicle card.");
        enumDesc.put(Type.PTSUSPECT, "Show me all your suspect card.");
        enumDesc.put(Type.PTVEHICLE, "Show me all your vehicle cards.");
        enumDesc.put(Type.SNOOP, "Snoop on any player's hand.");
        enumDesc.put(Type.SSBLUE, "Show me one blue vehicle card.");
        enumDesc.put(Type.SSFEMALE, "Show me one female suspect card");
        enumDesc.put(Type.SSFLYING, "Show me one flying vehicle card.");
        enumDesc.put(Type.SSMALE, "Show me one male suspect card.");
        enumDesc.put(Type.SSSOUTH, "Show me a location in the south");
        enumDesc.put(Type.SSWEST, "Show m e a location in the west.");
        enumDesc.put(Type.SUGGESTALL, "Make a suggestion from any location.");
        enumDesc.put(Type.SUGGESTCUR, "Make a suggestion from your current"
                + " location or switch locations.");
    }
    /**
     * Returns the title of the card type.
     * @return string representing the title of the card
     */
    public String title()
    {
        return enumTitle.get(kType);
    }
    
    /**
     * Returns whether this card is the same as the other action card
     * @param other the action card to check for equivalence to
     * @return whether this card is equal to the other action card
     */
    @Override
    public boolean equals(Object other) 
    {
        //IF the other card is an actioncard of equivalent type
        return (other instanceof ActionCard &&
               ((ActionCard)other).kType == this.kType);
    }
    
    /**
     * Returns the subtype of this action card
     * @return the Type of this Action Card 
     */
    public Type getType() 
    {
        //Return the type enum directly
        return kType;
    }
    /**
     * Checks if a clue card corresponds to a particular action
     * @param card the clue card to check
     * @param suggestion clue cards to compare to if action is suggestion
     * @return Whether the clue card would be shown if subject to this private
     * tip
     */
    public boolean matchesCard(Card card, Card[] suggestion) 
    {
        boolean correct = false;
        
        //IF the user's input is disproving a suggestion
        if (kType == Type.SUGGESTALL || kType == Type.SUGGESTCUR)
        {
            //CHECK correctness based on the suggestion
            correct = isCorrectTypeSuggest(card, suggestion);
        }
        //IF the user's input is in response to an Action
        else
        {
            //CHECK correctness based on the action card
            correct = isCorrectTypeAction(card);
        }
        
        return correct;
    }

    /**
     * Returns a description of this card
     * @return The description of this card, equivalent to the String
     * representing the type enum.
     */
    @Override
    public String toString() 
    {
        return enumTitle.get(kType);
    }
    
    /**
     * Deep copies the action card
     * @return The copy of this action card
     * @throws CloneNotSupportedException if the card cannot be cloned
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return new ActionCard(kType);
    }
    
    private boolean isCorrectTypeSuggest(Card card, Card[] suggest)
    {
        return card.equals(suggest[0]) || card.equals(suggest[1]) || 
               card.equals(suggest[2]);
    }
    
    private boolean isCorrectTypeAction(Card card)
    {
        boolean returnVal = true;
        
        //SWITCH on the type of private tip the played action card is
        switch(kType)
        {
            //CASE private tip destination
            case PTDESTINATION:
                returnVal = actionPrivateTipDest(card);
                break;
            //CASE private tip female suspect
            case PTFEMALE:
                returnVal = actionPrivateTipFemale(card);
                break;
            //CASE private tip male suspect
            case PTSUSPECT:
                returnVal = actionPrivateTipSuspect(card);
                break;
            //CASE private tip vehicle
            case PTVEHICLE:
                returnVal = actionPrivateTipVehicle(card);
                break;
            //CASE private tip red vehicle
            case PTREDVEHICLE:
                returnVal = actionPrivateTipRedVehicle(card);
                break;
            //CASE private tip northern destination
            case PTNORTHDEST:
                returnVal = actionPrivateTipNorthDest(card);
                break;
            default:
                returnVal = isCorrectTypeActionSuperSleuth(card);
        }
        return returnVal;
    }
    
    private boolean actionPrivateTipDest(Card card)
    {
        return card instanceof LocationCard;
    }
    private boolean actionPrivateTipFemale(Card card)
    {
        return (card instanceof SuspectCard
                && ((SuspectCard)card).getGender()
                == SuspectCard.Gender.GENDER2);
    }
    private boolean actionPrivateTipSuspect(Card card)
    {
        return card instanceof SuspectCard;
    }
    private boolean actionPrivateTipVehicle(Card card)
    {
        return card instanceof VehicleCard;
    }
    private boolean actionPrivateTipRedVehicle(Card card)
    {
        return (card instanceof VehicleCard 
                && ((VehicleCard)card).getColor()
                == VehicleCard.Color.COLOR1);
    }
    private boolean actionPrivateTipNorthDest(Card card)
    {
        return (card instanceof LocationCard
                && (((LocationCard)card).getDirection()
                == LocationCard.Direction.DIRECTION1
                || ((LocationCard)card).getDirection()
                == LocationCard.Direction.DIRECTION2));
    }
    
    private boolean isCorrectTypeActionSuperSleuth(Card card)
    {
        boolean returnVal = true;
        
        //SWITCH on the type of super sleuth the played action card is
        switch(kType)
        {
            //CASE super slueth female suspect
            case SSFEMALE:
                returnVal = actionSuperSleuthFemale(card);
                break;
            //CASE super sleuth male suspect
            case SSMALE:
                returnVal = actionSuperSleuthMale(card);
                break;
            //CASE super sleuth flying vehicle
            case SSFLYING:
                returnVal = actionSuperSleuthFlying(card);
                break;
            //CASE super sleuth blue vehicle
            case SSBLUE:
                returnVal = actionSuperSleuthBlue(card);
                break;
            //CASE super sleuth south destination
            case SSSOUTH:
                returnVal = actionSuperSleuthSouth(card);
                break;
            //CASE super sleuth west destination
            case SSWEST:
                returnVal = actionSuperSleuthWest(card);
                break;
            default:
        }     
        
        return returnVal;
    }
    
    private boolean actionSuperSleuthFemale(Card card)
    {
        return (card instanceof SuspectCard
                && ((SuspectCard)card).getGender()
                == SuspectCard.Gender.GENDER2);
    }
    private boolean actionSuperSleuthMale(Card card)
    {
        return (card instanceof SuspectCard
                && ((SuspectCard)card).getGender()
                == SuspectCard.Gender.GENDER1);
    }
    private boolean actionSuperSleuthFlying(Card card)
    {
        return (card instanceof VehicleCard 
                && ((VehicleCard)card).getType()
                == VehicleCard.Type.TYPE1);
    }
    private boolean actionSuperSleuthBlue(Card card)
    {
        return (card instanceof VehicleCard 
                && ((VehicleCard)card).getColor()
                == VehicleCard.Color.COLOR2);
    }
    private boolean actionSuperSleuthSouth(Card card)
    {
        return (card instanceof LocationCard
                && (((LocationCard)card).getDirection()
                == LocationCard.Direction.DIRECTION3
                || ((LocationCard)card).getDirection()
                == LocationCard.Direction.DIRECTION4));
    }
    private boolean actionSuperSleuthWest(Card card)
    {
        return (card instanceof LocationCard
                && (((LocationCard)card).getDirection()
                == LocationCard.Direction.DIRECTION2
                || ((LocationCard)card).getDirection()
                == LocationCard.Direction.DIRECTION3));
    }
}
