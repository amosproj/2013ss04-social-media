package com.amos.project4.views;

import javax.persistence.Column;

public class UserViewModel extends AbstractViewModel {

	private String name;
	private String password;
	
	/**
	 * Unique identifier of the user on facebook
	 */
	private String f_username;
	
	/**
	 * User facebook password to log on facebook
	 */
	private String f_userpass;
	
	/**
	 * Unique identifier of the user on twitter
	 */
	private String t_username;
	
	/**
	 * User twitter password to log on twitter
	 */
	private String t_userpass;
	
	/**
	 * Unique identifier of the user on Xing
	 */
	private String x_username;
	
	/**
	 * User Xing password to log on Xing
	 */
	private String x_userpass;
	
	/**
	 * Unique identifier of the user on LinkedIn
	 */
	private String l_username;
	
	/**
	 * User Xing password to log on LinkedIn
	 */
	private String l_userpass;
	
	public String getName() {
		return name;
	}
	public void setUserData(String name,String password) {
		this.name = name;
		this.password = password;
		this.setChanged();
		this.notifyObservers("Login");
	}
	public String getPassword() {
		return password;
	}
	
	
	public void setSocialMediaDatas(String f_user,String f_pass,String t_user,String t_pass,String x_user,String x_pass,String l_user,String l_pass){
		this.f_username = f_user;
		this.f_userpass = f_pass;
		this.t_username = t_user;
		this.t_userpass = t_pass;
		this.x_username = x_user;
		this.x_userpass = x_pass;
		this.l_username = l_user;
		this.l_userpass = l_pass;		
		this.setChanged();
		this.notifyObservers("Settings");		
	}
	
	
	public String getF_username() {
		return f_username;
	}
	public String getF_userpass() {
		return f_userpass;
	}
	public String getT_username() {
		return t_username;
	}
	public String getT_userpass() {
		return t_userpass;
	}
	public String getX_username() {
		return x_username;
	}
	public String getX_userpass() {
		return x_userpass;
	}
	public String getL_username() {
		return l_username;
	}
	public String getL_userpass() {
		return l_userpass;
	}
	
	
	

}
