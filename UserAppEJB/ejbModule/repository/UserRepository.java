package repository;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import beans.User;
import database.MongoDBConnector;

/**
 * Session Bean implementation class UserRepository
 */
@Stateless(mappedName = "UserRepository")
@LocalBean
public class UserRepository {

	@EJB
	MongoDBConnector connector;

	public List<User> findAll() {
		List<User> users = new ArrayList<User>();
		User user = new User("test", "test");
		users.add(user);
		return users;
	}
	

}
