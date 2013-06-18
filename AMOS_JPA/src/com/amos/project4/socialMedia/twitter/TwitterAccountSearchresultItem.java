package com.amos.project4.socialMedia.twitter;

import twitter4j.User;

import com.amos.project4.socialMedia.AccountSearchResultItem;

public class TwitterAccountSearchresultItem  implements AccountSearchResultItem{
	
	private twitter4j.User user;

	public TwitterAccountSearchresultItem(User user) {
		super();
		this.user = user;
	}

	@Override
	public String getTitle1() {
		return user!= null?user.getScreenName():"";
	}

	@Override
	public String getTitle2() {
		return user != null ? (user.getDescription() != null ? user
				.getDescription() : user.getLocation() != null ? user
				.getLocation() : user.getLang()) : "";
	}

	@Override
	public String getProfileURL() {
		return user!= null?user.getURL():"";
	}

	@Override
	public String getPictureURL() {
		return user!= null?user.getBiggerProfileImageURL():"";
	}

	@Override
	public String getAccountKey() {
		return user!= null?user.getScreenName():"";
	}

}
