/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import social.controllers.GoogleSocialControllers;

/**
 *
 * @author Petricioiu
 */
@Controller
@RequestMapping("/")
public class MainController {

    
    public MainController() throws IOException {
        GSON = new Gson();
        JSON_FACTORY = com.google.api.client.json.jackson2.JacksonFactory.getDefaultInstance();
        clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(GoogleSocialControllers.class.getResourceAsStream("/client_secrets.json")));
        CLIENT_ID = clientSecrets.getWeb().getClientId();
        TRANSPORT = new NetHttpTransport();
    }

     /*
     * Default HTTP transport to use to make HTTP requests.
     */
    private static  HttpTransport TRANSPORT; // = new NetHttpTransport();

    /*
     * Default JSON factory to use to deserialize JSON.
     */
    private static  JsonFactory JSON_FACTORY;// = com.google.api.client.json.jackson2.JacksonFactory.getDefaultInstance();

    /*
     * Gson object to serialize JSON responses to requests to this servlet.
     */
    private static  Gson GSON;// = new Gson();

    /*
     * Creates a client secrets object from the client_secrets.json file.
     */
    private static GoogleClientSecrets clientSecrets;
    /*
     * This is the Client ID that you generated in the API Console.
     */
    private static  String CLIENT_ID;

    /*
     * This is the Client Secret that you generated in the API Console.
     */
    private static  String CLIENT_SECRET;// = clientSecrets.getWeb().getClientSecret();

    /*
     * Optionally replace this with your application's name.
     */
    private static final  String APPLICATION_NAME = "Google+ Java Quickstart";
    
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome(HttpServletRequest request, HttpServletResponse response) {
        String state = new BigInteger(130, new SecureRandom()).toString(32);
        request.getSession().setAttribute("CLIENT_ID", CLIENT_ID);
        request.getSession().setAttribute("STATE", state);
        String states1 = (String) request.getSession().getAttribute("STATE");
        request.getSession().setAttribute("APPLICATION_NAME", APPLICATION_NAME);
        response.setStatus(HttpServletResponse.SC_OK);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/drive";
        }
        return "main/index";
    }
    
    @RequestMapping(value = "/drive", method = RequestMethod.GET)
    public String drive() {
        return "drive/drive";
    }
}
