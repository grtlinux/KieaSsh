import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class HelloMain {

	public static void main(String[] args) {
		String host = "idc.tainweb.com";
		int port = 22222;  // default=22
		String username = "";
		String password = "";
		
		Session session = null;
		Channel channel = null;
		
		try {
			// 1. create JSch object
			JSch jsch = new JSch();
			session = jsch.getSession(username, host, port);
			
			// 3. set password
			session.setPassword(password);
			
			// 4. set the info on session
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			
			// 5. connecting
			session.connect();
			
			// 6. open the channel of sftp
			channel = session.openChannel("exec");
			
			// 8. cast the channel to the channel of SSH
			ChannelExec channelExec = (ChannelExec) channel;
			
			System.out.println("---> Connected to " + host);
			
			channelExec.setCommand("touch /home/kang/SSH.TEST");
			channelExec.connect();
			
		} catch (JSchException e) {
			e.printStackTrace();
		} finally {
			if (channel != null) {
				channel.disconnect();
			}
			if (session != null) {
				session.disconnect();
			}
			
			System.out.println("---> Disconnected to " + host);
		}
	}
}
