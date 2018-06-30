package endpoint.agents;

import java.util.Collection;
import java.util.HashMap;

import model.AgentType;

public interface AgentTypeManagementEndpointLocal {

	public Collection<AgentType> getAgentTypes();

	public HashMap<String, Collection<AgentType>> getAllAgentTypes();
}
