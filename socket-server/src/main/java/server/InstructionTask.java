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

//开始调度 查询方案表与分组表关联后的每个分组生成一个调度(方案)
public class InstructionTask extends Thread {
	private Scheduler scheduler;

	private static PlanDaoInterface planDao = new PlanDao();
	private static GroupDaoInterface groupDao = new GroupDao();

	public InstructionTask() {
	}

	@Override
	public void run() {
		try {
			// 每次运行调度器清理上次的任务 然后重新根据方案生成调度
			// 每个方案生成一个调度 每个调度查询有多少个设备 每个设备发送命令
			List<Group> groupList = groupDao.getAll();
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			for (Group group : groupList) {
				Plan plan = planDao.getById(group.getPlanId());
				JobDetail j = JobBuilder.newJob(InstrctionJob.class).build();
				j.getJobDataMap().put("group", group);
//				TriggerKey key = TriggerKey.triggerKey("plan" + plan.getId());
				Trigger t = TriggerBuilder.newTrigger().withIdentity("plan" + plan.getId(), "plan")
						.withSchedule(CronScheduleBuilder.cronSchedule(plan.getExpression())).build();
				scheduler.scheduleJob(j, t);
			}
			scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resetPlan(String groupId) {
		try {
			Integer groupIdInt = Integer.parseInt(groupId);
			Group group = groupDao.getById(groupIdInt);
			Plan plan = planDao.getById(group.getPlanId());
			Trigger t = TriggerBuilder.newTrigger().withIdentity("plan" + plan.getId(), "plan")
					.withSchedule(CronScheduleBuilder.cronSchedule(plan.getExpression())).build();
			scheduler.rescheduleJob(TriggerKey.triggerKey("plan" + plan.getId()), t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addPlan(String groupId) {
		try {
			Integer groupIdInt = Integer.parseInt(groupId);
			Group group = groupDao.getById(groupIdInt);
			Plan plan = planDao.getById(group.getPlanId());
			JobDetail j = JobBuilder.newJob(InstrctionJob.class).build();
			j.getJobDataMap().put("group", group);
			Trigger t = TriggerBuilder.newTrigger().withIdentity("plan" + plan.getId(), "plan")
					.withSchedule(CronScheduleBuilder.cronSchedule(plan.getExpression())).build();
			scheduler.scheduleJob(j, t);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
