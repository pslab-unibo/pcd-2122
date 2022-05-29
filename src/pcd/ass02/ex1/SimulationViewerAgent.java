package pcd.ass02.ex1;

public class SimulationViewerAgent extends BaseAgent {
	
	private SimulationModel model;
	private Flag stopFlag;
	private SimulationView view;
	
	public SimulationViewerAgent(SimulationModel model, SimulationView view, Flag stopFlag) {
		super("viewer");
		this.model = model;
		this.view = view;
		this.stopFlag = stopFlag;
	}

	public void run() {

		while (!stopFlag.isSet()) {
			try {
				Thread.sleep(20);
				SimulationModel.SimulationSnapshot snap = model.getSnapshot();
				view.display(snap);			
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
