package utils;

import org.codehaus.jackson.map.ObjectMapper;

import model.AID;
import model.Agent;
import model.AgentDTO;
import model.AgentRemote;

public class AgentMapper {

	public AgentMapper() {}
	
	public AgentDTO serialize(Agent agent) {
		return agent.serialize(agent);
	}
	
	public AgentRemote deserialize(AgentDTO agentDTO) {
		ObjectMapper mapper = new ObjectMapper();
		AgentRemote agent = null;
		try {
			AID aid = mapper.convertValue(agentDTO.getFieldMap().get("id"), AID.class);
			agent = (AgentRemote)JNDIUtils.getContext().lookup("java:app/"+aid.getType().getModule()+"/"+aid.getType().getName());
		}catch(Exception e) {
			System.out.println("Illegal type, srry");
			return null;
		}
		
		return agent.deserialize(agentDTO);
	}
	
}
