import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

public class TemperatureSensorServer extends UnicastRemoteObject implements
		TemperatureSensor, Runnable {

	private volatile double temp;
	private ArrayList<TemperatureListener> list = new ArrayList<TemperatureListener>();

	// Constructor to initialize an object from TemperatureSensorServer class and initalize the class varaible `temp` while creating a object
	public TemperatureSensorServer() throws java.rmi.RemoteException {
		temp = 98.0;
	}

	// Remote Function to return current temparature reading of the sensor
	public double getTemperature() throws java.rmi.RemoteException {
		return temp;
	}

	// Remote Helper function for clients to get register with temperatureSensorServer
	public void addTemperatureListener(TemperatureListener listener)
			throws java.rmi.RemoteException {
		System.out.println("adding listener -" + listener);
		list.add(listener);
	}

	// Remote Helper function to deregister the clients from the server upon termination of client
	public void removeTemperatureListener(TemperatureListener listener)
			throws java.rmi.RemoteException {
		System.out.println("removing listener -" + listener);
		list.remove(listener);
	}

	// Thread function
	public void run() {
		// Get random numbers from system clock
		Random r = new Random();
		for (;;) {
			try {
				// Sleep for a random amount of time
				int duration = r.nextInt() % 10000 + 200;
				// Check to see if negative, if so, reverse
				if (duration < 0) {
					duration = duration * -1;
					Thread.sleep(duration);
				}
			} catch (InterruptedException ie) {
			}

			// Get a number, to see if temp goes up or down
			int num = r.nextInt();
			if (num < 0) {
				temp += 0.5;
			} else {
				temp -= 0.5;
			}

			// Notify registered listeners
			notifyListeners();
		}
	}

	// Callback function to notify the change of temperature for registered listeners
	private void notifyListeners() {
		// TO DO: Notify every listener in the registered list if there is a change in the temperature
		try {
			for (TemperatureListener listner: list) {
				listner.temperatureChanged(temp); // Remote callback (actual callback function)
			}	
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Main method of TemperatureSensorServer
	public static void main(String[] args) {

	   System.setProperty("java.security.policy", "file:allowall.policy");
 

		System.out.println("Loading temperature service");

		try {
			TemperatureSensorServer sensor = new TemperatureSensorServer();
			String registry = "localhost";

			String registration = "rmi://" + registry + "/TemperatureSensor";

			Naming.rebind(registration, sensor);

			Thread thread = new Thread(sensor);
			thread.start();
		} catch (RemoteException re) {
			System.err.println("Remote Error - " + re);
		} catch (Exception e) {
			System.err.println("Error - " + e);
		}

	}

}
