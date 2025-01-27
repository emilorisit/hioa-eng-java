package client.model;

import java.util.EventObject;

public class ResponseEvent extends EventObject {

	private static final long serialVersionUID = 7924601870729767091L;
	private String response = "false";

	public ResponseEvent(Object arg0, String response) {
		super(arg0);
		this.response = response;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
