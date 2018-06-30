package model;

import java.io.Serializable;

public class AgentType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String module;
	
	public AgentType() {}

	public AgentType(String name, String module) {
		super();
		this.name = name;
		this.module = module;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof AgentType)) {
			return false;
		}
		
		AgentType a = (AgentType)o;
		if(a.getName().equals(name) && a.getModule().equals(module)) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return module+" : "+name;
	}
}
