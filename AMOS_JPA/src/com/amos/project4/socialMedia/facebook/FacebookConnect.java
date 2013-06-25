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
	
	private static String ACCESS_TOKEN = "CAACEdEose0cBAFH9dnmHKW53hYZAPm1Xxw7xy6Cyrcge2Bg8SMycHvQ5rGmUGVktZBMya2Yoe22bPniLylt8ORYfbDi2TLHDr7mMyy7FZB3nczrQE7LBF975jhZA21szs5ZCMXWmhDMJ8WfClZCyZBOxtNxubjfZBGwZD";
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
		try {
			facebookClient = new DefaultFacebookClient(ACCESS_TOKEN);
		} catch (Exception e) {
			System.err.println("Could not initialize the default facebook client api.");
			//e.printStackTrace();
		}
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
			System.err.println("Wrong facebook acces token.");
//			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String getAccessUrl() {
		return ACCESS_TOKEN_URL;
	}

	@Override
	public boolean checkAndSetRequestTokenPin(String token) {
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
			System.err.println("Wrong facebook acces token.");
			//e.printStackTrace();
			return false;
		}
	}

	@Override
	public String[] getAccessToken() {
		String[] rslt = {ACCESS_TOKEN,ACCESS_TOKEN};
		return rslt;
	}

	public DefaultFacebookClient getFacebookClient() {
		return facebookClient;
	}
}
