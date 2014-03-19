package com.rapid.goshop.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConstants {

	private static final Properties props = new Properties();

	static {
		InputStream is = null;
		try {
			is = ApplicationConstants.class.getClassLoader()
					.getResourceAsStream("com/rapid/goshop/util/application.properties");
			props.load(is);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static final Boolean USE_PROXY = Boolean.parseBoolean(props
			.getProperty("USE_PROXY"));
	public static final String PROXY_UNAME = props.getProperty("PROXY_UNAME");
	public static final String PROXY_PASSWD = props.getProperty("PROXY_PASSWD");
	public static final String PROXY_LOCAL_MACHINE_ID = props
			.getProperty("PROXY_LOCAL_MACHINE_ID");
	public static final String PROXY_DOMAIN = props.getProperty("PROXY_DOMAIN");
	public static final String PROXY_HOST = props.getProperty("PROXY_HOST");
	public static final Integer PROXY_PORT = Integer.parseInt(props
			.getProperty("PROXY_PORT"));
	public static final String NEILESN_API_KEY = props
			.getProperty("NEILESN_API_KEY");
	public static final Integer NUM_STORE_GROUP=Integer.parseInt(props.getProperty("NUM_STORE_GROUP"));
	public static final Integer	NUM_STORE_PER_GROUP=Integer.parseInt(props.getProperty("NUM_STORE_PER_GROUP"));
	public static final String PROD_ICON_DNWLD_LOC=props.getProperty("PROD_ICON_DNWLD_LOC");
	
	public static final boolean CACHE_NEILSEN_RESPONSE = Boolean.parseBoolean(props.getProperty("CACHE_NEILSEN_RESPONSE"));
	
	public static final String PERSISTENCE_UNIT_NAME = "GoShop";
	
	public static final String DEF_USER_DATA = "USER_DATA";
	
	public static final String CART_TYPE_PRIVATE = "private";
	public static final String CART_TYPE_SHARED = "shared";
	public static final String CART_TYPE_PUBLIC = "public";
	public static final boolean CACHE_EIGHT_COUPONS_RESPONSE = Boolean.parseBoolean(props.getProperty("CACHE_EIGHT_COUPONS_RESPONSE"));
	public static final String EIGHT_COUPONS_API_KEY = props.getProperty("EIGHT_COUPONS_API_KEY");
	
	public static final String [] EIGHT_COUPONS_STORE_NAMES = props.getProperty("EIGHT_COUPONS_STORE_NAMES").split(",");
	public static final String [] EIGHT_COUPONS_STORE_IDS = props.getProperty("EIGHT_COUPONS_STORE_IDS").split(",");

}
