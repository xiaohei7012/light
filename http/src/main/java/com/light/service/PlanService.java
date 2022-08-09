package com.light.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.light.dao.PlanDaoInterface;
import com.light.model.Plan;

@Service
public class PlanService {

	@Autowired
	PlanDaoInterface planDao;

	public static void sendPlanReset(int id) {
		sendData("reset " + id);
	}

	public static void sendPlanAdd(int id) {
		sendData("add " + id);
	}

	public Object addPlan(Plan plan) {
		Result<String> result = new Result<String>();
		try {
			planDao.create(plan);
			result.setInfo("成功");
			result.setRet(true);

			sendPlanReset(plan.getId());
		} catch (Exception e) {
			result.setInfo("失败");
			result.setErrMsg(e.getMessage());
			result.setRet(false);
		}

		return result;
	}

	private static void sendData(String data) {
		Socket socket = null;
		try {
			socket = new Socket("localhost", 14332);
			OutputStream out = socket.getOutputStream();
			out.write(data.getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
