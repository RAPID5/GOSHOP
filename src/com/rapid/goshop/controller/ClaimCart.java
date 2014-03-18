package com.rapid.goshop.controller;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rapid.goshop.entities.Cart;
import com.rapid.goshop.entities.UserInfo;
import com.rapid.goshop.util.ApplicationConstants;
import com.rapid.goshop.vo.UserInfoVO;

/**
 * Servlet implementation class ClaimCart
 */
@WebServlet("/claimcart")
public class ClaimCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClaimCart() {
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
		
		long cartId = Long.parseLong(request.getParameter("cartId"));
		
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(ApplicationConstants.PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		UserInfo userInfo = em.find(UserInfo.class, user.getUserId());
		
		Cart cart = em.find(Cart.class, cartId);
		
		cart.setBuyerId(userInfo);
		
		em.getTransaction().begin();
		em.persist(cart);
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}

}
