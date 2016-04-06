package com.cabpooler.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import com.cabpooler.model.DriverInfo;
import com.cabpooler.model.JourneyInfo;
import com.cabpooler.model.UserRideInfo;
import com.cabpooler.util.Constants;
import com.cabpooler.util.DBConnection;

@Path("/ride")
public class RideService {
	
	@GET
	@Path("/{ridenumber}/status")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRideStatus(@PathParam("ridenumber") final String rideNumber) throws Exception{
		
		boolean validRideStatus = false;
		boolean assignedRide = false;
		
		DBConnection dbConnection = new DBConnection();
		Connection connection = dbConnection.connectToDataBase();
		
		PreparedStatement preparedStatement = connection.prepareStatement("select ridequeue.status from cabpooler.ridequeue where ridequeue.ridenumber = ?");
		preparedStatement.setString(1, rideNumber);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		JourneyInfo jInfo = new JourneyInfo();
		
		if(resultSet.next()){
			
			if(resultSet.getString("status").equals(Constants.RIDE_ASSIGNED)){
				
				preparedStatement = connection.prepareStatement("select * from cabpooler.journeyinfo where ridenumber = ? ");
					
				preparedStatement.setString(1, rideNumber);
				
				ResultSet resultSetDriverInfo = preparedStatement.executeQuery();
				
				if(resultSetDriverInfo.next()){
					jInfo.setStatus(resultSetDriverInfo.getString("status"));
					jInfo.setDriverNumber(resultSetDriverInfo.getString("drivernumber"));
				}
				assignedRide = true;
			}
			validRideStatus = true;
		}
		
		if(validRideStatus && assignedRide){
			return Response.status(200).entity(jInfo).build();
		}else if(validRideStatus && !assignedRide){
			return Response.status(204).build();
		}else{
			return Response.status(404).entity("Invalid Ride Number").build();
		}
			
	}
	
	@POST
	@Path("/request")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response requestRide(String uInfo) throws Exception{
		System.out.print(uInfo);
		UserRideInfo userRideInfo = new UserRideInfo();
		JSONObject jsonObject = new JSONObject(uInfo);
		
		boolean requestRideStatus = false;
		
		DBConnection dbConnection = new DBConnection();
		Connection connection = dbConnection.connectToDataBase();
		
		PreparedStatement preparedStatement = connection.prepareStatement("insert into cabpooler.ridequeue(source,destination,usernumber,status,requesttime,latitude_destination,latitude_source,longitude_source,longitude_destination) values ( ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?)");
		preparedStatement.setString(1, jsonObject.getString("source"));
		preparedStatement.setString(2, jsonObject.getString("destination"));
		preparedStatement.setString(3, jsonObject.getString("userNumber"));
		preparedStatement.setString(4, jsonObject.getString("status"));
		preparedStatement.setDouble(5,Double.parseDouble(jsonObject.getString("latitude_destination")));
		preparedStatement.setDouble(6, Double.parseDouble(jsonObject.getString("latitude_source")));
		preparedStatement.setDouble(7, Double.parseDouble(jsonObject.getString("longitude_source")));
		preparedStatement.setDouble(8, Double.parseDouble(jsonObject.getString("longitude_destination")));
		System.out.println(preparedStatement.toString());
		
		int requestRideResult = preparedStatement.executeUpdate();
		
		if(requestRideResult==1){
			preparedStatement = connection.prepareStatement("select max(rideNumber) as rideNumber from cabpooler.ridequeue where ridequeue.usernumber = ? "
				);
			preparedStatement.setString(1,jsonObject.getString("userNumber"));
		
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				userRideInfo.setRideNumber(resultSet.getString("ridenumber"));
				requestRideStatus = true;
			}
			
			if(requestRideStatus){
				return Response.status(201).entity(userRideInfo).build();
			}else{
				return Response.status(500).entity(" failed registering driver please try again ").build();
			}
		}
		
		return null;
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchRide() throws Exception{
		boolean searchRide =  false;
		
		DBConnection dbConnection = new DBConnection();
		Connection connection = dbConnection.connectToDataBase();
		
		PreparedStatement preparedStatement = connection.prepareStatement("select ridenumber from cabpooler.ridequeue where ridequeue.status=0 ORDER BY requesttime");
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		//UserRideInfo userRideInfo = new UserRideInfo();
		
		String rideNumber = null;
		
		if(resultSet.next()){
			rideNumber = resultSet.getString("rideNumber");
			
			//userRideInfo.setRideNumber(resultSet.getString("rideNumber"));
			/*userRideInfo.setSource(resultSet.getString("source"));
			userRideInfo.setDestination(resultSet.getString("destination"));
			userRideInfo.setUserNumber(resultSet.getString("usernumber"));
			userRideInfo.setStatus(resultSet.getString("status"));
			userRideInfo.setRequestTime(resultSet.getTimestamp("requesttime"));
			userRideInfo.setLatitude_source(resultSet.getDouble("latitude_source"));
			userRideInfo.setLongitude_source(resultSet.getDouble("longitude_source"));
			userRideInfo.setLatitude_destination(resultSet.getDouble("latitude_destination"));
			userRideInfo.setLongitude_destination(resultSet.getDouble("longitude_destination"));*/
			
			searchRide = true;
		}
		
		if(searchRide){
			return Response.status(201).entity(rideNumber).build();
		}else{
			return Response.status(500).entity(" failed searching, please try again ").build();
		}
	}
	
	/*@PUT
	@Path("/{ridenumber}/status")
	@Produces(MediaType.APPLICATION_JSON)
	public Response setRideStatus(@PathParam("ridenumber") final String rideNumber) throws Exception{
		
		return null;
	}*/
	
	@POST
	@Path("/{ridenumber}/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateRide(@PathParam("ridenumber") final String rideNumber, String driverStatus) throws Exception{
		
		JSONObject jsonObject = new JSONObject(driverStatus);
		String driverNumber = jsonObject.getString("drivernumber");
		String status = jsonObject.getString("status");
		 
		DBConnection dbConnection = new DBConnection();
		Connection connection = dbConnection.connectToDataBase();
		
		PreparedStatement preparedStatement = connection.prepareStatement("update cabpooler.ridequeue set ridequeue.status = ? where ridequeue.ridenumber = ?");
		preparedStatement.setString(1, status);
		preparedStatement.setString(2, rideNumber);
		
		int updateRideStatus = preparedStatement.executeUpdate();
		
		ResultSet resultSet = null;
		ResultSet resultSetJourneyInfoKey = null;
		
		int newJourneyInsertStatus = 0;
		String journeyId = null;
		
		UserRideInfo userRideInfo = new UserRideInfo();
		JourneyInfo journeyInfo = new JourneyInfo();
		
		if(updateRideStatus == 1 ){
			if(status.equals("3")){
				preparedStatement = connection.prepareStatement("select * from cabpooler.ridequeue where ridequeue.ridenumber = ?");
				preparedStatement.setString(1, rideNumber);
				
				resultSet = preparedStatement.executeQuery();
				
				if(resultSet.next()){
					userRideInfo.setRideNumber(rideNumber);
					userRideInfo.setSource(resultSet.getString("source"));
					userRideInfo.setDestination(resultSet.getString("destination"));
					userRideInfo.setUserNumber(resultSet.getString("usernumber"));
					userRideInfo.setStatus("Cab is on the way for pickup!!");
					userRideInfo.setRequestTime(resultSet.getTimestamp("requesttime"));
					userRideInfo.setLatitude_source(resultSet.getDouble("latitude_source"));
					userRideInfo.setLongitude_source(resultSet.getDouble("longitude_source"));
					userRideInfo.setLatitude_destination(resultSet.getDouble("latitude_destination"));
					userRideInfo.setLongitude_destination(resultSet.getDouble("longitude_destination"));
					
					//preparedStatement = connection.prepareStatement("
					String sqlInsert = "insert into cabpooler.journeyinfo(ridenumber,drivernumber,usernumber,source,destination,latitude_source,longitude_source,latitude_destination,longitude_destination,distance,cost,status,starttime) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					preparedStatement = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
					

					preparedStatement.setString(1, rideNumber);
					preparedStatement.setString(2, driverNumber);
					preparedStatement.setString(3, userRideInfo.getUserNumber());
					preparedStatement.setString(4, userRideInfo.getSource());
					preparedStatement.setString(5, userRideInfo.getDestination());
					preparedStatement.setDouble(6, userRideInfo.getLatitude_source());
					preparedStatement.setDouble(7, userRideInfo.getLongitude_source());
					preparedStatement.setDouble(8, userRideInfo.getLatitude_destination());
					preparedStatement.setDouble(9, userRideInfo.getLongitude_destination());
					preparedStatement.setString(10, "100");
					preparedStatement.setString(11, "200");
					preparedStatement.setString(12, status);
					preparedStatement.setTimestamp(13, userRideInfo.getRequestTime());
					
					
					newJourneyInsertStatus = preparedStatement.executeUpdate();
					if(newJourneyInsertStatus == 1){
						resultSetJourneyInfoKey = preparedStatement.getGeneratedKeys();
						resultSetJourneyInfoKey.next();
						journeyId = resultSetJourneyInfoKey.getString(1);
						
						
						preparedStatement = connection.prepareStatement("select * from cabpooler.journeyinfo where journeyinfo.journeynumber = ?");
						preparedStatement.setString(1, journeyId);
						resultSet = preparedStatement.executeQuery();
						
						if(resultSet.next()){
							System.out.println(resultSet.getString("journeynumber"));
							journeyInfo.setJourneyNumber(resultSet.getString("journeynumber"));
							journeyInfo.setRideNumber(resultSet.getString("ridenumber"));
							journeyInfo.setDriverNumber(resultSet.getString("drivernumber"));
							journeyInfo.setUserNumber(resultSet.getString("usernumber"));
							journeyInfo.setSource(resultSet.getString("source"));
							journeyInfo.setDestination(resultSet.getString("destination"));
							journeyInfo.setDistance(resultSet.getString("distance"));
							journeyInfo.setCost(resultSet.getString("cost"));
							journeyInfo.setStartTime(resultSet.getTimestamp("starttime"));
							//journeyInfo.setEndTime(resultSet.getTimestamp("endtime"));
							journeyInfo.setStatus(resultSet.getString("status"));
							journeyInfo.setLatitude_source(resultSet.getDouble("latitude_source"));
							journeyInfo.setLongitude_source(resultSet.getDouble("longitude_source"));
							journeyInfo.setLatitude_destination(resultSet.getDouble("latitude_destination"));
							journeyInfo.setLongitude_destination(resultSet.getDouble("longitude_destination"));
							
							return Response.status(201).entity(journeyInfo).build();
						}else{
							return Response.status(500).entity("Internal Server Error While retrieving data, please try later").build();
						}
					}else{
						return Response.status(500).entity("Internal Server Error While inserting data, please try later").build();
					}
				}else{
					return Response.status(500).entity("Internal Server Error While retrieving data, please try later").build();
				}
			}else{
				return Response.status(204).build();
			}
		}else{
			return Response.status(404).entity("Wrong Information Provided, please try again").build();
		}
	}
}
