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
