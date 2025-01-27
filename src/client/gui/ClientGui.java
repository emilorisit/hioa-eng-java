package client.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import client.model.CommandEvent;
import client.model.CommandListener;
import client.model.LogEntry;
import client.model.LogoutEvent;
import client.model.LogoutListener;
import client.model.PrefsDialog;

import server.gui.HistoryPanel;

public class ClientGui extends JFrame {

	private static final long serialVersionUID = 4054359082445793654L;
	private double tankLevel;
	private TablePanel tablePanel;
	private TankGraphics tankGraphics;
	private LogoutListener logoutListener;
	private PrefsDialog prefsDialog;
	private CommandListener commandListener;
	private HistoryPanel historyPanel;

	public ClientGui() {
		super("Client Interface");
		// Create objects
		tablePanel = new TablePanel();
		tankGraphics = new TankGraphics(tankLevel);
		historyPanel = new HistoryPanel();
		prefsDialog = new PrefsDialog(this);
		
		prefsDialog.setCommandListener(new CommandListener() {
			public void commandEventOccured(CommandEvent ce) {
				commandListener.commandEventOccured(ce);
				
			}
		});

		// Configure graphics
		setGraphics();
	}

	private void setGraphics() {
		// Creates the menubar
		setJMenuBar(createMenuBar());
		// Sets initial location on screen
		setLocation(300, 150);
		// Sets initial size
		setSize(550, 610);
		// Sets minimum size
		setMinimumSize(new Dimension(550, 610));
		

		// Close-operation handeled by window-listener, to secure logout from
		// server
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// ////// Add elements ////////////////
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 5;
		gc.insets = new Insets(10, 10, 10, 10);
		gc.anchor = GridBagConstraints.CENTER;
		gc.weighty = 2;
		add(new JLabel("Tank Monitoring System"), gc);
		
		gc.weighty = 1;
		gc.gridwidth = 1;
		gc.gridy=1;	
		gc.anchor = GridBagConstraints.CENTER;
		
		add(new JLabel("Level: "), gc);
		
		gc.gridy = 2;
		gc.gridheight = 1;
		gc.anchor = GridBagConstraints.NORTH;
		add(tankGraphics, gc);
		
		gc.gridy = 1;
		gc.gridx = 2;
		add(new JLabel("Tank log"),gc);
		
		gc.gridx = 2;
		gc.gridy = 2;
		gc.gridheight = 1;
		gc.anchor = GridBagConstraints.NORTH;
		add(tablePanel, gc);
		
		gc.gridy = 3;
		add(new JLabel("Temperature: "), gc);

		gc.gridy = 4;
		gc.gridwidth = 1;
		add(historyPanel, gc);
		
	}

	// //////// Menubar /////////////
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem logoutItem = new JMenuItem("Exit");
		
		JMenu toolMenu = new JMenu("Tools");
		JMenuItem prefsItem = new JMenuItem("Preferences...");
		
		
		prefsItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				prefsDialog.setVisible(true);
				
			}
		});
		
		
		
		/////// Add mnemonics ///////////
		fileMenu.setMnemonic(KeyEvent.VK_F);
		logoutItem.setMnemonic(KeyEvent.VK_X);
		toolMenu.setMnemonic(KeyEvent.VK_T);
		prefsItem.setMnemonic(KeyEvent.VK_P);

		// Dialog when logout is pressed
		logoutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int action = JOptionPane.showConfirmDialog(ClientGui.this,
						"Do you really want to exit?", "Confirm exit",
						JOptionPane.YES_NO_OPTION);
				if (action == JOptionPane.YES_OPTION) {
					WindowListener[] listeners = getWindowListeners();
					for (WindowListener listener : listeners) {
						listener.windowClosing(new WindowEvent(ClientGui.this,
								0));
					}
				}

			}
		});

		fileMenu.add(logoutItem);
		toolMenu.add(prefsItem);		

		menuBar.add(fileMenu);
		menuBar.add(toolMenu);
		
		
		// Listener for logout-event
		logoutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LogoutEvent lev = new LogoutEvent(this, true);
				if (logoutListener != null) {
					logoutListener.logoutOccured(lev);
				}

			}
		});
		return menuBar;
	}

	// ///////// Getters and setters ////////////////
	public void setTankLevel(double tankLevel) {
		this.tankLevel = tankLevel;
		tankGraphics.setTankLevel(tankLevel);
		tankGraphics.repaint();
	}

	public void setTankTemp(double tankTemp) {
		historyPanel.setTankTemp(tankTemp);
	}

	public void setLog(List<LogEntry> log) {
		tablePanel.setData(log);
		tablePanel.refresh();
	}

	// ////////// Listener /////////////////
	public void setLogoutListener(LogoutListener logoutListener) {
		this.logoutListener = logoutListener;

	}

	public void setCommandListener(CommandListener commandListener) {
		this.commandListener = commandListener;
		
	}
	// Shows warning produced by server
	public void showWarningDialog(String loginResponse) {
		JOptionPane.showMessageDialog(this, loginResponse);
	}


}
