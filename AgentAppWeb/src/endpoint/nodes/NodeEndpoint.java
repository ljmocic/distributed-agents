package endpoint.nodes;

import java.util.Collection;

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

import agents.types.AgentTypeManagerLocal;
import connections.ConnectionManagerLocal;
import connections.NodeSynchronizerLocal;
import model.AgentCenter;
import test.AgentCenterConfig;
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
				/*if(!doHandshakeProtocol(newNode)) {
					doNodeRollback(newNode);
				}*/
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
		if(connectionManager.removeNode(alias)) {
			agentTypeManager.removeTypesFromNode(alias);
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
					}
				}
			}
		}
	}
	
	private boolean doHandshakeProtocol(AgentCenter newAgentCenter) {
		try {
			Collection<AgentCenter> centers = connectionManager.getAgentCenters();
			System.out.println("These are my slaves");
			for(AgentCenter ac: centers) {
				sendMessageToOthers(ac, newAgentCenter);
			}
			
			HandshakeMessage message = new HandshakeMessage();
			for(AgentCenter ac: connectionManager.getAgentCenters()) {
				message.getAgentCenters().put(ac.getAlias(), ac);
			}
			message.setAgentTypes(agentTypeManager.getAgentTypesOnSystem());
			
			System.out.println("Welcome to the Round Table, slave");
			System.out.println(message);
			
			Entity<HandshakeMessage> request = Entity.entity(message, MediaType.APPLICATION_JSON);
			Response r = ResteasyClientFactory.target(newAgentCenter.getAddress()+"/AgentAppWeb/rest/node").request().post(request);
			if(r != null) {
				r.close();
			}
		}catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	private void sendMessageToOthers(AgentCenter ac, AgentCenter newAgent) throws Exception{
		System.out.println("Slave "+ac+", register "+newAgent);
		if(!ac.getAlias().equals(AgentCenterConfig.nodeName) && !ac.getAlias().equals(newAgent.getAlias())) {
			HandshakeMessage message = new HandshakeMessage();
			message.getAgentCenters().put(newAgent.getAlias(), newAgent);
			message.getAgentTypes().put(newAgent.getAlias(), agentTypeManager.getTypesFromCenter(newAgent.getAlias()));
			
			System.out.println("[message for "+ac+"]:");
			System.out.println(message);
			Entity<HandshakeMessage> request = Entity.entity(message, MediaType.APPLICATION_JSON);
			Response r = ResteasyClientFactory.target(ac.getAddress()+"/AgentAppWeb/rest/node").request().post(request);
			if(r != null) {
				r.close();
			}
		}
	}
	
	/*private void doNodeRollback(AgentCenter newAgentCenter) {
		
	}*/
}
