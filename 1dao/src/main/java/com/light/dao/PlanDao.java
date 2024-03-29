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
	public int count() {
		EntityManager entityManager = null;
		try {
			entityManager = JPAUtils.getEntityManger();
			int result = Integer.parseInt(
					entityManager.createNativeQuery("select count(*) from plan ").getSingleResult().toString());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (entityManager != null)
				entityManager.close();
		}
	}

	@Override
	public List<Plan> getList(int pageNum, int pageSize) {
		EntityManager entityManager = null;
		List<Plan> result = new ArrayList<Plan>();
		try {
			entityManager = JPAUtils.getEntityManger();
			result = entityManager.createQuery("from Plan ", Plan.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
//			throw e;
		} finally {
			if (entityManager != null)
				entityManager.close();
		}
		return result;
	}

	public Plan getPlanByImei(String imei) {
		EntityManager entityManager = null;
		try {
			entityManager = JPAUtils.getEntityManger();
			List<?> planList = entityManager.createNativeQuery(
					"SELECT p.* FROM light.plan p left join light.dgroup g on p.id = g.planId left join light.device d on g.id = d.groupid where d.imei =  '"
							+ imei + "'",
					Plan.class).getResultList();
			if (planList.size() >= 1) {
				return (Plan) planList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
//			throw e;
		} finally {
			if (entityManager != null)
				entityManager.close();
		}
		return null;
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

	@Override
	public Plan create() throws Exception {
		Plan plan = new Plan();
		create(plan);
		return plan;
	}

}
