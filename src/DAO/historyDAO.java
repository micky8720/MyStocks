package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import VO.historyVO;
import VO.registrationVO;

public class historyDAO {
	public List<historyVO> fetch_all_purchase_user(int user_id)
	{
		
		String query="SELECT * FROM PURCHASE WHERE user_id='"+user_id+"'";
		List<historyVO> list_of_purchase_user=new ArrayList<historyVO>();
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
				 historyVO historyVO=new historyVO();
				 historyVO.setManager_id(rs.getInt("manager_id"));
				 historyVO.setPurchase_id(rs.getInt("purchase_id"));
				 historyVO.setStock_name(rs.getString("stock_name"));
				 historyVO.setPrice(rs.getFloat("price"));
				 historyVO.setQuantity(rs.getFloat("quantity"));
				 historyVO.setAmount(rs.getFloat("amount"));
				 historyVO.setPurchase_date(rs.getString("date"));
				 list_of_purchase_user.add(historyVO);	
			}
			ps.close();
			databaseConnectionDAO.close(c);
			System.out.println("list check::"+list_of_purchase_user.size());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("list", list_of_purchase_user);
			//return list_of_all_applied_managers;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			//return null;
		}	
		return list_of_purchase_user;
	}
	public List<historyVO> fetch_watchlist_user(int user_id)
	{
		
		String query="SELECT * FROM WATCHLIST WHERE user_id='"+user_id+"'";
		List<historyVO> list_of_purchase_user=new ArrayList<historyVO>();
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
				 historyVO historyVO=new historyVO();
				 historyVO.setUser_id(rs.getInt("user_id"));
				 historyVO.setStock_name(rs.getString("stock_name"));
				 
				 
				 list_of_purchase_user.add(historyVO);	
			}
			ps.close();
			databaseConnectionDAO.close(c);
			System.out.println("list check::"+list_of_purchase_user.size());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("list", list_of_purchase_user);
			//return list_of_all_applied_managers;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			//return null;
		}	
		return list_of_purchase_user;
	}
	public List<historyVO> fetch_all_purchase_manager(int user_id)
	{
		
		String query="SELECT * FROM PURCHASE WHERE manager_id='"+user_id+"'";
		List<historyVO> list_of_purchase_user=new ArrayList<historyVO>();
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
				 historyVO historyVO=new historyVO();
				 historyVO.setManager_id(rs.getInt("manager_id"));
				 historyVO.setPurchase_id(rs.getInt("purchase_id"));
				 historyVO.setStock_name(rs.getString("stock_name"));
				 historyVO.setPrice(rs.getFloat("price"));
				 historyVO.setQuantity(rs.getFloat("quantity"));
				 historyVO.setAmount(rs.getFloat("amount"));
				 historyVO.setPurchase_date(rs.getString("date"));
				 historyVO.setUser_id(rs.getInt("user_id"));
				 list_of_purchase_user.add(historyVO);	
			}
			ps.close();
			databaseConnectionDAO.close(c);
			System.out.println("list check::"+list_of_purchase_user.size());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("list", list_of_purchase_user);
			//return list_of_all_applied_managers;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch of dao...");
			e.printStackTrace();
			//return null;
		}	
		return list_of_purchase_user;
	}

public List<historyVO> fetch_all_sell_user(int user_id)
{
	
	String query="SELECT * FROM SELL WHERE user_id='"+user_id+"'";
	List<historyVO> list_of_purchase_user=new ArrayList<historyVO>();
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
			 historyVO historyVO=new historyVO();
			 historyVO.setManager_id(rs.getInt("manager_id"));
			 historyVO.setSell_id(rs.getInt("sell_id"));
			 historyVO.setStock_name(rs.getString("stock_name"));
			 historyVO.setPrice(rs.getFloat("price"));
			 historyVO.setQuantity(rs.getFloat("quantity"));
			 historyVO.setAmount(rs.getFloat("amount"));
			 historyVO.setSelling_date(rs.getString("date"));
			 list_of_purchase_user.add(historyVO);	
		}
		ps.close();
		databaseConnectionDAO.close(c);
		System.out.println("list check::"+list_of_purchase_user.size());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("list", list_of_purchase_user);
		//return list_of_all_applied_managers;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		System.out.println("in catch of dao...");
		e.printStackTrace();
		//return null;
	}	
	return list_of_purchase_user;
}
public List<historyVO> fetch_all_sell_manager(int manager_id)
{
	
	String query="SELECT * FROM SELL WHERE manager_id='"+manager_id+"'";
	List<historyVO> list_of_purchase_user=new ArrayList<historyVO>();
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
			 historyVO historyVO=new historyVO();
			 historyVO.setManager_id(rs.getInt("manager_id"));
			 historyVO.setSell_id(rs.getInt("sell_id"));
			 historyVO.setStock_name(rs.getString("stock_name"));
			 System.out.println("check:"+rs.getString("stock_name"));
			 historyVO.setPrice(rs.getFloat("price"));
			 historyVO.setQuantity(rs.getFloat("quantity"));
			 historyVO.setAmount(rs.getFloat("amount"));
			 historyVO.setSelling_date(rs.getString("date"));
			 historyVO.setUser_id(rs.getInt("user_id"));
			 list_of_purchase_user.add(historyVO);	
		}
		ps.close();
		databaseConnectionDAO.close(c);
	
		System.out.println("list check::"+list_of_purchase_user.size());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("list", list_of_purchase_user);
		//return list_of_all_applied_managers;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		System.out.println("in catch of dao...");
		e.printStackTrace();
		//return null;
	}	
	return list_of_purchase_user;
}
}
