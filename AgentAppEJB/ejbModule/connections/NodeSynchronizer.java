package connections;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import model.AgentCenter;
import test.AgentCenterConfig;

/**
 * Session Bean implementation class NodeSynchronizer
 */
@Singleton
@Startup
public class NodeSynchronizer implements NodeSynchronizerLocal {

    @EJB
    ConnectionManagerLocal connectionManager;
	
    public NodeSynchronizer() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    @PostConstruct
	public void setupNode() {
    	System.out.println("Node up: "+AgentCenterConfig.nodeName);
    	AgentCenter ac = new AgentCenter();
		ac.setAlias(AgentCenterConfig.nodeName);
		ac.setAddress("http://"+AgentCenterConfig.nodeAddress+":"+AgentCenterConfig.nodePort);
		
		if(AgentCenterConfig.masterAddress != null) {
			Entity<AgentCenter> request = Entity.entity(ac, MediaType.APPLICATION_JSON);
			ResteasyClient client = new ResteasyClientBuilder().build();
	        ResteasyWebTarget target = client.target("http://"+AgentCenterConfig.masterAddress+"/AgentAppWeb/rest/node");
	        target.request(MediaType.APPLICATION_JSON).post(request);
		}else {
			connectionManager.addNode(ac);
		}
	}
    
    @Override
    @PreDestroy
    public void removeNode() {
    	System.out.println("Node down: "+AgentCenterConfig.nodeName);
		if(AgentCenterConfig.masterAddress != null) {
	    	ResteasyClient client = new ResteasyClientBuilder().build();
	        ResteasyWebTarget target = client.target("http://"+AgentCenterConfig.masterAddress+"/AgentAppWeb/rest/node/"+AgentCenterConfig.nodeName);
	        target.request().delete();
		}else {
			connectionManager.removeNode(AgentCenterConfig.nodeName);
		}
    }
}
