package agents.impls;

import java.util.Date;

import javax.ejb.Remote;
import javax.ejb.Stateful;

import org.codehaus.jackson.map.ObjectMapper;

import model.ACLMessage;
import model.AID;
import model.Agent;
import model.AgentDTO;
import model.AgentRemote;

@Stateful
@Remote(AgentRemote.class)
public class AgentImpl3 extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date date;
	
	public AgentImpl3(Date date) {
		super();
		this.date = date;
	}

	public AgentImpl3() {
		
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public void handleMessage(ACLMessage message) {
		System.out.println(date);
	}

	@Override
	public AgentDTO serialize(AgentRemote agent) {
		AgentDTO dto = new AgentDTO();
		//dto.setAgentClass(AgentImpl.class);
		dto.getFieldMap().put("id", getAID());
		dto.getFieldMap().put("date", getDate());
		
		return dto;
	}

	@Override
	public AgentRemote deserialize(AgentDTO agentDTO) {
		ObjectMapper mapper = new ObjectMapper();
		this.setAID(mapper.convertValue(agentDTO.getFieldMap().get("id"), AID.class));
		this.setDate(mapper.convertValue(agentDTO.getFieldMap().get("date"), Date.class));
		return this;
	}

}
