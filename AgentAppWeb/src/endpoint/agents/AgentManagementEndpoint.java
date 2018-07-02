package endpoint.agents;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import agents.AgentManagerLocal;
import agents.types.AgentTypeManagerLocal;
import connections.ConnectionManagerLocal;
import model.AID;
import model.Agent;
import model.AgentCenter;
import model.AgentRemote;
import test.AgentCenterConfig;
import utils.ResteasyClientFactory;

@Stateless
@LocalBean
@Path("/agents/running")
public class AgentManagementEndpoint implements AgentManagementEndpointLocal{

	@EJB
	AgentManagerLocal agentManager;
	
	@EJB
	AgentTypeManagerLocal agentTypeManager;

	@EJB
	ConnectionManagerLocal connectionManager;
	
	@PUT
	@Path("/{type}/{name}")
	@Override
	public void startAgent(@PathParam("type")String type, @PathParam("name")String name) {
		AgentRemote agent = agentManager.startAgent(type, name);
		if(agent != null) {
			Entity<Collection<AgentRemote>> request = Entity.entity(agentManager.getRunningAgents(), MediaType.APPLICATION_JSON);
			
			for(AgentCenter center: connectionManager.getAgentCenters()) {
				if(!center.getAlias().equals(AgentCenterConfig.nodeName)) {
					Response r = ResteasyClientFactory.target(center.getAddress()+"/AgentAppWeb/rest/agents/running")
						.request().post(request);
					if(r != null) {
						r.close();
					}
				}
			}
		}
	}

	@DELETE
	@Path("/aid")
	@Override
	public void stopAgent(String aid) {
		agentManager.removeAgent(parseToAID(aid));
		Entity<Collection<AgentRemote>> request = Entity.entity(agentManager.getRunningAgents(), MediaType.APPLICATION_JSON);
		
		for(AgentCenter center: connectionManager.getAgentCenters()) {
			if(!center.getAlias().equals(AgentCenterConfig.nodeName)) {
				Response r = ResteasyClientFactory.target(center.getAddress()+"/AgentAppWeb/rest/agents/running")
					.request().post(request);
				if(r != null) {
					r.close();
				}
			}
		}
	}

	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<AID> getRunningAgents() {
		return agentManager.getRunningAIDs();
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void newAgentStarted(Collection<AgentRemote> agents) {
		AgentCenter center = ((Agent)agents.toArray()[0]).getAID().getHost();
		agentManager.syncAgents(center, agents);
	}
		
	public AID parseToAID(String aidStr) {
		String[] keySet = aidStr.split(",");
		if(keySet.length != 3) {
			return null;
		}
		
		AID aid = new AID();
		
		String[] nameVal = keySet[0].split("=");
		aid.setName(nameVal[1]);
		
		String[] typeVal = keySet[1].split("=");
		aid.setType(agentTypeManager.getTypeWithName(typeVal[1]));
		
		String[] hostVal = keySet[2].split("=");
		aid.setHost(connectionManager.getAgentCenter(hostVal[1].substring(0, hostVal[1].length()-1)));
		
		return aid;
	}
}
