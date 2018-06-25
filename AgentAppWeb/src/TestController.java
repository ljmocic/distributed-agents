import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import test.StartupBeanLocal;

@Path("/test")
@Stateless
public class TestController {
	
	@EJB
	StartupBeanLocal startupBeanLocal;
	
	@GET
	public void greetNMeet() {
		System.out.println("Hi again");
		startupBeanLocal.greeting();
	}
}
