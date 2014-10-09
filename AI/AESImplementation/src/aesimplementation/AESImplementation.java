/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesimplementation;

import aesimplementation.Cipher;
import aesimplementation.FileSystem;
import java.io.UnsupportedEncodingException;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Petricioiu
 */
public class AESImplementation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedEncodingException {

        LeFrame leFrame = new LeFrame();
        leFrame.setVisible(true);
        //THIaRO++oA3vv7zvvqVoNu+/j+++ue++iO+/v27vv48=
//        FileSystem fileSystem = FileSystem.getInstance();
//        fileSystem.setFilePath("C:\\Users\\Petricioiu\\Documents\\develop\\facultate\\AI\\AESImplementation\\sBoxMatrix.txt");
//        byte[][] sBoxMatrix = fileSystem.readSBoxMatrix();
//
//        Cipher cipher = new Cipher();
//        cipher.setRCon();
//        cipher.setSBox(sBoxMatrix);
//        cipher.setLeGivenMatrix(false);
//        System.out.println(cipher.decryptCipheredText(new String(Base64.decodeBase64("THIaRO++oA3vv7zvvqVoNu+/j+++ue++iO+/v27vv48=".getBytes("UTF-8"))), "1234567890123456"));
//        System.out.println(cipher.decryptCipheredText(new String(Base64.decodeBase64("THIaRO++oA3vv7zvvqVoNu+/j+++ue++iO+/v27vv48=".getBytes("UTF-8"))), "1234567890123456"));

    }

}
