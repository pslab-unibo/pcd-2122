package pcd.ass01.concur;

import java.util.*;
import java.util.concurrent.CyclicBarrier;

public class MasterAgent extends BaseAgent {

	private final SimulationModel model;
	private final Flag stopFlag;
	private final long nSteps;
	private final int nWorkers;
	private final boolean withView;
	
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

		/* creating workers */
		
		CyclicBarrier newCycleBarrier = new CyclicBarrier(nWorkers + 1);

		CyclicBarrier updatePosBarrier = new CyclicBarrier(nWorkers);
		
		TaskCompletionLatch bodiesReady = new TaskCompletionLatch(nWorkers);
		
		int nBodiesPerWorker = model.getNumBodies() / nWorkers;
		
		int startIndex = 0;
       	int finalIndex = startIndex + nBodiesPerWorker - 1;	
		for (int i = 0; i < nWorkers - 1; i++){	       		
			UpdateBodyWorkerAgent worker = 
       				new UpdateBodyWorkerAgent("" + i, model, startIndex, finalIndex, newCycleBarrier, updatePosBarrier, bodiesReady, stopFlag);
       		worker.start();
       		startIndex += nBodiesPerWorker;
       		finalIndex += nBodiesPerWorker;
       	}
		
   		UpdateBodyWorkerAgent worker = 
   				new UpdateBodyWorkerAgent("" + nWorkers, model, startIndex, model.getNumBodies() - 1, newCycleBarrier, updatePosBarrier, bodiesReady, stopFlag);
   		worker.start();
		
		long iter = 0;

		/* simulation loop */

		try {
			while (iter < nSteps && !stopFlag.isSet()) {

				newCycleBarrier.await();
				
				model.nextCycle();
				iter++;
	
				bodiesReady.waitCompletion();
				
				if (withView) {
					model.makeSnapshot();
				}
			}
			
			log("done " + iter);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	
	

}
