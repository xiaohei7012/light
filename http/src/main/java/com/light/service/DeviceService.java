package com.light.service;

import org.springframework.beans.factory.annotation.Autowired;

import dao.DeviceDaoInterface;
import model.Device;

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

}
