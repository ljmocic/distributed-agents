package interfaces;

import java.util.List;

import javax.ejb.Local;

import beans.Message;
import beans.User;

@Local
public interface MessageServiceLocal {

	List<Message> getMessages();
	Message createMessage(Message Message);
	List<Message> getMessagesFromChat(User user1, User user2);
	List<Message> getMessagesFromGroup(String group);
	void updateMessage(Message message);
	void deleteMessage(Message message);
}
