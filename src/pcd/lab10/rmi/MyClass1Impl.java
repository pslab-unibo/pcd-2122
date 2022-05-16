package pcd.lab10.rmi;

public class MyClass1Impl implements java.io.Serializable, MyClass1  {

	private int x;
	
	public MyClass1Impl(int x){
		this.x = x;
	}
	
	public int get(){
		return x;
	}
	
	public void update(int c){
		x = c;
	}
}
