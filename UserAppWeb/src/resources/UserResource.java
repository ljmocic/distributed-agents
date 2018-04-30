package resources;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.User;
import interfaces.UserResourceInterface;
import interfaces.UserServiceLocal;

@Path("/user")
@Stateless
public class UserResource implements UserResourceInterface {
	
	@EJB
	UserServiceLocal userService;
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		return userService.getUsers();
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String loginUser(@Context HttpServletRequest request, @FormParam("username") String username, @FormParam("password") String password) {
		HttpSession session = request.getSession();
		
		String response;
		User user = userService.getUserByUsername(username);
		if(user != null) {
			if(user.getPassword().equals(password)) {
				session.setAttribute("user", user);
				response = "User successfully logged in!";
			}
			else {
				session.setAttribute("user", null);
				response = "User failed to log in!";
			}
		}
		else {
			session.setAttribute("user", null);
			response = "User failed to log in!";
		}
		return response;
	}
	
	@POST
	@Path("/logout")
	@Produces(MediaType.TEXT_PLAIN)
	public String logoutUser(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "User logged out!";
	}
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User createUser(@FormParam("username") String username, @FormParam("password") String password) {
		User user = new User(username, password);
		return userService.createUser(user);
	}
	
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("username") String username) {
		return userService.getUserByUsername(username);
	}

	@PUT
	@Path("/update/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(User user, @PathParam("username") String username) {
		userService.updateUser(user);
	}
	
	@DELETE
	@Path("/delete/{username}")
	public void deleteUser(@PathParam("username") String username) {
		userService.deleteUser(username);
	}
	
	@PUT
	@Path("/{username}/friends/{friend}")
	public void addFriend(@PathParam("username") String username, @PathParam("friend") String friend) {
		userService.addFriend(username, friend);
	}
	
	@DELETE
	@Path("/{username}/friends/{friend}")
	public void removeFriend(@PathParam("username") String username, @PathParam("friend") String friend) {
		userService.removeFriend(username, friend);
	}
	
	@GET
	@Path("/{username}/friends")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllFriendsOf(@PathParam(value="username") String username){
		return userService.getFriends(username);
	}

}
