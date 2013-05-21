package com.amos.project4.socialMedia.twitter;

import com.amos.project4.socialMedia.MediaConnectInterface;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class twitterConnect implements MediaConnectInterface {
	
	private static final String ACCESS_TOKEN = "1392245641-ZrerD6ClCwGZANUZA2n1z86vSArMYfuBAjHDajz";
	private static final String ACCESS_TOKEN_SECRET = "cYKG9nJz4dpjoX41zE5xApNpI0YEswojMJg9qRutM";
	private static final String ACCESS_TOKEN_URL = "https://api.twitter.com/oauth/access_token";
	private static final String AUTHENTIFICATION_URL = "";
	private static final String AUTORIZATION_URL = "https://api.twitter.com/oauth/authorize";
	private static final String CONSUMER_KEY = "dqIwqUcMOiASiPND31CMvg";
	private static final String CONSUMER_SECRET = "vaKBsbAGSDjMu54f150kNL9UH9dhjIGwYMx5aiUtc";
	private static final String RESQUEST_TOKEN_URL = "https://api.twitter.com/oauth/request_token";
	
	private Twitter twitter;
	private RequestToken requestToken;
	private AccessToken accessToken;
	private TwitterFactory tf;
	
	
	private static MediaConnectInterface instance;
	
	public static MediaConnectInterface getInstance(){
		if(instance == null){
			instance = new twitterConnect();
		}
		return instance;
	}	

	private twitterConnect() {
		super();
		try {
			init();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			return "Error !";
		}
		return this.requestToken.getAuthenticationURL();
	}
	
	public boolean checkAndSetAccessToken(String token){
		try{
	         if(token!= null && token.length() > 0){
	           accessToken = twitter.getOAuthAccessToken(requestToken, token);	           
	         }else{
	           return false;
	         }
	      } catch (TwitterException te) {
	        if(401 == te.getStatusCode()){
	          return false;
	        }else{
	          te.printStackTrace();
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
			System.out.println(user.getName());
			
			return (user.getId() > 0);
		} catch (IllegalStateException | TwitterException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
