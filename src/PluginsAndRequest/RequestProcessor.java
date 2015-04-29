package PluginsAndRequest;

import PluginsAndRequest.Request;
import ServerControl.ClientServer;
import java.io.IOException;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class RequestProcessor
 *
 * Kelas yang digunakan untuk mengatur alur pemrosesan request dengan thread
 *
 */
public class RequestProcessor extends Thread {
    private List <Request> listOfRequest = new ArrayList<>();
    private Boolean state = false;

    public int getRequestCount(){
        return listOfRequest.size();
    }

    public void addRequest(Request request){
        synchronized(state) {
            listOfRequest.add(request);
            state.notify();
        }
    }

    @Override
    public void run(){
        synchronized(state) {
            while (true) {
                int i = 0, size = listOfRequest.size();
                while (i < size) {

                    Request request = listOfRequest.get(i);
                    try {
                        Request.enumState requestState = request.getState();

                        if (requestState == Request.enumState.enqueue) {
                            byte[] response = request.execute();
                            request.getClientServer().sendMessage(response);
                            //System.out.println("response of request #" + request.getSerial() + " sent");
                            i = i + 1;
                        
                        } else if (requestState == Request.enumState.processed) {
                            if (request.getClientServer().isWriteComplete()) {
                                try {
                                    request.getClientServer().bury();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                listOfRequest.remove(i);
                                size = size - 1;
                            }
                        }
                    } catch (CharacterCodingException e) {
                        e.printStackTrace();
                    }
                }

                if (listOfRequest.size() == 0) try {
                    state.wait();
                    //System.out.println("notified");
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    return;
                }
            }
        }
    }
}
