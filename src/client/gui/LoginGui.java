package client.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.model.LoginEvent;
import client.model.LoginListener;

public class LoginGui extends JFrame {

	private static final long serialVersionUID = 1886214396400518202L;
	private JTextField userField;
	private JPasswordField passField;
	private JButton loginBtn;
	private LoginListener loginListener;
	
		////////// Constructor /////////////////
	public LoginGui() {
		// Sets name of window
		super("User login");

		//////// Create objects /////////////////
		userField = new JTextField(10);
		passField = new JPasswordField(10);
		loginBtn = new JButton("Login");
		
		loginBtn.setMnemonic(KeyEvent.VK_ENTER);

		// /////// Login-button-listener //////////////
		loginBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				// Retrieve u/p from fields
				String username = userField.getText().trim();
				String password = new String(passField.getPassword());
				
				// Check lenght and raise LoginEvent containing username and password				
				if (username.length() >= 3 && password.length() >= 3) {
					LoginEvent ev = new LoginEvent(this, username, password);
					if (loginListener != null) {
						loginListener.loginEventOccured(ev);
					}
				} else if (username.length() < 3) {
					JOptionPane.showMessageDialog(LoginGui.this,
							"Enter a valid username!");
				} else if (password.length() < 3) {
					JOptionPane.showMessageDialog(LoginGui.this,
							"Enter a valid password!");
				}

			}

		});

		// Initialize graphics		
		setGraphics();

	}

	private void setGraphics() {
		getRootPane().setDefaultButton(loginBtn);
		// Sets window size
		Dimension dim = new Dimension(330, 200);
		setSize(dim);
		// Stops resizing
		setResizable(false);
		// Set close-operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Show window
		setVisible(true);
		// Set location in center of screen
		setLocationRelativeTo(null);

		// Initiates gridbaglayout
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		// /////// Add components /////////////
		gc.weightx = 0.1;
		gc.weighty = 0.1;
		gc.gridx = 0;
		gc.gridy = 0;

		gc.insets = new Insets(30, 30, 0, 30);
		add(new JLabel("Username: "), gc);
		gc.gridx++;
		add(userField, gc);
		gc.gridx = 0;
		gc.gridy++;
		add(new JLabel("Password: "), gc);
		gc.gridx++;
		add(passField, gc);
		gc.gridx = 1;
		gc.gridy++;
		gc.weighty = 2;
		gc.insets = new Insets(0, 0, 0, 30);
		gc.anchor = GridBagConstraints.LINE_END;
		add(loginBtn, gc);
	}

	
	// Listens for 
	public void setLoginListener(LoginListener loginListener) {
		this.loginListener = loginListener;
	}

	// Shows warning produced by server
	public void showWarningDialog(String loginResponse) {
		JOptionPane.showMessageDialog(this, loginResponse);
	}
}
