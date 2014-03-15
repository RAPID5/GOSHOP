package com.rapid.goshop.vo;

import java.util.ArrayList;

public class StoreAvailCategory {
	
	private int numberOfMatchedProducts;
	private ArrayList<StoreProductAvailList> storeList;
	public int getNumberOfMatchedProducts() {
		return numberOfMatchedProducts;
	}
	public void setNumberOfMatchedProducts(int numberOfMatchedProducts) {
		this.numberOfMatchedProducts = numberOfMatchedProducts;
	}
	public ArrayList<StoreProductAvailList> getStoreList() {
		return storeList;
	}
	public void setStoreList(ArrayList<StoreProductAvailList> storeList) {
		this.storeList = storeList;
	}
	
	


}
