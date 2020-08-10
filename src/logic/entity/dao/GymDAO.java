package logic.entity.dao;


import java.sql.ResultSet;
import java.sql.SQLException;


import logic.entity.Gym;
import logic.factory.alertfactory.AlertFactory;

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



	
	
	public Gym getGymEntity(int id) {
		try {
			ResultSet rs = Query.getGym(this.st, id);
			while(rs.next()) {
			if(checkResultValidity(1, 3, rs)) {
				Gym g = new Gym();
				g.setGymId(rs.getInt("gym_id"));
				g.setGymName(rs.getString("gym_name"));
				g.setStreet(rs.getString("street"));
				return g;
			}
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
	}
	
	public Gym getGymEntityById(int id) {
		try {
			ResultSet rs = Query.getGymById(this.st, id);
			while(rs.next()) {
				Gym g = new Gym();
				String gymName = rs.getString("gym_name");
				String street = rs.getString("street");
				g.setGymId(id);
				g.setGymName(gymName);
				g.setStreet(street);
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
			while(rs.next()) {
				 
				return rs.getString("manager_id");
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
	}
	

}
