package dao;

public interface SimpleDaoInterface<T> {

	public void create(T entity);

	public void update(T entity);

	public void delete(T entity);
}
