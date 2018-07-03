package model;

import java.io.Serializable;
import java.util.HashMap;

public class AgentDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private Class<?> agentClass;
	private HashMap<String, Object> fieldMap;
	
	public AgentDTO(/*Class<?> agentClass,*/ HashMap<String, Object> fieldMap) {
		super();
		//this.agentClass = agentClass;
		this.fieldMap = fieldMap;
	}

	public AgentDTO() {
		super();
		this.fieldMap = new HashMap<>();
	}

	/*public Class<?> getAgentClass() {
		return agentClass;
	}

	public void setAgentClass(Class<?> agentClass) {
		this.agentClass = agentClass;
	}*/

	public HashMap<String, Object> getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(HashMap<String, Object> fieldMap) {
		this.fieldMap = fieldMap;
	}
	
	@Override
	public String toString(){
		String retVal = /*agentClass.getName() + */" [";
		for(String key: fieldMap.keySet()) {
			retVal += key + "=" + fieldMap.get(key)+", ";
		}
		
		retVal.substring(0, retVal.length()-2);
		retVal += " ]\n";
		
		return retVal;
	}
}
