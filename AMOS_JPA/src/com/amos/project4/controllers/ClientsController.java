package com.amos.project4.controllers;

import java.util.ArrayList;

import com.amos.project4.models.Client;
import com.amos.project4.models.ClientDAO;
import com.amos.project4.views.SearchParameters;

public class ClientsController extends AbstractController implements ClientsControllerInterface{
	
	private ClientDAO clientDAO;
	
	private ArrayList<Client> clientlist = new ArrayList<Client>();
	private Client selectedClient;

	public ClientsController() {
		super();
		clientDAO = ClientDAO.getClientDAOInstance();
		clientlist = new ArrayList<Client>(clientDAO.getAllClients());
	}
	
	@Override
	public void updateInternally(Object arg) {
		if(arg != null && arg.getClass().equals(SearchParameters.class)){
			clientlist = new ArrayList<Client>(clientDAO.getAllFilteredClients((SearchParameters)arg));
		}else if(arg!= null && arg.getClass().equals(Client.class)){
			selectedClient = (Client) arg;
		}
	}

	@Override
	public ArrayList<Client> getAllClients() {
		return clientlist != null? clientlist: new ArrayList<Client>();		
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

	@Override
	public Client getSelectedClient() {
		return selectedClient;
	}
	
}
