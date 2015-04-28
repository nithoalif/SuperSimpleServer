/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * All code and works here are created by Satria Priambada and team
 * You are free to use and distribute the code
 * We do not take responsibilities for any damage caused by using this code
 */



import PluginsAndRequest.*;
import ServerControl.Server;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
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
public final class PluginStaticFile implements ProcessRequest{
    private final String configFile = "staticfile.properties";
    private String DEFAULT_ROOT;
    
    public void loadConfig() {
        Properties prop = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFile);
        
        if (inputStream != null) {
            
            try {
                prop.load(inputStream);
                
                DEFAULT_ROOT = prop.getProperty("default_root");
                
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public PluginStaticFile(){
        loadConfig();
    }
    
    @Override
    public void process(Object o, Map m) {
        Request request = (Request)o;
        String requestedFile = request.getUrl();
        
        //create file object
        File file = new File(DEFAULT_ROOT, requestedFile);
        //get length of file
        int fileLength = (int)file.length();
        FileInputStream fileIn = null;
        
        //create byte array to store file data
        byte[] fileData = new byte[fileLength];

        try {
          //open input stream from file
          fileIn = new FileInputStream(file);
          //read file into byte array
          fileIn.read(fileData);
        } catch (IOException ex) {
            Logger.getLogger(PluginStaticFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileIn.close();//close file input stream
            } catch (IOException ex) {
                Logger.getLogger(PluginStaticFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String body = new String(fileData);
        //send HTTP headers
        m.put("header", "HTTP/1.0 200 OK\n"
                + "Server: RadioClub SuperSimpleServer\n"
                + "Date: " + new Date() + "\n"
                + "Content-type: text/html\n"
                + "Content-length: " + file.length() + "\n"
                + "Connection: close\n");
        m.put("body", body);
    }
}
