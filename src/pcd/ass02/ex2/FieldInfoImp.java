package pcd.ass02.ex2;

import pcd.ass02.ClassReport;
import pcd.ass02.FieldInfo;

public class FieldInfoImp implements FieldInfo {

	private String name;
	private String type;
	private ClassReport parent;
	
	FieldInfoImp(String name, String type, ClassReport parent){
		this.name = name;
		this.type = type;
		this.parent = parent;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getFieldTypeFullName() {
		return type;
	}

	@Override
	public ClassReport getParent() {
		return parent;
	}

}
