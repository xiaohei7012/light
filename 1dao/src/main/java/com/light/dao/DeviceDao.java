package com.light.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.light.model.Device;
import com.light.util.JPAUtils;

@Repository
public class DeviceDao extends SimpleDao<Device> implements DeviceDaoInterface {
	
	public static enum LIGHT_TYPE{
		ON,OFF,BAD
	}

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
			int result = Integer.parseInt(entityManager.createNativeQuery("select count(*) from device where status = 'ONLINE'", Device.class).getResultList().toString());
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
	public Device getByImei(String imei) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setCoord(String imei,double lon,double lat) {
		Device device = getByImei(imei);
		device.setLongitude(lon);
		device.setLatitude(lat);
		update(device);
	}

	public void setStatus(String imei,String l1,String l2,String l3,String l4,String l5,String l6,String fan,double temp) {
		Device device = getByImei(imei);
		device.setImei(imei);
		device.setL1(l1);
		device.setL2(l2);
		device.setL3(l3);
		device.setL4(l4);
		device.setL5(l5);
		device.setL6(l6);
		device.setFan(fan);
		device.setTemperature(temp);
		update(device);
	}

	@Override
	public void turnOn(Device d) {
		d.setL1(LIGHT_TYPE.ON.toString());
		d.setL2(LIGHT_TYPE.ON.toString());
		d.setL3(LIGHT_TYPE.ON.toString());
		d.setL4(LIGHT_TYPE.ON.toString());
		d.setL5(LIGHT_TYPE.ON.toString());
		d.setL6(LIGHT_TYPE.ON.toString());
		d.setFan("F9");
		update(d);
	}
	
	@Override
	public void turnOff(Device d) {
		d.setL1(LIGHT_TYPE.OFF.toString());
		d.setL2(LIGHT_TYPE.OFF.toString());
		d.setL3(LIGHT_TYPE.OFF.toString());
		d.setL4(LIGHT_TYPE.OFF.toString());
		d.setL5(LIGHT_TYPE.OFF.toString());
		d.setL6(LIGHT_TYPE.OFF.toString());
		d.setFan(LIGHT_TYPE.OFF.toString());
		update(d);
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
