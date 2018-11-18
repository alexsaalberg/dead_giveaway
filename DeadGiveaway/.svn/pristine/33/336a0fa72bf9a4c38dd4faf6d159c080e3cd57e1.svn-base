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
public class ActionCardTest extends TestCase {
    //Initializing all the action cards for convenience
    ActionCard ASLCard = new ActionCard(ActionCard.Type.ALLSNOOPLEFT);
    ActionCard ASRCard = new ActionCard(ActionCard.Type.ALLSNOOPRIGHT);
    ActionCard PTDCard = new ActionCard(ActionCard.Type.PTDESTINATION);
    ActionCard PTFCard = new ActionCard(ActionCard.Type.PTFEMALE);
    ActionCard PTNCard = new ActionCard(ActionCard.Type.PTNORTHDEST);
    ActionCard PTRVCard = new ActionCard(ActionCard.Type.PTREDVEHICLE);
    ActionCard PTSCard = new ActionCard(ActionCard.Type.PTSUSPECT);
    ActionCard PTVCard = new ActionCard(ActionCard.Type.PTVEHICLE);
    ActionCard SnCard = new ActionCard(ActionCard.Type.SNOOP);
    ActionCard SSBCard = new ActionCard(ActionCard.Type.SSBLUE);
    ActionCard SSFeCard = new ActionCard(ActionCard.Type.SSFEMALE);
    ActionCard SSFlCard = new ActionCard(ActionCard.Type.SSFLYING);
    ActionCard SSMCard = new ActionCard(ActionCard.Type.SSMALE);
    ActionCard SSSCard = new ActionCard(ActionCard.Type.SSSOUTH);
    ActionCard SSWCard = new ActionCard(ActionCard.Type.SSWEST);
    ActionCard SACard = new ActionCard(ActionCard.Type.SUGGESTALL);
    ActionCard SCCard = new ActionCard(ActionCard.Type.SUGGESTCUR);
    public ActionCardTest(String testName) {
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

    /**
     * Test of equals method, of class ActionCard.
     */
    public void testEquals() {
        System.out.println("equals");
        assertFalse(SSMCard.equals(SSWCard));
        assertTrue(SSMCard.equals(SSMCard));
    }

    /**
     * Test of getType method, of class ActionCard.
     */
    public void testGetType() {
        System.out.println("getType");
        ActionCard.Type expResult = ActionCard.Type.SSFLYING;
        ActionCard.Type result = SSFlCard.getType();
        assertEquals(expResult, result);
        }

    /**
     * Test of matchesCard method, of class ActionCard.
     */
    public void testMatchesCard() {
        System.out.println("matchesCard");
        Card locOther = new LocationCard(LocationCard.Title.TITLE1);
        //First if case checking if the other card is valid - F Case
        assertFalse(SSSCard.matchesCard(PTNCard, null));
        //First if case - T Case
        //All True True Cases
        assertTrue(PTSCard.matchesCard(
                new SuspectCard(SuspectCard.Name.NAME1), null));
        assertTrue(PTVCard.matchesCard(new VehicleCard(
                VehicleCard.Model.MODEL1), null));
        assertTrue(PTDCard.matchesCard(new LocationCard(
                LocationCard.Title.TITLE1), null));
        
        assertTrue(PTFCard.matchesCard(new SuspectCard(
                SuspectCard.Name.NAME1), null));
        //Fail the next case
        assertFalse(PTFCard.matchesCard(new SuspectCard(
                SuspectCard.Name.NAME2), null));
        
        assertTrue(PTRVCard.matchesCard(new VehicleCard(
                VehicleCard.Model.MODEL4), null));
        //Fail next case
        assertFalse(PTRVCard.matchesCard(new VehicleCard(
                VehicleCard.Model.MODEL1), null));
        
        assertTrue(PTNCard.matchesCard(locOther, null));
        assertTrue(PTNCard.matchesCard(
                new LocationCard(LocationCard.Title.TITLE3), null));
        assertFalse(PTNCard.matchesCard(
                new LocationCard(LocationCard.Title.TITLE8), null));
        //True False Cases
        assertFalse(PTSCard.matchesCard(locOther, null));
        assertFalse(PTVCard.matchesCard(locOther, null));
        assertFalse(PTDCard.matchesCard(new SuspectCard(
                SuspectCard.Name.NAME2), null));
        assertFalse(PTFCard.matchesCard(locOther, null));
        assertFalse(PTNCard.matchesCard(new SuspectCard(
                SuspectCard.Name.NAME2), null));
        assertFalse(PTRVCard.matchesCard(locOther, null));
        assertFalse(SSSCard.matchesCard(locOther, null));
        assertFalse(SnCard.equals(locOther));
    }

    /**
     * Test of toString method, of class ActionCard.
     */
    public void testToString() {
        System.out.println("toString");
        assertEquals("Private Tip", PTRVCard.toString());
        assertEquals("Snoop", SnCard.toString());
    }

    /**
     * Test of clone method, of class ActionCard.
     */
    public void testClone() throws Exception {
        System.out.println("clone");
        Object result = SSSCard.clone();
        assertEquals(SSSCard, result);
        result = SnCard.clone();
        assertEquals(SnCard, result);
    }
    
}
