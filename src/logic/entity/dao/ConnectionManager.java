package logic.entity.dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.postgresql.ds.PGConnectionPoolDataSource;

//public class ConnectionManager {
//    private static final String USR = "postgres";
//    private static final String PWD= "postgres";
//    private final Logger logger = Logger.getLogger(getClass().getName());
//    private static final int[] portNumber = {5432};
//    private static final String DBNAME = "fitappdb";
//    private static final int[] stmtSetUp = {ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY};
//    private PGConnectionPoolDataSource cp;
//
//    private List<Map<String, Object>> table;
//
//    protected ConnectionManager() {
//        cp = new PGConnectionPoolDataSource();
//        cp.setPortNumbers(portNumber);
//        cp.setDatabaseName(DBNAME);
//        cp.setUser(USR);
//        cp.setPassword(PWD);
//    }
//
//    /**
//     * Creates a connection with the database and launch the query.
//     *
//     * @param query the query to launch.
//     * @return a linked list obtained from the function resultMapping().
//     * */
//    protected List<Map<String, Object>> connect(String query){
//        try(Connection con = cp.getConnection(); // establishing connection.
//            Statement st = con.createStatement(stmtSetUp[0], stmtSetUp[1]); // preparing the statement.
//            ResultSet rs = st.executeQuery(query)){ // executes the query.
//            if(!rs.first()) // checks if result set is empty.
//                throw new SQLException("No result found for the query:\n\t"+query);
//            return resultMapping(rs);
//        } catch (SQLException e){
//            logger.log(Level.SEVERE, e.getMessage(), e);
//        }
//        return Collections.emptyList();
//    }
//
//
//    /**
//     * Converts the result given by a query and storing it in a linked list
//     * creating a table-like object.
//     *
//     * @param rs a ResultSet object to be converted in a table-like structure
//     * @return a linked list where each record of the list contains the whole
//     * information of one ResultSet cursor.
//     *
//     * @exception SQLException if a database access error occurs
//     * or this method is called on a closed connection
//     * */
//    protected List<Map<String, Object>> resultMapping (ResultSet rs) throws SQLException {
//        this.table = new ArrayList<>();
//        ResultSetMetaData rsMeta = rs.getMetaData();
//        String colName;
//        do{
//            int numCol = rsMeta.getColumnCount();
//            Map<String, Object> map = new HashMap<>();
//            for (int i = 1; i <= numCol; i++) {
//                colName = rsMeta.getColumnName(i);
//                map.put(colName, rs.getObject(i));
//            }
//            table.add(map);
//        } while(rs.next());
//        return this.table;
//    }
//
//    public List<Map<String, Object>> getTable() {
//        return table;
//    }
//
//    public void setTable(List<Map<String, Object>> table) {
//        this.table = table;
//    }
//}
public class ConnectionManager {
    private static final String USR = "postgres";
    private static final String PWD= "postgres";
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static final int[] portNumber = {5432};
    private static final String DBNAME = "fitappdb";
    private static final int SCROLL_TYPE = ResultSet.TYPE_SCROLL_INSENSITIVE;
    private static final int READ_TYPE = ResultSet.CONCUR_READ_ONLY;
    private PGConnectionPoolDataSource cp;
    protected Connection con;
    protected  Statement st;
    //private List<Map<String, Object>> table;
    private static ConnectionManager instance;

    protected static synchronized ConnectionManager getInstance(){
        if(ConnectionManager.instance == null){
            ConnectionManager.instance = new ConnectionManager();
        }
        return ConnectionManager.instance;
    }
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
        try{
        	// create connection
            con = cp.getConnection();
            st = con.createStatement(SCROLL_TYPE, READ_TYPE);
        } catch(SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
    public boolean checkColumnResultValidity(int n, ResultSet rs) throws SQLException {
		ResultSetMetaData rsMeta = rs.getMetaData();
		boolean b = rsMeta.getColumnCount() == n;
		System.out.println("b = "+b);
		return b;
	}
    public boolean checkRowResultValidity(int n, ResultSet rs) throws SQLException {
		rs.first();
		int i = 0;
		do {
			i++;
		}while(rs.next());
		rs.first();
		System.out.println(i);
		return i==n;
	}
    public boolean checkResultValidity(int row, int col, ResultSet rs) throws SQLException{
    	return checkColumnResultValidity(col, rs) && checkRowResultValidity(row, rs);
    }
}