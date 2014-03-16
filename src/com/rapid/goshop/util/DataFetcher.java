package com.rapid.goshop.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
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

	public String fetchNeilsonResource(String url) {
		try {
			
			 String cachedResponse = ApiCache.lookUp(url);
			 if(cachedResponse != null)
				 return cachedResponse;

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
			
			RequestConfig config = null;
			HttpHost proxy = null;
			HttpHost target = new HttpHost("nielsen.api.tibco.com", 443,
					"https");;

			
			// DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpClients.custom().setSSLSocketFactory(sslsf).build();
			if (ApplicationConstants.USE_PROXY) {
				proxy = new HttpHost(ApplicationConstants.PROXY_HOST, ApplicationConstants.PROXY_PORT, "http");
			    config = RequestConfig.custom().setProxy(proxy)
						.build();
				CredentialsProvider credsProvider = new BasicCredentialsProvider();
				NTCredentials ntCreds = new NTCredentials(
						ApplicationConstants.PROXY_UNAME,
						ApplicationConstants.PROXY_PASSWD,
						ApplicationConstants.PROXY_LOCAL_MACHINE_ID,
						ApplicationConstants.PROXY_DOMAIN);
				credsProvider.setCredentials(new AuthScope(
						ApplicationConstants.PROXY_HOST,
						ApplicationConstants.PROXY_PORT), ntCreds);
				HttpClients.custom().setDefaultCredentialsProvider(
						credsProvider);
			}
			CloseableHttpClient httpClient = HttpClients.custom()
					.setSSLSocketFactory(sslsf).build();
			;// HttpClients.createDefault();

			// httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
			// proxy);

			HttpGet getRequest = new HttpGet(url);// &" +
			
			getRequest.addHeader("User-Agent", "Shred");
			getRequest.addHeader("Accept", "application/json");
			getRequest.addHeader("Apikey",
					ApplicationConstants.NEILESN_API_KEY);
			if(ApplicationConstants.USE_PROXY)
				getRequest.setConfig(config);

			// HttpResponse response = httpClient.execute(getRequest);

			CloseableHttpResponse response = httpClient.execute(target,
					getRequest);

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
			ApiCache.persist(url, res.toString());
			return res.toString();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
		return "error";
	}
	
	public void fetchFavIconResource(String url, String resourceDownloadLocation) {
		try {
			

			
			RequestConfig config = null;
			HttpHost proxy = null;
		

			if (ApplicationConstants.USE_PROXY) {
				proxy = new HttpHost(ApplicationConstants.PROXY_HOST, ApplicationConstants.PROXY_PORT, "http");
			    config = RequestConfig.custom().setProxy(proxy)
						.build();
				CredentialsProvider credsProvider = new BasicCredentialsProvider();
				NTCredentials ntCreds = new NTCredentials(
						ApplicationConstants.PROXY_UNAME,
						ApplicationConstants.PROXY_PASSWD,
						ApplicationConstants.PROXY_LOCAL_MACHINE_ID,
						ApplicationConstants.PROXY_DOMAIN);
				credsProvider.setCredentials(new AuthScope(
						ApplicationConstants.PROXY_HOST,
						ApplicationConstants.PROXY_PORT), ntCreds);
				HttpClients.custom().setDefaultCredentialsProvider(
						credsProvider);
			}
			CloseableHttpClient httpClient = HttpClients.custom().build();
			;// HttpClients.createDefault();

			// httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
			// proxy);

			HttpGet getRequest = new HttpGet(url);// &" +
			
			if(ApplicationConstants.USE_PROXY)
				getRequest.setConfig(config);

			// HttpResponse response = httpClient.execute(getRequest);
			
			getRequest.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.117 Safari/537.36");
			getRequest.addHeader("Content-Type", "text/plain; charset=utf-8");
			getRequest.addHeader("Accept", "*/*");
			getRequest.addHeader("DNT", "1");
			getRequest.addHeader("Accept-Encoding", "gzip,deflate,sdch");
			getRequest.addHeader("Accept-Language", "en-US,en;q=0.8");

			CloseableHttpResponse response = httpClient.execute(getRequest);

			/*if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println(response.getStatusLine().getStatusCode());
				//HttpEntity entity = response.getEntity();
				//return;
			}*/
			
			HttpEntity entity = response.getEntity();
			if (entity != null) {
			    FileOutputStream fos = new java.io.FileOutputStream(resourceDownloadLocation);
			    entity.writeTo(fos);
			    fos.close();
			}

			httpClient.getConnectionManager().shutdown();


		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
