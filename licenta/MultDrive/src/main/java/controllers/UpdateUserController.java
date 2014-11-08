/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import entities.User;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Petricioiu
 */
@Controller
public class UpdateUserController {
    
    @Autowired
    @Qualifier("User")
    User user;
    
    @RequestMapping(value = "/welcome/update_user", method = RequestMethod.POST)
    public String updateUser(@RequestParam("username") String username, HttpServletRequest request) {
        user.setUsername(username);
        request.getSession().setAttribute("utilizator", user);
        return "main/index";
    }
}
