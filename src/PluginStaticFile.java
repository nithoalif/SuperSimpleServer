import PluginsAndRequest.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Class PluginStaticFile
 *
 * Kelas yang digunakan untuk memproses file statis yang diminta oleh request
 * @author Nitho Alif Ibadurrahman / 13513072
 */
public final class PluginStaticFile implements ProcessRequest{
    private final String configFile = "staticfile.properties";
    private final String mimeFile = "mimetype.properties";
    private final Map mimeMap = new HashMap();
    private String DEFAULT_ROOT;
    
    /**
     * Konstruktor yang membaca file konfigurasi dan memuat tipe MIME yang disupport
     */
    public PluginStaticFile(){
        loadConfig();
        loadMIME();
    }
    
    /**
     * Melakukan pembacaan terhadap file konfigurasi
     */
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
    
    /**
     * Melakukan pembacaan terhadap file MIME yang disupport
     */
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
    
    /**
     * Prosedur process yang membaca dan memproses file yang diminta oleh klien
     * @param o Object request
     * @param m Map yang berisi response ke client
     */
    @Override
    public void process(Object o, Map m) {
        /* Get the Request Header */
        Request request = (Request)o;
        String requestedFile = request.getUrl();
        
        /* Open the requested file */
        File file = new File(DEFAULT_ROOT, requestedFile);
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
        ArrayList header = (ArrayList) m.get("head");
        
        String fileType = requestedFile.substring(requestedFile.lastIndexOf(".") + 1);
        String contentType = new String();
        if (mimeMap.containsKey(fileType)){
            contentType = mimeMap.get(fileType).toString();
        } else{
            contentType = "text/plain";
        }
        
        header.set(3, "Content-type: " + contentType);
        header.set(4, "Content-length: " + file.length());
        m.put("body", fileData);
    }
}
