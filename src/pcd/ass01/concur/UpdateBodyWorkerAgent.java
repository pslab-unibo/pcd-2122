package pcd.ass01.concur;

import java.util.concurrent.CyclicBarrier;

public class UpdateBodyWorkerAgent extends BaseAgent {


	private SimulationModel model;
	private CyclicBarrier updatePosBarrier, newCycleBarrier;
	private Flag stopFlag;
	private TaskCompletionLatch bodyReady;
	private int startIndex;
	private int finalIndex;
	
	public UpdateBodyWorkerAgent(String id, 
				SimulationModel model, int startIndex, int finalIndex,   
				CyclicBarrier newCycleBarrier, CyclicBarrier updatePosBarrier, 
				TaskCompletionLatch bodyReady,
				Flag stopFlag) {
		super("update-worker-"+id);
		this.model = model;
		this.startIndex = startIndex;
		this.finalIndex = finalIndex;
		this.updatePosBarrier = updatePosBarrier;
		this.newCycleBarrier = newCycleBarrier;
		this.bodyReady = bodyReady;
		this.stopFlag = stopFlag;
	}
	
	public void run() {

		double dt = model.getDT();
		Boundary bounds = model.getBounds();
		
		while (!stopFlag.isSet()) {
			try {				
				/* wait for a new cycle */
				newCycleBarrier.await();
				
				/* compute bodies new pos */
				for (int i = startIndex; i <= finalIndex; i++) {
					Body b = model.getBody(i);
	
					/* compute total force on bodies */
					V2d totalForce = computeTotalForceOnBody(b);
	
					/* compute instant acceleration */
					V2d acc = new V2d(totalForce).scalarMul(1.0 / b.getMass());
	
					/* update velocity */
					b.updateVelocity(acc, dt);
				}
	
				updatePosBarrier.await();
				
				/* compute bodies new pos */
	
				for (int i = startIndex; i <= finalIndex; i++) {
					Body b = model.getBody(i);
					b.updatePos(dt);
				}
	
				/* check collisions with boundaries */
	
				for (int i = startIndex; i <= finalIndex; i++) {
					Body b = model.getBody(i);
					b.checkAndSolveBoundaryCollision(bounds);
				}
				
				bodyReady.notifyCompletion();
				
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		log("stopped");
	}

	private V2d computeTotalForceOnBody(Body b) {

		V2d totalForce = new V2d(0, 0);

		/* compute total repulsive force */

		for (int j = 0; j < model.getNumBodies(); j++) {
			Body otherBody = model.getBody(j);
			if (!b.equals(otherBody)) {
				try {
					V2d forceByOtherBody = b.computeRepulsiveForceBy(otherBody);
					totalForce.sum(forceByOtherBody);
				} catch (Exception ex) {
				}
			}
		}

		/* add friction force */
		totalForce.sum(b.getCurrentFrictionForce());

		return totalForce;
	}
	
}
