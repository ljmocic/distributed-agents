package beans;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity("messages")
public class Message {

	@Id
	@JsonIgnore
	private ObjectId id;
	private String timestamp;
	private String content;
	
	@Reference
	private User sender;

	@Reference
	private List<User> receivers;
	
	public Message() {
		this.receivers = new ArrayList<User>();
		this.timestamp = LocalDateTime.now().toString();
	}
	
	public Message(User sender, String content) {
		super();
		this.id = new ObjectId();
		this.sender = sender;
		this.receivers = new ArrayList<User>();
		this.timestamp = LocalDateTime.now().toString();
		this.content = content;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public List<User> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<User> receivers) {
		this.receivers = receivers;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
