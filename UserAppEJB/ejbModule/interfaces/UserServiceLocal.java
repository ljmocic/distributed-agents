package interfaces;

import java.util.List;

import javax.ejb.Local;

import beans.User;

@Local
public interface UserServiceLocal {

	List<User> getUsers();
	void createUser(User user);
	User getUserByUsername(String username);
	void updateUser(User user);
	void deleteUser(User user);

}
