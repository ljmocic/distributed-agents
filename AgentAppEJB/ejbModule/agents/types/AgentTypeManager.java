package agents.types;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import model.AgentType;
import test.AgentCenterConfig;
import utils.JNDIUtils;

/**
 * Session Bean implementation class AgentTypeManagement
 */
@Singleton
@Startup
public class AgentTypeManager implements AgentTypeManagerLocal {
	
	private JNDIUtils jndiUtils;
	private HashMap<String, Collection<AgentType>> typesOnSystem;
	
    public AgentTypeManager() {
        // TODO Auto-generated constructor stub
    }
    
    @PostConstruct
	public void initMap() {
    	Hashtable<String, Object> jndiProps = new Hashtable<>();
		jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
    	jndiUtils = new JNDIUtils(jndiProps);
    	typesOnSystem = new HashMap<>();
	}

	@Override
	public Collection<AgentType> getAgentTypesOnNode() throws NamingException{
		HashMap<String, AgentType> typesOnNode = new HashMap<>();
		
		NamingEnumeration<NameClassPair> moduleList = jndiUtils.getModules();
		while (moduleList.hasMore()) {
			String module = moduleList.next().getName();
			typesOnNode = jndiUtils.getBeansFromModule(module, typesOnNode);
		}
		return typesOnNode.values();
	}

	@Override
	public void setAgentTypesOnSystem(HashMap<String, Collection<AgentType>> types) {
		System.out.println("setAgentTypesOnSystem");
		for(String ac: types.keySet()) {
			typesOnSystem.put(ac, types.get(ac));
		}
	}

	@Override
	public boolean addTypesFromNode(String center, Collection<AgentType> types) {
		System.out.println("addTypesFromNode");
		if(typesOnSystem.containsKey(center))
			return false;
		
		typesOnSystem.put(center, types);
		return true;
	}

	@Override
	public HashMap<String, Collection<AgentType>> getAgentTypesOnSystem() {
		return typesOnSystem;
	}

	@Override
	public void removeTypesFromNode(String center) {
		System.out.println("removeTypesFromNode");
		if(!center.equals(AgentCenterConfig.nodeName)) {
			if(typesOnSystem.containsKey(center))
				typesOnSystem.remove(center);
		}else {
			typesOnSystem = new HashMap<>();
		}
	}

	@Override
	public Collection<AgentType> getTypesFromCenter(String center){
		return typesOnSystem.get(center);
	}
}
