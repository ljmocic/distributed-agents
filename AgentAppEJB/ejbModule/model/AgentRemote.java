package model;

public interface AgentRemote {

	public void handleMessage(ACLMessage message);
	
	public void setAID(AID aid);
	
	public AID getAID();
}
