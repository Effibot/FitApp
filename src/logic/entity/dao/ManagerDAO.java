package logic.entity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import logic.entity.Manager;
import logic.factory.alertfactory.AlertFactory;

public class ManagerDAO extends ConnectionManager{
	private static ManagerDAO instance;

	private ManagerDAO() {
		super();
	}

	public static synchronized ManagerDAO getInstance() {
		if (ManagerDAO.instance == null) {
			ManagerDAO.instance = new ManagerDAO();
		}
		return ManagerDAO.instance;
	}
	public Manager getManagerEntity(Integer managerId) {
		try {
			ResultSet rs = Query.getUser(this.st, managerId);
			rs.first();
			if (checkResultValidity(1, 3, rs)) {
				String username = rs.getString("username");
				String email = rs.getString("email");
				String pwd = rs.getString("password");
				return new Manager(managerId, username, pwd, email);
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
	}

	
}
