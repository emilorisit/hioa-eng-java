package client.model;

import java.util.EventObject;

public class LogoutEvent extends EventObject{

	private static final long serialVersionUID = 5462264560384223107L;
	private boolean logout;
	public LogoutEvent(Object source, boolean logout) {
		super(source);
		this.logout = logout;
	}
	public boolean isLogout() {
		return logout;
	}
	public void setLogout(boolean logout) {
		this.logout = logout;
	}

}
