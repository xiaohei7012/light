package server;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.light.dao.DeviceDao;
import com.light.dao.DeviceDaoInterface;
import com.light.model.Device;
import com.light.model.Group;

import service.LightService;

public class OnInstrctionJob implements Job{
	private static LightServer server = LightServer.getInstance();
	private static DeviceDaoInterface deviceDao = new DeviceDao();
	
	public OnInstrctionJob() {
		server = LightServer.getInstance();
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Group g = (Group)context.getMergedJobDataMap().get("group");
		List<Device> deviceList = deviceDao.getByGroupId(g.getId());
		for(Device d:deviceList) {
			deviceDao.turnOn(d);//开了还要处理关
			server.sendData(d.getImei(),LightService.parseInstruction(d));
			
		}
	}

}
