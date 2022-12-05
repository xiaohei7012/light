package com.light.dao;

import java.util.List;

import com.light.model.History;

public interface HistoryDaoInterface extends SimpleDaoInterface<History>{

	public void add();
	
	//专门用来检测在线 大于0就不重置离线
	public int getByDidInMinutes(String imei,int period);

	List<History> getByImei(String imei);
	
//	public void create(String imei,String l1,String l2,String l3,String l4,String l5,String l6,String fan,double temp);
}
