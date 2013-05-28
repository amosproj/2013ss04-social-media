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
package com.amos.project4.socialMedia.twitter;

import com.amos.project4.socialMedia.MediaConnectInterface;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterConnect implements MediaConnectInterface {

	private static final String ACCESS_TOKEN = "1392245641-ZrerD6ClCwGZANUZA2n1z86vSArMYfuBAjHDajz";
	private static final String ACCESS_TOKEN_SECRET = "cYKG9nJz4dpjoX41zE5xApNpI0YEswojMJg9qRutM";
	private static final String AUTHENTIFICATION_URL = "";
	
	private static final String ACCESS_TOKEN_URL = "https://api.twitter.com/oauth/access_token";
	private static final String AUTORIZATION_URL = "https://api.twitter.com/oauth/authorize";
	private static final String CONSUMER_KEY = "dqIwqUcMOiASiPND31CMvg";
	private static final String CONSUMER_SECRET = "vaKBsbAGSDjMu54f150kNL9UH9dhjIGwYMx5aiUtc";
	private static final String RESQUEST_TOKEN_URL = "https://api.twitter.com/oauth/request_token";
	
	private Twitter twitter;
	private RequestToken requestToken;
	private AccessToken accessToken;
	private TwitterFactory tf;
	
	
	private static TwitterConnect  instance;
	
	public static TwitterConnect  getInstance(){
		if(instance == null){
			instance = new TwitterConnect();
		}
		return instance;
	}	

	private TwitterConnect() {
		super();
		try {
			init();
		} catch (TwitterException e) {
			System.err.println("Could not initialize the default Twitter client API.");
			//e.printStackTrace();
		}
	}
	
	private void init() throws TwitterException{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		//cb.setOAuthAccessToken(ACCESS_TOKEN);
		//cb.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
		cb.setOAuthAccessTokenURL(ACCESS_TOKEN_URL);
		//cb.setOAuthAuthenticationURL(AUTHENTIFICATION_URL);
		cb.setOAuthAuthorizationURL(AUTORIZATION_URL);
		cb.setOAuthConsumerKey(CONSUMER_KEY);
		cb.setOAuthConsumerSecret(CONSUMER_SECRET);
		cb.setOAuthRequestTokenURL(RESQUEST_TOKEN_URL);
		
		tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();	
		
		requestToken = twitter.getOAuthRequestToken();
	    accessToken = null;
	}
	
	@Override
	public String getAccessUrl() {
		try {
			if(requestToken == null)
				requestToken = twitter.getOAuthRequestToken();
		} catch (TwitterException e) {
			return "";
		}
		return this.requestToken.getAuthenticationURL();
	}
	
	public boolean checkAndSetRequestTokenPin(String pin){
		try{
	         if(pin!= null && pin.length() > 0){
	           accessToken = twitter.getOAuthAccessToken(requestToken, pin);	           
	         }else{
	           return false;
	         }
	      } catch (TwitterException te) {
	        if(401 == te.getStatusCode()){
	          return false;
	        }else{
	        	System.err.println("Wrong Twitter access token or access token secret.");
	          //te.printStackTrace();
	        }
	      }
		return true;
	}
	
	public String[] getAccessToken(){
		String [] rslt = {accessToken.getToken(),accessToken.getTokenSecret()};
		return rslt ;
	}

	@Override
	public boolean login(String access_token, String secret_token) {		
		try {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setOAuthAccessToken(access_token);
			cb.setOAuthAccessTokenSecret(secret_token);
			cb.setOAuthAccessTokenURL(ACCESS_TOKEN_URL);
			cb.setOAuthAuthorizationURL(AUTORIZATION_URL);
			cb.setOAuthConsumerKey(CONSUMER_KEY);
			cb.setOAuthConsumerSecret(CONSUMER_SECRET);
			cb.setOAuthRequestTokenURL(RESQUEST_TOKEN_URL);
		
			tf = new TwitterFactory(cb.build());
			twitter = tf.getInstance();	
			//twitter.setOAuthConsumer(access_token, secret_token);
			User user = twitter.verifyCredentials();
			
			return (user.getId() > 0);
		} catch (IllegalStateException | TwitterException e) {
			System.err.println("Could not login to twitter API.");
			//e.printStackTrace();
			return false;
		}		
	}
	
	public Twitter getTwitter() {
		return twitter;
	}
}
