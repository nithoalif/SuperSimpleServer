package PluginsAndRequest;


import ServerControl.ClientServer;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ibrohim on 26/04/15.
 */
public class Request {
    private ClientServer client;
    protected String message;
    protected String url;

    private enumState state;
    private int serial;

    public enum enumState {
        enqueue,
        processed
    }
    
    public Request(ClientServer _client, String _message, int _serial) {
        System.out.println(_message);
        client = _client;
        message = _message;
        state = enumState.enqueue;
        serial = _serial;
        StringTokenizer parse = new StringTokenizer(message);
        //parse out method
        String method = parse.nextToken().toUpperCase();
        //parse out file requested
        url = parse.nextToken().toLowerCase();
    }
    
    public void setMessage(String _message) {
        message = _message;
    }

    public void setUrl(String _url) {
        url = _url;
    }
    
    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }
    
    public ClientServer getClientServer() {
        return client;
    }
    
    public String execute(){
        state = enumState.processed;
        Map result = new HashMap();
        PluginLoader plugins = PluginLoader.getInstance();
        //Menjalankan prerequest plugin
        for(Object o : plugins.GetProcessRequestList()){
            try {
                System.out.println(o.getClass().getName());
                Method m = o.getClass().getDeclaredMethod("preprocess", Object.class, Map.class); 
                m.invoke(o, (Object)this, result);
            } catch (Exception ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }
        //Menjalankan process request plugin
        for(Object o : plugins.GetProcessRequestList()){
            try {
                System.out.println(o.getClass().getName());
                Method m = o.getClass().getDeclaredMethod("process", Object.class, Map.class); 
                m.invoke(o, (Object)this, result);
            } catch (Exception ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }
        //menjalankan post request plugin
        for(Object o : plugins.GetPostRequestList()){
            try {
                System.out.println(o.getClass().getName());
                Method m = o.getClass().getDeclaredMethod("postprocess", Object.class, Map.class); 
                m.invoke(o, (Object)this, result);
            } catch (Exception ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }
        
        
        String body = new String((byte[])result.get("body"));
        return result.get("header").toString() + "\n" + body + "\n";
    }

    public enumState getState() {
        return state;
    }

    public int getSerial() {
        return serial;
    }

}
