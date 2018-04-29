package services;

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

	public GroupService() {
		Morphia morphia = new Morphia();
		datastore = morphia.createDatastore(new MongoClient(), "userappdb");
		morphia.mapPackage("beans");
	}

	@Override
	public void createGroup(Group group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteGroup(Group group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUserToGroup(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUserFromGroup(User user) {
		// TODO Auto-generated method stub
		
	}

}
