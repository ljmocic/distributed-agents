package services;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

@Singleton
@LocalBean
public class SynchronizationService {
	
	ResteasyWebTarget target;

    public SynchronizationService() {
    	ResteasyClient client = new ResteasyClientBuilder().build();
		target = client.target("http://localhost:8080/UserAppWeb/rest/synchronize");
    }

	public ResteasyWebTarget getTarget() {
		return target;
	}
}
