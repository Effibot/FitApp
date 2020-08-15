package logic.entity;

import java.util.List;

public class User extends AbstractUser {

	private List<Integer> bookedSession;

	public User() {
		super();
	}

	public User(int userId, String username, String pwd, String email, String position) {
    	super(userId, username, pwd, email);
    	setMyPosition(position);
    }

	public List<Integer> getBookedSession() {
		return bookedSession;
	}

	public void setBookedSession(List<Integer> bookedSession) {
		this.bookedSession = bookedSession;
	}

}
