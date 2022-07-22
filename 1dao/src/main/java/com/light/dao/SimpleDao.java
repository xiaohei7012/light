package com.light.dao;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;

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
		try {
			entityManager = JPAUtils.getEntityManger();
			entityManager.persist(entity);
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			if(entityManager!=null)
				entityManager.close();
		}
		
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub

	}

	public T getById(int id) {
		return null;

	}

}
