package logic.model.entity;

public class Manager extends AbstractUser {

    private Gym gym;

    public Manager() {
    }

    public Manager(int managerId, String managerName, String pwd, String email, boolean manager) {
        super(managerId, managerName, pwd, email, manager);
        setManager(true);
        setMyPosition("");
    }

    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
    }

}
