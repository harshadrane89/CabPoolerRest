package com.cabpooler.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

import com.cabpooler.model.JourneyInfo;
import com.cabpooler.util.DBConnection;

@Path("/journey")
public class JourneyService {
	
	@GET
	@Path("/{ridenumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJourneyInfo(@PathParam("ridenumber") final String rideNumber) throws Exception{
		
		boolean rideStatus = false;
		
		DBConnection dbConnection = new DBConnection();
		Connection connection = dbConnection.connectToDataBase();
		
		PreparedStatement preparedStatement = connection
				.prepareStatement("select * from cabpooler.journeyinfo where journeyinfo.ridenumber = ?");
		preparedStatement.setString(1, rideNumber);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		JourneyInfo journeyInfo = new JourneyInfo();
		
		while(resultSet.next()){
			rideStatus = true;
			
			journeyInfo.setJourneyNumber(resultSet.getString("journeynumber"));
			journeyInfo.setRideNumber(resultSet.getString("ridenumber"));
			journeyInfo.setDriverNumber(resultSet.getString("drivernumber"));
			journeyInfo.setUserNumber(resultSet.getString("usernumber"));
			journeyInfo.setSource(resultSet.getString("source"));
			journeyInfo.setCost(resultSet.getString("cost"));
			journeyInfo.setDistance(resultSet.getString("distance"));
			journeyInfo.setStatus(resultSet.getString("status"));
			journeyInfo.setDestination(resultSet.getString("destination"));
			journeyInfo.setStartTime(resultSet.getTimestamp("starttime"));
			journeyInfo.setEndTime(resultSet.getTimestamp("endtime"));
		}
		
		if (rideStatus) {
			 return Response.status(200).entity(journeyInfo).build();					
		} else {
			return Response.status(404).entity("Invalid Ride Number").build();
		}
	}
	
	@POST
	@Path("/{ridenumber}/mileage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMileage(@PathParam("ridenumber") final String rideNumber, String mileage) throws Exception{
		
		JSONObject jsonObject = new JSONObject(mileage);
		mileage = jsonObject.getString("mileage");
		
		DBConnection dbConnection = new DBConnection();
		Connection connection = dbConnection.connectToDataBase();
		
		PreparedStatement preparedStatement = connection.prepareStatement("update cabpooler.journeyinfo set journeyinfo.distance=? where journeyinfo.ridenumber=?");
		preparedStatement.setString(1, mileage);
		preparedStatement.setString(2, rideNumber);
		
		int updateCabLocationStatus = preparedStatement.executeUpdate();
		
		boolean journeyUpdate = false;
		
		if(updateCabLocationStatus == 1){
			preparedStatement = connection
					.prepareStatement("select * from cabpooler.journeyinfo where journeyinfo.ridenumber = ?");
			preparedStatement.setString(1, rideNumber);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			JourneyInfo journeyInfo = new JourneyInfo();
			
			while(resultSet.next()){
				journeyUpdate = true;
				
				journeyInfo.setJourneyNumber(resultSet.getString("journeynumber"));
				journeyInfo.setRideNumber(resultSet.getString("ridenumber"));
				journeyInfo.setDriverNumber(resultSet.getString("drivernumber"));
				journeyInfo.setUserNumber(resultSet.getString("usernumber"));
				journeyInfo.setSource(resultSet.getString("source"));
				journeyInfo.setCost(resultSet.getString("cost"));
				journeyInfo.setDistance(resultSet.getString("distance"));
				journeyInfo.setStatus(resultSet.getString("status"));
				journeyInfo.setDestination(resultSet.getString("destination"));
				journeyInfo.setStartTime(resultSet.getTimestamp("starttime"));
				journeyInfo.setEndTime(resultSet.getTimestamp("endtime"));
			}
			if(journeyUpdate){
				return Response.status(200).entity(journeyInfo).build();
			}else{
				return Response.status(204).build();
			}
		}else{
			return Response.status(404).entity("Error Updating mileage of journey").build();
		}
	}
	
	@POST
	@Path("/{ridenumber}/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateJourney(@PathParam("ridenumber") final String rideNumber, String driverStatus) throws Exception{
		
		JSONObject jsonObject = new JSONObject(driverStatus);
		String status = jsonObject.getString("status");
		 
		DBConnection dbConnection = new DBConnection();
		Connection connection = dbConnection.connectToDataBase();
		
		PreparedStatement preparedStatement = connection.prepareStatement("update cabpooler.journeyinfo set journeyinfo.status = ? where journeyinfo.rideNumber = ?");
		preparedStatement.setString(1, status);
		preparedStatement.setString(2, rideNumber);
		int update_journeyStatus = preparedStatement.executeUpdate();
		
		if(update_journeyStatus == 1){
			return Response.status(200).entity("Successfully updated Journey Status ").build();
		}else{
			return Response.status(500).entity("Failed - Internal Server Error").build();
		}
	}
}
