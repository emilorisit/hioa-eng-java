package server.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Timestamp;
import java.util.List;

public class ListenServer extends Thread {

	private ServerSocket serverSocket;
	private boolean listening;
	private ServerThread serverThread;
	private int portNr;
	private LoginListener loginListener;

	public ListenServer(boolean listening, int portNr) {
		this.listening = listening;
		this.portNr = portNr;

	}

	public void run() {
		startListening(listening);
	}

	private void startListening(boolean listening) {

		// Create server-socket
		try {
			serverSocket = new ServerSocket(portNr);
		} catch (IOException e) {
			System.out.println("Can't create server-socket");
		}

		// Listen while true
		while (listening) {
			try {
				serverThread = new ServerThread(serverSocket.accept());
				serverThread.setLoginEventListener(this.loginListener);
				serverThread.start();
			} catch (IOException e) {
				System.out.println("Can't start client-connection-tread");
			}
		} // ENDOF While

		// When told to stop listening, close server-socket
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.out.println("Can't close server-socket");
			e.printStackTrace();
		}
	}

	// /////// Listener /////////////
	public void setLoginEventListener(LoginListener loginListener) {
		this.loginListener = loginListener;

	}

	public void setTankLevel(double tankLevel) {
		if (serverThread != null) {
			serverThread.setTankLevel(tankLevel);
		}
	}

	public void setTankTemp(double tankTemp) {
		if (serverThread != null) {
			serverThread.setTankTemp(tankTemp);
		}
	}

	public void setId(int id) {
		if (serverThread != null) {
			serverThread.setId(id);
		}
	}

	public void setDtg(Timestamp dtg) {
		if (serverThread != null) {
			serverThread.setDtg(dtg);
		}
	}

	public void setLog(List<LogEntry> log) {
		if (serverThread != null) {
			serverThread.setLog(log);
		}
	}
}
