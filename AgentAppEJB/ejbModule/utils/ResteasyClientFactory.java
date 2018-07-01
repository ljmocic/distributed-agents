package utils;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ResteasyClientFactory {

	
	public static ResteasyWebTarget target(String targetURL) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		return client.target(targetURL);
	}
}
