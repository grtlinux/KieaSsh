package com.tainweb.ssl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpsGetTest {

	private static final String httpsUrl = "https://www.google.com";
	
	public static void main(String[] args) {
		HttpsURLConnection conn = null;
		BufferedReader reader = null;
		String line = null;
		
		try {
			URL url = new URL(httpsUrl);
			conn = (HttpsURLConnection)url.openConnection();
			
			conn.setRequestMethod("GET");
			
			conn.setRequestProperty("Authorization", "Bearer TOKEN");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");
			conn.setRequestProperty("Accept-Language", "ko-kr");
			conn.setRequestProperty("Access-Control-Allow-Origin", "*");
			conn.setRequestProperty("Content-Type", "application/json");
			
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(5000);
			
			System.out.println("url.protocol: " + url.getProtocol());
			System.out.println("httpsUrl: " + httpsUrl);
			System.out.println("responseCode: " + conn.getResponseCode());
			System.out.println("responseMsg : " + conn.getResponseMessage());
			
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
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
