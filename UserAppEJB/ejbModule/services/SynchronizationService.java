package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import beans.Host;
import beans.User;
import interfaces.SynchronizationServiceLocal;

@LocalBean
@Singleton
public class SynchronizationService implements SynchronizationServiceLocal {

	@EJB
	NodeService nodeService;
	
	HashMap<String, List<User>> activeUsers;
	
	public SynchronizationService() {
		activeUsers = new HashMap<>();
	}
	
	@Override
	public void synchronizeNode(List<User> activeUsersFromNode) {
		Host h = activeUsersFromNode.get(0).getHost();
		System.out.println("Dobio obavestenje o sinhr od cvora "+h.getAlias());
		activeUsers.put(h.getAlias(), activeUsersFromNode);
		printActiveUsers();
		nodeService.notifyNodes();
	}

	private void printActiveUsers() {
		for(List<User> vals: activeUsers.values()) {
			System.out.println("---------------------------");
			for(User uu: vals) {
				System.out.println("\tUser: "+uu.getUsername()+" -> node: "+uu.getHost().getAlias());
			}
		}
	}
	
	@Override
	public List<User> getAllActiveUsers() {
		List<User> alls = new ArrayList<>();
		for(List<User> vals: activeUsers.values()) {
			alls.addAll(vals);
		}
		
		return alls;
	}

	@Override
	public boolean checkIfLoggedIn(User u) {
		for(List<User> vals: activeUsers.values()) {
			for(User uu: vals) {
				if(uu.getUsername().equals(u.getUsername())) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	

}
