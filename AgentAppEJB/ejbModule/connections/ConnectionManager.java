package connections;

import java.util.Collection;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import model.AgentCenter;

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
    	System.out.println("init map");
        agentCenters = new HashMap<String, AgentCenter>();
    }

    @Override
    public boolean addNode(AgentCenter center) {
    	System.out.println("add node");
    	if(agentCenters.containsKey(center.getAlias())) {
    		return false;
    	}
    	
    	agentCenters.put(center.getAlias(), center);
    	return true;
    }
    
    @Override
    public boolean removeNode(String alias) {
    	System.out.println("remove node");
    	if(agentCenters.containsKey(alias)) {
    		agentCenters.remove(alias);
    		return true;
    	}
    	
    	return false;
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
