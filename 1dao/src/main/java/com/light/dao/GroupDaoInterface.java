package com.light.dao;

import java.util.List;

import com.light.model.Device;
import com.light.model.Group;

public interface GroupDaoInterface extends SimpleDaoInterface<Group>{
	public List<Group> getAll();
	
	public List<Group> getGroupWithPlan();
	
	public void addParentGroup();
	
	public Device getById();
	
	public void rename();
	
	public void addChildGroup();
	
	public void updatePlan();
	
	public List<Group> getByParentId();
	
	public boolean isLeftNode();

	public int count();

	public List<Group> getList(int pageNum, int pageSize);

}
