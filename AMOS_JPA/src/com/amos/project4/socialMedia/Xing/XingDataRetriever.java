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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import org.scribe.model.Response;

import com.amos.project4.models.Client;
import com.amos.project4.models.User;
import com.amos.project4.models.XingData;
import com.amos.project4.models.XingDataDAO;
import com.amos.project4.socialMedia.AccountSearchResultInterface;
import com.amos.project4.socialMedia.DataRetrieverInterface;
import com.amos.project4.socialMedia.Xing.XingContacts.UserCard;
import com.amos.project4.socialMedia.Xing.XingProfileMessage.XingMessage;
import com.amos.project4.socialMedia.Xing.XingProfileVisits.Visit;
import com.amos.project4.socialMedia.Xing.XingUser.Company;
import com.amos.project4.socialMedia.Xing.XingUser.Date;
import com.amos.project4.socialMedia.Xing.XingUserSearchResult.Item;
import com.amos.project4.socialMedia.Xing.XingUserSearchResult.XingUserId;
import com.google.gson.Gson;

public class XingDataRetriever implements DataRetrieverInterface{
	
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
	
	@Override
	public synchronized AccountSearchResultInterface makeSearch(Client selectedClient,int begin, int end) {
		XingAccountSearchResult list = new XingAccountSearchResult();
		String url_request = "https://api.xing.com/v1/users/find_by_emails?emails="+ selectedClient.getMail();
		Response response = this.connector.makeRequest(url_request);
		if(response != null && response.isSuccessful()){
			XingUserSearchResult results = parseSearchresults(response.getBody());
			if(results != null && results.getResults().getTotal() > 0){
				for(Item  item : results.getResults().getItems()){
					if(item.getUser() == null) continue;
					url_request = "https://api.xing.com/v1/users/"+ item.getUser().getId();
					response = this.connector.makeRequest(url_request);
					if(response != null){
						XingUser xuser = parseResponse(response.getBody());
						if(xuser.users.size() > 0){
							list.getList().addAll(xuser.users);
						}
					}
					
				}
			}
		}	
		return list;
	}
	
	public synchronized String importXingIDofUser(User user,Client client){
			if(user == null || client == null) return "";
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
						XingUserId rslt = results.getResults().getItems().get(0).getUser();
						if(rslt != null){
							saveXingData(client, ""+rslt.getId(), XingDataType.ID);
							xing_id = ""+rslt.getId();
						}
					}
				}
			}else{
				xing_id = account.get(0).getDataString();
			}		
			return xing_id;
	}
	
	public synchronized  void importXingData(User user,Client client, XingDataType type,String xing_id){
		String url_request = "";
		Response response = null;
		
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
						saveXingData(client,v.getVisited_at() + "#" + v.getUser_id() + "#" + v.getDisplay_name() + "#" + "" + "#" +  v.getCompany_name() , type);
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
		case USER_PICTURE:
			url_request = "https://api.xing.com/v1/users/"+ xing_id;
			response = this.connector.makeRequest(url_request);
			if(response != null){
				XingUser xuser = parseResponse(response.getBody());
				if(xuser.users.size() > 0){
					String pictureURL = xuser.users.get(0).getPictureURL();
					if(pictureURL != null){
						saveXingData(client, pictureURL, type);
					}
				}
			}
			return;
		default:
			break;
		}		
	}
	
	public synchronized List<XingData> getLastModifiedCompanies(List<Client> clients, int count){
		if(clients == null ) return new ArrayList<XingData>();
		TreeMap<java.util.Date,XingData> datas = new TreeMap<java.util.Date,XingData>();
					
		for(Client c : clients){
			List<XingData> ids = c.getXingDatasByType(XingDataType.ID);
			if(ids == null || ids.isEmpty()|| ids.get(0).getDataString() == null || ids.get(0).getDataString().isEmpty()) continue;
			String xing_id = ids.get(0).getDataString();
			
			String url_request = "https://api.xing.com/v1/users/"+ xing_id;
			Response response = this.connector.makeRequest(url_request);
			if(response != null){
				XingUser xuser = parseResponse(response.getBody());
				if(xuser.users.size() > 0){
					Company company = xuser.users.get(0).getProfessional_experience().getPrimary_company();
					if(company != null ){
						
					    DateFormat df = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH);
					    java.util.Date result = new java.util.Date(System.currentTimeMillis());
						try {
							String target = company.getBegin_date();
							if(target != null && !target.isEmpty()){
								result = df.parse(target);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					    
					    XingData data = new XingData();
						data.setType(XingDataType.COMPANY.toString());
						data.setDataString(result+"#"+company.getName()+"#"+company.getCareer_level()+"#"+company.getIndustry());
						data.setOwner(c);
						datas.put(result, data);
					}
				}
			}
			
			
			if(datas.size()>count){
				ArrayList<java.util.Date> dates = new ArrayList<java.util.Date>(datas.descendingMap().keySet());
				datas = new TreeMap<java.util.Date,XingData>(datas.tailMap(dates.get(count -1)));
			}
		}
		return new ArrayList<XingData>(datas.descendingMap().values());
	}
	
	private synchronized void saveXingData(Client client,String dataString,XingDataType type){
		XingData data = new XingData();
		data.setType(type.toString());
		data.setDataString(dataString);
		data.setOwner(client);
		client.addXingData(data);
		Xing_dao.persistXingData(data);
	}
	
	private synchronized XingUser parseResponse(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, XingUser.class);		
	}
	
	private synchronized XingUserSearchResult parseSearchresults(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, XingUserSearchResult.class);		
	}
	
	private synchronized XingProfileMessage parseProfileResponse(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, XingProfileMessage.class);		
	}
	
	private synchronized XingContacts parseContactResponse(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, XingContacts.class);		
	}
	
	private synchronized XingProfileVisits parseProfileVisitsResponse(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, XingProfileVisits.class);		
	}
}
