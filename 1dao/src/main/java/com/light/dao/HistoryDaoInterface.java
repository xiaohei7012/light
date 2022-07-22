package com.light.dao;

import java.util.List;

import com.light.model.History;

public interface HistoryDaoInterface {

	public void add();
	
	public List<History> getByDate();
}
