package interfaces;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.User;

public interface UserResourceInterface {

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers();
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String loginUser(@FormParam("username") String username, @FormParam("password") String password);
	
	@POST
	@Path("/logout/{username}")
	@Produces(MediaType.TEXT_PLAIN)
	public String logoutUser(@PathParam("username") String username);
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User createUser(@FormParam("username") String username, @FormParam("password") String password);
	
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("username") String username);

	@PUT
	@Path("/update/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(User user, @PathParam("username") String username);
	
	@DELETE
	@Path("/delete/{username}")
	public void deleteUser(@PathParam("username") String username);
	
	@PUT
	@Path("/{username}/friends/{friend}")
	public void addFriend(@PathParam("username") String username, @PathParam("friend") String friend);
	
	@DELETE
	@Path("/{username}/friends/{friend}")
	public void removeFriend(@PathParam("username") String username, @PathParam("friend") String friend);
	
	@GET
	@Path("/{username}/friends")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllFriendsOf(@PathParam("username") String username);
	
}
