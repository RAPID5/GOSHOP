package com.rapid.goshop.vo;

import java.util.List;

public class StoreInfoVO {
	private long storeInfoId;
	private String storeName;
	private float storeRating;
	private List<StoreReviewVO> storeReviews; 
	public long getStoreInfoId() {
		return storeInfoId;
	}
	public List<StoreReviewVO> getStoreReviews() {
		return storeReviews;
	}
	public void setStoreReviews(List<StoreReviewVO> storeReviews) {
		this.storeReviews = storeReviews;
	}
	public void setStoreInfoId(long storeInfoId) {
		this.storeInfoId = storeInfoId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public float getStoreRating() {
		return storeRating;
	}
	public void setStoreRating(float storeRating) {
		this.storeRating = storeRating;
	}
	
	

}
