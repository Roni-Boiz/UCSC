// This is Client Remote interface used by TemperatureMonitor
// This help to increase cohesion and loose couple the code, prevent breaking the system due to modification of method name.
interface TemperatureListener extends java.rmi.Remote
{
	public void temperatureChanged(double temperature) throws 	java.rmi.RemoteException;
}
