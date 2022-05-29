package pcd.ass01.concur;

public class BaseAgent extends Thread {

	public BaseAgent(String name) {
		super(name);
	}
	
	protected void log(String msg) {
		synchronized (System.out) {
			System.out.println("["+System.currentTimeMillis()+"]["+this.getName()+"] "+ msg);
		}
	}
}
