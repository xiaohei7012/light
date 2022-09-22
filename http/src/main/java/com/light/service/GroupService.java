package com.light.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.light.dao.DeviceDaoInterface;
import com.light.dao.GroupDaoInterface;
import com.light.dao.PlanDaoInterface;
import com.light.model.Device;
import com.light.model.Group;
import com.light.model.Plan;

@Service
public class GroupService {

	@Autowired
	GroupDaoInterface groupDao;
	
	@Autowired
	public DeviceDaoInterface deviceDao;
	
	@Autowired
	public PlanDaoInterface planDao;
	
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
		List<Map<String,Object>> dlist = new ArrayList<Map<String,Object>>();
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

	public Object getDetail(int id) {
		Result<Map<String,Object>> result = new Result<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Group group = groupDao.getById(id);
			map.put("id", group.getId());
			map.put("gname", group.getGname());
			map.put("parentId", group.getParentId());
			map.put("createTime", group.getCreateTime());
			List<Device> devices = deviceDao.getByGroupId(group.getId());
			if(group.getPlanId()!=null) {
				Plan plan = planDao.getById(group.getPlanId());
				map.put("plan", plan);
			}
			map.put("deviceList", devices);
			result.setInfo(map);
			result.setRet(true);
		} catch (Exception e) {
			result.setErrMsg(e.getMessage());
			result.setRet(false);
		}
		return result;
	}

	// 要先清空，再赋值
	public Object editGroup(Group group) {
		Result<String> result = new Result<String>();
		try {
			groupDao.update(group);
			List<Device> dlist = deviceDao.getByGroupId(group.getId());
			for(Device d:dlist) {
				deviceDao.updateGroup(d.getId(), null);//清空
			}
			for(String id :group.getIds()) {
				int did = Integer.parseInt(id);
				deviceDao.updateGroup(did, group.getId());
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
}
