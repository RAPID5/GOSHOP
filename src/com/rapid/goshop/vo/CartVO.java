package com.rapid.goshop.vo;

import java.util.ArrayList;
import java.util.List;

import com.rapid.goshop.entities.Cart;
import com.rapid.goshop.entities.ProductList;
import com.rapid.goshop.entities.UserInfo;

public class CartVO {
	
	private long cartId;
	private UserInfoVO ownerId;
	private UserInfoVO buyerId;
	private List<ProductDetail> productList = new ArrayList<ProductDetail>();
	private String cartType;
	private boolean isCartActive;
	private GroupVO cartSharedWithGroup;


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
	

	public void initialize(Cart cart) {
		
		if(cart.getBuyerId() != null) {
		UserInfoVO buyer = new UserInfoVO();
		buyer.setEmail(cart.getBuyerId().getEmail());
		buyer.setFirstname(cart.getBuyerId().getFirstname());
		buyer.setLastname(cart.getBuyerId().getLastname());
		buyer.setUserId(cart.getBuyerId().getUserId());
		this.setBuyerId(buyer);
		}
		
		this.setCartActive(cart.isCartActive());
		
		this.setCartId(cart.getCartId());
		
		if(cart.getCartSharedWithGroup() != null) {
		GroupVO groupVO = new GroupVO();
		groupVO.setGroupId(cart.getCartSharedWithGroup().getGroupId());
		groupVO.setGroupName(cart.getCartSharedWithGroup().getGroupName());
		this.setCartSharedWithGroup(groupVO);
		}
		
		this.setCartType(cart.getCartType());
		
		UserInfoVO owner = new UserInfoVO();
		owner.setEmail(cart.getOwnerId().getEmail());
		owner.setFirstname(cart.getOwnerId().getFirstname());
		owner.setLastname(cart.getOwnerId().getLastname());
		owner.setUserId(cart.getOwnerId().getUserId());
		this.setOwnerId(owner);
		
		for(ProductList cartProduct : cart.getProductList()) {
			ProductDetail productDetail = new ProductDetail();
			productDetail.setDescription(cartProduct.getDescription());
			productDetail.setQuantity(cartProduct.getQuantity());
			productDetail.setUPC(cartProduct.getUpcCode());
			this.productList.add(productDetail);
		}
	}
}
