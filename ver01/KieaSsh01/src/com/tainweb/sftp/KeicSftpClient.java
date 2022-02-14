package com.tainweb.sftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * 
 * @author kang-air
 *
 */
public class KeicSftpClient {

	private String host;
	private int port = 22;
	private String user;
	private String pass;
	
	private JSch jsch = null;
	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;
	
	public KeicSftpClient(String host, String user, String pass) throws Exception {
		this.host = host;
		this.user = user;
		this.pass = pass;
		
		init();
	}
	
	public KeicSftpClient(String host, int port, String user, String pass) throws Exception {
		this.host = host;
		this.port = port;
		this.user = user;
		this.pass = pass;
		
		init();
	}
	
	private void init() throws Exception {
		this.jsch = new JSch();
		this.session = jsch.getSession(this.user, this.host, this.port);
		this.session.setPassword(this.pass);
		
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		this.session.setConfig(config);
		this.session.connect();
		
		this.channel = this.session.openChannel("sftp");
		this.channel.connect();
		
		this.channelSftp = (ChannelSftp) channel;
	}
	
	public void upload(String dir, File localFile) {
		FileInputStream in = null;
		try {
			in = new FileInputStream(localFile);
			this.channelSftp.cd(dir);
			this.channelSftp.put(in, localFile.getName());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void upload(String dir, String path, String file) {
		File localFile = new File(path + File.separator + file);
		this.upload(dir, localFile);
	}
	
	public void download(String dir, File localFile) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(localFile);
			this.channelSftp.cd(dir);
			this.channelSftp.get(localFile.getName(), out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void download(String dir, String path, String file) {
		File localFile = new File(path + File.separator + file);
		this.download(dir, localFile);
	}
	
	public void disconnect() {
		this.channelSftp.quit();
		
		if (this.channel != null) {
			this.channel.disconnect();
		}
		if (this.session != null) {
			this.session.disconnect();
		}
	}
}
