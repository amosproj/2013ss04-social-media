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
package com.amos.project4.socialMedia.LinkedIn;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.scribe.model.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.amos.project4.models.Client;
import com.amos.project4.models.LinkedInData;
import com.amos.project4.models.LinkedInDataDAO;
import com.amos.project4.models.User;
import com.amos.project4.socialMedia.AccountSearchResultInterface;
import com.amos.project4.socialMedia.AccountSearchResultItem;
import com.amos.project4.socialMedia.DataRetrieverInterface;
import com.amos.project4.socialMedia.LinkedIn.LinkedInConnections.ConnectionsValue;
import com.amos.project4.socialMedia.LinkedIn.LinkedInEducations.EducationValue;
import com.amos.project4.socialMedia.LinkedIn.LinkedInPositions.CompanyValue;
import com.google.gson.Gson;

public class LinkedInDataRetriever implements DataRetrieverInterface{
	
	private LinkedInDataDAO LinkedIn_dao;
	private LinkedInConnect connector;
	
	InputSource is;
	DocumentBuilder db;
	
	private static LinkedInDataRetriever instance;
	
	private LinkedInDataRetriever() {
		LinkedIn_dao 	= LinkedInDataDAO.getInstance();
		connector 		= LinkedInConnect.getInstance();
		
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			is = new InputSource();
		} catch (ParserConfigurationException e) {
			//e.printStackTrace();
		}
	}
	
	public synchronized static LinkedInDataRetriever getInstance(){
		if(instance == null){
			instance = new LinkedInDataRetriever();
		}
		return instance;
	}
	
	public synchronized boolean init(User user){
		try{
			if(user == null) return false;
			LinkedIn_dao = LinkedInDataDAO.getInstance();
			connector = LinkedInConnect.getInstance();
			
			// Establish connection with LinkedIn
			connector.login(user.getT_token(), user.getT_token_secret());
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public synchronized  void deleteUserLinkedInData(Client client, LinkedInDataType type){
		if(client == null || type == null) return;		
		if(!type.toString().equalsIgnoreCase(LinkedInDataType.ID.toString())){
			this.LinkedIn_dao.deleteLinkedInDatas(client, type);
		}
	}
	
	@Override
	public synchronized AccountSearchResultInterface makeSearch(Client selectedClient, int begin, int end) {
		String url_request = "http://api.linkedin.com/v1/people-search:(people:(id,first-name,last-name,picture-url,headline,public-profile-url),num-results)?start="+ begin +"&count="+ (end - begin ) +"&first-name="+selectedClient.getFirstname().replace(" ", "%20")+"&last-name="+ selectedClient.getName().replace(" ", "%20");
		Response response = this.connector.makeRequest(url_request, true);
		if(response != null && response.isSuccessful()){
			linkedInSearchResult rslt = parseSearchUserResponse(response.getBody());
			if(rslt != null ) return rslt;
		}
		return new AccountSearchResultInterface() {			
			@Override
			public List<AccountSearchResultItem> getValues() {
				return new ArrayList<AccountSearchResultItem>();
			}
			
			@Override
			public long getNumResults() {
				return 0;
			}
		};
	}
	
	public synchronized String getLinkedInIDofUser(User user,Client client) throws SAXException, IOException{
		if(user == null || client == null) return "";
		String url_request = "";
		Response response = null;
		
		List<LinkedInData> account = this.LinkedIn_dao.getAllLinkedInDataOfClientByType(client.getID(),LinkedInDataType.ID);
		String linkedIn_id = "";
		if(account == null || account.size() == 0 ){
			url_request = "http://api.linkedin.com/v1/people-search?first-name="+client.getFirstname().replace(" ", "%20")+"&last-name="+ client.getName().replace(" ", "%20");
			response = this.connector.makeRequest(url_request, true);
			if(response != null && response.isSuccessful()){
				linkedInSearchResult rslt = parseSearchUserResponse(response.getBody());
				if(rslt.getNumResults() > 0 && rslt.getPeople().get_total() > 0){
					String id = rslt.getPeople().getValues().get(0).getId();					    
					if(id != null && !id.isEmpty()){
						saveLinkedInData(client, id, LinkedInDataType.ID);
						linkedIn_id = id;
					}
				}
			}
		}else{
			linkedIn_id = account.get(0).getDataString();
		}
		
		return linkedIn_id;
		
	}
	
	public synchronized  void importLinkedInData(User user,Client client, LinkedInDataType type, String linkedIn_id) throws SAXException, IOException{
		if(user == null || client == null) return ;
		String url_request = "";
		Response response = null;
		
		switch (type) {
		case ID:
			//saveLinkedInData(client, linkedIn_id, type);
			return;
		case COMPANY:
			url_request = "http://api.linkedin.com/v1/people/id="+ linkedIn_id +"/positions";
			response = this.connector.makeRequest(url_request, true);
			if(response != null && response.isSuccessful()){
				LinkedInPositions pos = parsePositionsResponse(response.getBody());
				if(pos != null && pos.get_total() > 0  ){
					for( CompanyValue  val : pos.getValues()){
						if(val.isCurent()){
							String id = val.getCompany().getId();
							String name = val.getCompany().getName();
							String ctype = val.getCompany().getType();
							String industry = val.getCompany().getIndustry();
							saveLinkedInData(client, id + "#" + name + "#" + ctype + "#" + industry, type);
						}
					}
				}
			}
			return;
		case HEADLINES:
			url_request = "http://api.linkedin.com/v1/people/id="+ linkedIn_id ;
			response = this.connector.makeRequest(url_request, true);
			if(response != null && response.isSuccessful()){
				LinkedInUser user_pro_head = parseUserResponse(response.getBody());
			    String headline = user_pro_head.getHeadline();			    
				if(headline != null && !headline.isEmpty()){
					saveLinkedInData(client, headline, type);
				}
			}
			return;
		case CURRENT_JOB:
			url_request = "http://api.linkedin.com/v1/people/id="+ linkedIn_id +"/positions";
			response = this.connector.makeRequest(url_request, true);
			if(response != null && response.isSuccessful()){
				LinkedInPositions pos = parsePositionsResponse(response.getBody());
				if(pos != null && pos.get_total() > 0  ){
					for( CompanyValue  val : pos.getValues()){
						if(val.isCurent()){
							String id = val.getId();
							String title = val.getTitle();
							String name = val.getCompany().getName();
							saveLinkedInData(client, id + "#" + title + "#" + name , type);
						}
					}
				}
			}
			return;
		case POSITIONS:
			url_request = "http://api.linkedin.com/v1/people/id="+ linkedIn_id +"/positions";
			response = this.connector.makeRequest(url_request, true);
			if(response != null && response.isSuccessful()){
				LinkedInPositions pos = parsePositionsResponse(response.getBody());
				if(pos != null && pos.get_total() > 0  ){
					for( CompanyValue  val : pos.getValues()){
						String id = val.getCompany().getId();
						String name = val.getCompany().getName();
						String ctype = val.getCompany().getType();
						String industry = val.getCompany().getIndustry();
						saveLinkedInData(client, id + "#" + name + "#" + ctype + "#" + industry, type);
					}
				}
			}
			return;
		case EDUCATIONS:
			url_request = "http://api.linkedin.com/v1/people/id="+ linkedIn_id +"/educations";
			response = this.connector.makeRequest(url_request, true);
			if(response != null && response.isSuccessful()){
				LinkedInEducations educations = parseEducationsResponse(response.getBody());
				if(educations != null && educations.get_total() > 0){
					for(EducationValue val: educations.getValues()){
						if(val.getEducation() != null){
							String id_ = val.getEducation().getId();
							String school_name_ = val.getEducation().getSchoolName();
							String degree_ = val.getEducation().getDegree();
							String start_year = val.getEducation().getStartDate();
							String end_year = val.getEducation().getEndDate();
							saveLinkedInData(client, id_ + "#" + school_name_ + "#" + degree_ + "#" + start_year + "#" + end_year, type);
						}
					}
				}
//				is.setCharacterStream(new StringReader(response.getBody()));
//			    Document doc = db.parse(is);
//				String total = doc.getElementsByTagName("educations").item(0).getAttributes().getNamedItem("total").getTextContent();
//				if(Integer.parseInt(total) > 0){
//				    NodeList nl = doc.getElementsByTagName("education");
//				    for (int i = 0; i < nl.getLength(); i++) {
//						Node education = nl.item(i);
//						Node id = education.getChildNodes().item(0);
//						String id_ = id.getTextContent();
//						
//						Node school_name = education.getChildNodes().item(1);
//						String school_name_ = school_name.getTextContent();
//						
//						Node degree = education.getChildNodes().item(2);
//						String degree_ = degree.getTextContent();
//						
//						Node start_date = education.getChildNodes().item(3);
//						String start_year = start_date.getChildNodes().getLength()>0?start_date.getChildNodes().item(0).getTextContent():"";
//						
//						Node end_date = education.getChildNodes().item(4);
//						String end_year = end_date.getChildNodes().getLength()>0?end_date.getChildNodes().item(0).getTextContent():"";
//						
//						saveLinkedInData(client, id_ + "#" + school_name_ + "#" + degree_ + "#" + start_year + "#" + end_year, type);
//					}
//				}
			}
			return;
		case CONTACTS:
			url_request = "http://api.linkedin.com/v1/people/id="+linkedIn_id+"/connections";
			response = this.connector.makeRequest(url_request, true);
			if(response != null && response.isSuccessful()){
				LinkedInConnections connections = parseLinkedInConnections(response.getBody());
				if(connections.get_total() > 0){
					for(ConnectionsValue val : connections.getValues()){
						String id_ = val.getId();
						String first_name_ = val.getFirstName();
						String last_name_ = val.getLastName();
						String industy_ = val.getIndustry();
						saveLinkedInData(client, id_ + "#" + first_name_ + "#" + last_name_ + "#" + industy_, type);
					}
				}
//				is.setCharacterStream(new StringReader(response.getBody()));
//			    Document doc = db.parse(is);
//				String total = doc.getElementsByTagName("connections").item(0).getAttributes().getNamedItem("total").getTextContent();
//				if(Integer.parseInt(total) > 0){
//				    NodeList nl = doc.getElementsByTagName("person");
//				    for (int i = 0; i < nl.getLength(); i++) {
//						Node person = nl.item(i);
//						Node id = person.getChildNodes().item(0);
//						String id_ = id.getTextContent();
//						
//						Node first_name = person.getChildNodes().item(1);
//						String first_name_ = first_name.getTextContent();
//						
//						Node last_name = person.getChildNodes().item(2);
//						String last_name_ = last_name.getTextContent();
//						
//						saveLinkedInData(client, id_ + "#" + first_name_ + "#" + last_name_, type);
//					}
//				}
			}
			return;
		case TWITTER_ACCOUNT:
			url_request = "http://api.linkedin.com/v1/people/id="+ linkedIn_id+":(id,primary-twitter-account)";
			response = this.connector.makeRequest(url_request, true);
			if(response != null && response.isSuccessful()){
				LinkedInUser user_tw = parseUserResponse(response.getBody());
			    String tmp = user_tw.getPrimaryTwitterAccount();			    
				if(tmp != null && !tmp.isEmpty()){
					saveLinkedInData(client, tmp, type);
				}
			};
			return;
		case INTERESTS:
			url_request = "http://api.linkedin.com/v1/people/id="+ linkedIn_id + ":(id,interests)";
			response = this.connector.makeRequest(url_request, true);
			if(response != null && response.isSuccessful()){
				LinkedInUser user_interests = parseUserResponse(response.getBody());
			    String tmp = user_interests.getInterests();			    
				if(tmp != null && !tmp.isEmpty()){
					saveLinkedInData(client, tmp, type);
				}
			}
			return;
		case PHONES_NUMBER:
			url_request = "http://api.linkedin.com/v1/people/id="+linkedIn_id+":(id,phone-numbers)";
			response = this.connector.makeRequest(url_request, false);
			if(response != null && response.isSuccessful()){
				is.setCharacterStream(new StringReader(response.getBody()));
			    Document doc = db.parse(is);
				String total = doc.getElementsByTagName("phone-numbers").item(0).getAttributes().getNamedItem("total").getTextContent();
				if(Integer.parseInt(total) > 0){
				    NodeList nl = doc.getElementsByTagName("phone-number");
				    for (int i = 0; i < nl.getLength(); i++) {
						Node number = nl.item(i);
						Node n_type = number.getChildNodes().item(0);
						String n_type_ = n_type.getTextContent();
						
						Node n_number = number.getChildNodes().item(1);
						String n_number__ = n_number.getTextContent();
						
						saveLinkedInData(client, n_type_ + "#" + n_number__ , type);
					}
				}
			}
			return;
		case PROFILE_VIEWS:
			break;
		case PROFILE_PICTURE:
			url_request = "http://api.linkedin.com/v1/people/id="+ linkedIn_id + ":(id,picture-url)";
			response = this.connector.makeRequest(url_request, true);
			if(response != null && response.isSuccessful()){
				LinkedInUser user_pro_pic = parseUserResponse(response.getBody());
			    String tmp = user_pro_pic.getPictureUrl();		    
				if(tmp != null && !tmp.isEmpty()){
					saveLinkedInData(client, tmp, type);
				}
			}
			return;
		case PROFILE_URL:
			url_request = "http://api.linkedin.com/v1/people/id="+ linkedIn_id + ":(id,public-profile-url)";
			response = this.connector.makeRequest(url_request, true);
			if(response != null && response.isSuccessful()){
				LinkedInUser user_pro_url = parseUserResponse(response.getBody());				
			    String profile_url = user_pro_url.getPublicProfileUrl();			    
				if(profile_url != null && !profile_url.isEmpty()){
					saveLinkedInData(client, profile_url, type);
				}
			}
			return;
		default:
			break;
		}
		
		
	}
	
	public synchronized List<LinkedInData> getLastModifiedClients(List<Client> clients, int count){
		if(clients == null ) return new ArrayList<LinkedInData>();
		TreeMap<Date,LinkedInData> datas = new TreeMap<Date,LinkedInData>();
					
		for(Client c : clients){
			List<LinkedInData> ids = c.getLinkedInDatasByType(LinkedInDataType.ID);
			if(ids == null || ids.isEmpty()|| ids.get(0).getDataString() == null || ids.get(0).getDataString().isEmpty()) continue;
			String linkedIn_id = ids.get(0).getDataString();
			
			
			String url_request = "http://api.linkedin.com/v1/people/id="+ linkedIn_id+":(id,first-name,last-name,headline,last-modified-timestamp)" ;
			Response response = this.connector.makeRequest(url_request, true);
			if(response != null && response.isSuccessful()){
				LinkedInUser user_pro_head = parseUserResponse(response.getBody());
						    
				if(user_pro_head != null && user_pro_head.getHeadline() != null && !user_pro_head.getHeadline().isEmpty()){
					LinkedInData data = new LinkedInData();
					data.setType(LinkedInDataType.HEADLINES.toString());
					data.setDataString(user_pro_head.getId()+"#"+user_pro_head.getLastModifiedDate()+"#"+user_pro_head.getHeadline());
					data.setOwner(c);
					datas.put(user_pro_head.getLastModifiedDate(), data);
				}
			}
			if(datas.size()>count){
				ArrayList<Date> dates = new ArrayList<Date>(datas.descendingMap().keySet());
				datas = new TreeMap<Date,LinkedInData>(datas.tailMap(dates.get(count -1)));
			}
		}
		return new ArrayList<LinkedInData>(datas.descendingMap().values());
	}
	
	
	private synchronized void saveLinkedInData(Client client,String dataString,LinkedInDataType type){
		LinkedInData data = new LinkedInData();
		data.setType(type.toString());
		data.setDataString(dataString);
		data.setOwner(client);
		client.addLinkedInData(data);
		LinkedIn_dao.persistLinkedInData(data);
	}
	
	private synchronized LinkedInPositions parsePositionsResponse(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, LinkedInPositions.class);		
	}
	
	private synchronized LinkedInUser parseUserResponse(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, LinkedInUser.class);		
	}
	
	private synchronized LinkedInEducations parseEducationsResponse(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, LinkedInEducations.class);		
	}
	
	public synchronized LinkedInConnections parseLinkedInConnections(String body){
		Gson gson = new Gson();
		return gson.fromJson(body, LinkedInConnections.class);
	}
	
	private synchronized linkedInSearchResult parseSearchUserResponse(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, linkedInSearchResult.class);		
	}	
}

