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

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.scribe.model.Response;

import com.amos.project4.models.Client;
import com.amos.project4.models.ClientDAO;
import com.amos.project4.models.User;
import com.amos.project4.models.UserDAO;
import com.amos.project4.models.XingData;
import com.amos.project4.socialMedia.Xing.XingConnect;
import com.amos.project4.socialMedia.Xing.XingContacts;
import com.amos.project4.socialMedia.Xing.XingDataRetriever;
import com.amos.project4.socialMedia.Xing.XingDataType;
import com.amos.project4.socialMedia.Xing.XingProfileMessage;
import com.amos.project4.socialMedia.Xing.XingProfileVisits;
import com.amos.project4.socialMedia.Xing.XingUser;
import com.amos.project4.socialMedia.Xing.XingUserSearchResult;
import com.google.gson.Gson;


public class XingConnectTest {
	
	private XingConnect connect;
	XingDataRetriever retriever;
	UserDAO udao;
	ClientDAO cdao;
	User user;
	Client client;
	String xing_id;
	@Before
	public void setUp() throws Exception {
		connect = XingConnect.getInstance();
		retriever = XingDataRetriever.getInstance();
		udao = UserDAO.getUserDAOInstance();
		user = udao.getUserByUsername("test");
		retriever.init(user);
		cdao = ClientDAO.getInstance();
		client = cdao.getclient(new Integer(221));
		xing_id = retriever.importXingIDofUser(user, client);
	}

	@Test
	public void testMeDetails() {
		Response res = this.connect.makeRequest("https://api.xing.com/v1/users/me");
		System.out.println(res.getBody());
		XingUser user = parseUserResponse(res.getBody());
		assertTrue(res!= null && res.isSuccessful() && !user.users.isEmpty());
	}
	
	@Test
	public void testGetLastPostOfClients(){
		int count = 10;
		List<XingData> datas = retriever.getLastModifiedCompanies(cdao.getAllClients(), count);		
		for (XingData data : datas) {
			System.out.println(data.getDataString().split("#")[0] + " : " + data.getDataString().split("#")[1] + " from " + data.getOwner().getFirstname() + " " + data.getOwner().getName());
		}
		assertTrue(datas!= null && datas.size() <= count);
	}
	
	@Test
	public void testSearchByEmail() {
		Response res = this.connect.makeRequest("https://api.xing.com/v1/users/find_by_emails?emails=jupiterbak2002@yahoo.fr");
		XingUserSearchResult results = parseSearchresults(res.getBody());
//		System.out.println(res.getBody());
		String id = results.getResults().getItems().get(0).getUser().getId();
		assertTrue(res!= null && res.isSuccessful() && id.equalsIgnoreCase("16291420_c75757"));
	}
	
	@Test
	public void testProfileMessage()  {
		Response res = this.connect.makeRequest("https://api.xing.com/v1/users/16291420_c75757/profile_message");
		XingProfileMessage msg = parseProfileResponse(res.getBody());
//		System.out.println(res.getBody());
		assertTrue(res!= null && res.isSuccessful() );
	}
	
	@Test
	public void testGetContacts()  {
		Response res = this.connect.makeRequest("https://api.xing.com/v1/users/16291420_c75757/contacts?limit=5&user_fields=id,display_name");
		XingContacts contacts = parseContactResponse(res.getBody());
		assertTrue(res!= null && res.isSuccessful() );
	}
	
	@Test
	public void testProfileVisits()  {
		Response res = this.connect.makeRequest("https://api.xing.com/v1/users/16291420_c75757/visits");
		XingProfileVisits visits = parseProfileVisitsResponse(res.getBody());
		assertTrue(res!= null && res.isSuccessful() );
	}
	
	@Test
	public void testXingRetrieverBirthday()  {
		retriever.deleteUserXingData(client,XingDataType.BIRTHDAY );
		retriever.importXingData(user, client, XingDataType.BIRTHDAY,xing_id);
	}
	
	@Test
	public void testXingRetrieverID()  {
		retriever.deleteUserXingData(client,XingDataType.ID );
		retriever.importXingData(user, client, XingDataType.ID,xing_id);
	}
	
	@Test
	public void testXingRetrieverProfileMessage()  {
		retriever.deleteUserXingData(client,XingDataType.PROFILE_MESSAGE );
		retriever.importXingData(user, client, XingDataType.PROFILE_MESSAGE,xing_id);
	}
	
	@Test
	public void testXingRetrieverCompany()  {
		retriever.deleteUserXingData(client,XingDataType.COMPANY );
		retriever.importXingData(user, client, XingDataType.COMPANY,xing_id);
	}
	
	@Test
	public void testXingRetrieverContacts()  {
		retriever.deleteUserXingData(client,XingDataType.CONTACTS );
		retriever.importXingData(user, client, XingDataType.CONTACTS,xing_id);
	}
	
	@Test
	public void testXingRetrieverProfileVisits()  {
		retriever.deleteUserXingData(client,XingDataType.PROFILE_VISITS);
		retriever.importXingData(user, client, XingDataType.PROFILE_VISITS,xing_id);
	}
	
	@Test
	public void testXingRetrieverPermalink()  {
		retriever.deleteUserXingData(client,XingDataType.PERMALINK);
		retriever.importXingData(user, client, XingDataType.PERMALINK,xing_id);
	}
	
	
	private XingUser parseUserResponse(String body){		
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


