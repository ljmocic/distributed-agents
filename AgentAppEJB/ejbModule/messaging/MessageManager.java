package messaging;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.ACLMessage;

/**
 * Session Bean implementation class MessageManager
 */
@Stateless
public class MessageManager implements MessageManagerLocal {

	@Resource(lookup = "java:jboss/exported/jms/RemoteConnectionFactory")
	private ConnectionFactory connectionFactory;
	
    public MessageManager() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void sendMessage(ACLMessage message) {
		try {
			Context context = new InitialContext();
			Queue q = (Queue)context.lookup("queue/AgentMessages");
			context.close();
			Connection c = connectionFactory.createConnection();
			Session s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
			c.start();
			
			MessageConsumer consumer = s.createConsumer(q);
			consumer.setMessageListener(new MDBConsumer());
			ObjectMessage msg = s.createObjectMessage(message);
			MessageProducer producer = s.createProducer(q);
			producer.send(msg);
			producer.close();
			consumer.close();
			c.stop();
		}catch(NamingException ne) {
			ne.printStackTrace();
		}catch(JMSException me) {
			me.printStackTrace();
		}
	}

}
