package authentication;

import javax.ejb.Singleton;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.spi.HttpRequest;

import model.User;

/**
 * Session Bean implementation class AuthenticationBean
 */
@Singleton
public class AuthenticationBean implements AuthenticationBeanRemote {

    /**
     * Default constructor. 
     */
    public AuthenticationBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public String register(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String login(User u, @Context HttpRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String logout(HttpRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
