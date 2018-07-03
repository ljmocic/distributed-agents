package endpoint.agents;

import java.util.Collection;

import javax.ejb.Local;

import model.AID;
import utils.AgentsMessage;

@Local
public interface AgentManagementEndpointLocal {

	public void startAgent(String type, String name);
	public void stopAgent(String aid);
	public Collection<AID> getRunningAgents();
	public void newAgentStarted(AgentsMessage agents);
}
