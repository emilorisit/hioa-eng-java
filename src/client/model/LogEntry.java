package client.model;

public class LogEntry {

	private static int count = 1;

	private int id;
	private String dtg;
	private double tankLevel;
	private double tankTemp;

	public LogEntry(String dtg, double tankLevel, double tankTemp) {
		super();
		this.dtg = dtg;
		this.tankLevel = tankLevel;
		this.tankTemp = tankTemp;
		this.id = count;
		count++;
	}

	public LogEntry(int id, String dtg, double tankLevel, double tankTemp) {
		super();
		this.dtg = dtg;
		this.tankLevel = tankLevel;
		this.tankTemp = tankTemp;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDtg() {
		return dtg;
	}

	public void setDtg(String dtg) {
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
