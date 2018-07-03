package test;

import javax.annotation.Resource;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import messaging.MDBConsumer;

/**
 * Session Bean implementation class StartupBean
 */
@Stateless
@Startup
public class StartupBean implements StartupBeanLocal {

    /**
     * Default constructor. 
     */

    @Resource(lookup = "java:jboss/exported/jms/RemoteConnectionFactory")
	private ConnectionFactory connectionFactory;

    public StartupBean() {
        // TODO Auto-generated constructor stub
    }
	
	@Override
	public void greeting() throws Exception{
		Context context = new InitialContext();
		System.out.println("Hello world");
		Queue q = (Queue)context.lookup("jms/queue/AgentMessages");
		context.close();
		Connection c = connectionFactory.createConnection("username", "password");
		Session s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
		c.start();
		
		MessageConsumer consumer = s.createConsumer(q);
		consumer.setMessageListener(new MDBConsumer());
		TextMessage msg = s.createTextMessage("Queue message!");
		long sent = System.currentTimeMillis();
		msg.setLongProperty("sent", sent);
		MessageProducer producer = s.createProducer(q);
		producer.send(msg);
		producer.close();
		consumer.close();
		c.stop();

	}
    
    

}
