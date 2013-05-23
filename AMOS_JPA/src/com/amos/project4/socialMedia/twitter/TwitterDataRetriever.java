package com.amos.project4.socialMedia.twitter;

import java.util.List;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.amos.project4.models.Client;
import com.amos.project4.models.TwitterData;
import com.amos.project4.models.TwitterDataDAO;
import com.amos.project4.models.User;

public class TwitterDataRetriever {
	
	private TwitterDataDAO twitter_dao;
	private TwitterConnect connector;
	
	private static TwitterDataRetriever instance;
	
	private TwitterDataRetriever() {
		twitter_dao = TwitterDataDAO.getInstance();
		connector = TwitterConnect.getInstance();
	}
	
	public synchronized static TwitterDataRetriever getInstance(){
		if(instance == null){
			instance = new TwitterDataRetriever();
		}
		return instance;
	}
	
	public synchronized boolean init(User user){
		try{
			if(user == null) return false;
			twitter_dao = TwitterDataDAO.getInstance();
			connector = TwitterConnect.getInstance();
			
			// Establish connection with twitter
			connector.login(user.getT_token(), user.getT_token_secret());
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public synchronized  void deleteUserTwitterData(Client client){
		if(client == null ) return;
		//this.twitter_dao.deleteAllTwitterData(client.getID());
		List<TwitterData> datas = this.twitter_dao.getAllTwitterDataOfClient(client.getID());
		for(TwitterData data : datas){
			if(!data.getType().equalsIgnoreCase(TwitterDataType.TWITTER_NAME.toString())){
				this.twitter_dao.deleteTwitterData(data);
			}
		}
		
	}
	
	public synchronized  void importTwitterData(User user,Client client, TwitterDataType type) throws TwitterException{
		if(user == null || client == null) return;
		
		// get the Twitter
		Twitter twitter = connector.getTwitter();
		
		//Check if account of client exist and get it
		List<TwitterData> account = twitter_dao.getAllTwitterDataOfClientByType(client.getID(), TwitterDataType.TWITTER_NAME);
		if(account == null || account.size() == 0 ) return; // the client doesn't have an account yet
		String client_twitter_name = account.get(0).getDataString();
		
		switch (type) {
		case FRIENDS:
			ResponseList<twitter4j.User> friends = twitter.getFriendsList(client_twitter_name, -1);
			for(twitter4j.User t_user : friends){
				saveTwitterData(client,t_user.getScreenName(),TwitterDataType.FRIENDS);
			}
			return;
		case ID:
			twitter4j.User t_user = twitter.showUser(client_twitter_name);
			saveTwitterData(client,"" + t_user.getId(), TwitterDataType.ID);
			return;
		case TWITTER_NAME:
			return;
		case TRENDS:
			ResponseList<twitter4j.Location> trends = twitter.getAvailableTrends();
			for(twitter4j.Location trend : trends){
				saveTwitterData(client,trend.getName(),TwitterDataType.TRENDS);				
			}
			return;
		case TWEETS:
			ResponseList<twitter4j.Status> tweets = twitter.getUserTimeline(client_twitter_name);
			for(twitter4j.Status tweet : tweets){
				saveTwitterData(client,tweet.getText(),TwitterDataType.TWEETS);				
			}
		default:
			break;
		}		
	}
	
	private synchronized void saveTwitterData(Client client,String dataString,TwitterDataType type){
		TwitterData data = new TwitterData();
		data.setType(type.toString());
		data.setDataString(dataString);
		data.setOwner(client);
		client.addTwitterData(data);
		twitter_dao.persistTwitterData(data);
	}
}
