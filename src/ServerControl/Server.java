package ServerControl;

import PluginsAndRequest.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class Server
 * 
 * Kelas yang digunakan untuk menjalankan SuperSimpleServer
 * @author Ibrohim Kholilul Islam / 13513090
 */

public class Server {
    private String host = "0.0.0.0";
    private int port = 8080;
    private final String configFile = "sss.properties";
    private String pluginsLocation = "src/";
    
    protected AsynchronousServerSocketChannel socket;
    protected Future<AsynchronousSocketChannel> futureClient;
    protected ArrayList<ClientServer> connectedUsers = new ArrayList <> ();
    protected ArrayList<RequestProcessor> threadPool = new ArrayList <> ();
    
    protected final Integer syncObject = 0;
    protected final Integer threadLock = 1;
    
    private int requestNumber = 0;
    protected int pointer = 0;

    boolean canceled = false;
    
    /**
     * Melakukan pembacaan terhadap file konfigurasi
     */
    public void loadConfig() {
        Properties prop = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFile);
        
        if (inputStream != null) {
            
            try {
                prop.load(inputStream);
                
                host = prop.getProperty("host");
                port = Integer.valueOf(prop.getProperty("port"));
                pluginsLocation = prop.getProperty("pluginslocation");
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Prosedur yang melakukan pembatalan terhadap jobs
     */
    public void cancelJobs() {
        canceled = true;
        try {
            socket.close();
        }
        catch (IOException thrownIOException) {
            thrownIOException.printStackTrace();
        }
    }

    /**
     * Prosedur yang membuka socket tertentu dan melakukan listen socket tersebut
     */
    protected void listenSocket(){
        try {
            SocketAddress listenTo = new InetSocketAddress(host, port);
            socket = AsynchronousServerSocketChannel.open();
            socket.bind(listenTo);
        }
        catch (Exception thrownException) {
            System.out.println("Error at Server::listenSocket");
            System.out.println(thrownException.getMessage());
        }
    }

    /**
     * Konstruktor untuk server
     */
    public Server() {
        listenSocket();
        loadConfig();
        
        System.out.println();
    }


    public void run(){
        // setup threads
        for (int i=0; i<4; i++) threadPool.add(new RequestProcessor());
        for (int i=0; i<4; i++) threadPool.get(i).start();

        PluginLoader plugins = PluginsAndRequest.PluginLoader.getInstance();
        plugins.Load(pluginsLocation);
        
        CompletionHandler<Integer, Object> handlerMessage =
            new CompletionHandler<Integer, Object>() {
              
            @Override
            public void completed(Integer result, Object attachment) {
                ClientServer client = (ClientServer) attachment;
                
                if (result.intValue() > 0) {
                    System.out.println("request" + (++requestNumber) + " assigned to " + pointer);
                    RequestProcessor thread = threadPool.get(pointer);
                    
                    /* Parse out method */
                    StringTokenizer parse = new StringTokenizer(client.getMessage());
                    String method = parse.nextToken().toUpperCase();
                    
                    if (method.equalsIgnoreCase("POST")){
                        Request request = new RequestPOST(client, client.getMessage(), requestNumber);
                        thread.addRequest(request);
                    } else{
                        Request request = new RequestGET(client, client.getMessage(), requestNumber);
                        thread.addRequest(request);
                    }
                    pointer = (pointer + 1) % threadPool.size();
                } else {
                    System.out.println("request" + (++requestNumber) + " have empty message ");
                }

            }
            
            @Override
            public void failed(Throwable e, Object attachment) {
                e.printStackTrace();
            }
        };
        
        CompletionHandler<AsynchronousSocketChannel, Object> handlerClient =
            new CompletionHandler<AsynchronousSocketChannel, Object>() {
            
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                synchronized (syncObject) {
                    new ClientServer(result, handlerMessage);
                    syncObject.notify();
                }
            }
            
            @Override
            public void failed(Throwable e, Object attachment) {
                e.printStackTrace();
            }
        };

        try {
            
            synchronized (syncObject)
            {
                while(!canceled) {
                    socket.accept("client", handlerClient);
                    syncObject.wait();
                }
            }

            System.out.println("--");
        }
        catch (Exception thrownException){
            thrownException.printStackTrace();
        }

    }

}
