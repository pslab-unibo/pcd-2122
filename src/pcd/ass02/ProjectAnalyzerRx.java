package pcd.ass02;

import io.reactivex.rxjava3.core.Observable;
import io.vertx.core.json.JsonObject;

/**
 * 
 * Revised ProjectAnalyzer interface - Rx API
 * 
 * @author aricci
 *
 */
public interface ProjectAnalyzerRx {

	Observable<ClassReport> getClassReport(String srcClassPath);

	Observable<InterfaceReport> getInterfaceReport(String srcInterfacePath);

	Observable<PackageReport> getPackageReport(String srcPackagePath);

	Observable<ProjectReport> getProjectReport(String srcProjectFolderPath);
	
	Observable<JsonObject> analyzeProject(String srcProjectFolderName);
}
