package server.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class HistoryPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3071835400205159532L;
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

	private HistoryDrawing historyDrawing;
	private int panelHeight;
	private int panelWidth;
	private ArrayList<Integer> tankTemps;

	public HistoryPanel() {

		// Set history panels dimensions and layout
		panelHeight = 100;
		panelWidth = 200;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(panelWidth, panelHeight));
		setBorder(BorderFactory.createBevelBorder(1));
		// Set initial values for X
		tankTemps = new ArrayList<Integer>();

		x0 = 0;
		x1 = ((panelWidth / 10) * 2);
		x2 = ((panelWidth / 10) * 3);
		x3 = ((panelWidth / 10) * 4);
		x4 = ((panelWidth / 10) * 5);
		x5 = ((panelWidth / 10) * 6);
		x6 = ((panelWidth / 10) * 7);
		x7 = ((panelWidth / 10) * 8);
		x8 = ((panelWidth / 10) * 9);
		x9 = ((panelWidth));

		// ////// Initial values for Y
		for (int i = 0; i < 10; i++) {
			tankTemps.add(0);
		}

		historyDrawing = new HistoryDrawing(x1, x2, x3, x4, x5, x6, x7, x8, x9,
				x0, y1, y2, y3, y4, y5, y6, y7, y8, y9, y0);
		add(historyDrawing, BorderLayout.CENTER);

	}

	private void setPanelUpdates() {
		if (historyDrawing != null) {
			historyDrawing.setY0(y0);
			historyDrawing.setY1(y1);
			historyDrawing.setY2(y2);
			historyDrawing.setY3(y3);
			historyDrawing.setY4(y4);
			historyDrawing.setY5(y5);
			historyDrawing.setY6(y6);
			historyDrawing.setY7(y7);
			historyDrawing.setY8(y8);
			historyDrawing.setY9(y9);
			historyDrawing.repaint();

		}
	}

	public void setTankTemp(double tankTemp) {
		tankTemps.add(0, (int) tankTemp);
		y9 = (panelHeight - (tankTemps.get(0) * 2));
		y8 = (panelHeight - (tankTemps.get(1) * 2));
		y7 = (panelHeight - (tankTemps.get(2) * 2));
		y6 = (panelHeight - (tankTemps.get(3) * 2));
		y5 = (panelHeight - (tankTemps.get(4) * 2));
		y4 = (panelHeight - (tankTemps.get(5) * 2));
		y3 = (panelHeight - (tankTemps.get(6) * 2));
		y2 = (panelHeight - (tankTemps.get(7) * 2));
		y1 = (panelHeight - (tankTemps.get(8) * 2));
		y0 = (panelHeight - (tankTemps.get(9) * 2));
		setPanelUpdates();
	}

}
