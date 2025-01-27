package server.model;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class SimulatedTank extends Thread {

	private double tankLevel;
	private double tankTemp;
	private double randomNumber;
	private int oddEven;
	private boolean tankActive;
	private double tankLevelMax;
	private double tankLevelMin;
	private double tankTempMax;
	private double tankTempMin;
	private double tankInitLevel;
	private double tankInitTemp;
	private Timestamp dtg;
	private long time;
	private List<LogEntry> log;
	private int sleepTime;
	private TankListener tankListener;
	private int id;

	public void run() {
		simulateLevel();
	}

	public void simulateLevel() {

		tankActive = true;
		tankLevelMax = 100d;
		tankTempMax = 50d;
		tankInitLevel = 75d;
		tankInitTemp = 40d;
		tankLevel = tankInitLevel;
		tankTemp = tankInitTemp;
		log = new LinkedList<LogEntry>();
		sleepTime = 1000;
		id = 1;

		while (tankActive) {
			time = System.currentTimeMillis();
			dtg = new Timestamp(time);

			randomNumber = (1 + Math.random() * 10);
			oddEven = (int) (Math.random() * 10);

			// ///////// Tank level ///////////////////////////
			if ((oddEven % 2 == 0)
					&& (tankLevel >= (tankLevelMin + randomNumber))) {
				tankLevel = (tankLevel - randomNumber);
			} else if ((oddEven % 2 != 0)
					&& (tankLevel <= (tankLevelMax - randomNumber))) {
				tankLevel = (tankLevel + randomNumber);
			}
			double tankLevelFormated = roundThreeDecimals(tankLevel);

			// /////// Tank temperature /////////////////////
			randomNumber = (1 + Math.random() * 10);
			oddEven = (int) (Math.random() * 10);
			if ((oddEven % 2 == 0)
					&& (tankTemp >= (tankTempMin + randomNumber))) {
				tankTemp = (tankTemp - randomNumber);
			} else if ((oddEven % 2 != 0)
					&& (tankTemp <= (tankTempMax - randomNumber))) {
				tankTemp = (tankTemp + randomNumber);
			}
			double tankTempFormated = roundThreeDecimals(tankTemp);
			LogEntry logEntry = new LogEntry(dtg, tankLevelFormated,
					tankTempFormated);
			log.add(logEntry);
			// ///////// Raises event ///////////
			TankEvent ev = new TankEvent(this, log, id, dtg, tankLevelFormated,
					tankTempFormated);
			if (tankListener != null) {
				tankListener.tankEventOccured(ev);
			}
			id++;

			// ////// Sleeps for sleepTime ////////////
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// ////// Use Norwegian decimals and limit to three decimals /////////
	private double roundThreeDecimals(double d) {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(
				Locale.GERMAN);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(',');
		DecimalFormat threeDForm = new DecimalFormat("##.###", otherSymbols);
		return Double.valueOf(threeDForm.format(d));
	}

	public List<LogEntry> getLog() {
		return log;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public void setTankListener(TankListener listener) {
		this.tankListener = listener;
	}

}
