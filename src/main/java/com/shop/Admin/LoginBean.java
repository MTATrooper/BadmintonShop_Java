package com.shop.Admin;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.shop.Admin.DAO.LoginDAO;

import SessionUtils.SessionUtils;

@ManagedBean(name="loginBean")
@SessionScoped 
public class LoginBean {
	private LoginDAO loginDAO = new LoginDAO();
	private String username;
	private String password;
	private String newpass;
	
	public LoginDAO getLoginDAO() {
		return loginDAO;
	}
	
	public void setLoginDAO(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNewpass() {
		return newpass;
	}

	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}

	public String validateLogin()
	{
		boolean valid = LoginDAO.validate(username, password);
		if (valid) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", username);
			session.setAttribute("password", password);
			return "/views/Home/index.xhtml";
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect Username and Passowrd",
							"Please enter correct username and Password"));
			return "/views/Login/login.xhtml";
		}
	}
	
	public String logout()
	{
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "/";
	}
	public String changepass()
	{
		loginDAO.changepass(username, newpass);
		HttpSession session = SessionUtils.getSession();
		session.setAttribute("password", newpass);
		return "/views/Home/index.xhtml";
	}
}
