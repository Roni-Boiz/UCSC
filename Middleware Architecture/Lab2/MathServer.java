import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.lang.SecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class MathServer extends UnicastRemoteObject implements MathService {

    // TODO: Add a private variable to keep the client count
    // global private variable to store number of clients. volatile is used for make it thread safe
    private volatile int clients = 0;
    
    public MathServer() throws RemoteException {
        super();
    }

    // TODO: add a method to increment the client count. Make it thread safe
    public synchronized void incrementClient() throws RemoteException {
        clients++;
        System.out.println("Client Started. Number of clients in the server: " + clients);
    }
    
    public synchronized void decrementClient() throws RemoteException {
        clients--;
        System.out.println("Client Ended. Number of Clients using Server : " + clients);
    }
    
    public int add(int a, int b) throws RemoteException {
        System.out.println("Adding " + a + " and " + b + " in the Server");
        return a + b;
    }

    public int subtract(int a, int b) throws RemoteException {
        System.out.println("Substracting " + a + " and " + b + " in the Server");
        return a - b;
    }

    public int multiply(int a, int b) throws RemoteException {
        System.out.println("Multiplying " + a + " and " + b + " in the Server");
        return a * b;
    }

    // this method is not remotely accessible as it's not in the remote interface
    public int test(int a) {
        System.out.println("this is a test");
        return 0;
    }

    public int divide(int a, int b) throws RemoteException {
        try {
            System.out.println("Dividing " + a + " and " + b + " in the Server");

            // TODO: Uncomment this to observe the clients get blocked
            // Did this to avoid long print statement in terminal
            System.out.println("I'm doing something that takes a long time.");
            for (double i = 0; i < 100000.0; i++) { // 10000000000000000.0
                System.out.print("-\b");  // I'm doing something that takes a long time.
            }
            System.out.println("");
            ///////////////////////////////////////////////////////
            
            return a / b;  //check for division with zero here!

        } catch (ArithmeticException ex) {
            System.out.println(ex.getMessage());
            return 0;  //check for division with zero here!
        }
    }

    public static void main(String[] args) {

        // set the policy file as the system securuty policy
        System.setProperty("java.security.policy", "file:allowall.policy");

        try {

            MathServer svr = new MathServer();

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("CalculatorService", svr);

            System.out.println("Service started....");
        } catch (RemoteException re) {
            System.err.println(re.getMessage());
        } catch (AlreadyBoundException abe) {
            System.err.println(abe.getMessage());
        }
    }
}
