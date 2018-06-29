package model;

import java.util.Date;

import javax.ejb.Remote;
import javax.ejb.Stateful;

@Stateful
@Remote(AgentRemote.class)
public class AgentImpl extends Agent {

	@Override
	public void handleMessage(ACLMessage message) {
		System.out.println(new Date() + " -> handling message ...");
	}

}
