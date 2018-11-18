package deadgiveaway;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Unit Test class for SuspectCard
 * @author Austin Sparks
 */
public class SuspectCardTest 
{
    SuspectCard suspect1;
    SuspectCard suspect2;
    
    @Before
    public void setUp()
    {
        suspect1 = new SuspectCard(SuspectCard.Name.NAME1);
        suspect2 = new SuspectCard(SuspectCard.Name.NAME2);
    }
    /**
     * Test of equals method, of class SuspectCard.
     */
    @Test
    public void testEquals() 
    {
        SuspectCard suspect3 = new SuspectCard(SuspectCard.Name.NAME1);
        
        // suspect1 should not be equal to 2
        assertFalse(suspect1.equals(suspect2));
        // suspect1 should be equals to 3
        assertTrue(suspect1.equals(suspect3));
    }

    /**
     * Test of getName method, of class SuspectCard.
     */
    @Test
    public void testGetName() 
    {
        // suspect1 has name NAME1
        assertEquals(SuspectCard.Name.NAME1, suspect1.getName());
    }

    /**
     * Test of getGender method, of class SuspectCard.
     */
    @Test
    public void testGetGender() 
    {
        // suspect1 is female
        assertEquals(SuspectCard.Gender.GENDER2, suspect1.getGender());
        // suspect2 is male
        assertEquals(SuspectCard.Gender.GENDER1, suspect2.getGender());
    }

    /**
     * Test of clone method, of class SuspectCard.
     */
    @Test
    public void testClone() throws Exception 
    {
        SuspectCard clone = (SuspectCard)suspect1.clone();
        // cards should be equal
        assertTrue(suspect1.equals(clone));
    }

    /**
     * Test of toString method, of class SuspectCard.
     */
    @Test
    public void testToString() 
    {
        // suspect1 is named Aphrodite
        assertEquals("Aphrodite", suspect1.toString());
        // suspect2 is named Ares
        assertEquals("Ares", suspect2.toString());
    }
    
}
