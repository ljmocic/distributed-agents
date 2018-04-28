package models;

import java.util.Date;
import java.util.List;

public class Message {

	private int id;
	
	private String sender;
	
	private List<String> receivers;
	
	private Date date;
	
	private String content;
	
	public Message() {}

	public Message(int id, String sender, List<String> receivers, Date date, String content) {
		super();
		this.id = id;
		this.sender = sender;
		this.receivers = receivers;
		this.date = date;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public List<String> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<String> receivers) {
		this.receivers = receivers;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
