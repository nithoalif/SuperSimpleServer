package PluginsAndRequest;

import ServerControl.ClientServer;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class Request
 * 
 * Kelas yang digunakan untuk mengenkapsulaasi request dari client
 * @author Ibrohim Kholilul Islam / 13513090
 */
public abstract class Request {
    protected ClientServer client;
    protected String message;
    protected String url;
    protected enumState state;
    protected int serial;

    public abstract byte[] execute();
    
    /**
     * Enum enumState 
     * Merupakan status dari request yang sedang diproses atau sedang menunggu
     */
    public enum enumState {
        enqueue,
        processed
    }
    
    /**
     * Mengambil client dari Request
     * @return ClientServer
     */
    public ClientServer getClientServer() {
        return client;
    }
    
    /**
     * Mengambil pesan dari Request
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Mengambil url dari Request
     * @return url
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * Mengambil state dari Request
     * @return state
     */
    public enumState getState() {
        return state;
    }
    
    /**
     * Mengambil urutan dari Request
     * @return serial
     */
    public int getSerial() {
        return serial;
    }

    /**
     * prosedur untuk mengatur isi pesan dari Request
     * @param _message pesan yang akan diisikan ke message
     */
    public void setMessage(String _message) {
        message = _message;
    }

    /**
     * prosedur untuk mengatur isi url dari Request
     * @param _url url yang akan diisikan ke url
     */
    public void setUrl(String _url) {
        url = _url;
    }
    
    /**
     * Membuat header untuk request
     * @param _header array string header
     */
    public void ConstructHeader(ArrayList _header){
        /* Costruct Response Header */
        _header.add("HTTP/1.1 200 OK");
        _header.add("Server: RadioClub SuperSimpleServer");
        _header.add("Date: " + new Date());
        _header.add("Content-type: plain/text");
        _header.add("Content-length: 0");
    }
}
