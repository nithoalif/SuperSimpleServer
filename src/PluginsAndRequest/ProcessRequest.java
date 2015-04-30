package PluginsAndRequest;

import java.util.Map;

/**
 * Interface ProcessRequest
 * 
 * Interface untuk plugin ProcessRequest
 * @author Satria Priambada / 13513034
 */
public interface ProcessRequest {

    /**
     * Prosedur process
     * @param o Object request
     * @param m Map yang berisi response ke client
     */
    public void process(Object o, Map m);
}
