package com.light.dao;

import java.util.List;

import com.light.model.Device;

public interface DeviceDaoInterface extends SimpleDaoInterface<Device>{
	public List<Device> getAll() throws Exception;
	
	public Device getById();
	
	public void reset();
	
	public void remove();
	
	public void updateGroup();
	
	public List<Device> getByGroupId();
	
	public void expireIncrement();
	
}
