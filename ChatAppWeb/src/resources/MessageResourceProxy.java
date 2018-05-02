package resources;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;

import beans.Message;
import interfaces.MessageResourceInterface;
import services.MessageServiceProxy;

@Path("/message")
@Stateless
public class MessageResourceProxy implements MessageResourceInterface {

	@EJB
	MessageServiceProxy messageServiceProxy;
	
	@Override
	public List<Object> getAllMessages() {
		return messageServiceProxy.getRest().getAllMessages();
	}

	@Override
	public Message createMessage(Message message) {
		return messageServiceProxy.getRest().createMessage(message);
	}

	@Override
	public List<Message> getMessage(String username) {
		return messageServiceProxy.getRest().getMessage(username);
	}

	@Override
	public List<Message> getMessageForGroup(String name) {
		return messageServiceProxy.getRest().getMessageForGroup(name);
	}

	@Override
	public void updateMessage(Object message) {
		messageServiceProxy.getRest().updateMessage(message);
		
	}

	@Override
	public void deleteMessage(Object message) {
		messageServiceProxy.getRest().deleteMessage(message);
	}

}
