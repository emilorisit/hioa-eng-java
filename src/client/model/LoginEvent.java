package client.model;

import java.util.EventObject;

public class LoginEvent extends EventObject {


	private static final long serialVersionUID = 2383507193725892178L;
	private String username;
	private String password;

	public LoginEvent(Object arg0, String username, String password) {
		super(arg0);
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
