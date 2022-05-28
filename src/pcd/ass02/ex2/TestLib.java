package pcd.ass02.ex2;


import java.util.ArrayList;
import java.util.List;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import pcd.ass02.*;

class LibTester extends AbstractVerticle {
	
	public void start() {		
		Flag stopFlag = new Flag();
		var lib = new ProjectAnalyzerLib(vertx, stopFlag);
		
		// this.testClassReport(lib);
		// this.testPackageReport(lib);
		this.testProjectReport(lib);
		// this.testProjectAnalysis(lib);
	}	
	
	private void testClassReport(ProjectAnalyzerExt lib) {
		Future<ClassReport> repf = lib.getClassReport("src/pcd/ass02/data/MyClass.java");
		repf.onSuccess(rep -> {
			log(rep.getFullClassName());
			log(rep.getSrcFullFileName());
			log("" + rep.getFieldsInfo());
			log("" + rep.getMethodsInfo());
		});
	}

	private void testPackageReport(ProjectAnalyzerExt lib) {
		Future<PackageReport> prepf = lib.getPackageReport("src/pcd/ass02/data");
		prepf.onSuccess(rep -> {
			log("done");
			for (var c: rep.getClassesInfo()) {
				log("-> " + c.getFullClassName());
			}
		});
	}

	private void testProjectReport(ProjectAnalyzerExt lib) {
		Future<ProjectReport> repf = lib.getProjectReport("src");
		repf.onSuccess(rep -> {
			log("done >> " + rep.getPackages().size() + " packages found");
		});
	}

	private void testProjectAnalysis(ProjectAnalyzerExt lib) {
		vertx.eventBus().consumer("my-topic", msg -> {
			log("new elem: " + msg.body());
		});
		
		Future<Void> repf = lib.analyzeProject("src/pcd/ass02/data", "my-topic");
		repf.onSuccess(h -> {
			log("done.");
		});		
	}
	
	private void log(String s) {
		System.out.println("[LibTester] " + s);
	}
}

public class TestLib {

	public static void main(String[] args) {
		Vertx  vertx = Vertx.vertx();
		vertx.deployVerticle(new LibTester());
		
	}

}
