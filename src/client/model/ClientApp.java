package client.model;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

import client.gui.ClientGui;
import client.gui.LoginGui;

public class ClientApp {

	private static LoginGui loginGui;
	private static TcpClient tcpClient;
	private static ClientGui clientGui;

	public static void main(String[] args) {

		// InvokeLater due to swing
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				// Show login userinterface
				loginGui = new LoginGui();

				// Set actionlistener awaiting user login, btn press.
				loginGui.setLoginListener(new LoginListener() {

					public void loginEventOccured(LoginEvent ev) {

						tcpClient = new TcpClient();
						tcpClient.setUsername(ev.getUsername());
						tcpClient.setPassword(ev.getPassword());
						tcpClient.start();

						// Sets listener for login-response from server
						// If login-reply from server is positive, show client
						// interface
						tcpClient.setResponseListener(new ResponseListener() {
							public void responseOccured(ResponseEvent re) {
								if (re.getResponse().equals("LOGINOK")) {
									loginGui.setVisible(false);
									clientGui = new ClientGui();
									
									// /////// Windowlistener /////////////////
									// Listens for window closing and secures disconnection from
									// server and garbage collector
									clientGui.addWindowListener(new WindowAdapter() {
										public void windowClosing(WindowEvent e) {
											tcpClient.disconnect();
											clientGui.dispose();
											System.gc();
										}
									});
									clientGui.setCommandListener(new CommandListener() {
							
										public void commandEventOccured(CommandEvent ce) {
											tcpClient.sendCmd(ce);
											
										}
									});
								
									
									
									// Listens for dataRecieveEvent and calls set-methods in
									// clientGui
									tcpClient.setDataRecieveListener(new DataRecieveListener() {
										public void dataRecieveEventOccured(DataRecieveEvent re) {
											clientGui.setLog(re.getLog());
											clientGui.setTankLevel(re.getTankLevel());
											clientGui.setTankTemp(re.getTankTemp());
											clientGui.setVisible(true);

										}
									});
									tcpClient.setAlarmEventListener(new AlarmListener(){
										public void alarmEventOccured(
												AlarmEvent ae) {
											clientGui.showWarningDialog(ae.getResponse());
											
										}
										
									});
									// Start processing recieved data from server
									tcpClient.startRecieving();


									
								} 
								

								// Process server login-errors
								else if ((re.getResponse()
										.startsWith("Wrong username"))
										|| (re.getResponse()
												.startsWith("Can't find"))
										|| (re.getResponse()
												.startsWith("Wrong password"))
												||re.getResponse().startsWith("Can't connect")) {
									loginGui.showWarningDialog(re
											.getResponse());
								}

							}
						}); // ENDOF setLoginResponsListener
						

					} // ENDOF Logineventoccured
				}); // ENDOF Loginlistener
				
				


				
				// Set listener for user logout and call disconnect from
				// TCP-client
				if (clientGui != null) {
					clientGui.setLogoutListener(new LogoutListener() {
						public void logoutOccured(LogoutEvent lev) {
							tcpClient.disconnect();
						}
					});

				}

			}
		});
	}


}