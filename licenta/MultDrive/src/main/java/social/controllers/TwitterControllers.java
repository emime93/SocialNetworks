/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package social.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

/**
 *
 * @author Petricioiu
 */
@Controller
public class TwitterControllers {

    @RequestMapping(value = "/twitter/signin", method = RequestMethod.GET)
    public String twitterSignIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, TwitterException {
        Twitter twitter = new TwitterFactory().getInstance();
        request.getSession().setAttribute("twitter", twitter);
        try {
            StringBuffer callbackURL = request.getRequestURL();
            int index = callbackURL.lastIndexOf("/");
            callbackURL.replace(index, callbackURL.length(), "").append("/callback");
            RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL.toString());
            request.getSession().setAttribute("requestToken", requestToken);
            return "redirect:" + requestToken.getAuthenticationURL();
        } catch (TwitterException e) {
            throw new ServletException(e);
        }
    }

    @RequestMapping(value = "/twitter/callback", method = RequestMethod.GET)
    public String twitterCallback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, TwitterException {
        Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
     
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        String verifier = request.getParameter("oauth_verifier");
        try {
            twitter.getOAuthAccessToken(requestToken, verifier);
            request.getSession().removeAttribute("requestToken");
        } catch (TwitterException e) {
            throw new ServletException(e);
        }
        return "redirect:/welcome";
    }
}
