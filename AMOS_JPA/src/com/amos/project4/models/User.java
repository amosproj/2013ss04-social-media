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
	@Column(name = "\"f_token\"",columnDefinition="VARCHAR(250)")
	private String f_token;
	
	/**
	 * User facebook password to log on facebook
	 */
	@Column(name = "\"f_token_secret\"",columnDefinition="VARCHAR(250)")
	private String f_token_secret;
	
	
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
	@Column(name = "\"x_token\"",columnDefinition="VARCHAR(250)")
	private String x_token;
	
	/**
	 * User Xing password to log on Xing
	 */
	@Column(name = "\"x_token_secret\"",columnDefinition="VARCHAR(250)")
	private String x_token_secret;
	
	/**
	 * Unique identifier of the user on LinkedIn
	 */
	@Column(name = "\"l_token\"",columnDefinition="VARCHAR(250)")
	private String l_token;
	
	/**
	 * User Xing password to log on LinkedIn
	 */
	@Column(name = "\"l_token_secret\"",columnDefinition="VARCHAR(250)")
	private String l_token_secret;

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

	public String getF_token() {
		return f_token;
	}

	public void setF_token(String f_username) {
		this.f_token = f_username;
	}

	public String getF_token_secret() {
		return f_token_secret;
	}

	public void setF_token_secret(String f_userpass) {
		this.f_token_secret = f_userpass;
	}

	public String getX_token() {
		return x_token;
	}

	public void setX_token(String x_username) {
		this.x_token = x_username;
	}

	public String getX_token_secret() {
		return x_token_secret;
	}

	public void setX_token_secret(String x_userpass) {
		this.x_token_secret = x_userpass;
	}

	public String getL_token() {
		return l_token;
	}

	public void setL_token(String l_username) {
		this.l_token = l_username;
	}

	public String getL_token_secret() {
		return l_token_secret;
	}

	public void setL_token_secret(String l_userpass) {
		this.l_token_secret = l_userpass;
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
	
	@Override
	public String toString() {
		return "User [ID=" + ID + ", username=" + username + ", usermail="
				+ usermail + ", userpass=" + userpass + ", f_token=" + f_token
				+ ", f_token_secret=" + f_token_secret + ", t_token_secret="
				+ t_token_secret + ", t_token=" + t_token + ", x_token="
				+ x_token + ", x_token_secret=" + x_token_secret + ", l_token="
				+ l_token + ", l_token_secret=" + l_token_secret + "]";
	}

}
