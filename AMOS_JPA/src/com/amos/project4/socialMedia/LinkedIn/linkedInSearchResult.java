package com.amos.project4.socialMedia.LinkedIn;

import java.util.ArrayList;
import java.util.List;

import com.amos.project4.socialMedia.AccountSearchResultInterface;
import com.amos.project4.socialMedia.AccountSearchResultItem;

public class linkedInSearchResult implements AccountSearchResultInterface {
	private long numResults;
	private UserCollection people;
	
	public class UserCollection{
		private long _total;
		private List<LinkedInUser> values;
		public long get_total() {
			return _total;
		}
		public void set_total(long _total) {
			this._total = _total;
		}
		public List<LinkedInUser> getValues() {
			return values;
		}
		public void setValues(List<LinkedInUser> values) {
			this.values = values;
		}
	}
	public void setNumResults(long numResults) {
		this.numResults = numResults;
	}

	public UserCollection getPeople() {
		return people;
	}

	public void setPeople(UserCollection people) {
		this.people = people;
	}
	
	@Override
	public long getNumResults() {
		return numResults;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountSearchResultItem> getValues() {
		return (List<AccountSearchResultItem>) (people != null?people.values:new ArrayList<AccountSearchResultItem>(0));
	}

}
