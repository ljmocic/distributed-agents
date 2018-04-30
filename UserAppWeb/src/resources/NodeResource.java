package resources;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Host;
import services.NodeService;

@Path("/node")
@Stateless
public class NodeResource {
	
	@EJB
	NodeService nodeService;
	
	@GET
	@Path("/activate/{alias}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String activateNode(@Context HttpServletRequest request, @PathParam("alias") String alias) {
		nodeService.activateNode(new Host(request.getRemoteHost(), alias));
		System.out.println("Node " + alias + " activated: " + request.getRemoteHost());
		return "Node activated!";
	}
	
	@GET
	@Path("/deactivate/{alias}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String deactivateNode(@Context HttpServletRequest request, @PathParam("alias") String alias) {
		nodeService.deactivateNode(alias);
		System.out.println("Node deactivated!");
		return "Node deactivated!";
	}
	
	@GET
	@Path("/notify")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String notifyNodes() {
		nodeService.notifyNodes();
		return "Nodes notified!";
	}

}
