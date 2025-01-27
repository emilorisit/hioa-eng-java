package server.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class TransferData extends Thread {

	private ObjectOutputStream outToClient;
	private boolean transfer;
	private long reportingInterval;
	private int id;
	private Timestamp dtg;
	private double tankLevel;
	private double tankTemp;
	private int batchInterval;
	private List<LogEntry> transferLog;
	private boolean batch;
	private boolean interval;
	private List<LogEntry> log;
	private long lastSent;

	// ////// Constructor ////////////
	public TransferData(ObjectOutputStream outToClient, int id, Timestamp dtg,
			double tankLevel, double tankTemp, boolean transfer,
			long reportingInterval) {
		this.outToClient = outToClient;
		this.id = id;
		this.dtg = dtg;
		this.tankLevel = tankLevel;
		this.tankTemp = tankTemp;
		this.reportingInterval = reportingInterval;
		this.transfer = transfer;
		interval = true;
		transferLog = new LinkedList<LogEntry>();
	}

	public void run() {
		while (transfer) {
			while (interval) {
				transferDataInterval(reportingInterval);
			}
			while (batch) {
				for (LogEntry le : log) {
					transferLog.add(le);
				}
				transferDataBatch(batchInterval, transferLog);
			}

		}
	}

	private void transferDataInterval(long reportingInterval) {
		try {
			outToClient.writeObject("ID@" + id);
			outToClient.flush();
			lastSent = id;
			outToClient.writeObject("DTG@" + dtg);
			outToClient.flush();
			outToClient.writeObject("TL@" + tankLevel);
			outToClient.flush();
			outToClient.writeObject("TT@" + tankTemp);
			outToClient.flush();
		} catch (IOException e) {
			System.out
					.println("Can't write tank-data to client, stopping transfer");
			transfer = false;
			interval = false;
		}
		// Sets reportingInterval
		try {
			Thread.sleep(reportingInterval);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void transferDataBatch(int batchInterval, List<LogEntry> transferLog) {

		for (LogEntry le : transferLog) {
			if (le.getId() > lastSent) {

				try {
					outToClient.writeObject("ID@" + le.getId());
					outToClient.flush();
					lastSent = le.getId();
					outToClient.writeObject("DTG@" + le.getDtg());
					outToClient.flush();
					outToClient.writeObject("TL@" + le.getTankLevel());
					outToClient.flush();
					outToClient.writeObject("TT@" + le.getTankTemp());
					outToClient.flush();
				} catch (IOException e) {
					System.out
							.println("Can't write tank-data to client, stopping transfer");
					transfer = false;
					batch = false;
				}
			}

		}
		transferLog.clear();
		try {
			Thread.sleep(batchInterval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	// ////// Setters /////////////
	public void setReportingInterval(long reportingInterval) {
		this.reportingInterval = reportingInterval;
		interval = true;
		batch = false;
	}

	public void setBatchInterval(int batchInterval) {
		this.batchInterval = batchInterval;
		interval = false;
		batch = true;
	}

	public void setOutToClient(ObjectOutputStream outToClient) {
		this.outToClient = outToClient;
	}

	public void setTransfer(boolean transfer) {
		this.transfer = transfer;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDtg(Timestamp dtg) {
		this.dtg = dtg;
	}

	public void setTankLevel(double tankLevel) {
		this.tankLevel = tankLevel;
	}

	public void setTankTemp(double tankTemp) {
		this.tankTemp = tankTemp;
	}

	public void setLog(List<LogEntry> log) {
		this.log = log;

	}

}
