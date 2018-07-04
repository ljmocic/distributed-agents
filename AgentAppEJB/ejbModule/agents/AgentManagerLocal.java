package agents;

import java.util.Collection;

import javax.ejb.Local;

import model.AID;
import model.AgentCenter;
import model.AgentRemote;

@Local
public interface AgentManagerLocal {

	public AgentRemote startAgent(String agentClass, String name);
	public boolean removeAgent(AID aid);
	public Collection<AID> getRunningAIDs();
	public Collection<AgentRemote> getRunningAgents();
	public void syncAgents(AgentCenter center, Collection<AgentRemote> agents);
	public AgentRemote getRunningAgent(AID aid);
}
