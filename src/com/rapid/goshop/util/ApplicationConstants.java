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

}