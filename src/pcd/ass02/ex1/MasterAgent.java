package pcd.ass02.ex1;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MasterAgent extends BaseAgent {

	private final SimulationModel model;
	private final Flag stopFlag;
	private final long nSteps;
	private final int nWorkers;
	private final boolean withView;
	private ExecutorService exec;
	
	public MasterAgent(SimulationModel model, Flag stopFlag) {
		super("master");
		this.model = model;
		this.stopFlag = stopFlag;
		nWorkers = Runtime.getRuntime().availableProcessors() + 1;
		nSteps = Long.MAX_VALUE;
		withView = true;
	}

	public MasterAgent(SimulationModel model, long nSteps, int nWorkers) {
		super("master");
		this.model = model;
		this.nSteps = nSteps;
		this.stopFlag = new Flag();
		this.nWorkers = nWorkers;
		withView = false;
	}
	
	public void run() {
		exec = Executors.newFixedThreadPool(nWorkers);		
		long iter = 0;

		/* simulation loop */
		
		try {
			while (iter < nSteps && !stopFlag.isSet()) {

				var futs = new ArrayList<Future<Void>>();
				for (int i = 0; i < model.getNumBodies(); i++) {
					futs.add(exec.submit(new UpdateVelTask(model, i)));
				}

				for (var f: futs) { f.get(); }
				
				futs.clear();
				for (int i = 0; i < model.getNumBodies(); i++) {
					futs.add(exec.submit(new UpdatePosTask(model, i)));
				}
	
				for (var f: futs) { f.get(); }

				if (withView) {
					model.makeSnapshot();
				}

				model.nextCycle();
				iter++;
			}			
			log("done " + iter);
			exec.shutdown();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
