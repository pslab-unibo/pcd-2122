package pcd.ass02.ex2;

import java.util.HashMap;

/**
 * Model component used to keep track of statistics
 * when doing project analysis.
 * 
 * @author aricci
 *
 */
public class Statistics {

	private HashMap<String, String> clMap;
	private HashMap<String, String> intMap;
	private HashMap<String, String> packMap;
	private int numMethods;
	private int numFields;
		
	static class StatSnapshot {
		private int numMethods;
		private int numFields;
		private int numClasses;
		private int numInterfaces;
		private int numPackages;
	
		StatSnapshot(int numPackages, int numClasses, int numInterfaces, int numMethods, int numFields){
			this.numClasses = numClasses;
			this.numFields = numFields;
			this.numInterfaces = numInterfaces;
			this.numMethods = numMethods;
			this.numPackages = numPackages;
		}

		public int getNumMethods() {
			return numMethods;
		}

		public int getNumFields() {
			return numFields;
		}

		public int getNumClasses() {
			return numClasses;
		}

		public int getNumInterfaces() {
			return numInterfaces;
		}

		public int getNumPackages() {
			return numPackages;
		}
	}
	
	public Statistics(){
		clMap = new HashMap<String, String>();
		intMap = new HashMap<String, String>();
		packMap = new HashMap<String, String>();
		numMethods = 0;
		numFields = 0;
	}

	public  void notifyNewClass(String name) {
		if (clMap.get(name) == null) {
			clMap.put(name, name);
		}
	}

	public  void notifyNewInterface(String name) {
		if (intMap.get(name) == null) {
			intMap.put(name, name);
		}
	}

	public  void notifyNewPackage(String name) {
		if (packMap.get(name) == null) {
			packMap.put(name, name);
		}
	}

	public  void notifyNewMethod() {
		numMethods++;
	}

	public  void notifyNewField() {
		numFields++;
	}

	public StatSnapshot getSnapshot() {
		return new StatSnapshot(packMap.size(), clMap.size(), intMap.size(), numMethods, numFields);
	}
	
	public  void reset() {
		clMap.clear();
		intMap.clear();
		packMap.clear();
		numMethods = 0;
		numFields = 0;
	}
}
