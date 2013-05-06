package com.amos.project4.controllers;

import java.util.ArrayList;

import com.amos.project4.models.Client;
import com.amos.project4.views.SearchParameters;

public interface ClientControllerInterface {
	
	/**
	 * get All Clients from the database	
	 * @return
	 */
	public ArrayList<Client> getAllClients();
	
	/**
	 * Add a client to the Database
	 * @param client
	 * @return boolean: true when successful, false else
	 */
	public boolean addClient(Client client);
	
	/**
	 * Update a client in the Database
	 */
	public boolean updateClient(Client oldClient, Client newClient);
	
	/** 
	 * delete a client entry from the database
	 */
	public boolean deleteClient(Client client);
	
	/**
	 * Set the search parameters
	 * 
	 */
	public void setSearch(SearchParameters search);
}
