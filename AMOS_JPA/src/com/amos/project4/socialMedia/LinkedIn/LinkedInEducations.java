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
package com.amos.project4.socialMedia.LinkedIn;

import java.util.List;

public class LinkedInEducations {
	long _total;
	private String id;
	private boolean isCurent;
	private String title;
	private List<CompanyValue> values;
	
	public long get_total() {
		return _total;
	}
	public void set_total(long _total) {
		this._total = _total;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isCurent() {
		return isCurent;
	}
	public void setCurent(boolean isCurent) {
		this.isCurent = isCurent;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<CompanyValue> getValues() {
		return values;
	}
	public void setValues(List<CompanyValue> values) {
		this.values = values;
	}
	public class CompanyValue{
		private Company company;

		public Company getCompany() {
			return company;
		}
		public void setCompany(Company company) {
			this.company = company;
		}
	}
	public class Company{
		private String id;
		private String industry;
		private String name;
		private String type;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getIndustry() {
			return industry;
		}
		public void setIndustry(String industry) {
			this.industry = industry;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
	}

}
