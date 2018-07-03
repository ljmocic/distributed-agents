package endpoint.messages;

import java.util.Collection;

import javax.ejb.Local;

import model.ACLMessage;

@Local
public interface MessageEndpointLocal {

	public Collection<String> getPerformatives();
	public void sendACLMessage(ACLMessage message);
}
