package utils;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ResteasyClientFactory {

	private static ResteasyClient client;
	static {
		client = new ResteasyClientBuilder().build();
	}
	
	public static ResteasyWebTarget target(String targetURL) {
		return client.target(targetURL);
	}
}
