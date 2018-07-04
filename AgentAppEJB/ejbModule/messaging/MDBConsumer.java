package messaging;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import agents.AgentManagerLocal;
import model.ACLMessage;
import model.AID;
import model.AgentRemote;
import utils.AgentCenterConfig;

/**
 * Message-Driven Bean implementation class for: MDBConsumer
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "queue/AgentMessages"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "AgentMessages")
public class MDBConsumer implements MessageListener {

	@EJB
	AgentManagerLocal agentManager;
	
    /**
     * Default constructor. 
     */
    public MDBConsumer() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        ObjectMessage om = (ObjectMessage) message;
        try {
        	ACLMessage aclMsg = om.getBody(ACLMessage.class);
        	
        	for(AID aid: aclMsg.getReceivers()) {
        		if(aid.getHost().getAlias().equals(AgentCenterConfig.nodeName)) {
	        		AgentRemote agent = agentManager.getRunningAgent(aid);
	        		if(agent != null) {
	        			System.out.println("Message for "+aid);
	        			agent.handleMessage(aclMsg);
	        		}
        		}
        	}
        }catch(JMSException je) {
        	je.printStackTrace();
        }
    }
}
