package pcd.ass01.seq;

public class TestSeqBodySimulation {

    public static void main(String[] args) {
    	int nBodies = Integer.parseInt(args[0]);
    	int nSteps = Integer.parseInt(args[1]);

    	System.out.println("Launching the simulation with " + nBodies + " bodies for " + nSteps + " steps.");
    	long t0 = System.currentTimeMillis();
    	Simulator sim = new Simulator(nBodies);
        sim.execute(nSteps);
    	long t1 = System.currentTimeMillis();
    	System.out.println("Elapsed time: " + (t1 - t0) + "ms.");
    }
}
