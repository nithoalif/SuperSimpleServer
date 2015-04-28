package PluginsAndRequest;


import ServerControl.ClientServer;
import ServerControl.Server;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ibrohim on 26/04/15.
 */
public class Request {
    private ClientServer client;
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
        url = _message;
        state = enumState.enqueue;
        serial = _serial;
    }

    public String getMessage() {
        return url;
    }

    public ClientServer getClientServer() {
        return client;
    }

    public String execute(){
        state = enumState.processed;
        for(Object o : Server.plugins.GetProcessRequestList()){
            try {
                Method m = o.getClass().getDeclaredMethod("process", Object.class); 
                m.invoke(o, (Object)getMessage());
                
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        return "";
    }

    public enumState getState() {
        return state;
    }

    public int getSerial() {
        return serial;
    }

}
