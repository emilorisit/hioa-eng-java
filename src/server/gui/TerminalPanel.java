package server.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TerminalPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2110874311721451819L;
	private JTextArea textArea;

	public TerminalPanel() {

		textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);

		scrollPane.setPreferredSize(new Dimension(400, 100));
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.gridx = 0;
		gc.gridy = 0;
		add(scrollPane, gc);

	}

	public void setText(String eventString) {
		this.textArea.append(eventString + "\n");
		textArea.repaint();
	}

}
