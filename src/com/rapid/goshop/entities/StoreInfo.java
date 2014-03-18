package com.rapid.goshop.entities;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;


@Entity
@Table(name="store_info", schema="goshop_schema")
public class StoreInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@TableGenerator(name = "Store_info_key_Incrementor", table = "Id_Generator", schema = "goshop_schema", 
		initialValue = 1, pkColumnName = "gen_name", valueColumnName = "gen_value", 
		pkColumnValue = "store_info_id_gen", allocationSize = 1)
	@GeneratedValue(strategy = TABLE, generator = "Store_info_key_Incrementor")
	@Column( name = "store_info_id" )
	private long storeInfoId;
	
	@Column(name="store_name")
	private String storeName;
	
	@Column(name="store_rating")
	private float storeRating;
	
	@OneToMany(mappedBy="storeInfoForCart")
	List<Cart> listOfCartOwned = new ArrayList<Cart>();

	@ElementCollection
	@CollectionTable(name="store_reviews",joinColumns=@JoinColumn(name="store_info_id"))
	private List<StoreReviews> storeReviews = new ArrayList<StoreReviews>();

	@Version
    private Long version;

	public long getStoreInfoId() {
		return storeInfoId;
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

	public List<Cart> getListOfCartOwned() {
		return listOfCartOwned;
	}

	public void setListOfCartOwned(List<Cart> listOfCartOwned) {
		this.listOfCartOwned = listOfCartOwned;
	}

	public List<StoreReviews> getStoreReviews() {
		return storeReviews;
	}

	public void setStoreReviews(List<StoreReviews> storeReviews) {
		this.storeReviews = storeReviews;
	}
	
}
