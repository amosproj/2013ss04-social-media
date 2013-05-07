/*
 * 
 * This file is part of the software project "Social Media and Datev".
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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Simple POJO Class to represent a client data entry
 * 
 * @author Jupiter BAKAKEU
 * 
 */

@Entity
@Table(name = "\"Kundendaten\"")
public class Client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "\"ID\"")
	private Integer ID;

	@Column(name = "\"Nachname\"")
	private String name;

	@Column(name = "\"Vorname\"")
	private String firstname;

	@Temporal(TemporalType.DATE)
	@Column(name = "\"Geburtstag\"")
	private Date birthdate;

	@Column(name = "\"Mail\"")
	private String mail;

	@Column(name = "\"Wohnort\"")
	private String place;

	public Client() {
		super();
	}

	public Client(String name, String firstname, Date birthdate, String mail,
			String place) {
		super();
		this.name = name;
		this.firstname = firstname;
		this.birthdate = birthdate;
		this.mail = mail;
		this.place = place;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

}
