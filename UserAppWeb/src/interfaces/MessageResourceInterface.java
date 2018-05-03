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

import beans.Message;

public interface MessageResourceInterface {

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getAllMessages();
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object createMessage(Message message);
	
	@GET
	@Path("/user1/{username1}/user2/{username2}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessage(@PathParam("username1") String username1, @PathParam("username2") String username2);
	
	@GET
	@Path("/group/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessageForGroup(@PathParam("name") String name);

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateMessage(Message message);
	
	@DELETE
	@Path("/delete/{messageId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteMessage(Message message);
	
}
