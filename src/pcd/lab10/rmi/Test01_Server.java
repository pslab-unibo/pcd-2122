package pcd.lab10.rmi;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
        
public class Test01_Server  {
                
    public static void main(String args[]) {
        
        try {
            HelloService helloObj = new HelloServiceImpl();
            HelloService helloObjStub = (HelloService) UnicastRemoteObject.exportObject(helloObj, 0);

            Counter count = new CounterImpl(0);
            Counter countStub = (Counter) UnicastRemoteObject.exportObject(count, 0);
            
            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            
            registry.rebind("helloObj", helloObjStub);
            registry.rebind("countObj", countStub);
            
            System.out.println("Objects registered.");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}