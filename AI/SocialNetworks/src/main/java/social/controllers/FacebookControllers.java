/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package social.controllers;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.internal.http.HttpRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Petricioiu
 */
@Controller
public class FacebookControllers {

    private Facebook facebook;
    
    @RequestMapping(value = "/facebook/signin", method = RequestMethod.GET)
    public String signIn(HttpServletRequest request, HttpServletResponse response, Model model) throws FacebookException {
        
        facebook = new FacebookFactory().getInstance();
        model.addAttribute("facebook", facebook);
        
        StringBuffer callbackURL = request.getRequestURL();
        int index = callbackURL.lastIndexOf("/");
        callbackURL.replace(index, callbackURL.length(), "").append("/callback");
        return "redirect:" + facebook.getOAuthAuthorizationURL(callbackURL.toString());
        
    }
    
    @RequestMapping(value = "/facebook/callback", method = RequestMethod.GET)
    public String callbackFacebook(HttpServletRequest request, Model model) throws ServletException, IOException {
        String oauthCode = request.getParameter("code");
        try {
            facebook.getOAuthAccessToken(oauthCode);
        } catch (FacebookException e) {
            throw new ServletException(e);
        }
        return "redirect:/welcome";
    }
}
