package logic.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import logic.factory.alertfactory.AlertFactory;
import logic.model.entity.Manager;

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
			ResultSet rs = Query.getGymUser(this.st, managerId);
			rs.first();
			if (checkResultValidity(1, 4, rs)) {
				String username = rs.getString("username");
				String email = rs.getString("email");
				String pwd = rs.getString("password");
				boolean manager = rs.getBoolean("manager");
				return new Manager(managerId, username, pwd, email,manager);
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
	}

	
}
