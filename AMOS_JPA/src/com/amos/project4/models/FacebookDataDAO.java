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
	
	public FacebookData getFacebookData(Integer data_id){
		if(data_id == null || data_id == 0) return null;
		em = factory.createEntityManager();
		FacebookData rslt = em.find(FacebookData.class, data_id);
		em.close();
		return rslt;
	}
	
	public List<FacebookData> getAllFacebookDataOfClient(Integer client_id){
		if(client_id == null || client_id == 0) return new ArrayList<FacebookData>();
		em = factory.createEntityManager();
		Client owner = em.find(Client.class, client_id);	
		em.close();
		return owner.getFacebook_datas();
	}
	
	@SuppressWarnings("unchecked")
	public List<FacebookData> getAllFacebookDataOfClientBytype(Integer owner_id, FacebookDataType type){
		em = factory.createEntityManager();
		Query q = em.createQuery("SELECT d FROM FacebookData d WHERE d.ClientID = :paramid and d.type = :paramtype  ORDER BY d.ID");
		q.setParameter("paramid", owner_id);
		q.setParameter("paramtype", type.toString());
		List<FacebookData> rslt = q.getResultList();
		em.close();
		return rslt;
	}
	
	public boolean persistFacebookData(FacebookData data) {
		if(data == null) return false;
		try {
			em = factory.createEntityManager();
			em.getTransaction().begin();
			em.persist(data);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
		
		
	}
	
	public boolean updateFacebookData(FacebookData data){
		if(data == null) return false;
		try{
			em = factory.createEntityManager();
			em.getTransaction().begin();
			em.persist(data);
			em.getTransaction().commit();
			return true;
		}catch(Exception e){
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
	}
	
	public boolean deleteFacebookData(FacebookData data){
		if(data == null) return false;
		try{
			em = factory.createEntityManager();
			em.getTransaction().begin();
			Client owner = data.getOwner();
			if(owner != null){
				owner.getFacebook_datas().remove(data);
				em.persist(owner);
			}
			em.remove(data);
			em.getTransaction().commit();
			return true;
		}catch(Exception e){
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
	}
	
	public boolean deleteAllFacebookData(Integer owner_id){
		if(owner_id == null || owner_id == 0) return false;
		Client owner = em.find(Client.class, owner_id);
		for (FacebookData data : owner.getFacebook_datas()) {
			if(! deleteFacebookData(data)) return false;
		}
		return true;
	}
}
