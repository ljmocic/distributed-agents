package resources;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.User;
import interfaces.UserResourceInterface;
import services.AuthorizationService;
import services.UserServiceProxy;

@Path("/user")
@Stateless
public class UserResourceProxy implements UserResourceInterface {
	
	@EJB
	UserServiceProxy userService;

	@EJB
	AuthorizationService authorizationService;
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		return userService.getRest().getAllUsers();
	}

	@Override
	public String loginUser(String username, String password) {
		String response = userService.getRest().loginUser(username, password);
		if(response.equals("User succesfully logged in")) {
			User u = null;
			try {
				u = userService.getRest().getUser(username);
			}catch(Exception e) {
				u = new User();
				u.setId(null);
				u.setUsername(username);
				u.setPassword(password);
			}
			u.setHost(authorizationService.createHost());
			authorizationService.addActiveUser(u);
		}
		
		return response;
	}

	@Override
	public String logoutUser(@PathParam("username") String username) {
		authorizationService.removeActiveUser(username);
		return userService.getRest().logoutUser(username);
	}

	@Override
	public User createUser(String username, String password) {
		return userService.getRest().createUser(username, password);
	}

	@Override
	public User getUser(String username) {
		return userService.getRest().getUser(username);
	}

	@Override
	public void updateUser(User user, String username) {
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
	public List<User> getAllFriendsOf(String username) {
		return userService.getRest().getAllFriendsOf(username);
	}

}
