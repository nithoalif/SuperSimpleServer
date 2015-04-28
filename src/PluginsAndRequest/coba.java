/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * All code and works here are created by Satria Priambada and team
 * You are free to use and distribute the code
 * We do not take responsibilities for any damage caused by using this code
 */

package PluginsAndRequest;

/**
 * Class 
 *
 * Kelas yang digunakan untuk 
 *
 * @author Satria Priambada
 * @version 0.1
 */
public class coba {
    public static void main(String args[]){
        PluginLoader P1 = new PluginLoader();
        P1.Load("C:/Users/Satria/Documents/NetBeansProjects/ServerOOP/src");
        System.out.println(P1.GetPreRequestList().get(0).toString() );
        //System.out.println(Integer.toString());
    }

}
