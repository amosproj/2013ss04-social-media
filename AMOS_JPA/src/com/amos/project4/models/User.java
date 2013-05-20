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

package com.amos.project4.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "\"Userdatas\"")
public class User implements Serializable {

	private static final long serialVersionUID = -1;

	/**
	 * Identifier ID of the user
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "\"ID\"",nullable = false, length=50)
	private Integer ID;
	
	/**
	 * unique Username of a user
	 */
	@Column(name = "\"username\"",columnDefinition="VARCHAR(50)",nullable = false, length=50,unique = true)
	private String username;
	
	/**
	 * Unique usermail of an user
	 */
	@Column(name = "\"usermail\"",columnDefinition="VARCHAR(50)",nullable = false, length=50,unique = true)
	private String usermail;
	
	/**
	 * Unique hashed password of the user
	 */
	@Column(name = "\"userpass\"",columnDefinition="VARCHAR(250)",nullable = false, length=250)
	private String userpass;
	
	/**
	 * Unique identifier of the user on facebook
	 */
	@Column(name = "\"f_username\"",columnDefinition="VARCHAR(50)")
	private String f_username;
	
	/**
	 * User facebook password to log on facebook
	 */
	@Column(name = "\"f_userpass\"",columnDefinition="VARCHAR(50)")
	private String f_userpass;
	
//	/**
//	 * Unique identifier of the user on twitter
//	 */
//	@Column(name = "\"t_username\"",columnDefinition="VARCHAR(50)")
//	private String t_username;
//	
//	/**
//	 * User twitter password to log on twitter
//	 */
//	@Column(name = "\"t_userpass\"",columnDefinition="VARCHAR(50)")
//	private String t_userpass;
	
	/**
	 * URL to get the access token
	 */
	@Column(name = "\"t_token_secret\"",columnDefinition="VARCHAR(250)")
	private String t_token_secret;
	
	/**
	 * twitter Access Token
	 */
	@Column(name = "\"t_token\"",columnDefinition="VARCHAR(250)")
	private String t_token;

	/**
	 * Unique identifier of the user on Xing
	 */
	@Column(name = "\"x_username\"",columnDefinition="VARCHAR(50)")
	private String x_username;
	
	/**
	 * User Xing password to log on Xing
	 */
	@Column(name = "\"x_userpass\"",columnDefinition="VARCHAR(50)")
	private String x_userpass;
	
	/**
	 * Unique identifier of the user on LinkedIn
	 */
	@Column(name = "\"l_username\"",columnDefinition="VARCHAR(50)")
	private String l_username;
	
	/**
	 * User Xing password to log on LinkedIn
	 */
	@Column(name = "\"l_userpass\"",columnDefinition="VARCHAR(50)")
	private String l_userpass;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsermail() {
		return usermail;
	}

	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}

	public String getUserpass() {
		return userpass;
	}

	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}

	public String getF_username() {
		return f_username;
	}

	public void setF_username(String f_username) {
		this.f_username = f_username;
	}

	public String getF_userpass() {
		return f_userpass;
	}

	public void setF_userpass(String f_userpass) {
		this.f_userpass = f_userpass;
	}

//	public String getT_username() {
//		return t_username;
//	}
//
//	public void setT_username(String t_username) {
//		this.t_username = t_username;
//	}
//
//	public String getT_userpass() {
//		return t_userpass;
//	}
//
//	public void setT_userpass(String t_userpass) {
//		this.t_userpass = t_userpass;
//	}

	public String getX_username() {
		return x_username;
	}

	public void setX_username(String x_username) {
		this.x_username = x_username;
	}

	public String getX_userpass() {
		return x_userpass;
	}

	public void setX_userpass(String x_userpass) {
		this.x_userpass = x_userpass;
	}

	public String getL_username() {
		return l_username;
	}

	public void setL_username(String l_username) {
		this.l_username = l_username;
	}

	public String getL_userpass() {
		return l_userpass;
	}

	public void setL_userpass(String l_userpass) {
		this.l_userpass = l_userpass;
	}

	public Integer getID() {
		return ID;
	}
	
	public String getT_token_secret() {
		return t_token_secret;
	}

	public void setT_token_secret(String t_token_secret) {
		this.t_token_secret = t_token_secret;
	}

	public String getT_token() {
		return t_token;
	}

	public void setT_token(String t_token) {
		this.t_token = t_token;
	}

	
	
	

}
