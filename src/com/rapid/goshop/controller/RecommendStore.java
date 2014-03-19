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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rapid.goshop.entities.Cart;
import com.rapid.goshop.entities.ProductList;
import com.rapid.goshop.entities.StoreInfo;
import com.rapid.goshop.entities.UserInfo;
import com.rapid.goshop.util.ApplicationConstants;
import com.rapid.goshop.util.DataFetcher;
import com.rapid.goshop.vo.Availability;
import com.rapid.goshop.vo.CartVO;
import com.rapid.goshop.vo.Deal;
import com.rapid.goshop.vo.GroupVO;
import com.rapid.goshop.vo.Product;
import com.rapid.goshop.vo.Retailer;
import com.rapid.goshop.vo.StoreAvailCategory;
import com.rapid.goshop.vo.StoreAvailCategoryList;
import com.rapid.goshop.vo.StoreAvailability;
import com.rapid.goshop.vo.StoreProductAvailList;
import com.rapid.goshop.vo.UserInfoVO;

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
		UserInfoVO user = (UserInfoVO) request.getSession().getAttribute(
				ApplicationConstants.DEF_USER_DATA);
		if (user == null) {
			response.sendRedirect("index.html");
			return;
		}
		Gson gson = new Gson();
		
		Type listType = new TypeToken<List<Long>>() {
		}.getType();
		List<Long> cartIdList = gson.fromJson(request.getParameter("cartIdList"), listType);
		
		List<Cart> cartList = new ArrayList<Cart>();
		
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(ApplicationConstants.PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		for(Long cartId : cartIdList) {
			Cart cart = em.find(Cart.class, cartId);
			cartList.add(cart);
		}
		
		List<String> upcCodes = new ArrayList<String>();
		
		for(Cart cart : cartList) {
			for(ProductList product : cart.getProductList()) {
				upcCodes.add(product.getUpcCode());
			}
		}
		
		UserInfo userInfo = em.find(UserInfo.class, user.getUserId());
		
		String latitude = String.valueOf(userInfo.getAddress().get(0).getLatitude());
		String longitude = String.valueOf(userInfo.getAddress().get(0).getLongitude());
		System.out.println("latitude = " + latitude);
		System.out.println("longitude = " + longitude);
		//String latitude ="40.695753";
		//String longitude = "-73.780664";

		ArrayList<StoreAvailability> StoreAvailabilityList = new ArrayList<StoreAvailability>();
		DataFetcher dataFetcher = new DataFetcher();
		
		
		ArrayList<StoreProductAvailList> masterList = new ArrayList<StoreProductAvailList>();

		for (String upcCode : upcCodes) {
			System.out.println("upccode = " + upcCode);
			String url = "https://nielsen.api.tibco.com:443/StoreAvailability/v1?product_id="
					+ URLEncoder.encode(upcCode, "UTF-8")
					+ "&lat="
					+ URLEncoder.encode(latitude, "UTF-8")
					+ "&long="
					+ URLEncoder.encode(longitude, "UTF-8");
			System.out.println("url = " + url);
			String jsonReply = dataFetcher.fetchNeilsonResource(url);
			System.out.println(jsonReply);
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
			/*

			synchronized (syncObject) {
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			*/

		}

		Collections.sort(masterList, Collections.reverseOrder());

		int lastSize = -1;
		StoreAvailCategory lastStoreAvailCategory = null;
		List<StoreAvailCategory> storeAvailCategoryList = new ArrayList<StoreAvailCategory>();

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
		
		em.getTransaction().begin();
		
		for (StoreAvailCategory storeAvailCategory : storeAvailCategoryList) {
			for(StoreProductAvailList  storeprodAvailList : storeAvailCategory.getStoreList()) {
				Retailer retailer = storeprodAvailList.getRetailer();

				StoreInfo storeInfo = null;
				TypedQuery<StoreInfo> query = em.createQuery("SELECT s from StoreInfo s WHERE s.storeCode = :storeCode",StoreInfo.class);
				query.setParameter("storeCode", retailer.getStoreId());
				List<StoreInfo> storeList = query.getResultList();
				
				if(storeList.size() == 0) {
					storeInfo = new StoreInfo();
					storeInfo.setStoreCode(retailer.getStoreId());
					storeInfo.setStoreName(retailer.getStoreName());
					em.persist(storeInfo);
				}
				else {
					storeInfo = storeList.get(0);
				}
				retailer.setStoreInfo(storeInfo);
				
				String eightCouponsChainId = null;
				int index = 0;
				for(String eightCouponStoreName : ApplicationConstants.EIGHT_COUPONS_STORE_NAMES) {
					if(retailer.getRetailerName().toLowerCase().contains(eightCouponStoreName.toLowerCase())) {
						eightCouponsChainId = ApplicationConstants.EIGHT_COUPONS_STORE_IDS[index];
						break;
					}
					index++;
				}
				
				if(eightCouponsChainId != null) {
					String url = "http://api.8coupons.com/v1/getstoredeals?key=" + ApplicationConstants.EIGHT_COUPONS_API_KEY + "&chainID=" + eightCouponsChainId;
					String jsonResponse = dataFetcher.fetchEightCouponsResource(url);
					Type dealListType = new TypeToken<List<Deal>>() {
					}.getType();
					try {
					List<Deal> dealsList = gson.fromJson(jsonResponse, dealListType);
					int maxDeals = dealsList.size() < ApplicationConstants.MAX_NUMBER_OF_DEALS - 1 ? dealsList.size() -1 : ApplicationConstants.MAX_NUMBER_OF_DEALS - 1;
					retailer.setDeals(dealsList.subList(0, maxDeals));
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					
					
				}
			}
		}
		
		em.getTransaction().commit();
		
		em.close();
		emf.close();

		response.setContentType("application/json");
		Type finalListType = new TypeToken<List<StoreAvailCategory>>() {
		}.getType();
		response.getWriter()
				.write(gson.toJson(storeAvailCategoryList, finalListType));

	}

}
