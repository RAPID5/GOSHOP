package com.rapid.goshop.entities;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: UserInfo
 *
 */
@Entity
@Table(name="user_info", schema="goshop_schema")

public class UserInfo implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public UserInfo() {
		super();
	}
	
	@Id
	@TableGenerator(name = "User_Info_key_Incrementor", table = "Id_Generator", schema = "goshop_schema", 
		initialValue = 1, pkColumnName = "gen_name", valueColumnName = "gen_value", 
		pkColumnValue = "user_id_gen", allocationSize = 1)
	@GeneratedValue(strategy = TABLE, generator = "User_Info_key_Incrementor")
	@Column( name = "user_id" )
	private long userId;
	
	@Column( name = "firstname" )
	private String firstname;
	
	@Column( name = "lastname" )
	private String lastname;
	
	@Column( name = "email" , unique=true )
	private String email;
	
	@Column( name = "password" )
	private String password;
	
	@ElementCollection
	@CollectionTable(name="user_address",joinColumns=@JoinColumn(name="user_id"))
	private List<Address> address = new ArrayList<Address>();
   
	@ManyToMany(mappedBy="userList")
	private List<UserGroup> groupList = new ArrayList<UserGroup>();

	@OneToMany(mappedBy="ownerId")
	List<Cart> listOfCartOwned = new ArrayList<Cart>();

	@OneToMany(mappedBy="buyerId")
	List<Cart> listOfCartBought = new ArrayList<Cart>();

	@OneToMany(mappedBy="user_id")
	List<UserNotification> listOfNotificationForUser = new ArrayList<UserNotification>();
	
	@Version
    private Long version;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public List<UserGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<UserGroup> groupList) {
		this.groupList = groupList;
	}

	public List<Cart> getListOfCartOwned() {
		return listOfCartOwned;
	}

	public void setListOfCartOwned(List<Cart> listOfCartOwned) {
		this.listOfCartOwned = listOfCartOwned;
	}

	public List<Cart> getListOfCartBought() {
		return listOfCartBought;
	}

	public void setListOfCartBought(List<Cart> listOfCartBought) {
		this.listOfCartBought = listOfCartBought;
	}

	public List<UserNotification> getListOfNotificationForUser() {
		return listOfNotificationForUser;
	}

	public void setListOfNotificationForUser(
			List<UserNotification> listOfNotificationForUser) {
		this.listOfNotificationForUser = listOfNotificationForUser;
	}
}
