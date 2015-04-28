/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * All code and works here are created by Satria Priambada and team
 * You are free to use and distribute the code
 * We do not take responsibilities for any damage caused by using this code
 */



import PluginsAndRequest.*;
import java.io.File;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class 
 *
 * Kelas yang digunakan untuk 
 *
 * @author Satria Priambada
 * @version 0.1
 */
public class PluginStaticFile implements ProcessRequest{
    
    @Override
    public void process(Object o) {
        try {
            String requested = o.getClass().getField("url").toString();
            System.out.println(requested);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(PluginStaticFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(PluginStaticFile.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public void procesx(Object o) {
        System.out.println("aaaa");
    }
    
}
