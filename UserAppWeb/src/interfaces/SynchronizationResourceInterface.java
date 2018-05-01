package interfaces;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.User;

@Path("/synchronize")
public interface SynchronizationResourceInterface {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void synchronize(List<User> activeUsersFromNode);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getActiveUsers();
	
	@GET
	@Path("/{username}")
	public boolean checkIfLoggedIn(@PathParam(value="name")String username);
	
}
