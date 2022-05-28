package pcd.ass02;

import java.util.List;

public interface PackageReport {

	String getPackageName();
	
	List<ClassReport> getClassesInfo();
	
	List<InterfaceReport> getInterfacesInfo();
}
