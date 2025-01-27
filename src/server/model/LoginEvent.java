package server.model;

import java.util.EventObject;

public class LoginEvent extends EventObject {

	private static final long serialVersionUID = 2768147158429522204L;
	private String userName;

	public LoginEvent(Object arg0, String userName) {
		super(arg0);
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

}
