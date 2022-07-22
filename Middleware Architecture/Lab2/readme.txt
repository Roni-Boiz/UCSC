Index Number - 19001551

8. Use volatile keyword when declaring variable, use synchronized keyword when defining the function
eg - private volatile int clients = 0;
     public synchronized void incrementClient() throws RemoteException {
        clients++;
        System.out.println("Client Started. Number of clients in the server: " + clients);
    }

9. Server object is singleton with respect to MathService. To make MathServer per client or per call istantiation
   we need to assign new registry for each time we execute MathServer.