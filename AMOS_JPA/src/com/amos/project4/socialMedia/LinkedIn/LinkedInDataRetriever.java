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
import java.util.List;

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
import com.google.gson.Gson;

public class LinkedInDataRetriever {
	
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
	
	public synchronized  void importLinkedInData(User user,Client client, LinkedInDataType type) throws SAXException, IOException{
		if(user == null || client == null) return;
		String url_request = "";
		Response response = null;
		
		List<LinkedInData> account = this.LinkedIn_dao.getAllLinkedInDataOfClientByType(client.getID(),LinkedInDataType.ID);
		String linkedIn_id = "";
		if(account == null || account.size() == 0 ){
			url_request = "http://api.linkedin.com/v1/people-search?first-name="+client.getFirstname().replace(" ", "%20")+"&last-name="+ client.getName().replace(" ", "%20");
			response = this.connector.makeRequest(url_request, false);
			if(response != null && response.isSuccessful()){
				is.setCharacterStream(new StringReader(response.getBody()));
			    Document doc = db.parse(is);
			    String id = doc.getElementsByTagName("id").getLength()>0?doc.getElementsByTagName("id").item(0).getTextContent():null;
			    
				if(id != null && !id.isEmpty()){
					saveLinkedInData(client, id, LinkedInDataType.ID);
					linkedIn_id = id;
				}
			}
		}else{
			linkedIn_id = account.get(0).getDataString();
		}
		
		switch (type) {
		case ID:			
			break;
		case COMPANY:
			url_request = "http://api.linkedin.com/v1/people/id="+ linkedIn_id +"/positions";
			response = this.connector.makeRequest(url_request, true);
			if(response != null && response.isSuccessful()){
				LinkedInPositions pos = parsePositionsResponse(response.getBody());
				if(pos != null && pos.isCurent() && pos.get_total() > 0){
					String id = pos.getValues().get(0).getCompany().getId();
					String name = pos.getValues().get(0).getCompany().getName();
					String ctype = pos.getValues().get(0).getCompany().getType();
					String industry = pos.getValues().get(0).getCompany().getIndustry();
					saveLinkedInData(client, id + "#" + name + "#" + ctype + "#" + industry, type);
				}
			}
			return;
		case HEADLINES:
			url_request = "http://api.linkedin.com/v1/people/id="+ linkedIn_id ;
			response = this.connector.makeRequest(url_request, false);
			if(response != null && response.isSuccessful()){
				is.setCharacterStream(new StringReader(response.getBody()));
			    Document doc = db.parse(is);
			    String headline = doc.getElementsByTagName("headline").getLength()>0?doc.getElementsByTagName("headline").item(0).getTextContent():null;
			    
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
				if(pos != null && pos.isCurent() && pos.get_total() > 0){
					String id = pos.getId();
					String title = pos.getTitle();
					String name = pos.getValues().get(0).getCompany().getName();
					saveLinkedInData(client, id + "#" + title + "#" + name , type);
				}
			}
			return;
		case POSITIONS:
			url_request = "http://api.linkedin.com/v1/people/id="+ linkedIn_id +"/positions";
			response = this.connector.makeRequest(url_request, true);
			if(response != null && response.isSuccessful()){
				LinkedInPositions pos = parsePositionsResponse(response.getBody());
				if(pos != null && pos.get_total() > 0){
					for(int j = 0; j < pos.get_total(); j++){
						String id = pos.getValues().get(j).getCompany().getId();
						String name = pos.getValues().get(j).getCompany().getName();
						String ctype = pos.getValues().get(j).getCompany().getType();
						String industry = pos.getValues().get(j).getCompany().getIndustry();
						saveLinkedInData(client, id + "#" + name + "#" + ctype + "#" + industry, type);
					}
				}
			}
			return;
		case EDUCATIONS:
			url_request = "http://api.linkedin.com/v1/people/id="+ linkedIn_id +"/educations";
			response = this.connector.makeRequest(url_request, true);
			if(response != null && response.isSuccessful()){
				is.setCharacterStream(new StringReader(response.getBody()));
			    Document doc = db.parse(is);
				String total = doc.getElementsByTagName("educations").item(0).getAttributes().getNamedItem("total").getTextContent();
				if(Integer.parseInt(total) > 0){
				    NodeList nl = doc.getElementsByTagName("education");
				    for (int i = 0; i < nl.getLength(); i++) {
						Node education = nl.item(i);
						Node id = education.getChildNodes().item(0);
						String id_ = id.getTextContent();
						
						Node school_name = education.getChildNodes().item(1);
						String school_name_ = school_name.getTextContent();
						
						Node degree = education.getChildNodes().item(2);
						String degree_ = degree.getTextContent();
						
						Node start_date = education.getChildNodes().item(3);
						String start_year = start_date.getChildNodes().getLength()>0?start_date.getChildNodes().item(0).getTextContent():"";
						
						Node end_date = education.getChildNodes().item(4);
						String end_year = end_date.getChildNodes().getLength()>0?end_date.getChildNodes().item(0).getTextContent():"";
						
						saveLinkedInData(client, id_ + "#" + school_name_ + "#" + degree_ + "#" + start_year + "#" + end_year, type);
					}
				}
			}
			return;
		case CONTACTS:
			url_request = "http://api.linkedin.com/v1/people/id="+linkedIn_id+"/connections:(id,first-name,last-name)";
			response = this.connector.makeRequest(url_request, true);
			if(response != null && response.isSuccessful()){
				is.setCharacterStream(new StringReader(response.getBody()));
			    Document doc = db.parse(is);
				String total = doc.getElementsByTagName("connections").item(0).getAttributes().getNamedItem("total").getTextContent();
				if(Integer.parseInt(total) > 0){
				    NodeList nl = doc.getElementsByTagName("person");
				    for (int i = 0; i < nl.getLength(); i++) {
						Node person = nl.item(i);
						Node id = person.getChildNodes().item(0);
						String id_ = id.getTextContent();
						
						Node first_name = person.getChildNodes().item(1);
						String first_name_ = first_name.getTextContent();
						
						Node last_name = person.getChildNodes().item(2);
						String last_name_ = last_name.getTextContent();
						
						saveLinkedInData(client, id_ + "#" + first_name_ + "#" + last_name_, type);
					}
				}
			}
			return;
		default:
			break;
		}
		
		
	}
	
	private synchronized void saveLinkedInData(Client client,String dataString,LinkedInDataType type){
		LinkedInData data = new LinkedInData();
		data.setType(type.toString());
		data.setDataString(dataString);
		data.setOwner(client);
		client.addLinkedInData(data);
		LinkedIn_dao.persistLinkedInData(data);
	}
	
	private LinkedInPositions parsePositionsResponse(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, LinkedInPositions.class);		
	}
	
	
}

