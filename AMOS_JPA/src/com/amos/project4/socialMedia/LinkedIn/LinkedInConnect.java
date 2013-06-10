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


import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.amos.project4.socialMedia.MediaConnectInterface;


public class LinkedInConnect implements MediaConnectInterface {

	private static final String ACCESS_TOKEN = "40d6f0c2-4bc2-4827-a610-7ccb6f11e9b3";
	private static final String ACCESS_TOKEN_SECRET = "2f714f4a-ed24-4c16-92da-3b9efc95d2d2";
	
	private static final String CONSUMER_KEY = "55z5wlqvr1lb";
	private static final String CONSUMER_SECRET = "5RVgdcwrNcjbtiDZ";
	
	private static final String PROTECTED_RESOURCE_URL = "http://api.linkedin.com/v1/people/~/connections:(id,last-name)";


	private OAuthService service;
	private Token requestToken;
	private Token accessToken;
	
	private static LinkedInConnect  instance;
	
	public static LinkedInConnect  getInstance(){
		if(instance == null){
			instance = new LinkedInConnect();
		}
		return instance;
	}	

	private LinkedInConnect() {
		super();
		try {
			init();
		} catch (Exception e) {
			System.err.println("Could not initialize the dafault Xing client api.");
			e.printStackTrace();
		}
	}
	
	private void init() throws Exception{
		service = new ServiceBuilder()
       .provider(LinkedInApi.class)
       .apiKey(CONSUMER_KEY)
       .apiSecret(CONSUMER_SECRET)
       .build();
		requestToken = service.getRequestToken();
		accessToken = new Token(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
	}
	
	@Override
	public String getAccessUrl() {
		try{
			requestToken = service.getRequestToken();
			return service.getAuthorizationUrl(requestToken);			
		}catch (Exception e) {
			System.err.println("Could not get Xing Oauth 1.0 request token.");
			return "";
		}			
	}
	
	public boolean checkAndSetRequestTokenPin(String pin){
		if(pin == null || pin.isEmpty()) return false;
		try{
			 Verifier verifier = new Verifier(pin);
			 Token accessToken_ = service.getAccessToken(requestToken, verifier);
			 if(!accessToken_.getToken().isEmpty()){
				 this.accessToken =  accessToken_;
			 }
			 return !accessToken_.getToken().isEmpty();
		}catch (Exception e) {
			System.err.println("Wrong LinkedIn pin.");
			//e.printStackTrace();
			return false;
		}
	}
	
	public String[] getAccessToken(){
		String[] rslt = {ACCESS_TOKEN,ACCESS_TOKEN_SECRET};
		if(accessToken != null){
			rslt[0] = accessToken.getToken();
			rslt[1] = accessToken.getSecret();			
		}
		return rslt;
	}

	@Override
	public boolean login(String access_token, String secret_token) {
		try{
			Token aToken = new Token(access_token, secret_token);
			OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		    service.signRequest(aToken, request);
		    Response response = request.send();
		    return response.isSuccessful();
		}catch (Exception e) {
			System.err.println("Wrong LinkedIn access token.");
			//e.printStackTrace();
			return false;
		}
	}
	
	public Response makeRequest(String url, Boolean json){
		OAuthRequest request = new OAuthRequest(Verb.GET, url);
		if(json)
			request.addHeader("x-li-format", "json");
	    service.signRequest(accessToken, request);
	    Response response = request.send();
	    if(response.isSuccessful()){
	    	return response;
	    }else{
	    	System.out.println(response.getMessage());
	    	return null;
	    }
	}
	
}

