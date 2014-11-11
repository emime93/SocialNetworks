/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Petricioiu
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Override
    public UserDetails loadUserByUsername(String socialId) throws UsernameNotFoundException {
        
        String username = null;
        String password = null;
        
        Connection con = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/multdrive;create=true","emime","robbip");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from users where username='" + socialId + "'");
            if(rs.next()) {
                String id = rs.getString("password");
                if (id.equals(socialId)) {
                    username = rs.getString("username");
                    password = rs.getString("password");
                }
            }
            else {
                st.executeUpdate("insert into users values ('" + socialId + "','" + socialId + "', 1)");
                username = socialId;
                password = "";
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDetailsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserDetailsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        UserDetails user = new org.springframework.security.core.userdetails.User(username, password, java.util.Arrays.asList(new GrantedAuthority[]{new GrantedAuthorityImpl("ROLE_USER")}));
        
        return user;
    }
    
}
