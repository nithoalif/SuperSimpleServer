package PluginsAndRequest;

import PluginsAndRequest.Request;
import java.io.IOException;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibrohim on 26/04/15.
 */
public class RequestProcessor extends Thread {

    private Boolean state = false;

    private List <Request> listOfRequest = new ArrayList<>();

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
                            String response = request.execute();
                            request.getClientServer().sendMessage(response);
                            System.out.println("response of request #" + request.getSerial() + " sent");
                            i = i + 1;
                        } else if (requestState == Request.enumState.processed) {
                            if (request.getClientServer().isWriteComplete()) {
                                try {
                                    request.getClientServer().bury();
                                } catch (IOException e) {
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
                    System.out.println("notified");
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    return;
                }
            }
        }

    }
}
