package dao;

import java.util.List;

import model.Device;

public interface DeviceDaoInterface {
	public List<Device> getAll();
	
	public Device getById();
	
	//重置
	public void reset();
	
	public void add();
	
	public void remove();
	
	//分组
	public void updateGroup();
	
	public List<Device> getByGroupId();
	
	//使用时间递增
	public void expireIncrement();
	
}
