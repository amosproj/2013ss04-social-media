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
package com.amos.project4.socialMedia;

import java.util.ArrayList;
import java.util.List;

import com.amos.project4.models.TwitterDataDAO;
import com.amos.project4.models.User;
import com.amos.project4.models.UserDAO;
import com.amos.project4.socialMedia.twitter.TwitterConnect;
import com.amos.project4.socialMedia.twitter.TwitterDataType;

public class TwitterDataRetriever {
	
	private TwitterDataDAO twitter_dao;
	private TwitterConnect connector;
	private UserDAO userDAO;
	
	private static TwitterDataRetriever instance;
	
	private TwitterDataRetriever() {
		twitter_dao = TwitterDataDAO.getInstance();
		connector = TwitterConnect.getInstance();
		userDAO = UserDAO.getUserDAOInstance();
	}
	
	public static TwitterDataRetriever getInstance(){
		if(instance == null){
			instance = new TwitterDataRetriever();
		}
		return instance;
	}
	
	public void retrieveAllTwitterData(List<User> users, List<TwitterDataType> types){
		ArrayList<User> _users = getConsernedUsers(users);
		
		
	}
	
	private ArrayList<User> getConsernedUsers(List<User> users){
		ArrayList<User> _users = null;
		if(users == null || users.isEmpty()){
			_users = new ArrayList<User>(userDAO.getAllUsers());
		}else{
			_users = new ArrayList<User>(users);
		}
		return _users;
	}
	
	

}
