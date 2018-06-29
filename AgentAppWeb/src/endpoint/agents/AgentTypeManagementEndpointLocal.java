package endpoint.agents;

import java.util.Collection;

import model.AgentType;

public interface AgentTypeManagementEndpointLocal {

	public Collection<AgentType> getAgentTypes();
}
