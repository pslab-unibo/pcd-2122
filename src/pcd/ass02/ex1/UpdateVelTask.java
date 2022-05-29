package pcd.ass02.ex1;

import java.util.concurrent.Callable;

public class UpdateVelTask implements Callable<Void> {

	private SimulationModel model;
	private int bodyIndex;

	public UpdateVelTask(SimulationModel model, int bodyIndex) {
		this.model = model;
		this.bodyIndex = bodyIndex;
	}

	public Void call() {
		double dt = model.getDT();
		/* compute bodies new pos */
		Body b = model.getBody(bodyIndex);

		/* compute total force on bodies */
		V2d totalForce = computeTotalForceOnBody(b);

		/* compute instant acceleration */
		V2d acc = new V2d(totalForce).scalarMul(1.0 / b.getMass());

		/* update velocity */
		b.updateVelocity(acc, dt);
		return null;
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
