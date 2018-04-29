package services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import beans.Group;
import beans.Message;
import beans.User;
import interfaces.GroupServiceLocal;
import interfaces.MessageServiceLocal;
import interfaces.UserServiceLocal;

@Stateless(mappedName = "MessageService")
@LocalBean
public class MessageService implements MessageServiceLocal {

	private Datastore datastore;

	@EJB
	GroupServiceLocal groupService;
	
	@EJB
	UserServiceLocal userService;
	
    public MessageService() {
    	Morphia morphia = new Morphia();
		datastore = morphia.createDatastore(new MongoClient(), "userappdb");
		morphia.mapPackage("beans");
    }

    @Override
	public List<Message> getMessages() {
		return datastore.createQuery(Message.class).asList();
	}
	
	@Override
	public Message createMessage(Message message) {
		User sender = userService.getUserByUsername(message.getSender().getUsername());
		List<User> recs = new ArrayList<User>();
		
		for(User uu: message.getReceivers()) {
			recs.add(userService.getUserByUsername(uu.getUsername()));
		}
		
		message.setSender(sender);
		message.setReceivers(recs);
		
		Key<beans.Message> key = datastore.save(message);
		return datastore.get(Message.class, key);
	}

	@Override
	public List<Message> getMessagesFromUser(User user) {
		List<Message> messages = datastore.createQuery(Message.class)
                .filter("sender ==", user.getId())
                .asList();
		return messages.size() > 0 ? messages : null;
	}

	@Override
	public List<Message> getMessagesFromGroup(String groupName){
		List<Message> allMessages = datastore.createQuery(Message.class).asList();
		
		Group g = groupService.getGroupByName(groupName);
		List<User> members = g.getMembers();
		
		List<Message> groupMessages = new ArrayList<>();
		
		for(Message m: allMessages) {
			List<User> receivers = m.getReceivers();
			receivers.add(m.getSender());
			boolean flag = false;
			
			for(User u: members) {
				if(!receivers.contains(u)) {
					flag = true;
					break;
				}
			}
			
			if(!flag) {
				groupMessages.add(m);
			}
		}
		
		return groupMessages;
	}
	
	@Override
	public void updateMessage(Message message) {
		createMessage(message);
	}

	@Override
	public void deleteMessage(Message message) {
		Message messageToDelete = datastore.get(Message.class, message.getId());
		datastore.delete(messageToDelete);
	}

}
