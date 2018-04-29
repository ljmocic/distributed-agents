package resources;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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
import interfaces.UserServiceLocal;

@Path("/user")
@Stateless
public class UserResource {
	
	@EJB
	UserServiceLocal userService;
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		return userService.getUsers();
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	public void createUser(@FormParam("username") String username, @FormParam("password") String password) {
		User user = new User(username, password);
		userService.createUser(user);
	}
	
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("username") String username) {
		return userService.getUserByUsername(username);
	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(User user) {
		userService.updateUser(user);
	}
	
	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteUser(User user) {
		userService.deleteUser(user);
	}

}
