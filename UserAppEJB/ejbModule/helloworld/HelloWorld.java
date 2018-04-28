package helloworld;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Session Bean implementation class HelloWorld
 */
@Singleton
@LocalBean
@Startup
public class HelloWorld {

    /**
     * Default constructor. 
     */
    public HelloWorld() {
    	System.out.println("Init hello world");
    }
    
    public String helloWorld() {
    	return "Hello world!";
    }

}
