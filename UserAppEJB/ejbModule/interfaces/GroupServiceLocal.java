package interfaces;

import java.util.List;

import javax.ejb.Local;

import beans.Group;

@Local
public interface GroupServiceLocal {
	
	Group createGroup(Group group);
	void updateGroup(Group group);
	void deleteGroup(String name);
	void addUserToGroup(String username, String name);
	void removeUserFromGroup(String username, String name);
	Group getGroupByName(String name);
	List<Group> getGroups();
	List<Group> getGroupsOfUser(String username);
}
