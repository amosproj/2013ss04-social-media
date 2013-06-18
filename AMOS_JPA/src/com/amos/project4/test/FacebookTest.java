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

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.amos.project4.models.Client;
import com.amos.project4.models.ClientDAO;
import com.amos.project4.models.User;
import com.amos.project4.models.UserDAO;
import com.amos.project4.socialMedia.facebook.FacebookConnect;
import com.amos.project4.socialMedia.facebook.FacebookDataRetriever;
import com.amos.project4.socialMedia.facebook.FacebookDataRetriever.FqlObject;
import com.restfb.Connection;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;

public class FacebookTest {

	private FacebookConnect connect;
	FacebookDataRetriever retriever;
	UserDAO udao;
	ClientDAO cdao;
	User user;
	Client client;
	
	String facebook_id = "";
	
	@Before
	public void setUp() throws Exception {
		connect = FacebookConnect.getInstance();
		retriever = FacebookDataRetriever.getInstance();
		udao = UserDAO.getUserDAOInstance();
		user = udao.getUserByUsername("test");
		retriever.init(user);
		cdao = ClientDAO.getInstance();
		client = cdao.getclient(new Integer(221));
		facebook_id = retriever.getFacebookIDofUser(user, client);
	}

	@Test
	public void testMeDetails() throws SAXException, IOException, ParserConfigurationException {
		Connection<JsonObject> publicSearch =  connect.getFacebookClient().fetchConnection("search", JsonObject.class, Parameter.with("q", "jupiter"), Parameter.with("type", "user"));
		System.out.println("Public search: " + publicSearch.getData().get(0).getInt("id"));
	}
	
	@Test
	public void testFQLSearch(){
		String query = "SELECT uid, about_me, name, pic_big FROM user WHERE strpos(name,'jupiter') >=0";
		List<FqlObject> pics = connect.getFacebookClient().executeFqlQuery(query, FqlObject.class);
		if(pics != null && pics.size() > 0){
			System.out.println(pics.get(0).toString());
		}
	}
}
