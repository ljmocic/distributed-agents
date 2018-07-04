package model;

import messaging.MessageManager;

public interface AgentRemote {

	public void handleMessage(ACLMessage message);
	
	public void setAID(AID aid);
	
	public AID getAID();
	
    public AgentDTO serialize(AgentRemote agent);
    
    public AgentRemote deserialize(AgentDTO agentDTO);
    
    public MessageManager getMessageManager();
}
