/*
 *
 * This file is part of the Datev and Social Media project.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.amos.project4.controllers;

import java.util.Observable;

import com.amos.project4.models.SocialMediaType;
import com.amos.project4.models.User;
import com.amos.project4.models.UserDAO;
import com.amos.project4.socialMedia.MediaConnectInterface;
import com.amos.project4.socialMedia.LinkedIn.LinkedInConnect;
import com.amos.project4.socialMedia.Xing.XingConnect;
import com.amos.project4.socialMedia.facebook.FacebookConnect;
import com.amos.project4.socialMedia.twitter.TwitterConnect;
import com.amos.project4.views.UserViewModel;

/**
 * 
 * @author Jupiter BAKAKEU
 *
 */
public class UserController extends AbstractController implements UserContollerInterface {

	User current_user;
	UserDAO uDAO;	
	private  MediaConnectInterface t_connect;
	private  MediaConnectInterface f_connect;
	private  MediaConnectInterface l_connect;
	private  MediaConnectInterface x_connect;
	
	public UserController() {
		super();
		current_user = null;
		uDAO = UserDAO.getUserDAOInstance();
	}
	
	public void InitConnector(){
		t_connect = TwitterConnect.getInstance();
		f_connect = FacebookConnect.getInstance();
		l_connect = LinkedInConnect.getInstance();
		x_connect = XingConnect.getInstance();
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
		this.current_user.setF_token(user.getF_token());
		this.current_user.setF_token_secret(user.getF_token_secret());
		this.current_user.setT_token(user.getT_token());
		this.current_user.setT_token_secret(user.getT_token_secret());
		this.current_user.setX_token(user.getX_token());
		this.current_user.setX_token_secret(user.getX_token_secret());
		this.current_user.setL_token(user.getL_token());
		this.current_user.setL_token_secret(user.getL_token_secret());
		
		uDAO.updateUser(this.current_user);
		setCurrentUser(this.current_user.getUsername());
	}
	
	/**
	 * get the current logged User
	 * 
	 */
	@Override
	public User getCurrent_user() {
		return current_user;
	}
	
	public boolean checkToken(String token, String secret, SocialMediaType sType){
		switch (sType) {
			case TWITTER:
				return t_connect.login(token, secret);
			case FACEBOOK:
				return f_connect.login(token, secret);
			case LINKEDIN:
				return l_connect.login(token, secret);
			case XING:
				return x_connect.login(token, secret);
			default:
				return true;
		}
		
	}
	
	public String getAccessTokenRequestURL(SocialMediaType sType){
		switch (sType) {
			case TWITTER:
				return t_connect.getAccessUrl();
			case FACEBOOK:
				return f_connect.getAccessUrl();
			case LINKEDIN:
				return l_connect.getAccessUrl();
			case XING:
				return x_connect.getAccessUrl();
			default:
				return "DEFAULT ACCESS TOKEN URL";
		}
	}
	
	public boolean checkAndSetAccessToken(String url,String pin, SocialMediaType sType){		
		switch (sType) {
			case TWITTER:
				return t_connect.checkAndSetRequestTokenPin(pin);
			case FACEBOOK:
				return f_connect.checkAndSetRequestTokenPin(pin);
			case LINKEDIN:
				return l_connect.checkAndSetRequestTokenPin(pin);
			case XING:
				return x_connect.checkAndSetRequestTokenPin(pin);
			default:
				return true;
		}
	}
	
	public String[] getAccessToken(SocialMediaType sType){
		switch (sType) {
		case TWITTER:
			return t_connect.getAccessToken();
		case FACEBOOK:
			return f_connect.getAccessToken();
		case LINKEDIN:
			return l_connect.getAccessToken();
		case XING:
			return x_connect.getAccessToken();
		default:
			String [] rslt = {"DEFAULT_ACCESS_TOKEN","DEFAULT_ACCESS_TOKEN"};
			return rslt;
		}
		
	}
}
