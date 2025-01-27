package server.model;

import server.gui.ServerGui;

public class ServerApp {

	private static SimulatedTank tank;
	private static ServerGui serverGui;
	private static boolean listenServerListening;
	private static int portNr;
	private static ListenServer listenServer;

	public static void main(String[] args) {
		listenServerListening = true;
		portNr = 22022;

		// Create the simulated tank with "sensors"
		tank = new SimulatedTank();
		tank.start();

		listenServer = new ListenServer(listenServerListening, portNr);
		listenServer.start();
		// Listen for login-events and print to terminal window
		listenServer.setLoginEventListener(new LoginListener() {
			public void loginEventOccured(LoginEvent le) {
				serverGui.setTerminalText(le.getUserName());
			}
		});

		// Set listener to tank, to create events of sensor readings
		tank.setTankListener(new TankListener() {
			public void tankEventOccured(TankEvent ev) {
				if (serverGui == null) {
					serverGui = new ServerGui();
				}
				serverGui.setLog(ev.getLog());
				serverGui.setTankLevel(ev.getTankLevel());
				serverGui.setTankTemp(ev.getTankTemp());

				listenServer.setId(ev.getId());
				listenServer.setTankLevel(ev.getTankLevel());
				listenServer.setTankTemp(ev.getTankTemp());
				listenServer.setDtg(ev.getDtg());
				listenServer.setLog(ev.getLog());

			}
		});

	}

}
