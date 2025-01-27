package client.model;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class PrefsDialog extends JDialog {

	private static final long serialVersionUID = 4554937040119827822L;
	private JButton okButton;
	private JButton cancelButton;
	private JSpinner intervalSpinner;
	private SpinnerNumberModel spinnerModel;
	private CommandListener commandListener;
	private JCheckBox batchBox;
	private JSpinner batchSpinner;
	private JLabel batchLabel;
	private JLabel batchIntervalLabel;
	private JLabel intervalLabel;

	public PrefsDialog(JFrame parent) {
		super(parent, "Preferences", false);

		// Set up buttons
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PrefsDialog.this.setVisible(false);

				// ReportingInterval in ms
				if (intervalSpinner.isEnabled()) {
					String reportingInterval = String
							.valueOf((int) (intervalSpinner.getValue()) * 1000);

					CommandEvent ce = new CommandEvent(this, "INTERVAL",
							reportingInterval);
					if (commandListener != null) {
						commandListener.commandEventOccured(ce);
					}
				} else if (batchBox.isEnabled()) {
					String batchInterval = String.valueOf((int) (batchSpinner
							.getValue()) * 1000);

					CommandEvent ce = new CommandEvent(this, "BATCH",
							batchInterval);
					if (commandListener != null) {
						commandListener.commandEventOccured(ce);
					}
				}
			}
		});

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				PrefsDialog.this.setVisible(false);

			}
		});

		// Set up spinner
		spinnerModel = new SpinnerNumberModel(1, 1, 60, 1);
		intervalSpinner = new JSpinner(spinnerModel);
		intervalSpinner.setEnabled(true);
		intervalLabel = new JLabel("Reporting interval: ");
		intervalLabel.setEnabled(true);

		// Set up batchInterval
		spinnerModel = new SpinnerNumberModel(10, 10, 60, 10);
		batchSpinner = new JSpinner(spinnerModel);
		batchLabel = new JLabel("Select batch transmission: ");
		batchIntervalLabel = new JLabel("Set batch interval: ");
		batchBox = new JCheckBox("Batch transmission");
		batchBox.setSelected(false);
		batchSpinner.setEnabled(false);
		batchIntervalLabel.setEnabled(false);
		batchBox.setMnemonic(KeyEvent.VK_B);

		// Add listener to batchbox
		batchBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				boolean isTicked = batchBox.isSelected();
				batchSpinner.setEnabled(isTicked);
				batchIntervalLabel.setEnabled(isTicked);
				intervalLabel.setEnabled(!isTicked);
				intervalSpinner.setEnabled(!isTicked);

			}
		});

		// Set up graphics
		setGraphics(parent);

	}

	private void setGraphics(JFrame parent) {

		getRootPane().setDefaultButton(okButton);
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;

		gc.gridx = 0;

		add(intervalLabel, gc);

		gc.gridx++;

		add(intervalSpinner, gc);

		// //////// Next row /////////////

		gc.gridy++;

		gc.gridx = 0;

		add(batchLabel, gc);

		gc.gridx++;

		add(batchBox, gc);

		// //////// Next row /////////////

		gc.gridy++;

		gc.gridx = 0;

		add(batchIntervalLabel, gc);

		gc.gridx++;

		add(batchSpinner, gc);

		// //////// Next row /////////////

		gc.gridy++;

		gc.gridx = 0;

		add(okButton, gc);

		gc.gridx++;

		add(cancelButton, gc);

		setSize(400, 400);
		setLocationRelativeTo(parent);
	}

	public void setCommandListener(CommandListener commandListener) {
		this.commandListener = commandListener;

	}
}
