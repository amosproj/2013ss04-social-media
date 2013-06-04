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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "\"XingDatas\"")
public class XingData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "\"ID\"",nullable = false, length=50)
	private Integer ID;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ClientID")
	private Client owner;
	
	@Column(name = "\"type\"",columnDefinition="VARCHAR(25)",nullable = false, length=25)
	String type;
	
	@Column(name = "\"data\"",columnDefinition="VARCHAR(1000)",nullable = false, length=1000)
	String dataString;

	public synchronized Integer getID() {
		return ID;
	}

	public synchronized void setID(Integer iD) {
		ID = iD;
	}

	public synchronized Client getOwner() {
		return owner;
	}

	public synchronized void setOwner(Client owner) {
		this.owner = owner;
	}

	public synchronized String getType() {
		return type;
	}

	public synchronized void setType(String type) {
		this.type = type;
	}

	public synchronized String getDataString() {
		return dataString;
	}

	public synchronized void setDataString(String dataString) {
		this.dataString = dataString;
	}

	@Override
	public synchronized int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result
				+ ((dataString == null) ? 0 : dataString.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public synchronized boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XingData other = (XingData) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		if (dataString == null) {
			if (other.dataString != null)
				return false;
		} else if (!dataString.equals(other.dataString))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public synchronized String toString() {
		return "XingData [ID=" + ID + ", type=" + type + ", dataString="
				+ dataString + "]";
	}
}

