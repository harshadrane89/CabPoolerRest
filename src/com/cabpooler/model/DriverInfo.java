package com.cabpooler.model;

public class DriverInfo {
	
	private String driverNumber;
	
	private String userName;
	
	private String password;
	
	private String carName;

	private String carNumber;
	
	private String contactNumber;
	
	private String language;
	
	private String hobbies;
	
	private boolean isActive;
	
	private JourneyInfo driverRideInfo;

	private String name;
	
	private CabLocationInfo cabLocationInfo;

	public String getDriverNumber() {
		return driverNumber;
	}

	public void setDriverNumber(String driverNumber) {
		this.driverNumber = driverNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setName(String name) {
		this.name = name;		
	}
	
	public String getName(){
		return name;
	}

	public JourneyInfo getDriverRideInfo() {
		return driverRideInfo;
	}

	public void setDriverRideInfo(JourneyInfo driverRideInfo) {
		this.driverRideInfo = driverRideInfo;
	}
	
	public CabLocationInfo getCabLocationInfo() {
		return cabLocationInfo;
	}

	public void setCabLocationInfo(CabLocationInfo cabLocationInfo) {
		this.cabLocationInfo = cabLocationInfo;
	}
}
