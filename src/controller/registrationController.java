package controller;
import VO.registrationVO;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import DAO.registrationDAO;
@ManagedBean(name="registrationController")
@RequestScoped

public class registrationController {
	@ManagedProperty(value = "#{registrationVO}")
	private registrationVO registrationVO;
	//registrationDAO registrationDAO=new registrationDAO();
	public registrationVO getRegistrationVO() {
		return registrationVO;
	}

	public void setRegistrationVO(registrationVO registrationVO) {
		this.registrationVO = registrationVO;
	}
 
   
	
	public String userRegistration()
	{ 
		 
		   System.out.println("in method....");
		   System.out.println("checking firstname;"+registrationVO.getFirstname());
		try {
			if(!registrationVO.getPassword().equals(registrationVO.getPassword2()))
			{
			String message = "Password doesn't match!!Try Again!"; 
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
			return "registerUser";
			}
			boolean valid =registrationDAO.insertData(registrationVO.getFirstname(), registrationVO.getLastname(), registrationVO.getContactnumber(),registrationVO.getEmail(), registrationVO.getUsername(), registrationVO.getPassword(), "user");
			System.out.println("after valid line....");
			if(valid)
			{
			//FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			//				" Registration Successful","login now"));
				
				//FacesContext.getCurrentInstance().addMessage("myForm:newPassword1", new FacesMessage(PASSWORDS_DONT_MATCH, PASSWORDS_DONT_MATCH));	
				String message = "Registration information submitted successfully !!"; 
			    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
				
				return "login";
			}
			else
			{
				String message = "Registration information couldn't be submitted!!Try Again!"; 
			    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
				return "registerUser";
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "registerUser";
		}
	}
	
	
	public String managerRegistration()
	{ 
		
		   System.out.println("in method....");
		try {
			if(!registrationVO.getPassword().equals(registrationVO.getPassword2()))
			{
			String message = "PAssword doesn't match!!Try Again!"; 
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
			return "registerManager";
			}
			boolean valid =registrationDAO.insertTempData(registrationVO.getFirstname(), registrationVO.getLastname(), registrationVO.getContactnumber(),registrationVO.getEmail(), registrationVO.getUsername(), registrationVO.getPassword(), "manager",registrationVO.getFees());
			System.out.println("after valid line....");
			if(valid)
			{
			//FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			//				" Registration Successful","login now"));
				
				//FacesContext.getCurrentInstance().addMessage("myForm:newPassword1", new FacesMessage(PASSWORDS_DONT_MATCH, PASSWORDS_DONT_MATCH));	
				String message = "Registration information submitted successfully !!"; 
			    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
				
				return "login";
			}
			else
			{
				String message = "Registration information couldn't be submitted!!Try Again!"; 
			    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
				return "registerManager";
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "registerManager";
		}
	}
	public String approveManager() throws ClassNotFoundException
	{  
		registrationDAO registrationDAO =new registrationDAO();
		System.out.println("in approve manager method..");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		int user_id=Integer.parseInt(params.get("user_id"));
		String email=registrationDAO.getEmail(user_id);
		System.out.println("email is"+email);
		System.out.println("check user id in controller method:"+user_id);
		boolean done=registrationDAO.approveManager(user_id);
		if(done)
		{
			//String email=registrationDAO.getEmail(user_id);
			System.out.println("email is"+email);
			sendEmail(email);
			
			String message = "Manager was approved suuceessfully!!"; 
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
			return "adminProfile";
		}
		else
		{
			String message = "Approval information couldn't be submitted!!Try Again!!"; 
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
			return "adminProfile";
		}
		
	}
	public String declineManager() throws ClassNotFoundException
	{  
		registrationDAO registrationDAO =new registrationDAO();
		System.out.println("in decline manager method..");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		int user_id=Integer.parseInt(params.get("user_id"));
		String email=registrationDAO.getEmail(user_id);
		System.out.println("email is"+email);
		System.out.println("check user id in controller method:"+user_id);
		boolean done=registrationDAO.declineManager(user_id);
		if(done)
		{
			//String email=registrationDAO.getEmail(user_id);
			System.out.println("email is"+email);
			sendDeclineEmail(email);
			
			String message = "Manager was declined suuceessfully!!"; 
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
			return "adminProfile";
		}
		else
		{
			String message = "Decline information couldn't be submitted!!Try Again!!"; 
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
			return "adminProfile";
		}
		
	}
	protected void sendEmail(String recieverAdreess)
	{
		  java.util.Properties properties = new java.util.Properties();
	        properties.put("mail.smtp.auth", "true");
	         properties.put("mail.smtp.starttls.enable", "true");
	         javax.mail.Session mailSession = javax.mail.Session.getInstance(properties);
	        
	        try {
	        	
	            MimeMessage message = new MimeMessage(mailSession);
	   
	           
	            message.setContent("Hello, Your request for registration has been approved by admin.  <br> Login to your account for further information.","text/html" );
	            message.setSubject("IMP:Regarding approval of a manager");
	            
	            InternetAddress sender = new InternetAddress("mystocks.se@gmail.com","Mail");
	            InternetAddress receiver = new InternetAddress(recieverAdreess);
	            message.setFrom(sender);
	            message.setRecipient(Message.RecipientType.TO, receiver);
	             message.saveChanges();
	            
	            javax.mail.Transport transport = mailSession.getTransport("smtp");
	            transport.connect("smtp.gmail.com", 587, "mystocks.se@gmail.com", "Abc12345");
	             transport.sendMessage(message, message.getAllRecipients());
	            transport.close();
	            
	                      
	        } catch (Exception e) {
	            e.printStackTrace();
	         }
	}
	protected void sendDeclineEmail(String recieverAdreess)
	{
		  java.util.Properties properties = new java.util.Properties();
	        properties.put("mail.smtp.auth", "true");
	         properties.put("mail.smtp.starttls.enable", "true");
	         javax.mail.Session mailSession = javax.mail.Session.getInstance(properties);
	        
	        try {
	        	
	            MimeMessage message = new MimeMessage(mailSession);
	   
	           
	            message.setContent("Hello, Your request for registration has been declined by admin.","text/html" );
	            message.setSubject("IMP:Regarding declining of a manager");
	            
	            InternetAddress sender = new InternetAddress("mystocks.se@gmail.com","Mail");
	            InternetAddress receiver = new InternetAddress(recieverAdreess);
	            message.setFrom(sender);
	            message.setRecipient(Message.RecipientType.TO, receiver);
	             message.saveChanges();
	            
	            javax.mail.Transport transport = mailSession.getTransport("smtp");
	            transport.connect("smtp.gmail.com", 587, "mystocks.se@gmail.com", "Abc12345");
	             transport.sendMessage(message, message.getAllRecipients());
	            transport.close();
	            
	                      
	        } catch (Exception e) {
	            e.printStackTrace();
	         }
	}
	
}
