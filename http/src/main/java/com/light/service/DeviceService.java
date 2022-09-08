package com.light.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.light.dao.DeviceDaoInterface;
import com.light.model.Device;

@Service
public class DeviceService {

	@Autowired
	DeviceDaoInterface deviceDao;

	public Object addDevice(Device device) {
		Result<String> result = new Result<String>();
		try {
			deviceDao.create(device);
			result.setInfo("成功");
			result.setRet(true);
		} catch (Exception e) {
			result.setInfo("失败");
			result.setErrMsg(e.getMessage());
			result.setRet(false);
		}
		return result;
	}

	public Object getOfflineDevice(int pageNum,int pageSize) {
		Result<Map<String,Object>> result = new Result<Map<String,Object>>();
		Map<String,Object> rmap = new HashMap<String,Object>();
		List<Map<String,Object>> dlist = new ArrayList<Map<String,Object>>();
		try {
			int count = deviceDao.countOffline();
			dlist = deviceDao.getOffline(pageNum,pageSize);
			rmap.put("count",count);
			rmap.put("list",dlist);
			result.setRet(true);
		} catch (Exception e) {
			result.setRet(false);
		}
		result.setInfo(rmap);
		return result;
	}
	
	public Object getOnlineDevice(int pageNum,int pageSize) {
		Result<Map<String,Object>> result = new Result<Map<String,Object>>();
		Map<String,Object> rmap = new HashMap<String,Object>();
		List<Map<String,Object>> dlist = new ArrayList<Map<String,Object>>();
		try {
			int count = deviceDao.countOnline();
			dlist = deviceDao.getOnline(pageNum,pageSize);
			rmap.put("count",count);
			rmap.put("list",dlist);
			result.setRet(true);
		} catch (Exception e) {
			result.setRet(false);
		}
		result.setInfo(rmap);
		return result;
	}

	public Object editDevice() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getById(int id) {
		Result<Device> result = new Result<Device>();
		try {
			Device d = deviceDao.getById(id);
			result.setInfo(d);
			result.setRet(true);
		} catch (Exception e) {
			result.setInfo(null);
			result.setErrMsg(e.getMessage());
			result.setRet(false);
		}
		return result;
	}

	public Object getAll() {
		Result<List<Device>> result = new Result<List<Device>>();
		try {
			List<Device> d = deviceDao.getAll();
			result.setInfo(d);
			result.setRet(true);
		} catch (Exception e) {
			result.setInfo(null);
			result.setErrMsg(e.getMessage());
			result.setRet(false);
		}
		return result;
	}
}
