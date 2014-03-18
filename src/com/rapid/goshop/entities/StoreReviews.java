package com.rapid.goshop.entities;

import java.io.Serializable;

import javax.persistence.Column;

public class StoreReviews implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Column(name="user_comment")
	private String userComment;
	
	private float rating;
	
	private long user_id;

	public String getUserComment() {
		return userComment;
	}

	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	

}
