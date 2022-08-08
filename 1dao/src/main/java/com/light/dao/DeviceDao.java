package com.light.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.light.model.Device;
import com.light.util.JPAUtils;

@Repository
public class DeviceDao extends SimpleDao<Device> implements DeviceDaoInterface {

	@Override
	public List<Device> getAll() throws Exception {
		EntityManager entityManager = null;
		List<Device> result = new ArrayList<Device>();
		try {
			entityManager = JPAUtils.getEntityManger();
			result = entityManager.createQuery("from Device", Device.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (entityManager != null)
				entityManager.close();
		}
		return result;
	}

	@Override
	public int countOffline() {
		EntityManager entityManager = null;
		try {
			entityManager = JPAUtils.getEntityManger();
			int result = Integer.parseInt(entityManager.createNativeQuery("select count(*) from device where status = 'OFFLINE' ").getSingleResult().toString());
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
	public List<Device> getOffline(int pageNum, int pageSize) {
		EntityManager entityManager = null;
		List<Device> result = new ArrayList<Device>();
		try {
			entityManager = JPAUtils.getEntityManger();
			result = entityManager.createQuery("from Device where status = 'OFFLINE' ", Device.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
//			throw e;
		} finally {
			if (entityManager != null)
				entityManager.close();
		}
		return result;
	}
	
	@Override
	public int countOnline() {
		EntityManager entityManager = null;
		try {
			entityManager = JPAUtils.getEntityManger();
			int result = Integer.parseInt(entityManager.createQuery("from Device where status = 'OFFLINE' ", Device.class).getResultList().toString());
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
	public List<Device> getOnline(int pageNum, int pageSize) {
		EntityManager entityManager = null;
		List<Device> result = new ArrayList<Device>();
		try {
			entityManager = JPAUtils.getEntityManger();
			result = entityManager.createQuery("from Device where status = 'ONLINE'", Device.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
//			throw e;
		} finally {
			if (entityManager != null)
				entityManager.close();
		}
		return result;
	}
	
	@Override
	public Device getById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateGroup() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Device> getByGroupId(int gid) {
		EntityManager entityManager = null;
		List<Device> result = new ArrayList<Device>();
		try {
			entityManager = JPAUtils.getEntityManger();
			result = entityManager.createQuery("from Device where groupId = :gid", Device.class).setParameter("gid", gid).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
//			throw e;
		} finally {
			if (entityManager != null)
				entityManager.close();
		}
		return result;
	}

	@Override
	public void expireIncrement() {
		// TODO Auto-generated method stub

	}

}
