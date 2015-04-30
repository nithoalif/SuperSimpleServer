/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * All code and works here are created by Satria Priambada and team
 * You are free to use and distribute the code
 * We do not take responsibilities for any damage caused by using this code
 */
package PluginsAndRequest;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test untuk plugin loader
 * @author Satria Priambada /13513034
 */
public class PluginLoaderTest {
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        PrintStream ps =  new PrintStream(new FileOutputStream(FileDescriptor.out));
        PrintStream es =  new PrintStream(new FileOutputStream(FileDescriptor.err));

        System.setOut(ps);
        System.setErr(es);
    }
    
    private void assertEmptyOutput(){
        String testOutput = outContent.toString();
        Assert.assertEquals("", testOutput);
        String testError = errContent.toString();
        Assert.assertEquals("", testError);
    }
    
    /**
     * Test of Load method, of class PluginLoader.
     */
    @Test
    public void testLoad() {
        System.out.println("Load");
        String args = "C:\\Users\\Satria\\Documents\\NetBeansProjects\\ServerOOP\\src";
        PluginLoader instance = PluginsAndRequest.PluginLoader.getInstance();
        instance.Load(args);
        String expected1 = "plugin prerequest loaded : PluginURL";
        String expected2 = "plugin postrequest loaded : PluginCompress";
        String expected3 = "plugin request loaded : PluginStaticFile";
        String SOutput = outContent.toString();
        assertTrue( SOutput.contains(expected1)  );
        assertTrue( SOutput.contains(expected2)  );
        assertTrue( SOutput.contains(expected3)  );
        // TODO review
    }
    
}
