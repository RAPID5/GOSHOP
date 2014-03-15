package com.rapid.goshop.vo;

import java.util.ArrayList;

public class StoreProductAvailList implements Comparable<StoreProductAvailList> {

	private Retailer retailer;
	private ArrayList<Product> productList;

	public Retailer getRetailer() {
		return retailer;
	}

	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}

	public ArrayList<Product> getProductList() {
		return productList;
	}

	public void setProductList(ArrayList<Product> productList) {
		this.productList = productList;
	}

	@Override
	public int compareTo(StoreProductAvailList ob) {
		StoreProductAvailList spl = (StoreProductAvailList) ob;
		if (this.productList.size() > spl.productList.size())
			return 1;
		else if (this.productList.size() < spl.productList.size())
			return -1;
		else
			return 0;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof StoreProductAvailList) {
			return ((StoreProductAvailList)o).retailer.getStoreId().equals(this.retailer.getStoreId());
		}
		else
			return false;
	}
	
}
