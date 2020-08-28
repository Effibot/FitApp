package logic.entity.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import logic.entity.Session;
import logic.factory.alertfactory.AlertFactory;

public class SessionDAO extends ConnectionManager {
    private static SessionDAO instance = null;
    String none ="default";
    private SessionDAO() { super(); }
    public static synchronized SessionDAO getInstance(){
        if(SessionDAO.instance == null){
            SessionDAO.instance = new SessionDAO();
        }
        return SessionDAO.instance;
    }


    
	public List<Session> getEventList(String data, String timeStart) {
		List<Session> list = new ArrayList<>();
		
        try {
            ResultSet rs = Query.getEventList(this.st, data, timeStart);
            while (rs.next()) {

            	int courseId = rs.getInt("course_id");
            	Time timeEventStart = Time.valueOf(rs.getString("time_start"));
            	Time timeEventEnd = Time.valueOf(rs.getString( "time_end"));
				Time[] duration = {timeEventStart, timeEventEnd };
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date date = df.parse(data);
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            	String description = rs.getString("description");
            	String street = rs.getString("street");
            	boolean individual = rs.getBoolean("individual");
            	String trainerName = rs.getString("trainer_name");
            	int sessionId = rs.getInt("session_id");
				Session s = new Session(trainerName, rs.getString("gym_id"), duration, sqlDate, description, courseId,
						street, individual, null,sessionId);

                list.add(s);
            }
        } catch (SQLException | ParseException e) {
        	AlertFactory.getInstance().createAlert(e);
        }

        return list;
    }
	
	public List<Session> getGymListEvent(String data, String timeStart, int event) {
		List<Session> list = new ArrayList<>();

		try {
            ResultSet rs = Query.getEventListByEvent(this.st, data, timeStart,String.valueOf(event));
            while (rs.next()) {
            	
            	Time timeEventStart = Time.valueOf(rs.getString("time_start"));
            	Time timeEventEnd = Time.valueOf(rs.getString( "time_end"));
				Time[] duration = {timeEventStart, timeEventEnd };
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date date = df.parse(data);
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            	String description = rs.getString("description");
            	int courseId = rs.getInt("course_id");
            	String street = rs.getString("street");
            	boolean individual = rs.getBoolean("individual");
            	String trainerName = rs.getString("trainer_name");
            	int sessionId = rs.getInt("session_id");

            	Session s = new Session(trainerName, rs.getString("gym_id"),duration,sqlDate,description,courseId,street,individual,null,sessionId);

                list.add(s);
            }
        } catch (SQLException | ParseException e) {
        	AlertFactory.getInstance().createAlert(e);
        }
		return list;
	}
	
	public String getCourseById(int id) {
		try {
			ResultSet rs = Query.getCourseName(this.st, new Integer(id));
			String courseName;
			while(rs.next()) {
				courseName = rs.getString("course_name");
				return courseName;
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
	}
	public List<Session> getCourseGym(int id) {
		List<Session> list = new ArrayList<>();

		try {
			ResultSet rs = Query.getAllCourse(this.st, id);
			while(rs.next()) {
				Time timeStart = Time.valueOf(rs.getString("time_start"));
				Time timeEnd = Time.valueOf(rs.getString( "time_end"));
				Time[] duration = {timeStart, timeEnd };
            	Date data = rs.getDate("day");
            	String description = rs.getString("description");
            	int courseId = rs.getInt("course_id");
            	String street = rs.getString("street");
            	boolean individual = rs.getBoolean("individual");
            	String trainerName = rs.getString("trainer_name");
            	String recurrence = rs.getString("recurrence");
				System.out.println(courseId);
            	int sessionId = rs.getInt("session_id");

            	Session s = new Session(trainerName, String.valueOf(id),duration,data,description,courseId,street,individual,recurrence,sessionId);
            	
				list.add(s);
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return list;
	}

	public List<Integer> getBookedSessionById(int id) {
		List<Integer> list = new ArrayList<>();
		try {
			ResultSet rs = Query.getBookedSession(this.st,id);
			while (rs.next()) {
				list.add(rs.getInt(("session_id")));
				
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return list;
	}

	public Session getBookedSessionEntity(Integer sessionId) {
		try {
			ResultSet rs = Query.getSession(this.st, Integer.parseInt(sessionId.toString()));
			while (rs.next()) {
				String trainerName = rs.getString("trainer_name");
				String gymId = rs.getString("gym_id");
				Time timeStart = Time.valueOf(rs.getString("time_start"));
				Time timeEnd = Time.valueOf(rs.getString("time_end"));
				Time[] duration = { timeStart, timeEnd };
				Date data = rs.getDate("day");
				String description = rs.getString("description");
				int courseId = rs.getInt("course_id");
				String street = rs.getString("street");
				boolean individual = rs.getBoolean("individual");
				String recurrence = rs.getString("recurrence");

				return new Session(trainerName, gymId, duration, data, description, courseId, street, individual,
						recurrence,sessionId);

			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return null;
	}
	
	public boolean hasSession(int trainerId) {
		try {
			ResultSet rs = Query.getTrainerSession(this.st, trainerId);
			if(checkMinRow(1, rs)) {
				return true;
			}
		} catch (SQLException e) {
			AlertFactory.getInstance().createAlert(e);
		}
		return false;
	}

    
}
