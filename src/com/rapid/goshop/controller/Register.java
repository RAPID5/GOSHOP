package com.rapid.goshop.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rapid.goshop.entities.Address;
import com.rapid.goshop.entities.UserInfo;
import com.rapid.goshop.util.ApplicationConstants;
import com.rapid.goshop.util.DataFetcher;
import com.rapid.goshop.vo.LatLong;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public final class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserInfo user = new UserInfo();

		user.setFirstname(request.getParameter("firstname"));
		user.setLastname(request.getParameter("lastname"));
		user.setEmail(request.getParameter("emailid"));
		user.setPassword(request.getParameter("password"));

		Address address = new Address();
		address.setCity(request.getParameter("city"));
		address.setState(request.getParameter("state"));
		address.setStreet(request.getParameter("adress"));
		address.setZip(request.getParameter("zipcode"));
		DataFetcher datafetcher = new DataFetcher();
		LatLong latlong = datafetcher.fetchLatLong(address.getStreet() + " "
				+ address.getCity() + " " + address.getState() + " "
				+ address.getZip());
		address.setLatitude((float) latlong.getLatitude());
		address.setLongitude((float) latlong.getLongitude());
		ArrayList<Address> addressList = new ArrayList<Address>();
		addressList.add(address);

		user.setAddress(addressList);

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(ApplicationConstants.PERSISTENCE_UNIT_NAME);

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
		emf.close();
		
		response.sendRedirect("index.html");

	}

}
