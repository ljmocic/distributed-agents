package agents.impls;

import java.util.Date;
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
public class Ping extends Agent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name = "Ping";
	private Integer pingId;
	
	public Ping() {
		this.pingId = (new Random()).nextInt();
	}

	@Override
	public void handleMessage(ACLMessage message) {
		if(message.getPerformative() == ACLPerformative.REQUEST) {
			System.out.println(String.format("[INFO](%s: %s) Ping (%s)", AgentCenterConfig.nodeName, new Date(), getAID()));
			ACLMessage msg = new ACLMessage();
			
			AID pongAID = new AID();
			pongAID.setName(message.getContent());
			pongAID.setType(new AgentType(Pong.class.getSimpleName()+JNDIUtils.INTERFACE, MODULE_NAME));
			pongAID.setHost(new AgentCenter(AgentCenterConfig.nodeName, "http://"+AgentCenterConfig.nodeAddress+":"+AgentCenterConfig.nodePort));
			
			AID[] receivers = new AID[1];
			receivers[0] = pongAID;
			
			msg.setPerformative(ACLPerformative.REQUEST);
			msg.setSender(getAID());
			msg.setReceivers(receivers);
			msg.setContent(getAID().getName());
			
			System.out.println(message);
			System.out.println(msg);
			getMessageManager().sendMessage(msg);
		}else if(message.getPerformative() == ACLPerformative.INFORM) {
			System.out.println(String.format("[INFO](%s: %s) Ping inform (%s)", AgentCenterConfig.nodeName, new Date(), getAID()));
		}
	}

	@Override
	public AgentDTO serialize(AgentRemote agent) {
		AgentDTO dto = new AgentDTO();
		//dto.setAgentClass(AgentImpl.class);
		dto.getFieldMap().put("id", getAID());
		dto.getFieldMap().put("pingId", pingId);
		dto.getFieldMap().put("name", name);
		
		return dto;
	}

	@Override
	public AgentRemote deserialize(AgentDTO agentDTO) {
		ObjectMapper mapper = new ObjectMapper();
		this.setAID(mapper.convertValue(agentDTO.getFieldMap().get("id"), AID.class));
		this.setPingId(mapper.convertValue(agentDTO.getFieldMap().get("pingId"), Integer.class));
		this.setName(agentDTO.getFieldMap().get("name").toString());
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPingId() {
		return pingId;
	}

	public void setPingId(Integer pingId) {
		this.pingId = pingId;
	}

	public Ping(String name, Integer pingId) {
		super();
		this.name = name;
		this.pingId = (new Random()).nextInt();
	}
}
