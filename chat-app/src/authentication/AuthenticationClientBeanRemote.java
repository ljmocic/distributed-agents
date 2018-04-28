package authentication;

import javax.ejb.Remote;
import javax.servlet.http.HttpServletRequest;

import model.User;

@Remote
public interface AuthenticationClientBeanRemote {

	public User register(User u/*, HttpServletRequest request*/);
	
	public User login(User u/*, HttpServletRequest request*/);
	
	public void logout(/*HttpServletRequest request*/);
	
}
