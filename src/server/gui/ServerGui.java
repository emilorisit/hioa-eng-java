package server.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import server.model.LogEntry;

public class ServerGui extends JFrame {

	private static final long serialVersionUID = -2113401695720945021L;
	private TablePanel tablePanel;
	private TankGraphics tankGraphics;
	private TerminalPanel terminalPanel;
	private double tankLevel;
	private HistoryPanel historyPanel;

	public ServerGui() {
		super("Server terminal");
		// ///// Create objects///////////
		tablePanel = new TablePanel();
		terminalPanel = new TerminalPanel();
		tankGraphics = new TankGraphics(tankLevel);
		historyPanel = new HistoryPanel();
		// ///// Configure graphics ////////
		setGraphics();
	}

	private void setGraphics() {
		setLocation(300, 150);
		setSize(670, 650);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.CENTER;

		add(new JLabel("Level: "), gc);

		gc.gridy = 2;
		gc.gridheight = 1;
		gc.anchor = GridBagConstraints.NORTH;
		add(tankGraphics, gc);

		gc.gridy = 3;
		gc.gridheight = 1;
		gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.SOUTH;
		add(new JLabel("Temperature: "), gc);

		gc.gridy = 4;
		gc.gridheight = 1;
		gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.CENTER;
		add(historyPanel, gc);

		gc.gridy = 1;
		gc.gridx = 2;
		add(new JLabel("Tank log"), gc);

		gc.gridx = 2;
		gc.gridy = 2;
		gc.gridheight = 1;
		gc.anchor = GridBagConstraints.NORTH;
		add(tablePanel, gc);

		gc.gridy = 3;
		gc.gridheight = 1;
		gc.anchor = GridBagConstraints.CENTER;
		add(new JLabel("Client activity"), gc);
		gc.gridy = 4;
		gc.gridheight = 1;
		add(terminalPanel, gc);

		// ////////Right ///////////////
		// // gc.weightx = 0.5;
		// // gc.weighty = 0.5;
		// gc.gridy++;
		// add(terminalPanel, gc);

	}

	// /////// Getters and setters /////////
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

	public void setTerminalText(String eventString) {
		terminalPanel.setText(eventString);
	}
}
