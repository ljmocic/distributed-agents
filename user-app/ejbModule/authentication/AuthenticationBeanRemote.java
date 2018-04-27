package authentication;

import javax.ejb.Remote;

import org.jboss.resteasy.spi.HttpRequest;

import model.User;

@Remote
public interface AuthenticationBeanRemote {

	public String register(User u);
	
	public String login(User u, HttpRequest request);
	
	public String logout(HttpRequest request);
}
