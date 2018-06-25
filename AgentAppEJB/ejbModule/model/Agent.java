package model;

import java.util.Date;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class Agent
 */
@Stateless
public class Agent implements AgentRemote {

    /**
     * Default constructor. 
     */
	private AID id;
	
    public Agent() {}

	public AID getId() {
		return id;
	}

	public void setId(AID id) {
		this.id = id;
	}

	@Override
	public void handleMessage(ACLMessage message) {
		System.out.println((new Date())+" -> handling message ...");
	}
}
