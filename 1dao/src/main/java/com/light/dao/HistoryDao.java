package com.light.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.light.model.History;
import com.light.util.JPAUtils;

@Repository
public class HistoryDao extends SimpleDao<History> implements HistoryDaoInterface {

	@Override
	public void add() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getByDidInMinutes(String imei,int period) {
		EntityManager entityManager = null;
		try {
			entityManager = JPAUtils.getEntityManger();
			Object result = entityManager.createNativeQuery("select count(*) from history where date = date(now()) and imei = '" + imei + "' and ctime >= date_sub(now(), interval " + period + " minute) order by ctime desc").getSingleResult();
			return Integer.parseInt(result.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(entityManager!=null)
				entityManager.close();
		}
		return 0;
	}
	
	@Override
	public List<History> getByImei(String imei) {
		EntityManager entityManager = null;
		try {
			entityManager = JPAUtils.getEntityManger();
			List<History> historyList = entityManager.createQuery("from History where imei = '" + imei + "' order by ctime desc",History.class).setMaxResults(10).getResultList();
			return historyList;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			if(entityManager!=null)
				entityManager.close();
		}
	}

//	public void create(String imei,String l1,String l2,String l3,String l4,String l5,String l6,String fan,double temp) {
//		History history = new History();
//		history.setImei(imei);
//		history.setL1(l1);
//		history.setL2(l2);
//		history.setL3(l3);
//		history.setL4(l4);
//		history.setL5(l5);
//		history.setL6(l6);
//		history.setFan(fan);
//		history.setTemperature(temp);
//		
//	}

}
