package client.model;

import java.util.EventObject;
import java.util.List;

public class DataRecieveEvent extends EventObject {

	private static final long serialVersionUID = 6240480287888638833L;
	private List<LogEntry> log;
	private double tankLevel;
	private double tankTemp;

	public DataRecieveEvent(Object source, List<LogEntry> list,
			double tankLevel, double tankTemp) {
		super(source);
		this.log = list;
		this.tankLevel = tankLevel;
		this.tankTemp = tankTemp;

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

}
