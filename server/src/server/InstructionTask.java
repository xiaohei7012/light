package server;

import java.util.TimerTask;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class InstructionTask extends TimerTask{
	private SimpleServer server;
	private Scheduler scheduler;
	
	public InstructionTask(SimpleServer server) {
		this.server = server;
	}

	@Override
	public void run() {
		try {
			//每次运行调度器清理上次的任务 然后重新根据方案生成调度
			//每个方案生成一个调度 每个调度查询有多少个设备 每个设备发送命令
			if(scheduler!=null&&scheduler.isStarted()) {
				scheduler.pauseAll();
				scheduler.shutdown();
			}
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
