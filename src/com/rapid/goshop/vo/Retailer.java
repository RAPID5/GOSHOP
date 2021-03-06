
package com.rapid.goshop.vo;

import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rapid.goshop.entities.StoreInfo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class Retailer {

    @SerializedName("RetailerName")
    @Expose
    private String retailerName;
    @SerializedName("StoreId")
    @Expose
    private String storeId;
    @SerializedName("Locations")
    @Expose
    private Locations locations;
    @SerializedName("StoreName")
    @Expose
    private String storeName;
    @SerializedName("StoreNumber")
    @Expose
    private String storeNumber;
    public List<Deal> getDeals() {
		return deals;
	}

	public void setDeals(List<Deal> deals) {
		this.deals = deals;
	}

	@SerializedName("Distance")
    @Expose
    private String distance;
    @SerializedName("storeInfo")
    @Expose
    private StoreInfo storeInfo;
    @SerializedName("deals")
    @Expose
    private List<Deal> deals;
    

    public StoreInfo getStoreInfo() {
		return storeInfo;
	}

	public void setStoreInfo(StoreInfo storeInfo) {
		this.storeInfo = storeInfo;
	}

	public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Locations getLocations() {
        return locations;
    }

    public void setLocations(Locations locations) {
        this.locations = locations;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return storeId.hashCode();
    }

    @Override
    public boolean equals(Object other) {
    	if(other instanceof Retailer)
    		return this.storeId.equals(((Retailer)other).storeId);
        else
        	return false;
    }

}
