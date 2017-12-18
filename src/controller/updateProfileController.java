package controller;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import DAO.loginDAO;
import DAO.registrationDAO;
import VO.registrationVO;
import VO.updateProfileVO;
@ManagedBean(name="updateProfileController")
@SessionScoped

public class updateProfileController {

	@ManagedProperty(value = "#{updateProfileVO}")
	
	private updateProfileVO updateProfileVO=new updateProfileVO();
	public updateProfileVO getUpdateProfileVO() {
		return updateProfileVO;
	}

	public void setUpdateProfileVO(updateProfileVO updateProfileVO) {
		this.updateProfileVO = updateProfileVO;
	}
	private List<registrationVO> list;
	private String username;
	
	registrationDAO registrationDAO=new registrationDAO();
	
	public List<registrationVO> getList() {
		//FacesContext fc = FacesContext.getCurrentInstance();
		//Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		 //username=params.get("username");
		System.out.println("test:username is "+username);
		list=registrationDAO.fetch_all_user_details(username);
		System.out.println("in getlist method of controller.."+list.size());
		return list;
	}

	public void setList(List<registrationVO> list) {
		
		this.list = list;
	}

	public String updateUser()
	{   System.out.println("in update user mathod in controller..");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		 username=params.get("username");
		 String role=registrationDAO.getRole(username);
		list=registrationDAO.fetch_all_user_details(username);
		System.out.println("here....username:"+username);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("list");
		
		System.out.println("users details list="+list);
		System.out.println("size here:"+list.size());
		
		
		return "updateProfile?faces-redirect=true&includeViewParams=true";
		
	}
	public String updateManager()
	{   System.out.println("in update manager mathod in controller..");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		 username=params.get("username");
		
		list=registrationDAO.fetch_all_user_details(username);
		System.out.println("here....username:"+username);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("list");
		
		System.out.println("users details list="+list);
		System.out.println("size here:"+list.size());
		
		
		return "updateProfileManager?faces-redirect=true&includeViewParams=true";
		
	}
	public String updateUserDatabase()
	{ 
		
		   System.out.println("in method....");
		try {
			
			boolean valid =registrationDAO.updateUserData(updateProfileVO.getFirstname(),updateProfileVO.getLastname() , updateProfileVO.getContactnumber(), updateProfileVO.getEmail(), updateProfileVO.getPassword(), username);
			System.out.println("after valid line....");
			String role=registrationDAO.getRole(username);
			System.out.println("checking username here:"+username);
			System.out.println("checking role here:"+role);
			
			if(valid)
			{
			//FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			//				" Registration Successful","login now"));
				
				//FacesContext.getCurrentInstance().addMessage("myForm:newPassword1", new FacesMessage(PASSWORDS_DONT_MATCH, PASSWORDS_DONT_MATCH));	
				String message = "Registration information updated successfully !!"; 
			    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
				
				return "userProfile?faces-redirect=true&includeViewParams=true";
			}
			else
			{
				String message = "Registration information couldn't be updated!!Try Again!"; 
			    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
				return "userProfile?faces-redirect=true&includeViewParams=true";
			}
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "login?faces-redirect=true";
		}
	}
	public String updateManagerDatabase()
	{ 
		   System.out.println("in method....");
		try {
			
			boolean valid =registrationDAO.updateManagerData(updateProfileVO.getFirstname(),updateProfileVO.getLastname() , updateProfileVO.getContactnumber(), updateProfileVO.getEmail(), updateProfileVO.getPassword(),updateProfileVO.getFees(), username);
			System.out.println("after valid line....");
			String role=registrationDAO.getRole(username);
			System.out.println("checking username here:"+username);
			System.out.println("checking role here:"+role);
			
			if(valid)
			{
			//FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			//				" Registration Successful","login now"));
				
				//FacesContext.getCurrentInstance().addMessage("myForm:newPassword1", new FacesMessage(PASSWORDS_DONT_MATCH, PASSWORDS_DONT_MATCH));	
				String message = "Registration information updated successfully !!"; 
			    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
				
				return "managerProfile?faces-redirect=true&includeViewParams=true";
			}
			else
			{
				String message = "Registration information couldn't be updated!!Try Again!"; 
			    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
				return "managerProfile?faces-redirect=true&includeViewParams=true";
			}
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "login?faces-redirect=true";
		}
	}
}
