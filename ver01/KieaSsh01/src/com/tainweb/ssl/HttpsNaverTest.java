package com.tainweb.ssl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;

public class HttpsNaverTest {

	private static final String httpsUrl = "https://www.naver.com/";
	
	public static void main(String[] args) {
		HttpsURLConnection conn = null;
		InputStream in = null;
		BufferedReader reader = null;
		String line = null;
		try {
			URL url = new URL(httpsUrl);
			conn = (HttpsURLConnection) url.openConnection();
			
			// set hostname verification
			conn.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return false;
				}
			});
			
			// Exception in thread "main" javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: 
			// sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
			// IF ABOVE ERROR, ADD THE BELOW
			/*
			TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}
			}};
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			 */
			// add end
			
			// input setting
			conn.setDoInput(true);
			// output setting
			//conn.setDoOutput(true);
			//caches setting
			conn.setUseCaches(false);
			//read timeout setting
			conn.setReadTimeout(5000);
			// connection timeout setting
			conn.setConnectTimeout(5000);
			// method setting(GET/POST)
			conn.setRequestMethod("GET");
			// header setting
			conn.setRequestProperty("HeaderKey", "HeaderValue");
			
			int responseCode = conn.getResponseCode();
			System.out.println("url.protocol: " + url.getProtocol());
			System.out.println("httpsUrl: " + httpsUrl);
			System.out.println("responseCode: " + responseCode);
			System.out.println("responseMsg : " + conn.getResponseMessage());
			
			// SSL setting
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, null, null);  // no validation for now
			conn.setSSLSocketFactory(context.getSocketFactory());
			
			// connect to host
			conn.connect();
			conn.setInstanceFollowRedirects(true);
			
			// print response fro host
			if (responseCode == HttpsURLConnection.HTTP_OK) {
				in = conn.getInputStream();
			} else {
				in = conn.getErrorStream();
			}
			reader = new BufferedReader(new InputStreamReader(in));
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try { reader.close(); } catch (Exception e) {}
			if (conn != null)
				try { conn.disconnect(); } catch (Exception e) {}
		}
	}
}
