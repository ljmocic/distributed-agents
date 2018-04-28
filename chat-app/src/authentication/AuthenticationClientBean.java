package authentication;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import javax.ejb.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.Host;
import model.User;

/**
 * Session Bean implementation class AuthenticationClientBean
 */
@Singleton
@Path("/chat-app")
public class AuthenticationClientBean implements AuthenticationClientBeanRemote {

    /**
     * Default constructor. 
     */
	private HashMap<String, User> activeUsers;
	private javax.naming.Context context;
	private String remoteName;
	
	{
		try {
			context = new InitialContext();
			remoteName = "ejb:/user-app/" + "AuthenticationBean!"
					+ AuthenticationBeanRemote.class.getName();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public AuthenticationClientBean() {
    	activeUsers = new HashMap<String, User>();
    }

    @POST
    @Path("/register")
	@Override
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public User register(User u/*, @Context HttpServletRequest request*/) {
    	System.out.println("Authentication Bean Client register");
    	try {
			AuthenticationBeanRemote authBean = (AuthenticationBeanRemote)context.lookup(remoteName);
			/*User retVal = authBean.register(u, request);
			
			if(retVal!=null) {
				retVal.setHost(generateHost(request));
				activeUsers.put(retVal.getUsername(), retVal);
			}
			
			return retVal;*/
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

    @POST
    @Path("/login")
	@Override
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public User login(User u/*, @Context HttpServletRequest request*/) {
    	System.out.println("Authentication Bean Client login");
    	try {
			AuthenticationBeanRemote authBean = (AuthenticationBeanRemote)context.lookup(remoteName);
			/*User retVal = authBean.login(u, request);
			
			if(retVal!=null) {
				//retVal.setHost(generateHost(request));
				activeUsers.put(retVal.getUsername(), retVal);
			}
			
			return retVal;*/
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void logout(/*@Context HttpServletRequest request*/) {
		System.out.println("Authentication Bean Client logount");
		try {
			AuthenticationBeanRemote authBean = (AuthenticationBeanRemote)context.lookup(remoteName);
			//User u = (User)request.getSession().getAttribute("user");
			
			//authBean.logout(request);
			/*try {
				activeUsers.remove(u.getUsername());
			}catch(Exception e) {
				e.printStackTrace();
			}*/
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Host generateHost(ServletRequest req) {
		Host h = null;
		try {
			h = new Host();
			h.setAddress(req.getLocalAddr()+":"+req.getLocalPort());
			h.setAlias(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return h;
	}
    
}
