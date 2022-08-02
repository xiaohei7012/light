package com.light.dao;

import com.light.model.Plan;

public interface PlanDaoInterface extends SimpleDaoInterface<Plan>{
	
	public void add();
	
	public void rename();
	
	public void updateExpretion();
}
