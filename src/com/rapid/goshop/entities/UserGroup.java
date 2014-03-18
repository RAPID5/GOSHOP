package com.rapid.goshop.entities;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


/**
 * Entity implementation class for Entity: UserGroup
 *
 */
@Entity
@Table(name="user_group", schema="goshop_schema")

public class UserGroup implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public UserGroup() {
		super();
	}

	@Id
	@TableGenerator(name = "Group_key_Incrementor", table = "Id_Generator", schema = "goshop_schema", 
			initialValue = 1, pkColumnName = "gen_name", valueColumnName = "gen_value", 
			pkColumnValue = "group_id_gen", allocationSize = 1)
	@GeneratedValue(strategy = TABLE, generator = "Group_key_Incrementor")
	@Column( name = "group_id" )
	private long groupId;
	
	@Column( name = "group_name" )
	private String groupName;

	@Column( name = "is_group_active" )
	private String isGroupActive;
	
	@ManyToMany
	@JoinTable(name="user_group_mapping" ,
	    joinColumns={@JoinColumn(name="group_id" , referencedColumnName="group_id")},
	    inverseJoinColumns={@JoinColumn(name="user_id" , referencedColumnName="user_id")})
	private List<UserInfo> userList = new ArrayList<UserInfo>();

	@OneToMany(mappedBy="cartSharedWithGroup")
	List<Cart> listOfCartOwnedByGroup = new ArrayList<Cart>();
	//@JoinColumn(name="cartSharedWithGroup")
	//private Cart cart;
	
	@Version
    private Long version;

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getIsGroupActive() {
		return isGroupActive;
	}

	public void setIsGroupActive(String isGroupActive) {
		this.isGroupActive = isGroupActive;
	}

	public List<UserInfo> getUserList() {
		return userList;
	}

	public void setUserList(List<UserInfo> userList) {
		this.userList = userList;
	}

	public List<Cart> getListOfCartOwnedByGroup() {
		return listOfCartOwnedByGroup;
	}

	public void setListOfCartOwnedByGroup(List<Cart> listOfCartOwnedByGroup) {
		this.listOfCartOwnedByGroup = listOfCartOwnedByGroup;
	}
	
	@Override
	public boolean equals(Object ob) {
		if(ob instanceof UserGroup) {
			UserGroup other = (UserGroup)ob;
			if(other.groupId == this.groupId)
				return true;
			else
				return false;
		}
		return false;
	}


}
