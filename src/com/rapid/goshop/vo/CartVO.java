package com.rapid.goshop.vo;

import java.util.ArrayList;
import java.util.List;

import com.rapid.goshop.entities.Cart;
import com.rapid.goshop.entities.UserInfo;

public class CartVO {
	
	private long cartId;
	private UserInfoVO ownerId;
	private UserInfoVO buyerId;
	private List<ProductDetail> productList = new ArrayList<ProductDetail>();
	private String cartType;
	private boolean isCartActive;
	private GroupVO cartSharedWithGroup;
	private StoreInfoVO storeInfoVo;
	public StoreInfoVO getStoreInfoVo() {
		return storeInfoVo;
	}
	public void setStoreInfoVo(StoreInfoVO storeInfoVo) {
		this.storeInfoVo = storeInfoVo;
	}
	public long getCartId() {
		return cartId;
	}
	public UserInfoVO getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(UserInfoVO buyerId) {
		this.buyerId = buyerId;
	}
	public void setCartId(long cartId) {
		this.cartId = cartId;
	}
	public UserInfoVO getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(UserInfoVO ownerId) {
		this.ownerId = ownerId;
	}
	public List<ProductDetail> getProductList() {
		return productList;
	}
	public void setProductList(List<ProductDetail> productList) {
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
	public GroupVO getCartSharedWithGroup() {
		return cartSharedWithGroup;
	}
	public void setCartSharedWithGroup(GroupVO cartSharedWithGroup) {
		this.cartSharedWithGroup = cartSharedWithGroup;
	}
	

}
