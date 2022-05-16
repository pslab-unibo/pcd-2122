package pcd.lab10.rmi;

public class MyClass2Impl implements java.io.Serializable, MyClass2  {

	private int x;
	
	public MyClass2Impl(int x){
		this.x = x;
	}
	
	public int get(){
		return x;
	}
	
	public void update(int c){
		x = c;
	}
}
