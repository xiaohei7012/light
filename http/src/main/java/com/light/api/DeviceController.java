package com.light.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.light.model.Device;
import com.light.service.DeviceService;

@RestController
public class DeviceController {

	@Autowired
	DeviceService deviceService;

	// 添加设备
	@RequestMapping(value = "/device", method = RequestMethod.POST)
	public Object addDevice(Model model, HttpServletRequest request, HttpSession session, @RequestBody Device device) {
		return deviceService.addDevice(device);
	}

	// 显示离线设备
	@RequestMapping(value = "/device/offline", method = RequestMethod.GET)
	public Object getOfflineDevice(Model model, HttpServletRequest request, HttpSession session, int pageNum,int pageSize) {
		return deviceService.getOfflineDevice(pageNum,pageSize);
	}

	// 显示联机设备
	@RequestMapping(value = "/device/online", method = RequestMethod.GET)
	public Object getOnlineDevice(Model model, HttpServletRequest request, HttpSession session, int pageNum,int pageSize) {
		return deviceService.getOnlineDevice(pageNum,pageSize);
	}

	// 更新设备
	@RequestMapping(value = "/device", method = RequestMethod.PUT)
	public Object eidtDevice(Model model, HttpServletRequest request, HttpSession session, Device device) {
		return deviceService.editDevice();
	}
}
