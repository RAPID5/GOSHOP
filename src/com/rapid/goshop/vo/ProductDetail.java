package com.rapid.goshop.vo;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ProductDetail {

	@SerializedName("UPC")
	@Expose
	private String uPC;
	@SerializedName("Description")
	@Expose
	private String description;
	@SerializedName("Item_Code")
	@Expose
	private String item_Code;
	@SerializedName("Module")
	@Expose
	private String module;
	@SerializedName("Rank")
	@Expose
	private String rank;
	@SerializedName("quantity")
	@Expose
	private String quantity;

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getUPC() {
		return uPC;
	}

	public void setUPC(String uPC) {
		this.uPC = uPC;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getItem_Code() {
		return item_Code;
	}

	public void setItem_Code(String item_Code) {
		this.item_Code = item_Code;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

}
