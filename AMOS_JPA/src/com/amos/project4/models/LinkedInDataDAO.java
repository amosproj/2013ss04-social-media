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

import com.amos.project4.socialMedia.LinkedIn.LinkedInDataType;

public class LinkedInDataDAO {
	
	private static final String PERSISTENCE_UNIT_NAME = "AMOS_JPA";
	private static EntityManagerFactory factory;
	private static EntityManager em;

	private static LinkedInDataDAO instance;

	public static LinkedInDataDAO getInstance() {
		if (instance == null) {
			instance = new LinkedInDataDAO();
		}
		return instance;
	}
	
	private LinkedInDataDAO() {
		super();
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();
	}
	
	public synchronized LinkedInData getLinkedInData(Integer data_id){
		if(data_id == null || data_id == 0) return null;
		LinkedInData rslt = em.find(LinkedInData.class, data_id);
		return rslt;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized List<LinkedInData> getAllLinkedInDataOfClient(Integer owner_id){
		if(owner_id == null || owner_id == 0) return new ArrayList<LinkedInData>();
		Client owner = em.find(Client.class, owner_id);
		Query q = em.createQuery("SELECT d FROM LinkedInData d WHERE d.owner = :paramid ORDER BY d.ID");
		q.setParameter("paramid", owner);
		List<LinkedInData> rslt = q.getResultList();
		return rslt;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized List<LinkedInData> getAllLinkedInDataOfClientByType(Integer owner_id, LinkedInDataType type){
		if(owner_id == null || owner_id == 0) return new ArrayList<LinkedInData>();
		Client owner = em.find(Client.class, owner_id);
		Query q = em.createQuery("SELECT d FROM LinkedInData d WHERE d.owner = :paramid and d.type = :paramtype ORDER BY d.ID");
		q.setParameter("paramid", owner);
		q.setParameter("paramtype", type.toString());
		List<LinkedInData> rslt = q.getResultList();
		return rslt;
	}
	
	public synchronized boolean persistLinkedInData(LinkedInData data) {
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
	
	public synchronized boolean updateLinkedInData(LinkedInData data){
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
	
	public synchronized boolean deleteLinkedInData(LinkedInData data){
		if(data == null) return false;
		try{
			em.getTransaction().begin();
			LinkedInData tmp = em.find(LinkedInData.class,data.getID() );
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
	
	public synchronized boolean deleteLinkedInDatas(Client client,LinkedInDataType type){
		if(client == null  || type == null) return false;
		try{
			List<LinkedInData> datas = this.getAllLinkedInDataOfClientByType(client.getID(), type);
			for(LinkedInData data : datas){
				this.deleteLinkedInData(data);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{

		}
	}
	
	@SuppressWarnings("unchecked")
	public synchronized boolean deleteAllLinkedInData(Integer owner_id){
		if(owner_id == null || owner_id == 0) return false;
		Client owner = em.find(Client.class, owner_id);
		Query q = em.createQuery("SELECT d FROM LinkedInData d WHERE d.owner = :paramid ORDER BY d.ID");
		q.setParameter("paramid", owner);
		List<LinkedInData> rslt = q.getResultList();
		for (LinkedInData data : rslt) {
			if(! deleteLinkedInData(data)) return false;
		}
		return true;
	}
}
