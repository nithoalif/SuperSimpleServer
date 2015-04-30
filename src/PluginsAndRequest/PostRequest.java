package PluginsAndRequest;

import java.util.Map;

/**
 * Interface PostRequest
 * 
 * Interface untuk plugin PostRequest
 * @author Bimo Aryo Tyasono / 13513075
 */
public interface PostRequest {

    /**
     * Prosedur postprocess
     * @param o Object request
     * @param m Map yang berisi response ke client
     */
    public void postprocess(Object o, Map m);
}
