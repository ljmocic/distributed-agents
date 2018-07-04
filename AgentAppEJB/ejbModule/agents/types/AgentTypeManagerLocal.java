package agents.types;

import java.util.Collection;
import java.util.HashMap;

import javax.ejb.Local;
import javax.naming.NamingException;

import model.AgentType;

@Local
public interface AgentTypeManagerLocal {

	public Collection<AgentType> getAgentTypesOnNode() throws NamingException;
	
	public void setAgentTypesOnSystem(HashMap<String, Collection<AgentType>> types);
	
	public Collection<AgentType> getAgentTypesOnSystem();
	
	public boolean addTypesFromNode(String center, Collection<AgentType> types);
	
	public void removeTypesFromNode(String center);
	
	public Collection<AgentType> getTypesFromCenter(String center);

	public AgentType getTypeWithName(String name);
	
	public HashMap<String, Collection<AgentType>> getTypesAsMap();
}
