package model;

import java.io.Serializable;

public class AID implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private AgentCenter host;
	private AgentType type;
	
	public AID () {}

	public AID(String name, AgentCenter host, AgentType type) {
		super();
		this.name = name;
		this.host = host;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AgentCenter getHost() {
		return host;
	}

	public void setHost(AgentCenter host) {
		this.host = host;
	}

	public AgentType getType() {
		return type;
	}

	public void setType(AgentType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "AID{name="+name+",type="+type+",host="+host+"}";
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof AID)) {
			return false;
		}
		
		AID a = (AID) o;
		if(a.getName().equals(name) && a.getType().equals(type) && a.getHost().equals(host)) {
			return true;
		}
		
		return false;
	}
}
