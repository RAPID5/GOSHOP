package com.rapid.goshop.entities;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


/**
 * Entity implementation class for Entity: Cart
 *
 */
@Entity
@Table(name="cart", schema="goshop_schema")

public class Cart implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Cart() {
		super();
	}
	
	@Id
	@TableGenerator(name = "Cart_key_Incrementor", table = "Id_Generator", schema = "goshop_schema", 
		initialValue = 1, pkColumnName = "gen_name", valueColumnName = "gen_value", 
		pkColumnValue = "cart_id_gen", allocationSize = 1)
	@GeneratedValue(strategy = TABLE, generator = "Cart_key_Incrementor")
	@Column( name = "cart_id" )
	private long cartId;

	@ManyToOne
	@JoinColumn(name="owner_id" , referencedColumnName="user_id")
	private UserInfo ownerId;

	@ManyToOne
	@JoinColumn(name="buyer_id" , referencedColumnName="user_id")
	private UserInfo buyerId;
	
	@ManyToOne
	@JoinColumn(name="store_info_for_cart" ,referencedColumnName="store_info_id")
	private StoreInfo storeInfoForCart;

	@ElementCollection
	@CollectionTable(name="cart_products",joinColumns=@JoinColumn(name="cart_id"))
	private List<ProductList> productList = new ArrayList<ProductList>();
	
	@Column(name = "cart_type")
	private String cartType;

	@Column(name="is_cart_active")
	private boolean isCartActive;
	
	@ManyToOne
	@JoinColumn(name="group_id" ,referencedColumnName="group_id")
	private UserGroup cartSharedWithGroup;   
		
	
	@Version
    private Long version;

	public long getCartId() {
		return cartId;
	}


	public void setCartId(long cartId) {
		this.cartId = cartId;
	}


	public UserInfo getOwnerId() {
		return ownerId;
	}


	public void setOwnerId(UserInfo ownerId) {
		this.ownerId = ownerId;
	}


	public UserInfo getBuyerId() {
		return buyerId;
	}


	public void setBuyerId(UserInfo buyerId) {
		this.buyerId = buyerId;
	}


	public StoreInfo getStoreInfoForCart() {
		return storeInfoForCart;
	}


	public void setStoreInfoForCart(StoreInfo storeInfoForCart) {
		this.storeInfoForCart = storeInfoForCart;
	}


	public List<ProductList> getProductList() {
		return productList;
	}


	public void setProductList(List<ProductList> productList) {
		this.productList = productList;
	}


	public String getCartType() {
		return cartType;
	}


	public void setCartType(String cartType) {
		this.cartType = cartType;
	}


	public boolean isCartActive() {
		return isCartActive;
	}


	public void setCartActive(boolean isCartActive) {
		this.isCartActive = isCartActive;
	}


	public UserGroup getCartSharedWithGroup() {
		return cartSharedWithGroup;
	}


	public void setCartSharedWithGroup(UserGroup cartSharedWithGroup) {
		this.cartSharedWithGroup = cartSharedWithGroup;
	}

}
