package com.tainweb.ssl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPostTest {

	private static final String httpUrl = "http://www.ideasound.co.kr/index.php/httpsurlconnection-httpurlconnection-get-post/";
	
	public static void main(String[] args) {
		HttpURLConnection conn = null;
		OutputStream os = null;
		BufferedReader reader = null;
		String line = null;
		
		try {
			URL url = new URL(httpUrl);
			conn = (HttpURLConnection)url.openConnection();
			
			conn.setRequestMethod("POST");
			
			conn.setRequestProperty("Authorization", "Bearer TOKEN");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");
			conn.setRequestProperty("Accept-Language", "ko-kr");
			conn.setRequestProperty("Access-Control-Allow-Origin", "*");
			conn.setRequestProperty("Content-Type", "application/json");
			
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(5000);
			
			System.out.println("url.protocol: " + url.getProtocol());
			System.out.println("httpsUrl: " + httpUrl);
			System.out.println("responseCode: " + conn.getResponseCode());
			System.out.println("responseMsg : " + conn.getResponseMessage());
			
			/*
			conn.setDoOutput(true);
			os = conn.getOutputStream();
			os.write("Hello, world !!".getBytes("utf-8"));
			os.flush();
			*/
			
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null)
				try { os.close(); } catch (Exception e) {}
			if (reader != null)
				try { reader.close(); } catch (Exception e) {}
			if (conn != null)
				try { conn.disconnect(); } catch (Exception e) {}
		}
	}
}
