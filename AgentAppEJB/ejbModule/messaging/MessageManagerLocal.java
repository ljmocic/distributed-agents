package messaging;

import javax.ejb.Local;

import model.ACLMessage;

@Local
public interface MessageManagerLocal {

	public void sendMessage(ACLMessage message);
}
