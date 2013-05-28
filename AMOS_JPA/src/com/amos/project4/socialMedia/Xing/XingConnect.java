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
package com.amos.project4.socialMedia.Xing;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.XingApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.amos.project4.socialMedia.MediaConnectInterface;


public class XingConnect implements MediaConnectInterface {

	private static final String ACCESS_TOKEN = "57ff1f73c8e7e107a2f0";
	private static final String ACCESS_TOKEN_SECRET = "c441f3a48a6758ad1945";
	
	private static final String CONSUMER_KEY = "50e448c0dbc8fb2ea1f9";
	private static final String CONSUMER_SECRET = "76d2a34ddd8edb4fcbb0e8f7ebc8868b97d35055";
	
	private static final String PROTECTED_RESOURCE_URL = "https://api.xing.com/v1/users/me";


	private OAuthService service;
	private Token requestToken;
	private Token accessToken;
	
	private static XingConnect  instance;
	
	public static XingConnect  getInstance(){
		if(instance == null){
			instance = new XingConnect();
		}
		return instance;
	}	

	private XingConnect() {
		super();
		try {
			init();
		} catch (Exception e) {
			System.err.println("Could not initialize the default Xing client API.");
			e.printStackTrace();
		}
	}
	
	private void init() throws Exception{
		service = new ServiceBuilder()
        .provider(XingApi.class)
        .apiKey(CONSUMER_KEY)
        .apiSecret(CONSUMER_SECRET)
        .build();
		requestToken = service.getRequestToken();
		accessToken = null;
	}
	
	@Override
	public String getAccessUrl() {
		try{
			requestToken = service.getRequestToken();
			return service.getAuthorizationUrl(requestToken);			
		}catch (Exception e) {
			System.err.println("Could not get Xing OAuth 1.0 request token.");
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
			System.err.println("Wrong Xing pin.");
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
			System.err.println("Wrong Xing access token.");
			//e.printStackTrace();
			return false;
		}
	}
	
}
