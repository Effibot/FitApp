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
	
	/*
	 * public Map<String,List<String>> getGymList(String data, String timeStart) {
	 * Map<String,List<String>> map = new HashMap<>();
	 * 
	 * 
	 * try { ResultSet rs = Query.getGymList(this.st, data, timeStart); int i = 0;
	 * while (rs.next()) { ArrayList<String> gymList = new ArrayList<>();
	 * gymList.add(rs.getString("gym_id")); gymList.add(rs.getString("course_id"));
	 * gymList.add(rs.getString("description")); map.put(Integer.toString(i),
	 * gymList); i++; } } catch (SQLException e) {
	 * AlertFactory.getInstance().createAlert(e); } return map; }
	 * 
	 * public Map<String,List<String>> getGymListEvent(String data, String
	 * timeStart, int event) { Map<String,List<String>> map = new HashMap<>();
	 * 
	 * ArrayList<String> gymList = new ArrayList<>(); try { ResultSet rs =
	 * Query.getGymListByEvent(this.st, data, timeStart,String.valueOf(event)); int
	 * i = 0; while (rs.next()) { gymList.add(rs.getString("gym_id"));
	 * gymList.add(rs.getString("course_id"));
	 * gymList.add(rs.getString("description")); map.put(Integer.toString(i),
	 * gymList); i++; } } catch (SQLException e) {
	 * AlertFactory.getInstance().createAlert(e); } System.out.println(map); return
	 * map; }
	 */

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
