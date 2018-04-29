package interfaces;

import java.util.List;

import javax.ejb.Local;

import beans.User;

@Local
public interface UserServiceLocal {

	List<User> getUsers();

}
