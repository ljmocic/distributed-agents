package test;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class StartupBean
 */
@Stateless
public class StartupBean implements StartupBeanLocal {

    /**
     * Default constructor. 
     */
    public StartupBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void greeting() {
		System.out.println("Hello world");
	}
    
    

}
