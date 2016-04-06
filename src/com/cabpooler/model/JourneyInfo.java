package com.cabpooler.model;

import java.sql.Timestamp;

public class JourneyInfo {
	
	private String journeyNumber;
	
	private String rideNumber;
	
	private String driverNumber;
	
	private String userNumber;
	
	private String source;
	
	private String destination;
	
	private String distance;
	
	private String cost;
	
	private String status;
	
	private Timestamp startTime;
	
	private Timestamp endTime;
	
	private double latitude_source;
	
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

	private double longitude_source;
	
	private double latitude_destination;
	
	private double longitude_destination;

	

	public String getJourneyNumber() {
		return journeyNumber;
	}

	public void setJourneyNumber(String journeyNumber) {
		this.journeyNumber = journeyNumber;
	}

	public String getRideNumber() {
		return rideNumber;
	}

	public void setRideNumber(String rideNumber) {
		this.rideNumber = rideNumber;
	}

	public String getDriverNumber() {
		return driverNumber;
	}

	public void setDriverNumber(String driverNumber) {
		this.driverNumber = driverNumber;
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

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	
}
