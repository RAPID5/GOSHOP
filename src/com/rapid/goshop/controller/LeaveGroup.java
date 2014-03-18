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

import com.rapid.goshop.entities.UserGroup;
import com.rapid.goshop.entities.UserInfo;
import com.rapid.goshop.util.ApplicationConstants;
import com.rapid.goshop.vo.UserInfoVO;

/**
 * Servlet implementation class LeaveGroup
 */
@WebServlet("/leavegroup")
public final class LeaveGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeaveGroup() {
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

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(ApplicationConstants.PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		TypedQuery<UserInfo> query = em.createQuery(
				"SELECT u from UserInfo u WHERE u.email = :email",
				UserInfo.class);
		query.setParameter("email", user.getEmail());
		List<UserInfo> userList = query.getResultList();
		
		long groupId = Long.parseLong(request.getParameter("groupId"));
		em.getTransaction().begin();
		
		if(userList.size() != 0) {
			
			UserGroup group = em.find(UserGroup.class, groupId);
			group.getUserList().remove(userList.get(0));
			userList.get(0).getGroupList().remove(group);
			em.persist(userList.get(0));
			em.persist(group);
		}
		em.getTransaction().commit();
		
		em.close();
		emf.close();
		
		response.getWriter().write("success");
	}

}
