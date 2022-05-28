package pcd.ass02.ex3;

import io.vertx.core.AbstractVerticle;

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

		var timerId = vertx.setPeriodic(50, id -> {
			  this.view.updateStatistics(stats.getSnapshot());
		});
		
		new ProjectAnalyzerLib()
			.analyzeProject(selectedDir)
			.subscribe(ev -> {
			
				String event = ev.getString("event");
				String name = ev.getString("name");
			
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
			},(Throwable t) -> {
				log("error  " + t);
			},() -> {
				log("completed.");
				vertx.cancelTimer(timerId);
			});
	}

	private void log(String msg) {
		System.out.println("[ Analyser Agent ] " + msg);
	}
}
