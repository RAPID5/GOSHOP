package com.rapid.goshop.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rapid.goshop.entities.Cart;
import com.rapid.goshop.entities.StoreInfo;
import com.rapid.goshop.entities.StoreReviews;
import com.rapid.goshop.entities.UserInfo;
import com.rapid.goshop.util.ApplicationConstants;
import com.rapid.goshop.vo.UserInfoVO;

/**
 * Servlet implementation class SelectStore
 */
@WebServlet("/selectstore")
public class SelectStore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectStore() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserInfoVO user = (UserInfoVO) request.getSession().getAttribute(
				ApplicationConstants.DEF_USER_DATA);
		if (user == null) {
			response.sendRedirect("index.html");
			return;
		}
		Gson gson = new Gson();
		
		Type listType = new TypeToken<List<Long>>() {
		}.getType();
		List<Long> cartIdList = gson.fromJson(request.getParameter("cartIdList"), listType);
		
		
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(ApplicationConstants.PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		UserInfo userInfo = em.find(UserInfo.class, user.getUserId());
		
		em.getTransaction().begin();
		StoreInfo storeInfo = em.find(StoreInfo.class, Long.parseLong(request.getParameter("storeId")));
		
		float oldRating = storeInfo.getStoreRating();
		float oldSize = storeInfo.getStoreReviews().size();
		
		for(Long cartId : cartIdList) {
			Cart cart = em.find(Cart.class, cartId);
			cart.setStoreInfoForCart(storeInfo);
			cart.setCartActive(false);
			storeInfo.getListOfCartOwned().add(cart);
			em.persist(cart);
		}
		float rating = Float.parseFloat(request.getParameter("rating"));
		StoreReviews storeReview = new StoreReviews();
		storeReview.setRating(rating);
		storeReview.setUserComment(request.getParameter("comment"));
		storeReview.setUser_id(user.getUserId());
		storeInfo.getStoreReviews().add(storeReview);
		
		storeInfo.setStoreRating((oldSize/(oldSize+1)*oldRating) + rating/(oldSize+1));
		
		em.persist(storeInfo);
		
		em.getTransaction().commit();
		
		em.close();
		emf.close();
		
	}

}
