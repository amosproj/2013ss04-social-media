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
