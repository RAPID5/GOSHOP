package com.rapid.goshop.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rapid.goshop.util.DataFetcher;

/**
 * Servlet implementation class Fetchproducts
 */
@WebServlet("/fetchproducts")
public final class Fetchproducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Fetchproducts() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		
		String url = "https://nielsen.api.tibco.com:443/Products/v1?search=" + URLEncoder.encode(name,"UTF-8");
		
		DataFetcher fetcher = new DataFetcher();
		String res = fetcher.fetch(url);
		response.getWriter().write(res);
	}

}
