package com.light.dao;

import java.util.List;

import com.light.model.Device;
import com.light.model.Group;

public interface GroupDaoInterface {
	public List<Group> getAll();
	
	//��Ӷ���Ŀ¼
	public void addParentGroup();
	
	public Device getById();
	
	//������
	public void rename();
	
	//�����Ŀ¼
	public void addChildGroup();
	
	//�޸ķ���
	public void updatePlan();
	
	//��ȡ�ӽڵ�
	public List<Group> getByParentId();
	
	//�Ƿ�Ҷ�ӽڵ�
	public boolean isLeftNode();
}
