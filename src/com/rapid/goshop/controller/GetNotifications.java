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
import com.rapid.goshop.entities.UserInfo;
import com.rapid.goshop.entities.UserNotification;
import com.rapid.goshop.util.ApplicationConstants;
import com.rapid.goshop.vo.GroupVO;
import com.rapid.goshop.vo.UserInfoVO;
import com.rapid.goshop.vo.UserNotificationVO;

/**
 * Servlet implementation class GetNotifications
 */
@WebServlet("/getnotifications")
public final class GetNotifications extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetNotifications() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserInfoVO user = (UserInfoVO) request.getSession().getAttribute(
				ApplicationConstants.DEF_USER_DATA);
		if (user == null) {
			response.sendRedirect("index.html");
			return;
		}

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(ApplicationConstants.PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		UserInfo userInfo = em.find(UserInfo.class, user.getUserId());
		
		TypedQuery<UserNotification> query = em.createQuery(
				"SELECT un from UserNotification un WHERE un.user_id = :user_id",
				UserNotification.class);
		query.setParameter("user_id", userInfo);
		List<UserNotification> notificationList = query.getResultList();
		
		List<UserNotificationVO> notVoList= new ArrayList<UserNotificationVO>();
		
		for(UserNotification notification : notificationList) {
			UserNotificationVO notificationvo = new UserNotificationVO();
			notificationvo.setDateTimeForMessage(notification.getDateTimeForMessage());
			notificationvo.setMessage(notification.getMessage());
			notVoList.add(notificationvo);
		}
		
		em.close();
		emf.close();
		
		Gson gson = new Gson();
		response.setContentType("application/json");
		Type listType = new TypeToken<List<UserNotificationVO>>() {
		}.getType();
		response.getWriter().write(gson.toJson(notVoList, listType));
		
	}

}
