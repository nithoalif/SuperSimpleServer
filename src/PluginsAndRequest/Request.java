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
 * Class Request
 * 
 * Kelas yang digunakan untuk mengenkapsulaasi request dari client
 * 
 */
public class Request {
    private ClientServer client;
    protected String message;
    protected String url;
    private enumState state;
    private int serial;

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
    
    public enum enumState {
        enqueue,
        processed
    }
    
    public ClientServer getClientServer() {
        return client;
    }
    
    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }
    
    public enumState getState() {
        return state;
    }

    public int getSerial() {
        return serial;
    }

    public void setMessage(String _message) {
        message = _message;
    }

    public void setUrl(String _url) {
        url = _url;
    }
    
    public byte[] execute(){
        state = enumState.processed;
        Map result = new HashMap();
        PluginLoader plugins = PluginLoader.getInstance();
        /* Execute PreRequest Plugin */
        for (Object o : plugins.GetPreRequestList()){
            try {
                System.out.println(o.getClass().getName());
                Method m = o.getClass().getDeclaredMethod("preprocess", Object.class, Map.class); 
                m.invoke(o, (Object)this, result);
            } catch (Exception e) {
                e.printStackTrace();
            }    
        }
        /* Execute ProcessRequest Plugin */
        for (Object o : plugins.GetProcessRequestList()){
            try {
                System.out.println(o.getClass().getName());
                Method m = o.getClass().getDeclaredMethod("process", Object.class, Map.class); 
                m.invoke(o, (Object)this, result);
            } catch (Exception e) {
                e.printStackTrace();
            }   
        }
        /* Execute PostRequest Plugin */
        for (Object o : plugins.GetPostRequestList()){
            try {
                System.out.println(o.getClass().getName());
                Method m = o.getClass().getDeclaredMethod("postprocess", Object.class, Map.class); 
                m.invoke(o, (Object)this, result);
            } catch (Exception e) {
                e.printStackTrace();
            }   
        }
        
        /* Close the connection */
        String header = new String();
        ArrayList<String> headerList = (ArrayList)result.get("head");
        headerList.add("Connection: close");
        for(String headerItem : headerList){
            header += headerItem + "\n";
        }
        header += "\n";
        
        /* Send the array of byte (HTTP Content) */
        byte[] head = header.getBytes();
        byte[] body = (byte[])result.get("body");
        byte[] response = new byte[head.length + body.length];
        System.arraycopy(head, 0, response, 0, head.length);
        System.arraycopy(body, 0, response, head.length, body.length);
        return response;
    }
}
