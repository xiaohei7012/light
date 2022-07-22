package dao;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;

import util.JPAUtils;

public abstract class SimpleDao<T> implements SimpleDaoInterface<T> {
	
	Class<T> clasz;

	@SuppressWarnings("unchecked")
	SimpleDao() {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		clasz = (Class<T>) type.getActualTypeArguments()[0];
	}

	@Override
	public void create(T entity) {
		EntityManager entityManager = JPAUtils.getEntityManger();
		try {
			entityManager.persist(entity);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
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
