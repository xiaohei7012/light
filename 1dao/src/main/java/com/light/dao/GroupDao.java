package com.light.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.light.model.Device;
import com.light.model.Group;
import com.light.util.JPAUtils;

@Repository
public class GroupDao extends SimpleDao<Group> implements GroupDaoInterface{

	@Override
	public List<Group> getGroupWithPlan() {
		EntityManager entityManager = null;
		List<Group> result = new ArrayList<Group>();
		try {
			entityManager = JPAUtils.getEntityManger();
			result = entityManager.createQuery("from Group where planId != null", Group.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (entityManager != null)
				entityManager.close();
		}
		return result;
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
