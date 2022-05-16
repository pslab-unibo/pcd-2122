package pcd.lab10.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloService extends Remote {
	
    String sayHello() throws RemoteException;
    
    String sayHello(int n) throws RemoteException;

    void sayHello(Message n) throws RemoteException;
    
    String sayHello(MyClass1 obj) throws RemoteException;

    String sayHello(MyClass2 obj) throws RemoteException;

}