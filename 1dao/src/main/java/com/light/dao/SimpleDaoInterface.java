package com.light.dao;

public interface SimpleDaoInterface<T> {

	public void create(T entity) throws Exception;

	public void update(T entity);

	public void delete(T entity);
	
	public T getById(int id);
}
