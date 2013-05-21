package com.amos.project4.socialMedia.facebook;

import com.amos.project4.socialMedia.MediaConnectInterface;

public class FacebookConnect implements MediaConnectInterface {

	@Override
	public boolean login(String token, String secret_token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getAccessUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkAndSetAccessToken(String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] getAccessToken() {
		// TODO Auto-generated method stub
		return null;
	}

}
