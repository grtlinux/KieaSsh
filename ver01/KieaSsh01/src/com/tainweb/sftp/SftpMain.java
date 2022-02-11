package com.tainweb.sftp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SftpMain {

	public static void main(String[] args) {
		try {
			String filename = null;
			String path = null;
			String host = null;
			int port = 0;
			String username = null;
			String password = null;
			String dir = null;
			
			long curr = System.currentTimeMillis();
			SimpleDateFormat sysDt = new SimpleDateFormat("yyyyMMdd");
			String sysDtFt = sysDt.format(new Date(curr));
			//String fileDt = sysDtFt;
			String fileNmDt = sysDtFt.substring(2, 8);
			
			String rtnMsg = null;
			
			try {
				//InetAddress local = null;
				InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				System.exit(2);
			}
			
			host = "";
			port = 22;
			username = "";
			password = "";
			path = "";
			filename = "" + fileNmDt;
			dir = "";
			
			rtnMsg = sftpStart(host, port, username, password, path, filename, dir);
			if (!"success".equals(rtnMsg)) {
				System.exit(3);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String sftpStart(
			String host,
			int port,
			String username,
			String password,
			String dir,
			String filename,
			String path) {
		SftpService service = new SftpService();
		String rtnMsg = "success";
		rtnMsg = service.init(host, port, username, password);
		if (!"success".equals(rtnMsg)) {
			return rtnMsg;
		}
		
		rtnMsg = service.download(dir, filename, path);
		
		service.disconnection();
		
		return rtnMsg;
	}
}
