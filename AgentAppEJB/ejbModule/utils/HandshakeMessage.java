package utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import model.AgentCenter;
import model.AgentType;

public class HandshakeMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, AgentCenter> agentCenters;
	private HashMap<String, Collection<AgentType>> agentTypes;
	
	public HandshakeMessage() {
		super();
    	agentCenters = new HashMap<String, AgentCenter>();
    	agentTypes = new HashMap<>();
	}

	public HashMap<String, AgentCenter> getAgentCenters() {
		return agentCenters;
	}

	public void setAgentCenters(HashMap<String, AgentCenter> agentCenters) {
		this.agentCenters = agentCenters;
	}

	public HandshakeMessage(HashMap<String, AgentCenter> agentCenters,
			HashMap<String, Collection<AgentType>> agentTypes) {
		super();
		this.agentCenters = agentCenters;
		this.agentTypes = agentTypes;
	}

	public HashMap<String, Collection<AgentType>> getAgentTypes() {
		return agentTypes;
	}

	public void setAgentTypes(HashMap<String, Collection<AgentType>> agentTypes) {
		this.agentTypes = agentTypes;
	}

	@Override
	public String toString() {
		String retVal = "================================";
		for(AgentCenter ac: agentCenters.values()) {
			retVal += "\n" +ac;
		}
		
		retVal += "\n-----------------------------------------------------------";
		for(String ac: agentTypes.keySet()) {
			retVal += "\n" + ac;
			for(AgentType type: agentTypes.get(ac)) {
				retVal += "\n\t"+type;
			}
		}
		retVal += "\n================================";
		
		return retVal;
	}
}
