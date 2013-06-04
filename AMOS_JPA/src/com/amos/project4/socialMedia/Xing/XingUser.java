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


public class XingUser {
	
	public List<User> users;
	
	public class User {
		private String id;
		private String first_name;
		private String last_name;
		private String display_name;
		private String active_email;
		private String permalink;
		private String gender;
		private Pictures photo_urls;
		private Date bith_date;
		private Adress business_address;
		private Adress private_address;
		private ProfessionalExperience professional_experience;
	  
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getFirst_name() {
			return first_name;
		}
		public void setFirst_name(String first_name) {
			this.first_name = first_name;
		}
		public String getLast_name() {
			return last_name;
		}
		public void setLast_name(String last_name) {
			this.last_name = last_name;
		}
		public String getDisplay_name() {
			return display_name;
		}
		public void setDisplay_name(String display_name) {
			this.display_name = display_name;
		}
		public String getActive_email() {
			return active_email;
		}
		public void setActive_email(String active_email) {
			this.active_email = active_email;
		}
		public String getPermalink() {
			return permalink;
		}
		public void setPermalink(String permalink) {
			this.permalink = permalink;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public Pictures getPhoto_urls() {
			return photo_urls;
		}
		public void setPhoto_urls(Pictures photo_urls) {
			this.photo_urls = photo_urls;
		}
		public Date getBith_date() {
			return bith_date;
		}
		public void setBith_date(Date bith_date) {
			this.bith_date = bith_date;
		}
		public Adress getBusiness_address() {
			return business_address;
		}
		public void setBusiness_address(Adress business_address) {
			this.business_address = business_address;
		}
		public Adress getPrivate_address() {
			return private_address;
		}
		public void setPrivate_address(Adress private_address) {
			this.private_address = private_address;
		}
		public ProfessionalExperience getProfessional_experience() {
			return professional_experience;
		}
		public void setProfessional_experience(
				ProfessionalExperience professional_experience) {
			this.professional_experience = professional_experience;
		}	  
	}
	public class ProfessionalExperience{
		Company primary_company;
		List<Company> non_primary_companies;
		public Company getPrimary_company() {
			return primary_company;
		}
		public void setPrimary_company(Company primary_company) {
			this.primary_company = primary_company;
		}
		public List<Company> getNon_primary_companies() {
			return non_primary_companies;
		}
		public void setNon_primary_companies(List<Company> non_primary_companies) {
			this.non_primary_companies = non_primary_companies;
		}
	}
	
	public class Company{
		String name;
		String title;
		String description;
		String url;
		String career_level;
		String begin_date;
		String company_size;
		String end_date;
		String industry;
		String tag;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getCareer_level() {
			return career_level;
		}
		public void setCareer_level(String career_level) {
			this.career_level = career_level;
		}
		public String getBegin_date() {
			return begin_date;
		}
		public void setBegin_date(String begin_date) {
			this.begin_date = begin_date;
		}
		public String getCompany_size() {
			return company_size;
		}
		public void setCompany_size(String company_size) {
			this.company_size = company_size;
		}
		public String getEnd_date() {
			return end_date;
		}
		public void setEnd_date(String end_date) {
			this.end_date = end_date;
		}
		public String getIndustry() {
			return industry;
		}
		public void setIndustry(String industry) {
			this.industry = industry;
		}
		public String getTag() {
			return tag;
		}
		public void setTag(String tag) {
			this.tag = tag;
		}
	}
	
	public class Adress{
		String mobile_phone;
		String email;
		String city;
		String zip_code;
		String phone;
		String street;
		String country;
		String province;
		String fax;
		public String getMobile_phone() {
			return mobile_phone;
		}
		public void setMobile_phone(String mobile_phone) {
			this.mobile_phone = mobile_phone;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getZip_code() {
			return zip_code;
		}
		public void setZip_code(String zip_code) {
			this.zip_code = zip_code;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getStreet() {
			return street;
		}
		public void setStreet(String street) {
			this.street = street;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getFax() {
			return fax;
		}
		public void setFax(String fax) {
			this.fax = fax;
		}
	}
	
	public class Date{
		String month;
		String year;
		String day;
		public String getMonth() {
			return month;
		}
		public void setMonth(String month) {
			this.month = month;
		}
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}
		public String getDay() {
			return day;
		}
		public void setDay(String day) {
			this.day = day;
		}
	}
	
	public class Pictures{
		String medium_thumb;
		String large;
		String maxi_thumb;
		String thumb;
		String mini_thumb;
		public String getMedium_thumb() {
			return medium_thumb;
		}
		public void setMedium_thumb(String medium_thumb) {
			this.medium_thumb = medium_thumb;
		}
		public String getLarge() {
			return large;
		}
		public void setLarge(String large) {
			this.large = large;
		}
		public String getMaxi_thumb() {
			return maxi_thumb;
		}
		public void setMaxi_thumb(String maxi_thumb) {
			this.maxi_thumb = maxi_thumb;
		}
		public String getThumb() {
			return thumb;
		}
		public void setThumb(String thumb) {
			this.thumb = thumb;
		}
		public String getMini_thumb() {
			return mini_thumb;
		}
		public void setMini_thumb(String mini_thumb) {
			this.mini_thumb = mini_thumb;
		}		
	}
}
