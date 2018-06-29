package endpoint.agents;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import agents.types.AgentTypeManagerLocal;
import model.AgentType;

@Path("/agents/classes")
@LocalBean
@Stateless
public class AgentTypeManagementEndpoint implements AgentTypeManagementEndpointLocal{
	
	@EJB
	AgentTypeManagerLocal agentManager;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Collection<AgentType> getAgentTypes(){
		try {
			return agentManager.getAgentTypesOnNode();
		}catch(NamingException e) {
			//
		}
		
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
