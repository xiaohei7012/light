package com.light.device;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.light.service.DeviceService;

import model.Device;

public class DeviceController {

	@Autowired
	DeviceService deviceService;

	// 添加设备
	@RequestMapping(value = "/device", method = RequestMethod.PUT)
	public Object addDevice(Model model, HttpServletRequest request, HttpSession session,
			Device device) {
		return deviceService.addDevice(device);
	}
}
