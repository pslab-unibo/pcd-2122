package pcd.ass02.ex2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.github.javaparser.StaticJavaParser;
import io.vertx.core.*;
import pcd.ass02.*;

public class ProjectAnalyzerLib implements ProjectAnalyzerExt {

	private Vertx vertx;
	private Flag stopFlag;

	public ProjectAnalyzerLib(Vertx vertx, Flag stopFlag) {
		this.vertx = vertx;
		this.stopFlag = stopFlag;
	}

	public Future<ClassReport> getClassReport(String srcClassPath) {
		return vertx.executeBlocking(p -> {
			try {
				var cu = StaticJavaParser.parse(new File(srcClassPath));
				var rep = new BaseReportImp(srcClassPath);
				var col = new InterfaceOrClassInfoCollector();
				col.visit(cu, rep);
				p.complete(rep);
			} catch (Exception ex) {
				p.fail(ex.getMessage());
			}
		});
	}

	public Future<InterfaceReport> getInterfaceReport(String srcInterfacePath) {
		return vertx.executeBlocking(p -> {
			try {
				var cu = StaticJavaParser.parse(new File(srcInterfacePath));
				var rep = new BaseReportImp(srcInterfacePath);
				var col = new InterfaceOrClassInfoCollector();
				col.visit(cu, rep);
				p.complete(rep);
			} catch (Exception ex) {
				p.fail(ex.getMessage());
			}
		});
	}

	private Future<BaseReportImp> asyncGetReportFile(File f) {
		return vertx.executeBlocking(p -> {
			try {
				var cu = StaticJavaParser.parse(f);
				var rep = new BaseReportImp(f.getAbsolutePath());
				var col = new InterfaceOrClassInfoCollector();
				col.visit(cu, rep);
				p.complete(rep);
			} catch (Exception ex) {
				p.fail(ex.getMessage());
			}
		});
	}

	public Future<PackageReport> getPackageReport(String packagePath) {

		PackageReportImp rep = new PackageReportImp();
		rep.setPackageName(packagePath);
		Promise<PackageReport> p = Promise.promise();
		var fs = vertx.fileSystem();
		var listf = fs.readDir(packagePath);
		listf.onSuccess(l -> {
			
			/* triggering the creation of a report for each java source in the dir */
			var fl = new ArrayList<Future>();
			for (var s : l) {
				File file = new File(s);
				if (file.isFile() && file.getName().endsWith(".java")) {
					var fut = this.asyncGetReportFile(file);
					fut.onSuccess(r -> {
						if (r.isAClass()) {
							rep.addClassReport(r);
						} else {
							rep.addInterfaceReport(r);
						}
					});
					fl.add(fut);
				}
			}
			
			/* when all the reports are ready, the package report is composed */
			CompositeFuture
			.all(fl)
			.onSuccess(res -> {
				p.complete(rep);
			});
		});
		return p.future();
	}

	/**
	 * Auxiliary method to collect all the directories from some initial rootDir
	 * @param rootDir
	 * @param collectedDirs
	 * @return
	 */
	private Future<Void> collectDirs(String rootDir, List<String> collectedDirs) {
		var fs = vertx.fileSystem();
		var listf = fs.readDir(rootDir);
		Promise<Void> p = Promise.promise();
		listf.onSuccess(l -> {
			var subd = new ArrayList<Future>();
			for (var s : l) {
				File file = new File(s);
				if (file.isDirectory()) {
					collectedDirs.add(s);
					subd.add(collectDirs(s, collectedDirs));
				}
			}
			CompositeFuture
			.all(subd)
			.onSuccess(h -> {
				p.complete();
			});
		});
		return p.future();

	}

	public Future<ProjectReport> getProjectReport(String path) {
		Promise<ProjectReport> p = Promise.promise();
		var listf = new ArrayList<Future>();
		listf.add(getPackageReport(path));
		var dirs = new ArrayList<String>();
		
		/* first collect all directories */
		collectDirs(path, dirs)
		.onSuccess(h -> {
			ProjectReportImp rep = new ProjectReportImp();
			/* then for each dir try to build a package report */
			for (var d : dirs) {
				var fut = getPackageReport(d);
				fut.onSuccess(r -> {
					/* add the report only if there are classes or interfaces */
					if (r.getClassesInfo().size() > 0 || r.getInterfacesInfo().size() > 0) {
						rep.addPackageReport(r);
					}
				});
				listf.add(fut);
			}
			CompositeFuture
			.all(listf)
			.onSuccess(res -> {
				p.complete(rep);
			});
		});
		return p.future();
	}


	private Future<Void> asyncAnalyseFile(File f, String topic) {
		return vertx.executeBlocking(p -> {
			try {
				if (!stopFlag.isSet()) {
					var cu = StaticJavaParser.parse(f);
					var col = new InterfaceOrClassAnalyser();
					var notifier = new ElemNotifier(vertx, topic);
					col.visit(cu, notifier);
					p.complete();
				} else {
					p.fail("interrupted");
				}
			} catch (Exception ex) {
				p.fail(ex.getMessage());
			}
		});
	}

	public Future<Void> analysePackage(String packagePath, String topic) {
		Promise<Void> p = Promise.promise();
		var fs = vertx.fileSystem();
		var listf = fs.readDir(packagePath);
		listf.onSuccess(l -> {
			var fl = new ArrayList<Future>();
			for (var s : l) {
				File file = new File(s);
				if (file.isFile()) {
					if (file.getName().endsWith(".java")) {
						fl.add(this.asyncAnalyseFile(file, topic));
					}
				}
			}
			CompositeFuture
			.all(fl)
			.onSuccess(res -> {
				p.complete();
			})
			.onFailure(err -> {
				p.fail(err);
			});
		});
		return p.future();
	}

	public Future<Void> analyzeProject(String path, String topic) {
		Promise<Void> p = Promise.promise();
		var listf = new ArrayList<Future>();
		listf.add(analysePackage(path, topic));

		var collectedDirs = new ArrayList<String>();
		
		/* first collect dirs */
		collectDirs(path, collectedDirs)
		.onSuccess(h -> {
			
			/* for each dir, explore it as a package */
			for (var d : collectedDirs) {
				listf.add(analysePackage(d, topic));
			}
			
			CompositeFuture
			.all(listf)
			.onSuccess(res -> {
				p.complete();
			})
			.onFailure(err -> {
				p.fail(err);
			});
		});
		return p.future();

	}

	private void log(String msg) {
		System.out.println("[LIB] " + msg);
	}

}