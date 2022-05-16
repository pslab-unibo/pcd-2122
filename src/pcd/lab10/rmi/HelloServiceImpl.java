package pcd.lab10.rmi;

import java.rmi.RemoteException;

public class HelloServiceImpl implements HelloService {
        
    public HelloServiceImpl() {}

    public String sayHello() {
    		return "Hello, world!";
    }
    
    public String sayHello(int n) {
    	    return "Hello, world! ==> " + n;
    }

    public /* synchronized */ void sayHello(Message m) {
    		System.out.println("hello: "+m.getContent());
    	    // while (true) {} 
    }

    public String sayHello(MyClass1 obj) throws RemoteException {
		obj.update(obj.get() + 1);
    return "Hello, world! ==> " + obj.get();
}
    
    public String sayHello(MyClass2 obj) throws RemoteException {
    		obj.update(obj.get() + 1);
        return "Hello, world! ==> " + obj.get();
    }
        
}