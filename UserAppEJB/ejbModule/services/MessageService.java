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
	
	@EJB
	private NodeService nodeService;
	
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
		
		if(message.getGroup()!=null) {
			Group group = groupService.getGroupByName(message.getGroup().getName());
		
			for(User uu: group.getMembers()) {
				recs.add(userService.getUserByUsername(uu.getUsername()));
			}

			message.setGroup(group);
		}else {
			for(User uu: message.getReceivers()) {
				recs.add(userService.getUserByUsername(uu.getUsername()));
			}
		}
		
		message.setSender(sender);
		message.setReceivers(recs);
		
		Key<beans.Message> key = datastore.save(message);
		
		nodeService.notifyNodes("messages");
		return datastore.get(Message.class, key);
	}

	@Override
	public List<Message> getMessagesFromChat(User user1, User user2) {
		List<Message> messages = datastore.createQuery(Message.class)
                .filter("sender ==", user1)
                .asList();
		messages.addAll(datastore.createQuery(Message.class)
                	.filter("sender ==", user2)
                	.asList());
		
		messages = filterMessages(messages, user1, user2);
		return messages.size() > 0 ? messages : null;
	}
	
	private List<Message> filterMessages(List<Message> messages, User user1, User user2){
		List<Message> reals = new ArrayList<>();
		
		for(Message m: messages) {
			//poruka od prvog -> da li je drugi u primaocima?
			if(m.getSender().getUsername().equals(user1.getUsername())) {
				if(m.getReceivers().size() == 1) {
					if(m.getReceivers().get(0).getUsername().equals(user2.getUsername())) {
						reals.add(m);
					}
				}
			}else if(m.getSender().getUsername().equals(user2.getUsername())) {
				if(m.getReceivers().size() == 1) {
					if(m.getReceivers().get(0).getUsername().equals(user1.getUsername())) {
						reals.add(m);
					}
				}
			}
		}
		
		return reals;
	}

	@Override
	public List<Message> getMessagesFromGroup(String groupName){
		List<Message> allMessages = datastore.createQuery(Message.class).asList();
		
		Group g = groupService.getGroupByName(groupName);
		
		List<Message> groupMessages = new ArrayList<>();
		
		for(Message message: allMessages) {
			if(message.getGroup()!= null && message.getGroup().getName().equals(g.getName())) {
				groupMessages.add(message);
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
