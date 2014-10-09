/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.springtuts.springmvctuts;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Petricioiu
 */
public class FileSystem {
    
    private static FileSystem instance = null;
    
    
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private FileSystem() {
    }
    
    public static FileSystem getInstance() {
        if (instance == null) {
            instance = new FileSystem();
        }
        return instance;
    }
    
    public byte[][] readSBoxMatrix() {
        byte[][] sBoxMatrix = new byte[16][16];
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.filePath)));
            try {
                String line;
                int i = 0, j = 0;
                while ((line = br.readLine()) != null) {
                    StringTokenizer st = new StringTokenizer(line);
                    while (st.hasMoreElements()) {
                        sBoxMatrix[i][j++] = (byte) Integer.parseInt(st.nextToken().toString(), 16);
                    }
                    j = 0;
                    ++i;
                }
            } catch (IOException ex) {
                Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return sBoxMatrix;
    }
    
    public String readPassword() {
        System.out.println("Password (16 characters): ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String pass = "";
        try {
            while(true) {
            pass = br.readLine();
            if (pass.length() == 16)
                break;
            System.out.print("Password (16 characters): ");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return pass;
    }
    
    public String readPlainText() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String text = "";
        try {
            System.out.print("Plain text: ");
            text = br.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return text;
    }
    
}
