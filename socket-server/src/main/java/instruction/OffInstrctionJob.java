package instruction;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.light.dao.DeviceDao;
import com.light.dao.DeviceDaoInterface;
import com.light.model.Device;
import com.light.model.Group;

import server.LightServer;
import service.LightService;

public class OffInstrctionJob implements Job{
	private static LightServer server = LightServer.getInstance();
	private static DeviceDaoInterface deviceDao = new DeviceDao();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Device d = (Device)context.getMergedJobDataMap().get("device");
//		List<Device> deviceList = deviceDao.getByGroupId(g.getId());
//		for(Device d:deviceList) {
			deviceDao.turnOff(d);//开了还要处理关
			server.sendData(d.getImei(),LightService.parseInstruction(d));
			
//		}
	}
}
