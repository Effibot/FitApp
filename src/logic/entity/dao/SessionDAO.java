package logic.entity.dao;

import logic.entity.Session;
import logic.factory.alertfactory.AlertFactory;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;

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
            	Time[] duration = {Time.valueOf(rs.getString("time_start")), Time.valueOf(rs.getString( "time_end"))};
            	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            	java.util.Date date = df.parse(data);
            	// if you really need java.sql.Date
            	java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            	int courseId = Integer.parseInt(rs.getString("course_id"));
            	String gymId = rs.getString("gym_id");
            	String description =  rs.getString("description");
            	String street = rs.getString("street");

            	
            	Session s = new Session(courseId,  gymId  ,duration,sqlDate, description,none,street);

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
            	Time[] duration = {Time.valueOf(rs.getString("time_start")), Time.valueOf(rs.getString( "time_end"))};
            	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            	java.util.Date date = df.parse(data);
            	java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            	Session s = new Session(Integer.parseInt(rs.getString("course_id")),  rs.getString("gym_id")  ,duration,sqlDate, rs.getString("description"),none,rs.getString("street"));
                
                list.add(s);
            }
        } catch (SQLException | ParseException e) {
        	AlertFactory.getInstance().createAlert(e);
        }
		return list;
	}
	
	public String getCourseById(int id) {
		try {
			ResultSet rs = Query.getCourseName(this.st, id);
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


    
}
