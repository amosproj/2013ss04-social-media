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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.amos.project4.models.Client;
import com.amos.project4.models.TwitterData;
import com.amos.project4.models.TwitterDataDAO;
import com.amos.project4.models.User;
import com.amos.project4.socialMedia.AccountSearchResult;
import com.amos.project4.socialMedia.AccountSearchResultInterface;
import com.amos.project4.socialMedia.AccountSearchResultItem;
import com.amos.project4.socialMedia.DataRetrieverInterface;

public class TwitterDataRetriever implements DataRetrieverInterface{
	
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
	}
	
	@Override
	public synchronized AccountSearchResultInterface makeSearch(Client selectedClient,int begin, int end) {
		TwitterAccountSearchResult rslts = new TwitterAccountSearchResult(new ArrayList<AccountSearchResultItem>());
		Twitter twitter = connector.getTwitter();
		int page = end / AccountSearchResult.PAGE_SIZE;
		try {
			ResponseList<twitter4j.User> users = twitter.searchUsers(selectedClient.getFirstname() + " " + selectedClient.getName(), page );
			if(users != null && !users.isEmpty()){
				rslts.setNumResults(users.size());
				for(twitter4j.User data : users){
					rslts.getList().add(new TwitterAccountSearchresultItem(data));
				}
			}
		} catch (TwitterException e) {
		}
		return rslts;
	}
	
	public synchronized String importTwitterIDofUser(User user, Client client) {
		if(user == null || client == null) return "";
		Twitter twitter = connector.getTwitter();
		
		List<TwitterData> account = this.twitter_dao.getAllTwitterDataOfClientByType(client.getID(),TwitterDataType.TWITTER_NAME);
		String twitter_id = "";
		if(account == null || account.size() == 0 ){
			try {
				ResponseList<twitter4j.User> users = twitter.searchUsers(client.getFirstname() + " " + client.getName(), 1 );
				if(users != null && !users.isEmpty()){
					twitter_id = users.get(0).getScreenName();
					saveTwitterData(client,users.get(0).getScreenName(),TwitterDataType.TWITTER_NAME);					
				}
			} catch (TwitterException e) {
			}
		}else{
			twitter_id = account.get(0).getDataString();
		}
		
		return twitter_id;
	}
	
	public synchronized  void importTwitterData(User user,Client client, TwitterDataType type, String twitter_id) throws TwitterException{
		if(user == null || client == null || twitter_id == null || twitter_id.isEmpty()) return;
		
		// get the Twitter
		Twitter twitter = connector.getTwitter();
		
		String client_twitter_name = twitter_id;
		
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
			for(twitter4j.Location trend : trends.subList(0, 20)){
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
	
	public synchronized List<TwitterData> getLastTweetsOfClients(List<Client> clients, int count){
		if(clients == null ) return new ArrayList<TwitterData>();
		TreeMap<Date,TwitterData> datas = new TreeMap<Date,TwitterData>();
		// get the Twitter
		Twitter twitter = connector.getTwitter();
		
		for(Client c : clients){
			List<TwitterData> ids = c.getTwitterDatasByType(TwitterDataType.TWITTER_NAME);
			if(ids == null || ids.isEmpty()|| ids.get(0).getDataString() == null || ids.get(0).getDataString().isEmpty()) continue;
			String twitter_name = ids.get(0).getDataString();
			
			ResponseList<twitter4j.Status> tweets = null;
			try{
				tweets = twitter.getUserTimeline(twitter_name);
			}catch (Exception e) {
				System.err.println("Access denied to get Tweets : " + twitter_name);
				continue;
			}
			if(tweets != null ){
				for(twitter4j.Status tweet : tweets){
					if(tweet != null && tweet.getId() > 0){
						TwitterData data = new TwitterData();
						data.setType(TwitterDataType.TWEETS.toString());
						data.setDataString(tweet.getId()+"#"+tweet.getCreatedAt()+"#"+tweet.getText());
						data.setOwner(c);
						datas.put(tweet.getCreatedAt(), data);
					}				
				}
			}
			if(datas.size()>count){
				ArrayList<Date> dates = new ArrayList<Date>(datas.descendingMap().keySet());
				datas = new TreeMap<Date,TwitterData>(datas.tailMap(dates.get(count -1)));
			}
		}
		return new ArrayList<TwitterData>(datas.descendingMap().values());
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
