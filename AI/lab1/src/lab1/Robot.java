/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Petricioiu
 */
public class Robot {
    
    private String name;
    private String inputText;

    public Robot(String name) {
        this.name = name;
    }
    
    public void start(Person person) {
        System.out.println("Hello there, my name is " + name);
        System.out.println("What is your name? :)");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            inputText = br.readLine();
            if (inputText.toLowerCase().contains("my name is") || 
                !inputText.contains(" ")) {
                if (inputText.contains(" "))
                    person.setName(inputText.substring(inputText.toLowerCase().indexOf("my name is") + 10, inputText.length()));
                else person.setName(inputText);
                System.out.println("Nice to meet you, " + person.getName());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
