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
	
	public synchronized  void deleteUserTwitterData(Client client, TwitterDataType type){
		if(client == null || type == null) return;		
		if(!type.toString().equalsIgnoreCase(TwitterDataType.TWITTER_NAME.toString())){
			this.twitter_dao.deleteTwitterDatas(client, type);
		}
//		if(client == null || type == null) return;
//		for(TwitterData data : client.getTwitter_datas()){
//			if(data.getType().toString().equalsIgnoreCase(type.toString()) && !type.toString().equalsIgnoreCase(TwitterDataType.TWITTER_NAME.toString())){
//				this.twitter_dao.deleteTwitterData(data);
//			}
//		}
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
			ResponseList<twitter4j.User> friends = null;
			try{
				friends = twitter.getFriendsList(client_twitter_name, -1);
			}catch (Exception e) {
				System.err.println("Access denied to get Friends: " + client_twitter_name);
				return;
			}
			for(twitter4j.User t_user : friends){
				saveTwitterData(client,t_user.getId()+ "#" +t_user.getScreenName(),TwitterDataType.FRIENDS);
			}
			return;
		case ID:
			twitter4j.User t_user = twitter.showUser(client_twitter_name);
			saveTwitterData(client,"" + t_user.getId(), TwitterDataType.ID);
			return;
		case USER_PICTURE:
			twitter4j.User p_user = twitter.showUser(client_twitter_name);
			saveTwitterData(client,"" + p_user.getBiggerProfileImageURL(), TwitterDataType.USER_PICTURE);
			return;
		case TWITTER_NAME:
			return;
		case TRENDS:
			ResponseList<twitter4j.Location> trends = twitter.getAvailableTrends();
			for(twitter4j.Location trend : trends){
				saveTwitterData(client,trend.getName()  + "#" + trend.getCountryName() + "#" + trend.getURL(),TwitterDataType.TRENDS);				
			}
			return;
		case TWEETS:
			ResponseList<twitter4j.Status> tweets = null;
			try{
				tweets = twitter.getUserTimeline(client_twitter_name);
			}catch (Exception e) {
				System.err.println("Access denied to get Tweets : " + client_twitter_name);
				return;
			}
			for(twitter4j.Status tweet : tweets){
				saveTwitterData(client,tweet.getCreatedAt() + "#" + tweet.getText() ,TwitterDataType.TWEETS);				
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
