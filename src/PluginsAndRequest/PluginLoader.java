package PluginsAndRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Class PluginLoader
 *
 * Kelas yang digunakan untuk melisting dan meload plugin 
 *
 */
public class PluginLoader {
    private ArrayList<Object> PreRequestList = new ArrayList<>();
    private ArrayList<Object> ProcessRequestList = new ArrayList<>();
    private ArrayList<Object> PostRequestList = new ArrayList<>();
    private static PluginLoader instance;

    public static PluginLoader getInstance(){
        if (instance == null){
            instance = new PluginLoader();
        }
        return instance;
    }
    
    public ArrayList GetPreRequestList(){
        return PreRequestList;
    }
    
    public ArrayList GetProcessRequestList(){
        return ProcessRequestList;
    }
    
    public ArrayList GetPostRequestList(){
        return PostRequestList;
    }

    public void Load(String args){
        try {
            /* Listing in the directory */
            File f = new File(args);
            String fileName[] = f.list();
            for(String iFile :fileName){
                String[] parts;
                parts = iFile.split(Pattern.quote("."));
                String namaDepanFile = parts[0];
                try {
                   Class clsPlugin = Class.forName(namaDepanFile);                     
                   Class[] clsInterfaces = clsPlugin.getInterfaces();
                   //pada kasus sebuah kelas mengimplementasikan lebih dari 1 interface perlu dilakukan iterasi ke seluruh interfacenya
                   for (Class iClsInterfaces : clsInterfaces){
                       /* Process PreRequest Plugin */
                       if (iClsInterfaces.getName().equals("PluginsAndRequest.PreRequest")){
                           Object o = clsPlugin.newInstance();
                           System.out.println("plugin prerequest loaded : " + namaDepanFile);
                           PreRequestList.add(o);
                       }
                       /* Process ProcessRequest Plugin */
                       else if (iClsInterfaces.getName().equals("PluginsAndRequest.ProcessRequest")){
                           Object o = clsPlugin.newInstance();
                           System.out.println("plugin request loaded : " + namaDepanFile);
                           ProcessRequestList.add(o);
                       }
                       /* Process PostRequest Plugin */
                       else if( iClsInterfaces.getName().equals("PluginsAndRequest.PostRequest")){
                           Object o = clsPlugin.newInstance();
                           System.out.println("plugin postrequest loaded : " + namaDepanFile);
                           PostRequestList.add(o);
                       }
                   }
                } catch (ClassNotFoundException e) {
                     // do nothing
                } catch (Exception e){
                    System.out.println("Error at PluginLoader::Load");
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
           System.out.println("Error at PluginLoader::Load");
           System.err.println(e);
        }
     }
}
