package pcd.ass02.ex2;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

/**
 * Main active component performing the analysis
 * 
 * @author aricci
 *
 */
public class AnalyserAgent extends AbstractVerticle {
	
	private AnalyserView view;
	private Flag stopFlag;
	private Statistics stats;
	private String selectedDir;
	
	public AnalyserAgent(Statistics stats, String selectedDir, Flag stopFlag, AnalyserView view) {
		this.view = view;
		this.stopFlag = stopFlag;
		this.stats = stats;
		this.selectedDir = selectedDir;
	}
	
	public void start() {
		log("started");

		String topicName = "project-data";
		
		vertx.eventBus().consumer(topicName, msg -> {
			
			log("new elem: " + msg.body());
			
			JsonObject obj = (JsonObject) msg.body();
			String event = obj.getString("event");
			String name = obj.getString("name");
			
			if (event.equals(ElemNotifier.NEW_CLASS_FOUND)) {
				stats.notifyNewClass(name);
			} else if (event.equals(ElemNotifier.NEW_INTERFACE_FOUND)) {
				stats.notifyNewInterface(name);
			} else if (event.equals(ElemNotifier.NEW_PACKAGE_DECLARED)) {
				stats.notifyNewPackage(name);
			} else if (event.equals(ElemNotifier.NEW_FIELD_FOUND)) {
				stats.notifyNewField();
			} else if (event.equals(ElemNotifier.NEW_METHOD_FOUND)) {
				stats.notifyNewMethod();
			}
		});
		
		var timerId = vertx.setPeriodic(50, id -> {
			  this.view.updateStatistics(stats.getSnapshot());
		});
		
		stopFlag.reset();
		stats.reset();
		
		new ProjectAnalyzerLib(vertx,stopFlag)
			.analyzeProject(selectedDir, topicName)
			.onSuccess(h -> {
				log("done.");
				vertx.cancelTimer(timerId);
				this.view.updateStatistics(stats.getSnapshot());
				this.view.resetState();
				vertx.undeploy(this.deploymentID()); 
			})
			.onFailure(err -> {
				log("interrupted.");
				vertx.cancelTimer(timerId);
				this.view.updateStatistics(stats.getSnapshot());
				vertx.undeploy(this.deploymentID());
			});
	}

	private void log(String msg) {
		System.out.println("[ Analyser Agent ] " + msg);
	}
}
