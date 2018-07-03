package model;

import java.util.Date;

import javax.ejb.Remote;
import javax.ejb.Stateful;

import org.codehaus.jackson.map.ObjectMapper;

@Stateful
@Remote(AgentRemote.class)
public class AgentImpl2 extends Agent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String impl2Field;

	@Override
	public void handleMessage(ACLMessage message) {
		System.out.println("[AgentImpl2 received message at "+new Date()+"]");
		System.out.println(message);
	}

	public String getImpl2Field() {
		return impl2Field;
	}

	public void setImpl2Field(String impl2Field) {
		this.impl2Field = impl2Field;
	}

	@Override
	public AgentDTO serialize(AgentRemote agent) {
		AgentDTO dto = new AgentDTO();
		//dto.setAgentClass(AgentImpl.class);
		dto.getFieldMap().put("id", getAID());
		dto.getFieldMap().put("impl2Field", impl2Field);
		
		return dto;
	}

	@Override
	public AgentRemote deserialize(AgentDTO agentDTO) {
		ObjectMapper mapper = new ObjectMapper();
		this.setAID(mapper.convertValue(agentDTO.getFieldMap().get("id"), AID.class));
		this.setImpl2Field(mapper.convertValue(agentDTO.getFieldMap().get("impl2Field"), String.class));
		return this;
	}

}
