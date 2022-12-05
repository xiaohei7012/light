package com.light.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.light.dao.DeviceDaoInterface;
import com.light.dao.HistoryDaoInterface;
import com.light.dao.PlanDaoInterface;
import com.light.model.Device;
import com.light.model.History;
import com.light.model.Instruction;
import com.light.model.Plan;
import com.light.util.Util;

@Service
public class DeviceService {

	public final static String HEAD = "YTE";
	public final static String SPLITWORD = " ";
	public final static String SPLITLINE = "\r\n";

	@Autowired
	DeviceDaoInterface deviceDao;

	@Autowired
	PlanDaoInterface planDao;
	
	@Autowired
	HistoryDaoInterface historyDao;

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

	public Object getOfflineDevice(int pageNum, int pageSize) {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String, Object> rmap = new HashMap<String, Object>();
		List<Map<String, Object>> dlist = new ArrayList<Map<String, Object>>();
		try {
			int count = deviceDao.countOffline();
			dlist = deviceDao.getOffline(pageNum, pageSize);
			rmap.put("count", count);
			rmap.put("list", dlist);
			result.setRet(true);
		} catch (Exception e) {
			result.setRet(false);
		}
		result.setInfo(rmap);
		return result;
	}

	public Object getOnlineDevice(int pageNum, int pageSize) {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String, Object> rmap = new HashMap<String, Object>();
		List<Map<String, Object>> dlist = new ArrayList<Map<String, Object>>();
		try {
			int count = deviceDao.countOnline();
			dlist = deviceDao.getOnline(pageNum, pageSize);
			rmap.put("count", count);
			rmap.put("list", dlist);
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
//			List<History> historyList = historyDao.getByImei(d.getImei());
			d.setHistoryList(null);
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

	public Object getHistoryByDid(int id) {
		Device device = deviceDao.getById(id);
		List<History> historyList = historyDao.getByImei(device.getImei());
		return success(historyList);
	}
	
	public Object sendInstruction(int id, Instruction instruction) {
		Device device = deviceDao.getById(id);
		StringBuilder sb = new StringBuilder();
		// 坏的发命令是要转换
		if(instruction.getL1().equalsIgnoreCase(Device.LightStatus.BAD.toString())) {
			sb.append(Device.LightStatus.OFF.toString());
		}else {
			sb.append(instruction.getL1());
		}
		sb.append(Instruction.SPLITWORD);
		
		if(instruction.getL2().equalsIgnoreCase(Device.LightStatus.BAD.toString())) {
			sb.append(Device.LightStatus.OFF.toString());
		}else {
			sb.append(instruction.getL2());
		}
		sb.append(Instruction.SPLITWORD);
		
		if(instruction.getL3().equalsIgnoreCase(Device.LightStatus.BAD.toString())) {
			sb.append(Device.LightStatus.OFF.toString());
		}else {
			sb.append(instruction.getL3());
		}
		sb.append(Instruction.SPLITWORD);
		
		if(instruction.getL4().equalsIgnoreCase(Device.LightStatus.BAD.toString())) {
			sb.append(Device.LightStatus.OFF.toString());
		}else {
			sb.append(instruction.getL4());
		}
		sb.append(Instruction.SPLITWORD);
		
		if(instruction.getL5().equalsIgnoreCase(Device.LightStatus.BAD.toString())) {
			sb.append(Device.LightStatus.OFF.toString());
		}else {
			sb.append(instruction.getL5());
		}
		sb.append(Instruction.SPLITWORD);
		
		if(instruction.getL6().equalsIgnoreCase(Device.LightStatus.BAD.toString())) {
			sb.append(Device.LightStatus.OFF.toString());
		}else {
			sb.append(instruction.getL6());
		}
		sb.append(Instruction.SPLITWORD);
		
		sb.append(instruction.getFan());
		
		
		boolean ack = DataService.sendData4Ack(
				HEAD + SPLITWORD + device.getImei() + SPLITWORD + "SEND" + SPLITWORD + sb + SPLITLINE);
		if(ack) {
			deviceDao.setStatus(device.getImei(), instruction.getL1(), instruction.getL2(), instruction.getL3(), instruction.getL4(), instruction.getL5(), instruction.getL6(), instruction.getFan(), device.getTemperature());
			return success(null);
		}else {
			return fail("没有ack");
		}
	}

	private <T> Result<T> success(T o) {
		Result<T> result = new Result<T>();
		result.setInfo(o);
		result.setRet(true);
		return result;
	}

	private <T> Result<T> fail(String message) {
		Result<T> result = new Result<T>();
		result.setErrMsg(message);
		result.setRet(false);
		return result;
	}

	public Object updateStartTime(int deviceId, String startTime) {
		try {
			Device device = deviceDao.getById(deviceId);
			Plan plan = null;
			if (device.getPlan() == null) {
				plan = planDao.create();
				device.setPlan(plan);
				deviceDao.update(device);
			}
			plan = device.getPlan();
			if (plan == null) {
				plan = planDao.create();
			}
			plan.setStartTime(startTime);
			String[] exp = plan.getStartTime().split(":");
			plan.setExpression("0" + " " + exp[1] + " " + exp[0] + " * * ?");
			plan.setInstruction(Instruction.parseInstruction(6)); 
			planDao.update(plan);
			//发送reset命令
			PlanService.sendPlanReset(plan.getId());
			return success(null);
		} catch (Exception e) {
			return fail(e.getMessage());
		}
	}

	public Object updateEndTime(int deviceId, String endTime) {
		try {
			Device device = deviceDao.getById(deviceId);
			Plan plan = null;
			if (device.getPlan() == null) {
				plan = planDao.create();
				device.setPlan(plan);
				deviceDao.update(device);
			}
			plan = device.getPlan();
			if (plan == null) {
				plan = planDao.create();
			}
			plan.setEndTime(endTime);
			String[] exp = plan.getEndTime().split(":");
			plan.setExpressioff("0" + " " + exp[1] + " " + exp[0] + " * * ?");
			planDao.update(plan);
			//发送reset命令
			PlanService.sendPlanReset(plan.getId());
			return success(null);
		} catch (Exception e) {
			return fail(e.getMessage());
		}
	}

	public Object getGraphByDid(int id) {
		Device device = deviceDao.getById(id);
		List<History> historyList = historyDao.getByImei(device.getImei());
		return success(historyList);
	}

}
