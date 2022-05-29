package pcd.ass01.concur;

/**
 * 
 * Passive controller part, designed as a monitor.
 * 
 * @author aricci
 *
 */
public class Controller {

	private Flag stopFlag;
	private MasterAgent masterAgent;
	private SimulationViewerAgent viewerAgent;
	private SimulationView view;
	private SimulationModel model;
	
	public Controller(SimulationModel model) {
		this.model = model;
		this.stopFlag = new Flag();
	}
	
	public synchronized void setView(SimulationView view) {
		this.view = view;
	}
	
	public synchronized void notifyStarted() {
		stopFlag.reset();
    	model.init();
    	masterAgent = new MasterAgent(model, stopFlag);
    	masterAgent.start();
    	viewerAgent = new SimulationViewerAgent(model, view, stopFlag);
    	viewerAgent.start();
    	
	}
	
	public synchronized void notifyStopped() {
		stopFlag.set();
	}

}
