package com.light.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.light.model.Group;
import com.light.service.GroupService;

@RestController
public class GroupController {
	
	@Autowired
	GroupService groupService;

	@RequestMapping(value = "/group", method = RequestMethod.GET)
	public Object listGroup(Model model, HttpServletRequest request, HttpSession session, int pageNum,int pageSize) {
		return groupService.listGroup(pageNum, pageSize);
	}
	
	@RequestMapping(value = "/group", method = RequestMethod.POST)
	public Object addGroup(Model model, HttpServletRequest request, HttpSession session, @RequestBody Group group) {
		return groupService.addGroup(group);
	}
}
