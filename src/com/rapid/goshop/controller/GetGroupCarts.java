package com.rapid.goshop.controller;

import java.io.IOException;
import java.lang.reflect.Type;
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
import com.google.gson.reflect.TypeToken;
import com.rapid.goshop.entities.Cart;
import com.rapid.goshop.entities.UserGroup;
import com.rapid.goshop.entities.UserInfo;
import com.rapid.goshop.util.ApplicationConstants;
import com.rapid.goshop.vo.CartVO;
import com.rapid.goshop.vo.UserInfoVO;

/**
 * Servlet implementation class GetGroupCarts
 */
@WebServlet("/getgroupcarts")
public final class GetGroupCarts extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetGroupCarts() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserInfoVO user = (UserInfoVO) request.getSession().getAttribute(
				ApplicationConstants.DEF_USER_DATA);
		if (user == null) {
			response.sendRedirect("index.html");
			return;
		}

		long groupId = Long.parseLong(request.getParameter("groupId"));

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(ApplicationConstants.PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		UserInfo userInfo = em.find(UserInfo.class, user.getUserId());
		em.refresh(userInfo);

		UserGroup userGroup = em.find(UserGroup.class, groupId);
		em.refresh(userGroup);

		TypedQuery<Cart> groupCartsQuery = em
				.createQuery(
						"SELECT c from Cart c WHERE c.ownerId != :ownerId and ( c.cartType = :cartTypeShared OR  c.cartType = :cartTypePublic)   and c.isCartActive = :isCartActive and c.cartSharedWithGroup = :cartSharedWithGroup",
						Cart.class);

		groupCartsQuery.setParameter("cartTypeShared",
				ApplicationConstants.CART_TYPE_SHARED);
		groupCartsQuery.setParameter("cartTypePublic",
				ApplicationConstants.CART_TYPE_PUBLIC);
		groupCartsQuery.setParameter("ownerId",
				userInfo);
		groupCartsQuery.setParameter("isCartActive", true);
		groupCartsQuery.setParameter("cartSharedWithGroup", userGroup);
		List<Cart> cartList = groupCartsQuery.getResultList();

		List<CartVO> allCartsVO = new ArrayList<CartVO>();

		for (Cart cart : cartList) {
			CartVO cartvo = new CartVO();
			cartvo.initialize(cart);
			allCartsVO.add(cartvo);
		}

		em.close();
		emf.close();
		Type listType = new TypeToken<List<CartVO>>() {
		}.getType();
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(allCartsVO, listType));
	}

}
