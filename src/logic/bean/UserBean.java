package logic.bean;

import logic.model.entity.User;

public class UserBean {
    private User user;
    private int userId;

    public UserBean(int userId) {
        this.userId = userId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
