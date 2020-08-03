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

	/**
	 * Get a list of all available trainings inserted in the db
	 *
	 * @return linked list with training names.
	 */

//	public List<String> getTrainingList() {
//		ArrayList<String> trainingList = new ArrayList<>();
//		String query = "select * from course;";
//		this.setTable(connect(query));
//		if (!this.getTable().isEmpty()) {
//			Map<String, Object> map;
//			for (int i = 0; i < this.getTable().size(); i++) {
//				map = this.getTable().get(i);
//				trainingList.add((String) map.get("course_name"));
//			}
//			return trainingList;
//		}
//		return Collections.emptyList();
//	}


	public Gym getGymEntity(int id) {
		try {
			ResultSet rs = Query.getGym(this.st, id);
			if(checkResultValidity(1, 3, rs)) {
				Gym g = new Gym();
				g.setGymId(rs.getInt("gym_id"));
				g.setGymName(rs.getString("gym_name"));
				g.setStreet(rs.getString("street"));
				return g;
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
	}
	// return a map of registered trainer for the current gymUser instance
//	public Map<Integer,String> getTrainers(int gymId) {
//		String sql = "select trainer_id, trainer_name from trainer where gym_id = "+gymId;
//		this.setTable(connect(sql));
//		if(!this.getTable().isEmpty()){
//			HashMap<Integer, String> tMap = new HashMap<>();
//			Map<String, Object> map;
//			for(int i = 0; i < this.getTable().size(); i++){
//				map = this.getTable().get(i);
//				tMap.put((Integer)map.get("trainer_id"),(String)map.get("trainer_name"));
//			}
//			return tMap;
//		}
//		return null;
//	}

	// return a map of all available courses in the database
//	public Map<Integer, String> getCourses(){
//		String sql = "select course_id, course_name from course";
//		this.setTable(connect(sql));
//		if(!this.getTable().isEmpty()){
//			HashMap<Integer, String> cMap = new HashMap<>();
//			for(Map<String, Object> m : this.getTable()){
//				cMap.put((Integer)m.get("course_id"), (String)m.get("course_name"));
//			}
//			return cMap;
//		}
//		return null;
//	}
}
