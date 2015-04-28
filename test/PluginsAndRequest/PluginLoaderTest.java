/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * All code and works here are created by Satria Priambada and team
 * You are free to use and distribute the code
 * We do not take responsibilities for any damage caused by using this code
 */
package PluginsAndRequest;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Satria
 */
public class PluginLoaderTest {
    
    public PluginLoaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of GetPreRequestList method, of class PluginLoader.
     */
    @Test
    public void testGetPreRequestList() {
        System.out.println("GetPreRequestList");
        PluginLoader instance = new PluginLoader();
        ArrayList expResult = null;
        ArrayList result = instance.GetPreRequestList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetProcessRequestList method, of class PluginLoader.
     */
    @Test
    public void testGetProcessRequestList() {
        System.out.println("GetProcessRequestList");
        PluginLoader instance = new PluginLoader();
        ArrayList expResult = null;
        ArrayList result = instance.GetProcessRequestList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetPostRequestList method, of class PluginLoader.
     */
    @Test
    public void testGetPostRequestList() {
        System.out.println("GetPostRequestList");
        PluginLoader instance = new PluginLoader();
        ArrayList expResult = null;
        ArrayList result = instance.GetPostRequestList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInstance method, of class PluginLoader.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        PluginLoader expResult = null;
        PluginLoader result = PluginLoader.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Load method, of class PluginLoader.
     */
    @Test
    public void testLoad() {
        System.out.println("Load");
        String args = "";
        PluginLoader instance = new PluginLoader();
        instance.Load(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
