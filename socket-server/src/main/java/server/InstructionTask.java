package server;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.light.dao.PlanDao;
import com.light.dao.PlanDaoInterface;
import com.light.model.Plan;

//开始调度 查询方案表 每个方案生成一个调度
public class InstructionTask extends Thread{
	private Scheduler scheduler;
	
	private PlanDaoInterface planDao = new PlanDao();
	
	public InstructionTask() {
	}

	@Override
	public void run() {
		try {
			//每次运行调度器清理上次的任务 然后重新根据方案生成调度
			//每个方案生成一个调度 每个调度查询有多少个设备 每个设备发送命令
			List<Plan> planList = planDao.getAll();
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			for(Plan p : planList) {
				JobDetail j = JobBuilder.newJob(InstrctionJob.class).build();
				Trigger t = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(p.getExpression())).build();
				scheduler.scheduleJob(j, t);
			}
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public void reset() {
		
	}
}
