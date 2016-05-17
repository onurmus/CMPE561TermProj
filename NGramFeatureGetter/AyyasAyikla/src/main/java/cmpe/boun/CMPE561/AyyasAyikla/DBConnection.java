package cmpe.boun.CMPE561.AyyasAyikla;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class DBConnection {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/561tweets?useUnicode=true&characterEncoding=utf8";//characterEncoding=utf-8;";
	
	// Database credentials
	static final String USER = "root";
	static final String PASS = "";
	private Connection conn;
	private Statement stmt;
	
	public DBConnection(){
		startConnection();
	}	
	public void startConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void closeConnection(){
		if (conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}	
	public Connection getConn() {
		return conn;
	}
	public Statement getStmt() {
		return stmt;
	}
	
    public ResultSet getDataSet1() throws SQLException{

    	String query = "SELECT * FROM dataset1";		
		ResultSet ds1 =this.getStmt().executeQuery(query);
		
		return ds1;
	}

    public ResultSet getDataSet1Drunk() throws SQLException{

    	String query = "SELECT * FROM dataset1 where hashtag in ('drunk','drank','imdrunk')";		
		ResultSet ds1 =this.getStmt().executeQuery(query);
		
		return ds1;
	}
    
    public ResultSet getDataSet1Sober() throws SQLException{

    	String query = "SELECT * FROM dataset1 where hashtag in ('notdrunk','imnotdrunk','sober')";			
		ResultSet ds1 =this.getStmt().executeQuery(query);
		
		return ds1;
	}
    
    public ResultSet getDataSet2() throws SQLException{
		
    	String query = "SELECT * FROM dataset2v2";		
		ResultSet ds1 =this.getStmt().executeQuery(query);
		
		return ds1;
	}
    
    public ResultSet getDataSet2Drunk() throws SQLException{
		
    	String query = "SELECT * FROM `dataset2v2` WHERE hashtag in ('drunk','drank','imdrunk')";		
		ResultSet ds1 =this.getStmt().executeQuery(query);
		
		return ds1;
	}
    
    public ResultSet getDataSet2Sober() throws SQLException{
		
    	String query = "SELECT * FROM `dataset2v2` WHERE hashtag = '' LIMIT 5000";		
		ResultSet ds1 =this.getStmt().executeQuery(query);
		
		return ds1;
	}
    
    public ResultSet getDataSet3Drunk() throws SQLException{
		
    	String query = "SELECT * FROM `dataset3`";		
		ResultSet ds1 =this.getStmt().executeQuery(query);
		
		return ds1;
	}
    
    public ResultSet getDataSet3Sober() throws SQLException{
		
    	String query = "SELECT * FROM `dataset3v3` LIMIT 400";
		ResultSet ds1 =this.getStmt().executeQuery(query);
		
		return ds1;
	}
}
	
	
