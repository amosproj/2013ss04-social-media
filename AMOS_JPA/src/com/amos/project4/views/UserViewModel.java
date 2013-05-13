package com.amos.project4.views;

public class UserViewModel extends AbstractViewModel {

	private String name;
	private String password;
	public String getName() {
		return name;
	}
	public void setUserData(String name,String password) {
		this.name = name;
		this.password = password;
		this.setChanged();
		this.notifyObservers(this);
	}
	public String getPassword() {
		return password;
	}
	
	
	

}
