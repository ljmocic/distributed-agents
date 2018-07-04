package endpoint.agents;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.ws.rs.GET;
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
	@Path("/system")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Collection<AgentType> getAgentTypes(){
		try {
			return agentManager.getAgentTypesOnNode();
		}catch(NamingException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Collection<AgentType> getAllAgentTypes() {
		return agentManager.getAgentTypesOnSystem();
	}
	
	
}
