// This is server remote interface used by TemperatureSensorServer
// This help to increase cohesion and loose couple the code, prevent breaking the system due to modification of method name.
interface TemperatureSensor extends java.rmi.Remote
{
	public double getTemperature() throws
		java.rmi.RemoteException;
	public void addTemperatureListener
		(TemperatureListener listener )
		throws java.rmi.RemoteException;
	public void removeTemperatureListener
		(TemperatureListener listener )
		throws java.rmi.RemoteException;
}
