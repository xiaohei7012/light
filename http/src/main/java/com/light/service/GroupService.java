package com.light.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.light.dao.DeviceDaoInterface;
import com.light.dao.GroupDaoInterface;
import com.light.model.Device;
import com.light.model.Group;

@Service
public class GroupService {

	@Autowired
	GroupDaoInterface groupDao;
	
	@Autowired
	public DeviceDaoInterface deviceDao;
	
	public Object addGroup(Group group) {
		Result<String> result = new Result<String>();
		try {
			groupDao.create(group);
			
			for(String d:group.getIds()) {
				Device device = deviceDao.getById(Integer.parseInt(d));
				device.setGroupId(group.getId());
				deviceDao.update(device);
			}
			result.setInfo("成功");
			result.setRet(true);
		} catch (Exception e) {
			result.setInfo("失败");
			result.setErrMsg(e.getMessage());
			result.setRet(false);
		}
		return result;
	}


	public Object listGroup(int pageNum,int pageSize) {
		Result<Map<String,Object>> result = new Result<Map<String,Object>>();
		Map<String,Object> rmap = new HashMap<String,Object>();
		List<Group> dlist = new ArrayList<Group>();
		try {
			int count = groupDao.count();
			dlist = groupDao.getList(pageNum,pageSize);
			rmap.put("count",count);
			rmap.put("list",dlist);
			result.setRet(true);
		} catch (Exception e) {
			result.setRet(false);
		}
		result.setInfo(rmap);
		return result;
	}
}
