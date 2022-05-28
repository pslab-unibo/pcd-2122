package pcd.ass02.ex2;

import java.util.ArrayList;
import java.util.List;

import pcd.ass02.*;

/**
 * Class implementing a report either for a class or an interface.
 * 
 * @author aricci
 *
 */
public class BaseReportImp implements ClassReport, InterfaceReport {

	private List<FieldInfo> fields;
	private List<MethodInfo> methods;
	private String fullName;
	private String srcFullFileName;
	private boolean isAnInterface;
	private String packageName;
	
	public BaseReportImp(String SrcFullFileName) {
		this.srcFullFileName = SrcFullFileName;
		methods = new ArrayList<MethodInfo>();
		fields = new ArrayList<FieldInfo>();
	}

	public void setPackageName(String name) {
		packageName = name;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public void setClass() {
		isAnInterface = false;
	}
	
	public void setInterface() {
		isAnInterface = true;
	}
	
	
	public void setFullName(String name) {
		this.fullName = name;
	}
	
	public void addMethod(MethodInfo m) {
		methods.add(m);
	}
	
	public void addField(FieldInfo f) {
		fields.add(f);
	}
		
	public String getFullName() {
		return fullName;
	}

	public String getSrcFullFileName() {
		return this.srcFullFileName;
	}

	@Override
	public List<MethodInfo> getMethodsInfo() {
		return methods;
	}

	@Override
	public List<FieldInfo> getFieldsInfo() {
		return fields;
	}

	
	public boolean isAClass() {
		return !isAnInterface;
	}

	public boolean isAnInterface() {
		return isAnInterface;
	}

	@Override
	public String getInterfaceFullName() {
		return this.getFullName();
	}

	@Override
	public String getFullClassName() {
		return this.getFullName();
	}

}
