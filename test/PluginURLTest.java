/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * All code and works here are created by Satria Priambada and team
 * You are free to use and distribute the code
 * We do not take responsibilities for any damage caused by using this code
 */

import PluginsAndRequest.Request;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Satria
 */
public class PluginURLTest {
    
    public PluginURLTest() {
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
     * Test of preprocess method, of class PluginURL.
     */
    @Test
    public void testPreprocess() {
        System.out.println("preprocess");
        final Request request = mock(Request.class);
        
        when(request.getUrl()).thenCallRealMethod();
        Mockito.doCallRealMethod().when(request).setUrl(Mockito.any(String.class));
        
        Object o = request;
        request.setUrl("/");
        Map m = new HashMap();
        PluginURL instance = new PluginURL();
        instance.preprocess((Object)request, m);
        Assert.assertEquals("/index.html",request.getUrl());
        
        request.setUrl("/aaaa/");
        instance.preprocess((Object)request, m);
        Assert.assertEquals("/aaaa/index.html",request.getUrl());
        
        request.setUrl("/aaaa/aaaa/aaaa/");
        instance.preprocess((Object)request, m);
        Assert.assertEquals("/aaaa/aaaa/aaaa/index.html",request.getUrl());
    }
    
}
