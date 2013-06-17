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
	private long _total;
	private List<EducationValue> values;	
	public long get_total() {
		return _total;
	}
	public void set_total(long _total) {
		this._total = _total;
	}
	public List<EducationValue> getValues() {
		return values;
	}
	public void setValues(List<EducationValue> values) {
		this.values = values;
	}
	public class EducationValue{
		private Education education;
		private String id;
		private boolean isCurent;
		private String title;
		
		public Education getEducation() {
			return education;
		}
		public void setEducation(Education education) {
			this.education = education;
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
	}
	public class Education{
		private String id;
		private String schoolName;
		private String fieldOfStudy;
		private String degree;
		private String activities;
		private String notes;
		private String startDate;
		private String endDate;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getSchoolName() {
			return schoolName;
		}
		public void setSchoolName(String schoolName) {
			this.schoolName = schoolName;
		}
		public String getFieldOfStudy() {
			return fieldOfStudy;
		}
		public void setFieldOfStudy(String fieldOfStudy) {
			this.fieldOfStudy = fieldOfStudy;
		}
		public String getDegree() {
			return degree;
		}
		public void setDegree(String degree) {
			this.degree = degree;
		}
		public String getActivities() {
			return activities;
		}
		public void setActivities(String activities) {
			this.activities = activities;
		}
		public String getNotes() {
			return notes;
		}
		public void setNotes(String notes) {
			this.notes = notes;
		}
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}		
	}

}
