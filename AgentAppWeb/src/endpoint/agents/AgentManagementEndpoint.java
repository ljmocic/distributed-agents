package endpoint.agents;

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
import model.AgentCenter;
import model.AgentDTO;
import model.AgentRemote;
import utils.AgentCenterConfig;
import utils.AgentMapper;
import utils.AgentsMessage;
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
		AgentRemote agentt = agentManager.startAgent(type, name);
		if(agentt != null) {
			Collection<AgentRemote> runningAgents = agentManager.getRunningAgents();
			List<AgentDTO> runningAgentDTOs = new ArrayList<>();
			
			for(AgentRemote agent: runningAgents) {
				AgentDTO dto = agent.serialize(agent);
				System.out.println(dto);
				runningAgentDTOs.add(dto);
			}
			
			AgentsMessage msg = new AgentsMessage();
			AgentCenter ac = new AgentCenter();
			ac.setAlias(AgentCenterConfig.nodeName);
			ac.setAddress("http://"+AgentCenterConfig.nodeAddress+":"+AgentCenterConfig.nodePort);
			msg.setAgentCenter(ac);
			msg.setAgents(runningAgentDTOs);
			Entity<AgentsMessage> request = Entity.entity(msg, MediaType.APPLICATION_JSON);
			
			for(AgentCenter center: connectionManager.getAgentCenters()) {
				System.out.println(center.getAddress()+"/AgentAppWeb/rest/agents/running");
				if(!center.getAlias().equals(AgentCenterConfig.nodeName)) {
					Response r = ResteasyClientFactory.target(center.getAddress()+"/AgentAppWeb/rest/agents/running")
						.request(MediaType.APPLICATION_JSON).post(request);
					if(r != null) {
						r.close();
					}
				}
			}
		}
	}

	@DELETE
	@Path("/{aid}")
	@Override
	public void stopAgent(@PathParam("aid") String aid) {
		AID aidd = parseToAID(aid);
		if(aidd.getHost().getAlias().equals(AgentCenterConfig.nodeName)) {
		
			agentManager.removeAgent(parseToAID(aid));
			Collection<AgentRemote> runningAgents = agentManager.getRunningAgents();
			List<AgentDTO> runningAgentDTOs = new ArrayList<>();
			
			for(AgentRemote agent: runningAgents) {
				AgentDTO dto = agent.serialize(agent);
				System.out.println(dto);
				runningAgentDTOs.add(dto);
			}
			
			AgentsMessage msg = new AgentsMessage();
			AgentCenter ac = new AgentCenter();
			ac.setAlias(AgentCenterConfig.nodeName);
			ac.setAddress("http://"+AgentCenterConfig.nodeAddress+":"+AgentCenterConfig.nodePort);
			msg.setAgentCenter(ac);
			msg.setAgents(runningAgentDTOs);
			Entity<AgentsMessage> request = Entity.entity(msg, MediaType.APPLICATION_JSON);
			
			for(AgentCenter center: connectionManager.getAgentCenters()) {
				if(!center.getAlias().equals(AgentCenterConfig.nodeName)) {
					Response r = ResteasyClientFactory.target(center.getAddress()+"/AgentAppWeb/rest/agents/running")
						.request(MediaType.APPLICATION_JSON).post(request);
					if(r != null) {
						r.close();
					}
				}
			}
		}else {
			Response r = ResteasyClientFactory.target(aidd.getHost().getAddress()+"/AgentAppWeb/rest/agents/running/"+aid)
					.request().delete();
				if(r != null) {
					r.close();
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
	public void newAgentStarted(AgentsMessage agents) {
		System.out.println("Agents sync\n");
		Collection<AgentDTO> agentDTOs = agents.getAgents();
		List<AgentRemote> agentss = new ArrayList<>();
		
		AgentMapper mapper = new AgentMapper();
		for(AgentDTO dto: agentDTOs) {
			System.out.println(dto);
			agentss.add(mapper.deserialize(dto));
		}
		
		agentManager.syncAgents(agents.getAgentCenter(), agentss);
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
		System.out.println(hostVal[1].substring(0, hostVal[1].length()));
		aid.setHost(connectionManager.getAgentCenter(hostVal[1].substring(0, hostVal[1].length())));
		
		return aid;
	}
	
	@GET
	@Path("/dto")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<AgentDTO> getAgentDTOs(){
		Collection<AgentRemote> runningAgents = agentManager.getRunningAgents();
		List<AgentDTO> runningAgentDTOs = new ArrayList<>();
		
		for(AgentRemote agent: runningAgents) {
			AgentDTO dto = agent.serialize(agent);
			System.out.println(dto);
			runningAgentDTOs.add(dto);
		}
		
		return runningAgentDTOs;
	}
}
