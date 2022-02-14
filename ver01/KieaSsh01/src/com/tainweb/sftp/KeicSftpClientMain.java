package com.tainweb.sftp;

public class KeicSftpClientMain {

	public static void main(String[] args) throws Exception {
		String host = "";
		String user = "";
		String pass = "";
		
		KeicSftpClient keicSftpClient = new KeicSftpClient(host, user, pass);
		
		String dir = "/home/kang/TEMP";
		String path = "/Users/kang-air/FILES";
		String file = "notepad.dmg";
		keicSftpClient.upload(dir, path, file);
		
		dir = "/home/kang/TEMP";
		path = "/Users/kang-air";
		file = "notepad.dmg";
		keicSftpClient.download(dir, path, file);
		
		keicSftpClient.disconnect();
		
		System.out.println(">>>>> KeicSftpClient OK!!!");
	}
}
