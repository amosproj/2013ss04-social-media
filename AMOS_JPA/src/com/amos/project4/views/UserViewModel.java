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

/**
 * 
 * @author Jupiter BAKAKEU
 * 
 */
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
	private String t_token;

	/**
	 * User twitter password to log on twitter
	 */
	private String t_token_secret;

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

	public void setUserData(String name, String password) {
		this.name = name;
		this.password = password;
		this.setChanged();
		this.notifyObservers("Login");
	}

	public String getPassword() {
		return password;
	}

	public void setSocialMediaDatas(String f_user, String f_pass,
			String t_token, String t_token_secret, String x_user, String x_pass,
			String l_user, String l_pass) {
		this.f_username = f_user;
		this.f_userpass = f_pass;
		this.t_token = t_token;
		this.t_token_secret = t_token_secret;
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

	public String getT_token() {
		return t_token;
	}

	public String getT_token_secret() {
		return t_token_secret;
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
