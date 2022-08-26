package com.light.dao;

import java.util.List;

import com.light.model.Plan;

public interface PlanDaoInterface extends SimpleDaoInterface<Plan>{
	
	public List<Plan> getAll();
	
	public void add();
	
	public void rename();
	
	public void updateExpretion();

	public int count();

	public List<Plan> getList(int pageNum, int pageSize);
}
