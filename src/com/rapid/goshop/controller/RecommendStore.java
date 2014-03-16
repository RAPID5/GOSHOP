package com.rapid.goshop.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rapid.goshop.util.ApplicationConstants;
import com.rapid.goshop.util.DataFetcher;
import com.rapid.goshop.vo.Availability;
import com.rapid.goshop.vo.Product;
import com.rapid.goshop.vo.Retailer;
import com.rapid.goshop.vo.StoreAvailCategory;
import com.rapid.goshop.vo.StoreAvailCategoryList;
import com.rapid.goshop.vo.StoreAvailability;
import com.rapid.goshop.vo.StoreProductAvailList;

/**
 * Servlet implementation class RecommendStore
 */
@WebServlet("/recommendstore")
public final class RecommendStore extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Object syncObject = new Object();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecommendStore() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String upcCodes[] = { "0049000050707", "0036000258493",
				"0038000596445", "0016000275270", "0012000504044" };
		//String latitude = "41.9465";
		//String longitude = "-87.7013";
		String latitude ="40.695753";
		String longitude = "-73.780664";

		ArrayList<StoreAvailability> StoreAvailabilityList = new ArrayList<StoreAvailability>();
		DataFetcher dataFetcher = new DataFetcher();
		Gson gson = new Gson();
		
		ArrayList<StoreProductAvailList> masterList = new ArrayList<StoreProductAvailList>();

		for (String upcCode : upcCodes) {
			String url = "https://nielsen.api.tibco.com:443/StoreAvailability/v1?product_id="
					+ URLEncoder.encode(upcCode, "UTF-8")
					+ "&lat="
					+ URLEncoder.encode(latitude, "UTF-8")
					+ "&long="
					+ URLEncoder.encode(longitude, "UTF-8");
			String jsonReply = dataFetcher.fetchNeilsonResource(url);
			StoreAvailability storeAvailability = null;
            try {
			storeAvailability = gson.fromJson(jsonReply,
					StoreAvailability.class);
            }
            catch(Exception e) {
            	System.out.println(jsonReply);
            	continue;
            }
			StoreAvailabilityList.add(storeAvailability);
			for (Availability availability : storeAvailability.getProduct()
					.getAvailability()) {
				for (Retailer retailer : availability.getRetailer()) {
					int pos = -1;
					StoreProductAvailList storeProductAvailList = new StoreProductAvailList();
					storeProductAvailList.setRetailer(retailer);
					storeProductAvailList.setProductList(new ArrayList<Product>());
					if((pos = masterList.indexOf(storeProductAvailList)) < 0)  {
						masterList.add(storeProductAvailList);
					}
					else {
						storeProductAvailList = masterList.get(pos);
					}
					storeProductAvailList.getProductList().add(storeAvailability.getProduct());
				}
			}

			storeAvailability.getProduct().setAvailability(null);

			synchronized (syncObject) {
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		Collections.sort(masterList, Collections.reverseOrder());

		int lastSize = -1;
		StoreAvailCategory lastStoreAvailCategory = null;
		ArrayList<StoreAvailCategory> storeAvailCategoryList = new ArrayList<StoreAvailCategory>();

		for (StoreProductAvailList storeProductAvailList : masterList) {
			if (lastSize == storeProductAvailList.getProductList().size()) {
				lastStoreAvailCategory.getStoreList()
						.add(storeProductAvailList);
			} else {
				if (storeAvailCategoryList.size() >= ApplicationConstants.NUM_STORE_GROUP)
					break;
				lastSize = storeProductAvailList.getProductList().size();
				lastStoreAvailCategory = new StoreAvailCategory();
				lastStoreAvailCategory.setNumberOfMatchedProducts(lastSize);
				lastStoreAvailCategory
						.setStoreList(new ArrayList<StoreProductAvailList>());
				lastStoreAvailCategory.getStoreList()
						.add(storeProductAvailList);
				storeAvailCategoryList.add(lastStoreAvailCategory);
			}
		}

		for (StoreAvailCategory storeAvailCategory : storeAvailCategoryList) {
			Collections.sort(storeAvailCategory.getStoreList(),
					new Comparator<StoreProductAvailList>() {

						@Override
						public int compare(StoreProductAvailList arg0,
								StoreProductAvailList arg1) {
							double storeProductAvailListRetailerDistance1 = Double
									.parseDouble(((StoreProductAvailList) arg0)
											.getRetailer().getDistance());
							double storeProductAvailListRetailerDistance2 = Double
									.parseDouble(((StoreProductAvailList) arg1)
											.getRetailer().getDistance());

							if (storeProductAvailListRetailerDistance1 > storeProductAvailListRetailerDistance2)
								return 1;
							else if (storeProductAvailListRetailerDistance1 < storeProductAvailListRetailerDistance2)
								return -1;
							else
								return 0;
						}

					});
			int toIndex = ApplicationConstants.NUM_STORE_PER_GROUP > storeAvailCategory
					.getStoreList().size() ? storeAvailCategory.getStoreList()
					.size() : ApplicationConstants.NUM_STORE_PER_GROUP;
			storeAvailCategory
					.setStoreList(new ArrayList<StoreProductAvailList>(
							storeAvailCategory.getStoreList().subList(0,
									toIndex)));
		}

		Gson gsonConvertor = new Gson();
		response.setContentType("application/json");
		response.getWriter()
				.write(gsonConvertor.toJson(storeAvailCategoryList));

	}

}
