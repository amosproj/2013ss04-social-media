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

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.amos.project4.models.Client;
import com.amos.project4.models.ClientDAO;
import com.amos.project4.models.User;
import com.amos.project4.models.UserDAO;
import com.amos.project4.socialMedia.twitter.TwitterConnect;
import com.amos.project4.socialMedia.twitter.TwitterDataRetriever;

public class TwitterTest {

	private TwitterConnect connect;
	TwitterDataRetriever retriever;
	UserDAO udao;
	ClientDAO cdao;
	User user;
	Client client;
	
	String facebook_id = "";
	
	@Before
	public void setUp() throws Exception {
		connect = TwitterConnect.getInstance();
		retriever = TwitterDataRetriever.getInstance();
		udao = UserDAO.getUserDAOInstance();
		user = udao.getUserByUsername("test");
		retriever.init(user);
		cdao = ClientDAO.getInstance();
		client = cdao.getclient(new Integer(221));
		facebook_id = retriever.getTwitterIDofUser(user, client);
	}

	@Test
	public void testMeDetails() throws SAXException, IOException, ParserConfigurationException, TwitterException {
		Twitter twitter = connect.getTwitter();
//		twitter4j.User t_user = twitter.showUser(facebook_id);
//		System.out.println(t_user.getName());
		int page = 1;
		ResponseList<twitter4j.User> users;
		do {
            users = twitter.searchUsers("Jupiter BAKAKEU", page );
            for (twitter4j.User user : users) {
                if (user.getStatus() != null) {
                    System.out.println("@" + user.getScreenName() + " - " + user.getStatus().getText());
                } else {
                    // the user is protected
                    System.out.println("@" + user.getScreenName());
                }
            }
            page++;
        } while (users.size() != 0 && page < 50);
		
	}
}
