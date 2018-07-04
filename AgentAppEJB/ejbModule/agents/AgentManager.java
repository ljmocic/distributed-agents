package agents;

import java.util.Collection;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import agents.types.AgentTypeManagerLocal;
import connections.ConnectionManagerLocal;
import model.AID;
import model.AgentCenter;
import model.AgentRemote;
import model.AgentType;
import utils.AgentCenterConfig;
import utils.JNDIUtils;

/**
 * Session Bean implementation class AgentManager
 */
@Singleton
@Startup
public class AgentManager implements AgentManagerLocal {

    /**
     * Default constructor. 
     */
	private HashMap<AID, AgentRemote> runningAgents;
	
	@EJB
	ConnectionManagerLocal connectionManager;
	
	@EJB
	AgentTypeManagerLocal agentTypeManager;
	
    public AgentManager() {
        // TODO Auto-generated constructor stub
    }
    
    @PostConstruct
    public void initMap() {
    	runningAgents = new HashMap<>();
    }

	@Override
	public AgentRemote startAgent(String agentClass, String name) {
		AgentType type = agentTypeManager.getTypeWithName(agentClass);
		AgentCenter host = connectionManager.getAgentCenter(AgentCenterConfig.nodeName);
		
		AID aid = new AID(name, host, type);
		for(AID dd: runningAgents.keySet()) {
			if(dd.equals(aid)) {
				System.out.println("Agent already running: "+aid);
				return null;
			}
		}
		
		AgentRemote agent = null;
		try {
			agent = JNDIUtils.agentLookup(type.getModule(),type.getName());
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Agent type not found: "+type.getName());
			return null;
		}
		
		if(agent != null) {
			agent.setAID(aid);
			runningAgents.put(aid, agent);
			System.out.println("Agent "+aid+" started");
			return agent;
		}
		
		return null;
	}

	@Override
	public boolean removeAgent(AID aid) {
		for(AID aidd: runningAgents.keySet()) {
			if(aidd.equals(aid)) {
				System.out.println("Agent "+aidd+" stopped");
				runningAgents.remove(aidd);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Collection<AgentRemote> getRunningAgents() {
		return runningAgents.values();
	}

	@Override
    public Collection<AID> getRunningAIDs(){
		return runningAgents.keySet();
	}

	@Override
	public void syncAgents(AgentCenter center, Collection<AgentRemote> agents) {
		Object[] aids = runningAgents.keySet().toArray();
		for(int i = aids.length-1; i>=0; i--) {
			if(((AID)aids[i]).getHost().equals(center)) {
				runningAgents.remove(aids[i]);
			}
		}
		
		System.out.println("Synced agents from "+center.getAlias());
		for(AgentRemote agent: agents) {
			System.out.println("\t"+agent.getAID());
			runningAgents.put(agent.getAID(), agent);
		}
		
	}
	
	@Override
	public AgentRemote getRunningAgent(AID aid) {
		for(AID agent: runningAgents.keySet()) {
			if(agent.equals(aid)) {
				return runningAgents.get(agent);
			}
		}
		
		return null;
	}
}
