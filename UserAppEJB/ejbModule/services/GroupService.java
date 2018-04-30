package services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import beans.Group;
import beans.User;
import interfaces.GroupServiceLocal;

/**
 * Session Bean implementation class GroupService
 */
@Stateless(mappedName = "GroupService")
@LocalBean
public class GroupService implements GroupServiceLocal {

	private Datastore datastore;

	@EJB
	private UserService userService;
	
	public GroupService() {
		Morphia morphia = new Morphia();
		datastore = morphia.createDatastore(new MongoClient(), "userappdb");
		morphia.mapPackage("beans");
	}

	@Override
	public List<Group> getGroups() {
		return datastore.createQuery(Group.class).asList();
	}
	
	@Override
	public Group createGroup(Group group) {
		datastore.save(group);
		return getGroupByName(group.getName());
		
	}

	@Override
	public void deleteGroup(String name) {
		Group g = getGroupByName(name);
		System.out.println(g);
		datastore.delete(g);
	}
	
	@Override
	public Group getGroupByName(String name) {
		List<Group> result = datastore.createQuery(Group.class).
				filter("name ==", name).asList();
		
		if(result.size() > 0)
			return result.get(0);
		else 
			return null;
	}

	@Override
	public void addUserToGroup(String username, String name) {
		Group group = getGroupByName(name);
		User user = userService.getUserByUsername(username);

		group.getMembers().add(user);
		createGroup(group);
	}

	@Override
	public void removeUserFromGroup(String username, String name) {
		Group group = getGroupByName(name);
		User user = userService.getUserByUsername(username);
		
		for(User uu: group.getMembers()) {
			if(uu.getUsername().equals(user.getUsername())) {
				group.getMembers().remove(uu);
				break;
			}
		}
		
		createGroup(group);
	}

	@Override
	public void updateGroup(Group group) {
		Group gg = getGroupByName(group.getName());
		if(group.getAdmin()!=null) {
			gg.setAdmin(userService.getUserByUsername(group.getAdmin().getUsername()));
		}else {
			gg.setAdmin(null);
		}
		gg.setMembers(group.getMembers());
		
		createGroup(gg);
	}
}
