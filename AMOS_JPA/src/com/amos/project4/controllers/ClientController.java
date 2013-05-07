/*
 * 
 * This file is part of the software project "Social Media and Datev".
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

import com.amos.project4.models.Client;
import com.amos.project4.models.ClientDAO;
import com.amos.project4.views.SearchParameters;

public class ClientController extends AbstractController implements ClientControllerInterface{
	
	private ClientDAO clientDAO;
	SearchParameters search;

	public ClientController() {
		super();
		clientDAO = ClientDAO.getClientDAOInstance();
		search = new SearchParameters();
	}

	@Override
	public ArrayList<Client> getAllClients() {
		if(this.evt_object != null){
			search = (SearchParameters) this.evt_object;
		}		
		return new ArrayList<Client>(clientDAO.getAllFilteredClients(search));
	}

	@Override
	public boolean addClient(Client client) {		
		return clientDAO.addClient(client);
	}

	@Override
	public boolean updateClient(Client oldClient, Client newClient) {
		return clientDAO.updateClient(newClient);
	}

	@Override
	public boolean deleteClient(Client client) {
		return clientDAO.deleteClient(client);
	}
	
	public void setSearch(SearchParameters search) {
		this.search = search;
	}
}
