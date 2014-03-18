package com.rapid.goshop.controller;

import java.io.IOException;
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

import com.rapid.goshop.entities.UserInfo;
import com.rapid.goshop.util.ApplicationConstants;
import com.rapid.goshop.vo.UserInfoVO;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public final class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(ApplicationConstants.PERSISTENCE_UNIT_NAME);

		EntityManager em = emf.createEntityManager();
		
		TypedQuery<UserInfo> query = em.createQuery("SELECT u from UserInfo u WHERE u.email = :email and u.password = :password",UserInfo.class);
		query.setParameter("email", username);
		query.setParameter("password", password);
		List<UserInfo> userList = query.getResultList();
		em.close();
		emf.close();
		
		if(userList.size() == 1) {
			UserInfoVO user = new UserInfoVO();
			user.initialize(userList.get(0));
			request.getSession().setAttribute(ApplicationConstants.DEF_USER_DATA, user);
			response.getWriter().write("success");
		}
		else
			response.getWriter().write("failure");
	}

}
