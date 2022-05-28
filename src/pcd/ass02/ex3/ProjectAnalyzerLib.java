package pcd.ass02.ex3;

import java.io.File;
import com.github.javaparser.StaticJavaParser;
import pcd.ass02.*;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vertx.core.json.JsonObject;

/**
 * Library implemented using Rx
 * 
 * @author aricci
 *
 */
public class ProjectAnalyzerLib implements ProjectAnalyzerRx {

	public ProjectAnalyzerLib() {}

	public Observable<ClassReport> getClassReport(String srcClassPath) {
		Observable<ClassReport> src = Observable
		.fromCallable(() -> {
			try {
				var cu = StaticJavaParser.parse(new File(srcClassPath));
				var rep = new BaseReportImp(srcClassPath);
				var col = new InterfaceOrClassInfoCollector();
				col.visit(cu, rep);
				return rep;
			} catch (Exception ex) {
				throw ex;
			}
		});		
		return src.subscribeOn(Schedulers.io());
	}

	public Observable<InterfaceReport> getInterfaceReport(String srcClassPath) {
		Observable<InterfaceReport> src = Observable
		.fromCallable(() -> {
			try {
				var cu = StaticJavaParser.parse(new File(srcClassPath));
				var rep = new BaseReportImp(srcClassPath);
				var col = new InterfaceOrClassInfoCollector();
				col.visit(cu, rep);
				return rep;
			} catch (Exception ex) {
				throw ex;
			}
		});		
		return src.subscribeOn(Schedulers.io());
	}

	public Observable<PackageReport> getPackageReport(String path) {
		
		Observable<File> fileStream = Observable
		.create(emitter -> {
			try {
				for (var f: new File(path).listFiles()) {
		            emitter.onNext(f);
		        }
		        emitter.onComplete();
			} catch (Exception ex) {
				throw ex;
			}
	    });

		Observable<File> javaFiles = 
		fileStream
		.filter(f -> {
			return f.isFile() && f.getName().endsWith(".java");
		});
		
		Observable<BaseReportImp> reports = 
		javaFiles
		.subscribeOn(Schedulers.io())
		.map(f -> {
			try {
				var cu = StaticJavaParser.parse(f);
				var rep = new BaseReportImp(f.getName());
				var col = new InterfaceOrClassInfoCollector();
				col.visit(cu, rep);
				return rep;
			} catch (Exception ex) {
				throw ex;
			}			
		});
	
		PackageReportImp rep = new PackageReportImp();
		
		reports
		.blockingSubscribe(r -> {
			if (r.isAClass()) {
				rep.addClassReport(r);
			} else {
				rep.addInterfaceReport(r);
			}
		});
		
		return Observable.just(rep);
	}

	private Observable<File> collectDirs(String path){
		Observable<File> fileStream = Observable
		.create(emitter -> {
			try {
				for (var f: new File(path).listFiles()) {
		            emitter.onNext(f);
		        }
		        emitter.onComplete();
			} catch (Exception ex) {
				throw ex;
			}
	    });
		
		Observable<File> dirs = 
			fileStream
			.filter(f -> {
				return f.isDirectory();
			});
		
		Observable<File> others = 
			dirs
			.flatMap(d -> {
				return collectDirs(d.getAbsolutePath());
			});
		
		return dirs.mergeWith(others);	
	}
	
	
	public Observable<ProjectReport> getProjectReport(String path) {
		
		Observable<File> dirs = this.collectDirs(path);

		Observable<PackageReport> reports =
			dirs
			.flatMap(d -> {
				return this.getPackageReport(d.getAbsolutePath()); 
			});
		
		ProjectReportImp rep = new ProjectReportImp();
		reports
		.blockingSubscribe(r -> {
			if (r.getClassesInfo().size() > 0 || r.getInterfacesInfo().size() > 0) {
				rep.addPackageReport(r);
			}
		});
		
		return Observable.just(rep);
	}

	public Observable<JsonObject> analyzePackage(String path){
		Observable<File> fileStream = Observable
		.create(emitter -> {
			try {
				for (var f: new File(path).listFiles()) {
		            emitter.onNext(f);
		        }
		        emitter.onComplete();
			} catch (Exception ex) {
				throw ex;
			}
	    });

		Observable<File> javaFiles = 
		fileStream
		.filter(f -> {
			return f.isFile() && f.getName().endsWith(".java");
		});
		
		return javaFiles
			.subscribeOn(Schedulers.io())
			.flatMap(f -> {
				try {
					return Observable.create(emitter -> {
						var cu = StaticJavaParser.parse(f);
						var col = new InterfaceOrClassAnalyser();
						var notifier = new ElemNotifier(emitter);
						col.visit(cu, notifier);
						emitter.onComplete();
					});
				} catch (Exception ex) {
					throw ex;
				}			
			});
	}
	
	public Observable<JsonObject> analyzeProject(String path){
		return 
			collectDirs(path)
			.flatMap(d -> {
				return this.analyzePackage(d.getAbsolutePath()); 
			});
		
	}

	private void log(String msg) {
		System.out.println("[LIB] " + msg);
	}

}