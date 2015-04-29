package PluginsAndRequest;


import ServerControl.ClientServer;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
        client = _client;
        message = _message;
        state = enumState.enqueue;
        serial = _serial;
        StringTokenizer parse = new StringTokenizer(message);
        //parse out method
        String method = parse.nextToken().toUpperCase();
        //parse out file requested
        try {
            url = parse.nextToken().toLowerCase();
        } catch (Exception e) {
            System.out.println("------");
            System.out.println(_message);
            e.printStackTrace();
            System.out.println("------");
        }
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
    
    public byte[] execute(){
        state = enumState.processed;
        Map result = new HashMap();
        PluginLoader plugins = PluginLoader.getInstance();
        //Menjalankan prerequest plugin
        for(Object o : plugins.GetPreRequestList()){
            try {
                //System.out.println(o.getClass().getName());
                Method m = o.getClass().getDeclaredMethod("preprocess", Object.class, Map.class); 
                m.invoke(o, (Object)this, result);
            } catch (Exception ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }
        //Menjalankan process request plugin
        for(Object o : plugins.GetProcessRequestList()){
            try {
                //System.out.println(o.getClass().getName());
                Method m = o.getClass().getDeclaredMethod("process", Object.class, Map.class); 
                m.invoke(o, (Object)this, result);
            } catch (Exception ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }
        //menjalankan post request plugin
        for(Object o : plugins.GetPostRequestList()){
            try {
                //System.out.println(o.getClass().getName());
                Method m = o.getClass().getDeclaredMethod("postprocess", Object.class, Map.class); 
                m.invoke(o, (Object)this, result);
            } catch (Exception ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }
        
        
        String header = new String();
        
        ArrayList<String> headerList = (ArrayList)result.get("head");
        headerList.add("Connection: close");
        for(String headerItem : headerList){
            header += headerItem + "\n";
        }
        header += "\n";
        
        byte[] head = header.getBytes();
        byte[] body = (byte[])result.get("body");
        byte[] response = new byte[head.length + body.length];
        System.arraycopy(head, 0, response, 0, head.length);
        System.arraycopy(body, 0, response, head.length, body.length);
        return response;
    }

    public enumState getState() {
        return state;
    }

    public int getSerial() {
        return serial;
    }

}
