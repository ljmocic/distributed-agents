package resources;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import beans.User;
import interfaces.SynchronizationResourceInterface;
import interfaces.SynchronizationServiceLocal;
import services.UserService;

@Stateless
public class SynchronizationResource implements SynchronizationResourceInterface{

	@EJB
	SynchronizationServiceLocal synchronizationService;
	
	@EJB
	UserService userService;
	
	@Override
	public void synchronize(List<User> activeUsersFromNode) {
		synchronizationService.synchronizeNode(activeUsersFromNode);
	}

	@Override
	public List<User> getActiveUsers() {
		return synchronizationService.getAllActiveUsers();
	}

	@Override
	public boolean checkIfLoggedIn(String username) {
		User u = userService.getUserByUsername(username);
		if(u!=null)
			return synchronizationService.checkIfLoggedIn(u);
		return false;
	}

}
