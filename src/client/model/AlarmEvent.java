package client.model;

import java.util.EventObject;

public class AlarmEvent extends EventObject {


	private static final long serialVersionUID = -1061743716154856629L;
	private String response;

	public AlarmEvent(Object source, String response) {
		super(source);
	this.response = response;

	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
}
