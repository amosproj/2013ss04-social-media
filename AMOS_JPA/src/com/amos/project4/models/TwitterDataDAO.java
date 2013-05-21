package com.amos.project4.models;

import java.util.ArrayList;
import java.util.Collection;
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

	public static TwitterDataDAO getTwitterDataDAOInstance() {
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
	
	public TwitterData getTwitterData(Integer data_id){
		if(data_id == null || data_id == 0) return null;
		em = factory.createEntityManager();
		TwitterData rslt = em.find(TwitterData.class, data_id);
		em.close();
		return rslt;
	}
	
	public Collection<TwitterData> getAllTwitterDataOfUser(Integer user_id){
		if(user_id == null || user_id == 0) return new ArrayList<TwitterData>();
		em = factory.createEntityManager();
		User owner = em.find(User.class, user_id);	
		em.close();
		return owner.getTwitter_datas();
	}
	
	public Collection<TwitterData> getAllTwitterDataOfUserBytype(Integer user_id, TwitterDataType type){
		em = factory.createEntityManager();
		Query q = em.createQuery("SELECT d FROM TwitterData d WHERE d.UserID = :paramid and d.type = :paramtype  ORDER BY d.ID");
		q.setParameter("paramid", user_id);
		q.setParameter("paramtype", type.toString());
		Collection rslt = q.getResultList();
		em.close();
		return rslt;
	}
	
	public boolean persistTwitterData(TwitterData data) {
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
	
	public boolean updateTwitterData(TwitterData data){
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
	
	public boolean deleteTwitterData(TwitterData data){
		if(data == null) return false;
		try{
			em = factory.createEntityManager();
			em.getTransaction().begin();
			User owner = data.getOwner();
			if(owner != null){
				owner.getTwitter_datas().remove(data);
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
	
	public boolean deleteAllTwitterData(Integer user_id){
		if(user_id == null || user_id == 0) return false;
		User owner = em.find(User.class, user_id);
		for (TwitterData data : owner.getTwitter_datas()) {
			if(! deleteTwitterData(data)) return false;
		}
		return true;
	}
}
