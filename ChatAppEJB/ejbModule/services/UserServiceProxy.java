package services;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import interfaces.UserResourceInterface;

@Singleton
@LocalBean
public class UserServiceProxy {

	UserResourceInterface rest;

    public UserServiceProxy() {
    	ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/UserAppWeb/rest/user");
		rest = target.proxy(UserResourceInterface.class);
    }

	public UserResourceInterface getRest() {
		return rest;
	}

}
