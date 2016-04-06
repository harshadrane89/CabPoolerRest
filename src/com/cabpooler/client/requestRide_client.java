package com.cabpooler.client;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

import com.cabpooler.model.UserRideInfo;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class requestRide_client {
 public static void main(String[] args) {
	 try {
		 
		    ClientConfig config = new DefaultClientConfig();
		    config.getClasses().add(JacksonJaxbJsonProvider.class);
		    config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		 	
			Client client = Client.create(config);
			
			//String drivernumber = "4";
			
			//String status = "false"; 
			
			WebResource webResource = client
			   .resource(com.cabpooler.util.Constants.BASE_API_URL+"/ride/request");
			
//			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date date = dateFormat.parse(new Date().toString());
//			long time = date.getTime();
//			System.out.println(new Timestamp(time));
//			Timestamp timestamp = new Timestamp(time);
				
			UserRideInfo userRideInfo = new UserRideInfo();
			userRideInfo.setSource("Hyderabad");
			userRideInfo.setDestination("Visakhapatnam");
			userRideInfo.setUserNumber("1");
			userRideInfo.setStatus("0");
			//userRideInfo.setRequestTime(timestamp);
			userRideInfo.setLatitude_source(37.340513);
			userRideInfo.setLongitude_source(-121.898808);
			userRideInfo.setLatitude_destination(37.335217);
			userRideInfo.setLongitude_destination(-121.881072);			
			
			ClientResponse response = webResource.type("application/json")
			   .post(ClientResponse.class, userRideInfo);
				
				
			
			System.out.println("Output from Server .... \n"); 
			if (response.getStatus() == 201) {
				System.out.println("succesfully updated the status of the USER, status "+response.getStatus());
				System.out.println("succesfully updated the status of the USER, status "+response.getStatusInfo());
				System.out.println("message from server is "+response.getEntity(UserRideInfo.class).getRideNumber());
			}else{
				System.out.println("failed updating the status of the USER, status "+response.getStatus());
				System.out.println("failed updating  the status of the USER, status "+response.getStatusInfo());
				System.out.println("message from server is "+response.getEntity(String.class));
				throw new RuntimeException("Failed : HTTP error code : "
				     + response.getStatus());
			}
	 
			
	 
		  } catch (Exception e) {
	 
			e.printStackTrace();
	 
		  }
}
}
