/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtuts.springmvctuts;

import com.springtuts.springmvctuts.Cipher;
import com.springtuts.springmvctuts.FileSystem;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Petricioiu
 */
public class AESImplementation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        FileSystem fileSystem = FileSystem.getInstance();
        fileSystem.setFilePath("C:\\Users\\Petricioiu\\Documents\\develop\\facultate\\AI\\AESImplementation\\sBoxMatrix.txt");
        byte[][] sBoxMatrix = fileSystem.readSBoxMatrix();
        
        Cipher cipher = new Cipher();
        cipher.setRCon();
     //   cipher.setDefaultCipherKey();
      //  cipher.setDefaultState();
        cipher.buildCipherKey("1234567890123456");
        cipher.buildState("abcdefghijklmnop");
        cipher.setSBox(sBoxMatrix);
        cipher.setLeGivenMatrix(false);
        cipher.showStateInHex();
        
       // cipher.encrypt();
        String base64String = "THIaRO++oA3vv7zvvqVoNu+/j+++ue++iO+/v27vv48=";
        System.out.println(base64String);
        System.out.println(cipher.decryptCipheredText(base64String, "1234567890123456")); 
       // cipher.decrypt();
        
    }

}
