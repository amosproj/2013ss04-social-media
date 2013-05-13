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

package com.amos.project4.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.amos.project4.views.SearchParameters;

/**
 * 
 * @author jupiter BAKAKEU
 * 
 */
public class ClientDAO {
	private static final String PERSISTENCE_UNIT_NAME = "AMOS_JPA";
	private static EntityManagerFactory factory;
	private static EntityManager em;

	private static ClientDAO instance;

	public static ClientDAO getClientDAOInstance() {
		if (instance == null) {
			instance = new ClientDAO();
		}
		return instance;
	}

	private ClientDAO() {
		super();
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();
	}

	@SuppressWarnings("unchecked")
	public List<Client> getAllClients() {
		em = factory.createEntityManager();
		Query q = em.createQuery("select c from Client c ORDER BY c.ID");
		List<Client> clients = q.getResultList();
		em.close();
		return clients;

	}

	@SuppressWarnings("unchecked")
	public List<Client> getAllFilteredClients(SearchParameters search) {
		if (search == null || search.getSearch_text() == ""
				|| search.getSearch_text().equalsIgnoreCase("%")) {
			return this.getAllClients();
		}
		em = factory.createEntityManager();
		Query q = null;
		HashMap<String, Client> resultlist = new HashMap<String, Client>();

		switch (search.getSearch_cat()) {
		case 1:
			try {
				q = em.createQuery("SELECT c FROM Client c WHERE c.ID = :param  ORDER BY c.ID");
				q.setParameter("param", new Integer(search.getSearch_text()));
				addClientToMap(resultlist, q.getResultList());
			} catch (Exception e) {

			}
			break;
		case 2:
			try {
				q = em.createQuery("SELECT c FROM Client c WHERE c.name = :param  ORDER BY c.ID");
				q.setParameter("param", search.getSearch_text());
				addClientToMap(resultlist, q.getResultList());
			} catch (Exception e) {

			}
			break;
		case 3:
			try {
				q = em.createQuery("SELECT c FROM Client c WHERE c.firstname = :param  ORDER BY c.ID");
				q.setParameter("param", search.getSearch_text());
				addClientToMap(resultlist, q.getResultList());
			} catch (Exception e) {

			}
			break;
		case 4:
			try {
				// em.createQuery("SELECT c FROM Client c WHERE c.birthdate = :param  ORDER BY c.ID");
				// q.setParameter("param", new Date() search.getSearch_text());
				// addClientToMap(resultlist,q.getResultList());
			} catch (Exception e) {

			}
			break;
		case 5:
			try {
				q = em.createQuery("SELECT c FROM Client c WHERE c.mail = :param  ORDER BY c.ID");
				q.setParameter("param", search.getSearch_text());
				addClientToMap(resultlist, q.getResultList());
			} catch (Exception e) {

			}
			break;
		case 6:
			try {
				q = em.createQuery("SELECT c FROM Client c WHERE c.place = :param  ORDER BY c.ID");
				q.setParameter("param", search.getSearch_text());
				addClientToMap(resultlist, q.getResultList());
			} catch (Exception e) {

			}
			break;
		case 7:
			try {
				q = em.createQuery("SELECT c FROM Client c WHERE c.zipCode = :param  ORDER BY c.ID");
				q.setParameter("param", search.getSearch_text());
				addClientToMap(resultlist, q.getResultList());
			} catch (Exception e) {

			}
			break;
		case 8:
			try {
				q = em.createQuery("SELECT c FROM Client c WHERE c.gender = :param  ORDER BY c.ID");
				q.setParameter("param", search.getSearch_text());
				addClientToMap(resultlist, q.getResultList());
			} catch (Exception e) {

			}
			break;
		
		default:
			try {
				q = em.createQuery("SELECT c FROM Client c WHERE c.ID = :param  ORDER BY c.ID");
				q.setParameter("param", new Integer(search.getSearch_text()));
				addClientToMap(resultlist, q.getResultList());
			} catch (Exception e) {

			}

			try {
				q = em.createQuery("SELECT c FROM Client c WHERE c.name = :param  ORDER BY c.ID");
				q.setParameter("param", search.getSearch_text());
				addClientToMap(resultlist, q.getResultList());
			} catch (Exception e) {

			}

			try {
				q = em.createQuery("SELECT c FROM Client c WHERE c.firstname = :param  ORDER BY c.ID");
				q.setParameter("param", search.getSearch_text());
				addClientToMap(resultlist, q.getResultList());
			} catch (Exception e) {

			}

			try {
				// em.createQuery("SELECT c FROM Client c WHERE c.birthdate = :param  ORDER BY c.ID");
				// q.setParameter("param", new Date() search.getSearch_text());
				// addClientToMap(resultlist,q.getResultList());
			} catch (Exception e) {

			}

			try {
				q = em.createQuery("SELECT c FROM Client c WHERE c.mail = :param  ORDER BY c.ID");
				q.setParameter("param", search.getSearch_text());
				addClientToMap(resultlist, q.getResultList());
			} catch (Exception e) {

			}

			try {
				q = em.createQuery("SELECT c FROM Client c WHERE c.place = :param  ORDER BY c.ID");
				q.setParameter("param", search.getSearch_text());
				addClientToMap(resultlist, q.getResultList());
			} catch (Exception e) {

			}
			try {
				q = em.createQuery("SELECT c FROM Client c WHERE c.zipCode = :param  ORDER BY c.ID");
				q.setParameter("param", search.getSearch_text());
				addClientToMap(resultlist, q.getResultList());
			} catch (Exception e) {

			}
			try {
				q = em.createQuery("SELECT c FROM Client c WHERE c.gender = :param  ORDER BY c.ID");
				q.setParameter("param", search.getSearch_text());
				addClientToMap(resultlist, q.getResultList());
			} catch (Exception e) {

			}
			break;
		}

		List<Client> clients = new ArrayList<Client>();
		clients.addAll(resultlist.values());
		em.close();
		return clients;
	}

	private void addClientToMap(HashMap<String, Client> map, List<Client> list) {
		for (Client c : list) {
			map.put("" + c.getID(), c);
		}
	}

	public Client getclient(Integer ID) {
		em = factory.createEntityManager();
		Client _client = em.find(Client.class, ID);
		em.close();
		return _client;
	}

	public boolean addClient(Client client) {
		em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(client);
		em.getTransaction().commit();
		em.close();
		return true;
	}

	public boolean updateClient(Client client) {
		// Create an entity manager
		em = factory.createEntityManager();
		// Query q = em.createQuery("SELECT p FROM Person p WHERE p.ID = :id ");
		// q.setParameter("id", client.getID());
		Client _client = em.find(Client.class, client.getID()); // (Client)
																// q.getSingleResult();
		_client.setBirthdate(client.getBirthdate());
		_client.setFirstname(client.getFirstname());
		_client.setID(client.getID());
		_client.setMail(client.getMail());
		_client.setName(client.getName());
		_client.setPlace(client.getPlace());

		em.getTransaction().begin();
		em.persist(_client);
		em.getTransaction().commit();
		em.close();
		return true;
	}

	public boolean deleteClient(Client client) {
		em = factory.createEntityManager();
		Client _client = em.find(Client.class, client.getID());
		if (_client == null || _client.getID() == 0)
			return true;
		em.getTransaction().begin();
		em.remove(_client);
		em.getTransaction().commit();
		em.close();
		return true;
	}

}
