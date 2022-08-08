package server;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class InstrctionJob implements Job{
	private static SimpleServer server;
	
	public InstrctionJob() {
		server = SimpleServer.getInstance();
	}
	

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
	}

}
