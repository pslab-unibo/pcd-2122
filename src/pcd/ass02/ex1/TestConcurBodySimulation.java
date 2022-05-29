package pcd.ass02.ex1;

public class TestConcurBodySimulation {

    public static void main(String[] args) throws Exception {
    	int nBodies = Integer.parseInt(args[0]);
    	int nSteps = Integer.parseInt(args[1]);
    	int nWorkers = Integer.parseInt(args[2]);

    	System.out.println("Launching the simulation with \n Num bodies: " + nBodies + "\n Num steps: " + nSteps + "\n Num workers: " + nWorkers);
    	long t0 = System.currentTimeMillis();

    	SimulationModel model = new SimulationModel();
    	model.init();
    	MasterAgent master = new MasterAgent(model, nSteps, nWorkers);
    	master.start();
    	
    	master.join();
        
    	long t1 = System.currentTimeMillis();
    	System.out.println("Elapsed time: " + (t1 - t0) + "ms.");
    	
    	System.exit(0);
    }
}
