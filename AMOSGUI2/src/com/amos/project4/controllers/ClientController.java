package com.amos.project4.controllers;

import java.util.ArrayList;

import com.amos.project4.models.Client;

public class ClientController extends AbstractController implements ClientControllerInterface{

	@Override
	public ArrayList<Client> getAllClients() {
		
		return new ArrayList<Client>();
	}

	@Override
	public boolean addClient(Client client) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateClient(Client oldClient, Client newClient) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteClient(Client client) {
		// TODO Auto-generated method stub
		return false;
	}

}
