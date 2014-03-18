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
import com.rapid.goshop.entities.UserGroup;
import com.rapid.goshop.entities.UserInfo;
import com.rapid.goshop.util.ApplicationConstants;
import com.rapid.goshop.vo.GroupVO;
import com.rapid.goshop.vo.UserInfoVO;

/**
 * Servlet implementation class GetGroups
 */
@WebServlet("/getgroups")
public final class GetGroups extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetGroups() {
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

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(ApplicationConstants.PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		TypedQuery<UserInfo> query = em.createQuery(
				"SELECT u from UserInfo u WHERE u.email = :email",
				UserInfo.class);
		query.setParameter("email", user.getEmail());
		List<UserInfo> userList = query.getResultList();

		List<GroupVO> groupList = new ArrayList<GroupVO>();

		if (userList.size() > 0) {

			for (UserGroup ug : userList.get(0).getGroupList()) {
				GroupVO group = new GroupVO();
				group.setGroupId(ug.getGroupId());
				group.setGroupName(ug.getGroupName());
				groupList.add(group);
			}
		}
		
		em.close();
		emf.close();

		Gson gson = new Gson();

		response.setContentType("application/json");
		Type listType = new TypeToken<List<GroupVO>>() {
		}.getType();
		response.getWriter().write(gson.toJson(groupList, listType));

	}

}
