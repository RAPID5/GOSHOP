package com.rapid.goshop.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
//import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.rapid.goshop.util.ApplicationConstants;
import com.rapid.goshop.util.DataFetcher;

/**
 * Servlet implementation class FetchIcon
 */
@WebServlet("/fetchicon")
public final class FetchIcon extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchIcon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String upc = request.getParameter("upc");
		File iconFile = new File(ApplicationConstants.PROD_ICON_DNWLD_LOC + "/" + upc);
		if(!iconFile.exists()) {
			DataFetcher datFetcher = new DataFetcher();
			datFetcher.fetchFavIconResource("http://www.favico.net/" + upc + ".jpg", ApplicationConstants.PROD_ICON_DNWLD_LOC + "/" + upc);
			
		}
			
		FileInputStream fis = new FileInputStream(iconFile);
		response.setContentType("image/jpeg");
		IOUtils.copy(fis, response.getOutputStream());
		fis.close();
		
	}

}
