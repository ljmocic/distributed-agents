package agents.impls;

import java.util.Random;

import javax.ejb.Remote;
import javax.ejb.Stateful;

import org.codehaus.jackson.map.ObjectMapper;

import model.ACLMessage;
import model.ACLPerformative;
import model.AID;
import model.Agent;
import model.AgentCenter;
import model.AgentDTO;
import model.AgentRemote;
import model.AgentType;
import utils.AgentCenterConfig;
import utils.JNDIUtils;

@Stateful
@Remote(AgentRemote.class)
public class Pong extends Agent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name = "Pong";
	private Integer pongId;
	
	public Pong() {
		this.pongId = (new Random()).nextInt();
	}

	@Override
	public void handleMessage(ACLMessage message) {
		if(message.getPerformative() == ACLPerformative.REQUEST) {
			ACLMessage msg = new ACLMessage();
			System.out.println("Pong received request");
			
			AID pingAID = new AID();
			pingAID.setName(message.getContent());
			pingAID.setType(new AgentType(Ping.class.getSimpleName()+JNDIUtils.INTERFACE, MODULE_NAME));
			pingAID.setHost(new AgentCenter(AgentCenterConfig.nodeName, "http://"+AgentCenterConfig.nodeAddress+":"+AgentCenterConfig.nodePort));
			
			AID[] receivers = new AID[1];
			receivers[0] = pingAID;
			
			msg.setPerformative(ACLPerformative.INFORM);
			msg.setSender(getAID());
			msg.setReceivers(receivers);
			msg.setContent(name);
			
			System.out.println(message);
			System.out.println(msg);
			getMessageManager().sendMessage(msg);
		}
	}

	@Override
	public AgentDTO serialize(AgentRemote agent) {
		AgentDTO dto = new AgentDTO();
		//dto.setAgentClass(AgentImpl.class);
		dto.getFieldMap().put("id", getAID());
		dto.getFieldMap().put("pongId", pongId);
		dto.getFieldMap().put("name", name);
		
		return dto;
	}

	@Override
	public AgentRemote deserialize(AgentDTO agentDTO) {
		ObjectMapper mapper = new ObjectMapper();
		this.setAID(mapper.convertValue(agentDTO.getFieldMap().get("id"), AID.class));
		this.setPongId(mapper.convertValue(agentDTO.getFieldMap().get("pongId"), Integer.class));
		this.setName(agentDTO.getFieldMap().get("name").toString());
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPongId() {
		return pongId;
	}

	public void setPongId(Integer PongId) {
		this.pongId = PongId;
	}

	public Pong(String name, Integer PongId) {
		super();
		this.name = name;
		this.pongId = (new Random()).nextInt();
	}
}
