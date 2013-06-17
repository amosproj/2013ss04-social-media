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

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.amos.project4.models.Client;
import com.amos.project4.models.ClientDAO;
import com.amos.project4.models.FacebookData;
import com.amos.project4.models.FacebookDataDAO;
import com.amos.project4.socialMedia.AccountSearchResult;
import com.amos.project4.socialMedia.AccountSearchResultItem;
import com.amos.project4.socialMedia.facebook.FacebookDataType;

public class FacebookDataController extends AbstractController implements MediaSearchController {

	private Client selected_client;
	private FacebookDataDAO facebook_DAO;
	private List<FacebookData> facebookDatas;
	
	
	public FacebookDataController() {
		super();
		this.facebook_DAO = FacebookDataDAO.getInstance();
		List<Client> clients = ClientDAO.getInstance().getAllClients();
		if(clients != null && !clients.isEmpty()) setSelected_client(clients.get(0));
	}

	public void updateInternally(Object arg, Observable o) {
		if(o!= null && arg.getClass().equals(Client.class)){
			Client c = (Client) arg;
			this.setSelected_client(c);
		}
	}

	public void setSelected_client(Client selected_client) {
		this.selected_client = selected_client;
		facebookDatas = selected_client.getFacebook_datas();
	}

	public FacebookDataDAO getFacebook_DAO() {
		return facebook_DAO;
	}

	public List<FacebookData> getFacebookDatas(FacebookDataType type) {
		ArrayList<FacebookData> rslts = new ArrayList<FacebookData>();
		for (FacebookData facebookData : this.selected_client.getFacebook_datas()) {
			if(facebookData.getType().equalsIgnoreCase(type.toString())){
				rslts.add(facebookData);
			}
		}
		return rslts;
	}

	@Override
	public AccountSearchResult<AccountSearchResultItem> getAccountSearchresult() {
		return null;
	}

	@Override
	public void setSelectedClientAccount(String ID) {
		
	}
}
