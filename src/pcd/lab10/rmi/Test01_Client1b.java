package pcd.lab10.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Test01_Client1b {

    private Test01_Client1b() {}

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Counter c = (Counter) registry.lookup("countObj");
            int value = c.getValue();
            System.out.println("> value "+value);
            c.inc();
            System.out.println("> value "+c.getValue());
            
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}