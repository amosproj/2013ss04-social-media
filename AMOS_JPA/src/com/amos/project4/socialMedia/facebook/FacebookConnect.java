<<<<<<< HEAD
package com.amos.project4.socialMedia.facebook;

import com.amos.project4.socialMedia.MediaConnectInterface;

public class FacebookConnect implements MediaConnectInterface {

	@Override
	public boolean login(String token, String secret_token) {
		// TODO Auto-generated method stub
		return false;
=======
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
package com.amos.project4.socialMedia.facebook;

import com.amos.project4.socialMedia.MediaConnectInterface;
import com.restfb.DefaultFacebookClient;
import com.restfb.types.User;

public class FacebookConnect implements MediaConnectInterface {
	
	private static String ACCESS_TOKEN = "CAACEdEose0cBAJzhp6gQhNksXJHQSoPb3WVhWtEly4TSwuw99Pd1ZAm513V8tk30OvpGjk2378oKPpI9F0Haz6ZCimFrZBDG3NYMQMae3qzOfVNOq6GdBLtyCrfJZCdFXuHN2XpbLcSCUIKapy4o0Xbdlcy6n8JdmUO6YpGNiKqXpaO1QA37qGuZAPCPMJQ4VRSTo8d7NAgZDZD";
	private static String ACCESS_TOKEN_URL = "http://developers.facebook.com/tools/explorer";
	
	private DefaultFacebookClient facebookClient;
	private static FacebookConnect instance;
	
	public static  FacebookConnect getInstance(){
		if(instance == null){
			instance = new FacebookConnect();
		}
		return instance;
	}	

	private FacebookConnect() {
		super();
		init();
	}
	
	public void init(){
		facebookClient = new DefaultFacebookClient(ACCESS_TOKEN);
	}

	@Override
	public boolean login(String token, String secret_token) {
		try{
			if(token == null || token.isEmpty()) return false;
			DefaultFacebookClient facebookClient_ = new DefaultFacebookClient(token);
			User user = facebookClient_.fetchObject("me", User.class);
			if(user != null && user.getId().length() > 0){
				ACCESS_TOKEN = token;
				this.facebookClient = facebookClient_;
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
>>>>>>> 4e0e1ec6bcb32dac60099c4f3a3907a74f5582a7
	}

	@Override
	public String getAccessUrl() {
<<<<<<< HEAD
		// TODO Auto-generated method stub
		return null;
=======
		return ACCESS_TOKEN_URL;
>>>>>>> 4e0e1ec6bcb32dac60099c4f3a3907a74f5582a7
	}

	@Override
	public boolean checkAndSetAccessToken(String token) {
<<<<<<< HEAD
		// TODO Auto-generated method stub
		return false;
=======
		try{
			DefaultFacebookClient facebookClient_ = new DefaultFacebookClient(token);
			User user = facebookClient_.fetchObject("me", User.class);
			if(user != null && user.getId().length() > 0){
				ACCESS_TOKEN = token;
				this.facebookClient = facebookClient_;
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
>>>>>>> 4e0e1ec6bcb32dac60099c4f3a3907a74f5582a7
	}

	@Override
	public String[] getAccessToken() {
<<<<<<< HEAD
		// TODO Auto-generated method stub
		return null;
=======
		String[] rslt = {ACCESS_TOKEN,ACCESS_TOKEN};
		return rslt;
>>>>>>> 4e0e1ec6bcb32dac60099c4f3a3907a74f5582a7
	}

}
