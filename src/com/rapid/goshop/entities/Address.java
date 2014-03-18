package com.rapid.goshop.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Address
 *
 */
@Embeddable
public class Address implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	 private String street; 
	    private String city; 
	    private String state;
	    
	    @Column(name="zip_code")
	    private String zip;
	    
	    private float latitude;
	    private float longitude;

	    public String getStreet() {
	        return street;
	    }
	    
	    public void setStreet(String address) {
	        this.street = address;
	    }

	    public String getCity() {
	        return city;
	    }

	    public void setCity(String city) {
	        this.city = city;
	    }

	    public String getState() {
	        return state;
	    }

	    public void setState(String state) {
	        this.state = state;
	    }

	    public String getZip() {
	        return zip;
	    }

	    public void setZip(String zip) {
	        this.zip = zip;
	    }
	   
	    public String toString() {
	        return "Address street: " + getStreet() +
	               ", city: " + getCity() +
	               ", state: " + getState() +
	               ", zip: " + getZip();
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


	
	
   
}
