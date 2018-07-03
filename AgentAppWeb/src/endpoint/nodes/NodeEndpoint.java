package endpoint.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import agents.AgentManagerLocal;
import agents.types.AgentTypeManagerLocal;
import connections.ConnectionManagerLocal;
import connections.NodeSynchronizerLocal;
import model.AgentCenter;
import model.AgentDTO;
import model.AgentRemote;
import utils.AgentCenterConfig;
import utils.AgentsMessage;
import utils.HandshakeMessage;
import utils.ResteasyClientFactory;

/**
 * Session Bean implementation class NodeEndpoint
 */
@Stateless
@Path("/node")
@LocalBean
public class NodeEndpoint implements NodeEndpointLocal{

	@EJB
	ConnectionManagerLocal connectionManager;
	
	@EJB
	NodeSynchronizerLocal nodeSynchronizer;
	
	@EJB
	AgentTypeManagerLocal agentTypeManager;
	
	@EJB
	AgentManagerLocal agentManager;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<AgentCenter> getActiveNodes() {
		return connectionManager.getAgentCenters();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void registerNode(HandshakeMessage message) {
		AgentCenter newNode = (AgentCenter)message.getAgentCenters().values().toArray()[0];
		System.out.println("Node register request received :" + newNode);
		
		if(AgentCenterConfig.masterAddress == null && message.getAgentCenters().values().size() == 1) {
			System.out.println("Master, please register me! " + newNode);
			boolean flagOne = connectionManager.addNode(newNode);
			boolean flagTwo = agentTypeManager.addTypesFromNode(newNode.getAlias(), message.getAgentTypes().get(newNode.getAlias()));
			System.out.println(flagOne + ", " + flagTwo);
			
			if(flagOne && flagTwo) {
				System.out.println("Master is pleased! " + newNode);
				doHandshakeProtocol(newNode);
			}
		}else {
			System.out.println("Meet your fellow bannerman!");
			System.out.println(message);
			
			if(AgentCenterConfig.masterAddress != null && message.getAgentCenters().values().size() > 0) {
				for(String ac: message.getAgentCenters().keySet()) {
					connectionManager.addNode(message.getAgentCenters().get(ac));
					agentTypeManager.addTypesFromNode(ac, message.getAgentTypes().get(ac));
				}
			}
		}
	}
	
	@DELETE
	@Path("/{alias}")
	public void deactivateNode(@PathParam("alias") String alias) {
		System.out.println("Node remove request received :"+alias);
		AgentCenter center = connectionManager.getAgentCenter(alias);
		
		if(connectionManager.removeNode(alias)) {
			agentTypeManager.removeTypesFromNode(alias);
			agentManager.syncAgents(center, new ArrayList<>());
			
			System.out.println("Master, I have failed you ... "+alias);
			
			if(AgentCenterConfig.masterAddress == null) {
				for(AgentCenter ac: connectionManager.getAgentCenters()) {
					
					if(!ac.getAlias().equals("master")) {
						System.out.println("Node removal request sent to: "+ac.getAddress());
						Response r = ResteasyClientFactory.target(ac.getAddress()+"/AgentAppWeb/rest/node/"+alias)
							.request().delete();
						if(r != null) {
							r.close();
						}
						
						AgentsMessage msg = new AgentsMessage();
						msg.setAgentCenter(center);
						msg.setAgents(new ArrayList<>());
						Entity<AgentsMessage> request = Entity.entity(msg, MediaType.APPLICATION_JSON);
						
						 r = ResteasyClientFactory.target(ac.getAddress()+"/AgentAppWeb/rest/agents/running")
								.request().post(request);
					}
				}
			}
		}
	}
	
	private void doHandshakeProtocol(AgentCenter newAgentCenter) {
		
		Collection<AgentCenter> centers = connectionManager.getAgentCenters();
		System.out.println("These are my slaves");
		
		HandshakeMessage message = new HandshakeMessage();
		message.getAgentCenters().put(newAgentCenter.getAlias(), newAgentCenter);
		message.getAgentTypes().put(newAgentCenter.getAlias(), agentTypeManager.getTypesFromCenter(newAgentCenter.getAlias()));
		
		for(AgentCenter ac: centers) {
			try{
				if(!ac.getAlias().equals(AgentCenterConfig.nodeName) && !ac.getAlias().equals(newAgentCenter.getAlias())) {
					System.out.println("Slave "+ac+", register "+newAgentCenter);
					sendMessageTo(ac, message);
				}
			}catch(Exception e) {
				try {
					sendMessageTo(ac, message);
				}catch(Exception ee) {
					System.out.println("Timeout with "+ac);
					deactivateNode(newAgentCenter.getAlias());
					return;
				}
			}
		}
		
		HandshakeMessage messageForNewNode = new HandshakeMessage();
		for(AgentCenter ac: connectionManager.getAgentCenters()) {
			messageForNewNode.getAgentCenters().put(ac.getAlias(), ac);
		}
		messageForNewNode.setAgentTypes(agentTypeManager.getAgentTypesOnSystem());
		
		System.out.println("Welcome to the Round Table, slave");
		System.out.println(messageForNewNode);
		
		try {
			sendMessageTo(newAgentCenter, messageForNewNode);
		}catch(Exception e) {
			try {
				sendMessageTo(newAgentCenter, messageForNewNode);
			}catch(Exception ee) {
				System.out.println("Timeout with "+newAgentCenter);
				deactivateNode(newAgentCenter.getAlias());
				return;
			}
		}
		
		System.out.println("These agents are running, remember that");
		try {
			Collection<AgentRemote> runningAgents = agentManager.getRunningAgents();
			List<AgentDTO> runningAgentDTOs = new ArrayList<>();
			
			for(AgentRemote agent: runningAgents) {
				AgentDTO dto = agent.serialize(agent);
				System.out.println(dto);
				runningAgentDTOs.add(dto);
			}
			
			AgentsMessage msg = new AgentsMessage();
			msg.setAgents(runningAgentDTOs);
			AgentCenter ac = new AgentCenter();
			ac.setAlias(AgentCenterConfig.nodeName);
			ac.setAddress("http://"+AgentCenterConfig.nodeAddress+":"+AgentCenterConfig.nodePort);
			msg.setAgentCenter(ac);
			
			Entity<AgentsMessage> request = Entity.entity(msg, MediaType.APPLICATION_JSON);
			
			System.out.println(newAgentCenter.getAddress()+"/AgentAppWeb/rest/agents/running");
			Response r = ResteasyClientFactory.target(newAgentCenter.getAddress()+"/AgentAppWeb/rest/agents/running")
					.request(MediaType.APPLICATION_JSON).post(request);
			if(r != null) {
				r.close();
			}
		}catch(Exception e) {
			System.out.println("Timeout with "+newAgentCenter);
			deactivateNode(newAgentCenter.getAlias());
		}
		
	}
	
	private void sendMessageTo(AgentCenter sendTo, HandshakeMessage message) throws Exception{
		System.out.println("[message for "+sendTo+"]:");
		System.out.println(message);
		
		Entity<HandshakeMessage> request = Entity.entity(message, MediaType.APPLICATION_JSON);
		Response r = ResteasyClientFactory.target(sendTo.getAddress()+"/AgentAppWeb/rest/node").request().post(request);
		if(r != null) {
			r.close();
		}
	}
	
}
