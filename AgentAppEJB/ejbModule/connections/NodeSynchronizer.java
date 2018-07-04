package connections;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.NamingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import agents.types.AgentTypeManagerLocal;
import model.AgentCenter;
import utils.AgentCenterConfig;
import utils.HandshakeMessage;
import utils.ResteasyClientFactory;

/**
 * Session Bean implementation class NodeSynchronizer
 */
@Singleton
@Startup
public class NodeSynchronizer implements NodeSynchronizerLocal {

    @EJB
    ConnectionManagerLocal connectionManager;
    
    @EJB
    AgentTypeManagerLocal agentTypeManager;
	
    public NodeSynchronizer() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    @PostConstruct
	public void setupNode() {
    	HandshakeMessage message = new HandshakeMessage();
    	
    	AgentCenter ac = new AgentCenter();
		ac.setAlias(AgentCenterConfig.nodeName);
		ac.setAddress("http://"+AgentCenterConfig.nodeAddress+":"+AgentCenterConfig.nodePort);
		message.getAgentCenters().put(ac.getAlias(), ac);
		
		try {
			message.getAgentTypes().put(ac.getAlias(), agentTypeManager.getAgentTypesOnNode());
		}catch(NamingException e) {
			return;
		}
		
		if(AgentCenterConfig.masterAddress != null) {
			Entity<HandshakeMessage> request = Entity.entity(message, MediaType.APPLICATION_JSON);
	        Response r = ResteasyClientFactory.target("http://"+AgentCenterConfig.masterAddress+"/AgentAppWeb/rest/node")
	        	.request(MediaType.APPLICATION_JSON).post(request);
	        if(r != null) {
	        	r.close();
	        }
		}else {
			connectionManager.addNode(ac);
			agentTypeManager.addTypesFromNode(ac.getAlias(), message.getAgentTypes().get(ac.getAlias()));
		}
	}
    
    @Override
    @PreDestroy
    public void removeNode() {
		if(AgentCenterConfig.masterAddress != null) {
	    	Response r = ResteasyClientFactory.target("http://"+AgentCenterConfig.masterAddress+"/AgentAppWeb/rest/node/"+AgentCenterConfig.nodeName)
	    		.request().delete();
	    	if(r != null) {
	    		r.close();
	    	}
		}else {
			connectionManager.removeNode(AgentCenterConfig.nodeName);
			agentTypeManager.removeTypesFromNode(AgentCenterConfig.nodeName);
		}
    }
}
