package com.tainweb.sftp;

import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * 
 * @author kang-air
 *
 */
public class KeicExecClient {

	public static void main(String[] args) throws Exception {
		String host = "";
		int port = 22;
		String user = "";
		String pass = "";
		String cmd = "touch /home/kang/SSH.TEST";
		
		KeicExecClient keicExecClient = new KeicExecClient(host, port, user, pass);
		keicExecClient.exec(cmd);
		keicExecClient.disconnect();
		
		System.out.println(">>>>> KeicExecClient OK!!!");
	}
	
	///////////////////////////////////////////////////

	private String host;
	private int port = 22;
	private String user;
	private String pass;
	
	private JSch jsch = null;
	private Session session = null;
	private Channel channel = null;
	private ChannelExec channelExec = null;
	
	public KeicExecClient(String host, String user, String pass) throws Exception {
		this.host = host;
		this.user = user;
		this.pass = pass;
	}
	
	public KeicExecClient(String host, int port, String user, String pass) throws Exception {
		this.host = host;
		this.port = port;
		this.user = user;
		this.pass = pass;
	}
	
	private void exec(String cmd) throws Exception {
		this.jsch = new JSch();
		this.session = jsch.getSession(this.user, this.host, this.port);
		this.session.setPassword(this.pass);
		
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		this.session.setConfig(config);
		this.session.connect();
		
		this.channel = this.session.openChannel("exec");
		this.channelExec = (ChannelExec) channel;
		
		this.channelExec.setCommand(cmd);
		this.channelExec.connect();
	}
	
	public void disconnect() {
		if (this.channel != null) {
			this.channel.disconnect();
		}
		if (this.session != null) {
			this.session.disconnect();
		}
	}
}
