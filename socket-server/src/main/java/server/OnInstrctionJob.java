package server;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.light.dao.DeviceDao;
import com.light.dao.DeviceDaoInterface;
import com.light.dao.PlanDao;
import com.light.dao.PlanDaoInterface;
import com.light.model.Device;
import com.light.model.Group;
import com.light.model.Plan;

import service.LightService;

public class OnInstrctionJob implements Job {
	private static LightServer server = LightServer.getInstance();
	private static DeviceDaoInterface deviceDao = new DeviceDao();
	private static PlanDaoInterface planDao = new PlanDao();

	public OnInstrctionJob() {
		server = LightServer.getInstance();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Group g = (Group) context.getMergedJobDataMap().get("group");
		List<Device> deviceList = deviceDao.getByGroupId(g.getId());
		if (g.getPlanId() == null) {
			return;
		}
		Plan plan = planDao.getById(g.getPlanId());
		for (Device d : deviceList) {
			deviceDao.setPlan(d, plan);
			LightService.sendData(d.getImei(), plan);
//			deviceDao.turnOn(d);//开了还要处理关
//			server.sendData(d.getImei(),LightService.parseInstruction(d));

		}
	}

}
