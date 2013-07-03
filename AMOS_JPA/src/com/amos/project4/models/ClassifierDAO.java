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

/**
 * 
 * @author jupiter BAKAKEU
 * 
 */
public class ClassifierDAO {
	private static final String PERSISTENCE_UNIT_NAME = "AMOS_JPA";
	private static EntityManagerFactory factory;
	private static EntityManager em;

	private static ClassifierDAO instance;

	public static ClassifierDAO getInstance() {
		if (instance == null) {
			instance = new ClassifierDAO();
		}
		return instance;
	}

	private ClassifierDAO() {
		super();
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();
	}

	@SuppressWarnings("unchecked")
	public synchronized List<ClassifierData> getAllClassifierDatas() {
		em = factory.createEntityManager();
		Query q = em.createQuery("select c from ClassifierData c ORDER BY c.ID");
		List<ClassifierData> classes = q.getResultList();
		em.close();
		return classes;

	}
	
	@SuppressWarnings("unchecked")
	public synchronized List<ClassifierData> getAllClassifierDatas(String name) {
		em = factory.createEntityManager();
		Query q = em.createQuery("select c from ClassifierData c ORDER WHERE c.name =:paramName BY c.ID");
		q.setParameter("paramName", name);
		List<ClassifierData> classes = q.getResultList();
		em.close();
		return classes;

	}

	public synchronized boolean addClassifierData(ClassifierData data) {
		em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(data);
		em.getTransaction().commit();
		em.close();
		return true;
	}

	public synchronized boolean updateClassifierData(ClassifierData data) {
		// Create an entity manager
		em = factory.createEntityManager();
		ClassifierData _data = em.find(ClassifierData.class, data.getID()); 
		_data.setID(data.getID());
		_data.setClassifier(data.getClassifier());
		_data.setName(_data.getName());

		em.getTransaction().begin();
		em.persist(_data);
		em.getTransaction().commit();
		em.close();
		return true;
	}

	public synchronized boolean deleteClassifierData(ClassifierData data) {
		em = factory.createEntityManager();
		ClassifierData _data = em.find(ClassifierData.class, data.getID());
		if (_data == null || _data.getID() == 0)
			return true;
		em.getTransaction().begin();
		em.remove(_data);
		em.getTransaction().commit();
		em.close();
		return true;
	}
}
