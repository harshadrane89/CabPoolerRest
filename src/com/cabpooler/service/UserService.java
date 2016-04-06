package com.cabpooler.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import com.cabpooler.model.UserInfo;
import com.cabpooler.util.DBConnection;

@Path("/user")
public class UserService {
	
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(UserInfo userInfo) throws Exception{
		
		boolean registerStatus = false;
		
		if(userInfo.getUserName().trim().length() > 0 && userInfo.getPassword().trim().length() > 0 && userInfo.getName().trim().length() > 0 && 
				userInfo.getContactNumber().trim().length() > 0 && userInfo.getLanguage().trim().length() > 0 &&
				userInfo.getHobbies().trim().length() > 0){
		
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.connectToDataBase();
			
			PreparedStatement preparedStatement = connection
					.prepareStatement("insert into cabpooler.userinfo values(null, ? ,? ,? ,? ,? ,? ,? )");
			preparedStatement.setString(1, userInfo.getUserName());
			preparedStatement.setString(2, userInfo.getPassword());
			preparedStatement.setString(3, userInfo.getName());
			preparedStatement.setString(4, userInfo.getContactNumber());
			preparedStatement.setString(5, userInfo.getLanguage());
			preparedStatement.setString(6, userInfo.getHobbies());
			preparedStatement.setString(7, userInfo.getCreditCardNumber());
			
			int registerUpdate = preparedStatement.executeUpdate();
			
			if(registerUpdate==1){
				
				preparedStatement = connection.prepareStatement("select userinfo.usernumber from cabpooler.userinfo where userinfo.username = ?");
				
				preparedStatement.setString(1, userInfo.getUserName());
				ResultSet resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()){
					userInfo.setUserNumber(resultSet.getString("usernumber"));
					registerStatus = true;
				}
				
				if(registerStatus){
					return Response.status(201).entity(userInfo).build();
				}else{
					return Response.status(500).entity(" failed registering driver please try again "+userInfo.getName()).build();
				}
			}else{
				return Response.status(500).entity(" failed registering driver please try again "+userInfo.getName()).build();
			}	
		}else{
			return Response.status(400).entity(" All fields should be entered, please try again").build();
		}
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response loginStatus(String userLogin) throws Exception { 
		
		boolean loginStatus = false;

		JSONObject jsonObject = new JSONObject(userLogin);
		String username = jsonObject.getString("username");
		String password = jsonObject.getString("password");
		
		if(username.trim().length() > 0 && password.trim().length() > 0){
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.connectToDataBase();
	
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from cabpooler.userinfo where userinfo.username = ? AND userinfo.password = ?");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
	
			ResultSet resultSet = preparedStatement.executeQuery();
			
			UserInfo userInfo = new UserInfo();
			
			while(resultSet.next()) {
				userInfo.setUserNumber(resultSet.getString("usernumber"));
				userInfo.setUserName(resultSet.getString("username"));
				userInfo.setPassword(resultSet.getString("password"));
				userInfo.setName(resultSet.getString("name"));
				userInfo.setContactNumber(resultSet.getString("contactnumber"));
				userInfo.setLanguage(resultSet.getString("language"));
				userInfo.setHobbies(resultSet.getString("hobbies"));
				userInfo.setCreditCardNumber(resultSet.getString("creditcardnumber"));
				loginStatus = true;
			}
			
			if (loginStatus) {
				 //GenericEntity<UserInfo> generic = new GenericEntity<UserInfo>(userInfo){};
				 return Response.status(200).entity(userInfo).build();					
			} else {
				return Response.status(401).entity("Login Failed for User").build();
			}
		}else{
			return Response.status(400).entity(" All fields should be entered, please try again").build();
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