package com.cabpooler.client;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

import com.cabpooler.model.DriverInfo;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class registerClient {
 public static void main(String[] args) {
	 try {
		 
		    ClientConfig config = new DefaultClientConfig();
		    config.getClasses().add(JacksonJaxbJsonProvider.class);
		    config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		 	
			Client client = Client.create(config);
			
			//String drivernumber = "4";
			
			//String status = "false"; 
			
			WebResource webResource = client
			   .resource(com.cabpooler.util.Constants.BASE_API_URL+"/driver/register");
			
			DriverInfo driverInfo = new DriverInfo();
			driverInfo.setUserName("rajubhai");
			driverInfo.setPassword("pass22");
			driverInfo.setName("name 22");
			driverInfo.setCarName("car22");
			driverInfo.setCarNumber("22222");
			driverInfo.setContactNumber("22222222");
			driverInfo.setLanguage("tamil");
			driverInfo.setHobbies("Watching movies");
	 
			ClientResponse response = webResource.type("application/json")
			   .post(ClientResponse.class, driverInfo);
			
			System.out.println("Output from Server .... \n"); 
			if (response.getStatus() == 201) {
				System.out.println("succesfully updated the status of the driver, status "+response.getStatus());
				System.out.println("succesfully updated the status of the driver, status "+response.getStatusInfo());
				System.out.println("message from server is "+response.getEntity(DriverInfo.class).getDriverNumber());
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
