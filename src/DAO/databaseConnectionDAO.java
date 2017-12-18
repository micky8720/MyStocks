package DAO;

import java.sql.Connection;

public class databaseConnectionDAO {
	 private databaseConnectionDAO()
	 {
		 //private constructor as i am using singleton DB connection class..
	 }

	 public static Connection getConnection() {
		 Connection c=null;
		
	    	try{
	    		com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
	    		ds.setServerName(System.getenv("ICSI518_SERVER"));
				ds.setPortNumber(Integer.parseInt(System.getenv("ICSI518_PORT")));
				ds.setDatabaseName(System.getenv("ICSI518_DB"));
				ds.setUser(System.getenv("ICSI518_USER"));
				ds.setPassword(System.getenv("ICSI518_PASSWORD"));
				c = ds.getConnection();
				return c;
	    	}
	    	catch (Exception ex) {
	            System.out.println("Database.getConnection() Error -->" + ex.getMessage());
	            return null;
	        }
	 }
	    	 public static void close(Connection con) {
	    	        try {
	    	            con.close();
	    	        } catch (Exception ex) {
	    	        }
	    	    
		 
		 
	 }
}
