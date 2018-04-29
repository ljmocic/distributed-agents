package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import beans.Message;
import beans.User;
import interfaces.MessageServiceLocal;

@Stateless(mappedName = "MessageService")
@LocalBean
public class MessageService implements MessageServiceLocal {

	private Datastore datastore;

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
	public void updateMessage(Message message) {
		createMessage(message);
	}

	@Override
	public void deleteMessage(Message message) {
		Message messageToDelete = datastore.get(Message.class, message.getId());
		datastore.delete(messageToDelete);
	}

}
