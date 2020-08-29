package logic.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import logic.exception.InsertException;
import logic.factory.alertfactory.AlertFactory;
import logic.model.entity.Gym;

public class GymDAO extends ConnectionManager {

	private static GymDAO instance = null;

	private GymDAO() {
		super();
	}

	public static synchronized GymDAO getInstance() {
		if (GymDAO.instance == null) {
			GymDAO.instance = new GymDAO();
		}
		return GymDAO.instance;
	}


	public Gym getGymEntityById(int id) {
		try {
			ResultSet rs = Query.getGymById(this.st, id);
			while (rs.next()) {
				Gym g = new Gym();

				g.setGymId(id);
				g.setGymId(rs.getInt("gym_id"));
				g.setGymName(rs.getString("gym_name"));
				g.setStreet(rs.getString("street"));
				g.setManagerId(Integer.parseInt(rs.getString("manager_id")));
				g.setManagerName(rs.getString("manager_name"));
				return g;
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
	}

	public String getManagerNameGymIdByName(String gym) {
		try {
			ResultSet rs = Query.getGymByName(this.st, gym);
			while (rs.next()) {

				return rs.getString("manager_id");
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
	}

	public void registerGym(String gymName, String gymStreet, int managerId, String managerName) {
		try {
			int count = Query.registerGym(this.st, gymName, gymStreet, managerId, managerName);
			if (count < 1) {
				throw new InsertException();
			}
		} catch (SQLException | InsertException e) {
			AlertFactory.getInstance().createAlert(e);

		}
	}
}
