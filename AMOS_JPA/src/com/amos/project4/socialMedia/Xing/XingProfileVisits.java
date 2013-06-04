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
package com.amos.project4.socialMedia.Xing;

import java.util.List;

import com.amos.project4.socialMedia.Xing.XingUser.Pictures;

public class XingProfileVisits {
	List<Visit> visits;
	
	public List<Visit> getVisits() {
		return visits;
	}

	public class Visit{
		String display_name;
		String company_name;
		String visited_at;
		String user_id;
		Pictures photos_urls;
		public String getDisplay_name() {
			return display_name;
		}
		public void setDisplay_name(String display_name) {
			this.display_name = display_name;
		}
		public String getCompany_name() {
			return company_name;
		}
		public void setCompany_name(String company_name) {
			this.company_name = company_name;
		}
		public String getVisited_at() {
			return visited_at;
		}
		public void setVisited_at(String visited_at) {
			this.visited_at = visited_at;
		}
		public String getUser_id() {
			return user_id;
		}
		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}
		public Pictures getPhotos_urls() {
			return photos_urls;
		}
		public void setPhotos_urls(Pictures photos_urls) {
			this.photos_urls = photos_urls;
		}
	}
	
	public class Reason{
		String text;
		public String getText() {
			return text;
		}
	}
}
