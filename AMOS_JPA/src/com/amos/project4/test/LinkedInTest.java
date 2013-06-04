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
import com.amos.project4.socialMedia.LinkedIn.LinkedInDataRetriever;
import com.amos.project4.socialMedia.LinkedIn.LinkedInPositions;
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
	}

	@Test
	public void testMeDetails() throws SAXException, IOException, ParserConfigurationException {
		Response res = this.connect.makeRequest("http://api.linkedin.com/v1/people/~:(id,first-name,last-name,headline,picture-url,public-profile-url)", false);
//		System.out.println(res.getBody());
		is.setCharacterStream(new StringReader(res.getBody()));
	    Document doc = db.parse(is);
	    String id = doc.getElementsByTagName("id").item(0).getTextContent();
		assertTrue(res!= null && res.isSuccessful() && id.equalsIgnoreCase("iGNeDrcELX"));
	}
	
	@Test
	public void testSearchByName() throws SAXException, IOException {
		Response res = this.connect.makeRequest("http://api.linkedin.com/v1/people-search?first-name=Romuald%20Jupiter&last-name=BAKAKEU%20NGASSAM", false);
		
		is.setCharacterStream(new StringReader(res.getBody()));
	    Document doc = db.parse(is);
		
	    String id = doc.getElementsByTagName("id").item(0).getTextContent();
		assertTrue(res!= null && res.isSuccessful() && id.equalsIgnoreCase("iGNeDrcELX"));
	}
	
	@Test
	public void testMeCompany() throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		Response res = this.connect.makeRequest("http://api.linkedin.com/v1/people/id=iGNeDrcELX/positions", true);
//		System.out.println(res.getBody());		
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
		Response res = this.connect.makeRequest("http://api.linkedin.com/v1/people/id=iGNeDrcELX/educations", false);
		//System.out.println(res.getBody());
		is.setCharacterStream(new StringReader(res.getBody()));
	    Document doc = db.parse(is);
		String total = doc.getElementsByTagName("educations").item(0).getAttributes().getNamedItem("total").getTextContent();
		//System.out.println(total);
	    NodeList nl = doc.getElementsByTagName("education");
	    for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			System.out.println(node.toString());
		}
	}
	
	@Test
	public void testMeContacts() throws SAXException, IOException, ParserConfigurationException {
		Response res = this.connect.makeRequest("http://api.linkedin.com/v1/people/id=iGNeDrcELX/connections:(id,last-name)", false);
		//System.out.println(res.getBody());
		is.setCharacterStream(new StringReader(res.getBody()));
	    Document doc = db.parse(is);
		String total = doc.getElementsByTagName("connections").item(0).getAttributes().getNamedItem("total").getTextContent();
		//System.out.println(total);
	    NodeList nl = doc.getElementsByTagName("education");
	    for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			System.out.println(node.toString());
		}
	}
	
	private LinkedInPositions parsePositionsResponse(String body){		
		Gson gson = new Gson();
		return gson.fromJson(body, LinkedInPositions.class);		
	}
	

}
