package com.light.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.light.util.JPAUtils;

public abstract class SimpleDao<T> implements SimpleDaoInterface<T> {

	Class<T> clasz;

	@SuppressWarnings("unchecked")
	SimpleDao() {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		clasz = (Class<T>) type.getActualTypeArguments()[0];
	}

	@Override
	public void create(T entity) throws Exception{
		EntityManager entityManager = null;
		EntityTransaction tra = null;
		try {
			entityManager = JPAUtils.getEntityManger();
			tra = entityManager.getTransaction();
			tra.begin();
			entityManager.persist(entity);
			tra.commit();
		}catch(Exception e) {
			e.printStackTrace();
			tra.rollback();
			throw e;
		}finally {
			if(entityManager!=null)
				entityManager.close();
		}
		
	}

	@Override
	public void update(T entity) {
		EntityManager entityManager = null;
		EntityTransaction tra = null;
		try {
			entityManager = JPAUtils.getEntityManger();
			tra = entityManager.getTransaction();
			tra.begin();
			entityManager.merge(entity);
			tra.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tra.rollback();
		} finally {
			if(entityManager!=null)
				entityManager.close();
		}
	}

	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub

	}

	public T getById(int id) {
		EntityManager entityManager = JPAUtils.getEntityManger();
		try {
			List<T> list = entityManager.createQuery("from " + clasz.getSimpleName() + " where id = :id", clasz).setParameter("id", id).getResultList();
			if (list.size() == 0)
				return null;
			return list.get(0);
		} catch (Exception e) {

		} finally {
			entityManager.close();
		}
		return null;
	}

}
