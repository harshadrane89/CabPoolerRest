package com.cabpooler.client;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class rideStatus_client {
 public static void main(String[] args) {
	 try {
		 
		    ClientConfig config = new DefaultClientConfig();
		    config.getClasses().add(JacksonJaxbJsonProvider.class);
		    config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		 	
			Client client = Client.create(config);
			
			String rideNumber = "17";
			
			//String status = "false"; 
			
			WebResource webResource = client
			   .resource(com.cabpooler.util.Constants.BASE_API_URL+"/ride/"+rideNumber+"/status");
			
			//String driverStatus = "{\"status\":\"true\"}";
			ClientResponse response = webResource.type("application/json")
			   .get(ClientResponse.class);
			
			System.out.println("Output from Server .... \n" + response.getStatus()); 
			//StatusType driverStatusResponse = response.getStatusInfo();
			if (response.getStatus() == 200) {
				System.out.println("message from server is "+response.getEntity(String.class)); // in json format output
			}else if(response.getStatus() == 204){
				System.out.println(" Still in progress, please wait");
			}else{
				System.out.println("failed updating the status of the driver, status "+response.getStatus());
				System.out.println("failed updating  the status of the driver, status "+response.getStatusInfo());
				System.out.println("message from server is "+response.getEntity(String.class));
				throw new RuntimeException("Failed : HTTP error code : "
				     + response.getStatus());
			}	 			
	 
		  } catch (Exception e) {
	 
			e.printStackTrace();
	 
		  }
}
}
