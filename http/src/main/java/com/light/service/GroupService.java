package com.light.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.light.dao.GroupDaoInterface;
import com.light.model.Group;

@Service
public class GroupService {

	@Autowired
	GroupDaoInterface groupDao;
	
	public Object addGroup(Group group) {
		Result<String> result = new Result<String>();
		try {
			groupDao.create(group);
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
