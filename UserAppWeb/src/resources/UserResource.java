package resources;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import models.User;

@RequestScoped
@Path("/users")
@Produces("application/json")
@Consumes("application/json")
public class UserResource {

	@GET
	@Path("/")
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		users.add(new User("test", "test"));
		return users;
	}

}
