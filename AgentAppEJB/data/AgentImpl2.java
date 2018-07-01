package model;

import javax.ejb.Remote;
import javax.ejb.Stateful;

@Stateful
@Remote(AgentRemote.class)
public class AgentImpl2 extends Agent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void handleMessage(ACLMessage message) {
		// TODO Auto-generated method stub
		
	}

}
