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

import beans.Message;
import beans.User;
import interfaces.MessageServiceLocal;
import services.UserService;

@Path("/message")
@Stateless
public class MessageResource {
	
	@EJB
	MessageServiceLocal messageService;
	
	@EJB
	UserService userService;
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getAllUsers() {
		return messageService.getMessages();
	}
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public void createUser(@FormParam("username") String username, @FormParam("password") String password, @FormParam("message") String message) {
		User user = userService.createUser(new User(username, password));
		Message userMessage = new Message(user, message);
		messageService.createMessage(userMessage);
	}
	
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessage(@PathParam("username") String username) {
		User user = userService.getUserByUsername(username);
		return messageService.getMessagesFromUser(user);
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
