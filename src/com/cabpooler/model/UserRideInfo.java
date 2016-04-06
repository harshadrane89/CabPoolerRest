package com.cabpooler.model;

import java.sql.Timestamp;

public class UserRideInfo {
	
	private String rideNumber;
	
	private String userNumber;
	
	private String source;
	
	private String destination;
	
	private String status;
	
	private Timestamp requestTime;
	
	private double latitude_source;
	
	private double longitude_source;
	
	private double latitude_destination;
	
	private double longitude_destination;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getRideNumber() {
		return rideNumber;
	}

	public void setRideNumber(String rideNumber) {
		this.rideNumber = rideNumber;
	}

	public Timestamp getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}

	public double getLatitude_source() {
		return latitude_source;
	}

	public void setLatitude_source(double latitude_source) {
		this.latitude_source = latitude_source;
	}

	public double getLongitude_source() {
		return longitude_source;
	}

	public void setLongitude_source(double longitude_source) {
		this.longitude_source = longitude_source;
	}

	public double getLatitude_destination() {
		return latitude_destination;
	}

	public void setLatitude_destination(double latitude_destination) {
		this.latitude_destination = latitude_destination;
	}

	public double getLongitude_destination() {
		return longitude_destination;
	}

	public void setLongitude_destination(double longitude_destination) {
		this.longitude_destination = longitude_destination;
	}

	
}
