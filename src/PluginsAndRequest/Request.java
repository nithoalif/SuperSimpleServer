package PluginsAndRequest;

import ServerControl.ClientServer;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class Request
 * 
 * Kelas yang digunakan untuk mengenkapsulaasi request dari client
 * 
 */
public abstract class Request {
    protected ClientServer client;
    protected String message;
    protected String url;
    protected enumState state;
    protected int serial;

    public abstract byte[] execute();
    
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
    
    protected void ConstructHeader(ArrayList _header){
        /* Costruct Response Header */
        _header.add("HTTP/1.1 200 OK");
        _header.add("Server: RadioClub SuperSimpleServer");
        _header.add("Date: " + new Date());
        _header.add("Content-type: plain/text");
        _header.add("Content-length: 0");
    }
}
