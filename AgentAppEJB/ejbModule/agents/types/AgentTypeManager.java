package agents.types;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import model.AgentType;
import utils.JNDIUtils;

/**
 * Session Bean implementation class AgentTypeManagement
 */
@Stateless
@LocalBean
public class AgentTypeManager implements AgentTypeManagerLocal {
	
	private JNDIUtils jndiUtils;
	
    public AgentTypeManager() {
        // TODO Auto-generated constructor stub
    }
    
    @PostConstruct
	public void postConstruct() {
    	Hashtable<String, Object> jndiProps = new Hashtable<>();
		jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
    	jndiUtils = new JNDIUtils(jndiProps);
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
}
