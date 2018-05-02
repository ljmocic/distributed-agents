package beans;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity("groups")
public class Group {
	
	@Id
	@JsonIgnore
	private ObjectId id;
	private String name;
	
	@Reference
	private User admin;
	
	@Reference
	private List<User> members;
	
	public Group(String name, User admin) {
		super();
		this.id = new ObjectId();
		this.name = name;
		this.admin = admin;
		this.members = new ArrayList<User>();
	}
	
	public Group() {
		this.members = new ArrayList<User>();
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

}
