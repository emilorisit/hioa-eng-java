package server.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import server.model.LogEntry;
import server.model.LogEntryTableModel;

public class TablePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1543157074185071595L;
	private JTable table;
	private LogEntryTableModel tableModel;

	public TablePanel() {
		tableModel = new LogEntryTableModel();
		table = new JTable(tableModel);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(400, 300));

		add(new JScrollPane(table), BorderLayout.CENTER);

	}

	public void setData(List<LogEntry> db) {
		tableModel.setData(db);
	}

	public void refresh() {
		tableModel.fireTableDataChanged();
		table.scrollRectToVisible(new Rectangle(table.getCellRect(
				table.getRowCount(), 1, false)));
	}
}
