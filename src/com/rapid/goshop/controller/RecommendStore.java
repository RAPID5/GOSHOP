package com.rapid.goshop.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.rapid.goshop.util.DataFetcher;
import com.rapid.goshop.vo.Availability;
import com.rapid.goshop.vo.Product;
import com.rapid.goshop.vo.Retailer;
import com.rapid.goshop.vo.StoreAvailability;

/**
 * Servlet implementation class RecommendStore
 */
@WebServlet("/recommendstore")
public final class RecommendStore extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Object syncObject= new Object();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecommendStore() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String upcCodes[] = {"0049000050707","0036000258493","0038000596445","0016000275270","0012000504044"};
		String latitude = "41.9465";
		String longitude = "-87.7013";
		
		ArrayList<StoreAvailability> StoreAvailabilityList = new ArrayList<StoreAvailability>();
		DataFetcher dataFetcher = new DataFetcher();
		Gson gson = new Gson();
		HashMap<Retailer, ArrayList<Product>> productMap = new HashMap<Retailer, ArrayList<Product>>();
		
		for(String upcCode : upcCodes) {
			String url = "https://nielsen.api.tibco.com:443/StoreAvailability/v1?product_id="+URLEncoder.encode(upcCode,"UTF-8")  +"&lat="+URLEncoder.encode(latitude,"UTF-8")+"&long=" + URLEncoder.encode(longitude,"UTF-8");
			String jsonReply = dataFetcher.fetch(url);
			
			System.out.println(jsonReply);
			
			
			StoreAvailability storeAvailability = gson.fromJson(jsonReply, StoreAvailability.class);
			StoreAvailabilityList.add(storeAvailability);
			
			for(Availability availability : storeAvailability.getProduct().getAvailability()) {
				for(Retailer retailer : availability.getRetailer()) {
					if(productMap.containsKey(retailer)) {
						productMap.get(retailer).add(storeAvailability.getProduct());
					}
					else {
						ArrayList<Product> productList = new ArrayList<Product>();
						productList.add(storeAvailability.getProduct());
						productMap.put(retailer, productList);
					}
				}
			}
			
			synchronized (syncObject) {
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for (Entry<Retailer, ArrayList<Product>> entry : productMap.entrySet()) {
			    System.out.println(entry.getKey() + "," + entry.getValue().size());
			}
		}
		
	}

}
