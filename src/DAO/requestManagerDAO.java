package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import VO.registrationVO;
import controller.requestManagerController;

public class requestManagerDAO {

	public static boolean insertData(int manager_id,int user_id,float amount) throws ClassNotFoundException
	{ 
		String query="INSERT INTO MANAGER_ASSIGNMENT"
				+ "(manager_id,user_id,amount) VALUES"
				+ "(?,?,?)";
		Connection c=null;
		try {
			c=databaseConnectionDAO.getConnection();
			java.sql.PreparedStatement ps;
			ps = c.prepareStatement(query);
			ps.setInt(1, manager_id);
			ps.setInt(2, user_id);
			ps.setFloat(3, amount);
			
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
	public static boolean insertDataSelling(int manager_id,int user_id,String stock_name,float quantity) throws ClassNotFoundException
	{ 
		String query="INSERT INTO MANAGER_ASSIGNMENT_SELLING"
				+ "(manager_id,user_id,stock_name,quantity) VALUES"
				+ "(?,?,?,?)";
		Connection c=null;
		try {
			c=databaseConnectionDAO.getConnection();
			java.sql.PreparedStatement ps;
			ps = c.prepareStatement(query);
			ps.setInt(1, manager_id);
			ps.setInt(2, user_id);
			ps.setString(3,stock_name);
			ps.setFloat(4, quantity);
			
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
	public List<requestManagerController> fetch_all_users_requested(int manager_id)
	{
		
		String query="SELECT * FROM mystocks.user x JOIN (SELECT *  FROM mystocks.manager_assignment ) y ON x.user_id = y.user_id where manager_id='"+manager_id+"'";
		List<requestManagerController> list_of_all_requested_users=new ArrayList<requestManagerController>();
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
				 requestManagerController requestManagerController=new requestManagerController();
				 requestManagerController.setUser_id(rs.getInt("user_id"));
				 requestManagerController.setFirstname(rs.getString("firstname"));
				 requestManagerController.setContactnumber(rs.getString("contactnumber"));
				 requestManagerController.setEmail(rs.getString("email"));
				 requestManagerController.setAssignAmount(rs.getFloat("amount"));
				
				 list_of_all_requested_users.add(requestManagerController);
				
			}
			ps.close();
			databaseConnectionDAO.close(c);
			System.out.println("list check::"+ list_of_all_requested_users.size());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("list",  list_of_all_requested_users);
			//return list_of_all_applied_managers;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			//return null;
		}	
		return  list_of_all_requested_users;
	}
	public List<requestManagerController> fetch_all_users_requested_selling(int manager_id)
	{
		
		String query="SELECT * FROM mystocks.user x JOIN (SELECT *  FROM mystocks.manager_assignment_selling ) y ON x.user_id = y.user_id where manager_id='"+manager_id+"'";
		List<requestManagerController> list_of_all_requested_users_selling=new ArrayList<requestManagerController>();
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
				 requestManagerController requestManagerController=new requestManagerController();
				 requestManagerController.setUser_id(rs.getInt("user_id"));
				 requestManagerController.setFirstname(rs.getString("firstname"));
				 requestManagerController.setContactnumber(rs.getString("contactnumber"));
				 requestManagerController.setEmail(rs.getString("email"));
				 requestManagerController.setStockToSell(rs.getString("stock_name"));
				 requestManagerController.setQuantityToSell(rs.getFloat("quantity"));
				
				 list_of_all_requested_users_selling.add(requestManagerController);
				
			}
			ps.close();
			databaseConnectionDAO.close(c);
			System.out.println("list check in dao method::"+ list_of_all_requested_users_selling.size());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("list",  list_of_all_requested_users_selling);
			//return list_of_all_applied_managers;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			//return null;
		}	
		return  list_of_all_requested_users_selling;
	}
	
	public List<requestManagerController> fetch_all_purchased_stocks_users(int user_id)
	{
		
		String query="SELECT * FROM mystocks.purchase where user_id='"+user_id+"' ";
		List<requestManagerController> list_of_all_purchased_stocks=new ArrayList<requestManagerController>();
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
				 requestManagerController requestManagerController=new requestManagerController();
				 requestManagerController.setManager_id(rs.getInt("manager_id"));
				 requestManagerController.setStock_name(rs.getString("stock_name"));
				 requestManagerController.setPrice(rs.getFloat("price"));
				 requestManagerController.setAmount(rs.getFloat("amount"));
				 requestManagerController.setQuantity(rs.getFloat("quantity"));
				
				 list_of_all_purchased_stocks.add(requestManagerController);
				
			}
			ps.close();
			databaseConnectionDAO.close(c);
			System.out.println("list check in requestManagerDAO::"+ list_of_all_purchased_stocks.size());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("list",  list_of_all_purchased_stocks);
			//return list_of_all_applied_managers;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			//return null;
		}	
		return list_of_all_purchased_stocks;
	}
	public float getAssigned_amount(int user_id,int manager_id)
	{
		
		String query="SELECT SUM(AMOUNT) AS AMOUNT FROM MANAGER_ASSIGNMENT where user_ID='"+user_id+"' and manager_id='"+manager_id+"'";
		float amount=0;
		Connection c=null;
		ResultSet rs=null;
		try {
			c=databaseConnectionDAO.getConnection();
			java.sql.PreparedStatement ps;
			
			ps = c.prepareStatement(query);
			rs=ps.executeQuery();
		
			while(rs.next())
			{
				
				amount=rs.getFloat("amount");
				System.out.println("in dao amount is:"+amount);
				return amount;
			}
			ps.close();
			databaseConnectionDAO.close(c);
			
			//return list_of_all_applied_managers;
			return amount;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			return 0;
		}	
		
	}
	public float getQuanitity(int user_id,String stock_name)
	{
		
		String query="SELECT SUM(QUANTITY) AS QUANTITY FROM PURCHASE where user_ID='"+user_id+"' and STOCK_NAME='"+stock_name+"'";
		float quantity=0;
		Connection c=null;
		ResultSet rs=null;
		try {
			c=databaseConnectionDAO.getConnection();
			java.sql.PreparedStatement ps;
			
			ps = c.prepareStatement(query);
			rs=ps.executeQuery();
		
			while(rs.next())
			{
				
				quantity=rs.getFloat("quantity");
				System.out.println("in dao amount is:"+quantity);
				return quantity;
			}
			ps.close();
			databaseConnectionDAO.close(c);
			
			//return list_of_all_applied_managers;
			return quantity;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...quantity is:"+quantity);
			e.printStackTrace();
			return 0;
		}	
		
	}
}
