package com.light.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.light.model.Device;
import com.light.model.Group;

@Repository
public class GroupDao extends SimpleDao<Group> implements GroupDaoInterface{

	@Override
	public List<Group> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addParentGroup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Device getById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rename() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addChildGroup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePlan() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Group> getByParentId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLeftNode() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
