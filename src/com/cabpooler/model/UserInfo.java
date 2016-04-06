package com.cabpooler.model;

public class UserInfo {

	private String userNumber;

	private String userName;

	private String password;

	private String contactNumber;

	private String language;

	private String hobbies;
	
    private String creditCardNumber;
    
    private UserRideInfo userRideInfo;

	private String name;

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
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

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public UserRideInfo getUserRideInfo() {
		return userRideInfo;
	}

	public void setUserRideInfo(UserRideInfo userRideInfo) {
		this.userRideInfo = userRideInfo;
	}

	public void setName(String name) {
		this.name = name;		
	}
	
	public String getName(){
		return name;
	}
    
    
}
