package server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.List;

public class ServerThread extends Thread {

	private Socket socket;
	private String[][] loginList = new String[3][2];
	private ObjectOutputStream outToClient;
	private ObjectInputStream inFromClient;
	private LoginListener loginListener;
	private String commandIn;
	private String data1;
	private String data2;
	private int id;
	private Timestamp dtg;
	private double tankLevel;
	private double tankTemp;
	private boolean transfer;
	private long reportingInterval;
	private String[] reqSplit;
	private boolean listening;
	private String clientRequest;
	private TransferData transmitt;
	private Timestamp date;
	private long time;
	private int batchInterval;

	// ////// Constructor /////////////
	public ServerThread(Socket socket) {
		this.socket = socket;
		listening = true;
		transfer = false;
		reportingInterval = 1000;

		// Available username and password-combinations
		loginList[0][0] = "emilniclas";
		loginList[0][1] = "emilniclas123";
		loginList[1][0] = "guest";
		loginList[1][1] = "guest123";
		loginList[2][0] = "admin";
		loginList[2][1] = "admin123";
	}

	public void run() {
		// Create output- and inputstream
		try {
			outToClient = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e1) {
			System.out.println("Can't open outputstream");
		}
		try {
			inFromClient = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("Can't open inputstream");
		}
		while (listening) {
			// Listens for commands from client
			listenForCommand();
		}

	}

	private void listenForCommand() {

		try {
			clientRequest = (String) inFromClient.readObject();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Can't read from inputstream");
			e.printStackTrace();
		}
		// Evaluate client request
		reqSplit = clientRequest.split("@");
		commandIn = reqSplit[0];
		data1 = reqSplit[1];
		data2 = reqSplit[2];
		if (commandIn.equals("LOGIN")) {
			userCheck(data1, data2);
		}
		if (commandIn.equals("CLOSE")) {
			userLoggedOut();
		}
		if (commandIn.equals("DO")) {
			userCommands();
		}

	}

	// Checks userdata
	private void userCheck(String data1, String data2) {
		boolean userAccepted = false;
		for (int i = 0; i < loginList.length; i++) {
			// Match username
			if (data1.equals(loginList[i][0])) {
				// If username is correct, match password
				if (data2.equals(loginList[i][1])) {
					// If username and password is correct, initiate
					// userLoggedIn
					userAccepted = true;
					break;
					// If password doesn't match, initiate wrongPassword
				} else {
					wrongPassword();
				}

			}
		}
		if (userAccepted) {
			userLoggedIn(data1);
		} else {
			// If username can't be found, initiate wrongUser
			wrongUser(data1);

		}

	}

	private void wrongUser(String usernameIn) {
		try {
			outToClient.writeObject("Wrong username");
			outToClient.flush();
			// Write to terminal
			LoginEvent le = new LoginEvent(this, socket.getInetAddress()
					+ " tried to log in with username: " + usernameIn);
			if (loginListener != null) {
				loginListener.loginEventOccured(le);
			}
		} catch (IOException e) {
			System.out.println("Can't write wrong user-statement to client");
		}
	}

	private void wrongPassword() {
		try {
			outToClient.writeObject("Wrong password");
			outToClient.flush();
			// Write to terminal
			LoginEvent le = new LoginEvent(this, data1
					+ " used the wrong password");
			if (loginListener != null) {
				loginListener.loginEventOccured(le);
			}
		} catch (IOException e) {
			System.out
					.println("Can't write wrong password-statement to client");
		}
	}

	private void userLoggedIn(String usernameIn) {
		time = System.currentTimeMillis();
		date = new Timestamp(time);
		try {
			outToClient.writeObject("LOGINOK");
			outToClient.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
		LoginEvent le = new LoginEvent(this, date + " > User logged in: "
				+ data1);
		if (loginListener != null) {
			loginListener.loginEventOccured(le);
		} else
			System.out.println("loginListener == null");

		transfer = true;

		transmitt = new TransferData(outToClient, id, dtg, tankLevel, tankTemp,
				transfer, reportingInterval);
		transmitt.start();

	}

	private void userCommands() {
		if (reqSplit[1].equals("INTERVAL")) {

			reportingInterval = Integer.parseInt(reqSplit[2]);
			if (transmitt != null) {
				transmitt.setReportingInterval(reportingInterval);
			} else
				System.out.println("Can't set reporting interval");
		} else if (reqSplit[1].equals("BATCH")) {

			batchInterval = Integer.parseInt(reqSplit[2]);
			if (transmitt != null) {
				transmitt.setBatchInterval(batchInterval);
			} else
				System.out.println("Can't set batch interval");
		}

	}

	private void userLoggedOut() {
		time = System.currentTimeMillis();
		date = new Timestamp(time);
		listening = false;
		transmitt.setTransfer(false);
		LoginEvent le = new LoginEvent(this, date + " > User logged out: "
				+ data1);
		if (loginListener != null) {
			loginListener.loginEventOccured(le);
		}
	}

	// Sends alarm to user
	private void alarmToUser(String usernameIn) {
		try {
			outToClient.writeObject("ALARM@Tank temperature over 48 degrees!");
			outToClient.flush();
			// Write to terminal

		} catch (IOException e) {
		}
	}

	// /////// Getters and setters //////////////
	public void setId(int id) {
		this.id = id;
		if (transmitt != null)
			transmitt.setId(id);
	}

	public void setDtg(Timestamp dtg) {
		this.dtg = dtg;
		if (transmitt != null)
			transmitt.setDtg(dtg);
	}

	public void setTankLevel(double tankLevel) {
		this.tankLevel = tankLevel;
		if (transmitt != null)
			transmitt.setTankLevel(tankLevel);
	}

	public void setTankTemp(double tankTemp) {
		this.tankTemp = tankTemp;
		if (tankTemp > 48) {
			alarmToUser(data1);
		}
		if (transmitt != null)
			transmitt.setTankTemp(tankTemp);
	}

	// ////// Listener //////////////////
	public void setLoginEventListener(LoginListener loginListener) {
		this.loginListener = loginListener;

	}

	public void setLog(List<LogEntry> log) {
		if (transmitt != null)
			transmitt.setLog(log);

	}

}
