/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package social.controllers;

import entities.User;
import entities.UserDetailsServiceImpl;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.internal.http.HttpRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public String signIn(HttpServletRequest request, HttpServletResponse response, Model model) throws FacebookException, IOException {
        
        facebook = new FacebookFactory().getInstance();
        request.getSession().setAttribute("facebook", facebook);
        
        StringBuffer callbackURL = request.getRequestURL();
        int index = callbackURL.lastIndexOf("/");
        callbackURL.replace(index, callbackURL.length(), "").append("/callback");
        return "redirect:" + facebook.getOAuthAuthorizationURL(callbackURL.toString());
        
    }
    
    @Autowired
    @Qualifier("User")
    User user;
    
    @RequestMapping(value = "/facebook/callback", method = RequestMethod.GET)
    public String callbackFacebook(HttpServletRequest request, Model model, HttpServletResponse response) throws ServletException, IOException {
       
        String oauthCode = request.getParameter("code");
        try {
            facebook.getOAuthAccessToken(oauthCode);
            UserDetailsServiceImpl userDetailsServiceImpl = new UserDetailsServiceImpl();
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(facebook.getId());
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            user.setUsername(facebook.getName());
            request.getSession().setAttribute("utilizator", user);
            
        } catch (FacebookException e) {
            throw new ServletException(e);
        }
        
        return "redirect:/welcome";
                
    }
}
