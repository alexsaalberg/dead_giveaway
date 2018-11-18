/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package deadgiveaway;

import junit.framework.TestCase;

/**
 *
 * @author Quang
 */
public class LocationCardTest extends TestCase {
    
    public LocationCardTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testEnumMethods()
    {
        LocationCard title1 = new LocationCard(LocationCard.Title.TITLE1);
        LocationCard title2 = new LocationCard(LocationCard.Title.TITLE2);
        LocationCard title3 = new LocationCard(LocationCard.Title.TITLE3);
        LocationCard title4 = new LocationCard(LocationCard.Title.TITLE4);
        LocationCard title5 = new LocationCard(LocationCard.Title.TITLE5);
        LocationCard title6 = new LocationCard(LocationCard.Title.TITLE6);
        LocationCard title7 = new LocationCard(LocationCard.Title.TITLE7);
        LocationCard title8 = new LocationCard(LocationCard.Title.TITLE8);
        LocationCard title9 = new LocationCard(LocationCard.Title.TITLE9);
        
        
        assertEquals("Athena's Forest",title1.toString());
        assertEquals("Athens",title2.toString());
        assertEquals("Atlas's Sky",title3.toString());
        assertEquals("Dionysius's Vineyard",title4.toString());
        assertEquals("Hade's Underworld",title5.toString());
        assertEquals("Hephaestus's Volcano",title6.toString());
        assertEquals("Mount Olympus",title7.toString());
        assertEquals("Phoebe's Moon",title8.toString());
        assertEquals("Poseidon's Ocean",title9.toString());
    }
    
    /**
     * Test of equals method, of class LocationCard.
     */
    public void testEquals() {
        System.out.println("equals");
        LocationCard other = new LocationCard(LocationCard.Title.TITLE1);
        LocationCard instance = new LocationCard(LocationCard.Title.TITLE2);
        boolean expResult = false;
        boolean result = instance.equals(other);
        assertEquals(expResult, result);
        assertFalse(instance.equals(new 
            ActionCard(ActionCard.Type.ALLSNOOPLEFT)));
        assertTrue(instance.equals(instance));
    }

    /**
     * Test of getTitle method, of class LocationCard.
     */
    public void testGetTitle() {
        System.out.println("getTitle");
        LocationCard instance = new LocationCard(LocationCard.Title.TITLE2);
        LocationCard.Title expResult = LocationCard.Title.TITLE2;
        LocationCard.Title result = instance.getTitle();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDirection method, of class LocationCard.
     */
    public void testGetDirection() {
        System.out.println("getDirection");
        LocationCard instance = new LocationCard(LocationCard.Title.TITLE1);
        LocationCard.Direction expResult = LocationCard.Direction.DIRECTION1;
        LocationCard.Direction result = instance.getDirection();
        assertEquals(expResult, result);
    }

    /**
     * Test of clone method, of class LocationCard.
     */
    public void testClone() throws Exception {
        System.out.println("clone");
        LocationCard instance = new LocationCard(LocationCard.Title.TITLE1);
        Object expResult = instance;
        Object result = instance.clone();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class LocationCard.
     */
    public void testToString() {
        System.out.println("toString");
        LocationCard instance = new LocationCard(LocationCard.Title.TITLE1);
        String expResult = "Athena's Forest";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
