package utils;

import java.util.Collection;

import model.AgentCenter;
import model.AgentDTO;

public class AgentsMessage {

	private Collection<AgentDTO> agents;
	private AgentCenter agentCenter;
	
	public AgentsMessage() {}

	public AgentsMessage(Collection<AgentDTO> agents, AgentCenter agentCenter) {
		super();
		this.agents = agents;
		this.agentCenter = agentCenter;
	}

	public Collection<AgentDTO> getAgents() {
		return agents;
	}

	public void setAgents(Collection<AgentDTO> agents) {
		this.agents = agents;
	}

	public AgentCenter getAgentCenter() {
		return agentCenter;
	}

	public void setAgentCenter(AgentCenter agentCenter) {
		this.agentCenter = agentCenter;
	}

}
