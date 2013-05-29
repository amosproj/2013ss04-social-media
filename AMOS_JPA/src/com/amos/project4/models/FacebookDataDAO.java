package com.amos.project4.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.amos.project4.socialMedia.facebook.FacebookDataType;


public class FacebookDataDAO {
	
	private static final String PERSISTENCE_UNIT_NAME = "AMOS_JPA";
	private static EntityManagerFactory factory;
	private static EntityManager em;

	private static FacebookDataDAO instance;

	public static FacebookDataDAO getInstance() {
		if (instance == null) {
			instance = new FacebookDataDAO();
		}
		return instance;
	}
	
	private FacebookDataDAO() {
		super();
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();
	}
	
	public synchronized FacebookData getFacebookData(Integer data_id){
		if(data_id == null || data_id == 0) return null;
		FacebookData rslt = em.find(FacebookData.class, data_id);
		return rslt;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized List<FacebookData> getAllFacebookDataOfClient(Integer client_id){
		if(client_id == null || client_id == 0) return new ArrayList<FacebookData>();

		Client owner = em.find(Client.class, client_id);
		Query q = em.createQuery("SELECT d FROM FacebookData d WHERE d.owner = :paramid  ORDER BY d.ID");
		q.setParameter("paramid", owner);
		List<FacebookData> rslt = q.getResultList();
		return rslt;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized List<FacebookData> getAllFacebookDataOfClientByType(Integer owner_id, FacebookDataType type){
		Client owner = em.find(Client.class, owner_id);
		Query q = em.createQuery("SELECT d FROM FacebookData d WHERE d.owner = :paramid and d.type = :paramtype  ORDER BY d.ID");
		q.setParameter("paramid", owner);
		q.setParameter("paramtype", type.toString());
		List<FacebookData> rslt = q.getResultList();
		return rslt;
	}
	
	public synchronized boolean persistFacebookData(FacebookData data) {
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
	
	public synchronized boolean updateFacebookData(FacebookData data){
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
	
	public synchronized boolean deleteFacebookData(FacebookData data){
		if(data == null) return false;
		try{
			em.getTransaction().begin();
			
			FacebookData tmp = em.find(FacebookData.class,data.getID() );
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
	
	public synchronized boolean deleteFacebookDatas(Client client,FacebookDataType type){
		if(client == null  || type == null) return false;
		try{
			List<FacebookData> datas = this.getAllFacebookDataOfClientByType(client.getID(), type);
			for(FacebookData data : datas){
				this.deleteFacebookData(data);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{

		}
	}
	
	
	@SuppressWarnings("unchecked")
	public synchronized boolean deleteAllFacebookData(Integer owner_id){
		if(owner_id == null || owner_id == 0) return false;
		Client owner = em.find(Client.class, owner_id);
		Query q = em.createQuery("SELECT d FROM FacebookData d WHERE d.owner = :paramid  ORDER BY d.ID");
		q.setParameter("paramid", owner);
		List<FacebookData> rslt = q.getResultList();
		for (FacebookData data : rslt) {
			if(! deleteFacebookData(data)) return false;
		}
		return true;
	}
}
