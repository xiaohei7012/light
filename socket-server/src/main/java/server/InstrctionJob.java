package server;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.light.dao.DeviceDao;
import com.light.dao.DeviceDaoInterface;
import com.light.model.Device;
import com.light.model.Group;

public class InstrctionJob implements Job{
	private static LightServer server = LightServer.getInstance();
	private static DeviceDaoInterface deviceDao = new DeviceDao();
	
	public InstrctionJob() {
		server = LightServer.getInstance();
	}
	

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Group g = (Group)arg0.getMergedJobDataMap().get("group");
		List<Device> deviceList = deviceDao.getByGroupId(g.getId());
		for(Device d:deviceList) {
			server.sendData(d.getImei(),onInstruction());
		}
	}

	private static String onInstruction() {
		return "hello World";
	}
}
