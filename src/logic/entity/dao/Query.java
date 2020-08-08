package logic.entity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Query {

	private Query() {
		throw new IllegalStateException("utility class");
	}

	public static ResultSet login(Statement st, String username, String pwd) throws SQLException {
		String sql = "select user_id, manager from users where username = '" + username + "' and password = '" + pwd
				+ "';";
		return st.executeQuery(sql);
	}

	public static ResultSet getUser(Statement st, Integer id) throws SQLException {
		String sql = "select username, email, password from users where user_id = '" + id + "';";
		return st.executeQuery(sql);
	}
	
	public static ResultSet getUserByName(Statement st, String name) throws SQLException {
		String sql = "select user_id, email, password from users where username = '" + name + "';";
		return st.executeQuery(sql);
	}
	
	public static ResultSet getGym(Statement st, Integer id) throws SQLException {
		String sql = "select gym_id, gym_name, street from gym where manager_id = '"+id+"';";
		return st.executeQuery(sql);
	}
	
	public static ResultSet getEventList(Statement st, String data, String timeStart) throws SQLException{
        String sql = "select gym_id, course_id, description,street,time_start,time_end from training_session where day ='"+data+"' and time_start between '"+timeStart+"' and '23:59:59';";
        return st.executeQuery(sql);
		
	}
	public static ResultSet getEventListByEvent(Statement st, String data, String timeStart, String event) throws SQLException{
	        String sql = "select gym_id,course_id, description,street,time_start,time_end from training_session where day ='"+data+"' and time_start between '"+timeStart+"' and '23:59:59' and course_id ='"+event+"';";
	        return st.executeQuery(sql);
			
		}
	
	public static ResultSet getGymById(Statement st, Integer id) throws SQLException {
		String sql = "select gym_name, street from gym where gym_id = '"+id+"';";
		return st.executeQuery(sql);
	}
	
	public static ResultSet getCourseName(Statement st, Integer id) throws SQLException {
		String sql = "select course_name from course where course_id = '"+id+"';";
		return st.executeQuery(sql);
	}

	public static ResultSet getGymByName(Statement st, String gym) throws SQLException {
		String sql = "select manager_id from gym where gym_name='"+gym+"';";
		return st.executeQuery(sql);
	}
}