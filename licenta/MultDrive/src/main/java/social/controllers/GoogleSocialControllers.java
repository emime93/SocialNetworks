/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package social.controllers;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.TokenErrorResponse;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.gson.Gson;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.Constants.REDIRECT_URI;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utilities.google.Google;

/**
 *
 * @author Petricioiu
 */
@Controller
public class GoogleSocialControllers {

    @Autowired
    @Qualifier("Google")
    Google google;

    @RequestMapping(method = RequestMethod.POST, value = "/welcome/connect/google")
    public String connectGoogle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        // Only connect a user that is not already connected.
        String tokenData = (String) request.getSession().getAttribute("token");
        if (tokenData != null) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print(google.getGSON().toJson("Current user is already connected."));
            return "main/index";
        }

        // Ensure that this is no request forgery going on, and that the user
        // sending us this connect request is the user that was supposed to.
        String states = request.getParameter("state");
        String states1 = (String) request.getSession().getAttribute("STATE");
        if (states.equals(states1)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(google.getGSON().toJson("Invalid state parameter."));
            return "main/index";
        }
        // Normally the state would be a one-time use token, however in our
        // simple case, we want a user to be able to connect and disconnect
        // without reloading the page.  Thus, for demonstration, we don't
        // implement this best practice.
        //request.getSession().removeAttribute("state");

        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        getContent(request.getInputStream(), resultStream);

        String code = new String(resultStream.toByteArray(), "UTF-8");

        try {
            
            google.setCode(code);
            google.connect();
 
            // Store the token in the session for later use.
            request.getSession().setAttribute("token", google.getTokenResponse().toString());
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print(google.getGSON().toJson("Successfully connected user."));
            
        } catch (TokenResponseException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print(google.getGSON().toJson("Failed to upgrade the authorization code."));
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print(google.getGSON().toJson("Failed to read token data from Google. "
                    + e.getMessage()));
        }

        return "main/index";
    }

    /*  @RequestMapping(method = RequestMethod.POST, value = "/welcome/disconnect/google")
     public void disconnectGoogle(HttpServletRequest request, HttpServletResponse response) throws IOException {
     response.setContentType("application/json");

     // Only disconnect a connected user.
     String tokenData = (String) request.getSession().getAttribute("token");
     if (tokenData == null) {
     response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
     response.getWriter().print(GSON.toJson("Current user not connected."));
     return;
     }
     try {
     // Build credential from stored token data.
     GoogleCredential credential = new GoogleCredential.Builder()
     .setJsonFactory(JSON_FACTORY)
     .setTransport(TRANSPORT)
     .setClientSecrets(CLIENT_ID, CLIENT_SECRET).build()
     .setFromTokenResponse(JSON_FACTORY.fromString(
     tokenData, GoogleTokenResponse.class));
     // Execute HTTP GET request to revoke current token.
     HttpResponse revokeResponse = TRANSPORT.createRequestFactory()
     .buildGetRequest(new GenericUrl(
     String.format(
     "https://accounts.google.com/o/oauth2/revoke?token=%s",
     credential.getAccessToken()))).execute();
     // Reset the user's session.
     request.getSession().removeAttribute("token");
     response.setStatus(HttpServletResponse.SC_OK);
     response.getWriter().print(GSON.toJson("Successfully disconnected."));
     } catch (IOException e) {
     // For whatever reason, the given token was invalid.
     response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
     response.getWriter().print(GSON.toJson("Failed to revoke token for given user."));
     }
     }
     */
    static void getContent(InputStream inputStream, ByteArrayOutputStream outputStream)
            throws IOException {
        // Read the response into a buffered stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        int readChar;
        while ((readChar = reader.read()) != -1) {
            outputStream.write(readChar);
        }
        reader.close();
    }

}
