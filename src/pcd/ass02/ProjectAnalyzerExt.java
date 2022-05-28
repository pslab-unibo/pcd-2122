package pcd.ass02;

import io.vertx.core.*;
import java.util.function.*;

/**
 * 
 * Revised ProjectAnalyzer interface 
 * 
 * In this revision, the method analyzeProject uses an event bus topic
 * to generate the events to be observed by the client of the library.
 * 
 * 
 * ProjectAnalyzerExt
 * @author aricci
 *
 */
public interface ProjectAnalyzerExt {

	/**
	 * Async method to retrieve the report about a specific class,
	 * given the full path of the class source file
	 * 
	 * @param srcClassFileName
	 * @return
	 */
	Future<ClassReport> getClassReport(String srcClassPath);

	/**
	 * Async method to retrieve the report about a specific interface,
	 * given the full path of the interface source file
	 * 
	 * @param srcClassFileName
	 * @return
	 */
	Future<InterfaceReport> getInterfaceReport(String srcInterfacePath);

	/**
	 * Async method to retrieve the report about a package,
	 * given the full path of the package folder
	 * 
	 * @param srcClassFileName
	 * @return
	 */
	Future<PackageReport> getPackageReport(String srcPackagePath);

	/**
	 * Async method to retrieve the report about a project
	 * given the full path of the project folder 
	 * 
	 * @param srcProjectFolderPath
	 * @return
	 */
	Future<ProjectReport> getProjectReport(String srcProjectFolderPath);
	
	/**
	 * Async function that analyze a project given the full path of the project folder,
	 * generating events on the specified topic of the event bus 
	 * 
	 * @param srcProjectFolderName
	 * @param topic
	 */
	Future<Void> analyzeProject(String srcProjectFolderName, String topic);
}
