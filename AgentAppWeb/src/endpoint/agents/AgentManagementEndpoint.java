package endpoint.agents;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import agents.AgentManagerLocal;
import model.AID;

@Path("/running")
public class AgentManagementEndpoint {

	@EJB
	AgentManagerLocal agentManager;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<AID> getRunningAgents(){
		//TODO get running agents from slave
		return null;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<AID> sendRunningAgents(Collection<AID> runningAgents){
		//TODO receive running Agents from slave
		return null;
	}
	
	@PUT
	@Path("/{type}/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object startAgent(@PathParam(value = "type") String type, @PathParam(value = "name") String name) {
		//TODO find agent in system by type and name (from AID) and start it
		return null;
	}
	
	@DELETE
	@Path("/{aid}")
	@Produces(MediaType.TEXT_PLAIN)
	public String stopAgent(@PathParam("aid") String aid) {
		//TODO find agent by aid from running agents and stop it
		return null;
	}
	
}
