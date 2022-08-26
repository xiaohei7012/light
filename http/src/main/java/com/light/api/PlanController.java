package com.light.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.light.model.Plan;
import com.light.service.PlanService;

@RestController
public class PlanController {

	@Autowired
	PlanService planService;
	
	@RequestMapping(value = "/plan", method = RequestMethod.GET)
	public Object listPlan(Model model, HttpServletRequest request, HttpSession session, int pageNum,int pageSize) {
		return planService.listPlan(pageNum,pageSize);
	}
	
	@RequestMapping(value = "/plan", method = RequestMethod.POST)
	public Object addPlan(Model model, HttpServletRequest request, HttpSession session, @RequestBody Plan plan) {
		return planService.addPlan(plan);
	}
}
