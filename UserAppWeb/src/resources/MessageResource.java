package resources;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
	public List<Message> getAllMessages() {
		return messageService.getMessages();
	}
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Message createMessage(Message message) {
		return messageService.createMessage(message);
	}
	
	@GET
	@Path("/user1/{username1}/user2/{username2}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessage(@PathParam("username1") String username1, @PathParam("username2") String username2) {
		User user1 = userService.getUserByUsername(username1);
		User user2 = userService.getUserByUsername(username2);
		return messageService.getMessagesFromChat(user1, user2);
	}
	
	@GET
	@Path("/group/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessageForGroup(@PathParam("name") String name) {
		return messageService.getMessagesFromGroup(name);
	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateMessage(Message message) {
		messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/delete/{messageId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteMessage(Message message) {
		messageService.deleteMessage(message);
	}

}
