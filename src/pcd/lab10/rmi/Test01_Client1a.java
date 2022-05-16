package pcd.lab10.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Test01_Client1a {

    private Test01_Client1a() {}

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            HelloService obj = (HelloService) registry.lookup("helloObj");
            
            String response = obj.sayHello();
            System.out.println("response: " + response);
            
            System.out.println("response: " + obj.sayHello(10));
            
            Message msg = new Message("Cesena 20");
            obj.sayHello(msg);
            
            System.out.println("done.");
            
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}