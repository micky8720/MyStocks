package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.faces.context.FacesContext;

import VO.registrationVO;
import controller.registrationController;
import controller.updateProfileController;

import java.sql.DriverManager;
import java.sql.ResultSet;

public class registrationDAO {
	
	public static boolean insertData(String firstname,String lastname,String contactnumber,String email,String username,String password,String role) throws ClassNotFoundException
	{ 
		String query="INSERT INTO USER"
				+ "(firstname,lastname,contactnumber, email, username, password,role,balance) VALUES"
				+ "(?,?,?,?,?,?,?,?)";
		Connection c=null;
		try {
			c=databaseConnectionDAO.getConnection();
			java.sql.PreparedStatement ps;
			ps = c.prepareStatement(query);
			ps.setString(1, firstname);
			ps.setString(2, lastname);
			ps.setString(3, contactnumber);
			ps.setString(4, email);
			ps.setString(5, username);
			ps.setString(6, password);
			ps.setString(7, role);
			ps.setFloat(8, 100000);
			ps.executeUpdate();
			ps.close();
			databaseConnectionDAO.close(c);
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			return false;
		}	
		
	}
	public static boolean insertTempData(String firstname,String lastname,String contactnumber,String email,String username,String password,String role,float fees) throws ClassNotFoundException
	{ 
		String query="INSERT INTO TEMPUSER"
				+ "(firstname,lastname,contactnumber, email, username, password,role,fees,balance) VALUES"
				+ "(?,?,?,?,?,?,?,?,?)";
		Connection c=null;
		try {
			c=databaseConnectionDAO.getConnection();
			java.sql.PreparedStatement ps;
			ps = c.prepareStatement(query);
			ps.setString(1, firstname);
			ps.setString(2, lastname);
			ps.setString(3, contactnumber);
			ps.setString(4, email);
			ps.setString(5, username);
			ps.setString(6, password);
			ps.setString(7, role);
			ps.setFloat(8, fees);
			ps.setFloat(9,0);
			
			ps.executeUpdate();
			ps.close();
			databaseConnectionDAO.close(c);
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			return false;
		}	
		
	}
	public  boolean updateUserData(String firstname,String lastname,String contactnumber,String email,String password,String username) throws ClassNotFoundException
	{ 
		String query="UPDATE USER SET FIRSTNAME=? ,LASTNAME=?,CONTACTNUMBER=?,EMAIL=?,PASSWORD=? WHERE USERNAME=?";
		Connection c=null;
		try {
			c=databaseConnectionDAO.getConnection();
			java.sql.PreparedStatement ps;
			ps = c.prepareStatement(query);
			ps.setString(1, firstname);
			ps.setString(2, lastname);
			ps.setString(3, contactnumber);
			ps.setString(4, email);
			
			ps.setString(5, password);
			ps.setString(6, username);
			
			ps.executeUpdate();
			ps.close();
			databaseConnectionDAO.close(c);
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			return false;
		}	
		
	}
	public  boolean updateManagerData(String firstname,String lastname,String contactnumber,String email,String password,float fees,String username) throws ClassNotFoundException
	{ 
		String query="UPDATE USER SET FIRSTNAME=? ,LASTNAME=?,CONTACTNUMBER=?,EMAIL=?,PASSWORD=?,FEES=? WHERE USERNAME=?";
		Connection c=null;
		try {
			System.out.println("fees here in dao method:"+fees);
			c=databaseConnectionDAO.getConnection();
			java.sql.PreparedStatement ps;
			ps = c.prepareStatement(query);
			ps.setString(1, firstname);
			ps.setString(2, lastname);
			ps.setString(3, contactnumber);
			ps.setString(4, email);
			
			ps.setString(5, password);
			System.out.println("before fees in dao method");
			ps.setFloat(6, fees);
			System.out.println("after fees in dao method");
			ps.setString(7, username);
			
			ps.executeUpdate();
			ps.close();
			databaseConnectionDAO.close(c);
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			return false;
		}	
		
	}
	public List<registrationVO> fetch_all_applied_managers()
	{
		
		String query="SELECT * FROM TEMPUSER";
		List<registrationVO> list_of_all_applied_managers=new ArrayList<registrationVO>();
		Connection c=null;
		ResultSet rs=null;
		try {
			c=databaseConnectionDAO.getConnection();
			java.sql.PreparedStatement ps;
			
			ps = c.prepareStatement(query);
			rs=ps.executeQuery();
		
			while(rs.next())
			{
				 System.out.println(rs.getString(1));
				 registrationVO registrationVO=new registrationVO();
				 registrationVO.setFirstname(rs.getString("firstname"));
				 registrationVO.setLastname(rs.getString("lastname"));
				 registrationVO.setContactnumber(rs.getString("contactnumber"));
				 registrationVO.setEmail(rs.getString("email"));
				 registrationVO.setUsername(rs.getString("username"));
				 registrationVO.setRole(rs.getString("role"));
				 registrationVO.setUser_id(rs.getInt("user_id"));
				 list_of_all_applied_managers.add(registrationVO);
				
			}
			ps.close();
			databaseConnectionDAO.close(c);
			System.out.println("list check::"+list_of_all_applied_managers.size());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("list", list_of_all_applied_managers);
			//return list_of_all_applied_managers;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			//return null;
		}	
		return list_of_all_applied_managers;
	}
	public List<registrationVO> fetch_all_managers()
	{
		
		String query="SELECT * FROM USER where role='manager'";
		List<registrationVO> list_of_all_managers=new ArrayList<registrationVO>();
		Connection c=null;
		ResultSet rs=null;
		try {
			c=databaseConnectionDAO.getConnection();
			java.sql.PreparedStatement ps;
			
			ps = c.prepareStatement(query);
			rs=ps.executeQuery();
		
			while(rs.next())
			{
				 System.out.println(rs.getString(1));
				 registrationVO registrationVO=new registrationVO();
				 registrationVO.setFirstname(rs.getString("firstname"));
				 registrationVO.setLastname(rs.getString("lastname"));
				 registrationVO.setContactnumber(rs.getString("contactnumber"));
				 registrationVO.setEmail(rs.getString("email"));
				 registrationVO.setUsername(rs.getString("username"));
				 registrationVO.setRole(rs.getString("role"));
				 registrationVO.setUser_id(rs.getInt("user_id"));
				 registrationVO.setFees(rs.getFloat("fees"));
				 list_of_all_managers.add(registrationVO);
				
			}
			ps.close();
			databaseConnectionDAO.close(c);
			System.out.println("list check::"+list_of_all_managers.size());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("list", list_of_all_managers);
			//return list_of_all_applied_managers;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			//return null;
		}	
		return list_of_all_managers;
	}
	public static boolean approveManager(int user_id) throws ClassNotFoundException
	{ 
		
		String query1= "INSERT INTO mystocks.user (firstname, lastname,contactnumber,email,username,password,role,fees,balance)  SELECT firstname, lastname,contactnumber,email,username,password,role,fees,balance from mystocks.tempuser WHERE user_id='"+user_id+"'";
		   String query2= "DELETE FROM mystocks.tempuser WHERE user_id='"+user_id+"'";
		Connection c=null;
		try {
			c=databaseConnectionDAO.getConnection();
			java.sql.Statement s;
			s = c.createStatement();
	        s.executeUpdate(query1);
			
			s.executeUpdate(query2);
			s.close();
			databaseConnectionDAO.close(c);
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			return false;
		}	
		
	}
	public static boolean declineManager(int user_id) throws ClassNotFoundException
	{ 
		
		
		   String query= "DELETE FROM mystocks.tempuser WHERE user_id='"+user_id+"'";
		Connection c=null;
		try {
			c=databaseConnectionDAO.getConnection();
			java.sql.Statement s;
			s = c.createStatement();
	        s.executeUpdate(query);
			
		
			s.close();
			databaseConnectionDAO.close(c);
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			return false;
		}	
		
	}
	public String getEmail(int user_id)
	{
		
		String query="SELECT * FROM TEMPUSER where user_id='"+user_id+"'";
		String email=null;
		Connection c=null;
		ResultSet rs=null;
		try {
			c=databaseConnectionDAO.getConnection();
			java.sql.PreparedStatement ps;
			
			ps = c.prepareStatement(query);
			rs=ps.executeQuery();
		
			while(rs.next())
			{
				
				 email=rs.getString("email");
				System.out.println("in dao email is:"+email);
				return email;
			}
			ps.close();
			databaseConnectionDAO.close(c);
			
			//return list_of_all_applied_managers;
			return email;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			return null;
		}	
		
	}
	public String getRole(String username)
	{
		
		String query="SELECT * FROM USER where username='"+username+"'";
		String role=null;
		Connection c=null;
		ResultSet rs=null;
		try {
			c=databaseConnectionDAO.getConnection();
			java.sql.PreparedStatement ps;
			
			ps = c.prepareStatement(query);
			rs=ps.executeQuery();
		
			while(rs.next())
			{
				
				 role=rs.getString("role");
				System.out.println("in dao role is:"+role);
				return role;
			}
			ps.close();
			databaseConnectionDAO.close(c);
			
			//return list_of_all_applied_managers;
			return role;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			return null;
		}	
		
	}
	public int getUser_id(String username)
	{
		
		String query="SELECT * FROM USER where username='"+username+"'";
		int user_id=0;
		Connection c=null;
		ResultSet rs=null;
		try {
			c=databaseConnectionDAO.getConnection();
			java.sql.PreparedStatement ps;
			
			ps = c.prepareStatement(query);
			rs=ps.executeQuery();
		
			while(rs.next())
			{
				
				 user_id=rs.getInt("user_id");
				System.out.println("in dao user_id is:"+user_id);
				return user_id;
			}
			ps.close();
			databaseConnectionDAO.close(c);
			
			//return list_of_all_applied_managers;
			return user_id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			return 0;
		}	
		
	}
	public float getBalance(String username)
	{
		
		String query="SELECT * FROM USER where username='"+username+"'";
		float balance=0;
		Connection c=null;
		ResultSet rs=null;
		try {
			c=databaseConnectionDAO.getConnection();
			java.sql.PreparedStatement ps;
			
			ps = c.prepareStatement(query);
			rs=ps.executeQuery();
		
			while(rs.next())
			{
				
				balance=rs.getFloat("balance");
				System.out.println("in dao balance is:"+balance);
				return balance;
			}
			ps.close();
			databaseConnectionDAO.close(c);
			
			//return list_of_all_applied_managers;
			return balance;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			return 0;
		}	
		
	}
	public float getFees(int manager_id)
	{
		
		String query="SELECT * FROM USER where user_id='"+manager_id+"'";
		float fees=0;
		Connection c=null;
		ResultSet rs=null;
		try {
			c=databaseConnectionDAO.getConnection();
			java.sql.PreparedStatement ps;
			
			ps = c.prepareStatement(query);
			rs=ps.executeQuery();
		
			while(rs.next())
			{
				
				fees=rs.getFloat("fees");
				System.out.println("in dao fees is:"+fees);
				return fees;
			}
			ps.close();
			databaseConnectionDAO.close(c);
			
			//return list_of_all_applied_managers;
			return fees;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			return 0;
		}	
		
	}
	public List<registrationVO> fetch_all_user_details(String username)
	{
		
		String query="SELECT * FROM USER WHERE USERNAME='"+username+"'";
		List<registrationVO> list_of_all_user_details=new ArrayList<registrationVO>();
		Connection c=null;
		ResultSet rs=null;
		try {
			c=databaseConnectionDAO.getConnection();
			java.sql.PreparedStatement ps;
			
			ps = c.prepareStatement(query);
			rs=ps.executeQuery();
		
			while(rs.next())
			{
				 System.out.println(rs.getString(1));
				 registrationVO registrationVO=new registrationVO();
				 registrationVO.setFirstname(rs.getString("firstname"));
				 registrationVO.setLastname(rs.getString("lastname"));
				 registrationVO.setContactnumber(rs.getString("contactnumber"));
				 registrationVO.setEmail(rs.getString("email"));
				 registrationVO.setUsername(rs.getString("username"));
				 registrationVO.setRole(rs.getString("role"));
				 registrationVO.setUser_id(rs.getInt("user_id"));
				 registrationVO.setPassword(rs.getString("password"));
				 registrationVO.setFees(rs.getFloat("fees"));
				 System.out.println("fees here:"+rs.getFloat("fees"));
				 System.out.println(registrationVO.getFees());
				 list_of_all_user_details.add(registrationVO);
				
			}
			ps.close();
			databaseConnectionDAO.close(c);
			
			System.out.println("list check::"+list_of_all_user_details.size());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("list", list_of_all_user_details);
			//return list_of_all_applied_managers;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			//return null;
		}	
		return list_of_all_user_details;
	}
		
	}

