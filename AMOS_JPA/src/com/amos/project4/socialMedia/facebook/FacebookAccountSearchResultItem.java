package com.amos.project4.socialMedia.facebook;

import com.amos.project4.socialMedia.AccountSearchResultItem;
import com.restfb.types.User;

public class FacebookAccountSearchResultItem extends User implements AccountSearchResultItem{

	private static final long serialVersionUID = 1L;
	String pictureURL;
	
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	@Override
	public String getTitle1() {
		return this.getName();
	}

	@Override
	public String getTitle2() {
		return this.getAbout()!=null?this.getAbout():"";
	}

	@Override
	public String getProfileURL() {
		return getLink();
	}

	@Override
	public String getPictureURL() {
		return pictureURL;
	}

	@Override
	public String getAccountKey() {
		return getId();
	}

}
