package controller;
import VO.*;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import DAO.historyDAO;
import DAO.registrationDAO;
import DAO.requestManagerDAO;
import VO.registrationVO;

@ManagedBean(name="historyController")
@SessionScoped
public class historyController {

	@ManagedProperty(value = "#{historyVO}")
	private historyVO historyVO;
    private float balance;
    private String username;
    registrationDAO registrationDAO=new registrationDAO();
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public float getBalance() {
		balance=registrationDAO.getBalance(username);
		System.out.println("balance here:"+balance);
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public historyVO getHistoryVO() {
		return historyVO;
	}

	public void setHistoryVO(historyVO historyVO) {
		this.historyVO = historyVO;
	}
	private List<historyVO> list_purchase_user;
	private List<historyVO> list_sell_user;
	private List<historyVO> list_purchase_manager;
	private List<historyVO> list_sell_manager;
	private List<historyVO> list_watchlist_user;
	private List<historyVO> list_watchlist_manager;
	public List<historyVO> getList_watchlist_user() {
		historyDAO historyDAO=new historyDAO();
		list_watchlist_user=historyDAO.fetch_watchlist_user(historyVO.getUser_id());
		System.out.println("checking list in getlist metho:"+list_watchlist_user.size());
		return list_watchlist_user;
	}

	public void setList_watchlist_user(List<historyVO> list_watchlist_user) {
		this.list_watchlist_user = list_watchlist_user;
	}

	public List<historyVO> getList_watchlist_manager() {
		return list_watchlist_manager;
	}

	public void setList_watchlist_manager(List<historyVO> list_watchlist_manager) {
		this.list_watchlist_manager = list_watchlist_manager;
	}

	public List<historyVO> getList_purchase_manager() {
		
		historyDAO historyDAO=new historyDAO();
		System.out.println("in get list ...user id is:"+historyVO.getManager_id());
		list_purchase_manager=historyDAO.fetch_all_purchase_manager(historyVO.getManager_id());
		
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("list_users");
		
		
		return list_purchase_manager;
	}

	public void setList_purchase_manager(List<historyVO> list_purchase_manager) {
		this.list_purchase_manager = list_purchase_manager;
	}

	public List<historyVO> getList_sell_manager() {
		historyDAO historyDAO=new historyDAO();
		System.out.println("in get list ...manager id is:"+historyVO.getManager_id());
		list_sell_manager=historyDAO.fetch_all_sell_manager(historyVO.getManager_id());
		
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("list_sell_manager");
		
		
		
		return list_sell_manager;
	}

	public void setList_sell_manager(List<historyVO> list_sell_manager) {
		this.list_sell_manager = list_sell_manager;
	}

	public String getHistoryOfUser()
	{
		registrationDAO registrationDAO=new registrationDAO();
		
		System.out.println("in get list if user mathod in controller..");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		  username=params.get("username");
		 //String role=registrationDAO.getRole(username);
		int user_id=registrationDAO.getUser_id(username);
		historyVO.setUser_id(user_id);
		historyDAO historyDAO=new historyDAO();
		list_purchase_user=historyDAO.fetch_all_purchase_user(user_id);
		list_sell_user=historyDAO.fetch_all_sell_user(user_id);
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("list_users");
		
		
		return "historyUser?faces-redirect=true&includeViewParams=true";
	}
	public String getWatchlistOfUser()
	{
		registrationDAO registrationDAO=new registrationDAO();
		
		System.out.println("in get list if user mathod in controller..");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		  username=params.get("username");
		 //String role=registrationDAO.getRole(username);
		int user_id=registrationDAO.getUser_id(username);
		historyVO.setUser_id(user_id);
		historyDAO historyDAO=new historyDAO();
		list_purchase_user=historyDAO.fetch_watchlist_user(user_id);
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("list_users");
		
		
		return "watchlistUser?faces-redirect=true&includeViewParams=true";
	}
	public String getWatchlistOfManager()
	{
		registrationDAO registrationDAO=new registrationDAO();
		
		System.out.println("in get list if user mathod in controller..");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		  username=params.get("username");
		 //String role=registrationDAO.getRole(username);
		int user_id=registrationDAO.getUser_id(username);
		historyVO.setUser_id(user_id);
		historyDAO historyDAO=new historyDAO();
		list_purchase_user=historyDAO.fetch_watchlist_user(user_id);
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("list_users");
		
		
		return "watchlistManager?faces-redirect=true&includeViewParams=true";
	}
	public String getHistoryOfManager()
	{
		registrationDAO registrationDAO=new registrationDAO();
		
		System.out.println("in get list if user mathod in controller..");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		  username=params.get("username");
		 //String role=registrationDAO.getRole(username);
		int manager_id=registrationDAO.getUser_id(username);
		historyVO.setManager_id(manager_id);
		historyDAO historyDAO=new historyDAO();
		list_purchase_user=historyDAO.fetch_all_purchase_user(manager_id);
		list_sell_user=historyDAO.fetch_all_sell_user(manager_id);
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("list_users");
		
		
		return "historyManager?faces-redirect=true&includeViewParams=true";
	}

	public List<historyVO> getList_purchase_user() {
		
		
		historyDAO historyDAO=new historyDAO();
		System.out.println("in get list ...user id is:"+historyVO.getUser_id());
		list_purchase_user=historyDAO.fetch_all_purchase_user(historyVO.getUser_id());
		
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("list_users");
		
		return list_purchase_user;
	}

	public void setList_purchase_user(List<historyVO> list_purchase_user) {
		
		historyDAO historyDAO=new historyDAO();
		list_purchase_user=historyDAO.fetch_all_purchase_user(historyVO.getUser_id());
		this.list_purchase_user = list_purchase_user;
	}

	public List<historyVO> getList_sell_user() {
		
		historyDAO historyDAO=new historyDAO();
		System.out.println("in get list ...user id is:"+historyVO.getUser_id());
		list_sell_user=historyDAO.fetch_all_sell_user(historyVO.getUser_id());
		
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("list_sell_users");
		
		return list_sell_user;
		
	}

	public void setList_sell_user(List<historyVO> list_sell_user) {
		this.list_sell_user = list_sell_user;
	}
}
