package controller;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import DAO.registrationDAO;
import DAO.requestManagerDAO;
import VO.registrationVO;

@ManagedBean(name="requestManagerController")
@SessionScoped

public class requestManagerController {

	registrationDAO registrationDAO=new registrationDAO();
	  private List<registrationVO> list;
	  private String username;
	  private String stockToSell;
	  private float quantityToSell;
	  public String getStockToSell() {
		return stockToSell;
	}

	public void setStockToSell(String stockToSell) {
		this.stockToSell = stockToSell;
	}

	public float getQuantityToSell() {
		return quantityToSell;
	}

	public void setQuantityToSell(float quantityToSell) {
		this.quantityToSell = quantityToSell;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	private List<requestManagerController> list_users;
	private List<requestManagerController> list_users_selling;
	  public List<requestManagerController> getList_users_selling() {
		  requestManagerDAO requestManagerDAO=new requestManagerDAO();
		 // FacesContext fc = FacesContext.getCurrentInstance();
			//Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
			 //String managerName=params.get("managerName");
			 //String role=registrationDAO.getRole(username);
			int manager_id=registrationDAO.getUser_id(username);
			 System.out.println("manager id in get list method;"+manager_id);
			list_users_selling=requestManagerDAO .fetch_all_users_requested_selling(manager_id);
			System.out.println("list check in get list method:"+list_users_selling.size());
		return list_users_selling;
	}

	public void setList_users_selling(List<requestManagerController> list_users_selling) {
		this.list_users_selling = list_users_selling;
	}
	private List<requestManagerController> list_of_purchased_stocks;
      public List<requestManagerController> getList_of_purchased_stocks() {
    	
    //	  FacesContext fc = FacesContext.getCurrentInstance();
  		//Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
  		 //String username=params.get("username"); 
  		 int user_id=registrationDAO.getUser_id(username);
    	  requestManagerDAO requestManagerDAO=new requestManagerDAO();
    	  
    	  System.out.println("in get list method...");
    	  System.out.println("username id here:"+username);
    	  System.out.println("user id here:"+user_id);
    	  
    	  list_of_purchased_stocks=requestManagerDAO.fetch_all_purchased_stocks_users(user_id);
		
		return list_of_purchased_stocks;
	}

	public void setList_of_purchased_stocks(List<requestManagerController> list_of_purchased_stocks) {
		this.list_of_purchased_stocks = list_of_purchased_stocks;
	}

	public List<requestManagerController> getList_users() {
    	  requestManagerDAO requestManagerDAO=new requestManagerDAO();
    	  list_users=requestManagerDAO.fetch_all_users_requested(manager_id);
		return list_users;
	}

	public void setList_users(List<requestManagerController> list_users) {
		this.list_users = list_users;
	}
	private float assignAmount;
      private int user_id;
      private int manager_id;
      private String firstname;
      private String contactnumber;
      private String email;
      private String stock_name;
      private float quantity;
      private float price;
      private float amount;
      private int purchase_id;
      
	public String getStock_name() {
		return stock_name;
	}

	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public int getPurchase_id() {
		return purchase_id;
	}

	public void setPurchase_id(int purchase_id) {
		this.purchase_id = purchase_id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getContactnumber() {
		return contactnumber;
	}

	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getManager_id() {
		return manager_id;
	}

	public void setManager_id(int manager_id) {
		this.manager_id = manager_id;
	}

	public float getAssignAmount() {
		return assignAmount;
	}

	public void setAssignAmount(float assignAmount) {
		this.assignAmount = assignAmount;
	}

	public List<registrationVO> getList() {
		list=registrationDAO.fetch_all_managers();
		return list;
	}

	public void setList(List<registrationVO> list) {
		this.list = list;
	}
	  
	public String searchManager()
	{   System.out.println("in search manager mathod in controller..");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		String username=params.get("username");
		 String role=registrationDAO.getRole(username);
		list=registrationDAO.fetch_all_user_details(username);
		System.out.println("here....username:"+username);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("list");
		
		System.out.println("users details list="+list);
		System.out.println("size here:"+list.size());
		
		return "searchManager?faces-redirect=true&includeViewParams=true";
	}
	public String searchManagerSelling()
	{   System.out.println("in search manager selling mathod in controller..");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		 username=params.get("username");
		 String role=registrationDAO.getRole(username);
		list=registrationDAO.fetch_all_user_details(username);
		System.out.println("here....username:"+username);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("list");
		
		System.out.println("users details list="+list);
		System.out.println("size here:"+list.size());
		
		return "searchManagerSelling?faces-redirect=true&includeViewParams=true";
	}
	public String selectManager() throws ClassNotFoundException

	{ 
		
		System.out.println("in select manager method in controller..");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		String username=params.get("username");
		int manager_id=Integer.parseInt(params.get("manager_id"));
		int user_id=registrationDAO.getUser_id(username);
		requestManagerDAO.insertData(manager_id, user_id, assignAmount);
		System.out.println("in selectmanager method...username:"+username);
		System.out.println("in selectmanager method...manager_id:"+manager_id);
		 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Manager Selected Successfully..",""));
		return "userProfile?faces-redirect=true&includeViewParams=true";
		
	}
	public String selectManagerSelling() throws ClassNotFoundException

	{ 
		
		System.out.println("in select managerselling  method in controller..");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		String username=params.get("username");
		int manager_id=Integer.parseInt(params.get("manager_id"));
		int user_id=registrationDAO.getUser_id(username);
		requestManagerDAO.insertDataSelling(manager_id, user_id,stockToSell,quantityToSell);
		System.out.println("in selectmanager method...username:"+username);
		System.out.println("in selectmanager method...manager_id:"+manager_id);
		
		 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Manager Selected Successfully..",""));
		return "userProfile?faces-redirect=true&includeViewParams=true";
		
	}
	public String getListOfUsers()
	{
		requestManagerDAO requestManagerDAO =new requestManagerDAO ();
		System.out.println("in get list if user mathod in controller..");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		 String managerName=params.get("managerName");
		 //String role=registrationDAO.getRole(username);
		 manager_id=registrationDAO.getUser_id(managerName);
		list_users=requestManagerDAO .fetch_all_users_requested(manager_id);
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("list_users");
		
		
		return "requestedUsers?faces-redirect=true&includeViewParams=true";
	}
	public String getListOfUsersSelling()
	{
		requestManagerDAO requestManagerDAO =new requestManagerDAO ();
		System.out.println("in get list if user mathod in controller..");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		  username=params.get("managerName");
		 //String role=registrationDAO.getRole(username);
		 manager_id=registrationDAO.getUser_id(username);
		list_users_selling=requestManagerDAO .fetch_all_users_requested_selling(manager_id);
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("list_users_selling");
		
		
		return "requestedUsersSelling?faces-redirect=true&includeViewParams=true";
	}
	public String sellStocks()
	{
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		  username=params.get("username"); 
		  user_id=registrationDAO.getUser_id(username);
		 System.out.println("user id here:"+user_id);
		// requestManagerDAO requestManagerDAO=new requestManagerDAO();
   	  //list_of_purchased_stocks=requestManagerDAO.fetch_all_purchased_stocks_users(user_id);
   	//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("list_of_purchased_stocks");
		 
		 return "sellStocksUser?faces-redirect=true&includeViewParams=true";
		
	}
}
