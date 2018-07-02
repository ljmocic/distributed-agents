package endpoint.messages;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.ACLMessage;
import model.ACLPerformative;

@Stateless
@LocalBean
@Path("/messages")
public class MessageEndpoint implements MessageEndpointLocal{

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Collection<String> getPerformatives(){
		ACLPerformative[] perfs = ACLPerformative.values();
		Collection<String> c = new ArrayList<>();
		for(ACLPerformative perf: perfs) {
			c.add(perf.name());
		}
		
		return c;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Object sendACLMessage(ACLMessage message) {
		//TODO sendMessage by msm
		return null;
	}
	
}
