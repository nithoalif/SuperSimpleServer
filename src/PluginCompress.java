/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * All code and works here are created by Satria Priambada and team
 * You are free to use and distribute the code
 * We do not take responsibilities for any damage caused by using this code
 */



import PluginsAndRequest.*;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * Class 
 *
 * Kelas yang digunakan untuk 
 *
 * @author Satria Priambada
 * @version 0.1
 */
public class PluginCompress implements PostRequest{

    @Override
    public void postprocess(Object o, Map m) {
        byte[] dataToCompress = (byte[])m.get("body");
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    }

}
