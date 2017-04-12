package database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.Statement;

public class ConnectionManager {
	
	private ConnectionMysql connection;
	private Connection conn;
	private static final String DATABASE_NAME = "noiseapp";
	private static final String SENSOR_TABLE = "sensorslist";
	private static boolean firstRun = true;
	private static final String SENSOR_LIST_QUERY = "SELECT * FROM "+SENSOR_TABLE;
	
	
	public ConnectionManager(ConnectionMysql connection) throws ClassNotFoundException{
		this.connection = connection;
		initializeDB();
	}
	
	//EACH TIME; WE HAVE TO OPEN AND CLOSE THE CONNECTION TO THE DB
	private void getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		if(firstRun){
			System.out.println("First run here");
			firstRun = false;
			conn= (Connection) DriverManager.getConnection(connection.getUrl(), connection.getUsername(), connection.getPassword());
		}
		else{ //DATABASE noiseapp NOW EXISTS, LET'S USE IT!
			System.out.println("Not even more first run");
			conn= (Connection) DriverManager.getConnection(connection.getUrl()+DATABASE_NAME, connection.getUsername(), connection.getPassword());  
		}                            
		DatabaseMetaData md=(DatabaseMetaData) conn.getMetaData();
		System.out.println("Informazioni sul driver:");
		System.out.println("Nome: "+ md.getDriverName() + "; versione: " + md.getDriverVersion());
	}
	
	private void closeConnection(){
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Error while closing the connection to the DMBS");
			e.printStackTrace();
		}
	}
	
	//PRIVATE, FOR THE INITIALIZATION OF THE DB
	private void initializeDB() throws ClassNotFoundException{
		try {
			getConnection();
			String createDatabase = "CREATE DATABASE IF NOT EXISTS "+DATABASE_NAME;
			String useDatabase = "USE " + DATABASE_NAME;
			String sensors = "CREATE TABLE IF NOT EXISTS "+SENSOR_TABLE+"(SENSOR_NAME VARCHAR(32) PRIMARY KEY, LATITUDE VARCHAR(32) NOT NULL, LONGITUDE VARCHAR(32) NOT NULL)";
			Statement stmt = (Statement)conn.createStatement();
			stmt.execute(createDatabase);
			stmt.execute(useDatabase);
			stmt.execute(sensors);
			stmt.close();
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//TO EXECUTE A GENERIC OPERATION ON THE DB
	public String executeQuery(String query){
		String result = "Query executed";
		try {
			getConnection();
			Statement stmt = (Statement) conn.createStatement();
			stmt.execute(query);
			stmt.close();
			closeConnection();
			System.out.println("Query executed");
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Something went wrong during the query execution");
			e.printStackTrace();
			result = "query failed";
		}
		return result;
	}
	
	//FOR CHECKING IF THERE IS YET THE TABLE FOR A SPECIFIC SENSOR
	public String getQueryResultForSensor(String query, String whereParam){
		String result = "";
		try{
			getConnection();
			Statement stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				String temp = rs.getString("SENSOR_NAME");
				if(temp.toLowerCase().equals(whereParam.toLowerCase())){
					result = rs.getString("SENSOR_NAME");
					System.out.println(rs.getString("SENSOR_NAME"));
					break;
				}
				System.out.println(rs.getString("SENSOR_NAME"));
			}
			stmt.close();
			closeConnection();
		}
		catch (SQLException | ClassNotFoundException e) {
			System.out.println("Something went wrong during the query execution");
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	//RETURN THE SENSOR LIST
	public JSONArray sensorList(){
		JSONArray sensorList = new JSONArray();
		//ACCESS TO THE DB
		try{
			getConnection();
			Statement stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery(SENSOR_LIST_QUERY);
			while(rs.next()){
				JSONObject temp = new JSONObject();
				String sensorName = rs.getString("SENSOR_NAME");
				String latitude = rs.getString("LATITUDE");
				String longitude = rs.getString("LONGITUDE");
				temp.put("sensorName", sensorName);
				temp.put("latitude", latitude);
				temp.put("longitude", longitude);
				sensorList.put(temp);
			}
			System.out.println(sensorList);
			
		}
		catch (SQLException | ClassNotFoundException e) {
			System.out.println("Something went wrong during the query execution");
			JSONObject error = new JSONObject();
			error.put("Error", "Something went wrong during the execution of the query");
			sensorList.put(error);
			//e.printStackTrace();  //Uncomment to see stack trace for deeper debug
			e.printStackTrace();
		}
		return sensorList;
	}
	
	//RETURN THE SENSOR'S VALUES
	public JSONArray sensorValues(String sensorName){
		JSONArray sensorValues = new JSONArray();
		//ACCESS TO THE DB
		try{
			getConnection();
			Statement stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+sensorName);
			while(rs.next()){
				JSONObject temp = new JSONObject();
				String noiseLevel = rs.getString("NOISE_LEVEL");
				String date = rs.getString("DATE");
				temp.put("noiseLevel", noiseLevel);
				temp.put("date", date);
				sensorValues.put(temp);
				}
			System.out.println(sensorValues);
				
			}catch (SQLException | ClassNotFoundException e) {
				System.out.println("Something went wrong during the query execution");
				JSONObject error = new JSONObject();
				error.put("Error", "Sensor not found or technical issue happened");
				sensorValues.put(error);
				//e.printStackTrace();  //Uncomment to see stack trace for deeper debug
			}
			return sensorValues;
		}

}