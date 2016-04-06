package com.cabpooler.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import com.cabpooler.model.DriverInfo;
import com.cabpooler.util.DBConnection;

@Path("/driver")
public class DriverService {
	
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(DriverInfo driverInfo) throws Exception{
		
		boolean registerStatus = false;
		
		if(driverInfo.getUserName().trim().length() > 0 && driverInfo.getPassword().trim().length() > 0 && driverInfo.getName().trim().length() > 0 && 
				driverInfo.getCarName().trim().length() > 0 && driverInfo.getCarNumber().trim().length() > 0 &&
				driverInfo.getContactNumber().trim().length() > 0 && driverInfo.getLanguage().trim().length() > 0 &&
				driverInfo.getHobbies().trim().length() > 0 ){
		
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.connectToDataBase();
			
			PreparedStatement preparedStatement = connection
					.prepareStatement("insert into cabpooler.driverinfo values(null, ?, ?, ?, ?, ?, ?, ?, ?, false)");
			preparedStatement.setString(1, driverInfo.getUserName());
			preparedStatement.setString(2, driverInfo.getPassword());
			preparedStatement.setString(3, driverInfo.getName());
			preparedStatement.setString(4, driverInfo.getCarName());
			preparedStatement.setString(5, driverInfo.getCarNumber());
			preparedStatement.setString(6, driverInfo.getContactNumber());
			preparedStatement.setString(7, driverInfo.getLanguage());
			preparedStatement.setString(8, driverInfo.getHobbies());
			
			int registerUpdate = preparedStatement.executeUpdate();
			
			if(registerUpdate==1){
				
				preparedStatement = connection.prepareStatement("select driverinfo.drivernumber from cabpooler.driverinfo where driverinfo.username = ?");
				
				preparedStatement.setString(1, driverInfo.getUserName());
				ResultSet resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()){
					driverInfo.setDriverNumber(resultSet.getString("drivernumber"));
					registerStatus = true;
				}
				
				if(registerStatus){
					return Response.status(201).entity(driverInfo).build();
				}else{
					return Response.status(500).entity(" failed registering driver please try again "+driverInfo.getName()).build();
				}
			}else{
				return Response.status(500).entity(" failed registering driver please try again "+driverInfo.getName()).build();
			}	
		}else{
			return Response.status(400).entity(" All fields should be entered, please try again").build();
		}
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response loginStatus(String driverLogin) throws Exception { 
		
		boolean loginStatus = false;

		JSONObject jsonObject = new JSONObject(driverLogin);
		String username = jsonObject.getString("username");
		String password = jsonObject.getString("password");
		
		if(username.trim().length() > 0 && password.trim().length() > 0){
		
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.connectToDataBase();
	
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from cabpooler.driverinfo where driverinfo.username = ? AND driverinfo.password = ?");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
	
			ResultSet resultSet = preparedStatement.executeQuery();
			
			DriverInfo driverInfo = new DriverInfo();
			
			while(resultSet.next()) {
				driverInfo.setDriverNumber(resultSet.getString("drivernumber"));
				driverInfo.setUserName(resultSet.getString("username"));
				driverInfo.setPassword(resultSet.getString("password"));
				driverInfo.setName(resultSet.getString("name"));
				driverInfo.setCarName(resultSet.getString("carname"));
				driverInfo.setCarNumber(resultSet.getString("carnumber"));
				driverInfo.setContactNumber(resultSet.getString("contactnumber"));
				driverInfo.setLanguage(resultSet.getString("language"));
				driverInfo.setHobbies(resultSet.getString("hobbies"));
				driverInfo.setActive(resultSet.getBoolean("isactive"));				
				loginStatus = true;
			}
			
			if (loginStatus) {
				 //GenericEntity<DriverInfo> generic = new GenericEntity<DriverInfo>(driverInfo){};
				 return Response.status(200).entity(driverInfo).build();					
			} else {
				return Response.status(401).entity("Login Failed for Driver").build();
			}
		}else{
			return Response.status(400).entity(" All fields should be entered, please try again").build();
		}
	}
	
	@PUT
	@Path("/{drivernumber}/status")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setDriverStatus(@PathParam("drivernumber") final String driverNumber, String status) throws Exception {
		
		JSONObject jsonObject = new JSONObject(status);
		String isActive = jsonObject.getString("status");
		
		if(isActive.equals("false") || isActive.equals("true")){
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.connectToDataBase();
			
			PreparedStatement preparedStatement = connection.prepareStatement("update cabpooler.driverinfo set driverinfo.isactive = ? where driverinfo.drivernumber = ?");
			preparedStatement.setBoolean(1, Boolean.parseBoolean(isActive));
			preparedStatement.setInt(2, (Integer.parseInt(driverNumber)));
			
			int updateStatus = preparedStatement.executeUpdate();
			
			if(updateStatus==1){
				return Response.status(200).entity("Driver: " +driverNumber+" status :"+isActive).build();
			}else{
				return Response.status(500).entity(" failed updating status for driver "+driverNumber+ " please try again").build();
			}	
		}else{
			return Response.status(400).entity(" failed updating status as bad request ").build();
		}
	}

	@GET
	@Path("/{parameter}")
	public Response responseMsg(@PathParam("parameter") String parameter,
			@DefaultValue("Nothing to say") @QueryParam("value") String value) {

		String output = "Hello from: " + parameter + " : " + value;

		return Response.status(200).entity(output).build();
	}
}