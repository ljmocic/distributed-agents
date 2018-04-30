package interfaces;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.Group;

public interface GroupResourceInterface {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Group> getGroups();
	
	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Group findGroup(@PathParam(value="name") String name);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Group createGroup(Group g);
	
	@PUT
	@Path("/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateGroup(Group g, @PathParam(value="name") String name);
	
	@DELETE
	@Path("/{name}")
	public void deleteGroup(@PathParam(value="name")  String name);
	
	@PUT
	@Path("/{name}/users/{username}")
	public void addToGroup(@PathParam(value="name") String name, @PathParam(value="username") String username);
	
	@DELETE
	@Path("/{name}/users/{username}")
	public void removeFromGroup(@PathParam(value="name") String name, @PathParam(value="username") String username);

}
