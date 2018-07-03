package utils;

import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.util.Properties;

import javax.management.ObjectName;

public class AgentCenterConfig {

	public static String masterAddress;
	public static String nodeName;
	public static String nodeAddress;
	public static String nodePort;
	
	private static String configFile = "utils/master.properties";
	
	static {
		Properties prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();           
		InputStream stream = loader.getResourceAsStream(configFile);
		try {
			prop.load(stream);
			masterAddress = prop.getProperty("master-address");
			nodeName = prop.getProperty("node-name");
			
		}catch(Exception e) {
			masterAddress = null;
			nodeName = "master";
			
		}
		
		try {
			nodePort = ManagementFactory.getPlatformMBeanServer().getAttribute(new ObjectName("jboss.as:socket-binding-group=standard-sockets,socket-binding=http"), "port").toString(); 
	
			nodeAddress = (String)ManagementFactory.getPlatformMBeanServer().getAttribute(new ObjectName("jboss.as:interface=public"), "inet-address");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
