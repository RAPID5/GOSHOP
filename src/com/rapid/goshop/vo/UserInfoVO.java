package com.rapid.goshop.vo;

import com.rapid.goshop.entities.UserInfo;

public class UserInfoVO {
	
	private long userId;
	private String firstname;
	private String lastname;
	private String email;
	private float latitude;
	private float longitude;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	public void initialize(UserInfo user) {
		userId = user.getUserId();
		firstname = user.getFirstname();
		lastname = user.getLastname();
		email = user.getEmail();
		latitude = user.getAddress().get(0).getLatitude();
		longitude = user.getAddress().get(0).getLongitude();
	}

}
