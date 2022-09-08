package com.light.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.light.model.Device;
import com.light.model.Group;
import com.light.util.JPAUtils;

@Repository
public class GroupDao extends SimpleDao<Group> implements GroupDaoInterface{

	@Override
	public List<Map<String,Object>> getList(int pageNum, int pageSize) {
		EntityManager entityManager = null;
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		try {
			entityManager = JPAUtils.getEntityManger();
			List<?> glist = entityManager.createNativeQuery("select g.gname,(select count(*) from device d where d.groupId = g.id) as dcount,p.pname,g.createTime,g.id from dgroup g left join plan p on g.planId = p.id").getResultList();
			for(Object o: glist) {
				Map<String,Object> map = new HashMap<String,Object>();
				Object[] oarray = (Object[])o;
				map.put("gname", oarray[0]);
				map.put("dcount", oarray[1]);
				map.put("pname", oarray[2]);
				map.put("createTime", oarray[3]);
				map.put("id", oarray[4]);
				result.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (entityManager != null)
				entityManager.close();
		}
		return result;
	}
	
	@Override
	public List<Group> getAll() {
		EntityManager entityManager = null;
		List<Group> result = new ArrayList<Group>();
		try {
			entityManager = JPAUtils.getEntityManger();
			result = entityManager.createQuery("from Group", Group.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (entityManager != null)
				entityManager.close();
		}
		return result;
	}
	
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
	public List<Group> getGroupByPid(int pid) {
		EntityManager entityManager = null;
		List<Group> result = new ArrayList<Group>();
		try {
			entityManager = JPAUtils.getEntityManger();
			result = entityManager.createQuery("from Group where planId = " + pid, Group.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (entityManager != null)
				entityManager.close();
		}
		return result;
	}
	
	@Override
	public int count() {
		EntityManager entityManager = null;
		try {
			entityManager = JPAUtils.getEntityManger();
			int result = Integer.parseInt(entityManager.createNativeQuery("select count(*) from dgroup ").getSingleResult().toString());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (entityManager != null)
				entityManager.close();
		}
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
