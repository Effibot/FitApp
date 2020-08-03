package logic.entity;

public class Manager extends AbstractUser {

	private Gym gym;

	public Manager() {
	}

	public Manager(int managerId, String managerName, String pwd, String email) {
		super(managerId, managerName, pwd, email);
		setManager(true);
	}

	public Gym getGym() {
		return gym;
	}

	public void setGym(Gym gym) {
		this.gym = gym;
	}

}
