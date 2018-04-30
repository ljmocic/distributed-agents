package services;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

@Singleton
@LocalBean
@Startup
public class NodeService implements NodeServiceLocal {
	
	public static String nodeAlias = "nodeNo1";

    public NodeService() {
    	
    }
    
    @PostConstruct
    void activateNode() {
    	ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/UserAppWeb/rest/node/activate/" + nodeAlias);
        target.request(MediaType.TEXT_PLAIN).get();
        //System.out.println("ACTIVATE NODE");
    }
    
    @PreDestroy
	void deactivateNode() {
    	ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/UserAppWeb/rest/node/deactivate/" + nodeAlias);
        target.request(MediaType.TEXT_PLAIN).get();
        //System.out.println("DEACTIVATE NODE");
	}

}
