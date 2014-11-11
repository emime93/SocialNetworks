/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities.google;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.drive.Drive;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import social.controllers.GoogleSocialControllers;

/**
 *
 * @author Petricioiu
 */
@Component("Google")
@SessionScoped
public class Google implements Serializable{
    
    public Google() throws IOException {
        GSON = new Gson();
        JSON_FACTORY = com.google.api.client.json.jackson2.JacksonFactory.getDefaultInstance();
        clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(GoogleSocialControllers.class.getResourceAsStream("/client_secrets.json")));
        CLIENT_ID = clientSecrets.getWeb().getClientId();
        CLIENT_SECRET = "AncFJHiSays1KNRZwQIFeBDC";//clientSecrets.getWeb().getClientSecret();
        TRANSPORT = new NetHttpTransport();
    }
    
    public String getUserEmail() throws IOException {
        return tokenResponse.parseIdToken().getPayload().getSubject();
    }

    /*
     * Default HTTP transport to use to make HTTP requests.
     */
    private HttpTransport TRANSPORT;

    /*
     * Default JSON factory to use to deserialize JSON.
     */
    private JsonFactory JSON_FACTORY;

    /*
     * Gson object to serialize JSON responses to requests to this servlet.
     */
    private Gson GSON;

    /*
     * Creates a client secrets object from the client_secrets.json file.
     */
    private GoogleClientSecrets clientSecrets;
    /*
     * This is the Client ID that you generated in the API Console.
     */
    private String CLIENT_ID;

    /*
     * This is the Client Secret that you generated in the API Console.
     */
    private String CLIENT_SECRET;

    /*
     * Optionally replace this with your application's name.
     */
    private String APPLICATION_NAME = "MultDrive";

    /*
     * Authorization code 
     */
    private String code;

    GoogleTokenResponse tokenResponse;
    Drive drive;
    GoogleCredential credential;

    public HttpTransport getTRANSPORT() {
        return TRANSPORT;
    }

    public void setTRANSPORT(HttpTransport TRANSPORT) {
        this.TRANSPORT = TRANSPORT;
    }

    public JsonFactory getJSON_FACTORY() {
        return JSON_FACTORY;
    }

    public void setJSON_FACTORY(JsonFactory JSON_FACTORY) {
        this.JSON_FACTORY = JSON_FACTORY;
    }

    public Gson getGSON() {
        return GSON;
    }

    public void setGSON(Gson GSON) {
        this.GSON = GSON;
    }

    public GoogleClientSecrets getClientSecrets() {
        return clientSecrets;
    }

    public void setClientSecrets(GoogleClientSecrets clientSecrets) {
        this.clientSecrets = clientSecrets;
    }

    public String getCLIENT_ID() {
        return CLIENT_ID;
    }

    public void setCLIENT_ID(String CLIENT_ID) {
        this.CLIENT_ID = CLIENT_ID;
    }

    public String getCLIENT_SECRET() {
        return CLIENT_SECRET;
    }

    public void setCLIENT_SECRET(String CLIENT_SECRET) {
        this.CLIENT_SECRET = CLIENT_SECRET;
    }

    public String getAPPLICATION_NAME() {
        return APPLICATION_NAME;
    }

    public void setAPPLICATION_NAME(String APPLICATION_NAME) {
        this.APPLICATION_NAME = APPLICATION_NAME;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public GoogleTokenResponse getTokenResponse() {
        return tokenResponse;
    }

    public void setTokenResponse(GoogleTokenResponse tokenResponse) {
        this.tokenResponse = tokenResponse;
    }

    public Drive getDrive() {
        return drive;
    }

    public void setDrive(Drive drive) {
        this.drive = drive;
    }

    public GoogleCredential getCredential() {
        return credential;
    }

    public void setCredential(GoogleCredential credential) {
        this.credential = credential;
    }

    public void connect() throws IOException {
        tokenResponse = new GoogleAuthorizationCodeTokenRequest(TRANSPORT, JSON_FACTORY,
                CLIENT_ID, CLIENT_SECRET, code, "postmessage").execute();
        
        GoogleCredential credential = new GoogleCredential.Builder()
        .setJsonFactory(JSON_FACTORY)
        .setTransport(TRANSPORT)
        .setClientSecrets(CLIENT_ID, CLIENT_SECRET).build()
        .setFromTokenResponse(tokenResponse);
        
        setCredential(credential);
        
        setDrive(new Drive.Builder(TRANSPORT, JSON_FACTORY, credential).build());
    }

}
