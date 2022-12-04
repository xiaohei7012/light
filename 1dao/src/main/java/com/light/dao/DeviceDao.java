package com.light.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.light.model.Device;
import com.light.model.Plan;
import com.light.util.JPAUtils;

@Repository
public class DeviceDao extends SimpleDao<Device> implements DeviceDaoInterface {

	public static enum LIGHT_TYPE {
		ON, OFF, BAD
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
			int result = Integer.parseInt(entityManager
					.createNativeQuery("select count(*) from device where status = '" + LIGHT_TYPE.OFF + "' ")
					.getSingleResult().toString());
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
	public List<Map<String, Object>> getOffline(int pageNum, int pageSize) {
		EntityManager entityManager = null;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			entityManager = JPAUtils.getEntityManger();
			List<?> dlist = entityManager.createNativeQuery(
					"select d.dname,d.imei,d.status,g.gname,d.id from device d left join dgroup g on g.id = d.groupId where status = '"
							+ LIGHT_TYPE.OFF + "' ")
					.getResultList();
			for (Object o : dlist) {
				Map<String, Object> map = new HashMap<String, Object>();
				Object[] oarray = (Object[]) o;
				map.put("dname", oarray[0]);
				map.put("imei", oarray[1]);
				map.put("status", oarray[2]);
				map.put("gname", oarray[3]);
				map.put("id", oarray[4]);
				result.add(map);
			}
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
			int result = Integer.parseInt(entityManager
					.createNativeQuery("select count(*) from device")
					.getSingleResult().toString());
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
	public List<Map<String, Object>> getOnline(int pageNum, int pageSize) {
		EntityManager entityManager = null;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			entityManager = JPAUtils.getEntityManger();
			List<?> dlist = entityManager.createNativeQuery(
					"select d.dname,d.imei,d.status,g.gname,d.id from device d left join dgroup g on g.id = d.groupId")
					.getResultList();
			for (Object o : dlist) {
				Map<String, Object> map = new HashMap<String, Object>();
				Object[] oarray = (Object[]) o;
				map.put("dname", oarray[0]);
				map.put("imei", oarray[1]);
				map.put("status", oarray[2]);
				map.put("gname", oarray[3]);
				map.put("id", oarray[4]);
				result.add(map);
			}
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
		EntityManager entityManager = null;
		List<Device> list = new ArrayList<Device>();
		try {
			entityManager = JPAUtils.getEntityManger();
			list = entityManager.createQuery("from Device where imei = '" + imei + "'", Device.class).getResultList();
			if (list.size() >= 1) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
//			throw e;
		} finally {
			if (entityManager != null)
				entityManager.close();
		}
		return null;
	}

	public void setOnline(String imei) {
		Device device = getByImei(imei);
		if (device == null)
			return;
		device.setStatus(LIGHT_TYPE.ON.toString());
		update(device);
	}

	public void setOnline(String imei, double lon, double lat) {
		Device device = getByImei(imei);
		if (device == null)
			return;
		device.setLongitude(lon);
		device.setLatitude(lat);
		device.setStatus(LIGHT_TYPE.ON.toString());
		device.setLastupdateTime(new Date());
		update(device);
	}

	@Override
	public void setOffline(String imei) {
		Device device = getByImei(imei);
		if (device == null)
			return;
		device.setStatus(LIGHT_TYPE.OFF.toString());
		update(device);
	}

	public void setStatus(String imei, String l1, String l2, String l3, String l4, String l5, String l6, String fan,
			Double temp) {
		Device device = getByImei(imei);
		if (device == null)
			return;
		device.setImei(imei.toUpperCase());
		device.setL1(l1.toUpperCase());
		device.setL2(l2.toUpperCase());
		device.setL3(l3.toUpperCase());
		device.setL4(l4.toUpperCase());
		device.setL5(l5.toUpperCase());
		device.setL6(l6.toUpperCase());
		device.setFan(fan.toUpperCase());
		device.setTemperature(temp);
		device.setLastupdateTime(new Date());
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
		d.setFan("F0");
		update(d);
	}

	@Override
	public void setPlan(Device device, Plan plan) {
//		Device device = getByImei(imei);
		device.setL1(plan.getInsByIndex(0));
		device.setL2(plan.getInsByIndex(1));
		device.setL3(plan.getInsByIndex(2));
		device.setL4(plan.getInsByIndex(3));
		device.setL5(plan.getInsByIndex(4));
		device.setL6(plan.getInsByIndex(5));
		device.setFan(plan.getInsByIndex(6));
		update(device);
	}

	@Override
	public void setPlan(String imei, Plan plan) {
		Device device = getByImei(imei);
		device.setL1(plan.getInsByIndex(0));
		device.setL2(plan.getInsByIndex(1));
		device.setL3(plan.getInsByIndex(2));
		device.setL4(plan.getInsByIndex(3));
		device.setL5(plan.getInsByIndex(4));
		device.setL6(plan.getInsByIndex(5));
		device.setFan(plan.getInsByIndex(6));
		update(device);
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
	public void updateGroup(Device device, Integer gid) {
		device.setGroupId(gid);
		update(device);
	}

	@Override
	public void updateGroup(int did, Integer gid) {
		Device device = getById(did);
		device.setGroupId(gid);
		update(device);
	}

	@Override
	public List<Device> getByGroupId(int gid) {
		EntityManager entityManager = null;
		List<Device> result = new ArrayList<Device>();
		try {
			entityManager = JPAUtils.getEntityManger();
			result = entityManager.createQuery("from Device where groupId = :gid", Device.class)
					.setParameter("gid", gid).getResultList();
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

	@Override
	public void setFanRpm(String imei, double rpm) {
		Device device = getByImei(imei);
		if (device != null) {
			device.setRpm(rpm);
			device.setLastupdateTime(new Date());
			update(device);
		}
	}

	@Override
	public void increaseUseTime(String imei, String l1, String l2, String l3, String l4, String l5, String l6) {
		Device device = getByImei(imei);
		if (device != null) {
			device.setExpire(device.getExpire() + 5);
			if (l1.equalsIgnoreCase(LIGHT_TYPE.ON.toString())) {
				device.setL1usedTime(device.getL1usedTime() + 5);
			}
			if (l2.equalsIgnoreCase(LIGHT_TYPE.ON.toString())) {
				device.setL1usedTime(device.getL2usedTime() + 5);
			}
			if (l3.equalsIgnoreCase(LIGHT_TYPE.ON.toString())) {
				device.setL1usedTime(device.getL3usedTime() + 5);
			}
			if (l4.equalsIgnoreCase(LIGHT_TYPE.ON.toString())) {
				device.setL1usedTime(device.getL4usedTime() + 5);
			}
			if (l5.equalsIgnoreCase(LIGHT_TYPE.ON.toString())) {
				device.setL1usedTime(device.getL5usedTime() + 5);
			}
			if (l6.equalsIgnoreCase(LIGHT_TYPE.ON.toString())) {
				device.setL1usedTime(device.getL6usedTime() + 5);
			}
			update(device);
		}
	}

	@Override
	public Device create(String imei) throws Exception {
		Device device = new Device();
		device.setCreateTime(new Date());
		device.setDname(imei);
		device.setImei(imei);
		device.setStatus(LIGHT_TYPE.ON.toString());
		create(device);
		return device;
	}

}
