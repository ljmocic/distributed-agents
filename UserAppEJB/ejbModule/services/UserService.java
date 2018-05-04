package services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import beans.User;
import interfaces.UserServiceLocal;

@Stateless(mappedName = "UserService")
@LocalBean
public class UserService implements UserServiceLocal {
	
	@EJB
	private NodeService nodeService;
	
	private Datastore datastore;

	public UserService() {
		Morphia morphia = new Morphia();
		datastore = morphia.createDatastore(new MongoClient(), "userappdb");
		morphia.mapPackage("beans");
	}

	@Override
	public List<User> getUsers() {
		return datastore.createQuery(User.class).asList();
	}
	
	@Override
	public User createUser(User user) {
		System.out.println(user.getFriends());
		datastore.save(user);
		nodeService.notifyNodes("users");
		return getUserByUsername(user.getUsername());
	}
	
	@Override
	public User getUserByUsername(String username) {
		List<User> users = datastore.createQuery(User.class)
                .filter("username ==", username)
                .asList();
		
		User u = users.size() > 0 ? users.get(0) : null;
		return u;
	}
	
	@Override
	public void updateUser(User user) {
		User uu = getUserByUsername(user.getUsername());
		uu.setFirstName(user.getFirstName());
		uu.setLastName(user.getLastName());
		uu.setPassword(user.getPassword());
		uu.setFriends(user.getFriends());
		
		createUser(uu);
	}
	
	@Override
	public void deleteUser(String username) {
		User userToDelete = getUserByUsername(username);
		datastore.delete(userToDelete);
		
		List<User> uuu = datastore.createQuery(User.class).asList();
		for(User uu: uuu) {
			if(uu.getUsername()==null) {
				datastore.delete(uu);
				break;
			}
		}
	}
	
	@Override
	public void addFriend(String activeUser, String toAdd) {
		User u = getUserByUsername(activeUser);
		User uToAdd = getUserByUsername(toAdd);
		
		System.out.println(u.getFriends());
		u.getFriends().add(uToAdd);
		
		createUser(u);
	}
	
	@Override
	public void removeFriend(String activeUser, String toUnfriend) {
		User u = getUserByUsername(activeUser);
		User uToUnfriend = getUserByUsername(toUnfriend);
		
		boolean flag = false;
		for(User uu: u.getFriends()) {
			if(uu.getUsername().equals(uToUnfriend.getUsername())) {
				u.getFriends().remove(uu);
				flag = true;
				break;
			}
		}
		
		if(!flag) {
			for(User uu: uToUnfriend.getFriends()) {
				if(uu.getUsername().equals(activeUser)) {
					uToUnfriend.getFriends().remove(uu);
					flag = true;
					break;
				}
			}
		}
		
		createUser(u);
		createUser(uToUnfriend);
	}
	
	@Override
	public List<User> getFriends(String username) {
		User u = getUserByUsername(username);
		List<User> allUsers = getUsers();
		
		List<User> friends = u.getFriends();
		
		for(User uu: allUsers) {
			if(!uu.getUsername().equals(username)){
				for(User fr: uu.getFriends()) {
					if(fr.getUsername().equals(username)) {
						friends.add(uu);
						break;
					}
				}
			}
		}
		
		return friends;
	}

}
