<%-- 
    Document   : aesOnline
    Created on : Oct 6, 2014, 10:32:51 PM
    Author     : Petricioiu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Aes Online</title>
        <style>
            input {
                outline-style: none;
                border-radius: 5px;
                font-size: 20px;
            }
            input[type='submit'] {
                color:#3366ff;
                outline-style: auto;
                font-size:14px;
                border-radius: 0px;
                background-color:#fff;
            }
        </style>
    </head>
    <body>
        <div style="background-color:#ff3300;border-radius:5px;color:#fff;width:400px;height:400px;position: absolute;left:50%;top:50%;margin:-200px 0 0 -200px;">
            <form style="padding:25px;" method="GET" action="aes/encrypt">
                <table>
                    <td>Plain text:</td>
                    <td><input type="text" name="plainText"/></td>
                    <tr />
                    <td>Key:</td>
                    <td><input type="text" name="key"/></td>
                    <tr />
                    <td>Cipher Text:</td>
                    <td><input type="text" name="cipherText" value="${cipherText}"/></td>
                    <tr /><td />
                    <td>
                        <table><td><input type="submit" value="encrypt" /></td><tr/>
                                <td><input type="submit" value="decrypt" onclick="form.action='aes/decrypt';"/></td>
                        </table>
                    </td>
                </table>
            </form>
                    <h4>Decrypted Text: ${decryptedText}</h4>
        </div>
    </body>
</html>
