package com.rapid.goshop.entities;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="user_notification", schema="goshop_schema")
public class UserNotification implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "User_notification_key_Incrementor", table = "Id_Generator", schema = "goshop_schema", 
		initialValue = 1, pkColumnName = "gen_name", valueColumnName = "gen_value", 
		pkColumnValue = "user_notification_id_gen", allocationSize = 1)
	@GeneratedValue(strategy = TABLE, generator = "User_notification_key_Incrementor")
	@Column( name = "user_notification_id" )
	private long userNotificationId;
	
	@Column(name="message")
	private String message;
	
	@Column(name="date_time_for_message")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeForMessage;
	
	@ManyToOne
	@JoinColumn(name="user_id" , referencedColumnName="user_id")
	private UserInfo user_id;

	@Version
    private Long version;

	public long getUserNotificationId() {
		return userNotificationId;
	}

	public void setUserNotificationId(long userNotificationId) {
		this.userNotificationId = userNotificationId;
	}

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

	public UserInfo getUser_id() {
		return user_id;
	}

	public void setUser_id(UserInfo user_id) {
		this.user_id = user_id;
	}

}
