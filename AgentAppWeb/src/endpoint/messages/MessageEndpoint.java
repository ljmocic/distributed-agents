package endpoint.messages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
			List<String> hosts = new ArrayList<>();
			
			for(int i=0; i<message.getReceivers().length; i++) {
				if(!hosts.contains(message.getReceivers()[i].getHost().getAlias())) {
					hosts.add(message.getReceivers()[i].getHost().getAlias());
					
					if(message.getReceivers()[i].getHost().getAlias().equals(AgentCenterConfig.nodeName)) {
						receiveACLMessage(message);
					}else {
						Entity<ACLMessage> msg = Entity.entity(message, MediaType.APPLICATION_JSON);
						ResteasyClientFactory.target(message.getReceivers()[i].getHost().getAddress()+"/AgentAppWeb/rest/messages")
							.request().put(msg);
					}
				}
			}
		}else {
			forwardMessage(message);
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public void receiveACLMessage(ACLMessage message) {
		messageManager.sendMessage(message);
	}
	
	
	private void forwardMessage(ACLMessage message) {
		Entity<ACLMessage> msg = Entity.entity(message, MediaType.APPLICATION_JSON);
		ResteasyClientFactory.target(message.getSender().getHost().getAddress()+"/AgentAppWeb/rest/messages")
			.request().post(msg);
	}
}
