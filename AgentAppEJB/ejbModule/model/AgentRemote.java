package model;

import javax.ejb.Remote;

@Remote
public interface AgentRemote {

	public void handleMessage(ACLMessage message);
}
