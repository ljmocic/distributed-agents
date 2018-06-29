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
import javax.ws.rs.core.MediaType;

import connections.ConnectionManagerLocal;
import connections.NodeSynchronizerLocal;
import model.AgentCenter;

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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<AgentCenter> getActiveNodes() {
		return connectionManager.getAgentCenters();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void registerNode(AgentCenter agentCenter) {
		System.out.println("Node register request received :"+agentCenter);
		if(connectionManager.addNode(agentCenter)) {
			System.out.println("Node registered :"+agentCenter);
		}
	}
	
	@DELETE
	@Path("/{alias}")
	public void deactivateNode(@PathParam("alias") String alias) {
		System.out.println("Node remove request received :"+alias);
		if(connectionManager.removeNode(alias)) {
			System.out.println("Node removed :"+alias);
		}
	}
}
