package com.light.dao;

import java.util.List;

import com.light.model.Device;

public interface DeviceDaoInterface extends SimpleDaoInterface<Device>{
	public List<Device> getAll() throws Exception;
	
	public int countOffline();
	
	public List<Device> getOffline(int pageNum, int pageSize);
	
	public int countOnline();
	
	public List<Device> getOnline(int pageNum, int pageSize);
	
	public Device getByImei(String imei);
	
	public void setCoord(String imei,double lon,double lat);
	
	public void setStatus(String imei,String l1,String l2,String l3,String l4,String l5,String l6,String fan,double temp);
	
	public void reset();
	
	public void remove();
	
	public void updateGroup();
	
	public List<Device> getByGroupId(int gid);
	
	public void expireIncrement();

	public void turnOn(Device d);
	
	public void turnOff(Device d);

	
}
