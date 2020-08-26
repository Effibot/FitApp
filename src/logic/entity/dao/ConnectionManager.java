package logic.entity.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.postgresql.ds.PGConnectionPoolDataSource;

public class ConnectionManager {
	private static final String USR = "postgres";
	private static final String PWD = "postgres";
	private final Logger logger = Logger.getLogger(getClass().getName());
	private static final int[] portNumber = { 5432 };
	private static final String DBNAME = "fitappdb";
	private static final int SCROLL_TYPE = ResultSet.TYPE_SCROLL_INSENSITIVE;
	private static final int READ_TYPE = ResultSet.CONCUR_READ_ONLY;
	private PGConnectionPoolDataSource cp;
	protected Connection con;
	protected Statement st;

	protected ConnectionManager() {
		// set up data connection for postgreSQL DB
		cp = new PGConnectionPoolDataSource();
		cp.setPortNumbers(portNumber);
		cp.setDatabaseName(DBNAME);
		cp.setUser(USR);
		cp.setPassword(PWD);
		// initialize connection and statement pointers.
		con = null;
		st = null;
		try {
			// create connection
			con = cp.getConnection();
			st = con.createStatement(SCROLL_TYPE, READ_TYPE);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public boolean checkColumnResultValidity(int n, ResultSet rs) throws SQLException {
		ResultSetMetaData rsMeta = rs.getMetaData();

		return rsMeta.getColumnCount() == n;
	}

	public boolean checkRowResultValidity(int n, ResultSet rs) throws SQLException {
		rs.first();
		int i = 0;
		do {
			i++;
		} while (rs.next());
		rs.first();
		return i == n;
	}

	public boolean checkResultValidity(int row, int col, ResultSet rs) throws SQLException {
		return checkColumnResultValidity(col, rs) && checkRowResultValidity(row, rs);
	}

	public boolean checkMinRow(int minRow, ResultSet rs) throws SQLException {
		rs.beforeFirst();
		int i = 0;
		while(rs.next()) {
			i++;
		}
		rs.first();
		return minRow <= i; 
	}
}