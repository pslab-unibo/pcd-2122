package pcd.lab10.rmi;

import java.rmi.*;

public interface MyClass2 extends Remote {

	int get() throws RemoteException;

	void update(int c) throws RemoteException;
	
}