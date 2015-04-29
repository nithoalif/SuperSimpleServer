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
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Satria
 */
public class RequestPOSTTest {
    
    public RequestPOSTTest() {
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

    @Test
    public void testExecute() {
        System.out.println("execute");
        
        final ClientServer CS = mock(ClientServer.class);
        when(CS.getMessage()).thenReturn("POST /index2.html HTTP/1.1");
        RequestGET instance = new RequestGET(CS, CS.getMessage(), 1);
        
        PostRequest pluginTester = new PostRequest(){
            @Override
            public void postprocess(Object o, Map m) {
                Request req = (Request) o;
                m.put("body", new byte[0]);
                assertEquals(req.url, "/index2.html");
            }
        };
        
        PluginLoader.getInstance().GetPostRequestList().add(pluginTester);
        instance.execute();
    }
    
}
