package model;

import java.util.Date;

import javax.ejb.Remote;
import javax.ejb.Stateful;

import org.codehaus.jackson.map.ObjectMapper;

@Stateful
@Remote(AgentRemote.class)
public class AgentImpl extends Agent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer implField;
	
	@Override
	public void handleMessage(ACLMessage message) {
		System.out.println("[AgentImpl received message at "+new Date()+"]");
		System.out.println(message);
	}

	@Override
	public AgentDTO serialize(AgentRemote agent) {
		AgentDTO dto = new AgentDTO();
		//dto.setAgentClass(AgentImpl.class);
		dto.getFieldMap().put("id", getAID());
		dto.getFieldMap().put("implField", implField);
		
		return dto;
	}

	@Override
	public AgentRemote deserialize(AgentDTO agentDTO) {
		ObjectMapper mapper = new ObjectMapper();
		this.setAID(mapper.convertValue(agentDTO.getFieldMap().get("id"), AID.class));
		this.setImplField(mapper.convertValue(agentDTO.getFieldMap().get("implField"), Integer.class));
		return this;
	}

	public Integer getImplField() {
		return implField;
	}

	public void setImplField(Integer implField) {
		this.implField = implField;
	}
	
}
