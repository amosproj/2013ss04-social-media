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

import java.util.List;

import org.scribe.model.Response;

import com.amos.project4.models.Client;
import com.amos.project4.models.User;
import com.amos.project4.models.XingData;
import com.amos.project4.models.XingDataDAO;
import com.amos.project4.socialMedia.Xing.XingContacts.UserCard;
import com.amos.project4.socialMedia.Xing.XingProfileMessage.XingMessage;
import com.amos.project4.socialMedia.Xing.XingProfileVisits.Visit;
import com.amos.project4.socialMedia.Xing.XingUser.Company;
import com.amos.project4.socialMedia.Xing.XingUser.Date;
import com.google.gson.Gson;

public class XingDataRetriever {
	
	private XingDataDAO Xing_dao;
	private XingConnect connector;
	
	private static XingDataRetriever instance;
	
	private XingDataRetriever() {
		Xing_dao = XingDataDAO.getInstance();
		connector = XingConnect.getInstance();
	}
	
	public synchronized static XingDataRetriever getInstance(){
		if(instance == null){
			instance = new XingDataRetriever();
		}
		return instance;
	}
	
	public synchronized boolean init(User user){
		try{
			if(user == null) return false;
			Xing_dao = XingDataDAO.getInstance();
			connector = XingConnect.getInstance();
			
			// Establish connection with Xing
			connector.login(user.getT_token(), user.getT_token_secret());
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public synchronized  void deleteUserXingData(Client client, XingDataType type){
		if(client == null || type == null) return;		
		if(!type.toString().equalsIgnoreCase(XingDataType.ID.toString())){
			this.Xing_dao.deleteXingDatas(client, type);
		}
	}
	
	public synchronized  void importXingData(User user,Client client, XingDataType type){
		if(user == null || client == null) return;
		String url_request = "";
		Response response = null;
		
		List<XingData> account = this.Xing_dao.getAllXingDataOfClientByType(client.getID(),XingDataType.ID);
		String xing_id = "";
		if(account == null || account.size() == 0 ){
			url_request = "https://api.xing.com/v1/users/find_by_emails?emails="+ client.getMail();
			response = this.connector.makeRequest(url_request);
			if(response != null && response.isSuccessful()){
				XingUserSearchResult results = parseSearchresults(response.getBody());
				if(results != null && results.getResults().getTotal() > 0){
					saveXingData(client, ""+results.getResults().getItems().get(0).getUser().getId(), XingDataType.ID);
					xing_id = ""+results.getResults().getItems().get(0).getUser().getId();
				}
			}
		}else{
			xing_id = account.get(0).getDataString();
		}
		
		if(xing_id == null || xing_id.isEmpty()) return;
	
		switch (type) {
		case ID:
//			url_request = "https://api.xing.com/v1/users/find_by_emails?emails="+ client.getMail();
//			response = this.connector.makeRequest(url_request);
//			if(response != null){
//				XingUser xuser = parseResponse(response.getBody());
//				if(xuser.users.size() > 0)
//					saveXingData(client, ""+xuser.users.get(0).getId(), type);
//			}
			return;
		case BIRTHDAY:
			url_request = "https://api.xing.com/v1/users/"+ xing_id;
			response = this.connector.makeRequest(url_request);
			if(response != null){
				XingUser xuser = parseResponse(response.getBody());
				if(xuser.users.size() > 0){
					Date birthdate = xuser.users.get(0).getBith_date();
					if(birthdate != null){
						String date = birthdate.getDay()+"#"+birthdate.getMonth()+"#"+birthdate.getYear();
						saveXingData(client, date, type);
					}
				}
			}
			return;
		case PERMALINK:
			url_request = "https://api.xing.com/v1/users/"+ xing_id;
			response = this.connector.makeRequest(url_request);
			if(response != null){
				XingUser xuser = parseResponse(response.getBody());
				if(xuser.users.size() > 0)
					saveXingData(client, ""+xuser.users.get(0).getPermalink(), type);
			}
			return;
		case PROFILE_MESSAGE:
			url_request ="https://api.xing.com/v1/users/"+xing_id + "/profile_message";
			response = this.connector.makeRequest(url_request);
			if(response != null){
				XingProfileMessage msg = parseProfileResponse(response.getBody());
				if(msg != null){
					XingMessage pmsg = msg.getProfile_message();
					if(pmsg != null){
						saveXingData(client,pmsg.getUpdated_at() + "#" + pmsg.getMessage(), type);
					}
				}
			}
			return;
		case CONTACTS:
			url_request ="https://api.xing.com/v1/users/"+ xing_id +"/contacts?user_fields=id,display_name";
			response = this.connector.makeRequest(url_request);
			if(response != null){
				XingContacts contacts = parseContactResponse(response.getBody());
				if(contacts != null && contacts.getContacts() != null && contacts.getContacts().getUsers() != null){
					for(UserCard u : contacts.getContacts().getUsers()){
						saveXingData(client,u.getId() + "#" + u.getDisplay_name(), type);
					}
				}
			}
			return;
		case PROFILE_VISITS:
			url_request = "https://api.xing.com/v1/users/"+ xing_id + "/visits";
			response = this.connector.makeRequest(url_request);
			if(response != null){
				XingProfileVisits visits = parseProfileVisitsResponse(response.getBody());
				if(visits != null && visits.getVisits() != null){
					for(Visit v : visits.getVisits()){
						saveXingData(client,v.getVisited_at() + "#" + v.getUser_id() + "#" + v.getDisplay_name() + "#" + v.getPhotos_urls().getLarge() + "#" +  v.getCompany_name() , type);
					}
				}
			}
			return;
		case COMPANY:
			url_request = "https://api.xing.com/v1/users/"+ xing_id;
			response = this.connector.makeRequest(url_request);
			if(response != null){
				XingUser xuser = parseResponse(response.getBody());
				if(xuser.users.size() > 0){
					Company company = xuser.users.get(0).getProfessional_experience().getPrimary_company();
					if(company != null){
						String compagny = company.getName()+"#"+company.getTitle()+"#"+company.getCareer_level()+"#"+company.getDescription()+"#"+company.getCompany_size()+"#"+company.getIndustry()+"#"+company.getUrl();
						saveXingData(client, compagny, type);
					}
				}
			}
			return;
		default:
			break;
		}		
	}
	
	private synchronized void saveXingData(Client client,String dataString,XingDataType type){
		XingData data = new XingData();
		data.setType(type.toString());
		data.setDataString(dataString);
		data.setOwner(client);
		client.addXingData(data);
		Xing_dao.persistXingData(data);
	}
	
	private XingUser parseResponse(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, XingUser.class);		
	}
	
	private XingUserSearchResult parseSearchresults(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, XingUserSearchResult.class);		
	}
	
	private XingProfileMessage parseProfileResponse(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, XingProfileMessage.class);		
	}
	
	private XingContacts parseContactResponse(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, XingContacts.class);		
	}
	
	private XingProfileVisits parseProfileVisitsResponse(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, XingProfileVisits.class);		
	}
}
