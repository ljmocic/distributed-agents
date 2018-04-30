package beans;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("users")
public class User {
	
	@Id
	private ObjectId id;
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	
	//@Reference
	private Host host;
	
	private List<User> friends;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.firstName = "";
		this.lastName = "";
		this.friends = new ArrayList<User>();
	}

	public User(String username, String password, String firstName, String lastName) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.friends = new ArrayList<User>();
	}
	
	public User() {
		this.friends = new ArrayList<User>();
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}
	
	@Override
	public boolean equals(Object u) {
		if(u instanceof User) {
			if(((User)u).getId().equals(id)) {
				return true;
			}
			
			if(((User)u).getUsername().equals(username)) {
				return true;
			}
		}
		
		return false;
	}
	
}
