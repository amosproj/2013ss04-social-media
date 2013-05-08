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

package com.amos.project4.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.amos.project4.models.Client;
import com.amos.project4.models.ClientDAO;

/**
 * 
 * @author jupiter BAKAKEU
 * 
 */
public class ClientDAOTest {

	private ClientDAO cDAO;

	@Before
	public void setUp() throws Exception {
		cDAO = ClientDAO.getClientDAOInstance();
		List<Client> clients;
		clients = cDAO.getAllClients();
		// Do we have entries?
		boolean createNewEntries = (clients.size() == 0);

		// No, so lets create new entries
		if (createNewEntries) {
			assertTrue(cDAO.getAllClients().size() == 0);
			for (int i = 0; i < 40 ; i++) {
				Client client = new Client();
				client.setFirstname("Jupiter_" + i);
				client.setName("BAKAKEU_" + i);
				cDAO.addClient(client);
			}
		}
	}

	@Test
	public void checkAvailableClients() {
		List<Client> clients;
		clients = cDAO.getAllClients();
		assertTrue(clients.size() > 0);
	}

	@Test(expected = javax.persistence.NoResultException.class)
	public void deletePerson() {
		// retrieve a client with an ID that does not exist
		Client _client = cDAO.getclient(1001);
	}

}
