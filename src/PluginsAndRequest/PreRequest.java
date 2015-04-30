package PluginsAndRequest;

import java.util.Map;

/**
 * Interface PreRequest
 * 
 * Interface untuk plugin PreRequest
 * @author Bimo Aryo Tyasono / 13513075
 */
public interface PreRequest {
    /**
     * Prosedur preprocess
     * @param o Object request
     * @param m Map yang berisi response ke client
     */
    public void preprocess(Object o, Map m);
}
