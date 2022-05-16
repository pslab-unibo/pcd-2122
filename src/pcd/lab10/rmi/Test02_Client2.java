package pcd.lab10.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Test02_Client2 {

    private Test02_Client2() {}

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            HelloService obj = (HelloService) registry.lookup("helloObj2");
            
            MyClass1 arg1 = new MyClass1Impl(200);
            System.out.println("before: >> "+arg1.get());
            obj.sayHello(arg1);
            System.out.println("after: >> "+arg1.get());
            
            MyClass2 arg = new MyClass2Impl(300); 
            UnicastRemoteObject.exportObject(arg, 0);

            System.out.println("before: >> "+arg.get());
            String response = obj.sayHello(arg);
            
            System.out.println("response: " + response);
            System.out.println("after: >> "+arg.get());
            
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}