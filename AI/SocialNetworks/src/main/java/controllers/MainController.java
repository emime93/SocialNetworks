/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Petricioiu
 */
@Controller
@RequestMapping("/")    
public class MainController {

    public MainController() {
        int x = 2;
        x ++;
    }
    
    
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() {
        return "main/index";
    }
}
