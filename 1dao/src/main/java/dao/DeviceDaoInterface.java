package dao;

import java.util.List;

import model.Device;

public interface DeviceDaoInterface {
	public List<Device> getAll();
	
	public Device getById();
	
	//����
	public void reset();
	
	public void add();
	
	public void remove();
	
	//����
	public void updateGroup();
	
	public List<Device> getByGroupId();
	
	//ʹ��ʱ�����
	public void expireIncrement();
	
}
