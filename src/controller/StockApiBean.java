package controller;



import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.synth.SynthSeparatorUI;

import DAO.databaseConnectionDAO;
import DAO.registrationDAO;
import DAO.requestManagerDAO;


@ManagedBean
@SessionScoped
public class StockApiBean {

    private static final long serialVersionUID = 1L;
    static final String API_KEY = "AF93E6L5I01EA39O";
    private int user_id;
    public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	private String symbol;
    private double price;
    private float qty;
    private double amt;
    private String table1Markup;
    private String table2Markup;

    private String selectedSymbol;
    private List<SelectItem> availableSymbols;

    public String getPurchaseSymbol() {
        if (getRequestParameter("symbol") != null) {
            symbol = getRequestParameter("symbol");
        }
        return symbol;
    }
    
    public void setPurchaseSymbol(String purchaseSymbol) {
        System.out.println("func setPurchaseSymbol()");  //check
    }

    public double getPurchasePrice() {
        if (getRequestParameter("price") != null) {
            price = Double.parseDouble(getRequestParameter("price"));
            System.out.println("price: " + price);
        }
        return price;
    }

    public void setPurchasePrice(double purchasePrice) {
        System.out.println("func setPurchasePrice()");  //check
    }
    
    private String getRequestParameter(String name) {
        return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter(name);
    }

    @PostConstruct
    public void init() {
        //initially populate stock list
        availableSymbols = new ArrayList<SelectItem>();
        availableSymbols.add(new SelectItem("AAPL", "Apple"));
        availableSymbols.add(new SelectItem("AMZN", "Amazon LLC"));
        availableSymbols.add(new SelectItem("AR", "Antero Resources"));
        availableSymbols.add(new SelectItem("EBAY", "Ebay"));
        availableSymbols.add(new SelectItem("FB", "Facebook, Inc."));
        availableSymbols.add(new SelectItem("GOLD", "Gold"));
        availableSymbols.add(new SelectItem("GOOGL", "Google"));
        availableSymbols.add(new SelectItem("MSFT", "Microsoft"));
        availableSymbols.add(new SelectItem("SLV", "Silver"));
        availableSymbols.add(new SelectItem("TWTR", "Twitter, Inc."));

        //initially populate intervals for stock api
        availableIntervals = new ArrayList<SelectItem>();
        availableIntervals.add(new SelectItem("1min", "1min"));
        availableIntervals.add(new SelectItem("5min", "5min"));
        availableIntervals.add(new SelectItem("15min", "15min"));
        availableIntervals.add(new SelectItem("30min", "30min"));
        availableIntervals.add(new SelectItem("60min", "60min"));
    }

    private String selectedInterval;
    private List<SelectItem> availableIntervals;

    public String getSelectedInterval() {
        return selectedInterval;
    }

    public void setSelectedInterval(String selectedInterval) {
        this.selectedInterval = selectedInterval;
    }

    public List<SelectItem> getAvailableIntervals() {
        return availableIntervals;
    }

    public void setAvailableIntervals(List<SelectItem> availableIntervals) {
        this.availableIntervals = availableIntervals;
    }

    public String getSelectedSymbol() {
        return selectedSymbol;
    }

    public void setSelectedSymbol(String selectedSymbol) {
        this.selectedSymbol = selectedSymbol;
    }

    public List<SelectItem> getAvailableSymbols() {
        return availableSymbols;
    }

    public void setAvailableSymbols(List<SelectItem> availableSymbols) {
        this.availableSymbols = availableSymbols;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public String getTable1Markup() {
        return table1Markup;
    }

    public void setTable1Markup(String table1Markup) {
        this.table1Markup = table1Markup;
    }

    public String getTable2Markup() {
        return table2Markup;
    }

    public void setTable2Markup(String table2Markup) {
        this.table2Markup = table2Markup;
    }

    public String createDbRecord(String symbol, double price, int qty, double amt) {
        try {
            //System.out.println("symbol: " + this.symbol + ", price: " + this.price + "\n");
            //System.out.println("qty: " + this.qty + ", amt: " + this.amt + "\n");
        	registrationDAO registrationDAO=new registrationDAO();
            Connection conn = databaseConnectionDAO.getConnection();
            Statement statement = conn.createStatement();
            
            //get userid
            FacesContext fc = FacesContext.getCurrentInstance();
    		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
    		 String username=params.get("username");
    		 String role=registrationDAO.getRole(username);
    		 
    			 
    		 int uid=registrationDAO.getUser_id(username);
    		 System.out.println("here user_id is:"+uid);
    		 
            float balance=registrationDAO.getBalance(username);
            System.out.println(uid);
            System.out.println("symbol:" + symbol);
            System.out.println("price:" + price);
            System.out.println("qty:" + qty);
            System.out.println("amt:" + amt);
            System.out.println("balance:"+balance);
            boolean abc=amt>balance;
            System.out.println("boolean:"+abc);
            
            	if(balance>=amt)
            	{
            		String query1="INSERT INTO purchase(USER_ID,STOCK_NAME,QUANTITY,PRICE,AMOUNT,DATE) VALUES ('"+uid+"','"+symbol+"','"+qty+"','"+price+"','"+amt+"',now())";
            		String query2="update mystocks.user set balance=balance-'"+amt+"' where username='"+username+"'";
         //   statement.executeUpdate("INSERT INTO `purchase` ( `user_id`, `stock_name`, `quantity`, `price`, `amount`) "
           //         + "VALUES ('" + uid + "','" + symbol + "','" + qty + "','" + price + "','" + amt +"')");
            statement.executeUpdate(query1);
            statement.executeUpdate(query2);
            
            statement.close();
            databaseConnectionDAO.close(conn);
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully purchased stock",""));
    		 
            	}
            	else
            	{
            		 statement.close();
                     databaseConnectionDAO.close(conn);
                     FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Not sufficient balance!!",""));
            	}
            	
            
    		 
    	 } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return "purchase";
    }
    public String createDbRecordSellingUser(String symbol, double price, int qty, double amt) {
        try {
            //System.out.println("symbol: " + this.symbol + ", price: " + this.price + "\n");
            //System.out.println("qty: " + this.qty + ", amt: " + this.amt + "\n");
        	registrationDAO registrationDAO=new registrationDAO();
        	requestManagerDAO requestManagerDAO=new requestManagerDAO();
            Connection conn = databaseConnectionDAO.getConnection();
            Statement statement = conn.createStatement();
            
            //get userid
            FacesContext fc = FacesContext.getCurrentInstance();
    		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
    		 String username=params.get("username");
    		 String role=registrationDAO.getRole(username);
    		 
    			 
    		 int uid=registrationDAO.getUser_id(username);
    		 System.out.println("here user_id in purchase db method is:"+uid);
    		 
            float quantity=requestManagerDAO.getQuanitity(uid,symbol);
            System.out.println(uid);
            System.out.println("symbol:" + symbol);
            System.out.println("price:" + price);
            System.out.println("qty:" + qty);
            System.out.println("amt:" + amt);
            System.out.println("quanitity:"+quantity);
            
            //System.out.println("boolean:"+abc);
            
            	if(qty<=quantity)
            	{
            		String query="INSERT INTO SELL(USER_ID,STOCK_NAME,QUANTITY,PRICE,AMOUNT,DATE) VALUES ('"+uid+"','"+symbol+"','"+qty+"','"+price+"','"+amt+"',now())";
            		String query2="update mystocks.user set balance=balance+'"+amt+"' where username='"+username+"'";
            		String query3="update mystocks.purchase set quantity=quantity-'"+qty+"',amount=price*quantity where user_id='"+uid+"' and stock_name='"+symbol+"' ";
          //  statement.executeUpdate("INSERT INTO `sell` ( `user_id`, `stock_name`, `quantity`, `price`, `amount`,'date') "
                 //   + "VALUES ('" + uid + "','" + symbol + "','" + qty + "','" + price + "','" + amt + "','now()')");
            //statement.executeUpdate(query2);
            statement.executeUpdate(query);
            statement.executeUpdate(query2);
            statement.executeUpdate(query3);
            statement.close();
            databaseConnectionDAO.close(conn);
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully sold stock",""));
    		 
            	}
            	else
            	{
            		 statement.close();
                     databaseConnectionDAO.close(conn);
                     FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Yoc can't sell stocks more than you have!!",""));
            	}
            	
            
    		 
    	 } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return "sellStocksUser_final";
    }
    public String createDbRecordSellingManager(String symbol, double price, int qty, double amt,int user_id) {
        try {
            //System.out.println("symbol: " + this.symbol + ", price: " + this.price + "\n");
            //System.out.println("qty: " + this.qty + ", amt: " + this.amt + "\n");
        	registrationDAO registrationDAO=new registrationDAO();
        	requestManagerDAO requestManagerDAO=new requestManagerDAO();
            Connection conn = databaseConnectionDAO.getConnection();
            Statement statement = conn.createStatement();
            
            //get userid
            FacesContext fc = FacesContext.getCurrentInstance();
    		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
    		 String username=params.get("username");
    		 String role=registrationDAO.getRole(username);
    		 int manager_id=registrationDAO.getUser_id(username);
    		 float quantity=requestManagerDAO.getQuanitity(user_id,symbol);
    		 
    		 System.out.println("here user_id in purchase db method user id is:"+user_id);
    		 System.out.println("manager id is:"+manager_id);
    		 
//            float quantity=requestManagerDAO.getQuanitity(user_id,symbol);
            System.out.println("user id here is :"+user_id);
            System.out.println("manager id here is:"+manager_id);
            System.out.println("symbol:" + symbol);
            System.out.println("price:" + price);
            System.out.println("qty:" + qty);
            System.out.println("amt:" + amt);
            System.out.println("quanitity:"+quantity);
            float fees=registrationDAO.getFees(manager_id);
            System.out.println("fees:"+fees);
            
            //System.out.println("boolean:"+abc);
            
            	if(qty<=quantity)
            	{
            		String query="INSERT INTO SELL(USER_ID,MANAGER_ID,STOCK_NAME,QUANTITY,PRICE,AMOUNT,DATE) VALUES ('"+user_id+"','"+manager_id+"','"+symbol+"','"+qty+"','"+price+"','"+amt+"',now())";
            		String query2="update mystocks.user set balance=balance+'"+amt+"' where user_id='"+user_id+"'";
            		String query3="update mystocks.purchase set quantity=quantity-'"+qty+"',amount=price*quantity where user_id='"+user_id+"' and stock_name='"+symbol+"' ";
            		String query4="update mystocks.user set balance=balance+'"+fees+"' where user_id='"+manager_id+"'";
            		//  statement.executeUpdate("INSERT INTO `sell` ( `user_id`, `stock_name`, `quantity`, `price`, `amount`,'date') "
                 //   + "VALUES ('" + uid + "','" + symbol + "','" + qty + "','" + price + "','" + amt + "','now()')");
            //statement.executeUpdate(query2);
            statement.executeUpdate(query);
            statement.executeUpdate(query2);
            statement.executeUpdate(query3);
            statement.executeUpdate(query4);
            statement.close();
            databaseConnectionDAO.close(conn);
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully sold stock",""));
    		 
            	}
            	else
            	{
            		 statement.close();
                     databaseConnectionDAO.close(conn);
                     FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Yoc can't sell stocks more than you have!!",""));
            	}
            	
            
    		 
    	 } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return "sellStocksUser_final";
    }

    public String watchListUser() throws Exception {
       // try {
            //System.out.println("symbol: " + this.symbol + ", price: " + this.price + "\n");
            //System.out.println("qty: " + this.qty + ", amt: " + this.amt + "\n");
        	registrationDAO registrationDAO=new registrationDAO();
        	requestManagerDAO requestManagerDAO=new requestManagerDAO();
            Connection conn = databaseConnectionDAO.getConnection();
            Statement statement = conn.createStatement();
            
            //get userid
            FacesContext fc = FacesContext.getCurrentInstance();
    		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
    		 String username=params.get("username");
    		 //String role=registrationDAO.getRole(username);
    		 int user_id=registrationDAO.getUser_id(username);
    		 //float quantity=requestManagerDAO.getQuanitity(user_id,symbol);
    		 
    		 System.out.println("here user_id in purchase db method user id is:"+user_id);
    		// System.out.println("manager id is:"+manager_id);
    		 
//            float quantity=requestManagerDAO.getQuanitity(user_id,symbol);
            System.out.println("user id here is :"+user_id);
            //System.out.println("manager id here is:"+manager_id);
            System.out.println("symbol:" + selectedSymbol);
            String query="INSERT INTO watchlist(user_id,stock_name) value('"+user_id+"','"+selectedSymbol+"')";
            statement.executeUpdate(query);
            statement.close();
            databaseConnectionDAO.close(conn);
            String message = "Added to watchlist successfully!!"; 
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
          
            
            	return "userProfile";
    }
    public String watchListManager() throws Exception {
        // try {
             //System.out.println("symbol: " + this.symbol + ", price: " + this.price + "\n");
             //System.out.println("qty: " + this.qty + ", amt: " + this.amt + "\n");
         	registrationDAO registrationDAO=new registrationDAO();
         	requestManagerDAO requestManagerDAO=new requestManagerDAO();
             Connection conn = databaseConnectionDAO.getConnection();
             Statement statement = conn.createStatement();
             
             //get userid
             FacesContext fc = FacesContext.getCurrentInstance();
     		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
     		 String username=params.get("username");
     		 //String role=registrationDAO.getRole(username);
     		 int user_id=registrationDAO.getUser_id(username);
     		 //float quantity=requestManagerDAO.getQuanitity(user_id,symbol);
     		 
     		 System.out.println("here user_id in purchase db method user id is:"+user_id);
     		// System.out.println("manager id is:"+manager_id);
     		 
//             float quantity=requestManagerDAO.getQuanitity(user_id,symbol);
             System.out.println("user id here is :"+user_id);
             //System.out.println("manager id here is:"+manager_id);
             System.out.println("symbol:" + selectedSymbol);
             String query="INSERT INTO watchlist(user_id,stock_name) value('"+user_id+"','"+selectedSymbol+"')";
             statement.executeUpdate(query);
             statement.close();
             databaseConnectionDAO.close(conn);
             String message = "Added to watchlist successfully!!"; 
 		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
           
             
             	return "managerProfile";
     }

  
  

    public String createDbRecordForManager(String symbol, double price, int qty, double amt,int user_id) {
        try {
            //System.out.println("symbol: " + this.symbol + ", price: " + this.price + "\n");
            //System.out.println("qty: " + this.qty + ", amt: " + this.amt + "\n");
        	registrationDAO registrationDAO=new registrationDAO();
            Connection conn = databaseConnectionDAO.getConnection();
            Statement statement = conn.createStatement();
            
            //get userid
            FacesContext fc = FacesContext.getCurrentInstance();
    		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
    		 String username=params.get("username");
    		 String role=registrationDAO.getRole(username);
    		 
    			 
    		 int manager_id=registrationDAO.getUser_id(username);
    		 System.out.println("here manaer_id is:"+manager_id);
    		 
           // float balance=registrationDAO.getBalance(username);
            System.out.println(manager_id);
            System.out.println("symbol:" + symbol);
            System.out.println("price:" + price);
            System.out.println("qty:" + qty);
            System.out.println("amt:" + amt);
            System.out.println("user_id:"+user_id);
            
            float fees=registrationDAO.getFees(manager_id);
            System.out.println("fees:"+fees);
            requestManagerDAO requestManagerDAO=new requestManagerDAO();
            float assignedAmmount=requestManagerDAO.getAssigned_amount(user_id, manager_id);
            System.out.println("assigned ammount is:"+assignedAmmount);
            //boolean abc=amt>balance;
            //System.out.println("boolean:"+abc);
            amt=amt+fees;
            System.out.println("final amout is"+amt);
            	if(assignedAmmount>=amt)
            	{
            		
            		String query1="INSERT INTO purchase(user_id,manager_id, stock_name, quantity, price, amount,date) VALUES ('" + user_id + "','"+manager_id+"','" + symbol + "','" + qty + "','" + price + "','" + amt +"',now())";
            		String query2="update mystocks.user set balance=balance-'"+amt+"' where user_id='"+user_id+"'";
            		String query3="update mystocks.user set balance=balance+'"+fees+"' where user_id='"+manager_id+"'";
            		String query4="update  mystocks.manager_assignment set amount=amount-'"+amt+"' where manager_id='"+manager_id+"' and user_id='"+user_id+"'";
           // statement.executeUpdate("INSERT INTO `purchase` ( `user_id`,`manager_id`, `stock_name`, `quantity`, `price`, `amount`,'date') "
             //       + "VALUES ('" + user_id + "','"+manager_id+"','" + symbol + "','" + qty + "','" + price + "','" + amt +"',now())");
          // statement.executeUpdate(query1);
            statement.executeUpdate(query1);
            statement.executeUpdate(query2);
            statement.executeUpdate(query3);
            statement.executeUpdate(query4);
            
            statement.close();
            databaseConnectionDAO.close(conn);
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully purchased stock",""));
    		 
            	}
            	else
            	{
            		 statement.close();
                     databaseConnectionDAO.close(conn);
                     FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Not sufficient balance!!",""));
            	}
            	
            
    		 
    	 } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return "purchaseForManager";
    }

    public void installAllTrustingManager() {
        TrustManager[] trustAllCerts;
        trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            System.out.println("Exception :" + e);
        }
        return;
    }

    public void timeseries() throws MalformedURLException, IOException {

        installAllTrustingManager();

        //System.out.println("selectedItem: " + this.selectedSymbol);
        //System.out.println("selectedInterval: " + this.selectedInterval);
        String symbol = this.selectedSymbol;
        String interval = this.selectedInterval;
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval=" + interval + "&apikey=" + API_KEY;

        this.table1Markup += "URL::: <a href='" + url + "'>Data Link</a><br>";
        InputStream inputStream = new URL(url).openStream();

        // convert the json string back to object
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject mainJsonObj = jsonReader.readObject();
        for (String key : mainJsonObj.keySet()) {
            if (key.equals("Meta Data")) {
                this.table1Markup = null; // reset table 1 markup before repopulating
                JsonObject jsob = (JsonObject) mainJsonObj.get(key);
                this.table1Markup += "<style>#detail >tbody > tr > td{ text-align:center;}</style><b>Stock Details</b>:<br>";
                this.table1Markup += "<table>";
                this.table1Markup += "<tr><td>Information</td><td>" + jsob.getString("1. Information") + "</td></tr>";
                this.table1Markup += "<tr><td>Symbol</td><td>" + jsob.getString("2. Symbol") + "</td></tr>";
                this.table1Markup += "<tr><td>Last Refreshed</td><td>" + jsob.getString("3. Last Refreshed") + "</td></tr>";
                this.table1Markup += "<tr><td>Interval</td><td>" + jsob.getString("4. Interval") + "</td></tr>";
                this.table1Markup += "<tr><td>Output Size</td><td>" + jsob.getString("5. Output Size") + "</td></tr>";
                this.table1Markup += "<tr><td>Time Zone</td><td>" + jsob.getString("6. Time Zone") + "</td></tr>";
                this.table1Markup += "</table>";
            } else {
                this.table2Markup = null; // reset table 2 markup before repopulating
                JsonObject dataJsonObj = mainJsonObj.getJsonObject(key);
                this.table2Markup += "<table class='table table-hover'>";
                this.table2Markup += "<thead><tr><th>Timestamp</th><th>Open</th><th>High</th><th>Low</th><th>Close</th><th>Volume</th></tr></thead>";
                this.table2Markup += "<tbody>";
                int i = 0;
                for (String subKey : dataJsonObj.keySet()) {
                    JsonObject subJsonObj = dataJsonObj.getJsonObject(subKey);
                    this.table2Markup
                            += "<tr>"
                            + "<td>" + subKey + "</td>"
                            + "<td>" + subJsonObj.getString("1. open") + "</td>"
                            + "<td>" + subJsonObj.getString("2. high") + "</td>"
                            + "<td>" + subJsonObj.getString("3. low") + "</td>"
                            + "<td>" + subJsonObj.getString("4. close") + "</td>"
                            + "<td>" + subJsonObj.getString("5. volume") + "</td>";
                    if (i == 0) {
                        String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                        this.table2Markup += "<td><a class='btn btn-success' href='" + path + "/purchase.xhtml?symbol=" + symbol + "&price=" + subJsonObj.getString("4. close") + "'>Buy Stock</a></td>";
                    }
                    this.table2Markup += "</tr>";
                    i++;
                }
                this.table2Markup += "</tbody></table>";
            }
        }
        return;
    }
    public String timeseriesForSellingUser() throws MalformedURLException, IOException {

    	String currentPrice=null;
        installAllTrustingManager();

        //System.out.println("selectedItem: " + this.selectedSymbol);
        //System.out.println("selectedInterval: " + this.selectedInterval);
        FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		String symbol=params.get("stock_name");
		
		 qty=Float.parseFloat(params.get("quantity"));
		System.out.println("stock name here:"+symbol);
		System.out.println("quantity here:"+qty);
     //   String symbol = this.selectedSymbol;
        String interval = "1min";
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval=" + interval + "&apikey=" + API_KEY;

        this.table1Markup += "URL::: <a href='" + url + "'>Data Link</a><br>";
        InputStream inputStream = new URL(url).openStream();

        // convert the json string back to object
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject mainJsonObj = jsonReader.readObject();
        for (String key : mainJsonObj.keySet()) {
            if (key.equals("Meta Data")) {
                this.table1Markup = null; // reset table 1 markup before repopulating
                JsonObject jsob = (JsonObject) mainJsonObj.get(key);
                this.table1Markup += "<style>#detail >tbody > tr > td{ text-align:center;}</style><b>Stock Details</b>:<br>";
                this.table1Markup += "<table>";
                this.table1Markup += "<tr><td>Information</td><td>" + jsob.getString("1. Information") + "</td></tr>";
                this.table1Markup += "<tr><td>Symbol</td><td>" + jsob.getString("2. Symbol") + "</td></tr>";
                this.table1Markup += "<tr><td>Last Refreshed</td><td>" + jsob.getString("3. Last Refreshed") + "</td></tr>";
                this.table1Markup += "<tr><td>Interval</td><td>" + jsob.getString("4. Interval") + "</td></tr>";
                this.table1Markup += "<tr><td>Output Size</td><td>" + jsob.getString("5. Output Size") + "</td></tr>";
                this.table1Markup += "<tr><td>Time Zone</td><td>" + jsob.getString("6. Time Zone") + "</td></tr>";
                this.table1Markup += "</table>";
            } else {
                this.table2Markup = null; // reset table 2 markup before repopulating
                JsonObject dataJsonObj = mainJsonObj.getJsonObject(key);
                this.table2Markup += "<table class='table table-hover'>";
                this.table2Markup += "<thead><tr><th>Timestamp</th><th>Open</th><th>High</th><th>Low</th><th>Close</th><th>Volume</th></tr></thead>";
                this.table2Markup += "<tbody>";
                int i = 0;
                for (String subKey : dataJsonObj.keySet()) {
                    JsonObject subJsonObj = dataJsonObj.getJsonObject(subKey);
                    this.table2Markup
                            += "<tr>"
                            + "<td>" + subKey + "</td>"
                            + "<td>" + subJsonObj.getString("1. open") + "</td>"
                            + "<td>" + subJsonObj.getString("2. high") + "</td>"
                            + "<td>" + subJsonObj.getString("3. low") + "</td>"
                            + "<td>" + subJsonObj.getString("4. close") + "</td>"
                            + "<td>" + subJsonObj.getString("5. volume") + "</td>";
                             
                    if (i == 0) {
                        String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                        this.table2Markup += "<td><a class='btn btn-success' href='" + path + "/sellStocksUser_final.xhtml?symbol=" + symbol + "&price=" + subJsonObj.getString("4. close") + "'>Sell Stocks</a></td>";
                  
                    }
                    this.table2Markup += "</tr>";
                    i++;
                    currentPrice= subJsonObj.getString("4. close");
                    break;
                }
                this.table2Markup += "</tbody></table>";
            }
        }
        return currentPrice;
    }
    public String timeseriesForWatchlistUser() throws MalformedURLException, IOException {

    	String currentPrice=null;
        installAllTrustingManager();

        //System.out.println("selectedItem: " + this.selectedSymbol);
        //System.out.println("selectedInterval: " + this.selectedInterval);
        FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		String symbol=params.get("stock_name");
		
		 qty=Float.parseFloat(params.get("quantity"));
		System.out.println("stock name here:"+symbol);
		System.out.println("quantity here:"+qty);
     //   String symbol = this.selectedSymbol;
        String interval = "1min";
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval=" + interval + "&apikey=" + API_KEY;

        this.table1Markup += "URL::: <a href='" + url + "'>Data Link</a><br>";
        InputStream inputStream = new URL(url).openStream();

        // convert the json string back to object
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject mainJsonObj = jsonReader.readObject();
        for (String key : mainJsonObj.keySet()) {
            if (key.equals("Meta Data")) {
                this.table1Markup = null; // reset table 1 markup before repopulating
                JsonObject jsob = (JsonObject) mainJsonObj.get(key);
                this.table1Markup += "<style>#detail >tbody > tr > td{ text-align:center;}</style><b>Stock Details</b>:<br>";
                this.table1Markup += "<table>";
                this.table1Markup += "<tr><td>Information</td><td>" + jsob.getString("1. Information") + "</td></tr>";
                this.table1Markup += "<tr><td>Symbol</td><td>" + jsob.getString("2. Symbol") + "</td></tr>";
                this.table1Markup += "<tr><td>Last Refreshed</td><td>" + jsob.getString("3. Last Refreshed") + "</td></tr>";
                this.table1Markup += "<tr><td>Interval</td><td>" + jsob.getString("4. Interval") + "</td></tr>";
                this.table1Markup += "<tr><td>Output Size</td><td>" + jsob.getString("5. Output Size") + "</td></tr>";
                this.table1Markup += "<tr><td>Time Zone</td><td>" + jsob.getString("6. Time Zone") + "</td></tr>";
                this.table1Markup += "</table>";
            } else {
                this.table2Markup = null; // reset table 2 markup before repopulating
                JsonObject dataJsonObj = mainJsonObj.getJsonObject(key);
                this.table2Markup += "<table class='table table-hover'>";
                this.table2Markup += "<thead><tr><th>Timestamp</th><th>Open</th><th>High</th><th>Low</th><th>Close</th><th>Volume</th></tr></thead>";
                this.table2Markup += "<tbody>";
                int i = 0;
                for (String subKey : dataJsonObj.keySet()) {
                    JsonObject subJsonObj = dataJsonObj.getJsonObject(subKey);
                    this.table2Markup
                            += "<tr>"
                            + "<td>" + subKey + "</td>"
                            + "<td>" + subJsonObj.getString("1. open") + "</td>"
                            + "<td>" + subJsonObj.getString("2. high") + "</td>"
                            + "<td>" + subJsonObj.getString("3. low") + "</td>"
                            + "<td>" + subJsonObj.getString("4. close") + "</td>"
                            + "<td>" + subJsonObj.getString("5. volume") + "</td>";
                             
                    if (i == 0) {
                      //  String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                       // this.table2Markup += "<td><a class='btn btn-success' href='" + path + "/sellStocksUser_final.xhtml?symbol=" + symbol + "&price=" + subJsonObj.getString("4. close") + "'>Sell Stocks</a></td>";
                  
                    }
                    this.table2Markup += "</tr>";
                    i++;
                    currentPrice= subJsonObj.getString("4. close");
                    //break;
                }
                this.table2Markup += "</tbody></table>";
            }
        }
        return currentPrice;
    }
    public String timeseriesForWatchlistManager() throws MalformedURLException, IOException {
         System.out.println("method called...");
    	String currentPrice=null;
        installAllTrustingManager();

        //System.out.println("selectedItem: " + this.selectedSymbol);
        //System.out.println("selectedInterval: " + this.selectedInterval);
        FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		String symbol=params.get("stock_name");
		
		 qty=Float.parseFloat(params.get("quantity"));
		System.out.println("stock name here:"+symbol);
		System.out.println("quantity here:"+qty);
     //   String symbol = this.selectedSymbol;
        String interval = "1min";
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval=" + interval + "&apikey=" + API_KEY;

        this.table1Markup += "URL::: <a href='" + url + "'>Data Link</a><br>";
        InputStream inputStream = new URL(url).openStream();

        // convert the json string back to object
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject mainJsonObj = jsonReader.readObject();
        for (String key : mainJsonObj.keySet()) {
            if (key.equals("Meta Data")) {
                this.table1Markup = null; // reset table 1 markup before repopulating
                JsonObject jsob = (JsonObject) mainJsonObj.get(key);
                this.table1Markup += "<style>#detail >tbody > tr > td{ text-align:center;}</style><b>Stock Details</b>:<br>";
                this.table1Markup += "<table>";
                this.table1Markup += "<tr><td>Information</td><td>" + jsob.getString("1. Information") + "</td></tr>";
                this.table1Markup += "<tr><td>Symbol</td><td>" + jsob.getString("2. Symbol") + "</td></tr>";
                this.table1Markup += "<tr><td>Last Refreshed</td><td>" + jsob.getString("3. Last Refreshed") + "</td></tr>";
                this.table1Markup += "<tr><td>Interval</td><td>" + jsob.getString("4. Interval") + "</td></tr>";
                this.table1Markup += "<tr><td>Output Size</td><td>" + jsob.getString("5. Output Size") + "</td></tr>";
                this.table1Markup += "<tr><td>Time Zone</td><td>" + jsob.getString("6. Time Zone") + "</td></tr>";
                this.table1Markup += "</table>";
            } else {
                this.table2Markup = null; // reset table 2 markup before repopulating
                JsonObject dataJsonObj = mainJsonObj.getJsonObject(key);
                this.table2Markup += "<table class='table table-hover'>";
                this.table2Markup += "<thead><tr><th>Timestamp</th><th>Open</th><th>High</th><th>Low</th><th>Close</th><th>Volume</th></tr></thead>";
                this.table2Markup += "<tbody>";
                int i = 0;
                for (String subKey : dataJsonObj.keySet()) {
                    JsonObject subJsonObj = dataJsonObj.getJsonObject(subKey);
                    this.table2Markup
                            += "<tr>"
                            + "<td>" + subKey + "</td>"
                            + "<td>" + subJsonObj.getString("1. open") + "</td>"
                            + "<td>" + subJsonObj.getString("2. high") + "</td>"
                            + "<td>" + subJsonObj.getString("3. low") + "</td>"
                            + "<td>" + subJsonObj.getString("4. close") + "</td>"
                            + "<td>" + subJsonObj.getString("5. volume") + "</td>";
                             
                    if (i == 0) {
                      //  String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                       // this.table2Markup += "<td><a class='btn btn-success' href='" + path + "/sellStocksUser_final.xhtml?symbol=" + symbol + "&price=" + subJsonObj.getString("4. close") + "'>Sell Stocks</a></td>";
                  
                    }
                    this.table2Markup += "</tr>";
                    i++;
                    currentPrice= subJsonObj.getString("4. close");
                    //break;
                }
                this.table2Markup += "</tbody></table>";
            }
        }
        return currentPrice;
    }
    public String timeseriesForSellingManager() throws MalformedURLException, IOException {

    	String currentPrice=null;
        installAllTrustingManager();

        //System.out.println("selectedItem: " + this.selectedSymbol);
        //System.out.println("selectedInterval: " + this.selectedInterval);
        FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		String symbol=params.get("stock_name");
		
		 qty=Float.parseFloat(params.get("quantity"));
		System.out.println("stock name here:"+symbol);
		System.out.println("quantity here:"+qty);
     //   String symbol = this.selectedSymbol;
        String interval = "1min";
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval=" + interval + "&apikey=" + API_KEY;

        this.table1Markup += "URL::: <a href='" + url + "'>Data Link</a><br>";
        InputStream inputStream = new URL(url).openStream();

        // convert the json string back to object
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject mainJsonObj = jsonReader.readObject();
        for (String key : mainJsonObj.keySet()) {
            if (key.equals("Meta Data")) {
                this.table1Markup = null; // reset table 1 markup before repopulating
                JsonObject jsob = (JsonObject) mainJsonObj.get(key);
                this.table1Markup += "<style>#detail >tbody > tr > td{ text-align:center;}</style><b>Stock Details</b>:<br>";
                this.table1Markup += "<table>";
                this.table1Markup += "<tr><td>Information</td><td>" + jsob.getString("1. Information") + "</td></tr>";
                this.table1Markup += "<tr><td>Symbol</td><td>" + jsob.getString("2. Symbol") + "</td></tr>";
                this.table1Markup += "<tr><td>Last Refreshed</td><td>" + jsob.getString("3. Last Refreshed") + "</td></tr>";
                this.table1Markup += "<tr><td>Interval</td><td>" + jsob.getString("4. Interval") + "</td></tr>";
                this.table1Markup += "<tr><td>Output Size</td><td>" + jsob.getString("5. Output Size") + "</td></tr>";
                this.table1Markup += "<tr><td>Time Zone</td><td>" + jsob.getString("6. Time Zone") + "</td></tr>";
                this.table1Markup += "</table>";
            } else {
                this.table2Markup = null; // reset table 2 markup before repopulating
                JsonObject dataJsonObj = mainJsonObj.getJsonObject(key);
                this.table2Markup += "<table class='table table-hover'>";
                this.table2Markup += "<thead><tr><th>Timestamp</th><th>Open</th><th>High</th><th>Low</th><th>Close</th><th>Volume</th></tr></thead>";
                this.table2Markup += "<tbody>";
                int i = 0;
                for (String subKey : dataJsonObj.keySet()) {
                    JsonObject subJsonObj = dataJsonObj.getJsonObject(subKey);
                    this.table2Markup
                            += "<tr>"
                            + "<td>" + subKey + "</td>"
                            + "<td>" + subJsonObj.getString("1. open") + "</td>"
                            + "<td>" + subJsonObj.getString("2. high") + "</td>"
                            + "<td>" + subJsonObj.getString("3. low") + "</td>"
                            + "<td>" + subJsonObj.getString("4. close") + "</td>"
                            + "<td>" + subJsonObj.getString("5. volume") + "</td>";
                             
                    if (i == 0) {
                        String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                        this.table2Markup += "<td><a class='btn btn-success' href='" + path + "/sellStocksManager.xhtml?symbol=" + symbol + "&price=" + subJsonObj.getString("4. close") + "'>Sell Stocks</a></td>";
                  
                    }
                    this.table2Markup += "</tr>";
                    i++;
                    currentPrice= subJsonObj.getString("4. close");
                    break;
                }
                this.table2Markup += "</tbody></table>";
            }
        }
        return currentPrice;
    }

    public void timeseriesManager() throws MalformedURLException, IOException {

        installAllTrustingManager();

        //System.out.println("selectedItem: " + this.selectedSymbol);
        //System.out.println("selectedInterval: " + this.selectedInterval);
        String symbol = this.selectedSymbol;
        String interval = this.selectedInterval;
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval=" + interval + "&apikey=" + API_KEY;

        this.table1Markup += "URL::: <a href='" + url + "'>Data Link</a><br>";
        InputStream inputStream = new URL(url).openStream();

        // convert the json string back to object
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject mainJsonObj = jsonReader.readObject();
        for (String key : mainJsonObj.keySet()) {
            if (key.equals("Meta Data")) {
                this.table1Markup = null; // reset table 1 markup before repopulating
                JsonObject jsob = (JsonObject) mainJsonObj.get(key);
                this.table1Markup += "<style>#detail >tbody > tr > td{ text-align:center;}</style><b>Stock Details</b>:<br>";
                this.table1Markup += "<table>";
                this.table1Markup += "<tr><td>Information</td><td>" + jsob.getString("1. Information") + "</td></tr>";
                this.table1Markup += "<tr><td>Symbol</td><td>" + jsob.getString("2. Symbol") + "</td></tr>";
                this.table1Markup += "<tr><td>Last Refreshed</td><td>" + jsob.getString("3. Last Refreshed") + "</td></tr>";
                this.table1Markup += "<tr><td>Interval</td><td>" + jsob.getString("4. Interval") + "</td></tr>";
                this.table1Markup += "<tr><td>Output Size</td><td>" + jsob.getString("5. Output Size") + "</td></tr>";
                this.table1Markup += "<tr><td>Time Zone</td><td>" + jsob.getString("6. Time Zone") + "</td></tr>";
                this.table1Markup += "</table>";
            } else {
                this.table2Markup = null; // reset table 2 markup before repopulating
                JsonObject dataJsonObj = mainJsonObj.getJsonObject(key);
                this.table2Markup += "<table class='table table-hover'>";
                this.table2Markup += "<thead><tr><th>Timestamp</th><th>Open</th><th>High</th><th>Low</th><th>Close</th><th>Volume</th></tr></thead>";
                this.table2Markup += "<tbody>";
                int i = 0;
                for (String subKey : dataJsonObj.keySet()) {
                    JsonObject subJsonObj = dataJsonObj.getJsonObject(subKey);
                    this.table2Markup
                            += "<tr>"
                            + "<td>" + subKey + "</td>"
                            + "<td>" + subJsonObj.getString("1. open") + "</td>"
                            + "<td>" + subJsonObj.getString("2. high") + "</td>"
                            + "<td>" + subJsonObj.getString("3. low") + "</td>"
                            + "<td>" + subJsonObj.getString("4. close") + "</td>"
                            + "<td>" + subJsonObj.getString("5. volume") + "</td>";
                    if (i == 0) {
                        String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                        this.table2Markup += "<td><a class='btn btn-success' href='" + path + "/purchaseForManager.xhtml?symbol=" + symbol + "&price=" + subJsonObj.getString("4. close") + "'>Buy Stock</a></td>";
                    }
                    this.table2Markup += "</tr>";
                    i++;
                }
                this.table2Markup += "</tbody></table>";
            }
        }
        return;
    }

    public void purchaseStock() {
        System.out.println("Calling function purchaseStock()");
        System.out.println("stockSymbol: " + FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("stockSymbol"));
        System.out.println("stockPrice" + FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("stockPrice"));
        return;
    }
}
