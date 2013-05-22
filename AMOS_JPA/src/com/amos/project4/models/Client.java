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
import java.util.List;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

	@Column(name = "\"ZipCode\"")
	private String zipCode;

	@Column(name = "\"Gender\"")
	private String gender;
	
	@OneToMany(mappedBy="owner")
	private List<TwitterData> twitter_datas;
	
	@OneToMany(mappedBy="owner")
	private List<FacebookData> facebook_datas;

	public Client() {
		super();
	}

	public Client(String name, String firstname, Date birthdate, String mail,
			String place, String zipCode, String gender) {
		super();
		this.name = name;
		this.firstname = firstname;
		this.birthdate = birthdate;
		this.mail = mail;
		this.place = place;
		this.zipCode = zipCode;
		this.gender = gender;
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

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}
	
	public List<TwitterData> getTwitter_datas() {
		return twitter_datas;
	}

	public List<FacebookData> getFacebook_datas() {
		return facebook_datas;
	}

	/*-------------------------------------------------------------------------------------------------------*/
	/*	Methods to map One to many relations
	/*-------------------------------------------------------------------------------------------------------*/
	

	public void addTwitterData(TwitterData data){
		if(getTwitter_datas().contains(data)){
			getTwitter_datas().remove(data);
			if(data.getOwner() != null){
				data.getOwner().getTwitter_datas().remove(data);
			}
		}
		data.setOwner(this);
		getTwitter_datas().add(data);
	}
	
	public void addFacebookData(FacebookData data){
		if(getFacebook_datas().contains(data)){
			getFacebook_datas().remove(data);
			if(data.getOwner() != null){
				data.getOwner().getTwitter_datas().remove(data);
			}
		}
		data.setOwner(this);
		getFacebook_datas().add(data);
	}

}
