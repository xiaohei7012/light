package com.light.dao;

import java.util.List;

import com.light.model.History;

public class HistoryDao extends SimpleDao<History> implements HistoryDaoInterface {

	@Override
	public void add() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<History> getByDate() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void create(String imei,String l1,String l2,String l3,String l4,String l5,String l6,String fan,double temp) {
		History history = new History();
		history.setImei(imei);
		history.setL1(l1);
		history.setL2(l2);
		history.setL3(l3);
		history.setL4(l4);
		history.setL5(l5);
		history.setL6(l6);
		history.setFan(fan);
		history.setTemperature(temp);
		
	}

}
