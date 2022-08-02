package com.light.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.light.dao.PlanDaoInterface;
import com.light.model.Plan;

@Service
public class PlanService {
	
	@Autowired
	PlanDaoInterface planDao;

	public Object addPlan(Plan plan) {
		Result<String> result = new Result<String>();
		try {
			planDao.create(plan);
			result.setInfo("成功");
			result.setRet(true);
		} catch (Exception e) {
			result.setInfo("失败");
			result.setErrMsg(e.getMessage());
			result.setRet(false);
		}
		return result;
	}

}
