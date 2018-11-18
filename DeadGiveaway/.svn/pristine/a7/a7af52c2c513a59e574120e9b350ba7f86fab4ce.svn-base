/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deadgiveaway.server;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alex
 */
public class ServerAppTest
{
    
    public ServerAppTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }
    
    public void testMain()
    {
        String[] args = new String[4];
        args[0] = "-p";
        args[1] = "5557";
        args[2] = "-s";
        args[3] = "0";
        
        ServerApp.main(args);
    }
    
    @Test
    public void testMainDefect406()
    {
        Long startTime = System.currentTimeMillis();
        
        String[] args = new String[4];
        args[0] = "-p";
        args[1] = "5557";
        args[2] = "-s";
        args[3] = "0";
        
        ServerApp.main(args);
        
        Long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        
        //If the server took more than 10 seconds.
        if((endTime-startTime)>(10000))
        {
            fail();
        }
        
    }
    
    public void testMainBadArgs()
    {
        String[] args = new String[4];
        args[0] = "-p";
        args[1] = "-1";
        args[2] = "-s";
        args[3] = "-1";
        
        ServerApp.main(args);
    }
}