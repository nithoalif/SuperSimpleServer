/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * All code and works here are created by Satria Priambada and team
 * You are free to use and distribute the code
 * We do not take responsibilities for any damage caused by using this code
 */
package PluginsAndRequest;

import ServerControl.ClientServer;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Satria
 */
public class RequestNGTest {
    
    public RequestNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of getMessage method, of class Request.
     */
    @Test
    public void testGetMessage() {
        System.out.println("getMessage");
        Request instance = null;
        String expResult = "";
        String result = instance.getMessage();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUrl method, of class Request.
     */
    @Test
    public void testGetUrl() {
        System.out.println("getUrl");
        Request instance = null;
        String expResult = "";
        String result = instance.getUrl();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getClientServer method, of class Request.
     */
    @Test
    public void testGetClientServer() {
        System.out.println("getClientServer");
        Request instance = null;
        ClientServer expResult = null;
        ClientServer result = instance.getClientServer();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of execute method, of class Request.
     */
    @Test
    public void testExecute() {
        System.out.println("execute");
        Request instance = null;
        String expResult = "";
        String result = instance.execute();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getState method, of class Request.
     */
    @Test
    public void testGetState() {
        System.out.println("getState");
        Request instance = null;
        Request.enumState expResult = null;
        Request.enumState result = instance.getState();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSerial method, of class Request.
     */
    @Test
    public void testGetSerial() {
        System.out.println("getSerial");
        Request instance = null;
        int expResult = 0;
        int result = instance.getSerial();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
