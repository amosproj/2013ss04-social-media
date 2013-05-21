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

package com.amos.project4.views;

import com.amos.project4.models.User;

/**
 * 
 * @author Jupiter BAKAKEU
 * 
 */
public class UserViewModel extends AbstractViewModel {

	private String name;
	private String password;
	private String mail;

	/**
	 * Unique identifier of the user on facebook
	 */
	private String f_token;

	/**
	 * User facebook password to log on facebook
	 */
	private String f_token_secret;

	/**
	 * Unique identifier of the user on twitter
	 */
	private String t_token;

	/**
	 * User twitter password to log on twitter
	 */
	private String t_token_secret;

	/**
	 * Unique identifier of the user on Xing
	 */
	private String x_token;

	/**
	 * User Xing password to log on Xing
	 */
	private String x_token_secret;

	/**
	 * Unique identifier of the user on LinkedIn
	 */
	private String l_token;

	/**
	 * User Xing password to log on LinkedIn
	 */
	private String l_token_secret;


	public String getName() {
		return name;
	}

	public void setUserData(String name, String password) {
		this.name = name;
		this.password = password;
		this.setChanged();
		this.notifyObservers("Login");
	}

	public String getPassword() {
		return password;
	}

	public void setSocialMediaDatas(String f_token, String f_token_secret,
			String t_token, String t_token_secret, String x_token, String x_token_secret,
			String l_token, String l_token_secret) {
		
		this.f_token = f_token;
		this.f_token_secret = f_token_secret;
		this.t_token = t_token;
		this.t_token_secret = t_token_secret;
		this.x_token = x_token;
		this.x_token_secret = x_token_secret;
		this.l_token = l_token;
		this.l_token_secret = l_token_secret;
		this.setChanged();
		this.notifyObservers("Settings");
	}

	public String getF_token() {
		return f_token;
	}

	public String getF_token_secret() {
		return f_token_secret;
	}

	public String getT_token() {
		return t_token;
	}

	public String getT_token_secret() {
		return t_token_secret;
	}

	public String getX_token() {
		return x_token;
	}

	public String getX_token_secret() {
		return x_token_secret;
	}

	public String getL_token() {
		return l_token;
	}

	public String getL_token_secret() {
		return l_token_secret;
	}

	public String getMail() {
		return mail;
	}

	public void updateModel(User _user) {
		this.name = _user.getUsername();
		this.password = _user.getUserpass();
		this.mail = _user.getUsermail();
		this.f_token = _user.getF_token();
		this.f_token_secret = _user.getF_token_secret();
		this.t_token = _user.getT_token();
		this.t_token_secret = _user.getT_token_secret();
		this.x_token = _user.getX_token();
		this.x_token_secret = _user.getX_token_secret();
		this.l_token = _user.getL_token();
		this.l_token_secret = _user.getL_token_secret();
	}

	@Override
	public String toString() {
		return "UserViewModel [name=" + name + ", password=" + password
				+ ", mail=" + mail + ", f_token=" + f_token
				+ ", f_token_secret=" + f_token_secret + ", t_token=" + t_token
				+ ", t_token_secret=" + t_token_secret + ", x_token=" + x_token
				+ ", x_token_secret=" + x_token_secret + ", l_token=" + l_token
				+ ", l_token_secret=" + l_token_secret + "]";
	}
	
	
	
}
