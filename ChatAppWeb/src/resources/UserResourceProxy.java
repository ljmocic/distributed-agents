package resources;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import interfaces.UserResourceInterface;
import services.UserServiceProxy;

@Path("/user")
@Stateless
public class UserResourceProxy implements UserResourceInterface {
	
	@EJB
	UserServiceProxy userService;

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object> getAllUsers() {
		return userService.getRest().getAllUsers();
	}

	@Override
	public String loginUser(HttpServletRequest request, String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String logoutUser(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createUser(String username, String password) {
		return userService.getRest().createUser(username, password);
	}

	@Override
	public Object getUser(String username) {
		return userService.getRest().getUser(username);
	}

	@Override
	public void updateUser(Object user, String username) {
		userService.getRest().updateUser(user, username);
		
	}

	@Override
	public void deleteUser(String username) {
		userService.getRest().deleteUser(username);
	}

	@Override
	public void addFriend(String username, String friend) {
		userService.getRest().addFriend(username, friend);
	}

	@Override
	public void removeFriend(String username, String friend) {
		userService.getRest().removeFriend(username, friend);
	}

	@Override
	public List<Object> getAllFriendsOf(String username) {
		return userService.getRest().getAllFriendsOf(username);
	}

}
