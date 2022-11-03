package com.light.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.light.dao.PlanDaoInterface;
import com.light.model.Plan;

@Service
public class PlanService {

	@Autowired
	PlanDaoInterface planDao;

	public static void sendPlanReset(int id) {
		DataService.sendData("reset " + id);
	}

	public static void sendPlanAdd(int id) {
		DataService.sendData("add " + id);
	}

	public Object addPlan(Plan plan) {
		Result<String> result = new Result<String>();
		try {
			planDao.create(plan);
			result.setInfo("成功");
			result.setRet(true);

			sendPlanAdd(plan.getId());
		} catch (Exception e) {
			result.setInfo("失败");
			result.setErrMsg(e.getMessage());
			result.setRet(false);
		}

		return result;
	}

	public Object editPlan(Plan plan) {
		Result<String> result = new Result<String>();
		try {
			String[] exp = plan.getStartTime().split(":");
			plan.setExpression("0" + " " + exp[1] + " " + exp[0] + " * * ?");
			String[] expe = plan.getEndTime().split(":");
			plan.setExpressioff("0" + " " + expe[1] + " " + expe[0] + " * * ?");
			planDao.update(plan);
			result.setInfo("成功");
			result.setRet(true);

			sendPlanReset(plan.getId());
		} catch (Exception e) {
			result.setInfo("失败");
			result.setErrMsg(e.getMessage());
			result.setRet(false);
		}
		return result;
	}


	public Object listPlan(int pageNum, int pageSize) {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String, Object> rmap = new HashMap<String, Object>();
		List<Plan> dlist = new ArrayList<Plan>();
		try {
			int count = planDao.count();
			dlist = planDao.getList(pageNum, pageSize);
			rmap.put("count", count);
			rmap.put("list", dlist);
			result.setRet(true);
		} catch (Exception e) {
			result.setRet(false);
		}
		result.setInfo(rmap);
		return result;
	}

	public Object getDetail(int id) {
		Result<Plan> result = new Result<Plan>();
		try {
			Plan plan = planDao.getById(id);
			result.setInfo(plan);
			result.setRet(true);
		} catch (Exception e) {
			result.setErrMsg(e.getMessage());
			result.setRet(false);
		}
		return result;
	}

}
