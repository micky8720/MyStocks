package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.context.FacesContext;

public class loginDAO {

    public static boolean validate(String username, String password) {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = databaseConnectionDAO.getConnection();
            ps = c.prepareStatement("select * from user where username = ? and password = ?");
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", rs.getString("username"));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("role", rs.getString("role"));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstname", rs.getString("firstname"));
                //System.out.println("uid: " + rs.getString("uid"));
                //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key,object);
                
                System.out.println("role:"+rs.getString("role"));
                databaseConnectionDAO.close(c);
                return true;
            }

        } catch (SQLException ex) {
            System.out.println("Login error:" + ex.getMessage());
            return false;
        } finally {
        	databaseConnectionDAO.close(c);
        }
        return false;
    }

}