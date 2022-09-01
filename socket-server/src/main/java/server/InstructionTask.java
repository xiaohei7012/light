package server;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.light.dao.GroupDao;
import com.light.dao.GroupDaoInterface;
import com.light.dao.PlanDao;
import com.light.dao.PlanDaoInterface;
import com.light.model.Group;
import com.light.model.Plan;

import service.LightService;

//开始调度 查询方案表与分组表关联后的每个分组生成一个调度(方案)
public class InstructionTask extends Thread {
	private Scheduler scheduler;

	private static PlanDaoInterface planDao = new PlanDao();
	private static GroupDaoInterface groupDao = new GroupDao();
	private static LightService service = new LightService();

	public InstructionTask() {
	}

	// 每次运行调度器清理上次的任务 然后重新根据方案生成调度
	// 每个方案都生成一个调度的开 调度的关 即两个调度
	// 每个方案生成一个调度 每个调度查询有多少个设备 每个设备发送命令
	@Override
	public void run() {
		try {
			List<Group> groupList = groupDao.getAll();
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			for (Group group : groupList) {
				Plan plan = planDao.getById(group.getPlanId());

				JobDetail j = JobBuilder.newJob(OnInstrctionJob.class).build();
				j.getJobDataMap().put("group", group);
				Trigger t = TriggerBuilder.newTrigger().withIdentity("plan" + plan.getId(), "plan")
						.withSchedule(CronScheduleBuilder.cronSchedule(plan.getExpression())).build();// 开的调度
				scheduler.scheduleJob(j, t);

				JobDetail offj = JobBuilder.newJob(OffInstrctionJob.class).build();
				offj.getJobDataMap().put("group", group);
				Trigger offt = TriggerBuilder.newTrigger().withIdentity("plan" + plan.getId() + "_off", "plan")
						.withSchedule(CronScheduleBuilder.cronSchedule(plan.getExpressioff())).build();// 关的调度
				scheduler.scheduleJob(offj, offt);
			}
			scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 重启也要重启两个
	// 重置要根据方案补发命令，方案命令或关命令
	public void resetPlan(String planId) {
		try {
			Plan plan = planDao.getById(Integer.parseInt(planId));
			Trigger on = TriggerBuilder.newTrigger().withIdentity("plan" + plan.getId(), "plan")
					.withSchedule(CronScheduleBuilder.cronSchedule(plan.getExpression())).build();
			scheduler.rescheduleJob(TriggerKey.triggerKey("plan" + plan.getId(), "plan"), on);

			Trigger off = TriggerBuilder.newTrigger().withIdentity("plan" + plan.getId() + "_off", "plan")
					.withSchedule(CronScheduleBuilder.cronSchedule(plan.getExpressioff())).build();
			scheduler.rescheduleJob(TriggerKey.triggerKey("plan" + plan.getId() + "_off", "plan"), off);
			
			//方案下的每个组里面每个设备都要补发
			service.resend4Plan(plan);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 加也要加两个
	public void addPlan(String groupId) {
		try {
			Integer groupIdInt = Integer.parseInt(groupId);
			Group group = groupDao.getById(groupIdInt);
			Plan plan = planDao.getById(group.getPlanId());

			JobDetail j = JobBuilder.newJob(OnInstrctionJob.class).build();
			j.getJobDataMap().put("group", group);
			Trigger t = TriggerBuilder.newTrigger().withIdentity("plan" + plan.getId(), "plan")
					.withSchedule(CronScheduleBuilder.cronSchedule(plan.getExpression())).build();
			scheduler.scheduleJob(j, t);

			JobDetail offj = JobBuilder.newJob(OffInstrctionJob.class).build();
			j.getJobDataMap().put("group", group);
			Trigger offt = TriggerBuilder.newTrigger().withIdentity("plan" + plan.getId() + "_off", "plan")
					.withSchedule(CronScheduleBuilder.cronSchedule(plan.getExpression())).build();
			scheduler.scheduleJob(offj, offt);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
