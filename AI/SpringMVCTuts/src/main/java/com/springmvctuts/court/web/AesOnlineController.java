/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.springmvctuts.court.web;

import com.springtuts.springmvctuts.Cipher;
import com.springtuts.springmvctuts.FileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Petricioiu
 */
@Controller
public class AesOnlineController {

    
    public AesOnlineController() {
        
    }
    
    @RequestMapping (value = "/aes", method = RequestMethod.GET)
    public String getAes() {
        
        return "aesOnline";
    }
    
    @RequestMapping (value = "aes/encrypt", method = RequestMethod.GET)
    public String encrypt(@RequestParam("plainText") String plainText, @RequestParam("key") String key, Model model) {
       
        FileSystem fileSystem = FileSystem.getInstance();
        fileSystem.setFilePath("C:\\Users\\Petricioiu\\Documents\\develop\\facultate\\AI\\AESImplementation\\sBoxMatrix.txt");
        byte[][] sBoxMatrix = fileSystem.readSBoxMatrix();
        
        Cipher cipher = new Cipher();
        cipher.setRCon();
        cipher.setSBox(sBoxMatrix);
        cipher.setLeGivenMatrix(false);
        cipher.buildState(plainText);
        cipher.buildCipherKey(key);
        cipher.encrypt();
        model.addAttribute("cipherText", cipher.getCipherText());
        
        return "aesOnline";
    }
    
    @RequestMapping (value = "aes/decrypt", method = RequestMethod.GET) 
    public String decrypt(@RequestParam("plainText") String base64String, @RequestParam("key") String key, Model model) {
        FileSystem fileSystem = FileSystem.getInstance();
        fileSystem.setFilePath("C:\\Users\\Petricioiu\\Documents\\develop\\facultate\\AI\\AESImplementation\\sBoxMatrix.txt");
        byte[][] sBoxMatrix = fileSystem.readSBoxMatrix();
        
        Cipher cipher = new Cipher();
        cipher.setRCon();
        cipher.setSBox(sBoxMatrix);
        cipher.setLeGivenMatrix(false);
        
        model.addAttribute("decryptedText", cipher.decryptCipheredText(base64String, key));
        return "aesOnline";
    }
    
}
