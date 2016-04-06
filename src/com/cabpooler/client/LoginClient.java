package com.cabpooler.client;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

import com.cabpooler.model.UserInfo;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class LoginClient {
 public static void main(String[] args) {
	 try {
		 
		    ClientConfig config = new DefaultClientConfig();
		    config.getClasses().add(JacksonJaxbJsonProvider.class);
		    config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		 	
			Client client = Client.create(config);
	 
			WebResource webResource = client
			   .resource(com.cabpooler.util.Constants.BASE_API_URL+"/user/login");
	 
			String loginInfo = "{\"username\":\"user1990\",\"password\":\"pass1990\"}"; 
	 
			ClientResponse response = webResource.type("application/json")
			   .post(ClientResponse.class, loginInfo);
			
			System.out.println("Output from Server .... \n");
			if (response.getStatus() == 200) {
				System.out.println("succesfully updated the status of the driver, status "+response.getStatus());
				System.out.println("succesfully updated the status of the driver, status "+response.getStatusInfo());
				System.out.println("message from server is "+response.getEntity(UserInfo.class).getUserNumber());
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
