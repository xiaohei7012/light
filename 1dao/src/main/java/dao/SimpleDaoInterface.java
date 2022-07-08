package dao;

public interface SimpleDaoInterface<T> {

	// 创建
	public void create(T entity);

	// 更新
	public void update(T entity);

	// 删除
	public void delete(T entity);
}
