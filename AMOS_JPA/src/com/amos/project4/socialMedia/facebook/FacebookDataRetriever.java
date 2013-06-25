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


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.amos.project4.models.Client;
import com.amos.project4.models.FacebookData;
import com.amos.project4.models.FacebookDataDAO;
import com.amos.project4.models.User;
import com.amos.project4.socialMedia.AccountSearchResultInterface;
import com.amos.project4.socialMedia.AccountSearchResultItem;
import com.amos.project4.socialMedia.DataRetrieverInterface;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;


public class FacebookDataRetriever implements DataRetrieverInterface{
	
	private FacebookDataDAO facebook_dao;
	private FacebookConnect connector;
	
	private static FacebookDataRetriever instance;
	
	private FacebookDataRetriever() {
		facebook_dao = FacebookDataDAO.getInstance();
		connector = FacebookConnect.getInstance();
	}
	
	public synchronized static FacebookDataRetriever getInstance(){
		if(instance == null){
			instance = new FacebookDataRetriever();
		}
		return instance;
	}
	
	public synchronized boolean init(User user){
		try{
			if(user == null) return false;
			facebook_dao = FacebookDataDAO.getInstance();
			connector = FacebookConnect.getInstance();
			
			// Establish connection with twitter
			return connector.login(user.getF_token(), user.getF_token_secret());
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public synchronized void deleteUserFacebookData(Client client,FacebookDataType type){
		if(client == null ) return;		
		if(!type.toString().equalsIgnoreCase(FacebookDataType.UID.toString())){
			this.facebook_dao.deleteFacebookDatas(client, type);
		}
	}
	
	@Override
	public synchronized AccountSearchResultInterface makeSearch(Client selectedClient,int begin, int end) {
		FacebookAccountSearchResult rslt = new FacebookAccountSearchResult(new ArrayList<AccountSearchResultItem>());	
		Connection<JsonObject> publicSearch =  connector.getFacebookClient().fetchConnection("search", JsonObject.class, 
				Parameter.with("q", selectedClient.getFirstname() + " " + selectedClient.getName()),//, Parameter.with("q", selectedClient.getName()),
				Parameter.with("type", "user")
				,Parameter.with("limit", "100"));
		if(publicSearch != null && !publicSearch.getData().isEmpty()){
			rslt.setNumResults(publicSearch.getData().size());
			int new_end = end > publicSearch.getData().size()?publicSearch.getData().size():end;
			for(JsonObject  data: publicSearch.getData().subList(begin, new_end)){
				// Get full User datas
				FacebookAccountSearchResultItem user_ = connector.getFacebookClient().fetchObject(data.getString("id"), FacebookAccountSearchResultItem.class);
				if(user_ != null && user_.getId().length() > 0){
					// get User picture URL
					String query = "SELECT uid, pic_big FROM user WHERE uid="+data.getString("id");
					List<FqlObject> pics = connector.getFacebookClient().executeFqlQuery(query, FqlObject.class);
					if(pics != null && pics.size() > 0){
						user_.setPictureURL(pics.get(0).pic_big);
					}
				}
				rslt.getList().add(user_);
			}
		}		
		return rslt;
	}
	
	public synchronized String importFacebookIDofUser(User user, Client client) {
		if(user == null || client == null) return "";
		
		List<FacebookData> account = this.facebook_dao.getAllFacebookDataOfClientByType(client.getID(),FacebookDataType.UID);
		String facebook_id = "";
		if(account == null || account.size() == 0 ){
			Connection<JsonObject> publicSearch =  connector.getFacebookClient().fetchConnection("search", JsonObject.class, Parameter.with("q", client.getFirstname()), Parameter.with("q", client.getName()),Parameter.with("type", "user"));
			if(publicSearch != null){
				facebook_id = publicSearch.getData().get(0).getString("id");
				saveFacebookData(client, facebook_id, FacebookDataType.UID);
			}
		}else{
			facebook_id = account.get(0).getDataString();
		}
		
		return facebook_id;
	}
	
	public synchronized void importFacebookData(User user,Client client, FacebookDataType type, String facebook_id) {
		if(user == null || client == null || facebook_id == null || facebook_id.isEmpty()) return;
		
		// get the facebook client
		DefaultFacebookClient facebook = connector.getFacebookClient();
		
		String client_facebook_ID = facebook_id;
		

		com.restfb.types.User user_;
		switch (type) {
		case BIRTHDAY:
			user_ = facebook.fetchObject(client_facebook_ID, com.restfb.types.User.class);
			if(user_ != null && user_.getId().length() > 0){
				saveFacebookData(client, user_.getBirthday(), type);
			}
			return;
		case USERNAME:
			user_ = facebook.fetchObject(client_facebook_ID, com.restfb.types.User.class);
			if(user_ != null && user_.getId().length() > 0){
				saveFacebookData(client, user_.getUsername(), type);
			}
			return;
		case RELATIONSHIP:
			user_ = facebook.fetchObject(client_facebook_ID, com.restfb.types.User.class);
			if(user_ != null && user_.getId().length() > 0){
				saveFacebookData(client, user_.getRelationshipStatus(), type);
			}
			return;
		case BIOGRAPHY :
			user_ = facebook.fetchObject(client_facebook_ID, com.restfb.types.User.class);
			if(user_ != null && user_.getId().length() > 0){
				saveFacebookData(client, user_.getBio(), type);
			}
			return;
		case EDUCATION:
			user_ = facebook.fetchObject(client_facebook_ID, com.restfb.types.User.class);
			if(user_ != null && user_.getId().length() > 0){
				List<com.restfb.types.User.Education> educations = user_.getEducation();
				if(educations != null){
					for(com.restfb.types.User.Education edu : educations){
						String year = (edu.getYear() != null) ?edu.getYear().getName():" ";
						String school = (edu.getSchool() != null) ? edu.getSchool().getName():" ";
						String degree =  (edu.getDegree() != null) ? edu.getDegree().getName():" ";
						saveFacebookData(client, degree +"#"+ year+"#"+school, type);
					}
				}				
			}
			return;
		case PROFILE_PICTURE:
			String query = "SELECT uid, pic_big FROM user WHERE uid="+client_facebook_ID;
			List<FqlObject> pics = facebook.executeFqlQuery(query, FqlObject.class);
			if(pics != null && pics.size() > 0){
				saveFacebookData(client, pics.get(0).pic_big, type);
			}
			return;
		case INTERESTS:
			user_ = facebook.fetchObject(client_facebook_ID, com.restfb.types.User.class);
			if(user_ != null && user_.getId().length() > 0){
				List<String> interrests = user_.getInterestedIn();
				if(interrests != null){
					for(String interest: interrests){
						saveFacebookData(client, interest, type);
					}
				}				
			}
			return;
		case WORKS:
			user_ = facebook.fetchObject(client_facebook_ID, com.restfb.types.User.class);
			if(user_ != null && user_.getId().length() > 0){
				List<com.restfb.types.User.Work> works = user_.getWork();
				if(works != null){
					for(com.restfb.types.User.Work work : works){
						String position = work.getPosition()!= null?work.getPosition().getName():" ";
						String employer = work.getEmployer() != null?work.getEmployer().getName():" ";
						String description = work.getDescription() != null?work.getDescription():" ";
						saveFacebookData(client, position + "#" +employer + "#" + description, type);
					}
				}				
			}
			return;
		case EVENTS:
			String query_e = "SELECT eid FROM event WHERE eid IN (SELECT eid from event_member where uid = " + client_facebook_ID + ")";
			List<FqlObject> events = facebook.executeFqlQuery(query_e, FqlObject.class);
			if(events != null && events.size() > 0){
				for (FqlObject ev : events) {
					try {
						com.restfb.types.Event tmp = facebook.fetchObject(ev.eid, com.restfb.types.Event.class);
						String location = tmp.getLocation() != null ? tmp.getLocation():" ";
						String owner = tmp.getOwner() != null?tmp.getOwner().getName():" ";
						saveFacebookData(client, tmp.getId()+"#"+tmp.getName()+"#"+tmp.getStartTime()+"#"+tmp.getEndTime()+"#"+location+"#"+owner+"#"+tmp.getDescription(), type);
						
					} catch (Exception e) {
						System.err.println("Could not get Data from Face book: Client ID = "+client_facebook_ID + " - Type : " + type.toString() + " ObjectID = " + ev.eid );
					}
					
				}
				
			}			
			return;
		case FRIENDS:
			List<FqlObject> friends = null;
			String queryf = "SELECT uid2 FROM friend WHERE uid1="+ client_facebook_ID;
			try{
				friends = facebook.executeFqlQuery(queryf, FqlObject.class);
			} catch (Exception e) {
				System.err.println("Could not get Data from Face book: Client ID = "+client_facebook_ID + " - Type : " + type.toString() );
				return;
			}
				
			if(friends != null && friends.size() > 0){
				for (FqlObject friend : friends) {
					try{
						com.restfb.types.User tmp = facebook.fetchObject(friend.uid2, com.restfb.types.User.class);
						String location = tmp.getLocation() != null ? tmp.getLocation().getName():" ";
						String about = tmp.getAbout() != null ? tmp.getAbout():" ";
						saveFacebookData(client, tmp.getId()+"#"+tmp.getName()+"#"+tmp.getGender()+"#"+location+"#"+about, type);
						
					} catch (Exception e) {
						System.err.println("Could not get Data from Face book: Client ID = "+client_facebook_ID + " - Type : " + type.toString() + " ObjectID = " + friend.uid2 );
					}
				}				
			}
			return;
		case LAST_POST:
			String query_p = "SELECT status_id FROM status WHERE uid =" + client_facebook_ID;//me()";
			List<FqlObject> rslts = facebook.executeFqlQuery(query_p, FqlObject.class);
			if(rslts != null && rslts.size() > 0){
				for (FqlObject ev : rslts) {
					try{
						com.restfb.types.StatusMessage tmp = facebook.fetchObject(ev.status_id, com.restfb.types.StatusMessage.class);
						saveFacebookData(client, tmp.getId()+"#"+tmp.getUpdatedTime()+"#"+tmp.getMessage(), type);
					} catch (Exception e) {
						System.err.println("Could not get Data from Face book: Client ID = "+client_facebook_ID + " - Type : " + type.toString() + " ObjectID = " + ev.status_id );
					}
				}				
			}
			String query_p2 = "SELECT post_id, actor_id, target_id, message, comments  FROM stream  WHERE source_id = "+ client_facebook_ID +" and actor_id =" + client_facebook_ID +
					"and not message ='' ORDER BY created_time  DESC LIMIT 100";//1482612290" ;//me()";
			rslts = facebook.executeFqlQuery(query_p2, FqlObject.class);
			if(rslts != null && rslts.size() > 0){
				System.out.println("Count = " + rslts.size() );
				for (FqlObject ev : rslts) {
					try{
						com.restfb.types.Post tmp = facebook.fetchObject(ev.post_id, com.restfb.types.Post.class);
						saveFacebookData(client, tmp.getId()+"#"+tmp.getCreatedTime()+"#"+tmp.getMessage(), type);
					} catch (Exception e) {
						System.err.println("Could not get Data from Face book: Client ID = "+client_facebook_ID + " - Type : " + type.toString() + " ObjectID = " + ev.post_id );
					}
				}
				
			}
			return;
		case MUTUAL_FRIENDS:
			queryf = "SELECT uid, first_name, last_name, pic_small FROM user WHERE uid IN (     SELECT uid2     FROM friend     WHERE uid1 = me() ) " +
							" AND uid IN (     SELECT uid2     FROM friend     WHERE uid1 = " + client_facebook_ID + " )";
			try{
				friends = facebook.executeFqlQuery(queryf, FqlObject.class);
			} catch (Exception e) {
				System.err.println("Could not get Data from Facebook: Client ID = "+client_facebook_ID + " - Type : " + type.toString()  );
				return;
			}
			
			if(friends != null && friends.size() > 0){
				for (FqlObject friend : friends) {
					try{
						com.restfb.types.User tmp = facebook.fetchObject(friend.uid, com.restfb.types.User.class);
						String location = tmp.getLocation() != null ? tmp.getLocation().getName():" ";
						String about = tmp.getAbout() != null ? tmp.getAbout():" ";
						saveFacebookData(client, tmp.getId()+"#"+tmp.getName()+"#"+tmp.getGender()+"#"+location+"#"+about, type);
					} catch (Exception e) {
						System.err.println("Could not get Data from Face book: Client ID = "+client_facebook_ID + " - Type : " + type.toString() + " ObjectID = " + friend.uid );
					}					
				}				
			}
			return;
		default:
			break;
		}		
	}
	
	public synchronized List<FacebookData> getLastPostOfClients(List<Client> clients, int count){
		if(clients == null ) return new ArrayList<FacebookData>();
		TreeMap<Date,FacebookData> datas = new TreeMap<Date,FacebookData>();
		
		// get the facebook client
		DefaultFacebookClient facebook = connector.getFacebookClient();
					
		for(Client c : clients){
			List<FacebookData> ids = c.getFacebookDatasByType(FacebookDataType.UID);
			if(ids == null || ids.isEmpty()|| ids.get(0).getDataString() == null || ids.get(0).getDataString().isEmpty()) continue;
			String client_facebook_ID = ids.get(0).getDataString();
			
			
			String query_p = "SELECT status_id FROM status WHERE uid =" + client_facebook_ID;
			List<FqlObject> rslts = facebook.executeFqlQuery(query_p, FqlObject.class);
			if(rslts != null && rslts.size() > 0){
				for (FqlObject ev : rslts) {
					try{
						com.restfb.types.StatusMessage tmp = facebook.fetchObject(ev.status_id, com.restfb.types.StatusMessage.class);
						if(tmp != null && tmp.getId() != null){
							FacebookData data = new FacebookData();
							data.setType(FacebookDataType.LAST_POST.toString());
							data.setDataString(tmp.getId()+"#"+tmp.getUpdatedTime()+"#"+tmp.getMessage());
							data.setOwner(c);
							datas.put(tmp.getUpdatedTime(), data);
						}
					} catch (Exception e) {
						System.err.println("Could not get Data from Face book: Client ID = "+client_facebook_ID + " - Type : " + FacebookDataType.LAST_POST.toString() + " ObjectID = " + ev.status_id );
					}
				}				
			}
			String query_p2 = "SELECT post_id, actor_id, target_id, message, comments  FROM stream  WHERE source_id = "+ client_facebook_ID +" and actor_id =" + client_facebook_ID +
					"and not message ='' ORDER BY created_time  DESC LIMIT 100";
			rslts = facebook.executeFqlQuery(query_p2, FqlObject.class);
			if(rslts != null && rslts.size() > 0){
				//System.out.println("Count = " + rslts.size() );
				for (FqlObject ev : rslts) {
					try{
						com.restfb.types.Post tmp = facebook.fetchObject(ev.post_id, com.restfb.types.Post.class);
						if(tmp != null && tmp.getId() != null){
							FacebookData data = new FacebookData();
							data.setType(FacebookDataType.LAST_POST.toString());
							data.setDataString(tmp.getId()+"#"+tmp.getCreatedTime()+"#"+tmp.getMessage());
							data.setOwner(c);
							datas.put(tmp.getCreatedTime(), data);
						}
					} catch (Exception e) {
						System.err.println("Could not get Data from Face book: Client ID = "+client_facebook_ID + " - Type : " + FacebookDataType.LAST_POST.toString() + " ObjectID = " + ev.post_id );
					}
				}				
			}
			if(datas.size()>count){
				ArrayList<Date> dates = new ArrayList<Date>(datas.descendingMap().keySet());
				datas = new TreeMap<Date,FacebookData>(datas.tailMap(dates.get(count -1)));
			}
		}
		return new ArrayList<FacebookData>(datas.descendingMap().values());
	}
	
	private synchronized void saveFacebookData(Client client,String dataString,FacebookDataType type){
		FacebookData data = new FacebookData();
		data.setType(type.toString());
		data.setDataString("" +dataString);
		data.setOwner(client);
		client.addFacebookData(data);
		facebook_dao.persistFacebookData(data);
	}
	
	public static class FqlObject {

		@Facebook
		String uid;

		@Facebook
		String pic_big;

		@Facebook
		String uid2;

		@Facebook
		String eid;

		@Facebook
		String status_id;
		
		@Facebook
		String post_id;
	}
}
