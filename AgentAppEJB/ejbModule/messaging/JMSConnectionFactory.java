package messaging;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.Session;

@Singleton
@LocalBean
public class JMSConnectionFactory {

	@Resource(lookup = "java:jboss/exported/jms/RemoteConnectionFactory")
	private ConnectionFactory connectionFactory;
	private Connection c;
	
	@Resource(lookup = "java:jboss/exported/jms/queue/AgentMessages")
	private Queue q;
	
	 @PostConstruct
     public void initConnection() {
    	try {
			c = connectionFactory.createConnection();
			c.start();
    	}catch(JMSException me) {
			me.printStackTrace();
		}
     }
	 
	 @PreDestroy
     public void closeConnection() {
		try {
			c.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
	 
	 public Session getSession() {
		try {
			return c.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		} catch (JMSException ex) {
			throw new IllegalStateException(ex);
		}
	 }
	 
	 public MessageProducer getProducer(Session session) {
		try {
			return session.createProducer(q);
		} catch (JMSException ex) {
			throw new IllegalStateException(ex);
		}
	}
	 
	 public MessageConsumer getConsumer(Session session) {
		 try {
			MessageConsumer consumer = session.createConsumer(q);
			consumer.setMessageListener(new MDBConsumer());
			
			return consumer;
		} catch (JMSException ex) {
			throw new IllegalStateException(ex);
		}
	 }
}
