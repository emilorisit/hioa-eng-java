package server.gui;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class HistoryDrawing extends JPanel {


	private static final long serialVersionUID = 6094681210221363547L;
	private int x1;
	private int x2;
	private int x3;
	private int x4;
	private int x5;
	private int x6;
	private int x7;
	private int x8;
	private int x9;
	private int x0;

	private int y1;
	private int y2;
	private int y3;
	private int y4;
	private int y5;
	private int y6;
	private int y7;
	private int y8;
	private int y9;
	private int y0;

	public HistoryDrawing(int x1, int x2, int x3, int x4, int x5, int x6, int x7,
			int x8, int x9, int x0, int y1, int y2, int y3, int y4, int y5,
			int y6, int y7, int y8, int y9, int y0) {
		super();
		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;
		this.x4 = x4;
		this.x5 = x5;
		this.x6 = x6;
		this.x7 = x7;
		this.x8 = x8;
		this.x9 = x9;
		this.x0 = x0;
		this.y1 = y1;
		this.y2 = y2;
		this.y3 = y3;
		this.y4 = y4;
		this.y5 = y5;
		this.y6 = y6;
		this.y7 = y7;
		this.y8 = y8;
		this.y9 = y9;
		this.y0 = y0;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.drawLine(x8, y8, x9, y9);
		g.drawLine(x7, y7, x8, y8);
		g.drawLine(x6, y6, x7, y7);
		g.drawLine(x5, y5, x6, y6);
		g.drawLine(x4, y4, x5, y5);
		g.drawLine(x3, y3, x4, y4);
		g.drawLine(x2, y2, x3, y3);
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x0, y0, x1, y1);

	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public void setX3(int x3) {
		this.x3 = x3;
	}

	public void setX4(int x4) {
		this.x4 = x4;
	}

	public void setX5(int x5) {
		this.x5 = x5;
	}

	public void setX6(int x6) {
		this.x6 = x6;
	}

	public void setX7(int x7) {
		this.x7 = x7;
	}

	public void setX8(int x8) {
		this.x8 = x8;
	}

	public void setX9(int x9) {
		this.x9 = x9;
	}

	public void setX0(int x0) {
		this.x0 = x0;
	}

	public void setY1(int y1) {
		this.y1 = y1;
		repaint();
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public void setY3(int y3) {
		this.y3 = y3;
	}

	public void setY4(int y4) {
		this.y4 = y4;
	}

	public void setY5(int y5) {
		this.y5 = y5;
	}

	public void setY6(int y6) {
		this.y6 = y6;
	}

	public void setY7(int y7) {
		this.y7 = y7;
	}

	public void setY8(int y8) {
		this.y8 = y8;
	}

	public void setY9(int y9) {
		this.y9 = y9;
	}

	public void setY0(int y0) {
		this.y0 = y0;
	}

}