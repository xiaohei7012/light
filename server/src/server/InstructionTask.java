package server;

import java.util.TimerTask;

public class InstructionTask extends TimerTask{
	private SimpleServer server;
	
	public InstructionTask(SimpleServer server) {
		this.server = server;
	}

	@Override
	public void run() {
		
	}
}
