package com.amos.project4.socialMedia.LinkedIn;

import java.util.List;

public class LinkedInConnections {
	private long _total;
	private List<ConnectionsValue> values;
	public long get_total() {
		return _total;
	}
	public void set_total(long _total) {
		this._total = _total;
	}
	public List<ConnectionsValue> getValues() {
		return values;
	}
	public void setValues(List<ConnectionsValue> values) {
		this.values = values;
	}
	public class ConnectionsValue{
		private String id;
		private String firstName;
		private String headline;
		private String lastName;
		private String industry;
		private String pictureUrl;
		private String interests;
		private Location location;
		private SiteStandardProfileRequest siteStandardProfileRequest;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getHeadline() {
			return headline;
		}
		public void setHeadline(String headline) {
			this.headline = headline;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getIndustry() {
			return industry;
		}
		public void setIndustry(String industry) {
			this.industry = industry;
		}
		public String getPictureUrl() {
			return pictureUrl;
		}
		public void setPictureUrl(String pictureUrl) {
			this.pictureUrl = pictureUrl;
		}
		public String getInterests() {
			return interests;
		}
		public void setInterests(String interests) {
			this.interests = interests;
		}
		public Location getLocation() {
			return location;
		}
		public void setLocation(Location location) {
			this.location = location;
		}
		public SiteStandardProfileRequest getSiteStandardProfileRequest() {
			return siteStandardProfileRequest;
		}
		public void setSiteStandardProfileRequest(
				SiteStandardProfileRequest siteStandardProfileRequest) {
			this.siteStandardProfileRequest = siteStandardProfileRequest;
		}
	}
	public class Location{
		String name;
		Country country;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Country getCountry() {
			return country;
		}
		public void setCountry(Country country) {
			this.country = country;
		}
	}	
	public class Country{
		String code;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
	}
	public class SiteStandardProfileRequest{
		String url;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
	}

}
