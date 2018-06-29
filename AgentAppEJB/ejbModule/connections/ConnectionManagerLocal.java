package connections;

import java.util.Collection;

import javax.ejb.Local;

import model.AgentCenter;

@Local
public interface ConnectionManagerLocal {

	public void initMap();
	
	public boolean addNode(AgentCenter agentCenter);
	
	public boolean removeNode(String alias);
	
	public Collection<AgentCenter> getAgentCenters();
	
	public AgentCenter getAgentCenter(String alias);
}
