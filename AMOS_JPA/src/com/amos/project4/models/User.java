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

/**
 * 
 * @author Jupiter BAKAKEU
 * 
 */
@Entity
@Table(name = "\"Userdatas\"")
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -51017586851741159L;

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
	
	/**
	 * Unique identifier of the user on twitter
	 */
	@Column(name = "\"t_username\"",columnDefinition="VARCHAR(50)")
	private String t_username;
	
	/**
	 * User twitter password to log on twitter
	 */
	@Column(name = "\"t_userpass\"",columnDefinition="VARCHAR(50)")
	private String t_userpass;
	
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

	public String getT_username() {
		return t_username;
	}

	public void setT_username(String t_username) {
		this.t_username = t_username;
	}

	public String getT_userpass() {
		return t_userpass;
	}

	public void setT_userpass(String t_userpass) {
		this.t_userpass = t_userpass;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result
				+ ((f_username == null) ? 0 : f_username.hashCode());
		result = prime * result
				+ ((f_userpass == null) ? 0 : f_userpass.hashCode());
		result = prime * result
				+ ((l_username == null) ? 0 : l_username.hashCode());
		result = prime * result
				+ ((l_userpass == null) ? 0 : l_userpass.hashCode());
		result = prime * result
				+ ((t_username == null) ? 0 : t_username.hashCode());
		result = prime * result
				+ ((t_userpass == null) ? 0 : t_userpass.hashCode());
		result = prime * result
				+ ((usermail == null) ? 0 : usermail.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		result = prime * result
				+ ((userpass == null) ? 0 : userpass.hashCode());
		result = prime * result
				+ ((x_username == null) ? 0 : x_username.hashCode());
		result = prime * result
				+ ((x_userpass == null) ? 0 : x_userpass.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		if (f_username == null) {
			if (other.f_username != null)
				return false;
		} else if (!f_username.equals(other.f_username))
			return false;
		if (f_userpass == null) {
			if (other.f_userpass != null)
				return false;
		} else if (!f_userpass.equals(other.f_userpass))
			return false;
		if (l_username == null) {
			if (other.l_username != null)
				return false;
		} else if (!l_username.equals(other.l_username))
			return false;
		if (l_userpass == null) {
			if (other.l_userpass != null)
				return false;
		} else if (!l_userpass.equals(other.l_userpass))
			return false;
		if (t_username == null) {
			if (other.t_username != null)
				return false;
		} else if (!t_username.equals(other.t_username))
			return false;
		if (t_userpass == null) {
			if (other.t_userpass != null)
				return false;
		} else if (!t_userpass.equals(other.t_userpass))
			return false;
		if (usermail == null) {
			if (other.usermail != null)
				return false;
		} else if (!usermail.equals(other.usermail))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (userpass == null) {
			if (other.userpass != null)
				return false;
		} else if (!userpass.equalsIgnoreCase(other.userpass))
			return false;
		if (x_username == null) {
			if (other.x_username != null)
				return false;
		} else if (!x_username.equals(other.x_username))
			return false;
		if (x_userpass == null) {
			if (other.x_userpass != null)
				return false;
		} else if (!x_userpass.equals(other.x_userpass))
			return false;
		return true;
	}

	
	
	
	

}
