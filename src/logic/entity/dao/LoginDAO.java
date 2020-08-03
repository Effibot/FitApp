package logic.entity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import logic.bean.LoginBean;
import logic.exception.UserNotFoundException;
import logic.factory.alertfactory.AlertFactory;

public class LoginDAO extends ConnectionManager {
	private static LoginDAO instance;

	private LoginDAO() {
		super();
	}

	public static synchronized LoginDAO getInstance() {
		if (LoginDAO.instance == null) {
			LoginDAO.instance = new LoginDAO();
		}
		return LoginDAO.instance;
	}

	public boolean authetication(LoginBean bean) {
		String username = bean.getUsername();
		String password = bean.getPassword();
		try {
			ResultSet rs = Query.login(this.st, username, password);
			if (checkResultValidity(1, 2, rs)) {
				bean.setId(rs.getInt("user_id"));
				bean.setType(rs.getBoolean("manager"));
				return true;
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(new UserNotFoundException());
		}
		return false;
	}
}
