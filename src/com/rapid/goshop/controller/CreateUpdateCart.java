package com.rapid.goshop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.rapid.goshop.entities.Cart;
import com.rapid.goshop.entities.ProductList;
import com.rapid.goshop.entities.UserGroup;
import com.rapid.goshop.entities.UserInfo;
import com.rapid.goshop.util.ApplicationConstants;
import com.rapid.goshop.vo.CartVO;
import com.rapid.goshop.vo.ProductDetail;
import com.rapid.goshop.vo.UserInfoVO;

/**
 * Servlet implementation class CreateUpdateCart
 */
@WebServlet("/createupdatecart")
public final class CreateUpdateCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateUpdateCart() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserInfoVO user = (UserInfoVO) request.getSession().getAttribute(
				ApplicationConstants.DEF_USER_DATA);
		if (user == null) {
			response.sendRedirect("index.html");
			return;
		}

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(ApplicationConstants.PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		TypedQuery<UserInfo> query = em.createQuery(
				"SELECT u from UserInfo u WHERE u.email = :email",
				UserInfo.class);
		query.setParameter("email", user.getEmail());
		List<UserInfo> userList = query.getResultList();

		String createOrUpdate = request.getParameter("createOrUpdate");
		String requestJson = request.getParameter("req");

		CartVO cartVO = null;

		Gson gson = new Gson();

		if (createOrUpdate.equals("create")) {
			em.getTransaction().begin();
			cartVO = gson.fromJson(requestJson,
					CartVO.class);
			Cart cart = new Cart();
			cart.setCartActive(true);
			cart.setCartType(ApplicationConstants.CART_TYPE_PRIVATE);
			cart.setOwnerId(userList.get(0));
			List<ProductList> cartProductList = new ArrayList<ProductList>();

			for (ProductDetail product : cartVO.getProductList()) {
				ProductList cartProduct = new ProductList();
				cartProduct.setUpcCode(product.getUPC());
				cartProduct.setQuantity(product.getQuantity());
				cartProduct.setDescription(product.getDescription());
				cartProductList.add(cartProduct);

			}

			cart.setProductList(cartProductList);

			em.persist(cart);
			em.getTransaction().commit();
			
			List<ProductDetail> productList = cartVO.getProductList();

			cartVO = new CartVO();
			cartVO.setCartActive(true);
			cartVO.setCartId(cart.getCartId());
			cartVO.setCartType(cart.getCartType());
			UserInfoVO userVO = new UserInfoVO();
			userVO.setEmail(cart.getOwnerId().getEmail());
			userVO.setFirstname(cart.getOwnerId().getFirstname());
			userVO.setLastname(cart.getOwnerId().getLastname());
			userVO.setUserId(cart.getOwnerId().getUserId());
			cartVO.setOwnerId(userVO);
			cartVO.setProductList(productList);

		} else if (createOrUpdate.equals("update")) {
			
			em.getTransaction().begin();

			cartVO = gson.fromJson(requestJson, CartVO.class);
			Cart cart = em.find(Cart.class, cartVO.getCartId());
			em.refresh(cart);
			if (cartVO.getBuyerId() != null) {
				TypedQuery<UserInfo> userQuery = em.createQuery("SELECT u from UserInfo u WHERE u.email = :email",UserInfo.class);
				query.setParameter("email", cartVO.getBuyerId().getEmail());
				List<UserInfo> buyerList = query.getResultList();
				if(buyerList.size() != 0) {
				em.refresh(buyerList.get(0));
				cart.setBuyerId(buyerList.get(0));
				}
			}
			cart.setCartActive(cartVO.isCartActive());
			if (cartVO.getCartSharedWithGroup() != null) {
				UserGroup sharedWithGroup = em.find(UserGroup.class, cartVO
						.getCartSharedWithGroup().getGroupId());
				em.refresh(sharedWithGroup);
				cart.setCartSharedWithGroup(sharedWithGroup);
			}
			cart.setCartType(cartVO.getCartType());
			
			List<ProductList> cartProductList = new ArrayList<ProductList>();

			for (ProductDetail product : cartVO.getProductList()) {
				ProductList cartProduct = new ProductList();
				cartProduct.setUpcCode(product.getUPC());
				cartProduct.setQuantity(product.getQuantity());
				cartProduct.setDescription(product.getDescription());
				cartProductList.add(cartProduct);

			}

			cart.setProductList(cartProductList);
			
			
			em.persist(cart);
			em.getTransaction().commit();
		}
		
		em.close();
		emf.close();

		response.getWriter().write(gson.toJson(cartVO, CartVO.class));
	}

}
