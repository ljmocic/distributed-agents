package endpoint.messages;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import messaging.MessageManagerLocal;
import model.ACLMessage;
import model.ACLPerformative;
import utils.AgentCenterConfig;
import utils.ResteasyClientFactory;

@Stateless
@LocalBean
@Path("/messages")
public class MessageEndpoint implements MessageEndpointLocal{

	@EJB
	MessageManagerLocal messageManager;
	
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
	@Override
	public void sendACLMessage(ACLMessage message) {
		if(message.getSender().getHost().getAlias().equals(AgentCenterConfig.nodeName)) {
			System.out.println("Endpoint received message to handle");
			System.out.println(message);
			messageManager.sendMessage(message);
		}else {
			forwardMessage(message);
		}
	}
	
	private void forwardMessage(ACLMessage message) {
		System.out.println("Message forward");
		Entity<ACLMessage> msg = Entity.entity(message, MediaType.APPLICATION_JSON);
		ResteasyClientFactory.target(message.getSender().getHost().getAddress()+"/AgentAppWeb/rest/messages")
			.request().post(msg);
	}
}
