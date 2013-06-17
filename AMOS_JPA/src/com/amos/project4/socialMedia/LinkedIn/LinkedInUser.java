package com.amos.project4.socialMedia.LinkedIn;

import java.util.List;

import com.amos.project4.socialMedia.AccountSearchResultItem;
import com.google.gson.annotations.SerializedName;

public class LinkedInUser implements AccountSearchResultItem{

	private String id;
	private String firstName;
	private String headline;
	private String lastName;
	private String publicProfileUrl;
	private String pictureUrl;
	private Positions positions;
	private String interests;
	private String primaryTwitterAccount;
	
	public class Positions{
		@SerializedName("_total")
		private long total;
		List<Companywrapper> values;		
		public long getTotal() {
			return total;
		}
		public void setTotal(long total) {
			this.total = total;
		}
		public List<Companywrapper> getValues() {
			return values;
		}
		public void setValues(List<Companywrapper> values) {
			this.values = values;
		}
	}
	
	public class Companywrapper{
		Company company;
		String id;
		boolean isCurrent;
		String title;
		public Company getCompany() {
			return company;
		}
		public void setCompany(Company company) {
			this.company = company;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public boolean isCurrent() {
			return isCurrent;
		}
		public void setCurrent(boolean isCurrent) {
			this.isCurrent = isCurrent;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
	}
	public class Company{
		String id;
		String industry;
		String name;
		String type;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getIndustry() {
			return industry;
		}
		public void setIndustry(String industrie) {
			this.industry = industrie;
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
	
	
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstname) {
		this.firstName = firstname;
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
	public String getPublicProfileUrl() {
		return publicProfileUrl;
	}
	public void setPublicProfileUrl(String publicProfileUrl) {
		this.publicProfileUrl = publicProfileUrl;
	}
	public Positions getPositions() {
		return positions;
	}
	public void setPositions(Positions positions) {
		this.positions = positions;
	}
	public String getInterests() {
		return interests;
	}
	public void setInterests(String interests) {
		this.interests = interests;
	}
	public String getPrimaryTwitterAccount() {
		return primaryTwitterAccount;
	}
	public void setPrimaryTwitterAccount(String primaryTwitterAccount) {
		this.primaryTwitterAccount = primaryTwitterAccount;
	}
	@Override
	public String getTitle1() {
		return this.getLastName() + " , " + this.getFirstName();
	}
	@Override
	public String getTitle2() {
		return this.getHeadline();
	}
	@Override
	public String getProfileURL() {
		return this.publicProfileUrl;
	}
	@Override
	public String getPictureURL() {
		return this.pictureUrl;
	}
}
