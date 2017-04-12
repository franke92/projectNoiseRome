package service;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import database.ConnectionManager;
import database.ConnectionMysql;

@Path("/sound")
public class SoundLevel {
	
	private double noiseLevel = 0;
	private static final String SENSOR_TABLE = "sensorslist";
    ConnectionMysql conn = new ConnectionMysql("jdbc:mysql://localhost:3306/","YOUR_MYSQL_USERNAME","YOUR_MYSQL_PASSWORD");
	
	@Path("/hellosound")
	@GET
	@Produces("text/plain")
	public Response hello(){
		System.out.println("Some one arrives here");
		return Response.ok().entity("Sound path say hello!").build();
	}
	
	
	//Return the sensors table
	@Path("/getSensorList")
	@Produces("text/plain")
	@GET
	public Response getSensorList(){
		//OPEN CONNECTION WITH THE DB
        ConnectionManager cm;
        JSONArray sensorList = new JSONArray();
		try {
			cm = new ConnectionManager(conn);
			 sensorList = cm.sensorList();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(Status.OK).type("text/plain").entity(sensorList.toString()).build();
	}
	
	//Return the sensor's table, to get the values
	@Path("/getSensorValues")
	@Produces("text/plain")
	@GET
	public Response getSensorValues(@QueryParam("sensorName") String sensorName){
		//OPEN CONNECTION WITH THE DB
	    ConnectionManager cm;
	    JSONArray sensorValues = new JSONArray();
	    try {
			cm = new ConnectionManager(conn);
			sensorValues = cm.sensorValues(sensorName);
		}catch(ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(Status.OK).type("text/plain").entity(sensorValues.toString()).build();
	}
	
	
	//Receive the data, insert it in the right sensor's table and add the data
	@Path("/sendNoiseLevel")
	@Consumes(MediaType.APPLICATION_JSON)
	@POST
	public Response newTemperature(String noise) throws ClassNotFoundException, SQLException{
		System.out.println("sendNoiseLevel here");
		System.out.println("Received noise level : " + noise);
		//PARSING JSON DATA HERE
		JSONObject jsonObj = new JSONObject(noise);
        String noiseValue = jsonObj.getString("noiseValue");
        String sensorName = jsonObj.getString("sensorName");
        String latitude = jsonObj.getString("latitude");
        String longitude = jsonObj.getString("longitude");
        double db = Double.parseDouble(noiseValue);
        noiseLevel = Double.parseDouble(noiseValue);
        System.out.println("Parsed noise's value : " + noiseLevel);
        System.out.println("Parsed coordinates : " + latitude + ", " + longitude);
        //ACQUIRING CURRENT DATE
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Request's happend on : "+dateFormat.format(date));
        //ADD THE RESULT TO THE NOISE'S SENSOR TABLE
        ConnectionManager cm = new ConnectionManager(conn);
        //START TO USE THE DB
        //CHECK THE PRESENCE OF THE SENSOR IN THE SENSORS TABLE
        String rs = cm.getQueryResultForSensor("SELECT * FROM "+ SENSOR_TABLE +" WHERE SENSOR_NAME = '" + sensorName + "' ", sensorName);
        String result = "";
        //IF EXIST, JUST ADD THE DATA
        if(!rs.equals("")){
        	result = cm.executeQuery("INSERT INTO "+ sensorName + " VALUES ("+ db +",'"+dateFormat.format(date)+"')");
        	System.out.println("Successfully added the values in "+sensorName);
        }
        //CREATE THE TABLE FOR THE SENSOR IF NOT EXISTS
        else{
        	cm.executeQuery("CREATE TABLE IF NOT EXISTS " + sensorName + " (NOISE_LEVEL FLOAT NOT NULL, DATE VARCHAR(32) NOT NULL)");
            cm.executeQuery("INSERT INTO " + SENSOR_TABLE + " VALUES ('" + sensorName +"','"+ latitude +"','"+ longitude +"')");
        	cm.executeQuery("INSERT INTO "+ sensorName + " VALUES ("+ db +",'"+dateFormat.format(date)+"')");
        	System.out.println("Successfully created the table "+ sensorName + " and added the values in it");
        }
        return Response.status(Status.OK).type("text/plain").entity("Result of the the request : "+result).build();
	}

}
