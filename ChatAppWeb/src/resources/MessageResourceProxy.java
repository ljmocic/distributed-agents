package resources;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.Path;

import interfaces.MessageResourceInterface;

@Path("/message")
@Stateless
public class MessageResourceProxy implements MessageResourceInterface {

	@Override
	public List<Object> getAllMessages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createMessage(Object message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Object> getMessage(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> getMessageForGroup(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMessage(Object message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMessage(Object message) {
		// TODO Auto-generated method stub
		
	}

}
