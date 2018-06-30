package model;

import java.io.Serializable;

/**
 * Session Bean implementation class Agent
 */
public abstract class Agent implements AgentRemote, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
