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
package com.amos.project4.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Before;
import org.junit.Test;
import org.scribe.model.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.amos.project4.models.Client;
import com.amos.project4.models.ClientDAO;
import com.amos.project4.models.User;
import com.amos.project4.models.UserDAO;
import com.amos.project4.socialMedia.LinkedIn.LinkedInConnect;
import com.amos.project4.socialMedia.LinkedIn.LinkedInConnections;
import com.amos.project4.socialMedia.LinkedIn.LinkedInConnections.ConnectionsValue;
import com.amos.project4.socialMedia.LinkedIn.LinkedInDataRetriever;
import com.amos.project4.socialMedia.LinkedIn.LinkedInDataType;
import com.amos.project4.socialMedia.LinkedIn.LinkedInEducations;
import com.amos.project4.socialMedia.LinkedIn.LinkedInEducations.EducationValue;
import com.amos.project4.socialMedia.LinkedIn.LinkedInPositions;
import com.amos.project4.socialMedia.LinkedIn.LinkedInUser;
import com.amos.project4.socialMedia.LinkedIn.linkedInSearchResult;
import com.google.gson.Gson;

public class LinkedInTest {

	private LinkedInConnect connect;
	LinkedInDataRetriever retriever;
	UserDAO udao;
	ClientDAO cdao;
	User user;
	Client client;
	InputSource is;
	DocumentBuilder db;
	
	String linkedIn_id = "";
	
	@Before
	public void setUp() throws Exception {
		connect = LinkedInConnect.getInstance();
		db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		is = new InputSource();	   
	    
		retriever = LinkedInDataRetriever.getInstance();
		udao = UserDAO.getUserDAOInstance();
		user = udao.getUserByUsername("test");
		retriever.init(user);
		cdao = ClientDAO.getInstance();
		client = cdao.getclient(new Integer(221));
		linkedIn_id = retriever.getLinkedInIDofUser(user, client);
	}

	@Test
	public void testMeDetails() throws SAXException, IOException, ParserConfigurationException {
		Response res = this.connect.makeRequest("http://api.linkedin.com/v1/people/~:(id,first-name,last-name,headline,picture-url,public-profile-url,primary-twitter-account)", true);
		//System.out.println(res.getBody());
		LinkedInUser user = parseUserResponse(res.getBody());
		assertTrue(res!= null && res.isSuccessful() && user.getId().equalsIgnoreCase("iGNeDrcELX"));
	}
	
	private LinkedInUser parseUserResponse(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, LinkedInUser.class);		
	}
	
	@Test
	public void testSearchByName() throws SAXException, IOException {
		Response res = this.connect.makeRequest("http://api.linkedin.com/v1/people-search?first-name=Romuald%20Jupiter&last-name=BAKAKEU%20NGASSAM", true);
		//System.out.println(res.getBody());
		linkedInSearchResult rslt = parseSearchUserResponse(res.getBody());
		if(rslt.getNumResults() > 0 && rslt.getPeople().get_total() > 0){
			String id = rslt.getPeople().getValues().get(0).getId();
			assertTrue(res!= null && res.isSuccessful() && id.equalsIgnoreCase("iGNeDrcELX"));
		}
	}
	
	@Test
	public void testSearchByName2() throws SAXException, IOException {
		String url_request = "http://api.linkedin.com/v1/people-search:(people:(id,first-name,last-name,picture-url,headline,public-profile-url),num-results)?start="+ 0 +"&count="+ (20 ) +"&first-name=Romuald%20Jupiter&last-name=BAKAKEU%20NGASSAM";
		Response response = this.connect.makeRequest(url_request, true);
		if(response != null && response.isSuccessful()){
			linkedInSearchResult rslt = parseSearchUserResponse(response.getBody());
			if(rslt.getNumResults() > 0 && rslt.getPeople().get_total() > 0){
				
			}
		}
	}
	
	private linkedInSearchResult parseSearchUserResponse(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, linkedInSearchResult.class);		
	}
	
	@Test
	public void testMeCompany() throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		Response res = this.connect.makeRequest("http://api.linkedin.com/v1/people/id=iGNeDrcELX/positions", true);
		//System.out.println(res.getBody());		
		LinkedInPositions pos = parsePositionsResponse(res.getBody());
		String id = pos.getValues().get(0).getCompany().getId();
		assertTrue(res!= null && res.isSuccessful() && id.equalsIgnoreCase("743470"));
	}
	
	@Test
	public void testMeHeadline() throws SAXException, IOException, ParserConfigurationException {
		Response res = this.connect.makeRequest("http://api.linkedin.com/v1/people/~:(id,first-name,last-name,headline,picture-url,public-profile-url)", false);
//		System.out.println(res.getBody());
		is.setCharacterStream(new StringReader(res.getBody()));
	    Document doc = db.parse(is);
		
	    String headline = doc.getElementsByTagName("headline").item(0).getTextContent();
		assertTrue(res!= null && res.isSuccessful() && !headline.isEmpty());
	}
	
	@Test
	public void testMeEducations() throws SAXException, IOException, ParserConfigurationException {
		Response res = this.connect.makeRequest("http://api.linkedin.com/v1/people/id=iGNeDrcELX/educations", true);
		//System.out.println(res.getBody());
		LinkedInEducations edus = parseEducationsResponse(res.getBody());
		if(edus.get_total() > 0){
			for(EducationValue  val : edus.getValues()){
				System.out.println(val.getEducation().getSchoolName());
			}
		}
	}
	
	private LinkedInEducations parseEducationsResponse(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, LinkedInEducations.class);		
	}
	
	@Test
	public void testMeContacts() throws SAXException, IOException, ParserConfigurationException {
		Response res = this.connect.makeRequest("http://api.linkedin.com/v1/people/id=iGNeDrcELX/connections", true);
		//System.out.println(res.getBody());
		LinkedInConnections connections = parseLinkedInConnections(res.getBody());
		if(connections.get_total() > 0){
			for(ConnectionsValue val : connections.getValues()){
				System.out.println(val.getLastName());
			}
		}
	}
	
	public LinkedInConnections parseLinkedInConnections(String body){
		Gson gson = new Gson();
		return gson.fromJson(body, LinkedInConnections.class);
	}
	
	@Test
	public void testMetwitterAccount() throws SAXException, IOException{
		Response res = this.connect.makeRequest("http://api.linkedin.com/v1/people/id=iGNeDrcELX:(id,primary-twitter-account)", true);
		//System.out.println(res.getBody());
		LinkedInUser user = parseUserResponse(res.getBody());
		System.out.println(user.getPrimaryTwitterAccount());
	}
	
	@Test
	public void testMeProfilePicture() throws SAXException, IOException, ParserConfigurationException {
		Response res = this.connect.makeRequest("http://api.linkedin.com/v1/people/id=iGNeDrcELX:(id,picture-url)", true);
		//System.out.println(res.getBody());
		LinkedInUser user = parseUserResponse(res.getBody());
		assertTrue(res!= null && res.isSuccessful() && user.getId().equalsIgnoreCase("iGNeDrcELX"));
	}
	
	@Test
	public void testMeProfileURL() throws SAXException, IOException, ParserConfigurationException {
		Response res = this.connect.makeRequest("http://api.linkedin.com/v1/people/~:(id,first-name,last-name,headline,picture-url,public-profile-url)", false);
		//System.out.println(res.getBody());
		is.setCharacterStream(new StringReader(res.getBody()));
	    Document doc = db.parse(is);
	    NodeList nl = doc.getElementsByTagName("public-profile-url");
	    for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			System.out.println(node.toString());
		}
	}
	
	@Test
	public void testMeInterests() throws SAXException, IOException, ParserConfigurationException {
		Response res = this.connect.makeRequest("http://api.linkedin.com/v1/people/id=iGNeDrcELX:(id,interests)", true);
		//System.out.println(res.getBody());
		LinkedInUser user = parseUserResponse(res.getBody());
		System.out.println(user.getInterests());
	}
	
	@Test
	public void testMePhonesNumbers() throws SAXException, IOException, ParserConfigurationException {
		Response res = this.connect.makeRequest("http://api.linkedin.com/v1/people/id=iGNeDrcELX:(id,phone-numbers)", false);
		//System.out.println(res.getBody());
		is.setCharacterStream(new StringReader(res.getBody()));
	    Document doc = db.parse(is);
	    NodeList nl = doc.getElementsByTagName("phone-numbers");
	    for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			System.out.println(node.toString());
		}
	}
	
	private LinkedInPositions parsePositionsResponse(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, LinkedInPositions.class);		
	}
	
	@Test
	public void testLinkedInID() throws SAXException, IOException  {
		retriever.deleteUserLinkedInData(client,LinkedInDataType.ID );
		retriever.importLinkedInData(user, client, LinkedInDataType.ID,linkedIn_id);
	}
	
	@Test
	public void testLinkedInCOMPANY() throws SAXException, IOException  {
		retriever.deleteUserLinkedInData(client,LinkedInDataType.COMPANY);
		retriever.importLinkedInData(user, client, LinkedInDataType.COMPANY,linkedIn_id);
	}
	
	@Test
	public void testLinkedInHEADLINES() throws SAXException, IOException  {
		retriever.deleteUserLinkedInData(client,LinkedInDataType.HEADLINES );
		retriever.importLinkedInData(user, client, LinkedInDataType.HEADLINES,linkedIn_id);
	}
	
	@Test
	public void testLinkedInCURRENT_JOB() throws SAXException, IOException  {
		retriever.deleteUserLinkedInData(client,LinkedInDataType.CURRENT_JOB );
		retriever.importLinkedInData(user, client, LinkedInDataType.CURRENT_JOB,linkedIn_id);
	}
	
	@Test
	public void testLinkedInPOSITIONS() throws SAXException, IOException  {
		retriever.deleteUserLinkedInData(client,LinkedInDataType.POSITIONS );
		retriever.importLinkedInData(user, client, LinkedInDataType.POSITIONS,linkedIn_id);
	}
	
	@Test
	public void testLinkedInEDUCATIONS() throws SAXException, IOException  {
		retriever.deleteUserLinkedInData(client,LinkedInDataType.EDUCATIONS );
		retriever.importLinkedInData(user, client, LinkedInDataType.EDUCATIONS,linkedIn_id);
	}
	
	@Test
	public void testLinkedInCONTACTS() throws SAXException, IOException  {
		retriever.deleteUserLinkedInData(client,LinkedInDataType.CONTACTS );
		retriever.importLinkedInData(user, client, LinkedInDataType.CONTACTS,linkedIn_id);
	}
	
	@Test
	public void testLinkedInTWITTER_ACCOUNT() throws SAXException, IOException  {
		retriever.deleteUserLinkedInData(client,LinkedInDataType.TWITTER_ACCOUNT );
		retriever.importLinkedInData(user, client, LinkedInDataType.TWITTER_ACCOUNT,linkedIn_id);
	}
	
	@Test
	public void testLinkedInPHONES_NUMBER() throws SAXException, IOException  {
		retriever.deleteUserLinkedInData(client,LinkedInDataType.PHONES_NUMBER );
		retriever.importLinkedInData(user, client, LinkedInDataType.PHONES_NUMBER,linkedIn_id);
	}
	
	@Test
	public void testLinkedInPROFILE_PICTURES() throws SAXException, IOException  {
		retriever.deleteUserLinkedInData(client,LinkedInDataType.PROFILE_PICTURES );
		retriever.importLinkedInData(user, client, LinkedInDataType.PROFILE_PICTURES,linkedIn_id);
	}
	
	@Test
	public void testLinkedInINTERESTS() throws SAXException, IOException  {
		retriever.deleteUserLinkedInData(client,LinkedInDataType.INTERESTS );
		retriever.importLinkedInData(user, client, LinkedInDataType.INTERESTS,linkedIn_id);
	}
	
	@Test
	public void testLinkedInPROFILE_URL() throws SAXException, IOException  {
		retriever.deleteUserLinkedInData(client,LinkedInDataType.PROFILE_URL );
		retriever.importLinkedInData(user, client, LinkedInDataType.PROFILE_URL,linkedIn_id);
	}
	
	

}
