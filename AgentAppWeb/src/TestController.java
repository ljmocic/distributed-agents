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
		try {
			startupBeanLocal.greeting();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//Uvek ce baciti, zato je zakomentarisano
		}
	}
}
