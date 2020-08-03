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
	public static ResultSet getGym(Statement st, Integer id) throws SQLException {
		String sql = "select gym_id, gym_name, street from gym where manager_id = '"+id+"';";
		return st.executeQuery(sql);
	}
}