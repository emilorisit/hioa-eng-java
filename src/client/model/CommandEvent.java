package client.model;

import java.util.EventObject;

public class CommandEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5033591984363531977L;
	private String reportingInterval;
	private Object command;

	public CommandEvent(Object arg0, String command, String reportingInterval) {
		super(arg0);
		this.reportingInterval = reportingInterval;
		this.command = command;
	}

	public String getReportingInterval() {
		return reportingInterval;
	}

	public void setReportingInterval(String reportingInterval) {
		this.reportingInterval = reportingInterval;
	}

	public Object getCommand() {
		return command;
	}

	public void setCommand(Object command) {
		this.command = command;
	}



}
