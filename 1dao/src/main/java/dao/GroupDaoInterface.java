package dao;

import java.util.List;

import model.Device;
import model.Group;

public interface GroupDaoInterface {
	public List<Group> getAll();
	
	//添加顶级目录
	public void addParentGroup();
	
	public Device getById();
	
	//重命名
	public void rename();
	
	//添加子目录
	public void addChildGroup();
	
	//修改方案
	public void updatePlan();
	
	//获取子节点
	public List<Group> getByParentId();
	
	//是否叶子节点
	public boolean isLeftNode();
}
