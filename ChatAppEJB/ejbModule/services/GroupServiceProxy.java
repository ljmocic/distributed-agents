package services;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import interfaces.GroupResourceInterface;

@Singleton
@LocalBean
public class GroupServiceProxy {

	GroupResourceInterface rest;

    public GroupServiceProxy() {
    	ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/UserAppWeb/rest/group");
		rest = target.proxy(GroupResourceInterface.class);
    }

	public GroupResourceInterface getRest() {
		return rest;
	}

}
