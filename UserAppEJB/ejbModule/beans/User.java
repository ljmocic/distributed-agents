package beans;

import java.util.ArrayList;
import java.util.List;

public class User {

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	
	private Host host;
	
	private List<String> friends;
	
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = "";
		this.lastName = "";
		this.friends = new ArrayList<String>();
	}

	public User(String username, String password, String firstName, String lastName, List<String> friends) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.friends = friends;
	}
	
	public User() {}

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

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}
	
	
}
