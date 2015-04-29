/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * All code and works here are created by Satria Priambada and team
 * You are free to use and distribute the code
 * We do not take responsibilities for any damage caused by using this code
 */

import PluginsAndRequest.Request;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
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
public class PluginCompressTest {
    private final String configFile = "staticfile.properties";
    private final String mimeFile = "mimetype.properties";
    private final Map mimeMap = new HashMap();
    private String DEFAULT_ROOT;
    
    public PluginCompressTest(){
        loadConfig();
        loadMIME();
    }
    
    public void loadConfig() {
        /* Open the config file */
        Properties prop = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFile);
        
        /* Set the default web directory  */
        if (inputStream != null) {
            try {
                prop.load(inputStream);
                DEFAULT_ROOT = prop.getProperty("default_root");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void loadMIME(){
        /* Open the suported MIME list file */
        Properties mime = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(mimeFile);
        
        /* Insert suported MIME type in the MIME map */
        if (inputStream != null) {
            try {
                mime.load(inputStream);
                for (String name: mime.stringPropertyNames()){
                    mimeMap.put(name, mime.getProperty(name));    
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
     * Test of postprocess method, of class PluginCompress.
     */
    @Test
    public void testPostprocess() {
        
        System.out.println("postprocess");
        final Request request = mock(Request.class);
        
        when(request.getUrl()).thenCallRealMethod();
        Mockito.doCallRealMethod().when(request).setUrl(Mockito.any(String.class));
        //Set request to index.html
        Object o = request;
        request.setUrl("/index.html");
        Map m = new HashMap();
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
       
        
        /* Open the requested file */
        File file = new File(DEFAULT_ROOT, requestedFile);
        //System.out.println(file.toString());
        int fileLength = (int)file.length();
        FileInputStream fileIn = null;
        
        /* Process the requested file into an array of byte  */
        byte[] fileData = new byte[fileLength];
        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileIn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        /* Construct HTTP Response */
        header = (ArrayList) m.get("head");
        String fileType = requestedFile.substring(requestedFile.lastIndexOf(".") + 1);
        
        String contentType = mimeMap.get(fileType).toString();
        
        header.set(3, "Content-type: " + contentType);
        header.set(4, "Content-length: " + file.length());
        
        //System.out.println(header.toString());
        //m.put("head", header);
        m.put("body", fileData);
        //byte dari file sebelum kompresi
        byte[] beforeCompress = (byte[]) m.get("body");
        //System.out.println(beforeCompress.length);
        //kompresi file dengan method
        PluginCompress instance = new PluginCompress();
        instance.postprocess((Object)request, m);
        //byte dari file setelah kompresi
        byte[] compressedData = (byte[]) m.get("body");
        //System.out.println(compressedData.length);
        
        //harusnya jumlah setelah dikompress menjadi lebih kecil
        Assert.assertTrue(compressedData.length < beforeCompress.length );
    }
    
}
