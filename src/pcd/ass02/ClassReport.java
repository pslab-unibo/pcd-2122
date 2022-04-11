package pcd.ass02;

import java.util.List;

public interface ClassReport {

	String getFullClassName();
	
	String getSrcFullFileName();

	List<MethodInfo> getMethodsInfo();

	List<FieldInfo> getFieldsInfo();
	
}
