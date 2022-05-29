package pcd.ass02.ex1;

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
