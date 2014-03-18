package com.rapid.goshop.vo;

import java.util.Date;

public class UserNotificationVO {
	private String message;
	private Date dateTimeForMessage;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDateTimeForMessage() {
		return dateTimeForMessage;
	}
	public void setDateTimeForMessage(Date dateTimeForMessage) {
		this.dateTimeForMessage = dateTimeForMessage;
	}
	
}
