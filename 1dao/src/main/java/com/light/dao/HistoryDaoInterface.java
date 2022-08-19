package com.light.dao;

import java.util.List;

import com.light.model.History;

public interface HistoryDaoInterface {

	public void add();
	
	public List<History> getByDate();
	
	public void create(String imei,String l1,String l2,String l3,String l4,String l5,String l6,String fan,double temp);
}
