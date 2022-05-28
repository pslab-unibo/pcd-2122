package pcd.ass02.ex3;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vertx.core.Future;
import pcd.ass02.*;

public class TestLib {

	public static void main(String[] args) throws Exception  {

		var lib = new ProjectAnalyzerLib();
		// testClassReport(lib);
		// testPackageReport(lib);
		testProjectReport(lib);
		// testProjectAnalysis(lib);
				
		Thread.sleep(1000000);
	}
	
	private static void testClassReport(ProjectAnalyzerRx lib) {
		lib.getClassReport("src/pcd/ass02/data/MyClass1.java")
			.subscribe(el -> {
				log("> " + el);
			});
	}

	private static void testPackageReport(ProjectAnalyzerRx lib) {
		lib.getPackageReport("src/pcd/ass02/data")
			.subscribe(el -> {
				log("> num classes: " + el.getClassesInfo().size());
			});
	}

	private static void testProjectReport(ProjectAnalyzerRx lib) {
		lib.getProjectReport("src")
			.subscribe(el -> {
				log("> num packages: " + el.getPackages().size());
			});
	}

	private static void testProjectAnalysis(ProjectAnalyzerRx lib) {
		lib.analyzeProject("src")
			.subscribe(ev -> {
				System.out.println("> " + ev);
			});
	}
	
	private static void log(String msg) {
		System.out.println("[TEST] " + msg);
	}


}
