package interfaces;

import java.util.List;

import javax.ejb.Local;

import beans.User;

@Local
public interface UserServiceLocal {

	List<User> getUsers();
	User createUser(User user);
	User getUserByUsername(String username);
	void updateUser(User user);
	void deleteUser(String username);
    void removeFriend(String username, String friend);
    void addFriend(String username, String friend);
    List<User> getFriends(String username);
}
