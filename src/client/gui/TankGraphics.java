package client.gui;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/*This class generates the tankLevel-indicator*/

public class TankGraphics extends JPanel {

	private static final long serialVersionUID = 1672832444537872257L;
	private int tankLevel;

	public TankGraphics(double tankLevel) {
		this.tankLevel = (int) tankLevel;
		setPreferredSize(new Dimension(60, 300));
	}

	public void paint(Graphics g) {
		super.paintComponents(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 120, 500);
		g.setColor(Color.RED);
		g.fillRect(0, (300 - (tankLevel * 3)), 60, tankLevel * 3);

	}
	////////// Setter //////////////
	public void setTankLevel(double tankLevel) {
		this.tankLevel = (int) tankLevel;
	}

}
