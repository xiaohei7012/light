package com.light.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.light.model.Plan;
import com.light.util.JPAUtils;

@Repository
public class PlanDao extends SimpleDao<Plan> implements PlanDaoInterface {

	@Override
	public List<Plan> getAll() {
		EntityManager entityManager = null;
		List<Plan> result = new ArrayList<Plan>();
		try {
			entityManager = JPAUtils.getEntityManger();
			result = entityManager.createQuery("from Plan", Plan.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (entityManager != null)
				entityManager.close();
		}
		return result;
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub

	}

	@Override
	public void rename() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateExpretion() {
		// TODO Auto-generated method stub

	}

}