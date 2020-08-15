package logic.entity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import logic.entity.User;
import logic.factory.alertfactory.AlertFactory;

public class UserDAO extends ConnectionManager {
	private static UserDAO instance;

	private UserDAO() {
		super();
	}

	public static synchronized UserDAO getInstance() {
		if (UserDAO.instance == null) {
			UserDAO.instance = new UserDAO();
		}
		return UserDAO.instance;
	}

	public User getUserEntity(Integer userId) {
		try {
			ResultSet rs = Query.getUser(this.st, userId);
			rs.first();
			if (checkResultValidity(1, 4, rs)) {
				String username = rs.getString("username");
				String email = rs.getString("email");
				String pwd = rs.getString("password");
				String street = rs.getString("street");
				return new User(userId, username, pwd, email, street);
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
	}
}
