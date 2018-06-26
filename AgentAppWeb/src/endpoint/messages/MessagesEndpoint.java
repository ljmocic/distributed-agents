package endpoint.messages;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.ACLMessage;

@Path("/messages")
public class MessagesEndpoint {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<String> getPerformatives(){
		//TODO return performatives (ACLPerformative -> JNDI lookup probably)
		return null;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object sendACLMessage(ACLMessage message) {
		//TODO sendMessage by msm
		return null;
	}
	
}
