package com.rapid.goshop.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;

public class DataFetcher {

	public String fetch(String url) {
		try {

			/*
			 * SystemDefaultRoutePlanner routePlanner = new
			 * SystemDefaultRoutePlanner( ProxySelector.getDefault());
			 * CloseableHttpClient httpClient = HttpClients.custom()
			 * .setRoutePlanner(routePlanner) .build();
			 */
			SSLContextBuilder builder = new SSLContextBuilder();
			try {
				builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (KeyStoreException e) {
				e.printStackTrace();
			}
			SSLConnectionSocketFactory sslsf = null;
			try {
				sslsf = new SSLConnectionSocketFactory(builder.build());
			} catch (KeyManagementException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			
		 	HttpHost target = new HttpHost("nielsen.api.tibco.com", 443, "https");
		 	HttpHost proxy = new HttpHost("proxy.tcs.com", 8080, "http");
		 	RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
			//DefaultHttpClient httpClient = new DefaultHttpClient();
		 	HttpClients.custom().setSSLSocketFactory(
		 	        sslsf).build();
		 	CredentialsProvider credsProvider = new BasicCredentialsProvider();
		 	NTCredentials ntCreds = new NTCredentials("553566", "Rmar@2014","01hw192491", "INDIA" );
		 	credsProvider.setCredentials( new AuthScope("proxy.tcs.com",8080), ntCreds);
		 	HttpClients.custom().setDefaultCredentialsProvider(credsProvider);
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(
		 	        sslsf).build();;//HttpClients.createDefault();

		 	//httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		 	
		 	HttpGet getRequest = new HttpGet(
					url);//&" +
					//"apikey=4904-b90b6c0f-5270-4dd2-a8a6-89467027fecd");
		 	//https://nielsen.api.tibco.com:443/MyBestSegments/v1/CONNEXIONSNE?addressline=1525%20Wilson%20Blvd&city=Arlington&state=VA&zip=22209

			getRequest.addHeader("User-Agent", "Shred");
			getRequest.addHeader("Accept", "application/json");
			getRequest.addHeader("Apikey", "4904-b90b6c0f-5270-4dd2-a8a6-89467027fecd");
				getRequest.setConfig(config);
				
				
				//HttpResponse response = httpClient.execute(getRequest);
				
			  CloseableHttpResponse response = httpClient.execute(target, getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {

				return "error:" + response.getStatusLine().getStatusCode();
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			String output;
			StringBuilder res = new StringBuilder();
			System.out.println("Output from Server .... \n");
			
			while ((output = br.readLine()) != null) {
				res.append(output);
			}

			httpClient.getConnectionManager().shutdown();
			return res.toString();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
		return "error";
	}
}
