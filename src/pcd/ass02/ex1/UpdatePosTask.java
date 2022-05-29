package pcd.ass02.ex1;

import java.util.concurrent.Callable;

public class UpdatePosTask implements Callable<Void> {

	private SimulationModel model;
	private int bodyIndex;
	
	public UpdatePosTask(SimulationModel model, int bodyIndex) {
		this.model = model;
		this.bodyIndex = bodyIndex;
	}
	
	public Void call() {
		Body b = model.getBody(bodyIndex);
		b.updatePos(model.getDT());
		b.checkAndSolveBoundaryCollision(model.getBounds());
		return null;
	}

	
}
