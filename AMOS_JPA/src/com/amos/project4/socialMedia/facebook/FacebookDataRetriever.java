package com.amos.project4.socialMedia.facebook;


import java.util.List;

import com.amos.project4.models.Client;
import com.amos.project4.models.FacebookData;
import com.amos.project4.models.FacebookDataDAO;
import com.amos.project4.models.User;
import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;


public class FacebookDataRetriever {
	
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
	
	public synchronized void deleteUserFacebookData(Client client){
		if(client == null ) return;
		List<FacebookData> datas = this.facebook_dao.getAllFacebookDataOfClient(client.getID());
		for(FacebookData data : datas){
			if(!data.getType().equalsIgnoreCase(FacebookDataType.UID.toString())){
				this.facebook_dao.deleteFacebookData(data);
			}
		}
		
	}
	
	public synchronized void importFacebookData(User user,Client client, FacebookDataType type) {
		if(user == null || client == null) return;
		
		// get the facebook client
		DefaultFacebookClient facebook = connector.getFacebookClient();
		
		//Check if account of client exist and get it
		List<FacebookData> account = facebook_dao.getAllFacebookDataOfClientBytype(client.getID(), FacebookDataType.UID);
		if(account == null || account.size() == 0 ) 
			return; // the client doesn't have an account yet
		String client_facebook_ID = account.get(0).getDataString();
		

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
						saveFacebookData(client, edu.getYear()+"|"+edu.getSchool()+"|"+edu.getDegree(), type);
					}
				}				
			}
			return;
		case PROFILE_PICTURE:
			String query = "SELECT uid, pic FROM user WHERE uid="+client_facebook_ID;
			List<FqlObject> pics = facebook.executeFqlQuery(query, FqlObject.class);
			if(pics != null && pics.size() > 0){
				saveFacebookData(client, pics.get(0).pic, type);
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
						saveFacebookData(client, work.getPosition() + "|" +work.getEmployer(), type);
					}
				}				
			}
			return;
		case EVENTS:
			String query_e = "SELECT eid FROM event WHERE eid IN (SELECT eid from event_member where uid = 1482612290)";
			List<FqlObject> events = facebook.executeFqlQuery(query_e, FqlObject.class);
			if(events != null && events.size() > 0){
				for (FqlObject ev : events) {
					com.restfb.types.Event tmp = facebook.fetchObject(ev.eid, com.restfb.types.Event.class);
					saveFacebookData(client, tmp.getId()+"|"+tmp.getName()+"|"+tmp.getStartTime()+"|"+tmp.getEndTime()+"|"+tmp.getLocation()+"|"+tmp.getOwner()+"|"+tmp.getDescription(), type);
				}
				
			}			
			return;
		case FRIENDS:
			String queryf = "SELECT uid2 FROM friend WHERE uid1="+ client_facebook_ID;
			List<FqlObject> friends = facebook.executeFqlQuery(queryf, FqlObject.class);
			if(friends != null && friends.size() > 0){
				for (FqlObject friend : friends) {
					saveFacebookData(client, friend.uid2, type);
				}				
			}
			return;
		case LAST_POST:
			String query_p = "SELECT status_id FROM status WHERE uid =" + client_facebook_ID;//me()";
			List<FqlObject> rslts = facebook.executeFqlQuery(query_p, FqlObject.class);
			if(rslts != null && rslts.size() > 0){
				for (FqlObject ev : rslts) {
					com.restfb.types.StatusMessage tmp = facebook.fetchObject(ev.status_id, com.restfb.types.StatusMessage.class);
					saveFacebookData(client, tmp.getId()+"|"+tmp.getUpdatedTime()+"|"+tmp.getMessage(), type);
				}				
			}
			String query_p2 = "SELECT post_id, actor_id, target_id, message, comments  FROM stream  WHERE source_id = "+ client_facebook_ID +" and actor_id =" + client_facebook_ID +
					"and not message ='' ORDER BY created_time  DESC LIMIT 100";//1482612290" ;//me()";
			rslts = facebook.executeFqlQuery(query_p2, FqlObject.class);
			if(rslts != null && rslts.size() > 0){
				System.out.println("Count = " + rslts.size() );
				for (FqlObject ev : rslts) {
					com.restfb.types.Post tmp = facebook.fetchObject(ev.post_id, com.restfb.types.Post.class);
					saveFacebookData(client, tmp.getId()+"|"+tmp.getCreatedTime()+"|"+tmp.getMessage(), type);
				}
				
			}
			return;
		case MUTUAL_FRIENDS:
			queryf = "SELECT uid, first_name, last_name, pic_small FROM user WHERE uid IN (     SELECT uid2     FROM friend     WHERE uid1 = me() ) " +
							" AND uid IN (     SELECT uid2     FROM friend     WHERE uid1 = " + client_facebook_ID + " )";
			friends = facebook.executeFqlQuery(queryf, FqlObject.class);
			if(friends != null && friends.size() > 0){
				for (FqlObject friend : friends) {
					saveFacebookData(client, friend.uid, type);
				}				
			}
			return;
		default:
			break;
		}		
	}
	
	private void saveFacebookData(Client client,String dataString,FacebookDataType type){
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
		String pic;

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
