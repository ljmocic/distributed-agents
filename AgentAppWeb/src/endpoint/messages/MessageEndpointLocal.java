package endpoint.messages;

import java.util.Collection;

import javax.ejb.Local;

import model.ACLMessage;

@Local
public interface MessageEndpointLocal {

	public Collection<String> getPerformatives();
	public Object sendACLMessage(ACLMessage message);
}
