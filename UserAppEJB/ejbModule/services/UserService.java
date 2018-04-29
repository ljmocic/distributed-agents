package services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import beans.User;
import interfaces.UserServiceLocal;
import repository.UserRepository;

/**
 * Session Bean implementation class UserService
 */
@Stateless(mappedName = "UserService")
@LocalBean
public class UserService implements UserServiceLocal {

	@EJB
	UserRepository userRepository;

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

}
