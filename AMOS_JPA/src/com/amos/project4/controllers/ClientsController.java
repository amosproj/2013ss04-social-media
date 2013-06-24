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

import com.amos.project4.models.BirthdayClient;
import com.amos.project4.models.Client;
import com.amos.project4.models.ClientDAO;
import com.amos.project4.views.AbstractControlledView;
import com.amos.project4.views.SearchParameters;

public class ClientsController extends AbstractController implements
		ClientsControllerInterface {

	private ClientDAO clientDAO;

	private ArrayList<Client> clientlist = new ArrayList<Client>();
	private Client selectedClient;

	public ClientsController() {
		super();
		clientDAO = ClientDAO.getInstance();
		clientlist = new ArrayList<Client>(clientDAO.getAllClients());
	}

	@Override
	public  void update(Observable o, Object arg) {
		updateInternally(arg, o);
		for (AbstractControlledView view : registeredViews) {
			if(view != null){
				if (arg != null && arg.getClass().equals(SearchParameters.class)) {
					view.modelPropertyChange(o, arg);
				} else if (arg != null && arg.getClass().equals(Client.class)) {
					view.modelPropertyChange(o, this.getSelectedClient());
				}
			}
		}		
	}
	
	@Override
	public void updateInternally(Object arg, Observable o) {
		if (arg != null && arg.getClass().equals(SearchParameters.class)) {
			clientlist = new ArrayList<Client>(
					clientDAO.getAllFilteredClients((SearchParameters) arg));
		} else if (arg != null && arg.getClass().equals(Client.class)) {
			selectedClient = clientDAO.getclient(((Client) arg).getID());
		}
	}

	@Override
	public ArrayList<Client> getAllClients() {
		//clientlist = new ArrayList<Client>(clientDAO.getAllClients());
		return clientlist != null ? clientlist : new ArrayList<Client>();
	}

	@Override
	public void refresh() {
		clientDAO.refresh();
		clientlist = new ArrayList<Client>(clientDAO.getAllClients());
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
	
	public List<BirthdayClient> getAllBirthDayOfTheWeek() {
		return clientDAO.getAllBirthdayOfTheWeek();
	}

}
