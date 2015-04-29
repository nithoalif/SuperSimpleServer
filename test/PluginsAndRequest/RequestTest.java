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
import java.util.ArrayList;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

/**
 *
 * @author Satria
 */
public class RequestTest {
    
    public RequestTest() {
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
     * Test of ConstructHeader method, of class Request.
     */
    @Test
    public void testConstructHeader() {
        System.out.println("ConstructHeader");
        ArrayList header = new ArrayList<>();
        Request myRequest = Mockito.mock(Request.class, Mockito.CALLS_REAL_METHODS);
        myRequest.ConstructHeader(header);
        assertEquals(header.get(0),"HTTP/1.1 200 OK");
        assertEquals(header.get(1),"Server: RadioClub SuperSimpleServer");
        assertEquals(header.get(3),"Content-type: plain/text");
        assertEquals(header.get(4),"Content-length: 0");
    }
    
}
