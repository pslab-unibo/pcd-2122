package pcd.ass02;

import java.util.List;

public interface InterfaceReport  {

	String getInterfaceFullName();
	
	String getSrcFullFileName();
	
	List<MethodInfo> getMethodsInfo();

}
