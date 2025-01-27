package server.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.EventObject;
import java.util.List;

public class TankEvent extends EventObject implements Serializable {


	private static final long serialVersionUID = 5258507444346535362L;
	private List<LogEntry> log;
	private double tankLevel;
	private double tankTemp;
	private Timestamp dtg;
	private int id;

	public TankEvent(Object source, List<LogEntry> log, int id, Timestamp dtg,
			double tankLevel, double tankTemp) {
		super(source);
		this.log = log;
		this.dtg = dtg;
		this.tankLevel = tankLevel;
		this.tankTemp = tankTemp;
		this.id = id;

	}

	public Timestamp getDtg() {
		return dtg;
	}

	public void setDtg(Timestamp dtg) {
		this.dtg = dtg;
	}

	public double getTankLevel() {
		return tankLevel;
	}

	public void setTankLevel(double tankLevel) {
		this.tankLevel = tankLevel;
	}

	public double getTankTemp() {
		return tankTemp;
	}

	public void setTankTemp(double tankTemp) {
		this.tankTemp = tankTemp;
	}

	public List<LogEntry> getLog() {
		return log;
	}

	public void setLog(List<LogEntry> log) {
		this.log = log;
	}

	public int getId() {
		return id;
	}

}
