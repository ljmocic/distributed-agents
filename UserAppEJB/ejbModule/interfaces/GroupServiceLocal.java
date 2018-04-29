package interfaces;

import javax.ejb.Local;

import beans.Group;
import beans.User;

@Local
public interface GroupServiceLocal {
	
	void createGroup(Group group);
	void deleteGroup(Group group);
	void addUserToGroup(User user);
	void removeUserFromGroup(User user);

}
