package client.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class LogEntryTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 7179800936945267663L;

	private List<LogEntry> db;

	String[] columnNames = { "ID", "DTG", "Tank Level", "Tank Temp." };

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public void setData(List<LogEntry> db) {
		this.db = db;
	}

	public int getColumnCount() {
		return 4;
	}

	public int getRowCount() {
		return db.size();
	}

	public Object getValueAt(int row, int col) {
		LogEntry logEntry = db.get(row);
		switch (col) {
		case 0:
			return logEntry.getId();
		case 1:
			return logEntry.getDtg();
		case 2:
			return logEntry.getTankLevel();
		case 3:
			return logEntry.getTankTemp();
		}
		return null;
	}
}
