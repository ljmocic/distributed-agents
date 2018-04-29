package interfaces;

import java.util.List;

import javax.ejb.Local;

import beans.Message;
import beans.User;

@Local
public interface MessageServiceLocal {

	List<Message> getMessages();
	Message createMessage(Message Message);
	List<Message> getMessagesFromUser(User user);
	List<Message> getMessagesFromGroup(String group);
	void updateMessage(Message message);
	void deleteMessage(Message message);
	
}
