package com.rapid.goshop.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductList {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "upc_code")
	private String upcCode;

	@Column(name = "description")
	private String description;

	@Column(name = "quantity")
	private int quantity;

	public String getUpcCode() {
		return upcCode;
	}

	public void setUpcCode(String upcCode) {
		this.upcCode = upcCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
