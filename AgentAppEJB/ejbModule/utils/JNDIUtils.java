package utils;

import java.util.HashMap;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import model.AgentRemote;
import model.AgentType;

public class JNDIUtils {

	private Hashtable<String, Object> jndiProperties;
	private Context context;
	
	private static final String INTF = "!" + AgentRemote.class.getName();
	private static final String EXP = "java:jboss/exported/AgentApp";

	public JNDIUtils() {}
	
	public JNDIUtils(Hashtable<String, Object> jndiProperties) {
		this.jndiProperties = jndiProperties;

		try {
			context = new InitialContext(jndiProperties);
		}catch(Exception e) {
			//
		}
	}

	public Hashtable<String, Object> getJndiProperties() {
		return jndiProperties;
	}

	public void setJndiProperties(Hashtable<String, Object> jndiProperties) {
		this.jndiProperties = jndiProperties;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	public NamingEnumeration<NameClassPair> getModules() throws NamingException{
		return context.list(EXP);
	}
	
	public NamingEnumeration<NameClassPair> getBeansFromModule(String module) throws NamingException{
		return context.list(EXP + "/" + module);
	}
	
	public HashMap<String, AgentType> getBeansFromModule(String module, HashMap<String, AgentType> types) throws NamingException{
		NamingEnumeration<NameClassPair> agentList = context.list(EXP + "/" + module);
		while (agentList.hasMore()) {
			String name = agentList.next().getName();
			AgentType agType = parseNameIfValid(module, name);
			if (agType != null) {
				types.put(agType.getName(), agType);
			}
		}
		
		return types;
	}
	
	private AgentType parseNameIfValid(String module, String name) {
		if (name != null && name.endsWith(INTF)) {
			return new AgentType(name, module);
		}
		return null;
	}
}
