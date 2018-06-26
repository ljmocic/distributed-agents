package endpoint.nodes;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import connections.ConnectionManagerLocal;

@Path("/node")
public class NodeEndpoint {

	@EJB
	ConnectionManagerLocal connectionManager;
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String heartBeatCheck() {
		//TODO check if node is still working
		return null;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object registerNode() {
		//TODO register new node
		return null;
	}
	
	@DELETE
	@Path("/{alias}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deactivateNode(@PathParam("alias") String alias) {
		//TODO remove node from system
		return null;
	}
}
