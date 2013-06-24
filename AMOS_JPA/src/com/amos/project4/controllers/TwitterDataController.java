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
package com.amos.project4.controllers;

import java.util.List;
import java.util.Observable;

import com.amos.project4.models.Client;
import com.amos.project4.models.ClientDAO;
import com.amos.project4.models.TwitterData;
import com.amos.project4.models.TwitterDataDAO;
import com.amos.project4.socialMedia.AccountSearchResult;
import com.amos.project4.socialMedia.AccountSearchResultItem;
import com.amos.project4.socialMedia.twitter.TwitterDataRetriever;
import com.amos.project4.socialMedia.twitter.TwitterDataType;

public class TwitterDataController extends AbstractController implements MediaSearchController {

	private Client selected_client;
	private TwitterDataDAO twitter_DAO;
	private List<TwitterData> twitterDatas;
	
	
	public TwitterDataController() {
		super();
		this.twitter_DAO = TwitterDataDAO.getInstance();
		List<Client> clients = ClientDAO.getInstance().getAllClients();
		if(clients != null && !clients.isEmpty()) this.selected_client = clients.get(0);
	}

	public void updateInternally(Object arg, Observable o) {
		if(o!= null && arg.getClass().equals(Client.class)){
			Client c = (Client) arg;
			this.setSelected_client(c);
		}
	}

	public void setSelected_client(Client selected_client) {
		this.selected_client = selected_client;
	}

	public TwitterDataDAO getTwitter_DAO() {
		return twitter_DAO;
	}

	public List<TwitterData> getTwitterDatas() {
		return twitterDatas;
	}

	@Override
	public AccountSearchResult<AccountSearchResultItem> getAccountSearchresult() {
		return new AccountSearchResult<AccountSearchResultItem>(TwitterDataRetriever.getInstance(),this.selected_client);
	}

	@Override
	public void setSelectedClientAccount(String ID) {
		twitter_DAO.deleteTwitterDatas(selected_client, TwitterDataType.TWITTER_NAME);
		TwitterData data = new TwitterData();
		data.setType(TwitterDataType.TWITTER_NAME.toString());
		data.setDataString(ID);
		data.setOwner(selected_client);
		selected_client.addTwitterData(data);
		twitter_DAO.persistTwitterData(data);		
	}

}
