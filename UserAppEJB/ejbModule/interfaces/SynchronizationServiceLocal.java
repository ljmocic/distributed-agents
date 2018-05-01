package interfaces;

import java.util.List;

import javax.ejb.Local;

import beans.User;

@Local
public interface SynchronizationServiceLocal {

	public void synchronizeNode(List<User> activeUsersFromNode);
	public List<User> getAllActiveUsers();
	public boolean checkIfLoggedIn(User u);
	
}
