package services;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import beans.Host;
import beans.User;

@Singleton
public class AuthorizationService {

	private List<User> activeUsers;
	
	@EJB
	SynchronizationService synchronizationService;
	
	public AuthorizationService() {
		activeUsers = new ArrayList<>();
	}
	
	public void addActiveUser(User user) {
		activeUsers.add(user);
		synchronizationService.getTarget().request().post(Entity.entity(activeUsers, MediaType.APPLICATION_JSON));
	}
	
	public void removeActiveUser(String username) {
		for (User user : activeUsers) {
			if(user.getUsername().equals(username));
		}
		synchronizationService.getTarget().request().post(Entity.entity(activeUsers, MediaType.APPLICATION_JSON));
	}
	
	public Host createHost() {
		String address = "";
		try {
			address = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String alias = "";
		try {
			alias = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Host(address, alias);
	}
	
	public List<User> getActiveUsers(){
		return activeUsers;
	}
}
