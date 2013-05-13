package com.amos.project4.controllers;

import java.util.Observable;

import com.amos.project4.models.User;
import com.amos.project4.models.UserDAO;
import com.amos.project4.views.UserViewModel;

public class UserController extends AbstractController implements UserContollerInterface {

	User current_user;
	UserDAO uDAO;	
	
	public UserController() {
		super();
		current_user = null;
		uDAO = UserDAO.getUserDAOInstance();
	}

	@Override
	public void updateInternally(Object arg, Observable o) {
		if(arg != null && ((String)arg).equalsIgnoreCase("Login")){
			UserViewModel user_vm = (UserViewModel) o;
			this.setCurrentUser(user_vm.getName());
		}else if(arg != null && ((String)arg).equalsIgnoreCase("Settings")){			
			updateUser((UserViewModel) o);
		}
	}
	
	private void setCurrentUser(String username){
		this.current_user = uDAO.getUserByUsername(username);		
	}
	
	private void updateUser(UserViewModel user){
		this.current_user.setF_username(user.getF_username());
		this.current_user.setF_userpass(user.getF_userpass());
		this.current_user.setT_username(user.getT_username());
		this.current_user.setT_userpass(user.getT_userpass());
		this.current_user.setX_username(user.getX_username());
		this.current_user.setX_userpass(user.getX_userpass());
		this.current_user.setL_username(user.getL_username());
		this.current_user.setL_userpass(user.getL_userpass());
		uDAO.updateUser(this.current_user);
		setCurrentUser(this.current_user.getUsername());
	}

	/**
	 * get the current logged User
	 */
	@Override
	public User getCurrent_user() {
		return current_user;
	}
}
