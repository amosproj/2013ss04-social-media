package com.amos.project4.controllers;

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
	public void updateInternally(Object arg) {
		UserViewModel user_vm = (UserViewModel) arg;
		this.setCurrentUser(user_vm.getName());
	}
	
	private void setCurrentUser(String username){
		this.current_user = uDAO.getUserByUsername(username);		
	}

	/**
	 * get the current logged User
	 */
	@Override
	public User getCurrent_user() {
		return current_user;
	}
}
