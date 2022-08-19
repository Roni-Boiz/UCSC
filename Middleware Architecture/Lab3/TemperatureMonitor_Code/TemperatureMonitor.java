import java.rmi.*;
import java.rmi.server.*;
import java.net.*;

public class TemperatureMonitor extends UnicastRemoteObject implements
		TemperatureListener, Runnable {

	private int count = 0;

	// Constructor to initialize a new object from TemperatureMonitor class
	public TemperatureMonitor() throws RemoteException {
	}

	// Main method of TemperatureMonitor class
	public static void main(String[] args) {

	   System.setProperty("java.security.policy", "file:allowall.policy");
 

		try {
			String registration = "//localhost/TemperatureSensor";

			Remote remoteService = Naming.lookup(registration);
			TemperatureSensor sensor = (TemperatureSensor) remoteService;
			double reading = sensor.getTemperature();
			System.out.println("Original temp : " + reading);
			TemperatureMonitor monitor = new TemperatureMonitor();

			// TO DO: Add method call to register the listener in the server object
			sensor.addTemperatureListener(monitor);

			monitor.run();
		} catch (MalformedURLException mue) {
		} catch (RemoteException re) {
		} catch (NotBoundException nbe) {
		}
	}

	// Remote Callback function to monitor and output the current temperature(Updated) reading of the sensor
	public synchronized void temperatureChanged(double temperature)
			throws java.rmi.RemoteException {
		System.out.println("\nTemperature change event : " + temperature);
		count = 0;
	}

	// Thread function
	public void run() {
		for (;;) {
			count++;

		// note that this might only work on windows console
			System.out.print("\r" + count);
			try {
				Thread.sleep(100);
			} catch (InterruptedException ie) {
			}

		}

	}
}
