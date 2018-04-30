package services;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;
import javax.servlet.ServletRequest;

import beans.Host;
import beans.User;

@Singleton
public class AuthorizationService {

	private List<User> activeUsers;
	
	public AuthorizationService() {
		activeUsers = new ArrayList<>();
	}
	
	public void addActiveUser(User u) {
		if(!activeUsers.contains(u)) {
			activeUsers.add(u);
		}
	}
	
	public void removeActiveUser(User u) {
		if(activeUsers.contains(u)) {
			activeUsers.remove(u);
		}
	}
	
	public Host createHost(ServletRequest request) {
		String address = request.getRemoteAddr() + request.getRemotePort();
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
