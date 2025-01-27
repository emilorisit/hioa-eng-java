package client.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

public class TcpClient extends Thread {

	private String server;
	private Socket socket;
	private int port;
	private String serverTransmission;
	private DataRecieveListener dataRecieveListener;
	private List<LogEntry> log;
	private int id;
	private String dtg;
	private double tankLevel;
	private double tankTemp;
	private String username;
	private String password;
	private ResponseListener responseListener;
	private ObjectOutputStream outToServer;
	private boolean recieving;
	private ObjectInputStream inFromServer;
	Timer internalTimer;
	private AlarmListener alarmListener;

	public void run() {
		try {

			loginClient();
			interpretServerTransmission(serverTransmission);
		} catch (UnknownHostException e) {
			System.out.println("Error while starting TCP-client");
			ResponseEvent lre = new ResponseEvent(this,
					"Can't connect to server");
			if (responseListener != null) {
				responseListener.responseOccured(lre);
			}

		} catch (IOException e) {
			System.out.println("I/O-error while starting TCP-client");
			// Creates loginResponseEvent containing errormessage
			ResponseEvent lre = new ResponseEvent(this,
					"Can't connect to server");
			if (responseListener != null) {
				responseListener.responseOccured(lre);
			}
		}
	} // ENDOF Run

	public void loginClient() throws UnknownHostException, IOException {

		server = "127.0.0.1";
		port = 22022;
		socket = new Socket(server, port);
		log = new LinkedList<LogEntry>();

		// ///// Kick-Timer ////////////////
		int delay = 60000;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			public void run() {
				// ///// Internal-Timer ////////////////
				int delay = 60000;
				Timer internalTimer = new Timer();
				internalTimer.schedule(new TimerTask() {

					public void run() {
						disconnect();

					}
				}, delay);
				int action = JOptionPane.showConfirmDialog(null,
						"Do you wish to stay connected to server?",
						"Stay connected", JOptionPane.YES_OPTION);
				if (action == JOptionPane.YES_OPTION) {
					internalTimer.cancel();
				} else {
					disconnect();
				}
			}
		}, delay, 60000);

		// Outputstream
		outToServer = new ObjectOutputStream(socket.getOutputStream());

		// Send login-info
		outToServer.writeObject("LOGIN@" + username + "@" + password);
		outToServer.flush();

		// Recieve from server
		inFromServer = new ObjectInputStream(socket.getInputStream());

		// //////// Interpret response from server //////////////
		try {
			// Puts input from server to string
			serverTransmission = (String) (inFromServer.readObject());
		} catch (ClassNotFoundException e) {
			ResponseEvent lre = new ResponseEvent(this,
					"Can't read server response");
			if (responseListener != null) {
				responseListener.responseOccured(lre);
			}
		}
		// Raise event with response from server
		ResponseEvent ev = new ResponseEvent(this, serverTransmission);
		if (responseListener != null) {
			responseListener.responseOccured(ev);
		}

	} // ENDOF loginClient

	// Send command to server
	private void sendCommand(CommandEvent ce) {
		try {
			outToServer.writeObject("DO@" + ce.getCommand() + "@"
					+ ce.getReportingInterval());
			outToServer.flush();
		} catch (IOException e) {
			System.out.println("Can't send command to server");
		}

	}

	// Start recieving on command from clientApp
	private void transfer() {
		while (recieving) {
			try {
				serverTransmission = (String) (inFromServer.readObject());
			} catch (ClassNotFoundException e) {
				System.out.println("class not found in transfer()");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("IO-exception in transfer()");
				recieving = false;
				JOptionPane.showMessageDialog(null,
						"Connection to server lost!");
				e.printStackTrace();
			}

			interpretServerTransmission(serverTransmission);
		}

	}

	// ///////// Split up server transmission //////////////
	private void interpretServerTransmission(String serverTransmission) {
		String transSplit[] = serverTransmission.split("@");
		if (transSplit[0].startsWith("ID")) {
			id = Integer.parseInt(transSplit[1]);
		} else if (transSplit[0].startsWith("DTG")) {
			dtg = (transSplit[1]);
		} else if (transSplit[0].startsWith("TL")) {
			tankLevel = Double.parseDouble(transSplit[1]);
		} else if (transSplit[0].startsWith("TT")) {
			tankTemp = Double.parseDouble(transSplit[1]);
			createLogentry(id, dtg, tankLevel, tankTemp);
		} else if (transSplit[0].startsWith("ALARM")) {
			AlarmEvent ae = new AlarmEvent(this, transSplit[1]);

			if (alarmListener != null) {
				alarmListener.alarmEventOccured(ae);
			} else {
				System.out.println("alarmlistener == null");
			}
		}
	}

	// /////////// Add server transmission data to logEntry ////////////
	private void createLogentry(int id, String dtg, double tankLevel,
			double tankTemp) {
		LogEntry logEntry = new LogEntry(id, dtg, tankLevel, tankTemp);
		log.add(logEntry);
		// Raise event each time data is recieved from server¨

		DataRecieveEvent ev = new DataRecieveEvent(this, log, tankLevel,
				tankTemp);

		if (dataRecieveListener != null) {
			dataRecieveListener.dataRecieveEventOccured(ev);
		}
	}

	// /////// Disconnect from server //////////
	public void disconnect() {
		try {
			recieving = false;
			outToServer.writeObject("CLOSE@" + username + "@0");
			outToServer.flush();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("Can't close connection to server");
		}

	}

	// /////////// Listeners /////////////
	public void setDataRecieveListener(DataRecieveListener recieveListener) {
		this.dataRecieveListener = recieveListener;
	}

	public void setResponseListener(ResponseListener responseListener) {
		this.responseListener = responseListener;
	}

	// ////////// Receives username and password from LoginGui, via ClientApp
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<LogEntry> getLog() {
		return log;
	}

	public void startRecieving() {
		recieving = true;
		transfer();
	}

	public void sendCmd(CommandEvent ce) {
		sendCommand(ce);
	}

	public void setAlarmEventListener(AlarmListener alarmListener) {
		this.alarmListener = alarmListener;

	}

}
