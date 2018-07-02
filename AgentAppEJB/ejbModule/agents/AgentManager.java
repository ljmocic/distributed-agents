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
import test.AgentCenterConfig;
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
		if(runningAgents.containsKey(aid)) {
			return null;
		}
		
		System.out.println(aid);
		AgentRemote agent = null;
		try {
			System.out.println("ejb:/" + type.getModule() +"//"+type.getName());
			agent = (AgentRemote)JNDIUtils.getContext().lookup("java:app/"+type.getModule()+"/"+type.getName());
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Agent not found");
			return null;
		}
		
		if(agent != null) {
			agent.setAID(aid);
			runningAgents.put(aid, agent);
			return agent;
		}
		
		return null;
	}

	@Override
	public void removeAgent(AID aid) {
		if(runningAgents.containsKey(aid)) {
			runningAgents.remove(aid);
		}
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
		
		for(AID aid: runningAgents.keySet()) {
			if(aid.getHost().equals(center)) {
				runningAgents.remove(aid);
			}
		}
		
		for(AgentRemote agent: agents) {
			runningAgents.put(agent.getAID(), agent);
		}
	}
}
