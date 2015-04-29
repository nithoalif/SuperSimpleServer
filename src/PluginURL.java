import PluginsAndRequest.*;
import java.util.Map;

/**
 * Class PluginURL
 *
 * Kelas yang digunakan untuk memodifikasi url yang direquest bila tidak ada file (berakhir dengan karakter '/')
 *
 */
public class PluginURL implements PreRequest{

    @Override
    public void preprocess(Object o, Map m) {
        /* Get the Request Header */
        Request request = (Request)o;
        String requestedFile = request.getUrl();
        
        /* Modify the requested file if the url end with character '/' */
        int panjangReqFile = requestedFile.length();
        if (requestedFile.charAt( panjangReqFile - 1) == '/'){
            request.setUrl( "/index.html" );
        }
    }
}
