package messaging;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import model.ACLMessage;

/**
 * Session Bean implementation class MessageManager
 */
@Stateless
@LocalBean
public class MessageManager implements MessageManagerLocal {

	@Inject
	JMSConnectionFactory factory;
	private Session session;
	private MessageProducer producer;
	@SuppressWarnings("unused")
	private MessageConsumer consumer;
	
	@PostConstruct
    private void initSession() throws JMSException{
    	session = factory.getSession();
		producer = factory.getProducer(session);
		consumer = factory.getConsumer(session);
    }
   
	@PreDestroy
	public void closeSession() throws JMSException{
		session.close();
	}
	
	@Override
	public void sendMessage(ACLMessage message) {
		try {
			ObjectMessage msg = session.createObjectMessage(message);
			producer.send(msg);
			System.out.println("Message sent to "+message.getSender());
			closeSession();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
