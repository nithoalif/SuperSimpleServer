/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * All code and works here are created by Satria Priambada and team
 * You are free to use and distribute the code
 * We do not take responsibilities for any damage caused by using this code
 */

import PluginsAndRequest.Request;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Satria
 */
public class PluginStaticFileTest {
    
    public PluginStaticFileTest() {
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
     * Test of process method, of class PluginStaticFile.
     */
    @Test
    public void testProcess() {
        System.out.println("process");
        final Request request = mock(Request.class);
        
        when(request.getUrl()).thenCallRealMethod();
        Mockito.doCallRealMethod().when(request).setUrl(Mockito.any(String.class));
        
        Map m = new HashMap();
        //Set request to index.html
        Object o = request;
        request.setUrl("/index.html");
        ArrayList header  = new ArrayList<String>();
        /* Costruct Response Header */
        header.add("HTTP/1.1 200 OK");
        header.add("Server: RadioClub SuperSimpleServer");
        header.add("Date: " + new Date());
        header.add("Content-type: plain/text");
        header.add("Content-length: 0");
        
        m.put("head", header);
        //System.out.println(header.toString());
        String requestedFile = request.getUrl();
        PluginStaticFile instance = new PluginStaticFile();
        instance.process(o, m);
        //assert bahwa panjang Content-length tidak lagi 0
        assertNotSame("Content-length: 0",header);
    }
    
}
