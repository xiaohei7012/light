package dao;

import java.util.List;

import model.History;

public interface HistoryDaoInterface {

	public void add();
	
	public List<History> getByDate();
}
