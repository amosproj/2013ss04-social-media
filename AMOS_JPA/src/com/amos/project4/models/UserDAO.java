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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class UserDAO {
	private static final String PERSISTENCE_UNIT_NAME = "AMOS_JPA";
	private static EntityManagerFactory factory;
	private static EntityManager em;

	private static UserDAO instance;

	public static UserDAO getUserDAOInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}
	
	private UserDAO() {
		super();
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}
	
	@SuppressWarnings("unchecked")
	public synchronized List<User> getAllUsers() {
		em = factory.createEntityManager();
		Query q = em.createQuery("select u from User u ORDER BY u.ID");		
		List<User> users = q.getResultList();
		em.close();
		return users;

	}
	
	/**
	 * Get the user object from the database by specifying its ID
	 * @param ID of the User
	 * @return
	 */
	public synchronized User getUserByID(Integer ID) {
		if(ID == 0) return null;
		em = factory.createEntityManager();
		User _user = em.find(User.class, ID);
		em.close();
		return _user;
	}
	
	/**
	 * Get a user by specifying his username
	 * @param username
	 * @return
	 */
	public synchronized User getUserByUsername(String username) {
		if(username == null || username.isEmpty()) return null;
		em = factory.createEntityManager();
		Query q = em.createQuery("SELECT u FROM User u WHERE u.username = :param  ORDER BY u.ID");
		q.setParameter("param", username);
		User _user = null;
		if(q.getResultList().size() > 0){
			_user = (User) q.getSingleResult();
		}
		em.close();
		return _user;
	}
	
	/**
	 * Get a user by specifying his mail address
	 * @param usermail
	 * @return
	 */	
	public synchronized User getUserByMail(String usermail) {
		if(usermail == null || usermail.isEmpty()) return null;
		em = factory.createEntityManager();
		Query q = em.createQuery("SELECT u FROM User u WHERE u.usermail = :param  ORDER BY u.ID");
		q.setParameter("param", usermail);
		User _user = null;
		if(q.getResultList().size() > 0){
			_user = (User) q.getSingleResult();
		}
		em.close();
		return _user;
	}
	
	/**
	 * Add/persist a user object in the Database
	 * @param user
	 * @return
	 */
	public synchronized boolean addUser(User user){
		if(user == null ){
			return false;
		}
		em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
		return true;
	}
	
	/**
	 * Update a specific user data in the Database
	 * @param user
	 * @return
	 */
	public synchronized boolean updateUser(User user){
		if(user == null || user.getID() == 0){
			return false;
		}
		em = factory.createEntityManager();
		em.getTransaction().begin();
		User _user = em.find(User.class, user.getID());
		//Update the user object
		_user.setUsername(user.getUsername());
		_user.setUserpass(user.getUserpass());
		_user.setUsermail(user.getUsermail());
		// Facebook
		_user.setF_token(user.getF_token());
		_user.setF_token_secret(user.getF_token_secret());
		// Twitter
		_user.setT_token(user.getT_token());
		_user.setT_token_secret(user.getT_token_secret());
		// linkedIn
		_user.setL_token(user.getL_token());
		_user.setL_token_secret(user.getL_token_secret());
		// Xing
		_user.setX_token(user.getX_token());
		_user.setX_token_secret(user.getX_token_secret());	
		
		_user.setT_token_secret(user.getT_token_secret());
		_user.setT_token(user.getT_token());
		
		
		em.persist(_user);
		em.getTransaction().commit();
		em.close();
		return true;
	}
	
	
	/**
	 * delete a specific user from the database
	 * @param user
	 * @return
	 */
	public synchronized boolean deleteUser(User user){
		if(user == null || user.getID() == 0){
			return false;
		}
		em = factory.createEntityManager();
		em.getTransaction().begin();
		User _user = em.find(User.class, user.getID());
		em.remove(_user);
		em.getTransaction().commit();
		em.clear();
		em.close();
		return true;
	}
	

}
