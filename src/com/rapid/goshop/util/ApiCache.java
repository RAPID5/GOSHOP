package com.rapid.goshop.util;

import java.io.IOException;

import jdbm.PrimaryHashMap;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;

public class ApiCache {
	
	public static synchronized String lookUp(String url) {
		RecordManager recMan = null;
		String response = null;
		try {
			recMan = RecordManagerFactory.createRecordManager("api.cache");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PrimaryHashMap<String,String> treeMap = recMan.hashMap("apicache"); 
		response = treeMap.get(url);
		try {
			recMan.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	public static synchronized void persist(String url, String response) {
		RecordManager recMan = null;
		try {
			recMan = RecordManagerFactory.createRecordManager("api.cache");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PrimaryHashMap<String,String> treeMap = recMan.hashMap("apicache"); 
		treeMap.put(url, response);
		try {
			recMan.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
