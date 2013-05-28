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
package com.amos.project4.socialMedia.LinkedIn;

import com.amos.project4.socialMedia.MediaConnectInterface;
import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;
import com.google.code.linkedinapi.schema.Person;


public class LinkedInConnect_LinkedIn_j implements MediaConnectInterface {

	private static final String ACCESS_TOKEN = "40d6f0c2-4bc2-4827-a610-7ccb6f11e9b3";
	private static final String ACCESS_TOKEN_SECRET = "2f714f4a-ed24-4c16-92da-3b9efc95d2d2<";
	
	private static final String CONSUMER_KEY = "55z5wlqvr1lb";
	private static final String CONSUMER_SECRET = "5RVgdcwrNcjbtiDZ";
	

	private LinkedInApiClientFactory factory;
	private LinkedInApiClient linkedIn;
	private LinkedInOAuthService oauthService;
	private LinkedInRequestToken requestToken;
	private LinkedInAccessToken accessToken;
	
	private static LinkedInConnect_LinkedIn_j  instance;
	
	public static LinkedInConnect_LinkedIn_j  getInstance(){
		if(instance == null){
			instance = new LinkedInConnect_LinkedIn_j();
		}
		return instance;
	}	

	private LinkedInConnect_LinkedIn_j() {
		super();
		try {
			init();
		} catch (Exception e) {
			System.err.println("Could not initialize the dafault Twitter client api.");
			//e.printStackTrace();
		}
	}
	
	private void init() throws Exception{
		final LinkedInOAuthService oauthService_ = LinkedInOAuthServiceFactory.getInstance().createLinkedInOAuthService(CONSUMER_KEY, CONSUMER_SECRET);
		final LinkedInApiClientFactory factory_ = LinkedInApiClientFactory.newInstance(CONSUMER_KEY, CONSUMER_SECRET);
		final LinkedInApiClient client_ = factory_.createLinkedInApiClient(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
		this.factory = factory_;
		this.linkedIn = client_;
		this.oauthService = oauthService_;
		this.requestToken = oauthService.getOAuthRequestToken();
		this.accessToken = null;
	}
	
	@Override
	public String getAccessUrl() {
		try{
			LinkedInRequestToken requestToken = oauthService.getOAuthRequestToken();
			if(requestToken != null ) {
				return requestToken.getAuthorizationUrl();
			}else{
				return "";
			}
		}catch (Exception e) {
			System.err.println("Could not get LinkedIn Oauth 1.0 request token.");
			return "";
		}			
	}
	
	public boolean checkAndSetRequestTokenPin(String pin){
		try{
			if(requestToken != null && oauthService != null){
				LinkedInAccessToken accessToken_ = oauthService.getOAuthAccessToken(requestToken, pin);
				final LinkedInApiClient client = factory.createLinkedInApiClient(accessToken_);
				Person profile = client.getProfileForCurrentUser();
				if(profile != null) {
					accessToken = accessToken_;
				}
				return profile != null;
			}else{
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String[] getAccessToken(){
		String[] rslt = {ACCESS_TOKEN,ACCESS_TOKEN};
		if(accessToken != null){
			rslt[0] = accessToken.getToken();
			rslt[0] = accessToken.getTokenSecret();			
		}
		return rslt;
	}

	@Override
	public boolean login(String access_token, String secret_token) {
		Person profile = null;
		try{
			if(access_token == null || access_token.isEmpty() || access_token == null || secret_token.isEmpty()) return false;
			LinkedInAccessToken accessToken_ = null;
			if(accessToken != null) {
				accessToken_ = accessToken;
			}else{
				accessToken_ =new LinkedInAccessToken(access_token, secret_token);
			}
			final LinkedInApiClient client = factory.createLinkedInApiClient(accessToken_);
			profile = client.getProfileForCurrentUser();
			if(accessToken == null) {
				accessToken = accessToken_;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return profile != null;		
	}
	
	public LinkedInApiClient getLinedIn() {
		return this.linkedIn;
	}
}
