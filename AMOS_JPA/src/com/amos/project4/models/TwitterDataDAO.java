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
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.amos.project4.socialMedia.twitter.TwitterDataType;

public class TwitterDataDAO {
	
	private static final String PERSISTENCE_UNIT_NAME = "AMOS_JPA";
	private static EntityManagerFactory factory;
	private static EntityManager em;

	private static TwitterDataDAO instance;

	public static TwitterDataDAO getInstance() {
		if (instance == null) {
			instance = new TwitterDataDAO();
		}
		return instance;
	}
	
	private TwitterDataDAO() {
		super();
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();
	}
	
	public synchronized TwitterData getTwitterData(Integer data_id){
		if(data_id == null || data_id == 0) return null;
		TwitterData rslt = em.find(TwitterData.class, data_id);
		return rslt;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized List<TwitterData> getAllTwitterDataOfClient(Integer owner_id){
		if(owner_id == null || owner_id == 0) return new ArrayList<TwitterData>();
		Client owner = em.find(Client.class, owner_id);
		Query q = em.createQuery("SELECT d FROM TwitterData d WHERE d.owner = :paramid ORDER BY d.ID");
		q.setParameter("paramid", owner);
		List<TwitterData> rslt = q.getResultList();
		return rslt;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized List<TwitterData> getAllTwitterDataOfClientByType(Integer owner_id, TwitterDataType type){
		if(owner_id == null || owner_id == 0) return new ArrayList<TwitterData>();
		Client owner = em.find(Client.class, owner_id);
		Query q = em.createQuery("SELECT d FROM TwitterData d WHERE d.owner = :paramid and d.type = :paramtype ORDER BY d.ID");
		q.setParameter("paramid", owner);
		q.setParameter("paramtype", type.toString());
		List<TwitterData> rslt = q.getResultList();
		return rslt;
	}
	
	public synchronized boolean persistTwitterData(TwitterData data) {
		if(data == null) return false;
		try {
			em.getTransaction().begin();
			
			em.persist(data);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return false;
		}finally{
		}
		
		
	}
	
	public synchronized boolean updateTwitterData(TwitterData data){
		if(data == null) return false;
		try{
			em.getTransaction().begin();
			em.persist(data);
			
			em.getTransaction().commit();
			return true;
		}catch(Exception e){
			em.getTransaction().rollback();
			return false;
		}finally{
		}
	}
	
	public synchronized boolean deleteTwitterData(TwitterData data){
		if(data == null) return false;
		try{
			em.getTransaction().begin();
			TwitterData tmp = em.find(TwitterData.class,data.getID() );
			em.merge(tmp);
			em.remove(tmp);
			em.getTransaction().commit();
			return true;
		}catch(Exception e){
			em.getTransaction().rollback();
			return false;
		}finally{
		}
	}
	
	public synchronized boolean deleteTwitterDatas(Client client,TwitterDataType type){
		if(client == null  || type == null) return false;
		try{
			List<TwitterData> datas = this.getAllTwitterDataOfClientByType(client.getID(), type);
			for(TwitterData data : datas){
				this.deleteTwitterData(data);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{

		}
		
//		if(client == null  || type == null) return false;
//		try{
//			em.getTransaction().begin();
//			Client owner = em.find(Client.class, client.getID());
//			Query q = em.createQuery("DELETE  FROM TwitterData d WHERE d.owner = :paramid and d.type = :paramtype");
//			q.setParameter("paramid", owner);
//			q.setParameter("paramtype", type.toString());
//			int deleted = q.executeUpdate();
//			em.getTransaction().commit();
//			return true;
//		}catch(Exception e){
//			em.getTransaction().rollback();
//			return false;
//		}finally{
//
//		}
	}
	
	@SuppressWarnings("unchecked")
	public synchronized boolean deleteAllTwitterData(Integer owner_id){
		if(owner_id == null || owner_id == 0) return false;
		Client owner = em.find(Client.class, owner_id);
		Query q = em.createQuery("SELECT d FROM TwitterData d WHERE d.owner = :paramid ORDER BY d.ID");
		q.setParameter("paramid", owner);
		List<TwitterData> rslt = q.getResultList();
		for (TwitterData data : rslt) {
			if(! deleteTwitterData(data)) return false;
		}
		return true;
	}
}
