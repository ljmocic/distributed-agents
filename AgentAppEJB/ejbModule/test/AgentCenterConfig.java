package test;

import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

public class AgentCenterConfig {

	public static String masterAddress;
	public static String nodeName;
	public static String nodeAddress;
	public static String nodePort;
	
	private static String configFile = "test/master.properties";
	
	static {
		Properties prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();           
		InputStream stream = loader.getResourceAsStream(configFile);
		try {
			prop.load(stream);
			masterAddress = prop.getProperty("master-address");
			nodeName = prop.getProperty("node-name");
			nodeAddress = prop.getProperty("node-address");
			nodePort = prop.getProperty("node-port");
			
		}catch(Exception e) {
			masterAddress = null;
			nodeName = "master";
			try {
				nodeAddress = InetAddress.getLocalHost().getHostAddress();
			}catch(Exception ee) {
				nodeAddress = "127.0.0.1";
			}
			nodePort = "8080";
		}
		
	}
}
