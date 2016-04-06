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
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import com.cabpooler.model.CabLocationInfo;
import com.cabpooler.util.DBConnection;

@Path("/cab")
public class CabService {

	@GET
	@Path("/{drivernumber}/locationupdate")
	@Produces("application/json")
	public Response getCabLocation(@PathParam("drivernumber") final String driverNumber) throws Exception{
		
		boolean cabLocationStatus = false; 
		
		DBConnection dbConnection = new DBConnection();
		Connection connection = dbConnection.connectToDataBase();
		
		PreparedStatement preparedStatement = connection.prepareStatement("select cablocation.latitude, cablocation.longitude from cabpooler.cablocation"
				+ " where cablocation.drivernumber = ?");
		preparedStatement.setString(1, driverNumber);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		CabLocationInfo cabLocationInfo = new CabLocationInfo();
		
		if(resultSet.next()){
			cabLocationInfo.setCabLatitude(resultSet.getDouble("latitude"));
			cabLocationInfo.setCabLongitude(resultSet.getDouble("longitude"));
			cabLocationInfo.setDriverNumber(driverNumber);
			
			cabLocationStatus = true;
		}
		
		if(cabLocationStatus){
			return Response.status(200).entity(cabLocationInfo).build();
		}else{
			return Response.status(404).entity("Error Retrieving Status of the Cab").build();
		}
	}
	
	@POST
	@Path("/{drivernumber}/location")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateCabLocation(String cabLocationInfo) throws Exception{
		System.out.println(cabLocationInfo);
		JSONObject jsonObject = new JSONObject(cabLocationInfo);
		DBConnection dbConnection = new DBConnection();
		Connection connection = dbConnection.connectToDataBase();
		
		PreparedStatement preparedStatement = connection.prepareStatement("update cabpooler.cablocation set cablocation.latitude = ?,"
				+ " cablocation.longitude = ? where cablocation.drivernumber = ?");
		preparedStatement.setString(1,( jsonObject.getString("latitude")));
		preparedStatement.setString(2, ( jsonObject.getString("longitude")));
		preparedStatement.setString(3, jsonObject.getString("driverNumber"));
		System.out.println(preparedStatement.toString());
		int updateCabLocationStatus = preparedStatement.executeUpdate();
		
		if(updateCabLocationStatus == 1){
			return Response.status(201).entity(cabLocationInfo).build();
		}else{
			return Response.status(404).entity("Error Updating location of the cab").build();
		}
	}
}
