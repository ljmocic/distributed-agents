package services;

import java.util.List;

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
	
	private Datastore datastore;

	public UserService() {
		Morphia morphia = new Morphia();
		datastore = morphia.createDatastore(new MongoClient(), "userappdb");
		morphia.mapPackage("beans");
	}

	public List<User> getUsers() {
		return datastore.createQuery(User.class).asList();
	}
	
	public void createUser(User user) {
		datastore.save(user);
	}
	
	public User getUserByUsername(String username) {
		List<User> users = datastore.createQuery(User.class)
                .filter("username ==", username)
                .asList();
		return users.size() > 0 ? users.get(0) : null;
	}
	
	public void updateUser(User user) {
		createUser(user);
	}
	
	public void deleteUser(User user) {
		User userToDelete = datastore.get(User.class, user.getId());
		datastore.delete(userToDelete);
	}
	
}
