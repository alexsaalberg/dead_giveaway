package deadgiveaway;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for VehicleCard
 * @author Austin Sparks
 */
public class VehicleCardTest 
{
    VehicleCard vehicle1;
    VehicleCard vehicle2;
    
    @Before
    public void setUp() 
    {
        vehicle1 = new VehicleCard(VehicleCard.Model.MODEL1);
        vehicle2 = new VehicleCard(VehicleCard.Model.MODEL4);
    }

    /**
     * Test of equals method, of class VehicleCard.
     */
    @Test
    public void testEquals() 
    {
        // 1 and 2 should not be equal
        assertFalse(vehicle1.equals(vehicle2));
        
        // create copy of vehicle1
        VehicleCard vehicle3 = new VehicleCard(VehicleCard.Model.MODEL1);
        // 1 and 3 should be equal
        assertTrue(vehicle1.equals(vehicle3));
    }

    /**
     * Test of getColor method, of class VehicleCard.
     */
    @Test
    public void testGetColor() 
    {
        // 1st vehicle is color2
        assertEquals(VehicleCard.Color.COLOR2, vehicle1.getColor());
        // 2nd vehicle is color1
        assertEquals(VehicleCard.Color.COLOR1, vehicle2.getColor());
    }

    /**
     * Test of getModel method, of class VehicleCard.
     */
    @Test
    public void testGetModel() 
    {
        // 1st is model1
        assertEquals(VehicleCard.Model.MODEL1, vehicle1.getModel());
        // 2nd is model4
        assertEquals(VehicleCard.Model.MODEL4, vehicle2.getModel());
    }

    /**
     * Test of getType method, of class VehicleCard.
     */
    @Test
    public void testGetType() 
    {
        // 1st is type1
        assertEquals(VehicleCard.Type.TYPE1, vehicle1.getType());
        // 2nd is type2
        assertEquals(VehicleCard.Type.TYPE2, vehicle2.getType());
    }

    /**
     * Test of clone method, of class VehicleCard.
     */
    @Test
    public void testClone() throws Exception 
    {
        // clone 1st one
        VehicleCard vehicle3 = (VehicleCard)vehicle1.clone();
        // cards should be equal
        assertTrue(vehicle3.equals(vehicle1));
    }

    /**
     * Test of toString method, of class VehicleCard.
     */
    @Test
    public void testToString() 
    {
        // 1sh is Apollo's Chariot
        assertEquals("Apollo's Chariot", vehicle1.toString());
        // 2nd is Hades' Dogs
        assertEquals("Hades' Dogs", vehicle2.toString());
    }
    
}
