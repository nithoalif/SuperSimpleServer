/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * All code and works here are created by Satria Priambada and team
 * You are free to use and distribute the code
 * We do not take responsibilities for any damage caused by using this code
 */

import java.util.Map;

/**
 * Class 
 *
 * Kelas yang digunakan untuk 
 *
 * @author Satria Priambada
 * @version 0.1
 */

import PluginsAndRequest.PreRequest;
import PluginsAndRequest.Request;
public class PluginURL implements PreRequest{

    @Override
    public void preprocess(Object o, Map m) {
        Request request = (Request)o;
        String requestedFile = request.getUrl();
        int panjangReqFile = requestedFile.length();
        if (requestedFile.charAt( panjangReqFile - 1) ==  '/' ){
            request.setUrl( "/index.html" );
        }
        //Bila bukan link kosong maka berikan pada PluginStaticFile
    }
}
