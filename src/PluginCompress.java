import PluginsAndRequest.*;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.zip.*;

/**
 * Class PluginCompress
 *
 * Kelas yang digunakan untuk memampatkan (compress) konten yang akan dikirim ke client
 *
 */
public class PluginCompress implements PostRequest{

    @Override
    public void postprocess(Object o, Map m) {
        try{
            /* Original data */
            byte[] dataToCompress = (byte[])m.get("body");
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(dataToCompress.length);
            
            /* Compress original data using GZip (Java API) */
            GZIPOutputStream zipStream = new GZIPOutputStream(byteStream);
            zipStream.write(dataToCompress);
            zipStream.flush();
            zipStream.close();
            byteStream.close();
            
            /* Resulted compressed Data */
            byte[] compressedData = byteStream.toByteArray(); 
            m.replace("body", compressedData);
            
            /* Add compression type to the Response Header */
            ArrayList<String> headerList = (ArrayList)m.get("head");
            headerList.set(4, "Content-length: " + compressedData.length);
            headerList.add("Content-Encoding: gzip");
        } catch(Exception e){
            e.printStackTrace();
        }
        
    }

}
