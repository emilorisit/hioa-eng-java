package server.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class LogEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3597338027781483554L;

	private static int count = 1;

	private int id;
	private Timestamp dtg;
	private double tankLevel;
	private double tankTemp;

	public LogEntry(Timestamp dtg, double tankLevel, double tankTemp) {
		super();
		this.dtg = dtg;
		this.tankLevel = tankLevel;
		this.tankTemp = tankTemp;
		this.id = count;
		count++;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

}
