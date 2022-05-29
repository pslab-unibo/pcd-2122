package pcd.ass02.ex1;

import java.util.ArrayList;
import java.util.Random;

public class SimulationModel {

	/* bodies in the field */
	private ArrayList<Body> bodies;

	/* boundary of the field */
	private Boundary bounds;

	/* virtual time */
	private double vt;

	/* virtual time step */
	private final double dt;
	
	private final int numBodiesToGen;

	private volatile SimulationSnapshot snap;
	
	public SimulationModel() {
		dt = 0.001;
		this.numBodiesToGen = 0;
	}

	public SimulationModel(int numBodies) {
		dt = 0.001;
		this.numBodiesToGen = numBodies;
	}
	
	public void init() {
		vt = 0;
		if (numBodiesToGen > 0) {
			this.testBodySet(numBodiesToGen);
		} else {
			/* initializing boundary and bodies */
			// testBodySet1_two_bodies();
			// testBodySet2_three_bodies();
			// testBodySet3_some_bodies();
			testBodySet4_many_bodies();
		}
	}
	
	public void nextCycle() {
		vt = vt + dt;
	}
	
	public double getDT() {
		return dt;
	}
	
	public double getVT() {
		return vt;
	}
	
	public Body getBody(int index){
		return bodies.get(index);
	}
	
	public int getNumBodies() {
		return bodies.size();
	}

	public Boundary getBounds() {
		return bounds;
	}
	
	public void makeSnapshot() {
		ArrayList<BodySnapshot> bods = new ArrayList<BodySnapshot>();
		for (var b: bodies) {
			P2d pos = b.getPos();
			bods.add(new BodySnapshot(new P2d(pos.getX(), pos.getY())));
		}
		snap = new SimulationSnapshot(this.getVT(), bods, bounds);
	}

	public SimulationSnapshot getSnapshot() {
		return snap;
	}
	
	private void testBodySet1_two_bodies() {
		bounds = new Boundary(-4.0, -4.0, 4.0, 4.0);
		bodies = new ArrayList<Body>();
		bodies.add(new Body(0, new P2d(-0.1, 0), new V2d(0,0), 1));
		bodies.add(new Body(1, new P2d(0.1, 0), new V2d(0,0), 2));		
	}

	private void testBodySet2_three_bodies() {
		bounds = new Boundary(-1.0, -1.0, 1.0, 1.0);
		bodies = new ArrayList<Body>();
		bodies.add(new Body(0, new P2d(0, 0), new V2d(0,0), 10));
		bodies.add(new Body(1, new P2d(0.2, 0), new V2d(0,0), 1));		
		bodies.add(new Body(2, new P2d(-0.2, 0), new V2d(0,0), 1));		
	}

	private void testBodySet3_some_bodies() {
		bounds = new Boundary(-4.0, -4.0, 4.0, 4.0);
		int nBodies = 100;
		Random rand = new Random(System.currentTimeMillis());
		bodies = new ArrayList<Body>();
		for (int i = 0; i < nBodies; i++) {
			double x = bounds.getX0()*0.25 + rand.nextDouble() * (bounds.getX1() - bounds.getX0()) * 0.25;
			double y = bounds.getY0()*0.25 + rand.nextDouble() * (bounds.getY1() - bounds.getY0()) * 0.25;
			Body b = new Body(i, new P2d(x, y), new V2d(0, 0), 10);
			bodies.add(b);
		}
	}

	private void testBodySet4_many_bodies() {
		bounds = new Boundary(-6.0, -6.0, 6.0, 6.0);
		int nBodies = 1000;
		Random rand = new Random(System.currentTimeMillis());
		bodies = new ArrayList<Body>();
		for (int i = 0; i < nBodies; i++) {
			double x = bounds.getX0()*0.25 + rand.nextDouble() * (bounds.getX1() - bounds.getX0()) * 0.25;
			double y = bounds.getY0()*0.25 + rand.nextDouble() * (bounds.getY1() - bounds.getY0()) * 0.25;
			Body b = new Body(i, new P2d(x, y), new V2d(0, 0), 10);
			bodies.add(b);
		}
	}

	private void testBodySet(int nBodies) {
		bounds = new Boundary(-6.0, -6.0, 6.0, 6.0);
		Random rand = new Random(System.currentTimeMillis());
		bodies = new ArrayList<Body>();
		for (int i = 0; i < nBodies; i++) {
			double x = bounds.getX0()*0.25 + rand.nextDouble() * (bounds.getX1() - bounds.getX0()) * 0.25;
			double y = bounds.getY0()*0.25 + rand.nextDouble() * (bounds.getY1() - bounds.getY0()) * 0.25;
			Body b = new Body(i, new P2d(x, y), new V2d(0, 0), 10);
			bodies.add(b);
		}
	}
	
	static public class BodySnapshot {
		private P2d pos;
		
		public BodySnapshot(P2d pos) {
			this.pos = pos;
		}
		
		public P2d getPos() {
			return pos;
		}
	}
	
	static public class SimulationSnapshot {

		/* bodies in the field */
		private final ArrayList<BodySnapshot> bodies;

		/* virtual time */
		private double vt;

		private Boundary bounds;

		public SimulationSnapshot(double vt, ArrayList<BodySnapshot> bodies, Boundary bounds) {
			this.vt = vt;
			this.bodies = bodies;
			this.bounds = bounds;
		}

		public double getVT() {
			return vt;
		}
		
		public ArrayList<BodySnapshot> getBodies(){
			return bodies;
		}	
		
		public Boundary getBounds() {
			return bounds;
		}
	}
	
}
