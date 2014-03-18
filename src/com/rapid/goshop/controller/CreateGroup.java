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
 * Servlet implementation class CreateGroup
 */
@WebServlet("/creategroup")
public final class CreateGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateGroup() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
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
		
		Gson gson = new Gson();
		String userEmailIdListString = request.getParameter("userIdList");
		Type listType = new TypeToken<List<String>>(){}.getType();
		List<String> userEmailIdList = gson.fromJson(userEmailIdListString, listType);

		EntityManager em = emf.createEntityManager();
		
		GroupVO groupVO = new GroupVO();

		if (request.getParameter("updateOrCreate").equals("create")) {
			em.getTransaction().begin();
			UserGroup userGroup = new UserGroup();
			userGroup.setGroupName(request.getParameter("groupName"));
			userGroup.setIsGroupActive("active");
			List<UserInfo> userList = new ArrayList<UserInfo>();

			for (String uname : userEmailIdList) {
				TypedQuery<UserInfo> query = em.createQuery(
						"SELECT u from UserInfo u WHERE u.email = :email",
						UserInfo.class);
				query.setParameter("email", uname);
				List<UserInfo> groupUser = query.getResultList();
				if (groupUser.size() > 0)
					userList.add(groupUser.get(0));
			}
			userGroup.setUserList(userList);
			em.persist(userGroup);
			em.getTransaction().commit();
			
			groupVO.setGroupId(userGroup.getGroupId());
			groupVO.setGroupName(userGroup.getGroupName());

		} else if (request.getParameter("updateOrCreate").equals("update")) {
			TypedQuery<UserGroup> query = em.createQuery(
					"SELECT ug from UserGroup ug WHERE ug.groupId = :groupId",
					UserGroup.class);
			query.setParameter("groupId", Long.parseLong(request.getParameter("groupId")));
			List<UserGroup> group = query.getResultList();

			if (group.size() > 0) {
				
				em.getTransaction().begin();
				
				List<UserInfo> userList = group.get(0).getUserList();

				for (String uname : userEmailIdList) {
					TypedQuery<UserInfo> userQuery = em.createQuery(
							"SELECT u from UserInfo u WHERE u.email = :email",
							UserInfo.class);
					userQuery.setParameter("email", uname);
					List<UserInfo> groupUser = userQuery.getResultList();
					if (groupUser.size() > 0 && !userList.contains(groupUser.get(0)))
						userList.add(groupUser.get(0));
				}
				
				em.persist(group.get(0));
				em.getTransaction().commit();
			}
			
			groupVO.setGroupId(group.get(0).getGroupId());
			groupVO.setGroupName(group.get(0).getGroupName());
		}

		em.close();
		emf.close();
		
		response.setContentType("applciation/json");
		response.getWriter().write(gson.toJson(groupVO, GroupVO.class));

	}

}
