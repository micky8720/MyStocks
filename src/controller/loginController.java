package controller;

import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import DAO.loginDAO;
import DAO.registrationDAO;
import VO.registrationVO;

@ManagedBean(name="loginController")
@SessionScoped

public class loginController {
	@ManagedProperty(value = "#{loginVO}")
	private List<registrationVO> list;
	
	registrationDAO registrationDAO=new registrationDAO();
	private VO.loginVO loginVO=new VO.loginVO();
	
	public List<registrationVO> getList() {
		list=registrationDAO.fetch_all_applied_managers();
		return list;
	}
	public void setList(List<registrationVO> list) {
		this.list = list;
	}
	
	
	
	public String validateLogin()
	{
		
		if(loginVO.getUsername().equals("admin") && loginVO.getPassword().equals("admin"))
		{
		  list=registrationDAO.fetch_all_applied_managers();
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("list");
		  System.out.println("test:"+list.get(0));
		  
		  
		  
			
			return "adminProfile?faces-redirect=true&includeViewParams=true";
		}
		else
		{
		boolean result=loginDAO.validate(loginVO.getUsername(), loginVO.getPassword()); 
		if(result)
		{
			if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("role").equals("user"))
				
			{
			System.out.println("role:"+ FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("role"));
			System.out.println("checking username:"+loginVO.getUsername());
			return "userProfile?faces-redirect=true";
			}
			else if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("role").equals("manager"))
			{
				return "managerProfile?faces-redirect=true&includeViewParams=true";
			}
			else {
				return null;
			}
		}
		else {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Either username or password is wrong. Please enter correct username and Password",""));
            return "login?faces-redirect=true&includeViewParams=true";
        }
		}
		
	}
	 public VO.loginVO getLoginVO() {
		return loginVO;
	}
	public void setLoginVO(VO.loginVO loginVO) {
		this.loginVO = loginVO;
	}
	public String logout() throws IOException {
	        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	        String message = "You have successfully logged out!!"; 
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
		    
	        return "login?faces-redirect=true";
	    }
}
