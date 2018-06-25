package model;

public class AgentType {

	private String name;
	private Object module;
	
	public AgentType() {}

	public AgentType(String name, Object module) {
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

	public Object getModule() {
		return module;
	}

	public void setModule(Object module) {
		this.module = module;
	}
	
}
