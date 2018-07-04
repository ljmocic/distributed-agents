package connections;

import java.util.Collection;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import model.AgentCenter;
import utils.AgentCenterConfig;

/**
 * Session Bean implementation class ConnectionManager
 */
@Singleton
@Startup
public class ConnectionManager implements ConnectionManagerLocal {

	private HashMap<String, AgentCenter> agentCenters;

	
    @PostConstruct
    @Override
    public void initMap() {
        agentCenters = new HashMap<String, AgentCenter>();
    }

    @Override
    public boolean addNode(AgentCenter center) {
    	if(agentCenters.containsKey(center.getAlias())) {
    		return false;
    	}
    	
    	agentCenters.put(center.getAlias(), center);
    	System.out.println("Node "+center.getAlias()+" registered");
    	return true;
    }
    
    @Override
    public boolean removeNode(String alias) {
    	if(!alias.equals(AgentCenterConfig.nodeName)) {
	    	if(agentCenters.containsKey(alias)) {
	    		agentCenters.remove(alias);
	    		System.out.println("Node "+alias+" removed");
	    		return true;
	    	}

	    	return false;
    	}
    	
    	initMap();
    	return true;
    }
    
    @Override
    public Collection<AgentCenter> getAgentCenters() {
    	if(agentCenters.values().size() > 0) {
    		return agentCenters.values();
    	}
    	
    	return null;
    }
    
    @Override
    public AgentCenter getAgentCenter(String alias) {
    	return agentCenters.get(alias);
    }
}
