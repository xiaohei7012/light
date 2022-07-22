package com.light.service;

import java.util.ArrayList;
import java.util.List;

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
			result.setRet(false);
		}
		return result;
	}

	public Object getDevice() {
		Result<List<Device>> result = new Result<List<Device>>();
		List<Device> dlist = new ArrayList<Device>();
		try {
			dlist = deviceDao.getAll();
			result.setInfo(dlist);
			result.setRet(true);
		} catch (Exception e) {
			result.setInfo(dlist);
			result.setRet(false);
		}
		return result;
	}

}
