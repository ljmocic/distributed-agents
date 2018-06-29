package model;

/**
 * Session Bean implementation class Agent
 */
public abstract class Agent implements AgentRemote {

    /**
     * Default constructor. 
     */
	private AID id;
	
    @Override
	public AID getAID() {
		return id;
	}

    @Override
	public void setAID(AID id) {
		this.id = id;
	}
}
