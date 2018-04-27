package model;

import java.util.List;

public class Group {
	
	private int id;
	
	private String name;
	
	private List<User> members;

	public Group(int id, String name, List<User> members) {
		super();
		this.id = id;
		this.name = name;
		this.members = members;
	}
	
	public Group() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}
	
	

}
