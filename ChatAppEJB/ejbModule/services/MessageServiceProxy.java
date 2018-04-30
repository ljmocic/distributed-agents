package services;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import interfaces.MessageResourceInterface;

@Singleton
@LocalBean
public class MessageServiceProxy {
	
	MessageResourceInterface rest;

	public MessageServiceProxy() {
    	ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/UserAppWeb/rest/message");
		rest = target.proxy(MessageResourceInterface.class);
    }
	
	public MessageResourceInterface getRest() {
		return rest;
	}

}
