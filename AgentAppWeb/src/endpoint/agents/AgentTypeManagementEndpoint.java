package endpoint.agents;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import agents.AgentManagerLocal;
import model.AgentType;

@Path("/agents/classes")
public class AgentTypeManagementEndpoint {
	
	@EJB
	AgentManagerLocal agentManager;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<AgentType> getAgentTypes(){
		//TODO get types from slave
		return null;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object sendAgentTypes(Collection<AgentType> agentTypes) {
		//TODO receive types from slave
		//JNDI lookup u lokalnoj bazi
		return null;
	}
	
}
