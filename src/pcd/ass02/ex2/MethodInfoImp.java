package pcd.ass02.ex2;

import pcd.ass02.InterfaceReport;
import pcd.ass02.MethodInfo;

public class MethodInfoImp implements MethodInfo {

	private String name;
	private int beginLine;
	private int endLine;
	private InterfaceReport parent;

	public MethodInfoImp(String name, int beginLine, int endLine, InterfaceReport parent) {
		this.name = name;
		this.beginLine = beginLine;
		this.endLine = endLine;
		this.parent = parent;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getSrcBeginLine() {
		return beginLine;
	}

	@Override
	public int getEndBeginLine() {
		return endLine;
	}

	@Override
	public InterfaceReport getParent() {
		return parent;
	}

}
