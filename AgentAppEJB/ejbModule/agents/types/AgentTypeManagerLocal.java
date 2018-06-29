package agents.types;

import java.util.Collection;

import javax.ejb.Local;
import javax.naming.NamingException;

import model.AgentType;

@Local
public interface AgentTypeManagerLocal {

	public Collection<AgentType> getAgentTypesOnNode() throws NamingException;
}
