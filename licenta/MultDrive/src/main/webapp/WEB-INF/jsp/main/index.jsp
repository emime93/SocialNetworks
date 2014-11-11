<%-- 
    Document   : index
    Created on : Oct 31, 2014, 11:19:06 AM
    Author     : Petricioiu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${APPLICATION_NAME}</title>
        <script src="https://apis.google.com/js/client:platform.js" async defer></script>

        <meta name="google-signin-clientid" content="${CLIENT_ID}" />
        <meta name="google-signin-scope" content="https://www.googleapis.com/auth/plus.login" />
        <meta name="google-signin-requestvisibleactions" content="http://schema.org/AddAction" />
        <meta name="google-signin-cookiepolicy" content="single_host_origin" />
        <meta name="google-signin-callback" content="signInCallback" />

        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet" type="text/css">
        <script src="https://apis.google.com/js/client:platform.js?onload=render" async defer></script>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/google/google-sign-in.js" />"></script>
        <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/bootstrap-social.css" />" />
        <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/bootstrap.css" />" />
        <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
        <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/forms.css" />" />
    </head>
    <body>

        <div class="sign-in-form">
            <div class="sign-in-form-header"></div>
            <div class="sign-in-form-general">
                <form method="POST" action="<c:url value="/j_spring_security_check" />"> 
                    <table>   
                        <tr>     
                            <td align="right">Username</td>     
                            <td><input type="text" name="j_username" /></td>   
                        </tr>   <tr>     <td align="right">Password</td>     
                            <td><input type="password" name="j_password" /></td>   
                        </tr> <tr>     <td align="right">Remember me</td>     
                            <td><input type="checkbox" name="_spring_security_remember_me" /></td>   
                        </tr>   <tr>     <td colspan="2" align="right">       
                                <input type="submit" value="Login" />       
                                <input type="reset" value="Reset" />     
                            </td>   
                        </tr> 
                    </table> 
                </form>
            </div>
            <div class="sign-in-form-footer">
                <a class="btn btn-social btn-facebook" href="<c:url value="/facebook/signin" />">
                    <i class="fa fa-facebook"></i> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sign in with Facebook
                </a>
                <div id="gConnect">
                    <button class="g-signin"
                            data-scope="https://www.googleapis.com/auth/plus.login"
                            data-requestvisibleactions="http://schemas.google.com/AddActivity"
                            data-clientId="{{ CLIENT_ID }}"
                            data-accesstype="offline"
                            data-callback="signInCallback"
                            data-theme="dark"
                            data-cookiepolicy="single_host_origin">
                    </button>
                </div>
            </div>
        </div>
        <div id="authOps" style="display:none">
            <h2>User is now signed in to the app using Google+</h2>
            <p>If the user chooses to disconnect, the app must delete all stored
                information retrieved from Google for the given user.</p>
            <button id="disconnect" >Disconnect your Google account from this app</button>

            <h2>User's profile information</h2>
            <p>This data is retrieved client-side by using the Google JavaScript API
                client library.</p>
            <div id="profile"></div>

            <h2>User's friends that are visible to this app</h2>
            <p>This data is retrieved from your server, where your server makes
                an authorized HTTP request on the user's behalf.</p>
            <p>If your app uses server-side rendering, this is the section you
                would change using your server-side templating system.</p>
            <div id="visiblePeople"></div>

            <h2>Authentication Logs</h2>
            <pre id="authResult"></pre>
        </div>
    </body>
</html>
