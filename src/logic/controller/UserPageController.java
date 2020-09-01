package logic.controller;

import logic.bean.UserBean;
import logic.model.dao.UserDAO;
import logic.model.entity.User;

public class UserPageController {
    private UserBean bean;
    private UserDAO dao = UserDAO.getInstance();

    public UserPageController(UserBean bean) {
        this.bean = bean;
        bean.setUser(createUser());
    }

    private User createUser() {
        return dao.getUserEntity(bean.getUserId());
    }

    public UserBean getBean() {
        return bean;
    }

    public void setBean(UserBean bean) {
        this.bean = bean;
    }
}

// User user = UserDAO.getInstance().getUserEntity(ctrl.getId());
// List<Integer> userSession =
// SessionDAO.getInstance().getBookedSessionById(user.getId());

// List<Session> s = new ArrayList<>();
// for(Integer i : userSession){
// s.add(SessionDAO.getInstance().getBookedSessionEntity(i));
// }
// user.setBookedSession(s);

/*
 * public class UserModel { private User user; private UserDAO dao; getdao
 * getuser setuser setdao
 *
 * }
 */