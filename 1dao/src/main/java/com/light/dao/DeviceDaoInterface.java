package com.light.dao;

import java.util.List;
import java.util.Map;

import com.light.model.Device;
import com.light.model.Plan;

public interface DeviceDaoInterface extends SimpleDaoInterface<Device>{
	public List<Device> getAll() throws Exception;
	
	public int countOffline();
	
	public List<Map<String, Object>> getOffline(int pageNum, int pageSize);
	
	public int countOnline();
	
	public List<Map<String, Object>> getOnline(int pageNum, int pageSize);
	
	public Device getByImei(String imei);
	
	public void setOnline(String imei);
	
	public void setOnline(String imei,double lon,double lat);
	
	public void setFanRpm(String imei,double rpm);
	
	public void setOffline(String imei);
	
	public void setStatus(String imei,String l1,String l2,String l3,String l4,String l5,String l6,String fan,Double temp);
	
	public void reset();
	
	public void remove();
	
	public void updateGroup(Device device,Integer gid);
	
	public void updateGroup(int did,Integer gid);// 为设备设定分组 gid为空的时候就是清空分组信息
	
	public List<Device> getByGroupId(int gid);
	
	public void expireIncrement();

	public void turnOn(Device d);
	
	public void turnOff(Device d);

	public void setPlan(String imei,Plan plan);

	public void setPlan(Device device, Plan plan);

	public void increaseUseTime(String imei,String l1,String l2,String l3,String l4,String l5,String l6);

	public Device create(String imei) throws Exception;
}
