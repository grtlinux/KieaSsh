package com.tainweb.sftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SftpService {

	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;
	
	public String init(String host, int port, String username, String password) {
		JSch jsch = new JSch();
		try {
			session = jsch.getSession(username, host, port);
			session.setPassword(password);
			
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			
			channel = session.openChannel("sftp");
			channel.connect();
		} catch (JSchException e) {
			return "connect failure: " + e.getMessage();
		}
		channelSftp = (ChannelSftp) channel;
		
		return "success";
	}
	
	public void upload(String dir, File file) {
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			channelSftp.cd(dir);
			channelSftp.put(in, file.getName());
		} catch (SftpException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("resource")
	public String download(String dir, String downloadFilename, String path) {
		FileInputStream in = null;
		try {
			channelSftp.cd(dir);
			in = (FileInputStream) channelSftp.get(downloadFilename);
		} catch (SftpException e) {
			return "SFTP File Loading Failure: " + e.getMessage();
		}
		
		FileOutputStream out = null;
		File file = new File(path + File.separator + downloadFilename);
		try {
			int i;
			out = new FileOutputStream(file);
			while ((i = in.read()) != -1) {
				out.write(i);
			}
		} catch (IOException e) {
			return "File Dsbr Gsilute: " + e.getMessage();
		} finally {
			try {
				out.flush();
				out.close();
				in.close();
			} catch (IOException e) {
				return "File Dsbr Gsilute: " + e.getMessage();
			}
		}
		return "successs";
	}
	
	public String disconnection() {
		channelSftp.quit();
		return "success";
	}
}
